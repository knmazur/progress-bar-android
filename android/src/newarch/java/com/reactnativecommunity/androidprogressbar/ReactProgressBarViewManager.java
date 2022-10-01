/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.reactnativecommunity.androidprogressbar;

import javax.annotation.Nullable;

import android.content.Context;
import android.widget.ProgressBar;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;

import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.RNCProgressBarManagerDelegate;
import com.facebook.react.viewmanagers.RNCProgressBarManagerInterface;

/**
 * Manages instances of ProgressBar. ProgressBar is wrapped in a
 * ProgressBarContainerView because the style of the ProgressBar can only be set
 * in the constructor; whenever the style of a ProgressBar changes, we have to
 * drop the existing ProgressBar (if there is one) and create a new one with the
 * style given.
 */

@ReactModule(name = ReactProgressBarViewManagerImpl.REACT_CLASS)
public class ReactProgressBarViewManager extends SimpleViewManager<ProgressBarContainerView> implements RNCProgressBarManagerInterface<ProgressBarContainerView> {
  private static Object sProgressBarCtorLock = new Object();

  /**
   * We create ProgressBars on both the UI and shadow threads. There is a race
   * condition in the ProgressBar constructor that may cause crashes when two
   * ProgressBars are constructed at the same time on two different threads. This
   * static ctor wrapper protects against that.
   */
  public static ProgressBar createProgressBar(Context context, int style) {
    synchronized (sProgressBarCtorLock) {
      return new ProgressBar(context, null, style);
    }
  }

    public ReactProgressBarViewManager(ReactApplicationContext context) {
        mDelegate = new RNCProgressBarManagerDelegate<>(this);
    }

  private final ViewManagerDelegate<ProgressBarContainerView> mDelegate;

   @Nullable
    @Override
    protected ViewManagerDelegate<ProgressBarContainerView> getDelegate() {
        return mDelegate;
    }

  @Override
  public String getName() {
    return ReactProgressBarViewManagerImpl.REACT_CLASS;
  }

  @Override
  protected ProgressBarContainerView createViewInstance(ThemedReactContext context) {
    return new ProgressBarContainerView(context);
  }

  @ReactProp(name = ReactProgressBarViewManagerImpl.PROP_STYLE)
  public void setStyleAttr(ProgressBarContainerView view, @Nullable String styleName) {
    ReactProgressBarViewManagerImpl.setStyle(view, styleName);
  }

   @ReactProp(name = "style")
  public void setStyle(ProgressBarContainerView view, @Nullable String styleName) {
    ReactProgressBarViewManagerImpl.setStyle(view, styleName);
  }

  @ReactProp(name = ViewProps.COLOR, customType = "Color")
  public void setColor(ProgressBarContainerView view, @Nullable int color) {
    ReactProgressBarViewManagerImpl.setColor(view, color);
  }

  @ReactProp(name = ReactProgressBarViewManagerImpl.PROP_INDETERMINATE)
  public void setIndeterminate(ProgressBarContainerView view, boolean indeterminate) {
    ReactProgressBarViewManagerImpl.setIndeterminate(view, indeterminate);
  }

  @ReactProp(name = ReactProgressBarViewManagerImpl.PROP_PROGRESS)
  public void setProgress(ProgressBarContainerView view, double progress) {
    ReactProgressBarViewManagerImpl.setProgress(view, progress);
  }

  @ReactProp(name = ReactProgressBarViewManagerImpl.PROP_ANIMATING)
  public void setAnimating(ProgressBarContainerView view, boolean animating) {
    ReactProgressBarViewManagerImpl.setAnimating(view, animating);
  }

  @Override
  public LayoutShadowNode createShadowNodeInstance() {
    return new ProgressBarShadowNode();
  }

  @Override
  public Class getShadowNodeClass() {
    return ProgressBarShadowNode.class;
  }

  @Override
  public void updateExtraData(ProgressBarContainerView root, Object extraData) {
    // do nothing
  }

  @Override
  protected void onAfterUpdateTransaction(ProgressBarContainerView view) {
    view.apply();
  }
}
