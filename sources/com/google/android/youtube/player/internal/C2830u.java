package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.u */
public interface C2830u extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.u$a */
    public static abstract class C2831a extends Binder implements C2830u {

        /* renamed from: com.google.android.youtube.player.internal.u$a$a */
        private static class C2832a implements C2830u {

            /* renamed from: a */
            private IBinder f1734a;

            C2832a(IBinder iBinder) {
                this.f1734a = iBinder;
            }

            public final IBinder asBinder() {
                return this.f1734a;
            }
        }

        public C2831a() {
            attachInterface(this, "com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
        }

        /* renamed from: a */
        public static C2830u m1738a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C2830u)) ? new C2832a(iBinder) : (C2830u) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
            return true;
        }
    }
}
