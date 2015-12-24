package io.nonobot.core.adapter;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Adapter {

  /**
   * Connect to the adapted service.
   */
  void connect();

  /**
   * Connect to the adapted service.
   */
  void connect(Handler<AsyncResult<Void>> completionHandler);

  /**
   * Handler notified when the adapter close.
   */
  void closeHandler(Handler<Void> handler);

  /**
   * Close.
   */
  void close();

}
