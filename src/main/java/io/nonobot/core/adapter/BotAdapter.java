/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nonobot.core.adapter;

import io.nonobot.core.adapter.impl.BotAdapterImpl;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * Expose the bot to an external (usually remote) service.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotAdapter {

  /**
   * Create new adapter.
   *
   * @param vertx the vertx instance to use
   * @return the bot adapter
   */
  static BotAdapter create(Vertx vertx) {
    return new BotAdapterImpl(vertx);
  }

  /**
   * Run the bot adapter, until it is closed: the adapter performs a connection request. If the connection request
   * fails or if the connection is closed, a new connection request is performed after the reconnect period.
   *
   * @param options the client options to use
   */
  void run(ClientOptions options);

  /**
   * @return true if the adapter is running
   */
  boolean isRunning();

  /**
   * @return true if the adapter is connected
   */
  boolean isConnected();

  /**
   * Set the connection request handler. The request handler is called when the adapter needs to connect to the adapted
   * service. The handler can be called many times (reconnect) but manages a single connection per adapter.<p>
   *
   * When the handler is connected, it should call {@link ConnectionRequest#complete()} to signal the adapter it is
   * connected, if the connection attempt fails, it should instead call {@link ConnectionRequest#fail(String)}.<p>
   *
   * When the adapter is disconnected, the handler should call {@link BotClient#close()} to signal it the adapter.
   * The adapter will likely try to reconnect to the service unless it is in closed state.
   *
   * @param handler the connection request handler
   * @return this object so it can be used fluently
   */
  @Fluent
  BotAdapter requestHandler(Handler<ConnectionRequest> handler);

  /**
   * Like {@link #connect(BotClient,Future)}.
   */
  @Fluent
  default BotAdapter connect(BotClient client) {
    return connect(client, Future.future());
  }

  /**
   * Connect to the adapted service.
   *
   * @param client the client to use
   * @param completionFuture the future to complete or fail when connection is either a success or a failure
   * @return this object so it can be used fluently
   */
  @Fluent
  BotAdapter connect(BotClient client, Future<Void> completionFuture);

  /**
   * Close the adapter, if the adapter is currently running, the current client connection will be closed.
   */
  void close();


}
