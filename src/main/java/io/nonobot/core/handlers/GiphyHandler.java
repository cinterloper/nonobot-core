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

package io.nonobot.core.handlers;

import io.nonobot.core.handler.Message;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class GiphyHandler extends BaseHandlerVerticle {

  private HttpClient client;

  @Override
  public void start() throws Exception {
    super.start();
    client = vertx.createHttpClient();
    bot.chatRouter().respond("^giphy\\s+(.+)", this::handle);
  }

  public void handle(Message msg) {
    String query = msg.matchedGroup(1);
    HttpClientRequest req = client.get(80, "api.giphy.com", "/v1/gifs/search?q=" + query + "&api_key=" + "dc6zaTOxFJmzC", resp -> {
      if (resp.statusCode() == 200 && resp.getHeader("Content-Type").equals("application/json")) {
        System.out.println(resp.statusCode());
        System.out.println(resp.statusMessage());
        System.out.println(resp.getHeader("Content-Type"));
        resp.exceptionHandler(err -> {
          msg.reply("Error: " + err.getMessage());
        });
        Buffer buf = Buffer.buffer();
        resp.handler(buf::appendBuffer);
        resp.endHandler(v -> {
          JsonObject json = new JsonObject(buf.toString());
          JsonArray images = json.getJsonArray("data");
          if (images.size() > 0) {
            JsonObject image = images.getJsonObject(0); // Should be random !!!
            String url = image.getJsonObject("images").getJsonObject("original").getString("url");
            msg.reply(url);
          }
          msg.reply("got response " + json);
        });
      } else {
        msg.reply("Error");
      }
    });
    req.exceptionHandler(err -> {
      msg.reply("Error: " + err.getMessage());
    });
    req.end();
  }
}
