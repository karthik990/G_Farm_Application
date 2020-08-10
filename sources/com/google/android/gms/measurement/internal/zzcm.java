package com.google.android.gms.measurement.internal;

final class zzcm implements Runnable {
    private final /* synthetic */ zzk zzaqn;
    private final /* synthetic */ zzby zzaqo;
    private final /* synthetic */ zzfu zzaqq;

    zzcm(zzby zzby, zzfu zzfu, zzk zzk) {
        this.zzaqo = zzby;
        this.zzaqq = zzfu;
        this.zzaqn = zzk;
    }

    public final void run() {
        this.zzaqo.zzamx.zzme();
        this.zzaqo.zzamx.zzc(this.zzaqq, this.zzaqn);
    }
}
