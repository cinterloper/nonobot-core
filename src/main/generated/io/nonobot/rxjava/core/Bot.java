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
import io.nonobot.core.BotOptions;
import io.nonobot.rxjava.core.chat.ChatRouter;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

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
   * Create a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @param options the bot options
   * @param completionHandler called when the bot is fully initialized
   * @return the bot instance
   */
  public static Bot createShared(Vertx vertx, BotOptions options, Handler<AsyncResult<Void>> completionHandler) { 
    Bot ret= Bot.newInstance(io.nonobot.core.Bot.createShared((io.vertx.core.Vertx) vertx.getDelegate(), options, completionHandler));
    return ret;
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
   * The bot's web router, handlers should add their own route to this router or better mount sub routers. This router is shared
   * between the handlers attached to this bot, therefore an handler should not catch all requests going through the router.<p>
   *
   * The main usage of this router is to provide a web server shared between the handlers, whose purpose is usually to provide
   * web service for pushing data to the botin the web hook style.<p>
   * @return the web router
   */
  public Router webRouter() { 
    if (cached_2 != null) {
      return cached_2;
    }
    Router ret= Router.newInstance(this.delegate.webRouter());
    cached_2 = ret;
    return ret;
  }

  /**
   * @return the bot name
   * @return 
   */
  public String name() { 
    if (cached_3 != null) {
      return cached_3;
    }
    String ret = this.delegate.name();
    cached_3 = ret;
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
  private Router cached_2;
  private java.lang.String cached_3;

  public static Bot newInstance(io.nonobot.core.Bot arg) {
    return arg != null ? new Bot(arg) : null;
  }
}
