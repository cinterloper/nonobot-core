package io.nonobot.core.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ClientOptions {

  private static final long DEFAULT_TIMEOUT = 10000;

  private long timeout;

  public ClientOptions() {
    timeout = DEFAULT_TIMEOUT;
  }

  public ClientOptions(JsonObject json) {
    throw new UnsupportedOperationException("todo");
  }

  public ClientOptions(ClientOptions that) {
    timeout = that.timeout;
  }

  public long getTimeout() {
    return timeout;
  }

  public ClientOptions setTimeout(long timeout) {
    this.timeout = timeout;
    return this;
  }
}
