package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.chat.ChatRouter;
import io.nonobot.core.client.ClientOptions;
import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DispatchTest extends BaseTest {

  ChatRouter router;

  @Override
  public void before() {
    super.before();
    router = ChatRouter.create(vertx, ar -> {});
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
    router.handler().respond("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.content());
          handleLatch.complete();
        }).create();
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.process(message, ar -> {});
    }));
  }

  @Test
  public void testMatchMessage(TestContext context) {
    Async handleLatch = context.async();
    router.handler().match("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.content());
          handleLatch.complete();
        }).create();
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.process("echo hello world", ar -> {});
    }));
  }

  @Test
  public void testTimeout(TestContext context) {
    Async failureLatch = context.async();
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.process("echo hello world", ar -> {
        context.assertTrue(ar.failed());
        failureLatch.complete();
      });
    }), new ClientOptions().setTimeout(300));
  }

  @Test
  public void testHandlerOrder(TestContext context) {
    Async doneLatch = context.async();
    NonoBot bot = NonoBot.create(vertx);
    router.handler().match("foobar", msg -> {
      msg.reply("1");
      doneLatch.countDown();
    }).create();
    router.handler().match("foobar", msg -> {
      context.fail();
    }).create();
    bot.client(context.asyncAssertSuccess(client -> {
      client.process("foobar", ar -> {
      });
    }));
  }

  @Test
  public void testDoubleReply(TestContext context) {
    Async doneLatch = context.async(2);
    NonoBot bot = NonoBot.create(vertx);
    Future<Void> replied = Future.future();
    router.handler().match("foobar", msg -> {
      msg.reply("1");
    }).create();
    ChatRouter.create(vertx, ar -> {}).handler().match("foobar", msg -> {
      replied.setHandler(v -> {
        msg.reply("2");
        doneLatch.countDown();
      });
    }).create();
    bot.client(context.asyncAssertSuccess(client -> {
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
    router.handler().respond("^echo\\s+(.+)", msg -> {
      context.assertEquals("echo hello world", msg.content());
      msg.reply("the_reply");
    }).create();
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
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
