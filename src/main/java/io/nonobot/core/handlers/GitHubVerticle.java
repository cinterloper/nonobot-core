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

import io.nonobot.core.chat.SendOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class GitHubVerticle extends BaseHandlerVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    Router router = Router.router(vertx);
    bot.webRouter().mountSubRouter("/github", router);
    router.post().handler(ctx -> {
      HttpServerRequest req = ctx.request();
      String event = req.getHeader("X-Github-Event");
      if (!"push".equals(event)) {
        req.response().setStatusCode(400).end("X-Github-Event " + event + " not handled");
        return;
      }
      String contentType = req.getHeader("Content-Type");
      if (!"application/json".equals(contentType)) {
        req.response().setStatusCode(400).end("Content-Type " + contentType + " not handled");
        return;
      }
      req.bodyHandler(body -> {
        JsonObject json = body.toJsonObject();
        req.response().end();
        String chatId = req.getParam("chat");
        JsonArray commits = json.getJsonArray("commits");
        if (chatId != null && commits != null && commits.size() > 0) {
          String commitWord = commits.size() > 1 ? "commits" : "commit";
          bot.chatRouter().sendMessage(new SendOptions().setChatId(chatId),
              "Got " + commits.size() + " new " + commitWord + " from " +
                  json.getJsonObject("pusher").getString("name") + " on " + json.getJsonObject("repository").getString("full_name"));
          for (int index = 0;index < commits.size();index++) {
            JsonObject commit = commits.getJsonObject(index);
            bot.chatRouter().sendMessage(new SendOptions().setChatId(chatId), "  * " + commit.getString("message") + " " + commit.getString("url"));
          }
        }
      });
    });
  }
}
