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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;
import java.util.ArrayList;

/**
 * Lazy picture loading for GWT.
 *
 * @author Nicolas Weeger
 */
public class LazyLoad extends GQuery {

  /**
   * Utility class checking pictures to see if they should be loaded.
   */
  private static class Updater {

    /**
     * Pictures to check.
     */
    private final ArrayList<Element> elements;

    /**
     * Options for those elements.
     */
    private final Options options;

    /**
     * Link to the handler registration for the browser's scroll handler, null
     * if checking a container.
     */
    private HandlerRegistration handler = null;

    /**
     * Function linked to the container's scroll event, null if checking the
     * whole window.
     */
    private Function containerHandler = null;

    /**
     * Standard constructor.
     *
     * @param elements pictures to check.
     * @param options options for the pictures.
     */
    private Updater(final NodeList<Element> elements, final Options options) {
      this.elements = new ArrayList<>(elements.getLength());
      for (int e = 0; e < elements.getLength(); e++) {
        this.elements.add(elements.getItem(e));
      }
      this.options = options;
    }

    /**
     * Check all non loaded yet pictures, and if they are visible then trigger
     * their loading event.
     */
    private void update() {
      for (int i = elements.size() - 1; i >= 0; i--) {
        final Element e = elements.get(i);
        if (isVisible(e)) {
          elements.remove(i);
          $(e).trigger(options.getEventName());
        }
      }

      if (elements.isEmpty()) {
        if (handler != null) {
          handler.removeHandler();
        }
        if (containerHandler != null) {
          options.getContainer().off("scroll", containerHandler);
        }
      }
    }

    /**
     * Check if a given element is visible by the user, that is not hidden by eg
     * CSS, and in the viewport.
     *
     * @param e item to check.
     * @return true if visible, false if invisible.
     */
    private boolean isVisible(final Element e) {
      final GQuery g = $(e);
      if (!g.is((":visible"))) {
        return false;
      }

      if (options.getContainer() != null) {
        return isVisibleContainer(g);
      }
      return isVisibleWindow(g);
    }

    /**
     * Check whether the specified item is visible in the defined container.
     *
     * @param g item to check.
     * @return true if visible, false else.
     */
    private boolean isVisibleContainer(final GQuery g) {
      assert options.getContainer() != null;
      final GQuery c = options.getContainer();
      if (c.offset().top > g.offset().top + g.height() + options.getThreshold()) {
        return false;
      }
      if (c.offset().top + c.height() < g.offset().top - options.getThreshold()) {
        return false;
      }
      if (c.offset().left > g.offset().left + g.width() + options.getThreshold()) {
        return false;
      }
      if (c.offset().left + c.width() < g.offset().left - options.getThreshold()) {
        return false;
      }
      return true;
    }

    /**
     * Check whether the specified item is visible in the browser window.
     *
     * @param g item to check.
     * @return true if visible, false else.
     */
    private boolean isVisibleWindow(final GQuery g) {
      if (Window.getScrollTop() > g.offset().top + g.height() + options.getThreshold()) {
        return false;
      }
      if (Window.getClientHeight() + Window.getScrollTop() < g.offset().top - options.getThreshold()) {
        return false;
      }
      if (Window.getScrollLeft() > g.offset().left + g.width() + options.getThreshold()) {
        return false;
      }
      if (Window.getClientWidth() + Window.getScrollLeft() < g.offset().left - options.getThreshold()) {
        return false;
      }
      return true;
    }

    /**
     * Set the handler registration for the browser's scroll handler.
     *
     * @param handler handler registration.
     */
    public void setHandler(final HandlerRegistration handler) {
      this.handler = handler;
    }

    /**
     * Set the handler for the browser's scroll handler, null if checking a
     * container.
     *
     * @param containerHandler handler.
     */
    public void setContainerHandler(final Function containerHandler) {
      this.containerHandler = containerHandler;
    }
  }

  /**
   * Shortcut to the class.
   */
  public static final Class<LazyLoad> LazyLoad = LazyLoad.class;

  /**
   * Register the plugin in GQuery.
   */
  static {
    GQuery.registerPlugin(LazyLoad.class, new Plugin<LazyLoad>() {
      @Override
      public LazyLoad init(GQuery gq) {
        return new LazyLoad(gq);
      }
    });
  }

  /**
   * Standard constructor.
   *
   * @param gq currently selected elements.
   */
  public LazyLoad(final GQuery gq) {
    super(gq);
  }

  /**
   * Lazy load selected pictures, only actually fetching the file when the
   * picture becomes visible in the user's viewport.
   *
   * Pictures must have a "data-original" attribute indicating the URL to load,
   * and they should have a correct size already.
   *
   * @return this.
   */
  public LazyLoad lazyload() {
    return lazyload(new Options());
  }

  /**
   * Lazy load selected pictures, only actually fetching the file when the
   * picture becomes visible in the user's viewport.
   *
   * Pictures must have a "data-original" attribute indicating the URL to load,
   * and they should have a correct size already.
   *
   * @param options configuration options.
   * @return this.
   */
  public LazyLoad lazyload(final Options options) {
    if (get().getLength() == 0) {
      return this;
    }

    final Options opt = options.copy();

    final Updater updater = new Updater(get(), opt);

    each(new Function() {
      @Override
      public void f(Element e) {

        final GQuery current = $(e);

        current.on(opt.getEventName(), new Function() {

          @Override
          public void f(final Element e) {

            current.off(opt.getEventName(), this);

            final String data = $(e).attr("data-original");
            if (data == null) {
              return;
            }
            $("<img/>").bind(Event.ONLOAD, new Function() {
              @Override
              public void f(final Element img) {
                current.hide().attr("src", $(img).attr("src"));
                if (opt.getEffect() == null) {
                  current.show();
                } else {
                  opt.getEffect().f(e);
                }
              }
            }).attr("src", data);
            current.removeAttr("data-original");
          }
        }).attr("src", opt.getPlaceholder());
      }
    });

    if (opt.getContainer() == null) {
      final HandlerRegistration registration = Window.addWindowScrollHandler(new Window.ScrollHandler() {
        @Override
        public void onWindowScroll(Window.ScrollEvent event) {
          updater.update();
        }
      });
      updater.setHandler(registration);
    } else {
      final Function handler;
      opt.getContainer().on("scroll", handler = new Function() {
        @Override
        public boolean f(final Event e) {
          updater.update();
          return true;
        }
      });
      updater.setContainerHandler(handler);
    }

    updater.update();

    return this;
  }

}
