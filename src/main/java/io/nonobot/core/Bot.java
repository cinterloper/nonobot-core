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

package io.nonobot.core;

import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.impl.BotImpl;
import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * The bot.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Bot {

  /**
   * Create a new bot for the Vert.x instance
   *
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  static Bot create(Vertx vertx) {
    return new BotImpl(vertx, new BotOptions());
  }

  /**
   * Create a new bot for the Vert.x instance and specified options.
   *
   * @param vertx the Vert.x instance
   * @param options the options
   * @return the created bot
   */
  static Bot create(Vertx vertx, BotOptions options) {
    return new BotImpl(vertx, options);
  }

  /**
   * Run the bot with the {@literal adapter}, the bot will take care of the adapter life cycle and restart it when
   * it gets disconnected, until {@link #close()} is called.
   *
   * @param adapter the bot adapter
   * @return this instance so it can be used fluently
   */
  @Fluent
  Bot run(BotAdapter adapter);

  /**
   * @return the Vert.x instance used by this bot
   */
  @CacheReturn
  Vertx vertx();

  /**
   * @return the bot name
   */
  @CacheReturn
  String name();

  /**
   * Create a new bot client.
   *
   * @param handler receives the {@link BotClient} after initialization
   * @return this instance so it can be used fluently
   */
  @Fluent
  Bot createClient(Handler<AsyncResult<BotClient>> handler);

  /**
   * Create a new bot client with the specified {@link ClientOptions}.
   *
   * @param options the client options
   * @param handler receives the {@link BotClient} after initialization
   * @return this instance so it can be used fluently
   */
  @Fluent
  Bot createClient(ClientOptions options, Handler<AsyncResult<BotClient>> handler);

  /**
   * Close the bot.
   */
  void close();
}
