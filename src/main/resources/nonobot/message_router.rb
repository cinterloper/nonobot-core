require 'nonobot/message_handler'
require 'vertx/vertx'
require 'nonobot/message'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.handler.MessageRouter
module Nonobot
  #  The message router.
  class MessageRouter
    # @private
    # @param j_del [::Nonobot::MessageRouter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::MessageRouter] the underlying java delegate
    def j_del
      @j_del
    end
    #  Gets a shared message router instance for the Vert.x instance. There should be a single message router per
    #  Vert.x instance.
    # @param [::Vertx::Vertx] vertx the Vert.x instance
    # @yield the handler notified when the router is fully initialized
    # @return [::Nonobot::MessageRouter] the message router
    def self.get_shared(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreHandler::MessageRouter.java_method(:getShared, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::MessageRouter)
      elsif vertx.class.method_defined?(:j_del) && block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreHandler::MessageRouter.java_method(:getShared, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) })),::Nonobot::MessageRouter)
      end
      raise ArgumentError, "Invalid arguments when calling get_shared(vertx)"
    end
    #  Add a message handler triggered when the <code>pattern</code> is fully matched, the pattern is a <code>java.util.regex</code>.
    # @param [String] pattern the matching pattern
    # @yield the message handler
    # @return [::Nonobot::MessageHandler] the message handler object
    def when(pattern=nil)
      if pattern.class == String && block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:when, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::Message)) })),::Nonobot::MessageHandler)
      end
      raise ArgumentError, "Invalid arguments when calling when(pattern)"
    end
    #  Add a message handler triggered when the <code>pattern</code> prepended with the bot name is fully matched,
    #  the pattern is a <code>java.util.regex</code>.
    # @param [String] pattern the matching pattern
    # @yield the message handler
    # @return [::Nonobot::MessageHandler] the message handler object
    def respond(pattern=nil)
      if pattern.class == String && block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:respond, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::Message)) })),::Nonobot::MessageHandler)
      end
      raise ArgumentError, "Invalid arguments when calling respond(pattern)"
    end
    #  Send a message to a target.
    # @param [Hash] options the options
    # @param [String] body the message body
    # @return [self]
    def send_message(options=nil,body=nil)
      if options.class == Hash && body.class == String && !block_given?
        @j_del.java_method(:sendMessage, [Java::IoNonobotCoreHandler::SendOptions.java_class,Java::java.lang.String.java_class]).call(Java::IoNonobotCoreHandler::SendOptions.new(::Vertx::Util::Utils.to_json_object(options)),body)
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
