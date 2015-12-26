package io.nonobot.core.handlers;

import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.handlers.impl.PingHandlerImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface PingHandler {

  static PingHandler create() {
    return new PingHandlerImpl();
  }

  MessageHandler toChatHandler(Vertx vertx, MessageRouter router);

}
