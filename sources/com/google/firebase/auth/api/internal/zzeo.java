package com.google.firebase.auth.api.internal;

import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

final class zzeo implements zzev {
    private final /* synthetic */ String zzqm;

    zzeo(zzep zzep, String str) {
        this.zzqm = str;
    }

    public final void zza(OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onCodeSent(this.zzqm, ForceResendingToken.zzdd());
    }
}
