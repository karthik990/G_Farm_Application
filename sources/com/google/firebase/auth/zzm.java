package com.google.firebase.auth;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

final class zzm extends OnVerificationStateChangedCallbacks {
    private final /* synthetic */ FirebaseAuth zziy;
    private final /* synthetic */ OnVerificationStateChangedCallbacks zzjb;

    zzm(FirebaseAuth firebaseAuth, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        this.zziy = firebaseAuth;
        this.zzjb = onVerificationStateChangedCallbacks;
    }

    public final void onCodeAutoRetrievalTimeOut(String str) {
    }

    public final void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        this.zzjb.onVerificationCompleted(phoneAuthCredential);
    }

    public final void onCodeSent(String str, ForceResendingToken forceResendingToken) {
        this.zzjb.onVerificationCompleted(PhoneAuthProvider.getCredential(str, this.zziy.zziq.getSmsCode()));
    }

    public final void onVerificationFailed(FirebaseException firebaseException) {
        this.zzjb.onVerificationFailed(firebaseException);
    }
}
