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

package io.nonobot.rxjava.core.message;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.message.MessageHandler original} non RX-ified interface using Vert.x codegen.
 */

public class MessageHandler {

  final io.nonobot.core.message.MessageHandler delegate;

  public MessageHandler(io.nonobot.core.message.MessageHandler delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public MessageHandler when(String pattern, Handler<Message> handler) { 
    this.delegate.when(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new Message(event));
      }
    });
    return this;
  }

  public MessageHandler respond(String pattern, Handler<Message> handler) { 
    this.delegate.respond(pattern, new Handler<io.nonobot.core.message.Message>() {
      public void handle(io.nonobot.core.message.Message event) {
        handler.handle(new Message(event));
      }
    });
    return this;
  }

  public void create() { 
    this.delegate.create();
  }


  public static MessageHandler newInstance(io.nonobot.core.message.MessageHandler arg) {
    return arg != null ? new MessageHandler(arg) : null;
  }
}
