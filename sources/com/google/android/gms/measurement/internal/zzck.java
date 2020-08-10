package com.google.android.gms.measurement.internal;

final class zzck implements Runnable {
    private final /* synthetic */ zzag zzagi;
    private final /* synthetic */ String zzagj;
    private final /* synthetic */ zzby zzaqo;

    zzck(zzby zzby, zzag zzag, String str) {
        this.zzaqo = zzby;
        this.zzagi = zzag;
        this.zzagj = str;
    }

    public final void run() {
        this.zzaqo.zzamx.zzme();
        this.zzaqo.zzamx.zzd(this.zzagi, this.zzagj);
    }
}
