require 'nonobot/bot_client'
require 'vertx/future'
require 'nonobot/connection_listener'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.adapter.BotAdapter
module Nonobot
  #  Expose the bot to an external (usually remote) service.
  class BotAdapter
    # @private
    # @param j_del [::Nonobot::BotAdapter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::BotAdapter] the underlying java delegate
    def j_del
      @j_del
    end
    # @yield 
    # @return [::Nonobot::BotAdapter]
    def self.create
      if block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreAdapter::BotAdapter.java_method(:create, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::ConnectionListener)) })),::Nonobot::BotAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create()"
    end
    #  Connect to the adapted service.
    # @param [::Nonobot::BotClient] client 
    # @param [::Vertx::Future] completionFuture the future to complete or fail when connection is either a success or a failure
    # @return [void]
    def connect(client=nil,completionFuture=nil)
      if client.class.method_defined?(:j_del) && !block_given? && completionFuture == nil
        return @j_del.java_method(:connect, [Java::IoNonobotCoreClient::BotClient.java_class]).call(client.j_del)
      elsif client.class.method_defined?(:j_del) && completionFuture.class.method_defined?(:j_del) && !block_given?
        return @j_del.java_method(:connect, [Java::IoNonobotCoreClient::BotClient.java_class,Java::IoVertxCore::Future.java_class]).call(client.j_del,completionFuture.j_del)
      end
      raise ArgumentError, "Invalid arguments when calling connect(client,completionFuture)"
    end
    #  Close the adapter.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
