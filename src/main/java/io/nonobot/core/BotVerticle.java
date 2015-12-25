package io.nonobot.core;

import io.nonobot.core.adapter.IrcAdapter;
import io.nonobot.core.adapter.IrcOptions;
import io.nonobot.core.adapter.SlackAdapter;
import io.nonobot.core.adapter.SlackOptions;
import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.handlers.GiphyHandler;
import io.nonobot.core.handlers.HelpHandler;
import io.vertx.core.AbstractVerticle;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotVerticle extends AbstractVerticle {

  private String getConfigProperty(String name) {
    String value = config().getString(name);
    if (value == null) {
      value = System.getenv(name.replace('.', '_').replace('-', '_').toUpperCase());
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
    String slackToken = getConfigProperty("slack.token");
    if (slackToken != null) {
      System.out.println("Connecting to slack");
      bot.addAdapter(SlackAdapter.create(bot, new SlackOptions().setToken(slackToken)));
    }

    // IRC adapter
    String ircChannels = getConfigProperty("irc.channels");
    if (ircChannels != null) {
      IrcOptions options = new IrcOptions();
      Arrays.asList(ircChannels.split("\\s*,\\s*")).forEach(options::addChannel);;
      String ircHost = getConfigProperty("irc.host");
      if (ircHost != null) {
        options.setHost(ircHost);
      }
      String ircName = getConfigProperty("irc.name");
      if (ircName != null) {
        options.setName(ircName);
      }
      String nickservPassword = getConfigProperty("irc.nickserv-password");
      if (nickservPassword != null) {
        options.setNickServPassword(nickservPassword);
      }
      String ircPort = getConfigProperty("irc.port");
      if (ircPort != null) {
        options.setPort(Integer.parseInt(ircPort));
      }
      IrcAdapter adapter = IrcAdapter.create(bot, options);
      bot.addAdapter(adapter);
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

    // Help handler
    HelpHandler.create().toChatHandler(vertx).bind(ar -> {
      System.out.println("Bound help");
    });
  }


}
