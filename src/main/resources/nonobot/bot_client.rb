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
    # @return [String]
    def name
      if !block_given?
        return @j_del.java_method(:name, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling name()"
    end
    # @param [String] message 
    # @yield 
    # @return [void]
    def process(message=nil)
      if message.class == String && block_given?
        return @j_del.java_method(:process, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling process(message)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    # @yield 
    # @return [void]
    def close_handler
      if block_given?
        return @j_del.java_method(:closeHandler, [Java::IoVertxCore::Handler.java_class]).call(Proc.new { yield })
      end
      raise ArgumentError, "Invalid arguments when calling close_handler()"
    end
  end
end
