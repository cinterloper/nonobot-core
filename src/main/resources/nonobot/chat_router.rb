require 'vertx/vertx'
require 'nonobot/chat_handler'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.chat.ChatRouter
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
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
    # @param [::Vertx::Vertx] vertx 
    # @yield 
    # @return [::Nonobot::ChatRouter]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreChat::ChatRouter.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) })),::Nonobot::ChatRouter)
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
    # @return [::Nonobot::ChatHandler]
    def handler
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:handler, []).call(),::Nonobot::ChatHandler)
      end
      raise ArgumentError, "Invalid arguments when calling handler()"
    end
  end
end
