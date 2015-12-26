package io.nonobot.core.handlers.impl;

import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.handlers.PingHandler;
import io.vertx.core.Vertx;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PingHandlerImpl implements PingHandler {

  static final Pattern p = Pattern.compile("^ping");

  @Override
  public MessageHandler toChatHandler(Vertx vertx, MessageRouter router) {
    MessageHandler handler = router.handler();
    handler.respond(p.pattern(), msg -> {
      msg.reply("pong");
    });
    return handler;
  }
}
