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

package io.nonobot.rxjava.core.chat;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.chat.ChatRouter original} non RX-ified interface using Vert.x codegen.
 */

public class ChatRouter {

  final io.nonobot.core.chat.ChatRouter delegate;

  public ChatRouter(io.nonobot.core.chat.ChatRouter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static ChatRouter create(Vertx vertx) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.chat.ChatRouter.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  public static ChatRouter create(Vertx vertx, Handler<AsyncResult<Void>> completionHandler) { 
    ChatRouter ret= ChatRouter.newInstance(io.nonobot.core.chat.ChatRouter.create((io.vertx.core.Vertx) vertx.getDelegate(), completionHandler));
    return ret;
  }

  public void close() { 
    this.delegate.close();
  }

  public ChatHandler handler() { 
    ChatHandler ret= ChatHandler.newInstance(this.delegate.handler());
    return ret;
  }


  public static ChatRouter newInstance(io.nonobot.core.chat.ChatRouter arg) {
    return arg != null ? new ChatRouter(arg) : null;
  }
}
