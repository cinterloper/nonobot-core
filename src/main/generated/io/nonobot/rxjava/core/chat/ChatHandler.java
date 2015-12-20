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
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.chat.ChatHandler original} non RX-ified interface using Vert.x codegen.
 */

public class ChatHandler {

  final io.nonobot.core.chat.ChatHandler delegate;

  public ChatHandler(io.nonobot.core.chat.ChatHandler delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static ChatHandler create(Vertx vertx) { 
    ChatHandler ret= ChatHandler.newInstance(io.nonobot.core.chat.ChatHandler.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }

  public ChatHandler pattern(String regex) { 
    this.delegate.pattern(regex);
    return this;
  }

  public ChatHandler messageHandler(Handler<ChatMessage> handler) { 
    this.delegate.messageHandler(new Handler<io.nonobot.core.chat.ChatMessage>() {
      public void handle(io.nonobot.core.chat.ChatMessage event) {
        handler.handle(new ChatMessage(event));
      }
    });
    return this;
  }

  public void bind(Handler<AsyncResult<Void>> completionHandler) { 
    this.delegate.bind(completionHandler);
  }

  public Observable<Void> bindObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> completionHandler = io.vertx.rx.java.RxHelper.observableFuture();
    bind(completionHandler.toHandler());
    return completionHandler;
  }

  public void unbind(Handler<AsyncResult<Void>> completionHandler) { 
    this.delegate.unbind(completionHandler);
  }

  public Observable<Void> unbindObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> completionHandler = io.vertx.rx.java.RxHelper.observableFuture();
    unbind(completionHandler.toHandler());
    return completionHandler;
  }


  public static ChatHandler newInstance(io.nonobot.core.chat.ChatHandler arg) {
    return arg != null ? new ChatHandler(arg) : null;
  }
}
