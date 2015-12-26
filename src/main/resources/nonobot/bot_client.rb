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
    #  Process a message, the message might trigger a reply from an handler, if that happens it should be fast. However
    #  if there is no handler for processing the message, the reply will be called and likely timeout. Therefore the client
    #  should not wait until the reply is called, instead if should just forward the reply content when it arrives.
    # @param [String] message the message content to process
    # @yield the handle to be notified with the message reply
    # @return [void]
    def process(message=nil)
      if message.class == String && block_given?
        return @j_del.java_method(:process, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling process(message)"
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
