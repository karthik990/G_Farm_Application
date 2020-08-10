package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzba implements RemoteCall {
    private final zzbb zzmx;

    zzba(zzbb zzbb) {
        this.zzmx = zzbb;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzmx.zzd((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
