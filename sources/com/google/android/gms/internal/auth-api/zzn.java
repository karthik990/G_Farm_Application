package com.google.android.gms.internal.auth-api;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzn extends zzp<Status> {
    zzn(zzi zzi, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    /* access modifiers changed from: protected */
    public final void zzc(Context context, zzw zzw) throws RemoteException {
        zzw.zzc(new zzo(this));
    }
}
