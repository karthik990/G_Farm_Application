package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

final class zzet implements zzev {
    private final /* synthetic */ Status zzqr;

    zzet(zzep zzep, Status status) {
        this.zzqr = status;
    }

    public final void zza(OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, Object... objArr) {
        onVerificationStateChangedCallbacks.onVerificationFailed(zzdr.zzb(this.zzqr));
    }
}
