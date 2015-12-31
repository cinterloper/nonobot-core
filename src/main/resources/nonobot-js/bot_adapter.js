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
var ConnectionRequest = require('nonobot-js/connection_request');
var Vertx = require('vertx-js/vertx');
var BotClient = require('nonobot-js/bot_client');
var Future = require('vertx-js/future');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBotAdapter = io.nonobot.core.adapter.BotAdapter;
var ClientOptions = io.nonobot.core.client.ClientOptions;

/**
 Expose the bot to an external (usually remote) service.

 @class
*/
var BotAdapter = function(j_val) {

  var j_botAdapter = j_val;
  var that = this;

  /**
   Run the bot adapter, until it is closed.

   @public
   @param options {Object} the client options to use 
   */
  this.run = function(options) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'object' && __args[0] != null)) {
      j_botAdapter["run(io.nonobot.core.client.ClientOptions)"](options != null ? new ClientOptions(new JsonObject(JSON.stringify(options))) : null);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {boolean}
   */
  this.isRunning = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_botAdapter["isRunning()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {boolean}
   */
  this.isConnected = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_botAdapter["isConnected()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Set the connection request handler.

   @public
   @param handler {function} the connection request handler 
   @return {BotAdapter} this object so it can be used fluently
   */
  this.requestHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botAdapter["requestHandler(io.vertx.core.Handler)"](function(jVal) {
      handler(utils.convReturnVertxGen(jVal, ConnectionRequest));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Connect to the adapted service.

   @public
   @param client {BotClient} the client to use 
   @param completionFuture {Future} the future to complete or fail when connection is either a success or a failure 
   @return {BotAdapter} this object so it can be used fluently
   */
  this.connect = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_botAdapter["connect(io.nonobot.core.client.BotClient)"](__args[0]._jdel);
      return that;
    }  else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'object' && __args[1]._jdel) {
      j_botAdapter["connect(io.nonobot.core.client.BotClient,io.vertx.core.Future)"](__args[0]._jdel, __args[1]._jdel);
      return that;
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
 Create new adapter.

 @memberof module:nonobot-js/bot_adapter
 @param vertx {Vertx} the vertx instance to use 
 @return {BotAdapter} the bot adapter
 */
BotAdapter.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JBotAdapter["create(io.vertx.core.Vertx)"](vertx._jdel), BotAdapter);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = BotAdapter;