package io.nonobot.core.adapter.impl;

import io.nonobot.core.client.BotClient;
import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.ShellAdapter;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.term.Term;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ShellAdapterImpl implements ShellAdapter {

  final NonoBot vertibot;

  public ShellAdapterImpl(Vertx vertx) {
    this.vertibot = NonoBot.create(vertx);
  }

  @Override
  public void handle(Term term) {
    vertibot.client(ar -> {
      if (ar.succeeded()) {
        BotClient client = ar.result();
        term.closeHandler(v -> {
          client.close();
        });
        handle(term, client);
      } else {
        term.close();
      }
    });
  }

  void handle(Term term, BotClient client) {
    term.readline("> ", line -> {
      if (line == null) {
        term.close();
      } else {
        term.write("\n");
        client.publish(line, reply -> {
          if (reply.succeeded()) {
            term.write(reply.result() + "\n");
          } else {
            term.write(reply.cause().getMessage() + "\n");
          }
          handle(term, client);
        });
      }
    });
  }
}
