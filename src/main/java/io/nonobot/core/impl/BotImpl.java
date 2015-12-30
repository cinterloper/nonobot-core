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

import io.nonobot.core.BotOptions;
import io.nonobot.core.Bot;
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
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotImpl implements Bot {

  final long reconnectPeriod;
  final String name = "nono"; // Bot name : make this configurable via options
  final Vertx vertx;
  private BotAdapter adapter;
  private boolean closed;
  final List<BotClientImpl> clients = new ArrayList<>();
  final String outboundAddress = "bots." + name + ".outbound";

  public BotImpl(Vertx vertx, BotOptions options) {
    this.reconnectPeriod = options.getReconnectPeriod();
    this.vertx = vertx;

    vertx.eventBus().<JsonObject>consumer(outboundAddress, msg -> {
      Identity target = new Identity(msg.body().getJsonObject("target"));
      String body = msg.body().getString("body");
      synchronized (BotImpl.this) {
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
  public Bot createClient(Handler<AsyncResult<BotClient>> handler) {
    createClient(new ClientOptions(), handler);
    return this;
  }

  @Override
  public Bot createClient(ClientOptions options, Handler<AsyncResult<BotClient>> handler) {
    BotClientImpl client = new BotClientImpl(this, vertx.getOrCreateContext(), options) {
      @Override
      public void close() {
        synchronized (BotImpl.this) {
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
  public Bot run(BotAdapter adapter) {
    synchronized (this) {
      if (this.adapter != null) {
        throw new IllegalStateException("Already running");
      }
      this.adapter = adapter;
    }
    connect(adapter);
    return this;
  }

  private void connect(BotAdapter adapter) {
    BotClientImpl client = new BotClientImpl(this, vertx.getOrCreateContext(), new ClientOptions()) {
      @Override
      public void close() {
        synchronized (BotImpl.this) {
          clients.remove(this);
        }
        reconnect(adapter);
      }
    };
    synchronized (this) {
      clients.add(client);
    }
    Future<Void> completionFuture = Future.future();
    completionFuture.setHandler(ar -> {
      if (ar.failed()) {
        System.out.println("Connection failure");
        ar.cause().printStackTrace();
        reconnect(adapter);
      }
    });
    adapter.connect(client, completionFuture);
  }

  private synchronized void reconnect(BotAdapter adapter) {
    if (closed) {
      return;
    }
    if (reconnectPeriod > 0) {
      System.out.println("Connection failure, will reconnect after " + reconnectPeriod);
      vertx.setTimer(reconnectPeriod, id -> {
        connect(adapter);
      });
    }
  }

  @Override
  public void close() {
    BotAdapter adapter;
    synchronized (this) {
      if (!closed) {
        closed = true;
        adapter = this.adapter;
      } else {
        return;
      }
    }
    adapter.close();
  }
}
