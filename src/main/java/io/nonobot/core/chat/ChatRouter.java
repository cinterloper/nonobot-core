package io.nonobot.core.chat;

import io.nonobot.core.chat.impl.ChatRouterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ChatRouter {

  static ChatRouter create(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) {
    return new ChatRouterImpl(vertx, completionHandler);
  }

  void close();

  ChatHandler handler();

}
