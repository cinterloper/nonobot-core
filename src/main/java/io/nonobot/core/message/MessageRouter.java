package io.nonobot.core.message;

import io.nonobot.core.message.impl.MessageRouterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MessageRouter {

  static MessageRouter create(Vertx vertx) {
    return create(vertx, null);
  }

  static MessageRouter create(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) {
    return new MessageRouterImpl(vertx, completionHandler);
  }

  void close();

  MessageHandler handler();

}
