package com.google.firebase.auth;

public final class FirebaseAuthUserCollisionException extends FirebaseAuthException {
    private String zzhy;
    private String zzif;
    private AuthCredential zzje;

    public FirebaseAuthUserCollisionException(String str, String str2) {
        super(str, str2);
    }

    public final FirebaseAuthUserCollisionException zzbt(String str) {
        this.zzif = str;
        return this;
    }

    public final FirebaseAuthUserCollisionException zza(AuthCredential authCredential) {
        this.zzje = authCredential;
        return this;
    }

    public final FirebaseAuthUserCollisionException zzbu(String str) {
        this.zzhy = str;
        return this;
    }

    public final AuthCredential getUpdatedCredential() {
        return this.zzje;
    }
}
