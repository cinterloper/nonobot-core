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

/** @module nonobot-js/bot */
var utils = require('vertx-js/util/utils');
var ChatRouter = require('nonobot-js/chat_router');
var Vertx = require('vertx-js/vertx');
var Router = require('vertx-web-js/router');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBot = io.nonobot.core.Bot;
var BotOptions = io.nonobot.core.BotOptions;

/**
 The bot.

 @class
*/
var Bot = function(j_val) {

  var j_bot = j_val;
  var that = this;

  /**
   @return the Vert.x instance used by this bot

   @public

   @return {Vertx}
   */
  this.vertx = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedvertx == null) {
        that.cachedvertx = utils.convReturnVertxGen(j_bot["vertx()"](), Vertx);
      }
      return that.cachedvertx;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {ChatRouter}
   */
  this.chatRouter = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedchatRouter == null) {
        that.cachedchatRouter = utils.convReturnVertxGen(j_bot["chatRouter()"](), ChatRouter);
      }
      return that.cachedchatRouter;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   The bot's web router, handlers should add their own route to this router or better mount sub routers. This router is shared
   between the handlers attached to this bot, therefore an handler should not catch all requests going through the router.<p>
  
   The main usage of this router is to provide a web server shared between the handlers, whose purpose is usually to provide
   web service for pushing data to the botin the web hook style.<p>

   @public

   @return {Router} the web router
   */
  this.webRouter = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedwebRouter == null) {
        that.cachedwebRouter = utils.convReturnVertxGen(j_bot["webRouter()"](), Router);
      }
      return that.cachedwebRouter;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   @return the bot name

   @public

   @return {string}
   */
  this.name = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedname == null) {
        that.cachedname = j_bot["name()"]();
      }
      return that.cachedname;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Close the bot.

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_bot["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_bot;
};

/**
 Create a shared bot instance for the Vert.x instance.

 @memberof module:nonobot-js/bot
 @param vertx {Vertx} the Vert.x instance 
 @param options {Object} the bot options 
 @param completionHandler {function} called when the bot is fully initialized 
 @return {Bot} the bot instance
 */
Bot.createShared = function(vertx, options, completionHandler) {
  var __args = arguments;
  if (__args.length === 3 && typeof __args[0] === 'object' && __args[0]._jdel && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
    return utils.convReturnVertxGen(JBot["createShared(io.vertx.core.Vertx,io.nonobot.core.BotOptions,io.vertx.core.Handler)"](vertx._jdel, options != null ? new BotOptions(new JsonObject(JSON.stringify(options))) : null, function(ar) {
    if (ar.succeeded()) {
      completionHandler(null, null);
    } else {
      completionHandler(null, ar.cause());
    }
  }), Bot);
  } else throw new TypeError('function invoked with invalid arguments');
};

/**
 Gets a shared bot instance for the Vert.x instance.

 @memberof module:nonobot-js/bot
 @param vertx {Vertx} the Vert.x instance 
 @param name {string} the bot name 
 @return {Bot} the bot instance
 */
Bot.getShared = function() {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JBot["getShared(io.vertx.core.Vertx)"](__args[0]._jdel), Bot);
  }else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(JBot["getShared(io.vertx.core.Vertx,java.lang.String)"](__args[0]._jdel, __args[1]), Bot);
  } else throw new TypeError('function invoked with invalid arguments');
};

/**
 Create a new bot for the Vert.x instance and specified options.

 @memberof module:nonobot-js/bot
 @param vertx {Vertx} the Vert.x instance 
 @param name {string} the bot name 
 @return {Bot} the created bot
 */
Bot.create = function() {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JBot["create(io.vertx.core.Vertx)"](__args[0]._jdel), Bot);
  }else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(JBot["create(io.vertx.core.Vertx,java.lang.String)"](__args[0]._jdel, __args[1]), Bot);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = Bot;