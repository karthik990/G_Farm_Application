package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzda implements RemoteCall {
    private final zzdb zzok;

    zzda(zzdb zzdb) {
        this.zzok = zzdb;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzok.zzab((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
