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

package io.nonobot.core.spi;

import io.nonobot.core.Config;
import io.nonobot.core.adapter.BotAdapter;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface BotAdapterFactory {


  /**
   * Create a new bot adapter or return null if no adapter could be created.
   *
   * @param config the bot config
   * @return the adapter
   */
  BotAdapter create(Config config);

}
