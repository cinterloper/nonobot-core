package io.nonobot.core.handlers;

import io.nonobot.core.chat.ChatHandler;
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

  ChatHandler toChatHandler(Vertx vertx);

}
