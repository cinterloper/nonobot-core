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

import io.nonobot.core.client.impl.BotClientImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

/**
 * The bot client provides a customized client interface for interacting with the bot.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotClient {

  static void client(Vertx vertx, ClientOptions options, Handler<AsyncResult<BotClient>> handler) {
    new BotClientImpl(vertx, vertx.getOrCreateContext(), options, handler);
  }

  static void client(Vertx vertx, Handler<AsyncResult<BotClient>> handler) {
    new BotClientImpl(vertx, vertx.getOrCreateContext(), new ClientOptions(), handler);
  }

  String name();

  Vertx vertx();

  /**
   * Alias the bot for this client, when the client process a message it will use the specified {@code name} to
   * detect if the message is addressed to the bot or not.
   *
   * @param name the bot name
   */
  void alias(String name);

  /**
   * Alias the bot for this client, when the client process a message it will use the specified {@code name} to
   * detect if the message is addressed to the bot or not.
   *
   * @param names the bot names
   */
  void alias(List<String> names);

  /**
   * Receive a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   * if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   * should not wait until the reply is called, instead if should just forward the reply content when it arrives.
   *
   * @param options the receive options
   * @param message the message content to process
   * @param replyHandler the handle to be notified with the message reply
   */
  void receiveMessage(ReceiveOptions options, String message, Handler<AsyncResult<String>> replyHandler);

  /**
   * Set a message handler on this client.
   *
   * @param handler the message handler
   * @return this object so it can be used fluently
   */
  @Fluent
  BotClient messageHandler(Handler<Message> handler);

  /**
   * Set an handler called when the client is closed.
   *
   * @param handler the handler
   * @return this object so it can be used fluently
   */
  @Fluent
  BotClient closeHandler(Handler<Void> handler);

  /**
   * Close the client.
   */
  void close();

}
