package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.chat.ChatHandler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotTest extends BaseTest {

  private ChatHandler assertBind(TestContext context, ChatHandler handler) {
    Async async = context.async();
    handler.bind(context.asyncAssertSuccess(v ->
        async.complete()
    ));
    async.awaitSuccess(2000);
    return handler;
  }

  @Test
  public void testBindHandler(TestContext context) {
    assertBind(context, ChatHandler.
        create(vertx).
        respond("^echo\\s+(.+)", msg -> {}));
    JsonObject handlers = (JsonObject) vertx.sharedData().getLocalMap("nonobot").get("handlers");
    context.assertNotNull(handlers);
    context.assertEquals(1, handlers.size());
    JsonObject desc = handlers.getJsonObject(handlers.fieldNames().iterator().next());
    context.assertEquals(new JsonObject().put("matchers", new JsonArray().add(new JsonObject().put("pattern", "^echo\\s+(.+)").put("respond", true))), desc);
  }

  @Test
  public void testUnbindHandler(TestContext context) {
    ChatHandler handler = assertBind(context, ChatHandler.
        create(vertx).
        respond("^echo\\s+(.+)", msg -> {}));
    Async async = context.async();
    handler.unbind(context.asyncAssertSuccess(v ->
        async.complete()
    ));
    async.awaitSuccess(2000);
    JsonObject handlers = (JsonObject) vertx.sharedData().getLocalMap("nonobot").get("handlers");
    context.assertEquals(new JsonObject(), handlers);
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
    Async msgLatch = context.async();
    assertBind(context, ChatHandler.
        create(vertx).
        respond("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.content());
          msgLatch.complete();
        }));
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.process(message, ar -> {});
    }));
  }

  @Test
  public void testMatchMessage(TestContext context) {
    Async msgLatch = context.async();
    assertBind(context, ChatHandler.
        create(vertx).
        match("^echo\\s+(.+)", msg -> {
          context.assertEquals("echo hello world", msg.content());
          msgLatch.complete();
        }));
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.process("echo hello world", ar -> {});
    }));
  }
}
