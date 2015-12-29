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

package io.nonobot.core.identity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class Identity {

  private String id;
  private String name;

  public Identity() {
  }

  public Identity(JsonObject json) {
    this.id = json.getString("id");
    this.name = json.getString("name");
  }

  public Identity(Identity that) {
    id = that.id;
    name = that.name;
  }

  public String getId() {
    return id;
  }

  public Identity setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Identity setName(String name) {
    this.name = name;
    return this;
  }

  public JsonObject toJson() {
    return new JsonObject().put("id", id).put("name", name);
  }
}
