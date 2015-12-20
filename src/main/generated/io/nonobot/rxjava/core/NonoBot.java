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
import io.vertx.rxjava.core.Vertx;
import io.nonobot.rxjava.core.client.BotClient;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
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

  public static NonoBot create(Vertx vertx) { 
    NonoBot ret= NonoBot.newInstance(io.nonobot.core.NonoBot.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  public Vertx vertx() { 
    Vertx ret= Vertx.newInstance(this.delegate.vertx());
    return ret;
  }

  public void client(Handler<AsyncResult<BotClient>> handler) { 
    this.delegate.client(new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
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
  }

  public Observable<BotClient> clientObservable() { 
    io.vertx.rx.java.ObservableFuture<BotClient> handler = io.vertx.rx.java.RxHelper.observableFuture();
    client(handler.toHandler());
    return handler;
  }


  public static NonoBot newInstance(io.nonobot.core.NonoBot arg) {
    return arg != null ? new NonoBot(arg) : null;
  }
}
