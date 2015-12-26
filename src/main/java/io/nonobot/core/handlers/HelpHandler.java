package io.nonobot.core.handlers;

import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
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

  MessageHandler toChatHandler(Vertx vertx, MessageRouter router);

}
