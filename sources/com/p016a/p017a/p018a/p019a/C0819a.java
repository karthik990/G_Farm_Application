package com.p016a.p017a.p018a.p019a;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* renamed from: com.a.a.a.a.a */
/* compiled from: StartAppSDK */
public interface C0819a extends IInterface {

    /* renamed from: com.a.a.a.a.a$a */
    /* compiled from: StartAppSDK */
    public static abstract class C0820a extends Binder implements C0819a {

        /* renamed from: com.a.a.a.a.a$a$a */
        /* compiled from: StartAppSDK */
        private static class C0821a implements C0819a {

            /* renamed from: a */
            private IBinder f69a;

            C0821a(IBinder iBinder) {
                this.f69a = iBinder;
            }

            public IBinder asBinder() {
                return this.f69a;
            }

            /* renamed from: a */
            public Bundle mo10126a(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f69a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C0819a m85a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0819a)) {
                return new C0821a(iBinder);
            }
            return (C0819a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = "com.google.android.finsky.externalreferrer.IGetInstallReferrerService";
            if (i == 1) {
                parcel.enforceInterface(str);
                Bundle a = mo10126a(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                if (a != null) {
                    parcel2.writeInt(1);
                    a.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
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
    Bundle mo10126a(Bundle bundle);
}
