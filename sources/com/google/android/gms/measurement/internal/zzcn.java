package com.google.android.gms.measurement.internal;

final class zzcn implements Runnable {
    private final /* synthetic */ zzk zzaqn;
    private final /* synthetic */ zzby zzaqo;
    private final /* synthetic */ zzfu zzaqq;

    zzcn(zzby zzby, zzfu zzfu, zzk zzk) {
        this.zzaqo = zzby;
        this.zzaqq = zzfu;
        this.zzaqn = zzk;
    }

    public final void run() {
        this.zzaqo.zzamx.zzme();
        this.zzaqo.zzamx.zzb(this.zzaqq, this.zzaqn);
    }
}
