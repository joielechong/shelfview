package com.rilixtech.shelfview;

import android.content.Context;

class Utils {

  private Context mContext;

  public Utils(Context mContext) {
    this.mContext = mContext;
  }

  public int dpToPixels(int dp) {
    float scale = mContext.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  public int pixelsToDp(int pixels) {
    float scale = mContext.getResources().getDisplayMetrics().density;
    return (int) (pixels / scale + 0.5f);
  }
}
