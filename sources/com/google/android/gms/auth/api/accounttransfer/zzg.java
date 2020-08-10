package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzv;
import com.google.android.gms.internal.auth.zzx;
import com.google.android.gms.internal.auth.zzz;

final class zzg extends zzb<DeviceMetaData> {
    private final /* synthetic */ zzv zzar;

    zzg(AccountTransferClient accountTransferClient, zzv zzv) {
        this.zzar = zzv;
        super(null);
    }

    /* access modifiers changed from: protected */
    public final void zza(zzz zzz) throws RemoteException {
        zzz.zza((zzx) new zzh(this, this), this.zzar);
    }
}
