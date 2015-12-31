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

/** @module nonobot-js/chat_router */
var utils = require('vertx-js/util/utils');
var ChatHandler = require('nonobot-js/chat_handler');
var Message = require('nonobot-js/message');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JChatRouter = io.nonobot.core.handler.ChatRouter;
var SendOptions = io.nonobot.core.handler.SendOptions;

/**
 The message router.

 @class
*/
var ChatRouter = function(j_val) {

  var j_chatRouter = j_val;
  var that = this;

  /**
   Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.

   @public
   @param pattern {string} the matching pattern 
   @param handler {function} the message handler 
   @return {ChatHandler} the message handler object
   */
  this.when = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      return utils.convReturnVertxGen(j_chatRouter["when(java.lang.String,io.vertx.core.Handler)"](pattern, function(jVal) {
      handler(utils.convReturnVertxGen(jVal, Message));
    }), ChatHandler);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Add a message handler triggered when the <code>pattern</code> prepended with the bot name is fully matched,
   the pattern is a <code>java.util.regex</code>.

   @public
   @param pattern {string} the matching pattern 
   @param handler {function} the message handler 
   @return {ChatHandler} the message handler object
   */
  this.respond = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      return utils.convReturnVertxGen(j_chatRouter["respond(java.lang.String,io.vertx.core.Handler)"](pattern, function(jVal) {
      handler(utils.convReturnVertxGen(jVal, Message));
    }), ChatHandler);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Send a message to a target.

   @public
   @param options {Object} the options 
   @param body {string} the message body 
   @return {ChatRouter} this object so it can be used fluently
   */
  this.sendMessage = function(options, body) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'string') {
      j_chatRouter["sendMessage(io.nonobot.core.handler.SendOptions,java.lang.String)"](options != null ? new SendOptions(new JsonObject(JSON.stringify(options))) : null, body);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Close the message router.

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_chatRouter["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_chatRouter;
};

// We export the Constructor function
module.exports = ChatRouter;