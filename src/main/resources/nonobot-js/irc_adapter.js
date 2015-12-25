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

/** @module nonobot-js/irc_adapter */
var utils = require('vertx-js/util/utils');
var Adapter = require('nonobot-js/adapter');
var NonoBot = require('nonobot-js/nono_bot');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JIrcAdapter = io.nonobot.core.adapter.IrcAdapter;
var IrcOptions = io.nonobot.core.adapter.IrcOptions;

/**

 @class
*/
var IrcAdapter = function(j_val) {

  var j_ircAdapter = j_val;
  var that = this;
  Adapter.call(this, j_val);

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_ircAdapter;
};

/**

 @memberof module:nonobot-js/irc_adapter
 @param bot {NonoBot} 
 @param options {Object} 
 @return {IrcAdapter}
 */
IrcAdapter.create = function(bot, options) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && (typeof __args[1] === 'object' && __args[1] != null)) {
    return utils.convReturnVertxGen(JIrcAdapter["create(io.nonobot.core.NonoBot,io.nonobot.core.adapter.IrcOptions)"](bot._jdel, options != null ? new IrcOptions(new JsonObject(JSON.stringify(options))) : null), IrcAdapter);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = IrcAdapter;