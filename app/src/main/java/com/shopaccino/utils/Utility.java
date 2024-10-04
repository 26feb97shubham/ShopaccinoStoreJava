package com.shopaccino.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

public class Utility {

    public static int getWidth(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Activity activity = (Activity) context;
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            WindowInsets insets = windowMetrics.getWindowInsets();
            return windowMetrics.getBounds().width() - insets.getStableInsetLeft() - insets.getStableInsetRight();
        } else {
            // For Android 10 (API level 29) and below
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Activity activity = (Activity) context;
            WindowManager windowManager = activity.getWindowManager();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
