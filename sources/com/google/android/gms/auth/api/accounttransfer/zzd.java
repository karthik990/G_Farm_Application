package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzaf;
import com.google.android.gms.internal.auth.zzx;
import com.google.android.gms.internal.auth.zzz;

final class zzd extends zzc {
    private final /* synthetic */ zzaf zzao;

    zzd(AccountTransferClient accountTransferClient, zzaf zzaf) {
        this.zzao = zzaf;
        super(null);
    }

    /* access modifiers changed from: protected */
    public final void zza(zzz zzz) throws RemoteException {
        zzz.zza((zzx) this.zzax, this.zzao);
    }
}
