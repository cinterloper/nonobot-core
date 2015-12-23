package io.nonobot.core.chat.impl;

import io.nonobot.core.chat.ChatMessage;
import io.nonobot.core.chat.ChatHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ChatHandlerImpl implements ChatHandler {

  private final Vertx vertx;
  private MessageConsumer<String> consumer;
  private String address;
  private final List<Matcher> matchers = new ArrayList<>();

  private class Matcher {
    final boolean respond;
    final String pattern;
    final Handler<ChatMessage> handler;
    private Matcher(boolean respond, String pattern, Handler<ChatMessage> handler) {
      this.respond = respond;
      this.pattern = pattern;
      this.handler = handler;
    }
    void handle(Message<String> msg) {
      handler.handle(new ChatMessage() {
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
  }

  public ChatHandlerImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public ChatHandler match(String pattern, Handler<ChatMessage> handler) {
    matchers.add(new Matcher(false, pattern, handler));
    return this;
  }

  @Override
  public ChatHandler respond(String pattern, Handler<ChatMessage> handler) {
    matchers.add(new Matcher(true, pattern, handler));
    return this;
  }

  private JsonObject createDesc() {
    JsonObject desc = new JsonObject();
    desc.put("matchers", new JsonArray());
    for (Matcher matcher : matchers) {
      desc.getJsonArray("matchers").add(
          new JsonObject().
              put("pattern", matcher.pattern).
              put("respond", matcher.respond));
    }
    return desc;
  }

  private Handler<Message<String>> createHandlers() {
    List<Matcher> copy = new ArrayList<>(matchers);
    return msg -> {
      for (Matcher matcher : copy) {
        matcher.handle(msg);
      }
    };
  }

  @Override
  public synchronized void bind(Handler<AsyncResult<Void>> completionHandler) {
    if (consumer != null) {
      throw new IllegalStateException();
    }
    address = UUID.randomUUID().toString();
    consumer = vertx.eventBus().consumer(address, createHandlers());
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
          nextHandlers.put(address, createDesc());
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
