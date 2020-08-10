package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzab;
import com.google.android.gms.internal.auth.zzx;
import com.google.android.gms.internal.auth.zzz;

final class zzj extends zzc {
    private final /* synthetic */ zzab zzau;

    zzj(AccountTransferClient accountTransferClient, zzab zzab) {
        this.zzau = zzab;
        super(null);
    }

    /* access modifiers changed from: protected */
    public final void zza(zzz zzz) throws RemoteException {
        zzz.zza((zzx) this.zzax, this.zzau);
    }
}
