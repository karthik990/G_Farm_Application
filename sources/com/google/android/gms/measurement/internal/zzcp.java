package com.google.android.gms.measurement.internal;

final class zzcp implements Runnable {
    private final /* synthetic */ zzk zzaqn;
    private final /* synthetic */ zzby zzaqo;

    zzcp(zzby zzby, zzk zzk) {
        this.zzaqo = zzby;
        this.zzaqn = zzk;
    }

    public final void run() {
        this.zzaqo.zzamx.zzme();
        this.zzaqo.zzamx.zzf(this.zzaqn);
    }
}
