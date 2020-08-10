package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzbm implements RemoteCall {
    private final zzbn zzng;

    zzbm(zzbn zzbn) {
        this.zzng = zzbn;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzng.zzj((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
