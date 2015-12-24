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
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.adapter.Adapter original} non RX-ified interface using Vert.x codegen.
 */

public class Adapter {

  final io.nonobot.core.adapter.Adapter delegate;

  public Adapter(io.nonobot.core.adapter.Adapter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Connect to the adapted service.
   */
  public void connect() { 
    this.delegate.connect();
  }

  /**
   * Connect to the adapted service.
   * @param completionHandler 
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
   * Close.
   */
  public void close() { 
    this.delegate.close();
  }


  public static Adapter newInstance(io.nonobot.core.adapter.Adapter arg) {
    return arg != null ? new Adapter(arg) : null;
  }
}
