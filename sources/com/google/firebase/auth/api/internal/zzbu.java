package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbu implements RemoteCall {
    private final zzbv zznm;

    zzbu(zzbv zzbv) {
        this.zznm = zzbv;
    }

    public final void accept(Object obj, Object obj2) {
        this.zznm.zzn((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
