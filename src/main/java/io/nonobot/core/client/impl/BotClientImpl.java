package io.nonobot.core.client.impl;

import io.nonobot.core.client.BotClient;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
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

  public BotClientImpl(Vertx vertx, String botName) {
    this.vertx = vertx;
    this.botName = botName;
  }

  @Override
  public void publish(String message, Handler<AsyncResult<String>> handler) {
    if (message.length() > (botName.length() + 1) && message.startsWith(botName) && message.charAt(botName.length()) == ' ') {
      message = message.substring(botName.length() + 1);
      LocalMap<String, Object> shared = vertx.sharedData().getLocalMap("nonobot");
      JsonObject handlers = (JsonObject) shared.get("handlers");
      if (handlers != null) {
        for (Map.Entry<String, Object> handlerDesc : handlers) {
          JsonObject desc = (JsonObject) handlerDesc.getValue();
          String pattern = desc.getString("pattern");
          Matcher matcher = Pattern.compile(pattern).matcher(message);
          if (matcher.matches()) {
            vertx.eventBus().send(handlerDesc.getKey(), message, new DeliveryOptions().setSendTimeout(3000), reply -> {
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
    handler.handle(Future.failedFuture("Incorrect message"));
  }

  @Override
  public void close() {
  }
}
