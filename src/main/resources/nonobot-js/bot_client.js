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
var Bot = require('nonobot-js/bot');
var Message = require('nonobot-js/message');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JBotClient = io.nonobot.core.client.BotClient;
var ReceiveOptions = io.nonobot.core.client.ReceiveOptions;

/**
 The bot client provides a customized client interface for interacting with the bot.

 @class
*/
var BotClient = function(j_val) {

  var j_botClient = j_val;
  var that = this;

  /**
   @return the bot this client exposes.

   @public

   @return {Bot}
   */
  this.bot = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnVertxGen(j_botClient["bot()"](), Bot);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
   detect if the message is addressed to the bot or not.

   @public
   @param names {Array.<string>} the bot names 
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
   Receive a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   should not wait until the reply is called, instead if should just forward the reply content when it arrives.

   @public
   @param options {Object} the receive options 
   @param message {string} the message content to process 
   @param replyHandler {function} the handle to be notified with the message reply 
   */
  this.receiveMessage = function(options, message, replyHandler) {
    var __args = arguments;
    if (__args.length === 3 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'string' && typeof __args[2] === 'function') {
      j_botClient["receiveMessage(io.nonobot.core.client.ReceiveOptions,java.lang.String,io.vertx.core.Handler)"](options != null ? new ReceiveOptions(new JsonObject(JSON.stringify(options))) : null, message, function(ar) {
      if (ar.succeeded()) {
        replyHandler(ar.result(), null);
      } else {
        replyHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Set a message handler on this client.

   @public
   @param handler {function} the message handler 
   @return {BotClient} this object so it can be used fluently
   */
  this.messageHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botClient["messageHandler(io.vertx.core.Handler)"](function(jVal) {
      handler(utils.convReturnVertxGen(jVal, Message));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Set an handler called when the client is closed, note that calling {@link BotClient#close} will not call this handler.

   @public
   @param handler {function} the handler 
   @return {BotClient} this object so it can be used fluently
   */
  this.closeHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_botClient["closeHandler(io.vertx.core.Handler)"](handler);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Close the client.

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_botClient["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_botClient;
};

// We export the Constructor function
module.exports = BotClient;