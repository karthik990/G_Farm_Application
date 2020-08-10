package com.wdullaer.materialdatetimepicker;

import android.content.Context;
import android.database.ContentObserver;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings.System;

public class HapticFeedbackController {
    private static final int VIBRATE_DELAY_MS = 125;
    private static final int VIBRATE_LENGTH_MS = 50;
    private final ContentObserver mContentObserver = new ContentObserver(null) {
        public void onChange(boolean z) {
            HapticFeedbackController hapticFeedbackController = HapticFeedbackController.this;
            hapticFeedbackController.mIsGloballyEnabled = HapticFeedbackController.checkGlobalSetting(hapticFeedbackController.mContext);
        }
    };
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public boolean mIsGloballyEnabled;
    private long mLastVibrate;
    private Vibrator mVibrator;

    /* access modifiers changed from: private */
    public static boolean checkGlobalSetting(Context context) {
        return System.getInt(context.getContentResolver(), "haptic_feedback_enabled", 0) == 1;
    }

    public HapticFeedbackController(Context context) {
        this.mContext = context;
    }

    public void start() {
        if (hasVibratePermission(this.mContext)) {
            this.mVibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        }
        this.mIsGloballyEnabled = checkGlobalSetting(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(System.getUriFor("haptic_feedback_enabled"), false, this.mContentObserver);
    }

    private boolean hasVibratePermission(Context context) {
        return context.getPackageManager().checkPermission("android.permission.VIBRATE", context.getPackageName()) == 0;
    }

    public void stop() {
        this.mVibrator = null;
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public void tryVibrate() {
        if (this.mVibrator != null && this.mIsGloballyEnabled) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.mLastVibrate >= 125) {
                this.mVibrator.vibrate(50);
                this.mLastVibrate = uptimeMillis;
            }
        }
    }
}
