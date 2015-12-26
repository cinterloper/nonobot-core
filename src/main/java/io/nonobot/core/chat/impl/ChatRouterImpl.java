package io.nonobot.core.chat.impl;

import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.chat.ChatMessage;
import io.nonobot.core.chat.ChatRouter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
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
public class ChatRouterImpl implements ChatRouter {

  final Vertx vertx;
  final MessageConsumer<JsonObject> consumer;
  final List<MessageHandler> messageHandlers = new CopyOnWriteArrayList<>();

  public ChatRouterImpl(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) {
    this.consumer = vertx.eventBus().consumer("nonobot.broadcast", this::handle);
    this.vertx = vertx;

    consumer.completionHandler(completionHandler);
  }

  private void handle(Message<JsonObject> message) {
    JsonObject body = message.body();
    boolean respond = body.getBoolean("respond");
    String content = body.getString("content");
    String replyAddress = body.getString("replyAddress");
    for (MessageHandler handler : messageHandlers) {
      if (handler.respond == respond) {
        Matcher matcher = handler.pattern.matcher(content);
        if (matcher.matches()) {
          handler.handler.handle(new ChatMessage() {
            boolean replied;
            @Override
            public String content() {
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
  public ChatHandler handler() {
    return new ChatHandler() {
      List<MessageHandler> handlers = new ArrayList<>();
      @Override
      public ChatHandler match(String pattern, Handler<ChatMessage> handler) {
        handlers.add(new MessageHandler(false, Pattern.compile(pattern), handler));
        return this;
      }
      @Override
      public ChatHandler respond(String pattern, Handler<ChatMessage> handler) {
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
    final Handler<ChatMessage> handler;
    public MessageHandler(boolean respond, Pattern pattern, Handler<ChatMessage> handler) {
      this.respond = respond;
      this.pattern = pattern;
      this.handler = handler;
    }
  }
}
