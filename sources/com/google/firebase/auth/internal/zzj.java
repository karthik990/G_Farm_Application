package com.google.firebase.auth.internal;

import com.google.firebase.auth.FirebaseAuthSettings;

public final class zzj extends FirebaseAuthSettings {
    private String zzjm;
    private String zzjo;

    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final String getSmsCode() {
        return this.zzjm;
    }

    public final boolean zzfe() {
        return (this.zzjo == null || this.zzjm == null) ? false : true;
    }

    public final void setAutoRetrievedSmsCodeForPhoneNumber(String str, String str2) {
        this.zzjo = str;
        this.zzjm = str2;
    }
}
