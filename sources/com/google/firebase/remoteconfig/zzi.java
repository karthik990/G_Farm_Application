package com.google.firebase.remoteconfig;

import com.google.android.gms.internal.firebase_remote_config.zzfc;
import java.util.concurrent.Callable;

final /* synthetic */ class zzi implements Callable {
    private final zzfc zzjw;

    private zzi(zzfc zzfc) {
        this.zzjw = zzfc;
    }

    static Callable zza(zzfc zzfc) {
        return new zzi(zzfc);
    }

    public final Object call() {
        return Boolean.valueOf(this.zzjw.zzde());
    }
}
