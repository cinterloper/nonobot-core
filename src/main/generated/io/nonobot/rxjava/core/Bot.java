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
import io.nonobot.core.BotOptions;
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
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.Bot original} non RX-ified interface using Vert.x codegen.
 */

public class Bot {

  final io.nonobot.core.Bot delegate;

  public Bot(io.nonobot.core.Bot delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Create a new bot for the Vert.x instance
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  public static Bot create(Vertx vertx) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  /**
   * Create a new bot for the Vert.x instance and specified options.
   * @param vertx the Vert.x instance
   * @param options the options
   * @return the created bot
   */
  public static Bot create(Vertx vertx, BotOptions options) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.create((io.vertx.core.Vertx) vertx.getDelegate(), options));
    return ret;
  }

  /**
   * Run the bot with the , the bot will take care of the adapter life cycle and restart it when
   * it gets disconnected, until {@link io.nonobot.rxjava.core.Bot#close} is called.
   * @param adapter the bot adapter
   * @return this instance so it can be used fluently
   */
  public Bot run(BotAdapter adapter) { 
    this.delegate.run((io.nonobot.core.adapter.BotAdapter) adapter.getDelegate());
    return this;
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
  public Bot createClient(Handler<AsyncResult<BotClient>> handler) { 
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
  public Bot createClient(ClientOptions options, Handler<AsyncResult<BotClient>> handler) { 
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
   * Close the bot.
   */
  public void close() { 
    this.delegate.close();
  }

  private Vertx cached_0;
  private java.lang.String cached_1;

  public static Bot newInstance(io.nonobot.core.Bot arg) {
    return arg != null ? new Bot(arg) : null;
  }
}
