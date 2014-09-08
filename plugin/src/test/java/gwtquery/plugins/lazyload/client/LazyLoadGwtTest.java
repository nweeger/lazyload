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

import static gwtquery.plugins.lazyload.client.LazyLoad.*;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.query.client.GQuery;
/**
 * Test class for LazyLoad plugin.
 */
public class LazyLoadGwtTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "gwtquery.plugins.lazyload.LazyLoad";
  }

  /**
   * This test merely ensures the plugin may compile.
   */
  public void testLazyLoadApply() {
    // execute the plugin method
    final GQuery g =  $("<div></div>").appendTo(document).as(LazyLoad).lazyload();
  }
}
