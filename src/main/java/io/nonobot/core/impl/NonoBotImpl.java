package io.nonobot.core.impl;

import io.nonobot.core.NonoBot;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.impl.BotClientImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NonoBotImpl implements NonoBot {

  final String name = "nono"; // Bot name : make this configurable via options
  final Vertx vertx;

  public NonoBotImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  public String name() {
    return name;
  }

  @Override
  public void client(Handler<AsyncResult<BotClient>> handler) {
    handler.handle(Future.succeededFuture(new BotClientImpl(vertx, name)));
  }
}
