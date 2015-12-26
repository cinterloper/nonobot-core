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

package io.nonobot.groovy.core.message;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.groovy.core.Vertx
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class MessageRouter {
  private final def io.nonobot.core.message.MessageRouter delegate;
  public MessageRouter(Object delegate) {
    this.delegate = (io.nonobot.core.message.MessageRouter) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static MessageRouter create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.message.MessageRouter.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.message.MessageRouter.class);
    return ret;
  }
  public static MessageRouter create(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.message.MessageRouter.create((io.vertx.core.Vertx)vertx.getDelegate(), completionHandler), io.nonobot.groovy.core.message.MessageRouter.class);
    return ret;
  }
  public void close() {
    this.delegate.close();
  }
  public MessageHandler handler() {
    def ret= InternalHelper.safeCreate(this.delegate.handler(), io.nonobot.groovy.core.message.MessageHandler.class);
    return ret;
  }
}
