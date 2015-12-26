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

package io.nonobot.core.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ClientOptions {

  private static final long DEFAULT_TIMEOUT = 10000;

  private long timeout;

  public ClientOptions() {
    timeout = DEFAULT_TIMEOUT;
  }

  public ClientOptions(JsonObject json) {
    throw new UnsupportedOperationException("todo");
  }

  public ClientOptions(ClientOptions that) {
    timeout = that.timeout;
  }

  public long getTimeout() {
    return timeout;
  }

  public ClientOptions setTimeout(long timeout) {
    this.timeout = timeout;
    return this;
  }
}
