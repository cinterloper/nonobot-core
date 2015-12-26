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

package io.nonobot.rxjava.core.handlers;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.nonobot.rxjava.core.message.MessageHandler;
import io.vertx.rxjava.core.Vertx;
import io.nonobot.rxjava.core.message.MessageRouter;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.handlers.PingHandler original} non RX-ified interface using Vert.x codegen.
 */

public class PingHandler {

  final io.nonobot.core.handlers.PingHandler delegate;

  public PingHandler(io.nonobot.core.handlers.PingHandler delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static PingHandler create() { 
    PingHandler ret= PingHandler.newInstance(io.nonobot.core.handlers.PingHandler.create());
    return ret;
  }

  public MessageHandler toChatHandler(Vertx vertx, MessageRouter router) { 
    MessageHandler ret= MessageHandler.newInstance(this.delegate.toChatHandler((io.vertx.core.Vertx) vertx.getDelegate(), (io.nonobot.core.message.MessageRouter) router.getDelegate()));
    return ret;
  }


  public static PingHandler newInstance(io.nonobot.core.handlers.PingHandler arg) {
    return arg != null ? new PingHandler(arg) : null;
  }
}
