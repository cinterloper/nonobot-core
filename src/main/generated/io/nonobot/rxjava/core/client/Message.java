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

/**
 * A message to send.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.client.Message original} non RX-ified interface using Vert.x codegen.
 */

public class Message {

  final io.nonobot.core.client.Message delegate;

  public Message(io.nonobot.core.client.Message delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * @return the chat id where the message should be posted
   * @return 
   */
  public String chatId() { 
    String ret = this.delegate.chatId();
    return ret;
  }

  /**
   * @return the message body
   * @return 
   */
  public String body() { 
    String ret = this.delegate.body();
    return ret;
  }


  public static Message newInstance(io.nonobot.core.client.Message arg) {
    return arg != null ? new Message(arg) : null;
  }
}
