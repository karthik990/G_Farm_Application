package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;

final class zzae implements OnSuccessListener<AuthResult> {
    private final /* synthetic */ TaskCompletionSource zzuc;

    zzae(zzac zzac, TaskCompletionSource taskCompletionSource) {
        this.zzuc = taskCompletionSource;
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzuc.setResult((AuthResult) obj);
        zzac.zzfl();
    }
}
