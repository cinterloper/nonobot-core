package io.nonobot.core.client;

import io.nonobot.core.NonoBot;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BotClient {

  NonoBot bot();

  void rename(String name);

  void rename(List<String> names);

  void process(String message, Handler<AsyncResult<String>> handler);

  void close();

  default void closeHandler(Handler<Void> handler) { /* todo */ }

}
