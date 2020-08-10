package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbi implements RemoteCall {
    private final zzbj zzne;

    zzbi(zzbj zzbj) {
        this.zzne = zzbj;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzne.zzh((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
