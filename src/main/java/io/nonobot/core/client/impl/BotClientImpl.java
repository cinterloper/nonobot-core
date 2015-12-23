package io.nonobot.core.client.impl;

import io.nonobot.core.client.BotClient;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class BotClientImpl implements BotClient {

  final Vertx vertx;
  final String botName;
  final Pattern botPattern;

  public BotClientImpl(Vertx vertx, String botName) {
    this.vertx = vertx;
    this.botName = botName;
    this.botPattern = Pattern.compile("^@?" + Pattern.quote(botName) + "(?:\\s|:)\\s*");
  }

  @Override
  public String name() {
    return botName;
  }

  @Override
  public void process(String message, Handler<AsyncResult<String>> handler) {
    Matcher botMatcher = botPattern.matcher(message);
    boolean respond;
    if (botMatcher.find()) {
      respond = true;
      message = message.substring(botMatcher.end());
    } else {
      respond = false;
    }
    LocalMap<String, Object> shared = vertx.sharedData().getLocalMap("nonobot");
    JsonObject handlers = (JsonObject) shared.get("handlers");
    if (handlers != null) {
      for (Map.Entry<String, Object> handlerDesc : handlers) {
        JsonObject desc = (JsonObject) handlerDesc.getValue();
        JsonArray matchers = desc.getJsonArray("matchers");
        for (int i = 0;i < matchers.size();i++) {
          JsonObject matcherDesc = matchers.getJsonObject(i);
          String matcherPattern = matcherDesc.getString("pattern");
          boolean matcherRespond = matcherDesc.getBoolean("respond");
          if (matcherRespond == respond) {
            Matcher matcher = Pattern.compile(matcherPattern).matcher(message);
            if (matcher.matches()) {
              vertx.eventBus().send(handlerDesc.getKey(), message, new DeliveryOptions().setSendTimeout(10000), reply -> {
                if (reply.succeeded()) {
                  handler.handle(Future.succeededFuture("" + reply.result().body()));
                } else {
                  handler.handle(Future.failedFuture(reply.cause()));
                }
              });
              return;
            }
          }
        }
      }
    }
    handler.handle(Future.failedFuture("Incorrect message"));
  }

  @Override
  public void close() {
  }
}
