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
/**
 * A message to send.
*/
@CompileStatic
public class Message {
  private final def io.nonobot.core.client.Message delegate;
  public Message(Object delegate) {
    this.delegate = (io.nonobot.core.client.Message) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * @return the chat id where the message should be posted
   * @return 
   */
  public String chatId() {
    def ret = this.delegate.chatId();
    return ret;
  }
  /**
   * @return the message body
   * @return 
   */
  public String body() {
    def ret = this.delegate.body();
    return ret;
  }
}
