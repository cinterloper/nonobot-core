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

package io.nonobot.groovy.core.message;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class MessageHandler {
  private final def io.nonobot.core.message.MessageHandler delegate;
  public MessageHandler(Object delegate) {
    this.delegate = (io.nonobot.core.message.MessageHandler) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public MessageHandler when(String pattern, Handler<Message> handler) {
    this.delegate.when(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new io.nonobot.groovy.core.message.Message(event));
      }
    });
    return this;
  }
  public MessageHandler respond(String pattern, Handler<Message> handler) {
    this.delegate.respond(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new io.nonobot.groovy.core.message.Message(event));
      }
    });
    return this;
  }
  public void create() {
    this.delegate.create();
  }
}
