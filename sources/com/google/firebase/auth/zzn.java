package com.google.firebase.auth;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzz;

final class zzn implements zza, zzz {
    private final /* synthetic */ FirebaseAuth zziy;

    zzn(FirebaseAuth firebaseAuth) {
        this.zziy = firebaseAuth;
    }

    public final void zza(zzes zzes, FirebaseUser firebaseUser) {
        this.zziy.zza(firebaseUser, zzes, true);
    }

    public final void zza(Status status) {
        int statusCode = status.getStatusCode();
        if (statusCode == 17011 || statusCode == 17021 || statusCode == 17005) {
            this.zziy.signOut();
        }
    }
}
