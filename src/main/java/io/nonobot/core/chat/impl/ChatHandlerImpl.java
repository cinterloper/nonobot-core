package io.nonobot.core.chat.impl;

import io.nonobot.core.chat.ChatMessage;
import io.nonobot.core.chat.ChatHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

import java.util.UUID;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ChatHandlerImpl implements ChatHandler {

  private final Vertx vertx;
  private String pattern;
  private Handler<ChatMessage> messageHandler;
  private MessageConsumer<String> consumer;
  private String address;

  public ChatHandlerImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public ChatHandler pattern(String regex) {
    pattern = regex;
    return this;
  }

  @Override
  public ChatHandler messageHandler(Handler<ChatMessage> handler) {
    this.messageHandler = handler;
    return this;
  }

  private void handle(Message<String> msg) {
    messageHandler.handle(new ChatMessage() {
      boolean replied;
      @Override
      public String content() {
        return msg.body();
      }
      @Override
      public void reply(String content) {
        if (!replied) {
          replied = true;
          msg.reply(content);
        }
      }
    });
  }

  @Override
  public synchronized void bind(Handler<AsyncResult<Void>> completionHandler) {
    if (consumer != null) {
      throw new IllegalStateException();
    }
    address = UUID.randomUUID().toString();
    consumer = vertx.eventBus().consumer(address, this::handle);
    consumer.completionHandler(ar -> {
      if (ar.succeeded()) {
        while (true) {
          LocalMap<String, Object> registry = vertx.sharedData().getLocalMap("nonobot");
          JsonObject prevHandlers = (JsonObject) registry.get("handlers");
          JsonObject nextHandlers;
          if (prevHandlers == null) {
            nextHandlers = new JsonObject();
          } else {
            nextHandlers = prevHandlers.copy();
          }
          JsonObject desc = new JsonObject().put("pattern", pattern);
          nextHandlers.put(address, desc);
          if (prevHandlers == null) {
            if (registry.putIfAbsent("handlers", nextHandlers) == null) {
              break;
            }
          } else {
            if (registry.replaceIfPresent("handlers", prevHandlers, nextHandlers)) {
              break;
            }
          }
        }
        completionHandler.handle(Future.succeededFuture());
      } else {
        synchronized (ChatHandlerImpl.this) {
          consumer = null;
        }
        completionHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public synchronized void unbind(Handler<AsyncResult<Void>> completionHandler) {
    if (consumer == null) {
      throw new IllegalStateException();
    }
    LocalMap<String, Object> registry = vertx.sharedData().getLocalMap("nonobot");
    while (true) {
      JsonObject prevHandlers = (JsonObject) registry.get("handlers");
      JsonObject nextHandlers;
      if (prevHandlers == null) {
        break;
      } else {
        nextHandlers = prevHandlers.copy();
      }
      nextHandlers.remove(address);
      if (registry.replaceIfPresent("handlers", prevHandlers, nextHandlers)) {
        break;
      }
    }
    consumer.unregister(completionHandler);
    consumer = null;
  }
}
