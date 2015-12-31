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
import io.nonobot.core.client.ReceiveOptions
import io.nonobot.core.client.ClientOptions
import io.vertx.groovy.core.Vertx
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
  public static void client(Vertx vertx, Map<String, Object> options, Handler<AsyncResult<BotClient>> handler) {
    io.nonobot.core.client.BotClient.client((io.vertx.core.Vertx)vertx.getDelegate(), options != null ? new io.nonobot.core.client.ClientOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()))
        } else {
          f = InternalHelper.<BotClient>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  public static void client(Vertx vertx, Handler<AsyncResult<BotClient>> handler) {
    io.nonobot.core.client.BotClient.client((io.vertx.core.Vertx)vertx.getDelegate(), new Handler<AsyncResult<io.nonobot.core.client.BotClient>>() {
      public void handle(AsyncResult<io.nonobot.core.client.BotClient> event) {
        AsyncResult<BotClient> f
        if (event.succeeded()) {
          f = InternalHelper.<BotClient>result(new BotClient(event.result()))
        } else {
          f = InternalHelper.<BotClient>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  public String name() {
    def ret = this.delegate.name();
    return ret;
  }
  public Vertx vertx() {
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
    return ret;
  }
  /**
   * Alias the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param name the bot name
   */
  public void alias(String name) {
    this.delegate.alias(name);
  }
  /**
   * Alias the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param names the bot names
   */
  public void alias(List<String> names) {
    this.delegate.alias(names);
  }
  /**
   * Receive a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   * if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   * should not wait until the reply is called, instead if should just forward the reply content when it arrives.
   * @param options the receive options (see <a href="../../../../../../../cheatsheet/ReceiveOptions.html">ReceiveOptions</a>)
   * @param message the message content to process
   * @param replyHandler the handle to be notified with the message reply
   */
  public void receiveMessage(Map<String, Object> options = [:], String message, Handler<AsyncResult<String>> replyHandler) {
    this.delegate.receiveMessage(options != null ? new io.nonobot.core.client.ReceiveOptions(new io.vertx.core.json.JsonObject(options)) : null, message, replyHandler);
  }
  /**
   * Set a message handler on this client.
   * @param handler the message handler
   * @return this object so it can be used fluently
   */
  public BotClient messageHandler(Handler<Message> handler) {
    this.delegate.messageHandler(new Handler<io.nonobot.core.client.Message>() {
      public void handle(io.nonobot.core.client.Message event) {
        handler.handle(new io.nonobot.groovy.core.client.Message(event));
      }
    });
    return this;
  }
  /**
   * Set an handler called when the client is closed.
   * @param handler the handler
   * @return this object so it can be used fluently
   */
  public BotClient closeHandler(Handler<Void> handler) {
    this.delegate.closeHandler(handler);
    return this;
  }
  /**
   * Close the client.
   */
  public void close() {
    this.delegate.close();
  }
}
