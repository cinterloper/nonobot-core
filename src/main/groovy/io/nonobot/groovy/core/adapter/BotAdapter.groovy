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
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
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
   * Like {@link io.nonobot.groovy.core.adapter.BotAdapter#connect}.
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
}
