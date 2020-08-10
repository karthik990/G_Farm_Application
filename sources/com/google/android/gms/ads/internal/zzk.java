package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;

final class zzk implements Runnable {
    private final /* synthetic */ zzaji zzwg;
    private final /* synthetic */ zzi zzwm;

    zzk(zzi zzi, zzaji zzaji) {
        this.zzwm = zzi;
        this.zzwg = zzaji;
    }

    public final void run() {
        zzi zzi = this.zzwm;
        zzajh zzajh = new zzajh(this.zzwg, null, null, null, null, null, null, null);
        zzi.zzb(zzajh);
    }
}
