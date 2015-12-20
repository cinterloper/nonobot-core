package io.nonobot.core.adapter;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.impl.SlackAdapterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface SlackAdapter {

  static SlackAdapter create(NonoBot bot, SlackOptions options) {
    return new SlackAdapterImpl(bot, options);
  }

  /**
   * Connect to the slack service.
   */
  void connect();

  /**
   * Connect to the slack service.
   */
  void connect(Handler<AsyncResult<Void>> completionHandler);

  void closeHandler(Handler<Void> handler);

  void close();

}
