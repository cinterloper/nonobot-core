package io.nonobot.core.chat;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ChatHandler {

  @Fluent
  ChatHandler match(String pattern, Handler<ChatMessage> handler);

  @Fluent
  ChatHandler respond(String pattern, Handler<ChatMessage> handler);

  void create();

}
