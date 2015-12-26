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
   @param completionHandler {function} the handler when connection is either a success or a failure 
   */
  this.connect = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_botAdapter["connect()"]();
    }  else if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botAdapter["connect(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        __args[0](null, null);
      } else {
        __args[0](null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Handler notified when the adapter close.

   @public
   @param handler {function} 
   */
  this.closeHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botAdapter["closeHandler(io.vertx.core.Handler)"](handler);
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

// We export the Constructor function
module.exports = BotAdapter;