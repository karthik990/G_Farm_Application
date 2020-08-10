package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.g */
public interface C2789g extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.g$a */
    public static abstract class C2790a extends Binder implements C2789g {

        /* renamed from: com.google.android.youtube.player.internal.g$a$a */
        private static class C2791a implements C2789g {

            /* renamed from: a */
            private IBinder f1667a;

            C2791a(IBinder iBinder) {
                this.f1667a = iBinder;
            }

            /* renamed from: a */
            public final void mo38122a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f1667a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38123a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    obtain.writeString(str);
                    this.f1667a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1667a;
            }

            /* renamed from: b */
            public final void mo38124b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f1667a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38125b(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    obtain.writeString(str);
                    this.f1667a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38126c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f1667a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo38127d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f1667a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C2790a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlayerStateChangeListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IPlayerStateChangeListener";
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(str);
                        mo38122a();
                        break;
                    case 2:
                        parcel.enforceInterface(str);
                        mo38123a(parcel.readString());
                        break;
                    case 3:
                        parcel.enforceInterface(str);
                        mo38124b();
                        break;
                    case 4:
                        parcel.enforceInterface(str);
                        mo38126c();
                        break;
                    case 5:
                        parcel.enforceInterface(str);
                        mo38127d();
                        break;
                    case 6:
                        parcel.enforceInterface(str);
                        mo38125b(parcel.readString());
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
    void mo38122a() throws RemoteException;

    /* renamed from: a */
    void mo38123a(String str) throws RemoteException;

    /* renamed from: b */
    void mo38124b() throws RemoteException;

    /* renamed from: b */
    void mo38125b(String str) throws RemoteException;

    /* renamed from: c */
    void mo38126c() throws RemoteException;

    /* renamed from: d */
    void mo38127d() throws RemoteException;
}
