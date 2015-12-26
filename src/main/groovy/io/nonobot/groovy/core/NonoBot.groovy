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
import io.nonobot.groovy.core.adapter.Adapter
import io.nonobot.core.client.ClientOptions
import io.vertx.groovy.core.Vertx
import io.nonobot.groovy.core.client.BotClient
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
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
  public static NonoBot create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.NonoBot.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.NonoBot.class);
    return ret;
  }
  public Vertx vertx() {
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
    return ret;
  }
  public String name() {
    def ret = this.delegate.name();
    return ret;
  }
  public void client(Handler<AsyncResult<BotClient>> handler) {
    this.delegate.client(new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
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
  }
  public void client(Handler<AsyncResult<BotClient>> handler, Map<String, Object> options) {
    this.delegate.client(new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()))
        } else {
          f = InternalHelper.<BotClient>failure(event.cause())
        }
        handler.handle(f)
      }
    }, options != null ? new io.nonobot.core.client.ClientOptions(new io.vertx.core.json.JsonObject(options)) : null);
  }
  public NonoBot addAdapter(Adapter adapter) {
    this.delegate.addAdapter((io.nonobot.core.adapter.Adapter)adapter.getDelegate());
    return this;
  }
  public NonoBot addAdapter(Adapter adapter, long reconnectPeriod) {
    this.delegate.addAdapter((io.nonobot.core.adapter.Adapter)adapter.getDelegate(), reconnectPeriod);
    return this;
  }
  public void close() {
    this.delegate.close();
  }
}
