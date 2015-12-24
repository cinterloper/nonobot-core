require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.adapter.Adapter
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class Adapter
    # @private
    # @param j_del [::Nonobot::Adapter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::Adapter] the underlying java delegate
    def j_del
      @j_del
    end
    #  Connect to the adapted service.
    # @yield 
    # @return [void]
    def connect
      if !block_given?
        return @j_del.java_method(:connect, []).call()
      elsif block_given?
        return @j_del.java_method(:connect, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling connect()"
    end
    #  Handler notified when the service close.
    # @yield 
    # @return [void]
    def close_handler
      if block_given?
        return @j_del.java_method(:closeHandler, [Java::IoVertxCore::Handler.java_class]).call(Proc.new { yield })
      end
      raise ArgumentError, "Invalid arguments when calling close_handler()"
    end
    #  Close.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
