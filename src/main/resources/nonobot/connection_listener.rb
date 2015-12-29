require 'nonobot/bot_client'
require 'vertx/future'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.adapter.ConnectionListener
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class ConnectionListener < ::Vertx::Future
    # @private
    # @param j_del [::Nonobot::ConnectionListener] the java delegate
    def initialize(j_del)
      super(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::ConnectionListener] the underlying java delegate
    def j_del
      @j_del
    end
    # @return [::Nonobot::BotClient]
    def client
      if !block_given?
        if @cached_client != nil
          return @cached_client
        end
        return @cached_client = ::Vertx::Util::Utils.safe_create(@j_del.java_method(:client, []).call(),::Nonobot::BotClient)
      end
      raise ArgumentError, "Invalid arguments when calling client()"
    end
  end
end
