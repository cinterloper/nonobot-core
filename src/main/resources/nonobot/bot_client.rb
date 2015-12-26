require 'nonobot/nono_bot'
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
    # @return [::Nonobot::NonoBot]
    def bot
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:bot, []).call(),::Nonobot::NonoBot)
      end
      raise ArgumentError, "Invalid arguments when calling bot()"
    end
    # @overload rename(name)
    #   @param [String] name 
    # @overload rename(names)
    #   @param [Array<String>] names 
    # @return [void]
    def rename(param_1=nil)
      if param_1.class == String && !block_given?
        return @j_del.java_method(:rename, [Java::java.lang.String.java_class]).call(param_1)
      elsif param_1.class == Array && !block_given?
        return @j_del.java_method(:rename, [Java::JavaUtil::List.java_class]).call(param_1.map { |element| element })
      end
      raise ArgumentError, "Invalid arguments when calling rename(param_1)"
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
