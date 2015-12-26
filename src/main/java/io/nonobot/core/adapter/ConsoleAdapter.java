package io.nonobot.core.adapter;

import io.nonobot.core.NonoBot;
import io.nonobot.core.client.BotClient;
import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.io.Console;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ConsoleAdapter implements Adapter {

  private Thread consoleThread;
  private Handler<Void> closeHandler;
  private final NonoBot bot;

  public ConsoleAdapter(NonoBot bot) {
    this.bot = bot;
  }

  @Override
  public void connect() {
  }

  @Override
  public void connect(Handler<AsyncResult<Void>> completionHandler) {
    Context context = bot.vertx().getOrCreateContext();
    bot.client(ar -> {
      if (ar.succeeded()) {
        synchronized (ConsoleAdapter.this) {
          consoleThread = new Thread(() -> {
            context.runOnContext(v -> {
              completionHandler.handle(Future.succeededFuture());
            });
            try {
              run(ar.result());
            } finally {
              Handler<Void> handler;
              synchronized (ConsoleAdapter.this) {
                handler = closeHandler;
              }
              if (handler != null) {
                context.runOnContext(handler);
              }
            }
          });
          consoleThread.start();
        }
      } else {
        completionHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  void run(BotClient client) {
    Console console = System.console();
    PrintWriter writer = console.writer();
    while (true) {
      writer.write("\n" + client.bot().name() + "> ");
      writer.flush();
      String line = console.readLine();
      if (line == null) {
        return;
      }
      client.process(line, ar -> {
        if (ar.succeeded()) {
          System.out.println(ar.result());
          System.out.print("\n" + client.bot().name() + "> ");
        }
      });
    }
  }

  @Override
  public synchronized void closeHandler(Handler<Void> handler) {
    closeHandler = handler;
  }

  @Override
  public synchronized void close() {
    // ?
  }
}
