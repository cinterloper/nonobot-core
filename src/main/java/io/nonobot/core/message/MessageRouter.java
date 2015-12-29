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

package io.nonobot.core.message;

import io.nonobot.core.message.impl.MessageRouterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * The message router.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MessageRouter {

  static MessageRouter getShared(Vertx vertx) {
    return getShared(vertx, null);
  }

  /**
   * Gets a shared message router instance for the Vert.x instance. There should be a single message router per
   * Vert.x instance.
   *
   * @param vertx the Vert.x instance
   * @param initHandler the handler notified when the router is fully initialized
   * @return the message router
   */
  static MessageRouter getShared(Vertx vertx, Handler<AsyncResult<Void>> initHandler) {
    return MessageRouterImpl.getShared(vertx, initHandler);
  }

  /**
   * Add a message handler triggered when the {@code pattern} is fully matched, the pattern is a {@code java.util.regex}.
   *
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  MessageHandler when(String pattern, Handler<Message> handler);

  /**
   * Add a message handler triggered when the {@code pattern} prepended with the bot name is fully matched,
   * the pattern is a {@code java.util.regex}.
   *
   * @param pattern the matching pattern
   * @param handler the message handler
   * @return the message handler object
   */
  MessageHandler respond(String pattern, Handler<Message> handler);

  /**
   * Close the message router.
   */
  void close();

}
