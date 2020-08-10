package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzdz;
import com.google.android.gms.internal.firebase_auth.zzeb;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfd;

public final class zzdm {
    private final Logger zzjt;
    private final zzdu zzor;

    public zzdm(zzdu zzdu, Logger logger) {
        this.zzor = (zzdu) Preconditions.checkNotNull(zzdu);
        this.zzjt = (Logger) Preconditions.checkNotNull(logger);
    }

    public final void zzb(zzes zzes) {
        try {
            this.zzor.zzb(zzes);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending token result.", e, new Object[0]);
        }
    }

    public final void zza(zzes zzes, zzem zzem) {
        try {
            this.zzor.zza(zzes, zzem);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending get token and account info user response", e, new Object[0]);
        }
    }

    public final void zza(zzec zzec) {
        try {
            this.zzor.zza(zzec);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending create auth uri response.", e, new Object[0]);
        }
    }

    public final void zza(zzfd zzfd) {
        try {
            this.zzor.zza(zzfd);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending password reset response.", e, new Object[0]);
        }
    }

    public final void zzdy() {
        try {
            this.zzor.zzdy();
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending delete account response.", e, new Object[0]);
        }
    }

    public final void zzdz() {
        try {
            this.zzor.zzdz();
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending email verification response.", e, new Object[0]);
        }
    }

    public final void zzby(String str) {
        try {
            this.zzor.zzby(str);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending set account info response.", e, new Object[0]);
        }
    }

    public final void onFailure(Status status) {
        try {
            this.zzor.onFailure(status);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending failure result.", e, new Object[0]);
        }
    }

    public final void zzea() {
        try {
            this.zzor.zzea();
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when setting FirebaseUI Version", e, new Object[0]);
        }
    }

    public final void zza(zzdz zzdz) {
        try {
            this.zzor.zza(zzdz);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending failure result with credential", e, new Object[0]);
        }
    }

    public final void zza(zzeb zzeb) {
        try {
            this.zzor.zza(zzeb);
        } catch (RemoteException e) {
            this.zzjt.mo26591e("RemoteException when sending failure result for mfa", e, new Object[0]);
        }
    }
}
