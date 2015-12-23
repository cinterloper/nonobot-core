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

/** @module nonobot-js/giphy_handler */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');
var ChatHandler = require('nonobot-js/chat_handler');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JGiphyHandler = io.nonobot.core.handlers.GiphyHandler;

/**

 @class
*/
var GiphyHandler = function(j_val) {

  var j_giphyHandler = j_val;
  var that = this;

  /**

   @public
   @param vertx {Vertx} 
   @return {ChatHandler}
   */
  this.toChatHandler = function(vertx) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      return utils.convReturnVertxGen(j_giphyHandler["toChatHandler(io.vertx.core.Vertx)"](vertx._jdel), ChatHandler);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_giphyHandler;
};

/**

 @memberof module:nonobot-js/giphy_handler

 @return {GiphyHandler}
 */
GiphyHandler.create = function() {
  var __args = arguments;
  if (__args.length === 0) {
    return utils.convReturnVertxGen(JGiphyHandler["create()"](), GiphyHandler);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = GiphyHandler;