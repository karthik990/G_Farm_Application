package com.google.android.exoplayer2;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.exoplayer2.util.Log;

final class WakeLockManager {
    private static final String TAG = "WakeLockManager";
    private static final String WAKE_LOCK_TAG = "ExoPlayer:WakeLockManager";
    private boolean enabled;
    private final PowerManager powerManager;
    private boolean stayAwake;
    private WakeLock wakeLock;

    public WakeLockManager(Context context) {
        this.powerManager = (PowerManager) context.getSystemService("power");
    }

    public void setEnabled(boolean z) {
        if (z && this.wakeLock == null) {
            PowerManager powerManager2 = this.powerManager;
            if (powerManager2 == null) {
                Log.m1396w(TAG, "PowerManager was null, therefore the WakeLock was not created.");
                return;
            }
            this.wakeLock = powerManager2.newWakeLock(1, WAKE_LOCK_TAG);
        }
        this.enabled = z;
        updateWakeLock();
    }

    public void setStayAwake(boolean z) {
        this.stayAwake = z;
        updateWakeLock();
    }

    private void updateWakeLock() {
        WakeLock wakeLock2 = this.wakeLock;
        if (wakeLock2 == null) {
            return;
        }
        if (this.enabled) {
            if (this.stayAwake && !wakeLock2.isHeld()) {
                this.wakeLock.acquire();
            } else if (!this.stayAwake && this.wakeLock.isHeld()) {
                this.wakeLock.release();
            }
        } else if (wakeLock2.isHeld()) {
            this.wakeLock.release();
        }
    }
}
