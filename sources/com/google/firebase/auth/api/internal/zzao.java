package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zzao implements Continuation<ResultT, Task<ResultT>> {
    private final /* synthetic */ zzan zzmj;
    private final /* synthetic */ zzap zzmk;

    zzao(zzap zzap, zzan zzan) {
        this.zzmk = zzap;
        this.zzmj = zzan;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        return task.getException() instanceof UnsupportedApiCallException ? this.zzmk.zza(this.zzmj.zzdw()) : task;
    }
}
