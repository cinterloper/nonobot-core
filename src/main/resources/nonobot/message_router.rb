require 'nonobot/message_handler'
require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.message.MessageRouter
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
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
    # @param [::Vertx::Vertx] vertx 
    # @yield 
    # @return [::Nonobot::MessageRouter]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreMessage::MessageRouter.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::MessageRouter)
      elsif vertx.class.method_defined?(:j_del) && block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreMessage::MessageRouter.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) })),::Nonobot::MessageRouter)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    # @return [::Nonobot::MessageHandler]
    def handler
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:handler, []).call(),::Nonobot::MessageHandler)
      end
      raise ArgumentError, "Invalid arguments when calling handler()"
    end
  end
end
