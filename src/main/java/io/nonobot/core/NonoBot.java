package io.nonobot.core;

import io.nonobot.core.client.BotClient;
import io.nonobot.core.impl.NonoBotImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface NonoBot {

  static NonoBot create(Vertx vertx) {
    return new NonoBotImpl(vertx);
  }

  Vertx vertx();

  void client(Handler<AsyncResult<BotClient>> handler);

}
