package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.e */
public interface C2783e extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.e$a */
    public static abstract class C2784a extends Binder implements C2783e {

        /* renamed from: com.google.android.youtube.player.internal.e$a$a */
        private static class C2785a implements C2783e {

            /* renamed from: a */
            private IBinder f1665a;

            C2785a(IBinder iBinder) {
                this.f1665a = iBinder;
            }

            /* renamed from: a */
            public final void mo38110a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IOnFullscreenListener");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1665a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1665a;
            }
        }

        public C2784a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IOnFullscreenListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = "com.google.android.youtube.player.internal.IOnFullscreenListener";
            if (i == 1) {
                parcel.enforceInterface(str);
                mo38110a(parcel.readInt() != 0);
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
    void mo38110a(boolean z) throws RemoteException;
}
