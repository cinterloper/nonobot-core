require 'nonobot/message'
require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.client.BotClient
module Nonobot
  #  The bot client provides a customized client interface for interacting with the bot.
  class BotClient
    # @private
    # @param j_del [::Nonobot::BotClient] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::BotClient] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [Hash] options 
    # @yield 
    # @return [void]
    def self.client(vertx=nil,options=nil)
      if vertx.class.method_defined?(:j_del) && block_given? && options == nil
        return Java::IoNonobotCoreClient::BotClient.java_method(:client, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
      elsif vertx.class.method_defined?(:j_del) && options.class == Hash && block_given?
        return Java::IoNonobotCoreClient::BotClient.java_method(:client, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotCoreClient::ClientOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,Java::IoNonobotCoreClient::ClientOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::Nonobot::BotClient) : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling client(vertx,options)"
    end
    # @return [String]
    def name
      if !block_given?
        return @j_del.java_method(:name, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling name()"
    end
    # @return [::Vertx::Vertx]
    def vertx
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:vertx, []).call(),::Vertx::Vertx)
      end
      raise ArgumentError, "Invalid arguments when calling vertx()"
    end
    #  Alias the bot for this client, when the client process a message it will use the specified <code>name</code> to
    #  detect if the message is addressed to the bot or not.
    # @overload alias(name)
    #   @param [String] name the bot name
    # @overload alias(names)
    #   @param [Array<String>] names the bot names
    # @return [void]
    def alias(param_1=nil)
      if param_1.class == String && !block_given?
        return @j_del.java_method(:alias, [Java::java.lang.String.java_class]).call(param_1)
      elsif param_1.class == Array && !block_given?
        return @j_del.java_method(:alias, [Java::JavaUtil::List.java_class]).call(param_1.map { |element| element })
      end
      raise ArgumentError, "Invalid arguments when calling alias(param_1)"
    end
    #  Receive a message, the message might trigger a reply from an handler, if that happens it should be fast. However
    #  if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
    #  should not wait until the reply is called, instead if should just forward the reply content when it arrives.
    # @param [Hash] options the receive options
    # @param [String] message the message content to process
    # @yield the handle to be notified with the message reply
    # @return [void]
    def receive_message(options=nil,message=nil)
      if options.class == Hash && message.class == String && block_given?
        return @j_del.java_method(:receiveMessage, [Java::IoNonobotCoreClient::ReceiveOptions.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoNonobotCoreClient::ReceiveOptions.new(::Vertx::Util::Utils.to_json_object(options)),message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling receive_message(options,message)"
    end
    #  Set a message handler on this client.
    # @yield the message handler
    # @return [self]
    def message_handler
      if block_given?
        @j_del.java_method(:messageHandler, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::Nonobot::Message)) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling message_handler()"
    end
    #  Set an handler called when the client is closed.
    # @yield the handler
    # @return [self]
    def close_handler
      if block_given?
        @j_del.java_method(:closeHandler, [Java::IoVertxCore::Handler.java_class]).call(Proc.new { yield })
        return self
      end
      raise ArgumentError, "Invalid arguments when calling close_handler()"
    end
    #  Close the client.
    # @return [void]
    def close
      if !block_given?
        return @j_del.java_method(:close, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
  end
end
