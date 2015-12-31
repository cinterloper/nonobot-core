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

package io.nonobot.core.impl;

import io.nonobot.core.Bot;
import io.nonobot.core.handler.ChatRouter;
import io.nonobot.core.handler.impl.ChatRouterImpl;
import io.vertx.core.Vertx;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotImpl implements Bot {

  static final class Key {

    final Vertx vertx;
    final String name;

    public Key(Vertx vertx, String name) {
      this.vertx = vertx;
      this.name = name;
    }

    public boolean equals(Object that) {
      return ((Key) that).vertx == vertx && ((Key) that).name.equals(name);
    }

    @Override
    public int hashCode() {
      return vertx.hashCode() ^ name.hashCode();
    }
  }

  static final ConcurrentMap<Key, BotImpl> routers = new ConcurrentHashMap<>();

  public static Bot getShared(Vertx vertx, String name) {
    return routers.computeIfAbsent(new Key(vertx, name), key -> new BotImpl(vertx, name));
  }

  final String name;
  final Vertx vertx;
  private boolean closed;
  private ChatRouterImpl chatRouter;

  public BotImpl(Vertx vertx, String name) {
    this.vertx = vertx;
    this.name = name;
    this.chatRouter = new ChatRouterImpl(vertx, name);
  }

  @Override
  public ChatRouter chatRouter() {
    return chatRouter;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  public String name() {
    return name;
  }

  @Override
  public void close() {
    synchronized (this) {
      if (!closed) {
        closed = true;
      } else {
        return;
      }
    }
  }
}
