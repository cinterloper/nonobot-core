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

package io.nonobot.rxjava.core;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.rxjava.core.Vertx;
import io.nonobot.rxjava.core.handler.ChatRouter;

/**
 * The bot.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.Bot original} non RX-ified interface using Vert.x codegen.
 */

public class Bot {

  final io.nonobot.core.Bot delegate;

  public Bot(io.nonobot.core.Bot delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Gets a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the bot instance
   */
  public static Bot getShared(Vertx vertx, String name) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.getShared((io.vertx.core.Vertx) vertx.getDelegate(), name));
    return ret;
  }

  /**
   * Gets a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @return the bot instance
   */
  public static Bot getShared(Vertx vertx) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.getShared((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  /**
   * Create a new bot for the Vert.x instance
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  public static Bot create(Vertx vertx) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  /**
   * Create a new bot for the Vert.x instance and specified options.
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the created bot
   */
  public static Bot create(Vertx vertx, String name) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.create((io.vertx.core.Vertx) vertx.getDelegate(), name));
    return ret;
  }

  /**
   * @return the Vert.x instance used by this bot
   * @return 
   */
  public Vertx vertx() { 
    if (cached_0 != null) {
      return cached_0;
    }
    Vertx ret= Vertx.newInstance(this.delegate.vertx());
    cached_0 = ret;
    return ret;
  }

  public ChatRouter chatRouter() { 
    if (cached_1 != null) {
      return cached_1;
    }
    ChatRouter ret= ChatRouter.newInstance(this.delegate.chatRouter());
    cached_1 = ret;
    return ret;
  }

  /**
   * @return the bot name
   * @return 
   */
  public String name() { 
    if (cached_2 != null) {
      return cached_2;
    }
    String ret = this.delegate.name();
    cached_2 = ret;
    return ret;
  }

  /**
   * Close the bot.
   */
  public void close() { 
    this.delegate.close();
  }

  private Vertx cached_0;
  private ChatRouter cached_1;
  private java.lang.String cached_2;

  public static Bot newInstance(io.nonobot.core.Bot arg) {
    return arg != null ? new Bot(arg) : null;
  }
}
