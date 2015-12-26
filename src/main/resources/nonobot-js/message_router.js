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

/** @module nonobot-js/message_router */
var utils = require('vertx-js/util/utils');
var MessageHandler = require('nonobot-js/message_handler');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JMessageRouter = io.nonobot.core.message.MessageRouter;

/**

 @class
*/
var MessageRouter = function(j_val) {

  var j_messageRouter = j_val;
  var that = this;

  /**

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_messageRouter["close()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {MessageHandler}
   */
  this.handler = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnVertxGen(j_messageRouter["handler()"](), MessageHandler);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_messageRouter;
};

/**

 @memberof module:nonobot-js/message_router
 @param vertx {Vertx} 
 @param completionHandler {function} 
 @return {MessageRouter}
 */
MessageRouter.create = function() {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JMessageRouter["create(io.vertx.core.Vertx)"](__args[0]._jdel), MessageRouter);
  }else if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'function') {
    return utils.convReturnVertxGen(JMessageRouter["create(io.vertx.core.Vertx,io.vertx.core.Handler)"](__args[0]._jdel, function(ar) {
    if (ar.succeeded()) {
      __args[1](null, null);
    } else {
      __args[1](null, ar.cause());
    }
  }), MessageRouter);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = MessageRouter;