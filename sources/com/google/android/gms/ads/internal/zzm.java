package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzajh;

final class zzm implements Runnable {
    private final /* synthetic */ zzl zzwp;

    zzm(zzl zzl) {
        this.zzwp = zzl;
    }

    public final void run() {
        zzi zzi = this.zzwp.zzwm;
        zzajh zzajh = new zzajh(this.zzwp.zzwg, null, null, null, null, null, null, null);
        zzi.zzb(zzajh);
    }
}
