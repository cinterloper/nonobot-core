package io.nonobot.core.message;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MessageHandler {

  @Fluent
  MessageHandler when(String pattern, Handler<Message> handler);

  @Fluent
  MessageHandler respond(String pattern, Handler<Message> handler);

  void create();

}
