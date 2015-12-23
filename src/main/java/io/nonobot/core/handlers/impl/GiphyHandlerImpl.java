package io.nonobot.core.handlers.impl;

import io.nonobot.core.chat.ChatHandler;
import io.nonobot.core.handlers.GiphyHandler;
import io.vertx.core.Vertx;
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
public class GiphyHandlerImpl implements GiphyHandler {

  static final Pattern p = Pattern.compile("^giphy\\s+(.+)");

  @Override
  public ChatHandler toChatHandler(Vertx vertx) {
    HttpClient client = vertx.createHttpClient();
    ChatHandler handler = ChatHandler.create(vertx);
    handler.respond(p.pattern(), msg -> {
      Matcher matcher = p.matcher(msg.content());
      if (matcher.matches()) {
        String query = matcher.group(1);
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
      } else {
        msg.reply("Error: should not happen");
      }
    });
    return handler;
  }
}
