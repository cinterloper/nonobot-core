package io.nonobot.core.client;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotClient {

  void publish(String message, Handler<AsyncResult<String>> handler);

  void close();

}
