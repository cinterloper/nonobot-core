package io.nonobot.core.handlers;

import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.chat.ChatRouter;
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

  ChatHandler toChatHandler(Vertx vertx, ChatRouter router);

}
