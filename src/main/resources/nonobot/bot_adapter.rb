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
    #  Run the bot adapter, until it is closed: the adapter performs a connection request. If the connection request
    #  fails or if the connection is closed, a new connection request is performed after the reconnect period.
    # @param [Hash] options the client options to use
    # @return [void]
    def run(options=nil)
      if options.class == Hash && !block_given?
        return @j_del.java_method(:run, [Java::IoNonobotCoreClient::ClientOptions.java_class]).call(Java::IoNonobotCoreClient::ClientOptions.new(::Vertx::Util::Utils.to_json_object(options)))
      end
      raise ArgumentError, "Invalid arguments when calling run(options)"
    end
    #  @return true if the adapter is running
    # @return [true,false]
    def running?
      if !block_given?
        return @j_del.java_method(:isRunning, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling running?()"
    end
    #  @return true if the adapter is connected
    # @return [true,false]
    def connected?
      if !block_given?
        return @j_del.java_method(:isConnected, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling connected?()"
    end
    #  Set the connection request handler. The request handler is called when the adapter needs to connect to the adapted
    #  service. The handler can be called many times (reconnect) but manages a single connection per adapter.<p>
    # 
    #  When the handler is connected, it should call {::Vertx::Future#complete} to signal the adapter it is
    #  connected, if the connection attempt fails, it should instead call {::Vertx::Future#fail}.<p>
    # 
    #  When the adapter is disconnected, the handler should call  to signal it the adapter.
    #  The adapter will likely try to reconnect to the service unless it is in closed state.
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
    #  Close the adapter, if the adapter is currently running, the current client connection will be closed.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
