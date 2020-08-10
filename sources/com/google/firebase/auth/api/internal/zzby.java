package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzby implements RemoteCall {
    private final zzbz zznp;

    zzby(zzbz zzbz) {
        this.zznp = zzbz;
    }

    public final void accept(Object obj, Object obj2) {
        this.zznp.zzp((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
