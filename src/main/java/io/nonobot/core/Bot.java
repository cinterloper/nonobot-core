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

import io.nonobot.core.handler.ChatRouter;
import io.nonobot.core.impl.BotImpl;
import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;

/**
 * The bot.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Bot {

  /**
   * Gets a shared bot instance for the Vert.x instance.
   *
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the bot instance
   */
  static Bot getShared(Vertx vertx, String name) {
    return BotImpl.getShared(vertx, name);
  }

  /**
   * Gets a shared bot instance for the Vert.x instance.
   *
   * @param vertx the Vert.x instance
   * @return the bot instance
   */
  static Bot getShared(Vertx vertx) {
    return BotImpl.getShared(vertx, "nono");
  }

  /**
   * Create a new bot for the Vert.x instance
   *
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  static Bot create(Vertx vertx) {
    return new BotImpl(vertx, "nono");
  }

  /**
   * Create a new bot for the Vert.x instance and specified options.
   *
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the created bot
   */
  static Bot create(Vertx vertx, String name) {
    return new BotImpl(vertx, name);
  }

  /**
   * @return the Vert.x instance used by this bot
   */
  @CacheReturn
  Vertx vertx();

  @CacheReturn
  ChatRouter chatRouter();

  /**
   * @return the bot name
   */
  @CacheReturn
  String name();

  /**
   * Close the bot.
   */
  void close();
}
