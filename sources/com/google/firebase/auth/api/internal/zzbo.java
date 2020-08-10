package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbo implements RemoteCall {
    private final zzbp zzni;

    zzbo(zzbp zzbp) {
        this.zzni = zzbp;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzni.zzk((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
