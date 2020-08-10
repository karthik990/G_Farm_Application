package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbg implements RemoteCall {
    private final zzbh zznc;

    zzbg(zzbh zzbh) {
        this.zznc = zzbh;
    }

    public final void accept(Object obj, Object obj2) {
        this.zznc.zzg((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
