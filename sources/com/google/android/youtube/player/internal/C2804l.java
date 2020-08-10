package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.youtube.player.internal.C2801k.C2802a;

/* renamed from: com.google.android.youtube.player.internal.l */
public interface C2804l extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.l$a */
    public static abstract class C2805a extends Binder implements C2804l {

        /* renamed from: com.google.android.youtube.player.internal.l$a$a */
        private static class C2806a implements C2804l {

            /* renamed from: a */
            private IBinder f1672a;

            C2806a(IBinder iBinder) {
                this.f1672a = iBinder;
            }

            /* renamed from: a */
            public final IBinder mo38153a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    this.f1672a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final C2801k mo38154a(C2798j jVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    obtain.writeStrongBinder(jVar != null ? jVar.asBinder() : null);
                    this.f1672a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return C2802a.m1637a(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38155a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1672a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1672a;
            }
        }

        /* renamed from: a */
        public static C2804l m1647a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IYouTubeService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C2804l)) ? new C2806a(iBinder) : (C2804l) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            C2798j jVar;
            String str = "com.google.android.youtube.player.internal.IYouTubeService";
            if (i == 1) {
                parcel.enforceInterface(str);
                IBinder a = mo38153a();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(a);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(str);
                IBinder readStrongBinder = parcel.readStrongBinder();
                IBinder iBinder = null;
                if (readStrongBinder == null) {
                    jVar = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                    jVar = (queryLocalInterface == null || !(queryLocalInterface instanceof C2798j)) ? new C2800a(readStrongBinder) : (C2798j) queryLocalInterface;
                }
                C2801k a2 = mo38154a(jVar);
                parcel2.writeNoException();
                if (a2 != null) {
                    iBinder = a2.asBinder();
                }
                parcel2.writeStrongBinder(iBinder);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(str);
                mo38155a(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(str);
                return true;
            }
        }
    }

    /* renamed from: a */
    IBinder mo38153a() throws RemoteException;

    /* renamed from: a */
    C2801k mo38154a(C2798j jVar) throws RemoteException;

    /* renamed from: a */
    void mo38155a(boolean z) throws RemoteException;
}
