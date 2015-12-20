/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.nonobot.groovy.core.adapter;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.groovy.core.Vertx
import io.vertx.core.Handler
import io.vertx.groovy.ext.shell.term.Term
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class ShellAdapter implements Handler<Term> {
  private final def io.nonobot.core.adapter.ShellAdapter delegate;
  public ShellAdapter(Object delegate) {
    this.delegate = (io.nonobot.core.adapter.ShellAdapter) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public void handle(Term arg0) {
    ((io.vertx.core.Handler) this.delegate).handle((io.vertx.ext.shell.term.Term)arg0.getDelegate());
  }
  public static ShellAdapter create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.nonobot.core.adapter.ShellAdapter.create((io.vertx.core.Vertx)vertx.getDelegate()), io.nonobot.groovy.core.adapter.ShellAdapter.class);
    return ret;
  }
}
