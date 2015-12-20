require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.chat.ChatMessage
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class ChatMessage
    # @private
    # @param j_del [::Nonobot::ChatMessage] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::ChatMessage] the underlying java delegate
    def j_del
      @j_del
    end
    # @return [String]
    def content
      if !block_given?
        return @j_del.java_method(:content, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling content()"
    end
    # @param [String] msg 
    # @return [void]
    def reply(msg=nil)
      if msg.class == String && !block_given?
        return @j_del.java_method(:reply, [Java::java.lang.String.java_class]).call(msg)
      end
      raise ArgumentError, "Invalid arguments when calling reply(msg)"
    end
  end
end
