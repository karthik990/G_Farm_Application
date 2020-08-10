package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzco implements RemoteCall {
    private final zzcp zzod;

    zzco(zzcp zzcp) {
        this.zzod = zzcp;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzod.zzw((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
