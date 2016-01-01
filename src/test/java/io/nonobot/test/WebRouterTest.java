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

package io.nonobot.test;

import io.nonobot.core.Bot;
import io.nonobot.core.BotOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class WebRouterTest extends BaseTest {

  @Test
  public void testUseRouter(TestContext context) {
    Async serverLatch = context.async();
    Bot bot = Bot.createShared(vertx, new BotOptions().
        setHttpServerOptions(new HttpServerOptions().setPort(8080).setHost("localhost")),
        context.asyncAssertSuccess(v -> {
          serverLatch.complete();
        }));
    serverLatch.awaitSuccess(2000);
    bot.webRouter().get("/myhandler").handler(req -> {
      req.response().end("okay");
    });
    HttpClient client = vertx.createHttpClient();
    Async doneLatch = context.async();
    client.getNow(8080, "localhost", "/myhandler", resp -> {
      resp.handler(buf -> {
        context.assertEquals("okay", buf.toString());
        doneLatch.complete();
      });
    });
  }
}
