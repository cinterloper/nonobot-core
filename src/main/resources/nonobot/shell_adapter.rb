require 'vertx/vertx'
require 'vertx-shell/term'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.core.adapter.ShellAdapter
module Nonobot
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class ShellAdapter
    # @private
    # @param j_del [::Nonobot::ShellAdapter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::Nonobot::ShellAdapter] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::VertxShell::Term] arg0 
    # @return [void]
    def handle(arg0=nil)
      if arg0.class.method_defined?(:j_del) && !block_given?
        return @j_del.java_method(:handle, [Java::IoVertxExtShellTerm::Term.java_class]).call(arg0.j_del)
      end
      raise ArgumentError, "Invalid arguments when calling handle(arg0)"
    end
    # @param [::Vertx::Vertx] vertx 
    # @return [::Nonobot::ShellAdapter]
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotCoreAdapter::ShellAdapter.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::Nonobot::ShellAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
  end
end
