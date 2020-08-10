package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzej implements SuccessContinuation {
    private final zzeh zzkd;
    private final boolean zzkf;
    private final zzeo zzkg;

    zzej(zzeh zzeh, boolean z, zzeo zzeo) {
        this.zzkd = zzeh;
        this.zzkf = z;
        this.zzkg = zzeo;
    }

    public final Task then(Object obj) {
        return this.zzkd.zza(this.zzkf, this.zzkg, (Void) obj);
    }
}
