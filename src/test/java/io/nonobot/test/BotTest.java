package io.nonobot.test;

import io.nonobot.core.NonoBot;
import io.nonobot.core.chat.ChatHandler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        pattern("^echo\\s+(.+)").
        messageHandler(msg -> {
        }));
    JsonObject handlers = (JsonObject) vertx.sharedData().getLocalMap("nonobot").get("handlers");
    context.assertNotNull(handlers);
    context.assertEquals(1, handlers.size());
    JsonObject desc = handlers.getJsonObject(handlers.fieldNames().iterator().next());
    context.assertEquals(new JsonObject().put("pattern", "^echo\\s+(.+)"), desc);
  }

  @Test
  public void testUnbindHandler(TestContext context) {
    ChatHandler handler = assertBind(context, ChatHandler.
        create(vertx).
        pattern("^echo\\s+(.+)").
        messageHandler(msg -> {
        }));
    Async async = context.async();
    handler.unbind(context.asyncAssertSuccess(v ->
        async.complete()
    ));
    async.awaitSuccess(2000);
    JsonObject handlers = (JsonObject) vertx.sharedData().getLocalMap("nonobot").get("handlers");
    context.assertEquals(new JsonObject(), handlers);
  }

  @Test
  public void testRouteMessage(TestContext context) {
    Async msgLatch = context.async();
    assertBind(context, ChatHandler.
        create(vertx).
        pattern("^echo\\s+(.+)").
        messageHandler(msg -> {
          context.assertEquals("echo hello world", msg.content());
          msgLatch.complete();
        }));
    NonoBot bot = NonoBot.create(vertx);
    bot.client(context.asyncAssertSuccess(client -> {
      client.publish("nono echo hello world", ar -> {});
    }));
  }
}