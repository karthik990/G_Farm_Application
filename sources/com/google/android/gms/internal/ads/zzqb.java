package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

public abstract class zzqb extends zzek implements zzqa {
    public zzqb() {
        super("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegate");
    }

    public static zzqa zzi(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegate");
        return queryLocalInterface instanceof zzqa ? (zzqa) queryLocalInterface : new zzqc(iBinder);
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zzb(parcel.readString(), Stub.asInterface(parcel.readStrongBinder()));
                break;
            case 2:
                IObjectWrapper zzak = zzak(parcel.readString());
                parcel2.writeNoException();
                zzel.zza(parcel2, (IInterface) zzak);
                break;
            case 3:
                zza(Stub.asInterface(parcel.readStrongBinder()));
                break;
            case 4:
                destroy();
                break;
            case 5:
                zzb(Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                break;
            case 6:
                zzc(Stub.asInterface(parcel.readStrongBinder()));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
