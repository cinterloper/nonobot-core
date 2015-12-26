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

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Expose the bot to an external (usually remote) service.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotAdapter {

  /**
   * Like {@link #connect(Handler)}.
   */
  void connect();

  /**
   * Connect to the adapted service.
   *
   * @param completionHandler the handler when connection is either a success or a failure
   */
  void connect(Handler<AsyncResult<Void>> completionHandler);

  /**
   * Handler notified when the adapter close.
   */
  void closeHandler(Handler<Void> handler);

  /**
   * Close the adapter.
   */
  void close();

}
