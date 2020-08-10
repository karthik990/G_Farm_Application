package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzop;

final class zzez implements Runnable {
    private final /* synthetic */ zzex zzajn;
    private final /* synthetic */ zzop zzajo;

    zzez(zzex zzex, zzop zzop) {
        this.zzajn = zzex;
        this.zzajo = zzop;
    }

    public final void run() {
        this.zzajn.zzb(this.zzajo);
    }
}
