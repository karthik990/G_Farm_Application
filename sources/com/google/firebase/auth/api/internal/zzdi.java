package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzdi implements RemoteCall {
    private final zzdj zzoo;

    zzdi(zzdj zzdj) {
        this.zzoo = zzdj;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzoo.zzaf((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
