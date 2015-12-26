package io.nonobot.core.handlers.impl;

import io.nonobot.core.message.MessageHandler;
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.handlers.HelpHandler;
import io.vertx.core.Vertx;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class HelpHandlerImpl implements HelpHandler {

  static final Pattern p = Pattern.compile("^help(\\s.+)?");

  @Override
  public MessageHandler toChatHandler(Vertx vertx, MessageRouter router) {
    return router.handler().respond(p.pattern(), msg -> {
      msg.reply(
          "Nonobot version 0.01 - https://github.com/nonobot/nonobot-core\n" +
          "\n" +
          "In a room, start your sentence with the nonobot name:\n" +
          "@nono: giphy something\n" +
          "\n" +
          "In private messages, don't mention the name\n" +
          "\n" +
          "Available handlers:\n" +
          "\n" +
          "- giphy something\n" +
          "- echo something\n" +
          "- help\n" +
          "");
    });
  }
}
