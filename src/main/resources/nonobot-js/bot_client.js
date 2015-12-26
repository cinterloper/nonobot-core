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

/** @module nonobot-js/bot_client */
var utils = require('vertx-js/util/utils');
var NonoBot = require('nonobot-js/nono_bot');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBotClient = io.nonobot.core.client.BotClient;

/**

 @class
*/
var BotClient = function(j_val) {

  var j_botClient = j_val;
  var that = this;

  /**

   @public

   @return {NonoBot}
   */
  this.bot = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnVertxGen(j_botClient["bot()"](), NonoBot);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param names {Array.<string>} 
   */
  this.rename = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      j_botClient["rename(java.lang.String)"](__args[0]);
    }  else if (__args.length === 1 && typeof __args[0] === 'object' && __args[0] instanceof Array) {
      j_botClient["rename(java.util.List)"](__args[0]);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param message {string} 
   @param handler {function} 
   */
  this.process = function(message, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_botClient["process(java.lang.String,io.vertx.core.Handler)"](message, function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_botClient["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   */
  this.closeHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botClient["closeHandler(io.vertx.core.Handler)"](handler);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_botClient;
};

// We export the Constructor function
module.exports = BotClient;