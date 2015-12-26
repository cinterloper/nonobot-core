package io.nonobot.core.client.impl;

import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotClientImpl implements BotClient {

  final Vertx vertx;
  final String botName;
  final Pattern botPattern;
  final ClientOptions options;

  public BotClientImpl(Vertx vertx, String botName, ClientOptions options) {
    this.vertx = vertx;
    this.botName = botName;
    this.botPattern = Pattern.compile("^@?" + Pattern.quote(botName) + "(?:\\s|:)\\s*");
    this.options = new ClientOptions(options);
  }

  @Override
  public String name() {
    return botName;
  }

  @Override
  public void process(String message, Handler<AsyncResult<String>> handler) {
    String replyAddress = UUID.randomUUID().toString();
    Future<String> reply = Future.future();
    reply.setHandler(handler);
    MessageConsumer<String> consumer = vertx.eventBus().consumer(replyAddress);
    consumer.handler(msg -> {
      String content = msg.body();
      if (content != null && !reply.isComplete()) {
        reply.complete(content);
        consumer.unregister();
      }
    });
    consumer.completionHandler(ar -> {
      if (ar.succeeded()) {
        Matcher botMatcher = botPattern.matcher(message);
        JsonObject body = new JsonObject().put("replyAddress", replyAddress);
        if (botMatcher.find()) {
          body.put("respond", true);
          body.put("content", message.substring(botMatcher.end()));
        } else {
          body.put("respond", false);
          body.put("content", message);
        }
        vertx.eventBus().publish("nonobot.broadcast", body);
        vertx.setTimer(options.getTimeout(), timerID -> {
          if (!reply.isComplete()) {
            consumer.unregister();
            reply.fail(new Exception("timeout"));
          }
        });
      } else {
        handler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public void close() {
  }
}
