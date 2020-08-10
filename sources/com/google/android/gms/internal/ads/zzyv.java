package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.ads.AdRequest.ErrorCode;

final class zzyv implements Runnable {
    private final /* synthetic */ zzyq zzbvd;
    private final /* synthetic */ ErrorCode zzbve;

    zzyv(zzyq zzyq, ErrorCode errorCode) {
        this.zzbvd = zzyq;
        this.zzbve = errorCode;
    }

    public final void run() {
        try {
            this.zzbvd.zzbuu.onAdFailedToLoad(zzzc.zza(this.zzbve));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }
}
