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

package io.nonobot.groovy.core.handlers;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.nonobot.groovy.core.message.MessageHandler
import io.vertx.groovy.core.Vertx
import io.nonobot.groovy.core.message.MessageRouter
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class GiphyHandler {
  private final def io.nonobot.core.handlers.GiphyHandler delegate;
  public GiphyHandler(Object delegate) {
    this.delegate = (io.nonobot.core.handlers.GiphyHandler) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static GiphyHandler create() {
    def ret= InternalHelper.safeCreate(io.nonobot.core.handlers.GiphyHandler.create(), io.nonobot.groovy.core.handlers.GiphyHandler.class);
    return ret;
  }
  public MessageHandler toChatHandler(Vertx vertx, MessageRouter router) {
    def ret= InternalHelper.safeCreate(this.delegate.toChatHandler((io.vertx.core.Vertx)vertx.getDelegate(), (io.nonobot.core.message.MessageRouter)router.getDelegate()), io.nonobot.groovy.core.message.MessageHandler.class);
    return ret;
  }
}
