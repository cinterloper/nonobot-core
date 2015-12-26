package io.nonobot.core.handlers;

import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.handlers.impl.GiphyHandlerImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface GiphyHandler {

  static GiphyHandler create() {
    return new GiphyHandlerImpl();
  }

  MessageHandler toChatHandler(Vertx vertx, MessageRouter router);

}
