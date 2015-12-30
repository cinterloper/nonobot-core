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
var BotAdapter = require('nonobot-js/bot_adapter');
var Vertx = require('vertx-js/vertx');
var BotClient = require('nonobot-js/bot_client');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBot = io.nonobot.core.Bot;
var BotOptions = io.nonobot.core.BotOptions;
var ClientOptions = io.nonobot.core.client.ClientOptions;

/**
 The bot.

 @class
*/
var Bot = function(j_val) {

  var j_bot = j_val;
  var that = this;

  /**
   Run the bot with the , the bot will take care of the adapter life cycle and restart it when
   it gets disconnected, until {@link Bot#close} is called.

   @public
   @param adapter {BotAdapter} the bot adapter 
   @return {Bot} this instance so it can be used fluently
   */
  this.run = function(adapter) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_bot["run(io.nonobot.core.adapter.BotAdapter)"](adapter._jdel);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

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
   Create a new bot client with the specified .

   @public
   @param options {Object} the client options 
   @param handler {function} receives the  after initialization 
   @return {Bot} this instance so it can be used fluently
   */
  this.createClient = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_bot["createClient(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        __args[0](utils.convReturnVertxGen(ar.result(), BotClient), null);
      } else {
        __args[0](null, ar.cause());
      }
    });
      return that;
    }  else if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_bot["createClient(io.nonobot.core.client.ClientOptions,io.vertx.core.Handler)"](__args[0] != null ? new ClientOptions(new JsonObject(JSON.stringify(__args[0]))) : null, function(ar) {
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
 Create a new bot for the Vert.x instance and specified options.

 @memberof module:nonobot-js/bot
 @param vertx {Vertx} the Vert.x instance 
 @param options {Object} the options 
 @return {Bot} the created bot
 */
Bot.create = function() {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JBot["create(io.vertx.core.Vertx)"](__args[0]._jdel), Bot);
  }else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && (typeof __args[1] === 'object' && __args[1] != null)) {
    return utils.convReturnVertxGen(JBot["create(io.vertx.core.Vertx,io.nonobot.core.BotOptions)"](__args[0]._jdel, __args[1] != null ? new BotOptions(new JsonObject(JSON.stringify(__args[1]))) : null), Bot);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = Bot;