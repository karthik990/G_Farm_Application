package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzxu extends zzek implements zzxt {
    public zzxu() {
        super("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
    }

    public static zzxt zzs(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
        return queryLocalInterface instanceof zzxt ? (zzxt) queryLocalInterface : new zzxv(iBinder);
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzxw zzxw;
        switch (i) {
            case 1:
                onAdClicked();
                break;
            case 2:
                onAdClosed();
                break;
            case 3:
                onAdFailedToLoad(parcel.readInt());
                break;
            case 4:
                onAdLeftApplication();
                break;
            case 5:
                onAdOpened();
                break;
            case 6:
                onAdLoaded();
                break;
            case 7:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzxw = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IMediationResponseMetadata");
                    zzxw = queryLocalInterface instanceof zzxw ? (zzxw) queryLocalInterface : new zzxy(readStrongBinder);
                }
                zza(zzxw);
                break;
            case 8:
                onAdImpression();
                break;
            case 9:
                onAppEvent(parcel.readString(), parcel.readString());
                break;
            case 10:
                zzb(zzqt.zzk(parcel.readStrongBinder()), parcel.readString());
                break;
            case 11:
                onVideoEnd();
                break;
            case 12:
                zzbj(parcel.readString());
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
