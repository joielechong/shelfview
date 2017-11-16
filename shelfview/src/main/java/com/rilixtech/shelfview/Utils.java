package com.rilixtech.shelfview;

import android.content.Context;

class Utils {

  //private Context mContext;
  private Utils(){}
  //private Utils(Context mContext) {
  //  this.mContext = mContext;
  //}

  static int dpToPixels(Context context, int dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  static int pixelsToDp(Context context, int pixels) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pixels / scale + 0.5f);
  }
}
