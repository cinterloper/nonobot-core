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

package io.nonobot.groovy.core.adapter;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.nonobot.core.client.ClientOptions
import io.vertx.groovy.core.Vertx
import io.nonobot.groovy.core.client.BotClient
import io.vertx.core.Handler
import io.vertx.groovy.core.Future
/**
 * Expose the bot to an external (usually remote) service.
*/
@CompileStatic
public class BotAdapter {
  private final def io.nonobot.core.adapter.BotAdapter delegate;
  public BotAdapter(Object delegate) {
    this.delegate = (io.nonobot.core.adapter.BotAdapter) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Create new adapter.
   * @param vertx the vertx instance to use
   * @return the bot adapter
   */
  public static BotAdapter create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.adapter.BotAdapter.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.adapter.BotAdapter.class);
    return ret;
  }
  /**
   * Run the bot adapter, until it is closed: the adapter performs a connection request. If the connection request
   * fails or if the connection is closed, a new connection request is performed after the reconnect period.
   * @param options the client options to use (see <a href="../../../../../../../cheatsheet/ClientOptions.html">ClientOptions</a>)
   */
  public void run(Map<String, Object> options = [:]) {
    this.delegate.run(options != null ? new io.nonobot.core.client.ClientOptions(new io.vertx.core.json.JsonObject(options)) : null);
  }
  /**
   * @return true if the adapter is running
   * @return 
   */
  public boolean isRunning() {
    def ret = this.delegate.isRunning();
    return ret;
  }
  /**
   * @return true if the adapter is connected
   * @return 
   */
  public boolean isConnected() {
    def ret = this.delegate.isConnected();
    return ret;
  }
  /**
   * Set the connection request handler. The request handler is called when the adapter needs to connect to the adapted
   * service. The handler can be called many times (reconnect) but manages a single connection per adapter.<p>
   *
   * When the handler is connected, it should call {@link io.vertx.groovy.core.Future#complete} to signal the adapter it is
   * connected, if the connection attempt fails, it should instead call {@link io.vertx.groovy.core.Future#fail}.<p>
   *
   * When the adapter is disconnected, the handler should call  to signal it the adapter.
   * The adapter will likely try to reconnect to the service unless it is in closed state.
   * @param handler the connection request handler
   * @return this object so it can be used fluently
   */
  public BotAdapter requestHandler(Handler<ConnectionRequest> handler) {
    this.delegate.requestHandler(new Handler<io.nonobot.core.adapter.ConnectionRequest>() {
      public void handle(io.nonobot.core.adapter.ConnectionRequest event) {
        handler.handle(new io.nonobot.groovy.core.adapter.ConnectionRequest(event));
      }
    });
    return this;
  }
  /**
   * Like {@link io.nonobot.groovy.core.adapter.BotAdapter#connect}.
   * @param client 
   * @return 
   */
  public BotAdapter connect(BotClient client) {
    this.delegate.connect((io.nonobot.core.client.BotClient)client.getDelegate());
    return this;
  }
  /**
   * Connect to the adapted service.
   * @param client the client to use
   * @param completionFuture the future to complete or fail when connection is either a success or a failure
   * @return this object so it can be used fluently
   */
  public BotAdapter connect(BotClient client, Future<Void> completionFuture) {
    this.delegate.connect((io.nonobot.core.client.BotClient)client.getDelegate(), (io.vertx.core.Future<java.lang.Void>)completionFuture.getDelegate());
    return this;
  }
  /**
   * Close the adapter, if the adapter is currently running, the current client connection will be closed.
   */
  public void close() {
    this.delegate.close();
  }
}
