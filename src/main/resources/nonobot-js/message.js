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

/** @module nonobot-js/message */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JMessage = io.nonobot.core.message.Message;
var Identity = io.nonobot.core.identity.Identity;

/**
 A message sent to an handler.

 @class
*/
var Message = function(j_val) {

  var j_message = j_val;
  var that = this;

  /**

   @public

   @return {Object}
   */
  this.room = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnDataObject(j_message["room()"]());
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {Object}
   */
  this.user = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnDataObject(j_message["user()"]());
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   @return the message body

   @public

   @return {string}
   */
  this.body = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_message["body()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Reply to the message with an acknowledgement handler given a <code>timeout</code>.

   @public
   @param msg {string} the reply 
   @param ackTimeout {number} the acknowledgement timeout 
   @param ackHandler {function} handler to be notified if the reply is consumed effectively 
   */
  this.reply = function() {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      j_message["reply(java.lang.String)"](__args[0]);
    }  else if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_message["reply(java.lang.String,io.vertx.core.Handler)"](__args[0], function(ar) {
      if (ar.succeeded()) {
        __args[1](null, null);
      } else {
        __args[1](null, ar.cause());
      }
    });
    }  else if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] ==='number' && typeof __args[2] === 'function') {
      j_message["reply(java.lang.String,long,io.vertx.core.Handler)"](__args[0], __args[1], function(ar) {
      if (ar.succeeded()) {
        __args[2](null, null);
      } else {
        __args[2](null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_message;
};

// We export the Constructor function
module.exports = Message;