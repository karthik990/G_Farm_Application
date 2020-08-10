package com.google.android.gms.internal.auth;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.AuthProxy;
import com.google.android.gms.auth.api.proxy.ProxyApi.SpatulaHeaderResult;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

abstract class zzaq extends ApiMethodImpl<SpatulaHeaderResult, zzak> {
    public zzaq(GoogleApiClient googleApiClient) {
        super(AuthProxy.API, googleApiClient);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Context context, zzan zzan) throws RemoteException;

    protected static SpatulaHeaderResult zzc(Status status) {
        return new zzax(status);
    }

    /* access modifiers changed from: protected */
    public /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zzak zzak = (zzak) anyClient;
        zza(zzak.getContext(), (zzan) zzak.getService());
    }

    /* access modifiers changed from: protected */
    public /* synthetic */ Result createFailedResult(Status status) {
        return zzc(status);
    }
}
