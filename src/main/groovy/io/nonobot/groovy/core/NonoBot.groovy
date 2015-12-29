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

package io.nonobot.groovy.core;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.nonobot.groovy.core.adapter.BotAdapter
import io.nonobot.core.client.ClientOptions
import io.vertx.groovy.core.Vertx
import io.nonobot.groovy.core.client.BotClient
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * The bot.
*/
@CompileStatic
public class NonoBot {
  private final def io.nonobot.core.NonoBot delegate;
  public NonoBot(Object delegate) {
    this.delegate = (io.nonobot.core.NonoBot) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * @param vertx the vertx instance
   * @param vertx 
   * @return the shared bot instance
   */
  public static NonoBot getShared(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.NonoBot.getShared((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.NonoBot.class);
    return ret;
  }
  /**
   * Create a new bot for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  public static NonoBot create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.NonoBot.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.NonoBot.class);
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
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
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
    def ret = this.delegate.name();
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
        AsyncResult<BotClient> f
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()))
        } else {
          f = InternalHelper.<BotClient>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Create a new bot client with the specified .
   * @param options the client options (see <a href="../../../../../../cheatsheet/ClientOptions.html">ClientOptions</a>)
   * @param handler receives the  after initialization
   * @return this instance so it can be used fluently
   */
  public NonoBot createClient(Map<String, Object> options, Handler<AsyncResult<BotClient>> handler) {
    this.delegate.createClient(options != null ? new io.nonobot.core.client.ClientOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()))
        } else {
          f = InternalHelper.<BotClient>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Like {@link io.nonobot.groovy.core.NonoBot#registerAdapter} with a period of <code>1</code> second.
   * @param adapter 
   * @return 
   */
  public NonoBot registerAdapter(BotAdapter adapter) {
    this.delegate.registerAdapter((io.nonobot.core.adapter.BotAdapter)adapter.getDelegate());
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
    this.delegate.registerAdapter((io.nonobot.core.adapter.BotAdapter)adapter.getDelegate(), reconnectPeriod);
    return this;
  }
  /**
   * Close the bot.
   */
  public void close() {
    this.delegate.close();
  }
  private Vertx cached_0;
  private String cached_1;
}
