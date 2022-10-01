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

/**
 * Manages instances of ProgressBar. ProgressBar is wrapped in a
 * ProgressBarContainerView because the style of the ProgressBar can only be set
 * in the constructor; whenever the style of a ProgressBar changes, we have to
 * drop the existing ProgressBar (if there is one) and create a new one with the
 * style given.
 */

public class ReactProgressBarViewManagerImpl {

  public static final String REACT_CLASS = "RNCProgressBar";

  /* package */ public static final String PROP_STYLE = "styleAttr";
  /* package */ public static final String PROP_INDETERMINATE = "indeterminate";
  /* package */ public static final String PROP_PROGRESS = "progress";
  /* package */ public static final String PROP_ANIMATING = "animating";

  /* package */ public static final String DEFAULT_STYLE = "Normal";

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

  public static void setStyle(ProgressBarContainerView view, @Nullable String styleName) {
    view.setStyle(styleName);
  }

  public static void setColor(ProgressBarContainerView view, @Nullable Integer color) {
    view.setColor(color);
  }

  public static void setIndeterminate(ProgressBarContainerView view, boolean indeterminate) {
    view.setIndeterminate(indeterminate);
  }

  public static void setProgress(ProgressBarContainerView view, double progress) {
    view.setProgress(progress);
  }

  public static void setAnimating(ProgressBarContainerView view, boolean animating) {
    view.setAnimating(animating);
  }

  /* package */ public static int getStyleFromString(@Nullable String styleStr) {
    if (styleStr == null) {
      throw new JSApplicationIllegalArgumentException("ProgressBar needs to have a style, null received");
    } else if (styleStr.equals("Horizontal")) {
      return android.R.attr.progressBarStyleHorizontal;
    } else if (styleStr.equals("Small")) {
      return android.R.attr.progressBarStyleSmall;
    } else if (styleStr.equals("Large")) {
      return android.R.attr.progressBarStyleLarge;
    } else if (styleStr.equals("Inverse")) {
      return android.R.attr.progressBarStyleInverse;
    } else if (styleStr.equals("SmallInverse")) {
      return android.R.attr.progressBarStyleSmallInverse;
    } else if (styleStr.equals("LargeInverse")) {
      return android.R.attr.progressBarStyleLargeInverse;
    } else if (styleStr.equals("Normal")) {
      return android.R.attr.progressBarStyle;
    } else {
      throw new JSApplicationIllegalArgumentException("Unknown ProgressBar style: " + styleStr);
    }
  }
}
