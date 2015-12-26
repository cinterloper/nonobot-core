package io.nonobot.core.impl;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.Adapter;
import io.nonobot.core.client.BotClient;
import io.nonobot.core.client.ClientOptions;
import io.nonobot.core.client.impl.BotClientImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NonoBotImpl implements NonoBot {

  final String name = "nonobot"; // Bot name : make this configurable via options
  final Vertx vertx;
  private boolean closed;
  private Set<Adapter> adapters = new HashSet<>();

  public NonoBotImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  public String name() {
    return name;
  }

  @Override
  public void client(Handler<AsyncResult<BotClient>> handler) {
    client(handler, new ClientOptions());
  }

  @Override
  public void client(Handler<AsyncResult<BotClient>> handler, ClientOptions options) {
    handler.handle(Future.succeededFuture(new BotClientImpl(vertx, name, options)));
  }

  @Override
  public NonoBot addAdapter(Adapter adapter) {
    return addAdapter(adapter, 1000);
  }

  @Override
  public NonoBot addAdapter(Adapter adapter, long reconnectPeriod) {
    synchronized (this) {
      if (closed) {
        throw new IllegalStateException("Closed");
      }
    }
    adapter.closeHandler(v -> {
      synchronized (NonoBotImpl.this) {
        adapters.remove(adapter);
        reconnect(adapter, reconnectPeriod);
      }
    });
    adapter.connect(ar -> {
      if (ar.succeeded()) {
        synchronized (NonoBotImpl.this) {
          adapters.add(adapter);
          if (!closed) {
            System.out.println("Adapter started");
            return;
          }
        }
        adapter.close();
      } else {
        System.out.println("Connection failure");
        ar.cause().printStackTrace();
        reconnect(adapter, reconnectPeriod);
      }
    });
    return this;
  }

  private synchronized void reconnect(Adapter adapter, long reconnectPeriod) {
    if (closed) {
      return;
    }
    if (reconnectPeriod > 0) {
      System.out.println("Connection failure, will reconnect after " + reconnectPeriod);
      vertx.setTimer(reconnectPeriod, id -> {
        addAdapter(adapter, reconnectPeriod);
      });
    }
  }

  @Override
  public void close() {
    List<Adapter> toClose;
    synchronized (this) {
      if (!closed) {
        closed = true;
        toClose = new ArrayList<>(adapters);
      } else {
        return;
      }
    }
    for (Adapter adapter : toClose) {
      adapter.close();
    }
  }
}
