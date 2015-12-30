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

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.client.Message;
import io.nonobot.core.client.impl.BotClientImpl;
import io.nonobot.core.identity.Identity;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NonoBotImpl implements NonoBot {

  static final ConcurrentMap<Vertx, NonoBotImpl> sharedBots = new ConcurrentHashMap<>();

  public static NonoBot getShared(Vertx vertx) {
    return sharedBots.computeIfAbsent(vertx, NonoBotImpl::new);
  }

  final String name = "nono"; // Bot name : make this configurable via options
  final Vertx vertx;
  private boolean closed;
  private Set<BotAdapter> adapters = new HashSet<>();
  final List<BotClientImpl> clients = new ArrayList<>();
  final String outboundAddress = "bots." + name + ".outbound";

  public NonoBotImpl(Vertx vertx) {
    this.vertx = vertx;

    vertx.eventBus().<JsonObject>consumer(outboundAddress, msg -> {
      Identity target = new Identity(msg.body().getJsonObject("target"));
      String body = msg.body().getString("body");
      synchronized (NonoBotImpl.this) {
        for (BotClientImpl client : clients) {
          client.handle(new Message() {
            @Override
            public Identity target() {
              return target;
            }
            @Override
            public String body() {
              return body;
            }
          });
        }
      }
    });
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  public String name() {
    return name;
  }

  @Override
  public NonoBot createClient(Handler<AsyncResult<BotClient>> handler) {
    createClient(new ClientOptions(), handler);
    return this;
  }

  @Override
  public NonoBot createClient(ClientOptions options, Handler<AsyncResult<BotClient>> handler) {
    BotClientImpl client = new BotClientImpl(this, vertx.getOrCreateContext(), options) {
      @Override
      public void close() {
        synchronized (NonoBotImpl.this) {
          clients.remove(this);
        }
      }
    };
    synchronized (this) {
      clients.add(client);
    }
    handler.handle(Future.succeededFuture(client));
    return this;
  }

  @Override
  public NonoBot registerAdapter(BotAdapter adapter) {
    return registerAdapter(adapter, 1000);
  }

  @Override
  public NonoBot registerAdapter(BotAdapter adapter, long reconnectPeriod) {
    synchronized (this) {
      if (closed) {
        throw new IllegalStateException("Closed");
      }
    }
    BotClientImpl client = new BotClientImpl(this, vertx.getOrCreateContext(), new ClientOptions()) {
      @Override
      public void close() {
        synchronized (NonoBotImpl.this) {
          clients.remove(this);
          adapters.remove(adapter);
        }
        reconnect(adapter, reconnectPeriod);
      }
    };
    synchronized (this) {
      clients.add(client);
    }
    Future<Void> completionFuture = Future.future();
    completionFuture.setHandler(ar -> {
      if (ar.succeeded()) {
        synchronized (NonoBotImpl.this) {
          adapters.add(adapter);
          if (!closed) {
            return;
          }
        }
      } else {
        System.out.println("Connection failure");
        ar.cause().printStackTrace();
        reconnect(adapter, reconnectPeriod);
      }
    });
    adapter.connect(client, completionFuture);
    return this;
  }

  private synchronized void reconnect(BotAdapter adapter, long reconnectPeriod) {
    if (closed) {
      return;
    }
    if (reconnectPeriod > 0) {
      System.out.println("Connection failure, will reconnect after " + reconnectPeriod);
      vertx.setTimer(reconnectPeriod, id -> {
        registerAdapter(adapter, reconnectPeriod);
      });
    }
  }

  @Override
  public void close() {
    List<BotAdapter> toClose;
    synchronized (this) {
      if (!closed) {
        closed = true;
        toClose = new ArrayList<>(adapters);
      } else {
        return;
      }
    }
    for (BotAdapter adapter : toClose) {
      adapter.close();
    }
  }
}
