package io.nonobot.core.message;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Message {

  String body();

  void reply(String msg);

}
