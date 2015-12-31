require 'nonobot/connection_request'
require 'vertx/vertx'
require 'nonobot/bot_client'
require 'vertx/future'
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
    #  Create new adapter.
    # @param [::Vertx::Vertx] vertx the vertx instance to use
    # @return [::Nonobot::BotAdapter] the bot adapter
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreAdapter::BotAdapter.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::BotAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    #  Run the bot adapter, until it is closed.
    # @param [Hash] options the client options to use
    # @return [void]
    def run(options=nil)
      if options.class == Hash && !block_given?
        return @j_del.java_method(:run, [Java::IoNonobotCoreClient::ClientOptions.java_class]).call(Java::IoNonobotCoreClient::ClientOptions.new(::Vertx::Util::Utils.to_json_object(options)))
      end
      raise ArgumentError, "Invalid arguments when calling run(options)"
    end
    # @return [true,false]
    def running?
      if !block_given?
        return @j_del.java_method(:isRunning, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling running?()"
    end
    # @return [true,false]
    def connected?
      if !block_given?
        return @j_del.java_method(:isConnected, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling connected?()"
    end
    #  Set the connection request handler.
    # @yield the connection request handler
    # @return [self]
    def request_handler
      if block_given?
        @j_del.java_method(:requestHandler, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::ConnectionRequest)) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling request_handler()"
    end
    #  Connect to the adapted service.
    # @param [::Nonobot::BotClient] client the client to use
    # @param [::Vertx::Future] completionFuture the future to complete or fail when connection is either a success or a failure
    # @return [self]
    def connect(client=nil,completionFuture=nil)
      if client.class.method_defined?(:j_del) && !block_given? && completionFuture == nil
        @j_del.java_method(:connect, [Java::IoNonobotCoreClient::BotClient.java_class]).call(client.j_del)
        return self
      elsif client.class.method_defined?(:j_del) && completionFuture.class.method_defined?(:j_del) && !block_given?
        @j_del.java_method(:connect, [Java::IoNonobotCoreClient::BotClient.java_class,Java::IoVertxCore::Future.java_class]).call(client.j_del,completionFuture.j_del)
        return self
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
