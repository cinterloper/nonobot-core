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

import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.vertx.core.Context;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotAdapterTest extends BaseTest {

  @Test
  public void testReconnectOnClientClose(TestContext context) throws Exception {
    Async async = context.async();
    AtomicInteger count = new AtomicInteger();
    BotAdapter adapter = BotAdapter.create(vertx);
    adapter.requestHandler(conn -> {
      switch (count.getAndIncrement()) {
        case 0: {
          conn.complete();
          vertx.runOnContext(v -> {
            conn.client().close();
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
    adapter.run(new ClientOptions().setReconnectPeriod(100));
  }

  @Test
  public void testReconnectOnFail(TestContext context) throws Exception {

    Async async = context.async();
    AtomicInteger count = new AtomicInteger();
    BotAdapter adapter = BotAdapter.create(vertx);
    adapter.requestHandler(conn -> {
      switch (count.getAndIncrement()) {
        case 0: {
          vertx.runOnContext(v -> {
            conn.fail("Could not connect");
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
    adapter.run(new ClientOptions().setReconnectPeriod(100));
  }

  @Test
  public void testSendMessage(TestContext context) throws Exception {
    Async async = context.async();
    Context ctx = vertx.getOrCreateContext();
    BotAdapter adapter = BotAdapter.create(vertx);
    adapter.requestHandler(conn -> {
      BotClient client = conn.client();
      client.messageHandler(msg -> {
        context.assertEquals("the_body", msg.body());
        context.assertEquals("the_chat_id", msg.chatId());
        async.complete();
      });
      conn.complete();
      ctx.runOnContext(v -> {
        vertx.eventBus().send("bots.nono.outbound", new JsonObject().put("chatId", "the_chat_id").put("body", "the_body"));
      });
    });
    adapter.run(new ClientOptions());
  }

  @Test
  public void testClose(TestContext context) throws Exception {
    Async async = context.async();
    BotAdapter adapter = BotAdapter.create(vertx);
    adapter.requestHandler(conn -> {
      BotClient client = conn.client();
      client.closeHandler(v -> {
        context.assertFalse(adapter.isRunning());
        context.assertFalse(adapter.isConnected());
        async.complete();
      });
      conn.complete();
    });
    adapter.run(new ClientOptions());
    while (!adapter.isConnected()) {
      Thread.sleep(1);
    }
    adapter.close();
  }
}
