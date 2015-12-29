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
 * The message router.
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
  public static MessageRouter getShared(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.message.MessageRouter.getShared((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.message.MessageRouter.class);
    return ret;
  }
  /**
   * Gets a shared message router instance for the Vert.x instance. There should be a single message router per
   * Vert.x instance.
   * @param vertx the Vert.x instance
   * @param initHandler the handler notified when the router is fully initialized
   * @return the message router
   */
  public static MessageRouter getShared(Vertx vertx, Handler<AsyncResult<Void>> initHandler) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.message.MessageRouter.getShared((io.vertx.core.Vertx)vertx.getDelegate(), initHandler), io.nonobot.groovy.core.message.MessageRouter.class);
    return ret;
  }
  /**
   * Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  public MessageHandler when(String pattern, Handler<Message> handler) {
    def ret= InternalHelper.safeCreate(this.delegate.when(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new io.nonobot.groovy.core.message.Message(event));
      }
    }), io.nonobot.groovy.core.message.MessageHandler.class);
    return ret;
  }
  /**
   * Add a message handler triggered when the <code>pattern</code> prepended with the bot name is fully matched,
   * the pattern is a <code>java.util.regex</code>.
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  public MessageHandler respond(String pattern, Handler<Message> handler) {
    def ret= InternalHelper.safeCreate(this.delegate.respond(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new io.nonobot.groovy.core.message.Message(event));
      }
    }), io.nonobot.groovy.core.message.MessageHandler.class);
    return ret;
  }
  /**
   * Close the message router.
   */
  public void close() {
    this.delegate.close();
  }
}
