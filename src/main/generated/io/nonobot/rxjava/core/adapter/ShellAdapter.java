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

package io.nonobot.rxjava.core.adapter;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.shell.term.Term;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.nonobot.core.adapter.ShellAdapter original} non RX-ified interface using Vert.x codegen.
 */

public class ShellAdapter implements Handler<Term> {

  final io.nonobot.core.adapter.ShellAdapter delegate;

  public ShellAdapter(io.nonobot.core.adapter.ShellAdapter delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public void handle(Term arg0) { 
    this.delegate.handle((io.vertx.ext.shell.term.Term) arg0.getDelegate());
  }

  public static ShellAdapter create(Vertx vertx) { 
    ShellAdapter ret= ShellAdapter.newInstance(io.nonobot.core.adapter.ShellAdapter.create((io.vertx.core.Vertx) vertx.getDelegate()));
    return ret;
  }


  public static ShellAdapter newInstance(io.nonobot.core.adapter.ShellAdapter arg) {
    return arg != null ? new ShellAdapter(arg) : null;
  }
}
