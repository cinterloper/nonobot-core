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
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.handler.MessageRouter original} non RX-ified interface using Vert.x codegen.
 */

public class MessageRouter {

  final io.nonobot.core.handler.MessageRouter delegate;

  public MessageRouter(io.nonobot.core.handler.MessageRouter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static MessageRouter getShared(Vertx vertx) { 
    MessageRouter ret= MessageRouter.newInstance(io.nonobot.core.handler.MessageRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate()));
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
    MessageRouter ret= MessageRouter.newInstance(io.nonobot.core.handler.MessageRouter.getShared((io.vertx.core.Vertx) vertx.getDelegate(), initHandler));
    return ret;
  }

  /**
   * Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  public MessageHandler when(String pattern, Handler<Message> handler) { 
    MessageHandler ret= MessageHandler.newInstance(this.delegate.when(pattern, new Handler<io.nonobot.core.handler.Message>() {
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
  public MessageHandler respond(String pattern, Handler<Message> handler) { 
    MessageHandler ret= MessageHandler.newInstance(this.delegate.respond(pattern, new Handler<io.nonobot.core.handler.Message>() {
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
  public MessageRouter sendMessage(SendOptions options, String body) { 
    this.delegate.sendMessage(options, body);
    return this;
  }

  /**
   * Close the message router.
   */
  public void close() { 
    this.delegate.close();
  }


  public static MessageRouter newInstance(io.nonobot.core.handler.MessageRouter arg) {
    return arg != null ? new MessageRouter(arg) : null;
  }
}
