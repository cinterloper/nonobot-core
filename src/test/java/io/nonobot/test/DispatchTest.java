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
import io.nonobot.core.message.MessageRouter;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.message.impl.MessageRouterImpl;
import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DispatchTest extends BaseTest {

  MessageRouter router;

  @Override
  public void before() {
    super.before();
    router = MessageRouter.getShared(vertx, ar -> {});
  }

  @Test
  public void testRespondToMessage1(TestContext context) {
    testRespondToMessage(context, "nonobot echo hello world");
  }

  @Test
  public void testRespondToMessage2(TestContext context) {
    testRespondToMessage(context, "nonobot:echo hello world");
  }

  @Test
  public void testRespondToMessage3(TestContext context) {
    testRespondToMessage(context, "@nonobot echo hello world");
  }

  @Test
  public void testRespondToMessage4(TestContext context) {
    testRespondToMessage(context, "@nonobot:echo hello world");
  }

  private void testRespondToMessage(TestContext context, String message) {
    Async handleLatch = context.async();
    router.respond("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.body());
          handleLatch.complete();
        });
    NonoBot bot = NonoBot.create(vertx);
    bot.createClient(context.asyncAssertSuccess(client -> {
      client.process(message, ar -> {});
    }));
  }

  @Test
  public void testMatchMessage(TestContext context) {
    Async handleLatch = context.async();
    router.when("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.body());
          handleLatch.complete();
        });
    NonoBot bot = NonoBot.create(vertx);
    bot.createClient(context.asyncAssertSuccess(client -> {
      client.process("echo hello world", ar -> {});
    }));
  }

  @Test
  public void testTimeout(TestContext context) {
    Async failureLatch = context.async();
    NonoBot bot = NonoBot.create(vertx);
    bot.createClient(new ClientOptions().setProcessTimeout(300), context.asyncAssertSuccess(client -> {
      client.process("echo hello world", ar -> {
        context.assertTrue(ar.failed());
        failureLatch.complete();
      });
    }));
  }

  @Test
  public void testHandlerOrder(TestContext context) {
    Async doneLatch = context.async();
    NonoBot bot = NonoBot.create(vertx);
    router.when("foobar", msg -> {
      msg.reply("1");
      doneLatch.countDown();
    });
    router.when("foobar", msg -> {
      context.fail();
    });
    bot.createClient(context.asyncAssertSuccess(client -> {
      client.process("foobar", ar -> {
      });
    }));
  }

  @Test
  public void testConcurrentReplies(TestContext context) {
    Async doneLatch = context.async(2);
    NonoBot bot = NonoBot.create(vertx);
    Future<Void> replied = Future.future();
    router.when("foobar", msg -> {
      msg.reply("1", context.asyncAssertSuccess());
    });
    new MessageRouterImpl(vertx).when("foobar", msg -> {
      replied.setHandler(v1 -> {
        msg.reply("2", 200, context.asyncAssertFailure(v2 -> {
          doneLatch.countDown();
        }));
      });
    });
    bot.createClient(context.asyncAssertSuccess(client -> {
      client.process("foobar", ar -> {
        context.assertTrue(ar.succeeded());
        context.assertEquals("1", ar.result());
        replied.complete();
        doneLatch.countDown();
      });
    }));
  }

  @Test
  public void testOverrideBotName(TestContext context) {
    Async handleLatch = context.async(2);
    router.respond("^echo\\s+(.+)", msg -> {
      context.assertEquals("echo hello world", msg.body());
      msg.reply("the_reply");
    });
    NonoBot bot = NonoBot.create(vertx);
    bot.createClient(context.asyncAssertSuccess(client -> {
      client.rename(Arrays.asList("bb8", "r2d2"));
      client.process("bb8 echo hello world", ar -> {
        handleLatch.countDown();
      });
      client.process("r2d2 echo hello world", ar -> {
        handleLatch.countDown();
      });
    }));
  }
}
