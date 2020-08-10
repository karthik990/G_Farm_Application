package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzlf extends zzej implements zzld {
    zzlf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.client.IClientApi");
    }

    public final zzkn createAdLoaderBuilder(IObjectWrapper iObjectWrapper, String str, zzxn zzxn, int i) throws RemoteException {
        zzkn zzkn;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        obtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzxn);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(3, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzkn = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdLoaderBuilder");
            zzkn = queryLocalInterface instanceof zzkn ? (zzkn) queryLocalInterface : new zzkp(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzkn;
    }

    public final zzaap createAdOverlay(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        Parcel transactAndReadException = transactAndReadException(8, obtainAndWriteInterfaceToken);
        zzaap zzu = zzaaq.zzu(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return zzu;
    }

    public final zzks createBannerAdManager(IObjectWrapper iObjectWrapper, zzjn zzjn, String str, zzxn zzxn, int i) throws RemoteException {
        zzks zzks;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjn);
        obtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzxn);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzks = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzks = queryLocalInterface instanceof zzks ? (zzks) queryLocalInterface : new zzku(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzks;
    }

    public final zzaaz createInAppPurchaseManager(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        Parcel transactAndReadException = transactAndReadException(7, obtainAndWriteInterfaceToken);
        zzaaz zzw = zzaba.zzw(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return zzw;
    }

    public final zzks createInterstitialAdManager(IObjectWrapper iObjectWrapper, zzjn zzjn, String str, zzxn zzxn, int i) throws RemoteException {
        zzks zzks;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjn);
        obtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzxn);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzks = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzks = queryLocalInterface instanceof zzks ? (zzks) queryLocalInterface : new zzku(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzks;
    }

    public final zzqa createNativeAdViewDelegate(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
        Parcel transactAndReadException = transactAndReadException(5, obtainAndWriteInterfaceToken);
        zzqa zzi = zzqb.zzi(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return zzi;
    }

    public final zzqf createNativeAdViewHolderDelegate(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper3);
        Parcel transactAndReadException = transactAndReadException(11, obtainAndWriteInterfaceToken);
        zzqf zzj = zzqg.zzj(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return zzj;
    }

    public final zzagz createRewardedVideoAd(IObjectWrapper iObjectWrapper, zzxn zzxn, int i) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) zzxn);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(6, obtainAndWriteInterfaceToken);
        zzagz zzy = zzaha.zzy(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return zzy;
    }

    public final zzks createSearchAdManager(IObjectWrapper iObjectWrapper, zzjn zzjn, String str, int i) throws RemoteException {
        zzks zzks;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzel.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjn);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(10, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzks = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzks = queryLocalInterface instanceof zzks ? (zzks) queryLocalInterface : new zzku(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzks;
    }

    public final zzlj getMobileAdsSettingsManager(IObjectWrapper iObjectWrapper) throws RemoteException {
        zzlj zzlj;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        Parcel transactAndReadException = transactAndReadException(4, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzlj = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
            zzlj = queryLocalInterface instanceof zzlj ? (zzlj) queryLocalInterface : new zzll(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzlj;
    }

    public final zzlj getMobileAdsSettingsManagerWithClientJarVersion(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        zzlj zzlj;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        obtainAndWriteInterfaceToken.writeInt(i);
        Parcel transactAndReadException = transactAndReadException(9, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zzlj = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
            zzlj = queryLocalInterface instanceof zzlj ? (zzlj) queryLocalInterface : new zzll(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zzlj;
    }
}
