package io.nonobot.core.handlers.impl;

import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.chat.ChatRouter;
import io.nonobot.core.handlers.PingHandler;
import io.vertx.core.Vertx;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PingHandlerImpl implements PingHandler {

  static final Pattern p = Pattern.compile("^ping");

  @Override
  public ChatHandler toChatHandler(Vertx vertx, ChatRouter router) {
    ChatHandler handler = router.handler();
    handler.respond(p.pattern(), msg -> {
      msg.reply("pong");
    });
    return handler;
  }
}
