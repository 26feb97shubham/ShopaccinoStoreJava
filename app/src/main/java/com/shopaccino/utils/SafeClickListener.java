package com.shopaccino.utils;

import android.os.SystemClock;
import android.view.View;

import android.os.SystemClock;
import android.view.View;

public abstract class SafeClickListener implements View.OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 600;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentClickTime = SystemClock.elapsedRealtime();
        if ((currentClickTime - lastClickTime) >= MIN_CLICK_INTERVAL) {
            // If enough time has passed, trigger the onSafeClick method
            lastClickTime = currentClickTime;
            onSafeClick(view);
        }
    }

    public abstract void onSafeClick(View view);
}
