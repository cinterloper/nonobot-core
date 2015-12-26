require 'nonobot/message_handler'
require 'vertx/vertx'
require 'nonobot/message_router'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.handlers.HelpHandler
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class HelpHandler
    # @private
    # @param j_del [::Nonobot::HelpHandler] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::HelpHandler] the underlying java delegate
    def j_del
      @j_del
    end
    # @return [::Nonobot::HelpHandler]
    def self.create
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreHandlers::HelpHandler.java_method(:create, []).call(),::Nonobot::HelpHandler)
      end
      raise ArgumentError, "Invalid arguments when calling create()"
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [::Nonobot::MessageRouter] router 
    # @return [::Nonobot::MessageHandler]
    def to_chat_handler(vertx=nil,router=nil)
      if vertx.class.method_defined?(:j_del) && router.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:toChatHandler, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotCoreMessage::MessageRouter.java_class]).call(vertx.j_del,router.j_del),::Nonobot::MessageHandler)
      end
      raise ArgumentError, "Invalid arguments when calling to_chat_handler(vertx,router)"
    end
  end
end
