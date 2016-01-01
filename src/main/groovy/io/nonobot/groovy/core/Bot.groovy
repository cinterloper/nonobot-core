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

package io.nonobot.groovy.core;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.nonobot.core.BotOptions
import io.nonobot.groovy.core.chat.ChatRouter
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.ext.web.Router
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * The bot.
*/
@CompileStatic
public class Bot {
  private final def io.nonobot.core.Bot delegate;
  public Bot(Object delegate) {
    this.delegate = (io.nonobot.core.Bot) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Create a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @param options the bot options (see <a href="../../../../../../cheatsheet/BotOptions.html">BotOptions</a>)
   * @param completionHandler called when the bot is fully initialized
   * @return the bot instance
   */
  public static Bot createShared(Vertx vertx, Map<String, Object> options, Handler<AsyncResult<Void>> completionHandler) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.createShared((io.vertx.core.Vertx)vertx.getDelegate(), options != null ? new io.nonobot.core.BotOptions(new io.vertx.core.json.JsonObject(options)) : null, completionHandler), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Gets a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the bot instance
   */
  public static Bot getShared(Vertx vertx, String name) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.getShared((io.vertx.core.Vertx)vertx.getDelegate(), name), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Gets a shared bot instance for the Vert.x instance.
   * @param vertx the Vert.x instance
   * @return the bot instance
   */
  public static Bot getShared(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.getShared((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Create a new bot for the Vert.x instance
   * @param vertx the Vert.x instance
   * @return the created bot
   */
  public static Bot create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.Bot.class);
    return ret;
  }
  /**
   * Create a new bot for the Vert.x instance and specified options.
   * @param vertx the Vert.x instance
   * @param name the bot name
   * @return the created bot
   */
  public static Bot create(Vertx vertx, String name) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.Bot.create((io.vertx.core.Vertx)vertx.getDelegate(), name), io.nonobot.groovy.core.Bot.class);
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
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
    cached_0 = ret;
    return ret;
  }
  public ChatRouter chatRouter() {
    if (cached_1 != null) {
      return cached_1;
    }
    def ret= InternalHelper.safeCreate(this.delegate.chatRouter(), io.nonobot.groovy.core.chat.ChatRouter.class);
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
    def ret= InternalHelper.safeCreate(this.delegate.webRouter(), io.vertx.groovy.ext.web.Router.class);
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
    def ret = this.delegate.name();
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
  private String cached_3;
}
