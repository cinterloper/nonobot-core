require 'nonobot/adapter'
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
    # @return [String]
    def name
      if !block_given?
        return @j_del.java_method(:name, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling name()"
    end
    # @param [Proc] handler 
    # @param [Hash] options 
    # @return [void]
    def client(handler=nil,options=nil)
      if block_given? && handler == nil && options == nil
        return @j_del.java_method(:client, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
      elsif handler.class == Proc && options.class == Hash && !block_given?
        return @j_del.java_method(:client, [Java::IoVertxCore::Handler.java_class,Java::IoNonobotCoreClient::ClientOptions.java_class]).call((Proc.new { |ar| handler.call(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }),Java::IoNonobotCoreClient::ClientOptions.new(::Vertx::Util::Utils.to_json_object(options)))
      end
      raise ArgumentError, "Invalid arguments when calling client(handler,options)"
    end
    # @param [::Nonobot::Adapter] adapter 
    # @param [Fixnum] reconnectPeriod 
    # @return [self]
    def add_adapter(adapter=nil,reconnectPeriod=nil)
      if adapter.class.method_defined?(:j_del) && !block_given? && reconnectPeriod == nil
        @j_del.java_method(:addAdapter, [Java::IoNonobotCoreAdapter::Adapter.java_class]).call(adapter.j_del)
        return self
      elsif adapter.class.method_defined?(:j_del) && reconnectPeriod.class == Fixnum && !block_given?
        @j_del.java_method(:addAdapter, [Java::IoNonobotCoreAdapter::Adapter.java_class,Java::long.java_class]).call(adapter.j_del,reconnectPeriod)
        return self
      end
      raise ArgumentError, "Invalid arguments when calling add_adapter(adapter,reconnectPeriod)"
    end
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
