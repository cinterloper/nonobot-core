package io.nonobot.core.spi;

import io.nonobot.core.Config;
import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.Adapter;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface AdapterFactory {

  Adapter create(NonoBot bot, Config config);

}
