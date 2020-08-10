package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbw implements RemoteCall {
    private final zzbx zzno;

    zzbw(zzbx zzbx) {
        this.zzno = zzbx;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzno.zzo((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
