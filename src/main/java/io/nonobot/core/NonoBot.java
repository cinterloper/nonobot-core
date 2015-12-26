package io.nonobot.core;

import io.nonobot.core.adapter.Adapter;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.impl.NonoBotImpl;
import io.vertx.codegen.annotations.Fluent;
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

  String name();

  void client(Handler<AsyncResult<BotClient>> handler);

  void client(Handler<AsyncResult<BotClient>> handler, ClientOptions options);

  @Fluent
  NonoBot addAdapter(Adapter adapter);

  @Fluent
  NonoBot addAdapter(Adapter adapter, long reconnectPeriod);

  void close();
}
