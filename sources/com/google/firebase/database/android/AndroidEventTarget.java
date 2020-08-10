package com.google.firebase.database.android;

import android.os.Handler;
import android.os.Looper;
import com.google.firebase.database.core.EventTarget;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class AndroidEventTarget implements EventTarget {
    private final Handler handler = new Handler(Looper.getMainLooper());

    public void restart() {
    }

    public void shutdown() {
    }

    public void postEvent(Runnable runnable) {
        this.handler.post(runnable);
    }
}
