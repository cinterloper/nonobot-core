require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.message.Message
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class Message
    # @private
    # @param j_del [::Nonobot::Message] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::Message] the underlying java delegate
    def j_del
      @j_del
    end
    # @return [String]
    def body
      if !block_given?
        return @j_del.java_method(:body, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling body()"
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
