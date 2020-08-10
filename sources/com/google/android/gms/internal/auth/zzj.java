package com.google.android.gms.internal.auth;

import android.os.RemoteException;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.auth.account.zza;
import com.google.android.gms.auth.account.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

final class zzj extends ApiMethodImpl<AddAccountResult, zzr> {
    private final /* synthetic */ String zzq;

    zzj(zzh zzh, Api api, GoogleApiClient googleApiClient, String str) {
        this.zzq = str;
        super(api, googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        ((zzc) ((zzr) anyClient).getService()).zza((zza) new zzk(this), this.zzq);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzo(status, null);
    }
}
