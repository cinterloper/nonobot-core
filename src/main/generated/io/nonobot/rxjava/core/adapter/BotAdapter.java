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
import io.nonobot.rxjava.core.client.BotClient;
import io.vertx.core.Handler;
import io.vertx.rxjava.core.Future;

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

  public static BotAdapter create(Handler<ConnectionListener> handler) { 
    BotAdapter ret= BotAdapter.newInstance(io.nonobot.core.adapter.BotAdapter.create(new Handler<io.nonobot.core.adapter.ConnectionListener>() {
      public void handle(io.nonobot.core.adapter.ConnectionListener event) {
        handler.handle(new ConnectionListener(event));
      }
    }));
    return ret;
  }

  /**
   * Like {@link io.nonobot.rxjava.core.adapter.BotAdapter#connect}.
   * @param client 
   */
  public void connect(BotClient client) { 
    this.delegate.connect((io.nonobot.core.client.BotClient) client.getDelegate());
  }

  /**
   * Connect to the adapted service.
   * @param client 
   * @param completionFuture the future to complete or fail when connection is either a success or a failure
   */
  public void connect(BotClient client, Future<Void> completionFuture) { 
    this.delegate.connect((io.nonobot.core.client.BotClient) client.getDelegate(), (io.vertx.core.Future<java.lang.Void>) completionFuture.getDelegate());
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
