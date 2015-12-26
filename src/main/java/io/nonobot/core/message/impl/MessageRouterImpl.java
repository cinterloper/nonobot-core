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

package io.nonobot.core.message.impl;

import io.nonobot.core.message.Message;
import io.nonobot.core.message.MessageRouter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class MessageRouterImpl implements MessageRouter {

  final Vertx vertx;
  final MessageConsumer<JsonObject> consumer;
  final List<MessageHandler> messageHandlers = new CopyOnWriteArrayList<>();

  public MessageRouterImpl(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) {
    this.consumer = vertx.eventBus().consumer("nonobot.broadcast", this::handle);
    this.vertx = vertx;

    if (completionHandler != null) {
      consumer.completionHandler(completionHandler);
    }
  }

  private void handle(io.vertx.core.eventbus.Message<JsonObject> message) {
    JsonObject body = message.body();
    boolean respond = body.getBoolean("respond");
    String content = body.getString("content");
    String replyAddress = body.getString("replyAddress");
    for (MessageHandler handler : messageHandlers) {
      if (handler.respond == respond) {
        Matcher matcher = handler.pattern.matcher(content);
        if (matcher.matches()) {
          handler.handler.handle(new Message() {
            boolean replied;
            @Override
            public String body() {
              return content;
            }
            @Override
            public void reply(String content) {
              if (!replied) {
                replied = true;
                vertx.eventBus().send(replyAddress, content);
              }
            }
          });
          return;
        }
      }
    }
    message.reply(null);
  }

  @Override
  public io.nonobot.core.message.MessageHandler handler() {
    return new io.nonobot.core.message.MessageHandler() {
      List<MessageHandler> handlers = new ArrayList<>();
      @Override
      public io.nonobot.core.message.MessageHandler when(String pattern, Handler<Message> handler) {
        handlers.add(new MessageHandler(false, Pattern.compile(pattern), handler));
        return this;
      }
      @Override
      public io.nonobot.core.message.MessageHandler respond(String pattern, Handler<Message> handler) {
        handlers.add(new MessageHandler(true, Pattern.compile(pattern), handler));
        return this;
      }
      @Override
      public void create() {
        messageHandlers.addAll(this.handlers);
      }
    };
  }

  @Override
  public void close() {
    consumer.unregister();
  }

  class MessageHandler {
    final boolean respond;
    final Pattern pattern;
    final Handler<Message> handler;
    public MessageHandler(boolean respond, Pattern pattern, Handler<Message> handler) {
      this.respond = respond;
      this.pattern = pattern;
      this.handler = handler;
    }
  }
}
