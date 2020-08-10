package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.f */
public interface C2786f extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.f$a */
    public static abstract class C2787a extends Binder implements C2786f {

        /* renamed from: com.google.android.youtube.player.internal.f$a$a */
        private static class C2788a implements C2786f {

            /* renamed from: a */
            private IBinder f1666a;

            C2788a(IBinder iBinder) {
                this.f1666a = iBinder;
            }

            /* renamed from: a */
            public final void mo38114a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f1666a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38115a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    obtain.writeInt(i);
                    this.f1666a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38116a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1666a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1666a;
            }

            /* renamed from: b */
            public final void mo38117b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f1666a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38118c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f1666a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C2787a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlaybackEventListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IPlaybackEventListener";
            if (i == 1) {
                parcel.enforceInterface(str);
                mo38114a();
            } else if (i == 2) {
                parcel.enforceInterface(str);
                mo38117b();
            } else if (i == 3) {
                parcel.enforceInterface(str);
                mo38118c();
            } else if (i == 4) {
                parcel.enforceInterface(str);
                mo38116a(parcel.readInt() != 0);
            } else if (i == 5) {
                parcel.enforceInterface(str);
                mo38115a(parcel.readInt());
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(str);
                return true;
            }
            parcel2.writeNoException();
            return true;
        }
    }

    /* renamed from: a */
    void mo38114a() throws RemoteException;

    /* renamed from: a */
    void mo38115a(int i) throws RemoteException;

    /* renamed from: a */
    void mo38116a(boolean z) throws RemoteException;

    /* renamed from: b */
    void mo38117b() throws RemoteException;

    /* renamed from: c */
    void mo38118c() throws RemoteException;
}
