package com.shopaccino.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
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
}
