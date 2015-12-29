/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nonobot.core.adapter;

import io.nonobot.core.client.BotClient;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * Expose the bot to an external (usually remote) service.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotAdapter {

  static BotAdapter create(Handler<ConnectionListener> handler) {

    class ConnectionListenerImpl implements ConnectionListener {

      final BotAdapter adapter;
      final Future<Void> fut;
      final BotClient client;

      public ConnectionListenerImpl(BotAdapter adapter, BotClient client, Future<Void> fut) {
        this.adapter = adapter;
        this.client = client;
        this.fut = fut;
      }

      @Override
      public boolean failed() {
        return fut.failed();
      }

      @Override
      public boolean succeeded() {
        return fut.succeeded();
      }

      @Override
      public Throwable cause() {
        return fut.cause();
      }

      @Override
      public Void result() {
        return null;
      }

      @Override
      public void fail(String failureMessage) {
        fut.fail(failureMessage);
      }

      @Override
      public void fail(Throwable throwable) {
        fut.fail(throwable);
      }

      @Override
      public void complete() {
        fut.complete();
      }

      @Override
      public void complete(Void result) {
        fut.complete(result);
      }

      @Override
      public void setHandler(Handler<AsyncResult<Void>> handler) {
        fut.setHandler(handler);
      }

      @Override
      public boolean isComplete() {
        return fut.isComplete();
      }

      @Override
      public BotClient client() {
        return client;
      }

    }

    return new BotAdapter() {

      @Override
      public void connect(BotClient client, Future<Void> completionFuture) {
        handler.handle(new ConnectionListenerImpl(this, client, completionFuture));
      }

      @Override
      public void close() {
        // For now nothing to do
      }
    };
  }

  /**
   * Like {@link #connect(BotClient,Future)}.
   */
  default void connect(BotClient client) {
    connect(client, Future.future());
  }

  /**
   * Connect to the adapted service.
   *
   * @param completionFuture the future to complete or fail when connection is either a success or a failure
   */
  void connect(BotClient client, Future<Void> completionFuture);

  /**
   * Close the adapter.
   */
  void close();

}
