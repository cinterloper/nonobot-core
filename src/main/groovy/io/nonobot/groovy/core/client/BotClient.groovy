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
 * The bot client provides a customized client interface for interacting with the bot.
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
  /**
   * @return the bot this client exposes.
   * @return 
   */
  public NonoBot bot() {
    def ret= InternalHelper.safeCreate(this.delegate.bot(), io.nonobot.groovy.core.NonoBot.class);
    return ret;
  }
  /**
   * Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param name the bot name
   */
  public void rename(String name) {
    this.delegate.rename(name);
  }
  /**
   * Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param names the bot names
   */
  public void rename(List<String> names) {
    this.delegate.rename(names);
  }
  /**
   * Process a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   * if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   * should not wait until the reply is called, instead if should just forward the reply content when it arrives.
   * @param message the message content to process
   * @param replyHandler the handle to be notified with the message reply
   */
  public void process(String message, Handler<AsyncResult<String>> replyHandler) {
    this.delegate.process(message, replyHandler);
  }
  /**
   * Close the client.
   */
  public void close() {
    this.delegate.close();
  }
}
