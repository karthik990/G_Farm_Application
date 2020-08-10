package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

public final class zzku extends zzej implements zzks {
    zzku(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.client.IAdManager");
    }

    public final void destroy() throws RemoteException {
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken());
    }

    public final String getAdUnitId() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(31, obtainAndWriteInterfaceToken());
        String readString = transactAndReadException.readString();
        transactAndReadException.recycle();
        return readString;
    }

    public final String getMediationAdapterClassName() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(18, obtainAndWriteInterfaceToken());
        String readString = transactAndReadException.readString();
        transactAndReadException.recycle();
        return readString;
    }

    public final zzlo getVideoController() throws RemoteException {
        zzlo zzlo;
        Parcel transactAndReadException = transactAndReadException(26, obtainAndWriteInterfaceToken());
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzlo = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IVideoController");
            zzlo = queryLocalInterface instanceof zzlo ? (zzlo) queryLocalInterface : new zzlq(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzlo;
    }

    public final boolean isLoading() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(23, obtainAndWriteInterfaceToken());
        boolean zza = zzel.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final boolean isReady() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(3, obtainAndWriteInterfaceToken());
        boolean zza = zzel.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final void pause() throws RemoteException {
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken());
    }

    public final void resume() throws RemoteException {
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken());
    }

    public final void setImmersiveMode(boolean z) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(34, obtainAndWriteInterfaceToken);
    }

    public final void setManualImpressionsEnabled(boolean z) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(22, obtainAndWriteInterfaceToken);
    }

    public final void setUserId(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(25, obtainAndWriteInterfaceToken);
    }

    public final void showInterstitial() throws RemoteException {
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken());
    }

    public final void stopLoading() throws RemoteException {
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken());
    }

    public final void zza(zzaaw zzaaw) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzaaw);
        transactAndReadExceptionReturnVoid(14, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzabc zzabc, String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzabc);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(15, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzahe zzahe) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzahe);
        transactAndReadExceptionReturnVoid(24, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzjn zzjn) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjn);
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzke zzke) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzke);
        transactAndReadExceptionReturnVoid(20, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzkh zzkh) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzkh);
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzkx zzkx) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzkx);
        transactAndReadExceptionReturnVoid(36, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzla zzla) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzla);
        transactAndReadExceptionReturnVoid(8, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzlg zzlg) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzlg);
        transactAndReadExceptionReturnVoid(21, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzlu zzlu) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzlu);
        transactAndReadExceptionReturnVoid(30, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzmu zzmu) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzmu);
        transactAndReadExceptionReturnVoid(29, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzod zzod) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzod);
        transactAndReadExceptionReturnVoid(19, obtainAndWriteInterfaceToken);
    }

    public final boolean zzb(zzjj zzjj) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjj);
        Parcel transactAndReadException = transactAndReadException(4, obtainAndWriteInterfaceToken);
        boolean zza = zzel.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final Bundle zzba() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(37, obtainAndWriteInterfaceToken());
        Bundle bundle = (Bundle) zzel.zza(transactAndReadException, Bundle.CREATOR);
        transactAndReadException.recycle();
        return bundle;
    }

    public final IObjectWrapper zzbj() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken());
        IObjectWrapper asInterface = Stub.asInterface(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return asInterface;
    }

    public final zzjn zzbk() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(12, obtainAndWriteInterfaceToken());
        zzjn zzjn = (zzjn) zzel.zza(transactAndReadException, zzjn.CREATOR);
        transactAndReadException.recycle();
        return zzjn;
    }

    public final void zzbm() throws RemoteException {
        transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken());
    }

    public final zzla zzbw() throws RemoteException {
        zzla zzla;
        Parcel transactAndReadException = transactAndReadException(32, obtainAndWriteInterfaceToken());
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzla = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAppEventListener");
            zzla = queryLocalInterface instanceof zzla ? (zzla) queryLocalInterface : new zzlc(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzla;
    }

    public final zzkh zzbx() throws RemoteException {
        zzkh zzkh;
        Parcel transactAndReadException = transactAndReadException(33, obtainAndWriteInterfaceToken());
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzkh = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdListener");
            zzkh = queryLocalInterface instanceof zzkh ? (zzkh) queryLocalInterface : new zzkj(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzkh;
    }

    public final String zzck() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(35, obtainAndWriteInterfaceToken());
        String readString = transactAndReadException.readString();
        transactAndReadException.recycle();
        return readString;
    }
}
