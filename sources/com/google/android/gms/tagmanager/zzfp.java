package com.google.android.gms.tagmanager;

final class zzfp implements Runnable {
    private final /* synthetic */ zzfn zzakz;

    zzfp(zzfn zzfn) {
        this.zzakz = zzfn;
    }

    public final void run() {
        this.zzakz.zzakp.dispatch();
    }
}
