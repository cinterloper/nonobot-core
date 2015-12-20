package io.nonobot.core.chat;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ChatMessage {

  String content();

  void reply(String msg);

}
