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

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

/**
 * Lazy loading options. The various setters return the Options instance so it's
 * easy to chain calls.
 *
 * @author Nicolas Weeger
 */
public class Options {

  /**
   * How many pixels around the element are considered for it to be visible.
   */
  private int threshold = 200;

  /**
   * Picture to display before the actual image is loaded. Should be a data uri.
   */
  private String placeholder = "data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==";

  /**
   * Container the items are into. If null then the browser's whole window is
   * considered.
   */
  private GQuery container = null;

  /**
   * Effect to apply to pictures when they are loaded. If none, then they are
   * simply shown.
   */
  private Function effect = null;

  /**
   * Event name to trigger when the picture should be shown.
   */
  private String eventName = "appear";

  /**
   * Return a copy of this instance.
   *
   * @return copy.
   */
  protected Options copy() {
    return new Options()
        .setContainer(container)
        .setEffect(effect)
        .setEventName(eventName)
        .setPlaceholder(placeholder)
        .setThreshold(threshold);
  }

  /**
   * Get the threshold to show pictures, in pixels.
   *
   * @return the threshold.
   */
  public int getThreshold() {
    return threshold;
  }

  /**
   * Set the threshold to show pictures, in pixels. Default value is 200 pixels.
   *
   * @param threshold the threshold to set, must be positive.
   * @return this.
   */
  public Options setThreshold(final int threshold) {
    assert threshold >= 0;
    this.threshold = threshold;
    return this;
  }

  /**
   * Get the picture to display before the actual picture is loaded.
   *
   * @return the placeholder
   */
  public String getPlaceholder() {
    return placeholder;
  }

  /**
   * Set the picture to display before the actual picture is loaded, should be a
   * data uri.
   *
   * @param placeholder the placeholder to set
   * @return this.
   */
  public Options setPlaceholder(final String placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  /**
   * Get the container the items are into. If null then the browser's whole
   * window is considered.
   *
   * @return the container.
   */
  public GQuery getContainer() {
    return container;
  }

  /**
   * Set the container the items are into. If null then the browser's whole
   * window is considered.
   *
   * @param container the container to set.
   * @return this.
   */
  public Options setContainer(final GQuery container) {
    this.container = container;
    return this;
  }

  /**
   * Get the effect to apply to pictures when they are loaded. If null, then
   * they are simply shown.
   *
   * @return the effect.
   */
  public Function getEffect() {
    return effect;
  }

  /**
   * Set the effect to apply to pictures when they are loaded. If null, then
   * they are simply shown.
   *
   * @param effect the effect to set
   * @return this.
   */
  public Options setEffect(final Function effect) {
    this.effect = effect;
    return this;
  }

  /**
   * Get the event name to trigger when the picture should be shown.
   *
   * @return the event name.
   */
  public String getEventName() {
    return eventName;
  }

  /**
   * Set the event name to trigger when the picture should be shown.
   *
   * @param eventName the event name to set.
   * @return this.
   */
  public Options setEventName(final String eventName) {
    this.eventName = eventName;
    return this;
  }
}
