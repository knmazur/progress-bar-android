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
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.bridge.ReactApplicationContext;

/**
 * Manages instances of ProgressBar. ProgressBar is wrapped in a
 * ProgressBarContainerView because the style of the ProgressBar can only be set
 * in the constructor; whenever the style of a ProgressBar changes, we have to
 * drop the existing ProgressBar (if there is one) and create a new one with the
 * style given.
 */
@ReactModule(name = ReactProgressBarViewManagerImpl.REACT_CLASS)
public class ReactProgressBarViewManager extends BaseViewManager<ProgressBarContainerView, ProgressBarShadowNode> {
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

  public ReactProgressBarViewManager(ReactApplicationContext context) {}

  @Override
  public String getName() {
    return ReactProgressBarViewManagerImpl.REACT_CLASS;
  }

  @Override
  protected ProgressBarContainerView createViewInstance(ThemedReactContext context) {
    return new ProgressBarContainerView(context);
  }

  @ReactProp(name = ReactProgressBarViewManagerImpl.PROP_STYLE)
  public void setStyle(ProgressBarContainerView view, @Nullable String styleName) {
    ReactProgressBarViewManagerImpl.setStyle(view, styleName);
  }

  @ReactProp(name = ViewProps.COLOR, customType = "Color")
  public void setColor(ProgressBarContainerView view, @Nullable Integer color) {
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
  public ProgressBarShadowNode createShadowNodeInstance() {
    return new ProgressBarShadowNode();
  }

  @Override
  public Class<ProgressBarShadowNode> getShadowNodeClass() {
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
