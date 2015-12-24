package io.nonobot.core.adapter;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.impl.SlackAdapterImpl;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface SlackAdapter extends Adapter {

  static SlackAdapter create(NonoBot bot, SlackOptions options) {
    return new SlackAdapterImpl(bot, options);
  }
}
