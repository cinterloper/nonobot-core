/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nonobot.core;

import io.nonobot.core.adapter.Adapter;
import io.nonobot.core.adapter.ConsoleAdapter;
import io.nonobot.core.message.MessageRouter;
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
      Object value = config().getValue(name);
      if (value == null) {
        value = System.getenv(name.replace('.', '_').replace('-', '_').toUpperCase());
      }
      return value != null ? value.toString() : null;
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

    if ("true".equals(config.getProperty("console"))) {
      bot.addAdapter(new ConsoleAdapter(bot));
    }

    MessageRouter router = MessageRouter.create(vertx, ar -> {}); // Handle AR

    // Echo handler
    router.handler().
        respond("^echo\\s+(.+)", msg -> {
          msg.reply(msg.body().substring(4));
        }).create();

    // Giphy handler
    GiphyHandler.create().toChatHandler(vertx, router).create();

    // Ping handler
    PingHandler.create().toChatHandler(vertx, router).create();

    // Help handler
    HelpHandler.create().toChatHandler(vertx, router).create();
  }
}
