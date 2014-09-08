/*
 * Copyright 2014 Nekoko SARL.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package gwtquery.plugins.lazyload.client;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.lazyload.client.LazyLoad.LazyLoad;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;

/**
 * Example code for the LazyLoad GwtQuery plugin.
 */
public class LazyLoadSample implements EntryPoint {

  @Override
  public void onModuleLoad() {
    $(".simple img").as(LazyLoad).lazyload();
    $(".container img").as(LazyLoad).lazyload(new Options().setContainer($("#container")));
    $(".fade img").as(LazyLoad).lazyload(new Options().setThreshold(20).setEffect(lazy().fadeIn(1000).done()));
    $(".external").click(new Function() {
      @Override
      public boolean f(Event e) {
        Window.open($(e).attr("href"), "_blank", null);
        e.preventDefault();
        return false;
      }
    });
  }
}
