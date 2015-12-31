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

import io.nonobot.core.handler.MessageRouter;
import io.nonobot.core.handler.SendOptions;
import io.vertx.core.AbstractVerticle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TimerHandler extends BaseHandlerVerticle {

  Pattern p = Pattern.compile("^timer ([0-9]+)");

  @Override
  public void start() throws Exception {
    super.start();
    router.respond(p.pattern(), msg -> {
      Matcher matcher = p.matcher(msg.body());
      matcher.matches();
      long period = Long.parseLong(matcher.group(1));
      msg.reply("Timer will fire in " + period + " ms");
      vertx.setTimer(period, h -> {
        router.sendMessage(new SendOptions().setTarget(msg.user()), "Timer fired");
      });
    });
  }
}
