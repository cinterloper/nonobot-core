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

package io.nonobot.rxjava.core.client;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import java.util.List;
import io.nonobot.rxjava.core.NonoBot;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * The bot client provides a customized client interface for interacting with the bot.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.client.BotClient original} non RX-ified interface using Vert.x codegen.
 */

public class BotClient {

  final io.nonobot.core.client.BotClient delegate;

  public BotClient(io.nonobot.core.client.BotClient delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * @return the bot this client exposes.
   * @return 
   */
  public NonoBot bot() { 
    NonoBot ret= NonoBot.newInstance(this.delegate.bot());
    return ret;
  }

  /**
   * Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param name the bot name
   */
  public void rename(String name) { 
    this.delegate.rename(name);
  }

  /**
   * Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
   * detect if the message is addressed to the bot or not.
   * @param names the bot names
   */
  public void rename(List<String> names) { 
    this.delegate.rename(names);
  }

  /**
   * Process a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   * if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   * should not wait until the reply is called, instead if should just forward the reply content when it arrives.
   * @param message the message content to process
   * @param replyHandler the handle to be notified with the message reply
   */
  public void process(String message, Handler<AsyncResult<String>> replyHandler) { 
    this.delegate.process(message, replyHandler);
  }

  /**
   * Process a message, the message might trigger a reply from an handler, if that happens it should be fast. However
   * if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
   * should not wait until the reply is called, instead if should just forward the reply content when it arrives.
   * @param message the message content to process
   * @return 
   */
  public Observable<String> processObservable(String message) { 
    io.vertx.rx.java.ObservableFuture<String> replyHandler = io.vertx.rx.java.RxHelper.observableFuture();
    process(message, replyHandler.toHandler());
    return replyHandler;
  }

  /**
   * Close the client.
   */
  public void close() { 
    this.delegate.close();
  }


  public static BotClient newInstance(io.nonobot.core.client.BotClient arg) {
    return arg != null ? new BotClient(arg) : null;
  }
}
