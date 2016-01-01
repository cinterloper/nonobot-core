require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.chat.ChatHandler
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class ChatHandler
    # @private
    # @param j_del [::Nonobot::ChatHandler] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::ChatHandler] the underlying java delegate
    def j_del
      @j_del
    end
    #  Close the chat handler.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
