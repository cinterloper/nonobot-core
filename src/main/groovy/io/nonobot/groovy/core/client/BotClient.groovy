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

package io.nonobot.groovy.core.client;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import java.util.List
import io.nonobot.groovy.core.NonoBot
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class BotClient {
  private final def io.nonobot.core.client.BotClient delegate;
  public BotClient(Object delegate) {
    this.delegate = (io.nonobot.core.client.BotClient) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public NonoBot bot() {
    def ret= InternalHelper.safeCreate(this.delegate.bot(), io.nonobot.groovy.core.NonoBot.class);
    return ret;
  }
  public void rename(String name) {
    this.delegate.rename(name);
  }
  public void rename(List<String> names) {
    this.delegate.rename(names);
  }
  public void process(String message, Handler<AsyncResult<String>> handler) {
    this.delegate.process(message, handler);
  }
  public void close() {
    this.delegate.close();
  }
  public void closeHandler(Handler<Void> handler) {
    this.delegate.closeHandler(handler);
  }
}
