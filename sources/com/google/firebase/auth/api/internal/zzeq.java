package com.google.firebase.auth.api.internal;

import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

final class zzeq implements zzev {
    private final /* synthetic */ String zzqm;

    zzeq(zzep zzep, String str) {
        this.zzqm = str;
    }

    public final void zza(OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onCodeAutoRetrievalTimeOut(this.zzqm);
    }
}
