package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcq implements RemoteCall {
    private final zzcr zzof;

    zzcq(zzcr zzcr) {
        this.zzof = zzcr;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzof.zzx((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
