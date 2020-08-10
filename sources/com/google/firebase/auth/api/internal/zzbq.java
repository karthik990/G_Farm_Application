package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbq implements RemoteCall {
    private final zzbr zznj;

    zzbq(zzbr zzbr) {
        this.zznj = zzbr;
    }

    public final void accept(Object obj, Object obj2) {
        this.zznj.zzl((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
