require 'nonobot/adapter'
require 'nonobot/nono_bot'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.adapter.SlackAdapter
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class SlackAdapter < ::Nonobot::Adapter
    # @private
    # @param j_del [::Nonobot::SlackAdapter] the java delegate
    def initialize(j_del)
      super(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::SlackAdapter] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Nonobot::NonoBot] bot 
    # @param [Hash] options 
    # @return [::Nonobot::SlackAdapter]
    def self.create(bot=nil,options=nil)
      if bot.class.method_defined?(:j_del) && options.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreAdapter::SlackAdapter.java_method(:create, [Java::IoNonobotCore::NonoBot.java_class,Java::IoNonobotCoreAdapter::SlackOptions.java_class]).call(bot.j_del,Java::IoNonobotCoreAdapter::SlackOptions.new(::Vertx::Util::Utils.to_json_object(options))),::Nonobot::SlackAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(bot,options)"
    end
  end
end
