package io.nonobot.core;

import io.nonobot.core.adapter.Adapter;
import io.nonobot.core.chat.ChatRouter;
import io.nonobot.core.handlers.GiphyHandler;
import io.nonobot.core.handlers.HelpHandler;
import io.nonobot.core.handlers.PingHandler;
import io.nonobot.core.spi.AdapterFactory;
import io.vertx.core.AbstractVerticle;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotVerticle extends AbstractVerticle {

  private final Config config = new Config() {
    @Override
    public String getProperty(String name) {
      String value = config().getString(name);
      if (value == null) {
        value = System.getenv(name.replace('.', '_').replace('-', '_').toUpperCase());
      }
      return value;
    }
  };

  @Override
  public void start() throws Exception {

    // Basic status
    vertx.createHttpServer().requestHandler(req -> {
      req.response().putHeader("Content-Type", "text/plain").end("Application started");
    }).listen(Integer.getInteger("http.port", 8080), System.getProperty("http.address", "localhost"));

    NonoBot bot = NonoBot.create(vertx);

    Iterator<AdapterFactory> adapterFactoryIt = ServiceLoader.load(AdapterFactory.class).iterator();
    while (true) {
      AdapterFactory factory = null;
      try {
        if (adapterFactoryIt.hasNext()) {
          factory = adapterFactoryIt.next();
        } else {
          break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (factory != null) {
        Adapter adapter = factory.create(bot, config);
        if (adapter != null) {
          bot.addAdapter(adapter);
        }
      }
    }

    ChatRouter router = ChatRouter.create(vertx, ar -> {}); // Handle AR

    // Echo handler
    router.handler().
        respond("^echo\\s+(.+)", msg -> {
          msg.reply(msg.content().substring(4));
        }).create();

    // Giphy handler
    GiphyHandler.create().toChatHandler(vertx, router).create();

    // Ping handler
    PingHandler.create().toChatHandler(vertx, router).create();

    // Help handler
    HelpHandler.create().toChatHandler(vertx, router).create();
  }
}
