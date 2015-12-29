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

import io.nonobot.core.identity.Identity;
import io.nonobot.core.message.Message;
import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.message.SendOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class MessageRouterImpl implements MessageRouter {

  static final ConcurrentMap<Vertx, MessageRouterImpl> routers = new ConcurrentHashMap<>();

  public static MessageRouter getShared(Vertx vertx, Handler<AsyncResult<Void>> initHandler) {
    MessageRouterImpl router = routers.computeIfAbsent(vertx, MessageRouterImpl::new);
    if (initHandler != null) {
      Context context = vertx.getOrCreateContext();
      router.registerForInit(ar -> context.runOnContext(v -> initHandler.handle(ar)));
    }
    return router;
  }

  final Vertx vertx;
  final MessageConsumer<JsonObject> consumer;
  final List<MessageHandlerImpl> messageHandlers = new ArrayList<>();
  final List<Handler<AsyncResult<Void>>> initHandlers = new CopyOnWriteArrayList<>();
  final Future<Void> initFuture = Future.future();

  public MessageRouterImpl(Vertx vertx) {
    this.consumer = vertx.eventBus().consumer("nonobot.inbound", this::handle);
    this.vertx = vertx;

    consumer.completionHandler(ar -> {
      if (ar.succeeded()) {
        initFuture.succeeded();
      } else {
        initFuture.fail(ar.cause());
      }
    });

    initFuture.setHandler(ar -> {
      synchronized (MessageRouterImpl.this) {
        for (Handler<AsyncResult<Void>> completionHandler : initHandlers) {
          completionHandler.handle(ar);
        }
      }
    });
  }

  private synchronized MessageRouterImpl registerForInit(Handler<AsyncResult<Void>> initHandler) {
    if (initFuture.isComplete()) {
      initHandler.handle(initFuture);
    } else {
      initHandlers.add(initHandler);
    }
    return this;
  }

  private void handle(io.vertx.core.eventbus.Message<JsonObject> message) {
    JsonObject body = message.body();
    boolean respond = body.getBoolean("respond");
    String content = body.getString("content");
    String replyAddress = body.getString("replyAddress");
    Identity room = body.getJsonObject("room") != null ? new Identity(body.getJsonObject("room")) : null;
    Identity user = body.getJsonObject("user") != null ? new Identity(body.getJsonObject("user")) : null;
    for (MessageHandlerImpl handler : messageHandlers) {
      if (handler.respond == respond) {
        Matcher matcher = handler.pattern.matcher(content);
        if (matcher.matches()) {
          handler.handler.handle(new Message() {
            boolean replied;
            @Override
            public Identity room() {
              return room;
            }
            @Override
            public Identity user() {
              return user;
            }
            @Override
            public String body() {
              return content;
            }
            @Override
            public void reply(String msg) {
              reply(msg, null);
            }
            @Override
            public void reply(String msg, Handler<AsyncResult<Void>> ackHandler) {
              reply(msg, DeliveryOptions.DEFAULT_TIMEOUT, ackHandler);
            }
            @Override
            public void reply(String msg, long ackTimeout, Handler<AsyncResult<Void>> ackHandler) {
              if (!replied) {
                replied = true;
                if (ackHandler != null) {
                  vertx.eventBus().send(replyAddress, msg, new DeliveryOptions().setSendTimeout(ackTimeout), ack -> {
                    if (ack.succeeded()) {
                      ackHandler.handle(Future.succeededFuture());
                    } else {
                      ackHandler.handle(Future.failedFuture(ack.cause()));
                    }
                  });
                } else {
                  vertx.eventBus().send(replyAddress, msg);
                }
              } else if (ackHandler != null) {
                ackHandler.handle(Future.failedFuture("Already replied"));
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
  public io.nonobot.core.message.MessageHandler when(String pattern, Handler<Message> handler) {
    MessageHandlerImpl messageHandler = new MessageHandlerImpl(false, Pattern.compile(pattern), handler);
    messageHandlers.add(messageHandler);
    return messageHandler;
  }

  @Override
  public io.nonobot.core.message.MessageHandler respond(String pattern, Handler<Message> handler) {
    MessageHandlerImpl messageHandler = new MessageHandlerImpl(true, Pattern.compile(pattern), handler);
    messageHandlers.add(messageHandler);
    return messageHandler;
  }

  @Override
  public MessageRouter sendMessage(SendOptions options, String body) {

    vertx.eventBus().publish("nonobot.outbound", new JsonObject().put("target", options.getTarget().toJson()).put("body", body));

    return this;
  }

  @Override
  public void close() {
    consumer.unregister();
  }

  class MessageHandlerImpl implements MessageHandler {
    final boolean respond;
    final Pattern pattern;
    final Handler<Message> handler;
    public MessageHandlerImpl(boolean respond, Pattern pattern, Handler<Message> handler) {
      this.respond = respond;
      this.pattern = pattern;
      this.handler = handler;
    }
    @Override
    public void close() {
      throw new UnsupportedOperationException("todo");
    }
  }
}
