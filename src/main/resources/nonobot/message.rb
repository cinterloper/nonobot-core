require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.handler.Message
module Nonobot
  #  A message sent to an handler.
  class Message
    # @private
    # @param j_del [::Nonobot::Message] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::Message] the underlying java delegate
    def j_del
      @j_del
    end
    #  @return the id that uniquely identifies the chat this message is coming from, this id can be used for posting
    #          messages to the chat
    # @return [String]
    def chat_id
      if !block_given?
        return @j_del.java_method(:chatId, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling chat_id()"
    end
    #  @return the message body
    # @return [String]
    def body
      if !block_given?
        return @j_del.java_method(:body, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling body()"
    end
    #  Reply to the message with an acknowledgement handler given a <code>timeout</code>.
    # @param [String] msg the reply
    # @param [Fixnum] ackTimeout the acknowledgement timeout
    # @yield handler to be notified if the reply is consumed effectively
    # @return [void]
    def reply(msg=nil,ackTimeout=nil)
      if msg.class == String && !block_given? && ackTimeout == nil
        return @j_del.java_method(:reply, [Java::java.lang.String.java_class]).call(msg)
      elsif msg.class == String && block_given? && ackTimeout == nil
        return @j_del.java_method(:reply, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(msg,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      elsif msg.class == String && ackTimeout.class == Fixnum && block_given?
        return @j_del.java_method(:reply, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(msg,ackTimeout,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling reply(msg,ackTimeout)"
    end
  end
end
