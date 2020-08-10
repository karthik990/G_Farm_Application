package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

final class zzk implements Runnable {
    private final /* synthetic */ FirebaseAuth zziy;

    zzk(FirebaseAuth firebaseAuth) {
        this.zziy = firebaseAuth;
    }

    public final void run() {
        for (AuthStateListener onAuthStateChanged : this.zziy.zzin) {
            onAuthStateChanged.onAuthStateChanged(this.zziy);
        }
    }
}
