package io.nonobot.core.handlers;

import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.handlers.impl.HelpHandlerImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface HelpHandler {

  static HelpHandler create() {
    return new HelpHandlerImpl();
  }

  ChatHandler toChatHandler(Vertx vertx);

}
