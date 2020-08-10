package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcm implements RemoteCall {
    private final zzcn zzoc;

    zzcm(zzcn zzcn) {
        this.zzoc = zzcn;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzoc.zzv((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
