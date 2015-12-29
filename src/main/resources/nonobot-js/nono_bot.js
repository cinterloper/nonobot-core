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

/** @module nonobot-js/nono_bot */
var utils = require('vertx-js/util/utils');
var BotAdapter = require('nonobot-js/bot_adapter');
var Vertx = require('vertx-js/vertx');
var BotClient = require('nonobot-js/bot_client');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JNonoBot = io.nonobot.core.NonoBot;
var ClientOptions = io.nonobot.core.client.ClientOptions;

/**
 The bot.

 @class
*/
var NonoBot = function(j_val) {

  var j_nonoBot = j_val;
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
        that.cachedvertx = utils.convReturnVertxGen(j_nonoBot["vertx()"](), Vertx);
      }
      return that.cachedvertx;
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
        that.cachedname = j_nonoBot["name()"]();
      }
      return that.cachedname;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Create a new bot client with the specified .

   @public
   @param options {Object} the client options 
   @param handler {function} receives the  after initialization 
   @return {NonoBot} this instance so it can be used fluently
   */
  this.createClient = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_nonoBot["createClient(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        __args[0](utils.convReturnVertxGen(ar.result(), BotClient), null);
      } else {
        __args[0](null, ar.cause());
      }
    });
      return that;
    }  else if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_nonoBot["createClient(io.nonobot.core.client.ClientOptions,io.vertx.core.Handler)"](__args[0] != null ? new ClientOptions(new JsonObject(JSON.stringify(__args[0]))) : null, function(ar) {
      if (ar.succeeded()) {
        __args[1](utils.convReturnVertxGen(ar.result(), BotClient), null);
      } else {
        __args[1](null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Add an  with the bot, the bot will take care of the adapter life cycle and restart it when
   it gets disconnected;

   @public
   @param adapter {BotAdapter} the bot adapter 
   @param reconnectPeriod {number} how long wait before it attempts to reconnect in millis 
   @return {NonoBot} this instance so it can be used fluently
   */
  this.registerAdapter = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_nonoBot["registerAdapter(io.nonobot.core.adapter.BotAdapter)"](__args[0]._jdel);
      return that;
    }  else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] ==='number') {
      j_nonoBot["registerAdapter(io.nonobot.core.adapter.BotAdapter,long)"](__args[0]._jdel, __args[1]);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Close the bot.

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_nonoBot["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_nonoBot;
};

/**
 @param vertx the vertx instance

 @memberof module:nonobot-js/nono_bot
 @param vertx {Vertx} 
 @return {NonoBot} the shared bot instance
 */
NonoBot.getShared = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JNonoBot["getShared(io.vertx.core.Vertx)"](vertx._jdel), NonoBot);
  } else throw new TypeError('function invoked with invalid arguments');
};

/**
 Create a new bot for the Vert.x instance.

 @memberof module:nonobot-js/nono_bot
 @param vertx {Vertx} the Vert.x instance 
 @return {NonoBot} the created bot
 */
NonoBot.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JNonoBot["create(io.vertx.core.Vertx)"](vertx._jdel), NonoBot);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = NonoBot;