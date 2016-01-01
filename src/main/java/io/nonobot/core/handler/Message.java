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

package io.nonobot.core.handler;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * A message sent to an handler.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Message {

  /**
   * @return the id that uniquely identifies the chat this message is coming from, this id can be used for posting
   *         messages to the chat
   */
  String chatId();

  /**
   * @return the message body
   */
  String body();

  /**
   * Return a group matched in the regex this message matched.
   *
   * @param index the index of the group
   * @return the group
   */
  String matchedGroup(int index);

  /**
   * Reply to the message.
   *
   * @param msg the reply
   */
  void reply(String msg);

  /**
   * Reply to the message with an acknowledgement handler.
   *
   * @param msg the reply
   * @param ackHandler handler to be notified if the reply is consumed effectively
   */
  void reply(String msg, Handler<AsyncResult<Void>> ackHandler);

  /**
   * Reply to the message with an acknowledgement handler given a {@code timeout}.
   *
   * @param msg the reply
   * @param ackTimeout the acknowledgement timeout
   * @param ackHandler handler to be notified if the reply is consumed effectively
   */
  void reply(String msg, long ackTimeout, Handler<AsyncResult<Void>> ackHandler);

}
