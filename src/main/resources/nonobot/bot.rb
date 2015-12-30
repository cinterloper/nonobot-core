require 'nonobot/bot_adapter'
require 'vertx/vertx'
require 'nonobot/bot_client'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.Bot
module Nonobot
  #  The bot.
  class Bot
    # @private
    # @param j_del [::Nonobot::Bot] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::Bot] the underlying java delegate
    def j_del
      @j_del
    end
    #  Create a new bot for the Vert.x instance and specified options.
    # @param [::Vertx::Vertx] vertx the Vert.x instance
    # @param [Hash] options the options
    # @return [::Nonobot::Bot] the created bot
    def self.create(vertx=nil,options=nil)
      if vertx.class.method_defined?(:j_del) && !block_given? && options == nil
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::Bot)
      elsif vertx.class.method_defined?(:j_del) && options.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotCore::BotOptions.java_class]).call(vertx.j_del,Java::IoNonobotCore::BotOptions.new(::Vertx::Util::Utils.to_json_object(options))),::Nonobot::Bot)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,options)"
    end
    #  @return the Vert.x instance used by this bot
    # @return [::Vertx::Vertx]
    def vertx
      if !block_given?
        if @cached_vertx != nil
          return @cached_vertx
        end
        return @cached_vertx = ::Vertx::Util::Utils.safe_create(@j_del.java_method(:vertx, []).call(),::Vertx::Vertx)
      end
      raise ArgumentError, "Invalid arguments when calling vertx()"
    end
    #  @return the bot name
    # @return [String]
    def name
      if !block_given?
        if @cached_name != nil
          return @cached_name
        end
        return @cached_name = @j_del.java_method(:name, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling name()"
    end
    #  Create a new bot client with the specified .
    # @param [Hash] options the client options
    # @yield receives the  after initialization
    # @return [self]
    def create_client(options=nil)
      if block_given? && options == nil
        @j_del.java_method(:createClient, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
        return self
      elsif options.class == Hash && block_given?
        @j_del.java_method(:createClient, [Java::IoNonobotCoreClient::ClientOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoNonobotCoreClient::ClientOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling create_client(options)"
    end
    #  Add an  with the bot, the bot will take care of the adapter life cycle and restart it when
    #  it gets disconnected;
    # @param [::Nonobot::BotAdapter] adapter the bot adapter
    # @param [Fixnum] reconnectPeriod how long wait before it attempts to reconnect in millis
    # @return [self]
    def register_adapter(adapter=nil,reconnectPeriod=nil)
      if adapter.class.method_defined?(:j_del) && !block_given? && reconnectPeriod == nil
        @j_del.java_method(:registerAdapter, [Java::IoNonobotCoreAdapter::BotAdapter.java_class]).call(adapter.j_del)
        return self
      elsif adapter.class.method_defined?(:j_del) && reconnectPeriod.class == Fixnum && !block_given?
        @j_del.java_method(:registerAdapter, [Java::IoNonobotCoreAdapter::BotAdapter.java_class,Java::long.java_class]).call(adapter.j_del,reconnectPeriod)
        return self
      end
      raise ArgumentError, "Invalid arguments when calling register_adapter(adapter,reconnectPeriod)"
    end
    #  Close the bot.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
