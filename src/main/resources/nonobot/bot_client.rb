require 'nonobot/message'
require 'nonobot/nono_bot'
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
    #  @return the bot this client exposes.
    # @return [::Nonobot::NonoBot]
    def bot
      if !block_given?
        return ::Vertx::Util::Utils.safe_create(@j_del.java_method(:bot, []).call(),::Nonobot::NonoBot)
      end
      raise ArgumentError, "Invalid arguments when calling bot()"
    end
    #  Rename the bot for this client, when the client process a message it will use the specified <code>name</code> to
    #  detect if the message is addressed to the bot or not.
    # @overload rename(name)
    #   @param [String] name the bot name
    # @overload rename(names)
    #   @param [Array<String>] names the bot names
    # @return [void]
    def rename(param_1=nil)
      if param_1.class == String && !block_given?
        return @j_del.java_method(:rename, [Java::java.lang.String.java_class]).call(param_1)
      elsif param_1.class == Array && !block_given?
        return @j_del.java_method(:rename, [Java::JavaUtil::List.java_class]).call(param_1.map { |element| element })
      end
      raise ArgumentError, "Invalid arguments when calling rename(param_1)"
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
    #  Set an handler called when the client is closed, note that calling {::Nonobot::BotClient#close} will not call this handler.
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
