require 'nonobot/chat_router'
require 'vertx/vertx'
require 'nonobot/chat_handler'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.handlers.PingHandler
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class PingHandler
    # @private
    # @param j_del [::Nonobot::PingHandler] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::PingHandler] the underlying java delegate
    def j_del
      @j_del
    end
    # @return [::Nonobot::PingHandler]
    def self.create
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreHandlers::PingHandler.java_method(:create, []).call(),::Nonobot::PingHandler)
      end
      raise ArgumentError, "Invalid arguments when calling create()"
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [::Nonobot::ChatRouter] router 
    # @return [::Nonobot::ChatHandler]
    def to_chat_handler(vertx=nil,router=nil)
      if vertx.class.method_defined?(:j_del) && router.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:toChatHandler, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotCoreChat::ChatRouter.java_class]).call(vertx.j_del,router.j_del),::Nonobot::ChatHandler)
      end
      raise ArgumentError, "Invalid arguments when calling to_chat_handler(vertx,router)"
    end
  end
end
