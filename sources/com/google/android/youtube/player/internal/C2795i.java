package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.i */
public interface C2795i extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.i$a */
    public static abstract class C2796a extends Binder implements C2795i {

        /* renamed from: com.google.android.youtube.player.internal.i$a$a */
        private static class C2797a implements C2795i {

            /* renamed from: a */
            private IBinder f1669a;

            C2797a(IBinder iBinder) {
                this.f1669a = iBinder;
            }

            /* renamed from: a */
            public final void mo38137a(C2777c cVar, int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IServiceBroker");
                    obtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f1669a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1669a;
            }
        }

        /* renamed from: a */
        public static C2795i m1625a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IServiceBroker");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C2795i)) ? new C2797a(iBinder) : (C2795i) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            C2777c cVar;
            String str = "com.google.android.youtube.player.internal.IServiceBroker";
            if (i == 1) {
                parcel.enforceInterface(str);
                IBinder readStrongBinder = parcel.readStrongBinder();
                Bundle bundle = null;
                if (readStrongBinder == null) {
                    cVar = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.youtube.player.internal.IConnectionCallbacks");
                    cVar = (queryLocalInterface == null || !(queryLocalInterface instanceof C2777c)) ? new C2779a(readStrongBinder) : (C2777c) queryLocalInterface;
                }
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                mo38137a(cVar, readInt, readString, readString2, readString3, bundle);
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
    void mo38137a(C2777c cVar, int i, String str, String str2, String str3, Bundle bundle) throws RemoteException;
}
