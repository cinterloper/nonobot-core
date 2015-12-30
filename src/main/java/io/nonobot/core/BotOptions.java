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

package io.nonobot.core;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class BotOptions {

  public static final long DEFAULT_RECONNECT_PERIOD = 3000;

  private long reconnectPeriod;

  public BotOptions() {
    this.reconnectPeriod = DEFAULT_RECONNECT_PERIOD;
  }

  public BotOptions(JsonObject json) {
    throw new UnsupportedOperationException();
  }

  public BotOptions(BotOptions that) {
    this.reconnectPeriod = that.reconnectPeriod;
  }

  public long getReconnectPeriod() {
    return reconnectPeriod;
  }

  public BotOptions setReconnectPeriod(long reconnectPeriod) {
    this.reconnectPeriod = reconnectPeriod;
    return this;
  }
}
