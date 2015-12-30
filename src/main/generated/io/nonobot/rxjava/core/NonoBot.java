/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.nonobot.rxjava.core;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.nonobot.rxjava.core.adapter.BotAdapter;
import io.nonobot.core.client.ClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.nonobot.rxjava.core.client.BotClient;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * The bot.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.NonoBot original} non RX-ified interface using Vert.x codegen.
 */

public class NonoBot {

  final io.nonobot.core.NonoBot delegate;

  public NonoBot(io.nonobot.core.NonoBot delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Create a new bot for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  public static NonoBot create(Vertx vertx) { 
    NonoBot ret= NonoBot.newInstance(io.nonobot.core.NonoBot.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  /**
   * @return the Vert.x instance used by this bot
   * @return 
   */
  public Vertx vertx() { 
    if (cached_0 != null) {
      return cached_0;
    }
    Vertx ret= Vertx.newInstance(this.delegate.vertx());
    cached_0 = ret;
    return ret;
  }

  /**
   * @return the bot name
   * @return 
   */
  public String name() { 
    if (cached_1 != null) {
      return cached_1;
    }
    String ret = this.delegate.name();
    cached_1 = ret;
    return ret;
  }

  /**
   * Create a new bot client.
   * @param handler receives the  after initialization
   * @return this instance so it can be used fluently
   */
  public NonoBot createClient(Handler<AsyncResult<BotClient>> handler) { 
    this.delegate.createClient(new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f;
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()));
        } else {
          f = InternalHelper.<BotClient>failure(event.cause());
        }
        handler.handle(f);
      }
    });
    return this;
  }

  /**
   * Create a new bot client.
   * @return 
   */
  public Observable<BotClient> createClientObservable() { 
    io.vertx.rx.java.ObservableFuture<BotClient> handler = io.vertx.rx.java.RxHelper.observableFuture();
    createClient(handler.toHandler());
    return handler;
  }

  /**
   * Create a new bot client with the specified .
   * @param options the client options
   * @param handler receives the  after initialization
   * @return this instance so it can be used fluently
   */
  public NonoBot createClient(ClientOptions options, Handler<AsyncResult<BotClient>> handler) { 
    this.delegate.createClient(options, new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f;
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()));
        } else {
          f = InternalHelper.<BotClient>failure(event.cause());
        }
        handler.handle(f);
      }
    });
    return this;
  }

  /**
   * Create a new bot client with the specified .
   * @param options the client options
   * @return 
   */
  public Observable<BotClient> createClientObservable(ClientOptions options) { 
    io.vertx.rx.java.ObservableFuture<BotClient> handler = io.vertx.rx.java.RxHelper.observableFuture();
    createClient(options, handler.toHandler());
    return handler;
  }

  /**
   * Like {@link io.nonobot.core.NonoBot} with a period of <code>1</code> second.
   * @param adapter 
   * @return 
   */
  public NonoBot registerAdapter(BotAdapter adapter) { 
    this.delegate.registerAdapter((io.nonobot.core.adapter.BotAdapter) adapter.getDelegate());
    return this;
  }

  /**
   * Add an  with the bot, the bot will take care of the adapter life cycle and restart it when
   * it gets disconnected;
   * @param adapter the bot adapter
   * @param reconnectPeriod how long wait before it attempts to reconnect in millis
   * @return this instance so it can be used fluently
   */
  public NonoBot registerAdapter(BotAdapter adapter, long reconnectPeriod) { 
    this.delegate.registerAdapter((io.nonobot.core.adapter.BotAdapter) adapter.getDelegate(), reconnectPeriod);
    return this;
  }

  /**
   * Close the bot.
   */
  public void close() { 
    this.delegate.close();
  }

  private Vertx cached_0;
  private java.lang.String cached_1;

  public static NonoBot newInstance(io.nonobot.core.NonoBot arg) {
    return arg != null ? new NonoBot(arg) : null;
  }
}
