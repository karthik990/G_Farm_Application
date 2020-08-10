package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;

final class zzx implements OnFailureListener {
    private final /* synthetic */ zzy zzua;

    zzx(zzy zzy) {
        this.zzua = zzy;
    }

    public final void onFailure(Exception exc) {
        if (exc instanceof FirebaseNetworkException) {
            zzv.zzjt.mo26595v("Failure to refresh token; scheduling refresh after failure", new Object[0]);
            this.zzua.zzub.zzfi();
        }
    }
}
