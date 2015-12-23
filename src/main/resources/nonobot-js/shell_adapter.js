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

/** @module nonobot-js/shell_adapter */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');
var Term = require('vertx-shell-js/term');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JShellAdapter = io.nonobot.core.adapter.ShellAdapter;

/**

 @class
*/
var ShellAdapter = function(j_val) {

  var j_shellAdapter = j_val;
  var that = this;

  /**

   @public
   @param arg0 {Term} 
   */
  this.handle = function(arg0) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
      j_shellAdapter["handle(io.vertx.ext.shell.term.Term)"](arg0._jdel);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_shellAdapter;
};

/**

 @memberof module:nonobot-js/shell_adapter
 @param vertx {Vertx} 
 @return {ShellAdapter}
 */
ShellAdapter.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JShellAdapter["create(io.vertx.core.Vertx)"](vertx._jdel), ShellAdapter);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = ShellAdapter;