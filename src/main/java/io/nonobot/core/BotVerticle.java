package io.nonobot.core;

import io.nonobot.core.adapter.SlackAdapter;
import io.nonobot.core.adapter.SlackOptions;
import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.handlers.GiphyHandler;
import io.vertx.core.AbstractVerticle;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    String slackToken = config().getString("slack.token");
    if (slackToken == null) {
      throw new Exception("No Slack token found");
    }

    NonoBot bot = NonoBot.create(vertx);

    // Echo handler
    ChatHandler.
        create(vertx).
        respond("^echo\\s+(.+)", msg -> {
          msg.reply(msg.content().substring(4));
        }).bind(ar -> {
      System.out.println("Bound Echo");
    });

    // Giphy handler
    GiphyHandler.create().toChatHandler(vertx).bind(ar -> {
      System.out.println("Bound Giphy");
    });

    // Slack adapter
    SlackAdapter slack = SlackAdapter.create(bot, new SlackOptions().setToken(slackToken));
    slack.closeHandler(v -> {
      System.out.println("Disconnected");
    });

    // Connec to slack
    slack.connect(ar -> {
      if (ar.succeeded()) {
        System.out.println("Connected so Slack");
      } else {
        System.out.println("Coult not connect to slack");
        ar.cause().printStackTrace();
      }
    });
  }


}
