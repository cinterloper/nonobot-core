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

package io.nonobot.rxjava.core;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;

/**
 * Configuration object.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.Config original} non RX-ified interface using Vert.x codegen.
 */

public class Config {

  final io.nonobot.core.Config delegate;

  public Config(io.nonobot.core.Config delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Returns a config property given a <code>name</code>.
   * @param name the property name
   * @return the property value
   */
  public String getProperty(String name) { 
    String ret = this.delegate.getProperty(name);
    return ret;
  }


  public static Config newInstance(io.nonobot.core.Config arg) {
    return arg != null ? new Config(arg) : null;
  }
}
