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

package io.nonobot.core.adapter.impl;

import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.adapter.ConnectionRequest;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.client.impl.BotClientImpl;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotAdapterImpl implements BotAdapter {

  private Handler<ConnectionRequest> connectHandler;
  private final Vertx vertx;
  private BotClient client;
  private boolean closed;
  private boolean running;
  private boolean connected;

  public BotAdapterImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public BotAdapter connect(BotClient client, Future<Void> completionFuture) {
    connectHandler.handle(new ConnectionRequestImpl(this, client, completionFuture));
    return this;
  }

  @Override
  public void run(ClientOptions options) {
    if (running) {
      throw new IllegalStateException("Running");
    }
    running = true;
    connect(options.getReconnectPeriod(), options.getName());
  }

  @Override
  public boolean isRunning() {
    return running;
  }

  @Override
  public boolean isConnected() {
    return connected;
  }

  private synchronized void connect(long reconnectPeriod, String name) {
    if (closed) {
      throw new IllegalStateException("Closed");
    }
    if (client != null) {
      throw new IllegalStateException("Already connected");
    }
    client = new BotClientImpl(vertx, vertx.getOrCreateContext(), new ClientOptions().setName(name), ar1 -> {
      if (ar1.succeeded()) {
        Future<Void> completionFuture = Future.future();
        completionFuture.setHandler(ar2 -> {
          if (ar2.failed()) {
            client = null;
            System.out.println("Connection failure");
            ar2.cause().printStackTrace();
            reconnect(reconnectPeriod, name);
          } else {
            connected = true;
          }
        });
        connectHandler.handle(new ConnectionRequestImpl(this, client, completionFuture));
      } else {
        client = null;
        ar1.cause().printStackTrace();
        reconnect(reconnectPeriod, name);
      }
    }) {
      @Override
      public void close() {
        super.close();
        client = null;
        connected = false;
        reconnect(reconnectPeriod, name);
      }
    };
  }

  private synchronized void reconnect(long reconnectPeriod, String name) {
    if (closed) {
      return;
    }
    if (reconnectPeriod > 0) {
      System.out.println("Connection failure, will reconnect after " + reconnectPeriod);
      vertx.setTimer(reconnectPeriod, id -> {
        connect(reconnectPeriod, name);
      });
    }
  }

  @Override
  public BotAdapter requestHandler(Handler<ConnectionRequest> handler) {
    connectHandler = handler;
    return this;
  }

  @Override
  public synchronized void close() {
    if (closed) {
      throw new IllegalStateException();
    }
    closed = true;
    running = false;
    if (client != null) {
      client.close();
    }
  }
}
