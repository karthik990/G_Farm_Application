package com.google.android.gms.internal.firebase_remote_config;

import java.util.concurrent.Callable;

final /* synthetic */ class zzek implements Callable {
    private final zzew zzkh;

    private zzek(zzew zzew) {
        this.zzkh = zzew;
    }

    static Callable zza(zzew zzew) {
        return new zzek(zzew);
    }

    public final Object call() {
        return this.zzkh.zzdb();
    }
}
