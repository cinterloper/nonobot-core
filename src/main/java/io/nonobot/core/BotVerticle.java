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

  private String getConfigProperty(String configName, String envName) {
    String value = config().getString(configName);
    if (value == null) {
      value = System.getenv(envName);
    }
    return value;
  }

  @Override
  public void start() throws Exception {

    // Basic status
    vertx.createHttpServer().requestHandler(req -> {
      req.response().putHeader("Content-Type", "text/plain").end("Application started");
    }).listen(Integer.getInteger("http.port", 8080), System.getProperty("http.address", "localhost"));

    NonoBot bot = NonoBot.create(vertx);

    // Slack adapter
    String slackToken = getConfigProperty("slack.token", "SLACK_TOKEN");
    if (slackToken != null) {
      System.out.println("Connecting to slack");
      bot.addAdapter(SlackAdapter.create(bot, new SlackOptions().setToken(slackToken)));
    }

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
  }


}
