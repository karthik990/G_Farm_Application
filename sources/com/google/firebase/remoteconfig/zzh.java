package com.google.firebase.remoteconfig;

import com.google.firebase.auth.FirebaseAuthProvider;
import java.util.concurrent.Callable;

final /* synthetic */ class zzh implements Callable {
    private final zzg zzjv;

    zzh(zzg zzg) {
        this.zzjv = zzg;
    }

    public final Object call() {
        return this.zzjv.zzbd(FirebaseAuthProvider.PROVIDER_ID);
    }
}
