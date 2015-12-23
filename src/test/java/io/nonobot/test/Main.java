package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.SlackAdapter;
import io.nonobot.core.adapter.SlackOptions;
import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.handlers.GiphyHandler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Main {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();
    NonoBot bot = NonoBot.create(vertx);

    ChatHandler.
        create(vertx).
        pattern("^echo\\s+(.+)").
        messageHandler(msg -> {
          msg.reply(msg.content().substring(4));
        }).bind(ar -> {
      System.out.println("echo handler bound");
    });

    GiphyHandler.create().toChatHandler(vertx).bind(ar -> {
      System.out.println("Giphy handler bound");
    });

    SlackAdapter slack = SlackAdapter.create(bot, new SlackOptions().setToken("your-token-here"));
    slack.closeHandler(v -> {
      System.out.println("disconnected");
    });
    slack.connect(ar -> {
      if (ar.succeeded()) {
        System.out.println("connected");
      } else {
        ar.cause().printStackTrace();
      }
    });

/*
    ChatHandler echoHandler = ChatHandler.create(vertx);
    echoHandler.pattern("^echo\\s+(.+)");
    echoHandler.messageHandler(msg -> {
      msg.reply(msg.content());
    });
    echoHandler.bind(ar -> {});
    TermServer server = TermServer.createTelnetTermServer(vertx, new TelnetTermOptions().setPort(5000));
    server.termHandler(ShellAdapter.create(vertx));
    server.listen(ar -> {});
*/
  }
}
