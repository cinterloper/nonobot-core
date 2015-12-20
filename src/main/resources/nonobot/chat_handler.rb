require 'nonobot/chat_message'
require 'vertx/vertx'
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
    # @param [::Vertx::Vertx] vertx 
    # @return [::Nonobot::ChatHandler]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreChat::ChatHandler.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::ChatHandler)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    # @param [String] regex 
    # @return [self]
    def pattern(regex=nil)
      if regex.class == String && !block_given?
        @j_del.java_method(:pattern, [Java::java.lang.String.java_class]).call(regex)
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pattern(regex)"
    end
    # @yield 
    # @return [self]
    def message_handler
      if block_given?
        @j_del.java_method(:messageHandler, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::ChatMessage)) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling message_handler()"
    end
    # @yield 
    # @return [void]
    def bind
      if block_given?
        return @j_del.java_method(:bind, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling bind()"
    end
    # @yield 
    # @return [void]
    def unbind
      if block_given?
        return @j_del.java_method(:unbind, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling unbind()"
    end
  end
end
