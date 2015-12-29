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

/** @module nonobot-js/bot_adapter */
var utils = require('vertx-js/util/utils');
var BotClient = require('nonobot-js/bot_client');
var Future = require('vertx-js/future');
var ConnectionListener = require('nonobot-js/connection_listener');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBotAdapter = io.nonobot.core.adapter.BotAdapter;

/**
 Expose the bot to an external (usually remote) service.

 @class
*/
var BotAdapter = function(j_val) {

  var j_botAdapter = j_val;
  var that = this;

  /**
   Connect to the adapted service.

   @public
   @param client {BotClient} 
   @param completionFuture {Future} the future to complete or fail when connection is either a success or a failure 
   */
  this.connect = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_botAdapter["connect(io.nonobot.core.client.BotClient)"](__args[0]._jdel);
    }  else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'object' && __args[1]._jdel) {
      j_botAdapter["connect(io.nonobot.core.client.BotClient,io.vertx.core.Future)"](__args[0]._jdel, __args[1]._jdel);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Close the adapter.

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_botAdapter["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_botAdapter;
};

/**

 @memberof module:nonobot-js/bot_adapter
 @param handler {function} 
 @return {BotAdapter}
 */
BotAdapter.create = function(handler) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'function') {
    return utils.convReturnVertxGen(JBotAdapter["create(io.vertx.core.Handler)"](function(jVal) {
    handler(utils.convReturnVertxGen(jVal, ConnectionListener));
  }), BotAdapter);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = BotAdapter;