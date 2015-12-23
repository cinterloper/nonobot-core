package io.nonobot.core.chat;

import io.nonobot.core.chat.impl.ChatHandlerImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ChatHandler {

  static ChatHandler create(Vertx vertx) {
    return new ChatHandlerImpl(vertx);
  }

  @Fluent
  ChatHandler pattern(String regex);

  @Fluent
  ChatHandler messageHandler(Handler<ChatMessage> handler);

//  void bind();

  void bind(Handler<AsyncResult<Void>> completionHandler);

  void unbind(Handler<AsyncResult<Void>> completionHandler);

}
