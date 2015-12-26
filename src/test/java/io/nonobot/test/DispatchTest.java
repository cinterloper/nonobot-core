package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.chat.ChatRouter;
import io.nonobot.core.client.ClientOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

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
}
