/*
 * Copyright 2015 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nonobot.core.adapter;

import io.nonobot.core.NonoBot;
import io.nonobot.core.client.BotClient;
import io.vertx.core.Context;
import io.vertx.core.Future;

import java.io.Console;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ConsoleBotAdapter implements BotAdapter {

  private Thread consoleThread;
  private final NonoBot bot;

  public ConsoleBotAdapter(NonoBot bot) {
    this.bot = bot;
  }

  @Override
  public void connect(BotClient client, Future<Void> completionFuture) {
    Context context = bot.vertx().getOrCreateContext();
    synchronized (ConsoleBotAdapter.this) {
      consoleThread = new Thread(() -> {
        context.runOnContext(v -> {
          completionFuture.complete();
        });
        try {
          run(client);
        } finally {
          client.close();
        }
      });
      consoleThread.start();
    }
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
  public synchronized void close() {
    // ?
  }
}
