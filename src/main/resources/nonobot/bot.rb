require 'vertx/vertx'
require 'nonobot/chat_router'
require 'vertx-web/router'
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
    #  Create a shared bot instance for the Vert.x instance.
    # @param [::Vertx::Vertx] vertx the Vert.x instance
    # @param [Hash] options the bot options
    # @yield called when the bot is fully initialized
    # @return [::Nonobot::Bot] the bot instance
    def self.create_shared(vertx=nil,options=nil)
      if vertx.class.method_defined?(:j_del) && options.class == Hash && block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:createShared, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotCore::BotOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,Java::IoNonobotCore::BotOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) })),::Nonobot::Bot)
      end
      raise ArgumentError, "Invalid arguments when calling create_shared(vertx,options)"
    end
    #  Gets a shared bot instance for the Vert.x instance.
    # @param [::Vertx::Vertx] vertx the Vert.x instance
    # @param [String] name the bot name
    # @return [::Nonobot::Bot] the bot instance
    def self.get_shared(vertx=nil,name=nil)
      if vertx.class.method_defined?(:j_del) && !block_given? && name == nil
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:getShared, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::Bot)
      elsif vertx.class.method_defined?(:j_del) && name.class == String && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:getShared, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class]).call(vertx.j_del,name),::Nonobot::Bot)
      end
      raise ArgumentError, "Invalid arguments when calling get_shared(vertx,name)"
    end
    #  Create a new bot for the Vert.x instance and specified options.
    # @param [::Vertx::Vertx] vertx the Vert.x instance
    # @param [String] name the bot name
    # @return [::Nonobot::Bot] the created bot
    def self.create(vertx=nil,name=nil)
      if vertx.class.method_defined?(:j_del) && !block_given? && name == nil
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::Bot)
      elsif vertx.class.method_defined?(:j_del) && name.class == String && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCore::Bot.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class]).call(vertx.j_del,name),::Nonobot::Bot)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,name)"
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
    # @return [::Nonobot::ChatRouter]
    def chat_router
      if !block_given?
        if @cached_chat_router != nil
          return @cached_chat_router
        end
        return @cached_chat_router = ::Vertx::Util::Utils.safe_create(@j_del.java_method(:chatRouter, []).call(),::Nonobot::ChatRouter)
      end
      raise ArgumentError, "Invalid arguments when calling chat_router()"
    end
    # @return [::VertxWeb::Router]
    def web_router
      if !block_given?
        if @cached_web_router != nil
          return @cached_web_router
        end
        return @cached_web_router = ::Vertx::Util::Utils.safe_create(@j_del.java_method(:webRouter, []).call(),::VertxWeb::Router)
      end
      raise ArgumentError, "Invalid arguments when calling web_router()"
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
