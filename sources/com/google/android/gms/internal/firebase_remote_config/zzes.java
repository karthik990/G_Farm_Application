package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzes implements Continuation {
    private final boolean zzkf;
    private final zzer zzkz;
    private final long zzla;

    zzes(zzer zzer, boolean z, long j) {
        this.zzkz = zzer;
        this.zzkf = z;
        this.zzla = j;
    }

    public final Object then(Task task) {
        return this.zzkz.zza(this.zzkf, this.zzla, task);
    }
}
