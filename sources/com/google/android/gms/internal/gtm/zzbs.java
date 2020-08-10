package com.google.android.gms.internal.gtm;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

abstract class zzbs {
    private static volatile Handler handler;
    /* access modifiers changed from: private */
    public final zzap zzwc;
    private final Runnable zzys = new zzbt(this);
    /* access modifiers changed from: private */
    public volatile long zzyt;

    zzbs(zzap zzap) {
        Preconditions.checkNotNull(zzap);
        this.zzwc = zzap;
    }

    public abstract void run();

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzyt = this.zzwc.zzcn().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzys, j)) {
                this.zzwc.zzco().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public final void zzi(long j) {
        if (zzez()) {
            if (j < 0) {
                cancel();
                return;
            }
            long abs = j - Math.abs(this.zzwc.zzcn().currentTimeMillis() - this.zzyt);
            if (abs < 0) {
                abs = 0;
            }
            getHandler().removeCallbacks(this.zzys);
            if (!getHandler().postDelayed(this.zzys, abs)) {
                this.zzwc.zzco().zze("Failed to adjust delayed post. time", Long.valueOf(abs));
            }
        }
    }

    public final long zzey() {
        if (this.zzyt == 0) {
            return 0;
        }
        return Math.abs(this.zzwc.zzcn().currentTimeMillis() - this.zzyt);
    }

    public final boolean zzez() {
        return this.zzyt != 0;
    }

    public final void cancel() {
        this.zzyt = 0;
        getHandler().removeCallbacks(this.zzys);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzbs.class) {
            if (handler == null) {
                handler = new zzdj(this.zzwc.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }
}
