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
var Adapter = require('nonobot-js/adapter');
var Vertx = require('vertx-js/vertx');
var BotClient = require('nonobot-js/bot_client');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JNonoBot = io.nonobot.core.NonoBot;

/**

 @class
*/
var NonoBot = function(j_val) {

  var j_nonoBot = j_val;
  var that = this;

  /**

   @public

   @return {Vertx}
   */
  this.vertx = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnVertxGen(j_nonoBot["vertx()"](), Vertx);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   */
  this.client = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_nonoBot["client(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnVertxGen(ar.result(), BotClient), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param adapter {Adapter} 
   @param reconnectPeriod {number} 
   @return {NonoBot}
   */
  this.addAdapter = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_nonoBot["addAdapter(io.nonobot.core.adapter.Adapter)"](__args[0]._jdel);
      return that;
    }  else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] ==='number') {
      j_nonoBot["addAdapter(io.nonobot.core.adapter.Adapter,long)"](__args[0]._jdel, __args[1]);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

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

 @memberof module:nonobot-js/nono_bot
 @param vertx {Vertx} 
 @return {NonoBot}
 */
NonoBot.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JNonoBot["create(io.vertx.core.Vertx)"](vertx._jdel), NonoBot);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = NonoBot;