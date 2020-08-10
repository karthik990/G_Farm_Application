package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.h */
public interface C2792h extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.h$a */
    public static abstract class C2793a extends Binder implements C2792h {

        /* renamed from: com.google.android.youtube.player.internal.h$a$a */
        private static class C2794a implements C2792h {

            /* renamed from: a */
            private IBinder f1668a;

            C2794a(IBinder iBinder) {
                this.f1668a = iBinder;
            }

            /* renamed from: a */
            public final void mo38131a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f1668a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1668a;
            }

            /* renamed from: b */
            public final void mo38132b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f1668a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38133c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f1668a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C2793a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlaylistEventListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IPlaylistEventListener";
            if (i == 1) {
                parcel.enforceInterface(str);
                mo38131a();
            } else if (i == 2) {
                parcel.enforceInterface(str);
                mo38132b();
            } else if (i == 3) {
                parcel.enforceInterface(str);
                mo38133c();
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
    void mo38131a() throws RemoteException;

    /* renamed from: b */
    void mo38132b() throws RemoteException;

    /* renamed from: c */
    void mo38133c() throws RemoteException;
}
