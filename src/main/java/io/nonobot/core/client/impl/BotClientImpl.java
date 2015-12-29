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

import io.nonobot.core.NonoBot;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.client.ProcessOptions;
import io.vertx.core.AsyncResult;
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
public class BotClientImpl implements BotClient {

  private final NonoBot bot;
  private volatile Pattern botPattern;
  private final ClientOptions options;
  Handler<Void> closeHandler;

  public BotClientImpl(NonoBot bot, ClientOptions options) {

    // Default names
    rename(Arrays.asList(bot.name(), "@" + bot.name()));

    this.bot = bot;
    this.options = new ClientOptions(options);
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
  public NonoBot bot() {
    return bot;
  }

  @Override
  public void process(String message, Handler<AsyncResult<String>> replyHandler) {
    process(new ProcessOptions(), message, replyHandler);
  }

  @Override
  public void process(ProcessOptions options, String message, Handler<AsyncResult<String>> replyHandler) {
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
        JsonObject body = new JsonObject().put("replyAddress", replyAddress);
        if (botMatcher.find()) {
          body.put("respond", true);
          body.put("content", botMatcher.group(1));
        } else {
          body.put("respond", false);
          body.put("content", message);
        }
        bot.vertx().eventBus().publish("nonobot.broadcast", body);
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
  public synchronized void closeHandler(Handler<Void> handler) {
    closeHandler = handler;
  }

  @Override
  public void close() {
  }
}
