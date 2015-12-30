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
import io.nonobot.core.BotOptions
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
public class Bot {
  private final def io.nonobot.core.Bot delegate;
  public Bot(Object delegate) {
    this.delegate = (io.nonobot.core.Bot) delegate;
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
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Create a new bot for the Vert.x instance and specified options.
   * @param vertx the Vert.x instance
   * @param options the options (see <a href="../../../../../../cheatsheet/BotOptions.html">BotOptions</a>)
   * @return the created bot
   */
  public static Bot create(Vertx vertx, Map<String, Object> options) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.create((io.vertx.core.Vertx)vertx.getDelegate(), options != null ? new io.nonobot.core.BotOptions(new io.vertx.core.json.JsonObject(options)) : null), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Run the bot with the , the bot will take care of the adapter life cycle and restart it when
   * it gets disconnected.
   * @param adapter the bot adapter
   * @return this instance so it can be used fluently
   */
  public Bot run(BotAdapter adapter) {
    this.delegate.run((io.nonobot.core.adapter.BotAdapter)adapter.getDelegate());
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
  public Bot createClient(Handler<AsyncResult<BotClient>> handler) {
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
  public Bot createClient(Map<String, Object> options, Handler<AsyncResult<BotClient>> handler) {
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
   * Close the bot.
   */
  public void close() {
    this.delegate.close();
  }
  private Vertx cached_0;
  private String cached_1;
}
