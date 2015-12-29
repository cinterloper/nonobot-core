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

package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.client.BotClient;
import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class AdapterTest extends BaseTest {

  static Function<BiConsumer<BotClient, Future<Void>>, BotAdapter> factory1 = new Function<BiConsumer<BotClient, Future<Void>>, BotAdapter>() {
    @Override
    public BotAdapter apply(BiConsumer<BotClient, Future<Void>> arg) {
      return new BotAdapter() {
        @Override
        public void connect(BotClient client, Future<Void> completionFuture) {
          arg.accept(client, completionFuture);
        }
        @Override
        public void close() {
        }
      };
    }
  };

  static Function<BiConsumer<BotClient, Future<Void>>, BotAdapter> factory2 = new Function<BiConsumer<BotClient, Future<Void>>, BotAdapter>() {
    @Override
    public BotAdapter apply(BiConsumer<BotClient, Future<Void>> arg) {
      return BotAdapter.create(conn -> {
        arg.accept(conn.client(), conn);
      });
    }
  };

  @Test
  public void testReconnectOnClientClose1(TestContext context) throws Exception {
    testReconnectOnClientClose(context, factory1);
  }

  @Test
  public void testReconnectOnClientClose2(TestContext context) throws Exception {
    testReconnectOnClientClose(context, factory2);
  }

  private void testReconnectOnClientClose(TestContext context, Function<BiConsumer<BotClient, Future<Void>>, BotAdapter> adapterFactory) throws Exception {

    Async async = context.async();
    NonoBot bot = NonoBot.create(vertx);
    AtomicInteger count = new AtomicInteger();

    BotAdapter adapter = adapterFactory.apply((client, completionFuture) -> {
      switch (count.getAndIncrement()) {
        case 0: {
          completionFuture.complete();
          vertx.runOnContext(v -> {
            client.close();
          });
          break;
        }
        case 1: {
          async.complete();
          break;
        }
        default: {
          context.fail();
          break;
        }
      }
    });
    bot.registerAdapter(adapter, 100);
  }

  @Test
  public void testReconnectOnFail1(TestContext context) throws Exception {
    testReconnectOnFail(context, factory1);
  }

  @Test
  public void testReconnectOnFail2(TestContext context) throws Exception {
    testReconnectOnFail(context, factory2);
  }

  private void testReconnectOnFail(TestContext context, Function<BiConsumer<BotClient, Future<Void>>, BotAdapter> adapterFactory) throws Exception {

    Async async = context.async();
    NonoBot bot = NonoBot.create(vertx);
    AtomicInteger count = new AtomicInteger();

    BotAdapter adapter = adapterFactory.apply((client, completionFuture) -> {
      switch (count.getAndIncrement()) {
        case 0: {
          vertx.runOnContext(v -> {
            completionFuture.fail("Could not connect");
          });
          break;
        }
        case 1: {
          async.complete();
          break;
        }
        default: {
          context.fail();
          break;
        }
      }
    });
    bot.registerAdapter(adapter, 100);
  }
}
