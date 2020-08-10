package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcy implements RemoteCall {
    private final zzcz zzoj;

    zzcy(zzcz zzcz) {
        this.zzoj = zzcz;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzoj.zzaa((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
