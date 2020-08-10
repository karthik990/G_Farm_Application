package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth.IdTokenListener;

final class zzj implements Runnable {
    private final /* synthetic */ FirebaseAuth zziy;
    private final /* synthetic */ IdTokenListener zziz;

    zzj(FirebaseAuth firebaseAuth, IdTokenListener idTokenListener) {
        this.zziy = firebaseAuth;
        this.zziz = idTokenListener;
    }

    public final void run() {
        this.zziz.onIdTokenChanged(this.zziy);
    }
}
