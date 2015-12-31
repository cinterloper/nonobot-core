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

package io.nonobot.core.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ReceiveOptions {

  private static final long DEFAULT_TIMEOUT = 10000;

  private long timeout;
  private String chatId;

  public ReceiveOptions() {
    timeout = DEFAULT_TIMEOUT;
  }

  public ReceiveOptions(JsonObject json) {
    throw new UnsupportedOperationException("todo");
  }

  public ReceiveOptions(ReceiveOptions that) {
    timeout = that.timeout;
    chatId = that.chatId;
  }

  public long getTimeout() {
    return timeout;
  }

  /**
   * The timeout in millis to wait until a message is considered not processed by the bot.
   *
   * @param timeout the process timeout
   * @return this object so it can be used fluently
   */
  public ReceiveOptions setTimeout(long timeout) {
    this.timeout = timeout;
    return this;
  }

  public String getChatId() {
    return chatId;
  }

  /**
   * Set the id that uniquely identifies where the message is coming from. This id can be used for idenfying the source
   * of this message or to post a message some time later to the chat.
   *
   * @param chatId the chat id
   * @return this object so it can be used fluently
   */
  public ReceiveOptions setChatId(String chatId) {
    this.chatId = chatId;
    return this;
  }
}
