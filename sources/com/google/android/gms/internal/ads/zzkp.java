package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.ads.formats.PublisherAdViewOptions;

public final class zzkp extends zzej implements zzkn {
    zzkp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.client.IAdLoaderBuilder");
    }

    public final void zza(PublisherAdViewOptions publisherAdViewOptions) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) publisherAdViewOptions);
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzpl zzpl) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzpl);
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzqw zzqw) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzqw);
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzqz zzqz) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzqz);
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzri zzri, zzjn zzjn) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzri);
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjn);
        transactAndReadExceptionReturnVoid(8, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzrl zzrl) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzrl);
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
    }

    public final void zza(String str, zzrf zzrf, zzrc zzrc) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzrf);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzrc);
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzkh zzkh) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzkh);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzlg zzlg) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzlg);
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken);
    }

    public final zzkk zzdh() throws RemoteException {
        zzkk zzkk;
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken());
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzkk = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdLoader");
            zzkk = queryLocalInterface instanceof zzkk ? (zzkk) queryLocalInterface : new zzkm(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzkk;
    }
}
