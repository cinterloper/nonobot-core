require 'nonobot/message'
require 'nonobot/chat_handler'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.chat.ChatRouter
module Nonobot
  #  The message router.
  class ChatRouter
    # @private
    # @param j_del [::Nonobot::ChatRouter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::ChatRouter] the underlying java delegate
    def j_del
      @j_del
    end
    #  Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.
    # @param [String] pattern the matching pattern
    # @yield the message handler
    # @return [::Nonobot::ChatHandler] the message handler object
    def when(pattern=nil)
      if pattern.class == String && block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:when, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::Message)) })),::Nonobot::ChatHandler)
      end
      raise ArgumentError, "Invalid arguments when calling when(pattern)"
    end
    #  Add a message handler triggered when the <code>pattern</code> prepended with the bot name is fully matched,
    #  the pattern is a <code>java.util.regex</code>.
    # @param [String] pattern the matching pattern
    # @yield the message handler
    # @return [::Nonobot::ChatHandler] the message handler object
    def respond(pattern=nil)
      if pattern.class == String && block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:respond, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::Message)) })),::Nonobot::ChatHandler)
      end
      raise ArgumentError, "Invalid arguments when calling respond(pattern)"
    end
    #  Send a message to a target.
    # @param [Hash] options the options
    # @param [String] body the message body
    # @return [self]
    def send_message(options=nil,body=nil)
      if options.class == Hash && body.class == String && !block_given?
        @j_del.java_method(:sendMessage, [Java::IoNonobotCoreChat::SendOptions.java_class,Java::java.lang.String.java_class]).call(Java::IoNonobotCoreChat::SendOptions.new(::Vertx::Util::Utils.to_json_object(options)),body)
        return self
      end
      raise ArgumentError, "Invalid arguments when calling send_message(options,body)"
    end
    #  Close the message router.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
