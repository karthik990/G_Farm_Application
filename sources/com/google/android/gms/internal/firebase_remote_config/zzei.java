package com.google.android.gms.internal.firebase_remote_config;

import java.util.concurrent.Callable;

final /* synthetic */ class zzei implements Callable {
    private final zzeh zzkd;
    private final zzeo zzke;

    zzei(zzeh zzeh, zzeo zzeo) {
        this.zzkd = zzeh;
        this.zzke = zzeo;
    }

    public final Object call() {
        return this.zzkd.zze(this.zzke);
    }
}
