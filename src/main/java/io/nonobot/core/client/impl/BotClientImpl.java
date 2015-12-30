/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nonobot.core.client.impl;

import io.nonobot.core.Bot;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.client.ReceiveOptions;
import io.nonobot.core.client.Message;
import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class BotClientImpl implements BotClient {

  final Context context;
  private final Bot bot;
  private volatile Pattern botPattern;
  private final ClientOptions options;
  private final String inboundAddress;
  Handler<Message> messageHandler;
  Handler<Void> closeHandler;

  public BotClientImpl(Bot bot, Context context, ClientOptions options) {

    // Default names
    rename(Arrays.asList(bot.name(), "@" + bot.name()));

    this.context = context;
    this.bot = bot;
    this.options = new ClientOptions(options);
    this.inboundAddress = "bots." + bot.name() + ".inbound";
  }

  public void handle(Message msg) {
    Handler<Message> handler;
    synchronized (this) {
      handler = messageHandler;
    }
    if (handler != null) {
      context.runOnContext(v -> {
        handler.handle(msg);
      });
    }
  }

  @Override
  public void rename(String name) {
    rename(Collections.singletonList(name));
  }

  @Override
  public void rename(List<String> names) {
    StringBuilder tmp = new StringBuilder("^(?:");
    Iterator<String> it = names.iterator();
    while (it.hasNext()) {
      String name = it.next();
      tmp.append("(?:").append(Pattern.quote(name)).append(")");
      if (it.hasNext()) {
        tmp.append("|");
      }
    }
    tmp.append(")");
    tmp.append("(?:\\s|:)\\s*(.*)");
    botPattern = Pattern.compile(tmp.toString());
  }

  @Override
  public Bot bot() {
    return bot;
  }

  @Override
  public void receiveMessage(ReceiveOptions options, String message, Handler<AsyncResult<String>> replyHandler) {
    String replyAddress = UUID.randomUUID().toString();
    Future<String> reply = Future.future();
    reply.setHandler(replyHandler);
    MessageConsumer<String> consumer = bot.vertx().eventBus().consumer(replyAddress);
    consumer.handler(msg -> {
      String content = msg.body();
      if (content != null && !reply.isComplete()) {
        if (msg.replyAddress() != null) {
          msg.reply(null);
        }
        reply.complete(content);
        consumer.unregister();
      } else {
        if (msg.replyAddress() != null) {
          msg.fail(0, "Already replied");
        }
      }
    });
    consumer.completionHandler(ar -> {
      if (ar.succeeded()) {
        Matcher botMatcher = botPattern.matcher(message);
        JsonObject msg = new JsonObject().put("replyAddress", replyAddress);
        if (botMatcher.find()) {
          msg.put("respond", true);
          msg.put("content", botMatcher.group(1));
        } else {
          msg.put("respond", false);
          msg.put("content", message);
        }
        if (options.getUser() != null) {
          msg.put("user", options.getUser().toJson());
        }
        if (options.getRoom() != null) {
          msg.put("room", options.getRoom().toJson());
        }
        bot.vertx().eventBus().publish(inboundAddress, msg);
        bot.vertx().setTimer(options.getTimeout(), timerID -> {
          if (!reply.isComplete()) {
            consumer.unregister();
            reply.fail(new Exception("timeout"));
          }
        });
      } else {
        replyHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public synchronized BotClient messageHandler(Handler<Message> handler) {
    messageHandler = handler;
    return this;
  }

  @Override
  public synchronized BotClient closeHandler(Handler<Void> handler) {
    closeHandler = handler;
    return this;
  }
}
