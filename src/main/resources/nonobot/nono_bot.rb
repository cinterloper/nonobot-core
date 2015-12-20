require 'vertx/vertx'
require 'nonobot/bot_client'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.NonoBot
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class NonoBot
    # @private
    # @param j_del [::Nonobot::NonoBot] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::NonoBot] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @return [::Nonobot::NonoBot]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::NonoBot.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::NonoBot)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    # @return [::Vertx::Vertx]
    def vertx
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:vertx, []).call(),::Vertx::Vertx)
      end
      raise ArgumentError, "Invalid arguments when calling vertx()"
    end
    # @yield 
    # @return [void]
    def client
      if block_given?
        return @j_del.java_method(:client, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling client()"
    end
  end
end
