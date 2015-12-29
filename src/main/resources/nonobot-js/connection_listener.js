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

/** @module nonobot-js/connection_listener */
var utils = require('vertx-js/util/utils');
var BotClient = require('nonobot-js/bot_client');
var Future = require('vertx-js/future');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JConnectionListener = io.nonobot.core.adapter.ConnectionListener;

/**

 @class
*/
var ConnectionListener = function(j_val) {

  var j_connectionListener = j_val;
  var that = this;
  Future.call(this, j_val);

  /**

   @public

   @return {BotClient}
   */
  this.client = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedclient == null) {
        that.cachedclient = utils.convReturnVertxGen(j_connectionListener["client()"](), BotClient);
      }
      return that.cachedclient;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_connectionListener;
};

// We export the Constructor function
module.exports = ConnectionListener;