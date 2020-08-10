package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.k */
public interface C2801k extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.k$a */
    public static abstract class C2802a extends Binder implements C2801k {

        /* renamed from: com.google.android.youtube.player.internal.k$a$a */
        private static class C2803a implements C2801k {

            /* renamed from: a */
            private IBinder f1671a;

            C2803a(IBinder iBinder) {
                this.f1671a = iBinder;
            }

            /* renamed from: a */
            public final void mo38145a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f1671a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38146a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    obtain.writeString(str);
                    this.f1671a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38147a(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f1671a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1671a;
            }

            /* renamed from: b */
            public final void mo38148b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f1671a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38149c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f1671a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo38150d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f1671a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C2801k m1637a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C2801k)) ? new C2803a(iBinder) : (C2801k) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IThumbnailLoaderService";
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(str);
                        mo38146a(parcel.readString());
                        break;
                    case 2:
                        parcel.enforceInterface(str);
                        mo38147a(parcel.readString(), parcel.readInt());
                        break;
                    case 3:
                        parcel.enforceInterface(str);
                        mo38145a();
                        break;
                    case 4:
                        parcel.enforceInterface(str);
                        mo38148b();
                        break;
                    case 5:
                        parcel.enforceInterface(str);
                        mo38149c();
                        break;
                    case 6:
                        parcel.enforceInterface(str);
                        mo38150d();
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(str);
            return true;
        }
    }

    /* renamed from: a */
    void mo38145a() throws RemoteException;

    /* renamed from: a */
    void mo38146a(String str) throws RemoteException;

    /* renamed from: a */
    void mo38147a(String str, int i) throws RemoteException;

    /* renamed from: b */
    void mo38148b() throws RemoteException;

    /* renamed from: c */
    void mo38149c() throws RemoteException;

    /* renamed from: d */
    void mo38150d() throws RemoteException;
}
