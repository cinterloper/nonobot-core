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

package io.nonobot.rxjava.core.adapter;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Expose the bot to an external (usually remote) service.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.adapter.BotAdapter original} non RX-ified interface using Vert.x codegen.
 */

public class BotAdapter {

  final io.nonobot.core.adapter.BotAdapter delegate;

  public BotAdapter(io.nonobot.core.adapter.BotAdapter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Like {@link io.nonobot.rxjava.core.adapter.BotAdapter#connect}.
   */
  public void connect() { 
    this.delegate.connect();
  }

  /**
   * Connect to the adapted service.
   * @param completionHandler the handler when connection is either a success or a failure
   */
  public void connect(Handler<AsyncResult<Void>> completionHandler) { 
    this.delegate.connect(completionHandler);
  }

  /**
   * Connect to the adapted service.
   * @return 
   */
  public Observable<Void> connectObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> completionHandler = io.vertx.rx.java.RxHelper.observableFuture();
    connect(completionHandler.toHandler());
    return completionHandler;
  }

  /**
   * Handler notified when the adapter close.
   * @param handler 
   */
  public void closeHandler(Handler<Void> handler) { 
    this.delegate.closeHandler(handler);
  }

  /**
   * Close the adapter.
   */
  public void close() { 
    this.delegate.close();
  }


  public static BotAdapter newInstance(io.nonobot.core.adapter.BotAdapter arg) {
    return arg != null ? new BotAdapter(arg) : null;
  }
}
