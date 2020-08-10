package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzra extends zzek implements zzqz {
    public zzra() {
        super("com.google.android.gms.ads.internal.formats.client.IOnContentAdLoadedListener");
    }

    public static zzqz zzm(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.IOnContentAdLoadedListener");
        return queryLocalInterface instanceof zzqz ? (zzqz) queryLocalInterface : new zzrb(iBinder);
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzqo zzqo;
        if (i != 1) {
            return false;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            zzqo = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeContentAd");
            zzqo = queryLocalInterface instanceof zzqo ? (zzqo) queryLocalInterface : new zzqq(readStrongBinder);
        }
        zza(zzqo);
        parcel2.writeNoException();
        return true;
    }
}
