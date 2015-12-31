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

package io.nonobot.rxjava.core.handler;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.nonobot.core.handler.SendOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * The message router.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.handler.ChatRouter original} non RX-ified interface using Vert.x codegen.
 */

public class ChatRouter {

  final io.nonobot.core.handler.ChatRouter delegate;

  public ChatRouter(io.nonobot.core.handler.ChatRouter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static ChatRouter getShared(Vertx vertx) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.handler.ChatRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  public static ChatRouter getShared(Vertx vertx, String name) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.handler.ChatRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate(), name));
    return ret;
  }

  /**
   * Gets a shared message router instance for the Vert.x instance. There should be a single message router per
   * Vert.x instance.
   * @param vertx the Vert.x instance
   * @param initHandler the handler notified when the router is fully initialized
   * @return the message router
   */
  public static ChatRouter getShared(Vertx vertx, Handler<AsyncResult<Void>> initHandler) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.handler.ChatRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate(), initHandler));
    return ret;
  }

  /**
   * Gets a shared message router instance for the Vert.x instance. There should be a single message router per
   * Vert.x instance.
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @param initHandler the handler notified when the router is fully initialized
   * @return the message router
   */
  public static ChatRouter getShared(Vertx vertx, String name, Handler<AsyncResult<Void>> initHandler) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.handler.ChatRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate(), name, initHandler));
    return ret;
  }

  /**
   * Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  public ChatHandler when(String pattern, Handler<Message> handler) { 
    ChatHandler ret= ChatHandler.newInstance(this.delegate.when(pattern, new Handler<io.nonobot.core.handler.Message>() {
      public void handle(io.nonobot.core.handler.Message event) {
        handler.handle(new Message(event));
      }
    }));
    return ret;
  }

  /**
   * Add a message handler triggered when the <code>pattern</code> prepended with the bot name is fully matched,
   * the pattern is a <code>java.util.regex</code>.
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  public ChatHandler respond(String pattern, Handler<Message> handler) { 
    ChatHandler ret= ChatHandler.newInstance(this.delegate.respond(pattern, new Handler<io.nonobot.core.handler.Message>() {
      public void handle(io.nonobot.core.handler.Message event) {
        handler.handle(new Message(event));
      }
    }));
    return ret;
  }

  /**
   * Send a message to a target.
   * @param options the options
   * @param body the message body
   * @return this object so it can be used fluently
   */
  public ChatRouter sendMessage(SendOptions options, String body) { 
    this.delegate.sendMessage(options, body);
    return this;
  }

  /**
   * Close the message router.
   */
  public void close() { 
    this.delegate.close();
  }


  public static ChatRouter newInstance(io.nonobot.core.handler.ChatRouter arg) {
    return arg != null ? new ChatRouter(arg) : null;
  }
}
