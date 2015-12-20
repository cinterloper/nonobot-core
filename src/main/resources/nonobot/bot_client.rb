require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.client.BotClient
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class BotClient
    # @private
    # @param j_del [::Nonobot::BotClient] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::BotClient] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [String] message 
    # @yield 
    # @return [void]
    def publish(message=nil)
      if message.class == String && block_given?
        return @j_del.java_method(:publish, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling publish(message)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
