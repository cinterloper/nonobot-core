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

import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.adapter.ConsoleBotAdapter;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.handlers.EchoHandler;
import io.nonobot.core.handlers.GiphyHandler;
import io.nonobot.core.handlers.GitHubVerticle;
import io.nonobot.core.handlers.HelpHandler;
import io.nonobot.core.handlers.PingHandler;
import io.nonobot.core.handlers.TimerHandler;
import io.nonobot.core.spi.BotAdapterFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotVerticle extends AbstractVerticle {

  private Bot bot;
  private BotAdapter adapter;

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    // Get bot options
    BotOptions botOptions = config().getJsonObject("botOptions") != null ? new BotOptions(config().getJsonObject("botOptions")) : new BotOptions();

    // Config
    Config config = new Config() {
      @Override
      public String getProperty(String name) {
        Object value = config().getValue(name);
        if (value == null) {
          String envKey = botOptions.getName() + "." + name;
          value = System.getenv(envKey.replace('.', '_').replace('-', '_').toUpperCase());
        }
        return value != null ? value.toString() : null;
      }
    };

    Iterator<BotAdapterFactory> adapterFactoryIt = ServiceLoader.load(BotAdapterFactory.class).iterator();
    while (true) {
      BotAdapterFactory factory = null;
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
        adapter = factory.create(vertx, config);
        if (adapter != null) {
          break;
        }
      }
    }

    // Use the console adapter
    if (adapter == null) {
      adapter = BotAdapter.create(vertx).requestHandler(new ConsoleBotAdapter());
    }

    //
    if (adapter != null) {
      adapter.run(new ClientOptions());
    } else {
      throw new Exception("No adapter found");
    }

    DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("bot.name", botOptions.getName()));

    vertx.deployVerticle(new GiphyHandler(), options);
    vertx.deployVerticle(new HelpHandler(), options);
    vertx.deployVerticle(new PingHandler(), options);
    vertx.deployVerticle(new EchoHandler(), options);
    vertx.deployVerticle(new TimerHandler(), options);
    vertx.deployVerticle(new GitHubVerticle(), options);

    bot = Bot.createShared(vertx, botOptions, ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });

  }

  @Override
  public void stop() throws Exception {
    bot.close();
    if (adapter != null) {
      adapter.close();
    }
  }
}
