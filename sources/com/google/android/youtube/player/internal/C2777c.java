package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.c */
public interface C2777c extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.c$a */
    public static abstract class C2778a extends Binder implements C2777c {

        /* renamed from: com.google.android.youtube.player.internal.c$a$a */
        private static class C2779a implements C2777c {

            /* renamed from: a */
            private IBinder f1663a;

            C2779a(IBinder iBinder) {
                this.f1663a = iBinder;
            }

            /* renamed from: a */
            public final void mo38061a(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IConnectionCallbacks");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.f1663a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1663a;
            }
        }

        public C2778a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IConnectionCallbacks");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IConnectionCallbacks";
            if (i == 1) {
                parcel.enforceInterface(str);
                mo38061a(parcel.readString(), parcel.readStrongBinder());
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
    void mo38061a(String str, IBinder iBinder) throws RemoteException;
}
