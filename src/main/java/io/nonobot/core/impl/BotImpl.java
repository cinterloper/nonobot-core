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

package io.nonobot.core.impl;

import io.nonobot.core.Bot;
import io.nonobot.core.BotOptions;
import io.nonobot.core.chat.ChatRouter;
import io.nonobot.core.chat.impl.ChatRouterImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotImpl implements Bot {

  static final class Key {

    final Vertx vertx;
    final String name;

    public Key(Vertx vertx, String name) {
      this.vertx = vertx;
      this.name = name;
    }

    public boolean equals(Object that) {
      return ((Key) that).vertx == vertx && ((Key) that).name.equals(name);
    }

    @Override
    public int hashCode() {
      return vertx.hashCode() ^ name.hashCode();
    }
  }

  static final ConcurrentMap<Key, BotImpl> bots = new ConcurrentHashMap<>();

  public static Bot createShared(Vertx vertx, BotOptions options, Handler<AsyncResult<Void>> completionHandler) {
    BotImpl bot = bots.computeIfAbsent(new Key(vertx, options.getName()), key -> new BotImpl(vertx, options.getName()));
    HttpServer server;
    if (options.getHttpServerOptions() != null) {
      server = vertx.createHttpServer(options.getHttpServerOptions());
      server.requestHandler(bot.webRouter::accept);
      server.listen(ar -> {
        if (ar.succeeded()) {
          completionHandler.handle(Future.succeededFuture());
        } else {
          completionHandler.handle(Future.failedFuture(ar.cause()));
        }
      });
    } else {
      server = null;
      completionHandler.handle(Future.succeededFuture());
    }
    return new Bot() {
      @Override
      public Vertx vertx() {
        return bot.vertx();
      }
      @Override
      public ChatRouter chatRouter() {
        return bot.chatRouter();
      }
      @Override
      public Router webRouter() {
        return bot.webRouter();
      }
      @Override
      public String name() {
        return bot.name();
      }
      @Override
      public void close() {
        boolean open = bot.closed;
        bot.close();
        if (open && server != null) {
          server.close();
        }
      }
    };
  }

  public static Bot getShared(Vertx vertx, String name) {
    return bots.computeIfAbsent(new Key(vertx, name), key -> new BotImpl(vertx, name));
  }

  final String name;
  final Vertx vertx;
  private boolean closed;
  private ChatRouterImpl chatRouter;
  private Router webRouter;

  public BotImpl(Vertx vertx, String name) {
    this.vertx = vertx;
    this.name = name;
    this.chatRouter = new ChatRouterImpl(vertx, name);
    this.webRouter = Router.router(vertx);
  }

  @Override
  public ChatRouter chatRouter() {
    return chatRouter;
  }

  @Override
  public Router webRouter() {
    return webRouter;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  public String name() {
    return name;
  }

  @Override
  public void close() {
    synchronized (this) {
      if (!closed) {
        closed = true;
      } else {
        return;
      }
    }
  }
}
