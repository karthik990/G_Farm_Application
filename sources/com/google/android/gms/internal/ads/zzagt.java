package com.google.android.gms.internal.ads;

final class zzagt implements Runnable {
    private final /* synthetic */ zzagr zzclh;
    private final /* synthetic */ zzaji zzwg;

    zzagt(zzagr zzagr, zzaji zzaji) {
        this.zzclh = zzagr;
        this.zzwg = zzaji;
    }

    public final void run() {
        zzagr zzagr = this.zzclh;
        zzajh zzajh = new zzajh(this.zzwg, null, null, null, null, null, null, null);
        zzagr.zzb(zzajh);
    }
}
