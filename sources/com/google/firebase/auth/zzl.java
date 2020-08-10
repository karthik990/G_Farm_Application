package com.google.firebase.auth;

import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;

final class zzl implements Runnable {
    private final /* synthetic */ FirebaseAuth zziy;
    private final /* synthetic */ InternalTokenResult zzja;

    zzl(FirebaseAuth firebaseAuth, InternalTokenResult internalTokenResult) {
        this.zziy = firebaseAuth;
        this.zzja = internalTokenResult;
    }

    public final void run() {
        for (IdTokenListener onIdTokenChanged : this.zziy.zzim) {
            onIdTokenChanged.onIdTokenChanged(this.zzja);
        }
        for (FirebaseAuth.IdTokenListener onIdTokenChanged2 : this.zziy.zzil) {
            onIdTokenChanged2.onIdTokenChanged(this.zziy);
        }
    }
}
