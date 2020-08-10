package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

final class zzi implements Runnable {
    private final /* synthetic */ AuthStateListener zzix;
    private final /* synthetic */ FirebaseAuth zziy;

    zzi(FirebaseAuth firebaseAuth, AuthStateListener authStateListener) {
        this.zziy = firebaseAuth;
        this.zzix = authStateListener;
    }

    public final void run() {
        this.zzix.onAuthStateChanged(this.zziy);
    }
}
