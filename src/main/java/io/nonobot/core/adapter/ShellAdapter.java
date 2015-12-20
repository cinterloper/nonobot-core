package io.nonobot.core.adapter;

import io.nonobot.core.adapter.impl.ShellAdapterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.term.Term;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ShellAdapter extends Handler<Term> {

  static ShellAdapter create(Vertx vertx) {
    return new ShellAdapterImpl(vertx);
  }
}
