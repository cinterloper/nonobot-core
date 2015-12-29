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

package io.nonobot.core.message;

import io.nonobot.core.identity.Identity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class SendOptions {

  private Identity target;

  public SendOptions() {
  }

  public SendOptions(JsonObject json) {
    throw new UnsupportedOperationException("todo");
  }

  public SendOptions(SendOptions that) {
    target = that.target != null ? new Identity(that.target) : null;
  }

  public Identity getTarget() {
    return target;
  }

  public SendOptions setTarget(Identity target) {
    this.target = target;
    return this;
  }
}
