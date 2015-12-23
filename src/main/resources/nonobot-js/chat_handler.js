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

/** @module nonobot-js/chat_handler */
var utils = require('vertx-js/util/utils');
var ChatMessage = require('nonobot-js/chat_message');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JChatHandler = io.nonobot.core.chat.ChatHandler;

/**

 @class
*/
var ChatHandler = function(j_val) {

  var j_chatHandler = j_val;
  var that = this;

  /**

   @public
   @param pattern {string} 
   @param handler {function} 
   @return {ChatHandler}
   */
  this.match = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_chatHandler["match(java.lang.String,io.vertx.core.Handler)"](pattern, function(jVal) {
      handler(utils.convReturnVertxGen(jVal, ChatMessage));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param pattern {string} 
   @param handler {function} 
   @return {ChatHandler}
   */
  this.respond = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_chatHandler["respond(java.lang.String,io.vertx.core.Handler)"](pattern, function(jVal) {
      handler(utils.convReturnVertxGen(jVal, ChatMessage));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param completionHandler {function} 
   */
  this.bind = function(completionHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_chatHandler["bind(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        completionHandler(null, null);
      } else {
        completionHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param completionHandler {function} 
   */
  this.unbind = function(completionHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_chatHandler["unbind(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        completionHandler(null, null);
      } else {
        completionHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_chatHandler;
};

/**

 @memberof module:nonobot-js/chat_handler
 @param vertx {Vertx} 
 @return {ChatHandler}
 */
ChatHandler.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JChatHandler["create(io.vertx.core.Vertx)"](vertx._jdel), ChatHandler);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = ChatHandler;