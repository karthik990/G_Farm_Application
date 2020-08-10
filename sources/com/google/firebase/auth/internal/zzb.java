package com.google.firebase.auth.internal;

import android.text.TextUtils;
import com.google.android.gms.internal.firebase_auth.zzfd;
import com.google.firebase.auth.ActionCodeResult;

public final class zzb implements ActionCodeResult {
    private final String zzif;
    private final int zzta;
    private final String zztb;

    public zzb(zzfd zzfd) {
        if (TextUtils.isEmpty(zzfd.zzae())) {
            this.zzif = zzfd.getEmail();
        } else {
            this.zzif = zzfd.zzae();
        }
        this.zztb = zzfd.getEmail();
        if (TextUtils.isEmpty(zzfd.zzey())) {
            this.zzta = 3;
        } else if (zzfd.zzey().equals("PASSWORD_RESET")) {
            this.zzta = 0;
        } else if (zzfd.zzey().equals("VERIFY_EMAIL")) {
            this.zzta = 1;
        } else if (zzfd.zzey().equals("RECOVER_EMAIL")) {
            this.zzta = 2;
        } else if (zzfd.zzey().equals("EMAIL_SIGNIN")) {
            this.zzta = 4;
        } else {
            this.zzta = 3;
        }
    }

    public final int getOperation() {
        return this.zzta;
    }

    public final String getData(int i) {
        if (i != 0) {
            if (i != 1) {
                return null;
            }
            return this.zztb;
        } else if (this.zzta == 4) {
            return null;
        } else {
            return this.zzif;
        }
    }
}
