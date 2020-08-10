package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbc implements RemoteCall {
    private final zzbd zzmz;

    zzbc(zzbd zzbd) {
        this.zzmz = zzbd;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzmz.zze((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
