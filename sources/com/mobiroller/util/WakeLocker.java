package com.mobiroller.util;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.mobiroller.preview.BuildConfig;

public abstract class WakeLocker {
    private static WakeLock wakeLock;

    public static void acquire(Context context) {
        WakeLock wakeLock2 = wakeLock;
        if (wakeLock2 != null) {
            wakeLock2.release();
        }
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        StringBuilder sb = new StringBuilder();
        sb.append("WakeLock:");
        sb.append(BuildConfig.APPLICATION_ID);
        wakeLock = powerManager.newWakeLock(805306394, sb.toString());
        wakeLock.acquire();
    }

    public static void release() {
        WakeLock wakeLock2 = wakeLock;
        if (wakeLock2 != null) {
            wakeLock2.release();
        }
        wakeLock = null;
    }
}
