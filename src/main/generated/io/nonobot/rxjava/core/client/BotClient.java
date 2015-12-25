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

package io.nonobot.rxjava.core.client;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.client.BotClient original} non RX-ified interface using Vert.x codegen.
 */

public class BotClient {

  final io.nonobot.core.client.BotClient delegate;

  public BotClient(io.nonobot.core.client.BotClient delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public String name() { 
    String ret = this.delegate.name();
    return ret;
  }

  public void process(String message, Handler<AsyncResult<String>> handler) { 
    this.delegate.process(message, handler);
  }

  public Observable<String> processObservable(String message) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    process(message, handler.toHandler());
    return handler;
  }

  public void close() { 
    this.delegate.close();
  }

  public void closeHandler(Handler<Void> handler) { 
    this.delegate.closeHandler(handler);
  }


  public static BotClient newInstance(io.nonobot.core.client.BotClient arg) {
    return arg != null ? new BotClient(arg) : null;
  }
}
