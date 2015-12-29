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
import io.vertx.rxjava.core.Future;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.adapter.ConnectionListener original} non RX-ified interface using Vert.x codegen.
 */

public class ConnectionListener extends Future<Void> {

  final io.nonobot.core.adapter.ConnectionListener delegate;

  public ConnectionListener(io.nonobot.core.adapter.ConnectionListener delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public BotClient client() { 
    if (cached_0 != null) {
      return cached_0;
    }
    BotClient ret= BotClient.newInstance(this.delegate.client());
    cached_0 = ret;
    return ret;
  }

  private BotClient cached_0;

  public static ConnectionListener newInstance(io.nonobot.core.adapter.ConnectionListener arg) {
    return arg != null ? new ConnectionListener(arg) : null;
  }
}
