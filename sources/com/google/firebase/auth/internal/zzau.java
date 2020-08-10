package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.internal.firebase_auth.zzj;
import java.util.concurrent.Executor;

public final class zzau implements Executor {
    private static zzau zzva = new zzau();
    private Handler handler = new zzj(Looper.getMainLooper());

    private zzau() {
    }

    public final void execute(Runnable runnable) {
        this.handler.post(runnable);
    }

    public static zzau zzfs() {
        return zzva;
    }
}
