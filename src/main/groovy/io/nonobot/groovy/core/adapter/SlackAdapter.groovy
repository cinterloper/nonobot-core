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
import io.nonobot.core.adapter.SlackOptions
import io.nonobot.groovy.core.NonoBot
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class SlackAdapter {
  private final def io.nonobot.core.adapter.SlackAdapter delegate;
  public SlackAdapter(Object delegate) {
    this.delegate = (io.nonobot.core.adapter.SlackAdapter) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static SlackAdapter create(NonoBot bot, Map<String, Object> options) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.adapter.SlackAdapter.create((io.nonobot.core.NonoBot)bot.getDelegate(), options != null ? new io.nonobot.core.adapter.SlackOptions(new io.vertx.core.json.JsonObject(options)) : null), io.nonobot.groovy.core.adapter.SlackAdapter.class);
    return ret;
  }
  /**
   * Connect to the slack service.
   */
  public void connect() {
    this.delegate.connect();
  }
  /**
   * Connect to the slack service.
   * @param completionHandler 
   */
  public void connect(Handler<AsyncResult<Void>> completionHandler) {
    this.delegate.connect(completionHandler);
  }
  public void closeHandler(Handler<Void> handler) {
    this.delegate.closeHandler(handler);
  }
  public void close() {
    this.delegate.close();
  }
}
