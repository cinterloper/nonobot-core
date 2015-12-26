require 'nonobot/chat_message'
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
    # @param [String] pattern 
    # @yield 
    # @return [self]
    def match(pattern=nil)
      if pattern.class == String && block_given?
        @j_del.java_method(:match, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::ChatMessage)) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling match(pattern)"
    end
    # @param [String] pattern 
    # @yield 
    # @return [self]
    def respond(pattern=nil)
      if pattern.class == String && block_given?
        @j_del.java_method(:respond, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::ChatMessage)) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling respond(pattern)"
    end
    # @return [void]
    def create
      if !block_given?
        return @j_del.java_method(:create, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling create()"
    end
  end
end
