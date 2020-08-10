package com.google.android.youtube.player.internal;

import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.google.android.youtube.player.internal.C2830u.C2831a;
import java.util.List;

/* renamed from: com.google.android.youtube.player.internal.d */
public interface C2780d extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.d$a */
    public static abstract class C2781a extends Binder implements C2780d {

        /* renamed from: com.google.android.youtube.player.internal.d$a$a */
        private static class C2782a implements C2780d {

            /* renamed from: a */
            private IBinder f1664a;

            C2782a(IBinder iBinder) {
                this.f1664a = iBinder;
            }

            /* renamed from: a */
            public final void mo38065a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38066a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f1664a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38067a(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    if (configuration != null) {
                        obtain.writeInt(1);
                        configuration.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f1664a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38068a(C2783e eVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(eVar != null ? eVar.asBinder() : null);
                    this.f1664a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38069a(C2786f fVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(fVar != null ? fVar.asBinder() : null);
                    this.f1664a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38070a(C2789g gVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(gVar != null ? gVar.asBinder() : null);
                    this.f1664a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38071a(C2792h hVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(hVar != null ? hVar.asBinder() : null);
                    this.f1664a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38072a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    this.f1664a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38073a(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f1664a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38074a(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f1664a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38075a(List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f1664a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo38076a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1664a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final boolean mo38077a(int i, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    boolean z = true;
                    if (keyEvent != null) {
                        obtain.writeInt(1);
                        keyEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f1664a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final boolean mo38078a(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = true;
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f1664a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f1664a;
            }

            /* renamed from: b */
            public final void mo38079b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38080b(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f1664a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38081b(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f1664a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38082b(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f1664a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38083b(List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f1664a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo38084b(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1664a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final boolean mo38085b(int i, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    boolean z = true;
                    if (keyEvent != null) {
                        obtain.writeInt(1);
                        keyEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f1664a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38086c(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f1664a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo38087c(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1664a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final boolean mo38088c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f1664a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo38089d(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f1664a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo38090d(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1664a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final boolean mo38091d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f1664a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: e */
            public final void mo38092e(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f1664a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: e */
            public final boolean mo38093e() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f1664a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: f */
            public final void mo38094f() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: g */
            public final void mo38095g() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: h */
            public final int mo38096h() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: i */
            public final int mo38097i() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: j */
            public final int mo38098j() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: k */
            public final void mo38099k() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: l */
            public final void mo38100l() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: m */
            public final void mo38101m() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: n */
            public final void mo38102n() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: o */
            public final void mo38103o() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: p */
            public final void mo38104p() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: q */
            public final void mo38105q() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: r */
            public final Bundle mo38106r() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: s */
            public final C2830u mo38107s() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f1664a.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return C2831a.m1738a(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C2780d m1550a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IEmbeddedPlayer");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C2780d)) ? new C2782a(iBinder) : (C2780d) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1, types: [com.google.android.youtube.player.internal.e] */
        /* JADX WARNING: type inference failed for: r3v2, types: [com.google.android.youtube.player.internal.e$a$a] */
        /* JADX WARNING: type inference failed for: r3v4, types: [com.google.android.youtube.player.internal.e] */
        /* JADX WARNING: type inference failed for: r3v5, types: [com.google.android.youtube.player.internal.h] */
        /* JADX WARNING: type inference failed for: r3v6, types: [com.google.android.youtube.player.internal.h$a$a] */
        /* JADX WARNING: type inference failed for: r3v8, types: [com.google.android.youtube.player.internal.h] */
        /* JADX WARNING: type inference failed for: r3v9, types: [com.google.android.youtube.player.internal.g] */
        /* JADX WARNING: type inference failed for: r3v10, types: [com.google.android.youtube.player.internal.g$a$a] */
        /* JADX WARNING: type inference failed for: r3v12, types: [com.google.android.youtube.player.internal.g] */
        /* JADX WARNING: type inference failed for: r3v13, types: [com.google.android.youtube.player.internal.f] */
        /* JADX WARNING: type inference failed for: r3v14, types: [com.google.android.youtube.player.internal.f$a$a] */
        /* JADX WARNING: type inference failed for: r3v16, types: [com.google.android.youtube.player.internal.f] */
        /* JADX WARNING: type inference failed for: r3v17, types: [android.content.res.Configuration] */
        /* JADX WARNING: type inference failed for: r3v19, types: [android.content.res.Configuration] */
        /* JADX WARNING: type inference failed for: r3v20, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r3v22, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r3v23, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r3v25, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r3v26, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r3v28, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r3v29, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v30, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v31 */
        /* JADX WARNING: type inference failed for: r3v32 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v43 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.youtube.player.internal.h$a$a, com.google.android.youtube.player.internal.e$a$a, com.google.android.youtube.player.internal.e, com.google.android.youtube.player.internal.h, com.google.android.youtube.player.internal.g$a$a, com.google.android.youtube.player.internal.g, com.google.android.youtube.player.internal.f$a$a, com.google.android.youtube.player.internal.f, android.content.res.Configuration, android.os.Bundle, android.view.KeyEvent, android.os.IBinder]
          uses: [com.google.android.youtube.player.internal.e, com.google.android.youtube.player.internal.h, com.google.android.youtube.player.internal.g, com.google.android.youtube.player.internal.f, android.content.res.Configuration, android.os.Bundle, android.view.KeyEvent, android.os.IBinder]
          mth insns count: 312
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 14 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                java.lang.String r1 = "com.google.android.youtube.player.internal.IEmbeddedPlayer"
                r2 = 1
                if (r5 == r0) goto L_0x0326
                r0 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x0315;
                    case 2: goto L_0x0303;
                    case 3: goto L_0x02f1;
                    case 4: goto L_0x02db;
                    case 5: goto L_0x02c5;
                    case 6: goto L_0x02af;
                    case 7: goto L_0x0299;
                    case 8: goto L_0x028f;
                    case 9: goto L_0x0285;
                    case 10: goto L_0x0277;
                    case 11: goto L_0x0269;
                    case 12: goto L_0x025b;
                    case 13: goto L_0x0251;
                    case 14: goto L_0x0247;
                    case 15: goto L_0x0239;
                    case 16: goto L_0x022b;
                    case 17: goto L_0x021d;
                    case 18: goto L_0x020f;
                    case 19: goto L_0x01fe;
                    case 20: goto L_0x01f0;
                    case 21: goto L_0x01e2;
                    case 22: goto L_0x01d4;
                    case 23: goto L_0x01c6;
                    case 24: goto L_0x01b5;
                    case 25: goto L_0x01a4;
                    case 26: goto L_0x017e;
                    case 27: goto L_0x0158;
                    case 28: goto L_0x0132;
                    case 29: goto L_0x010c;
                    case 30: goto L_0x0102;
                    case 31: goto L_0x00f8;
                    case 32: goto L_0x00df;
                    case 33: goto L_0x00d5;
                    case 34: goto L_0x00cb;
                    case 35: goto L_0x00c1;
                    case 36: goto L_0x00b7;
                    case 37: goto L_0x00a6;
                    case 38: goto L_0x009c;
                    case 39: goto L_0x0085;
                    case 40: goto L_0x0068;
                    case 41: goto L_0x0047;
                    case 42: goto L_0x0026;
                    case 43: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r5 = super.onTransact(r5, r6, r7, r8)
                return r5
            L_0x0012:
                r6.enforceInterface(r1)
                com.google.android.youtube.player.internal.u r5 = r4.mo38107s()
                r7.writeNoException()
                if (r5 == 0) goto L_0x0022
                android.os.IBinder r3 = r5.asBinder()
            L_0x0022:
                r7.writeStrongBinder(r3)
                return r2
            L_0x0026:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                int r8 = r6.readInt()
                if (r8 == 0) goto L_0x003c
                android.os.Parcelable$Creator r8 = android.view.KeyEvent.CREATOR
                java.lang.Object r6 = r8.createFromParcel(r6)
                r3 = r6
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
            L_0x003c:
                boolean r5 = r4.mo38085b(r5, r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0047:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                int r8 = r6.readInt()
                if (r8 == 0) goto L_0x005d
                android.os.Parcelable$Creator r8 = android.view.KeyEvent.CREATOR
                java.lang.Object r6 = r8.createFromParcel(r6)
                r3 = r6
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
            L_0x005d:
                boolean r5 = r4.mo38077a(r5, r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0068:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x007a
                android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r3 = r5
                android.os.Bundle r3 = (android.os.Bundle) r3
            L_0x007a:
                boolean r5 = r4.mo38078a(r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0085:
                r6.enforceInterface(r1)
                android.os.Bundle r5 = r4.mo38106r()
                r7.writeNoException()
                if (r5 == 0) goto L_0x0098
                r7.writeInt(r2)
                r5.writeToParcel(r7, r2)
                goto L_0x009b
            L_0x0098:
                r7.writeInt(r0)
            L_0x009b:
                return r2
            L_0x009c:
                r6.enforceInterface(r1)
                r4.mo38105q()
                r7.writeNoException()
                return r2
            L_0x00a6:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x00b0
                r0 = 1
            L_0x00b0:
                r4.mo38092e(r0)
                r7.writeNoException()
                return r2
            L_0x00b7:
                r6.enforceInterface(r1)
                r4.mo38104p()
                r7.writeNoException()
                return r2
            L_0x00c1:
                r6.enforceInterface(r1)
                r4.mo38103o()
                r7.writeNoException()
                return r2
            L_0x00cb:
                r6.enforceInterface(r1)
                r4.mo38102n()
                r7.writeNoException()
                return r2
            L_0x00d5:
                r6.enforceInterface(r1)
                r4.mo38101m()
                r7.writeNoException()
                return r2
            L_0x00df:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x00f1
                android.os.Parcelable$Creator r5 = android.content.res.Configuration.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r3 = r5
                android.content.res.Configuration r3 = (android.content.res.Configuration) r3
            L_0x00f1:
                r4.mo38067a(r3)
                r7.writeNoException()
                return r2
            L_0x00f8:
                r6.enforceInterface(r1)
                r4.mo38100l()
                r7.writeNoException()
                return r2
            L_0x0102:
                r6.enforceInterface(r1)
                r4.mo38099k()
                r7.writeNoException()
                return r2
            L_0x010c:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0116
                goto L_0x012b
            L_0x0116:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlaybackEventListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0126
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C2786f
                if (r8 == 0) goto L_0x0126
                r3 = r6
                com.google.android.youtube.player.internal.f r3 = (com.google.android.youtube.player.internal.C2786f) r3
                goto L_0x012b
            L_0x0126:
                com.google.android.youtube.player.internal.f$a$a r3 = new com.google.android.youtube.player.internal.f$a$a
                r3.<init>(r5)
            L_0x012b:
                r4.mo38069a(r3)
                r7.writeNoException()
                return r2
            L_0x0132:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x013c
                goto L_0x0151
            L_0x013c:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlayerStateChangeListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x014c
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C2789g
                if (r8 == 0) goto L_0x014c
                r3 = r6
                com.google.android.youtube.player.internal.g r3 = (com.google.android.youtube.player.internal.C2789g) r3
                goto L_0x0151
            L_0x014c:
                com.google.android.youtube.player.internal.g$a$a r3 = new com.google.android.youtube.player.internal.g$a$a
                r3.<init>(r5)
            L_0x0151:
                r4.mo38070a(r3)
                r7.writeNoException()
                return r2
            L_0x0158:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0162
                goto L_0x0177
            L_0x0162:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlaylistEventListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0172
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C2792h
                if (r8 == 0) goto L_0x0172
                r3 = r6
                com.google.android.youtube.player.internal.h r3 = (com.google.android.youtube.player.internal.C2792h) r3
                goto L_0x0177
            L_0x0172:
                com.google.android.youtube.player.internal.h$a$a r3 = new com.google.android.youtube.player.internal.h$a$a
                r3.<init>(r5)
            L_0x0177:
                r4.mo38071a(r3)
                r7.writeNoException()
                return r2
            L_0x017e:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0188
                goto L_0x019d
            L_0x0188:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IOnFullscreenListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0198
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C2783e
                if (r8 == 0) goto L_0x0198
                r3 = r6
                com.google.android.youtube.player.internal.e r3 = (com.google.android.youtube.player.internal.C2783e) r3
                goto L_0x019d
            L_0x0198:
                com.google.android.youtube.player.internal.e$a$a r3 = new com.google.android.youtube.player.internal.e$a$a
                r3.<init>(r5)
            L_0x019d:
                r4.mo38068a(r3)
                r7.writeNoException()
                return r2
            L_0x01a4:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x01ae
                r0 = 1
            L_0x01ae:
                r4.mo38090d(r0)
                r7.writeNoException()
                return r2
            L_0x01b5:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x01bf
                r0 = 1
            L_0x01bf:
                r4.mo38087c(r0)
                r7.writeNoException()
                return r2
            L_0x01c6:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                r4.mo38072a(r5)
                r7.writeNoException()
                return r2
            L_0x01d4:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo38089d(r5)
                r7.writeNoException()
                return r2
            L_0x01e2:
                r6.enforceInterface(r1)
                int r5 = r4.mo38098j()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x01f0:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo38086c(r5)
                r7.writeNoException()
                return r2
            L_0x01fe:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x0208
                r0 = 1
            L_0x0208:
                r4.mo38084b(r0)
                r7.writeNoException()
                return r2
            L_0x020f:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo38080b(r5)
                r7.writeNoException()
                return r2
            L_0x021d:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo38066a(r5)
                r7.writeNoException()
                return r2
            L_0x022b:
                r6.enforceInterface(r1)
                int r5 = r4.mo38097i()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0239:
                r6.enforceInterface(r1)
                int r5 = r4.mo38096h()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0247:
                r6.enforceInterface(r1)
                r4.mo38095g()
                r7.writeNoException()
                return r2
            L_0x0251:
                r6.enforceInterface(r1)
                r4.mo38094f()
                r7.writeNoException()
                return r2
            L_0x025b:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo38093e()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0269:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo38091d()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0277:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo38088c()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0285:
                r6.enforceInterface(r1)
                r4.mo38079b()
                r7.writeNoException()
                return r2
            L_0x028f:
                r6.enforceInterface(r1)
                r4.mo38065a()
                r7.writeNoException()
                return r2
            L_0x0299:
                r6.enforceInterface(r1)
                java.util.ArrayList r5 = r6.createStringArrayList()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo38083b(r5, r8, r6)
                r7.writeNoException()
                return r2
            L_0x02af:
                r6.enforceInterface(r1)
                java.util.ArrayList r5 = r6.createStringArrayList()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo38075a(r5, r8, r6)
                r7.writeNoException()
                return r2
            L_0x02c5:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo38082b(r5, r8, r6)
                r7.writeNoException()
                return r2
            L_0x02db:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo38074a(r5, r8, r6)
                r7.writeNoException()
                return r2
            L_0x02f1:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r6 = r6.readInt()
                r4.mo38081b(r5, r6)
                r7.writeNoException()
                return r2
            L_0x0303:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r6 = r6.readInt()
                r4.mo38073a(r5, r6)
                r7.writeNoException()
                return r2
            L_0x0315:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x031f
                r0 = 1
            L_0x031f:
                r4.mo38076a(r0)
                r7.writeNoException()
                return r2
            L_0x0326:
                r7.writeString(r1)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.internal.C2780d.C2781a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    /* renamed from: a */
    void mo38065a() throws RemoteException;

    /* renamed from: a */
    void mo38066a(int i) throws RemoteException;

    /* renamed from: a */
    void mo38067a(Configuration configuration) throws RemoteException;

    /* renamed from: a */
    void mo38068a(C2783e eVar) throws RemoteException;

    /* renamed from: a */
    void mo38069a(C2786f fVar) throws RemoteException;

    /* renamed from: a */
    void mo38070a(C2789g gVar) throws RemoteException;

    /* renamed from: a */
    void mo38071a(C2792h hVar) throws RemoteException;

    /* renamed from: a */
    void mo38072a(String str) throws RemoteException;

    /* renamed from: a */
    void mo38073a(String str, int i) throws RemoteException;

    /* renamed from: a */
    void mo38074a(String str, int i, int i2) throws RemoteException;

    /* renamed from: a */
    void mo38075a(List<String> list, int i, int i2) throws RemoteException;

    /* renamed from: a */
    void mo38076a(boolean z) throws RemoteException;

    /* renamed from: a */
    boolean mo38077a(int i, KeyEvent keyEvent) throws RemoteException;

    /* renamed from: a */
    boolean mo38078a(Bundle bundle) throws RemoteException;

    /* renamed from: b */
    void mo38079b() throws RemoteException;

    /* renamed from: b */
    void mo38080b(int i) throws RemoteException;

    /* renamed from: b */
    void mo38081b(String str, int i) throws RemoteException;

    /* renamed from: b */
    void mo38082b(String str, int i, int i2) throws RemoteException;

    /* renamed from: b */
    void mo38083b(List<String> list, int i, int i2) throws RemoteException;

    /* renamed from: b */
    void mo38084b(boolean z) throws RemoteException;

    /* renamed from: b */
    boolean mo38085b(int i, KeyEvent keyEvent) throws RemoteException;

    /* renamed from: c */
    void mo38086c(int i) throws RemoteException;

    /* renamed from: c */
    void mo38087c(boolean z) throws RemoteException;

    /* renamed from: c */
    boolean mo38088c() throws RemoteException;

    /* renamed from: d */
    void mo38089d(int i) throws RemoteException;

    /* renamed from: d */
    void mo38090d(boolean z) throws RemoteException;

    /* renamed from: d */
    boolean mo38091d() throws RemoteException;

    /* renamed from: e */
    void mo38092e(boolean z) throws RemoteException;

    /* renamed from: e */
    boolean mo38093e() throws RemoteException;

    /* renamed from: f */
    void mo38094f() throws RemoteException;

    /* renamed from: g */
    void mo38095g() throws RemoteException;

    /* renamed from: h */
    int mo38096h() throws RemoteException;

    /* renamed from: i */
    int mo38097i() throws RemoteException;

    /* renamed from: j */
    int mo38098j() throws RemoteException;

    /* renamed from: k */
    void mo38099k() throws RemoteException;

    /* renamed from: l */
    void mo38100l() throws RemoteException;

    /* renamed from: m */
    void mo38101m() throws RemoteException;

    /* renamed from: n */
    void mo38102n() throws RemoteException;

    /* renamed from: o */
    void mo38103o() throws RemoteException;

    /* renamed from: p */
    void mo38104p() throws RemoteException;

    /* renamed from: q */
    void mo38105q() throws RemoteException;

    /* renamed from: r */
    Bundle mo38106r() throws RemoteException;

    /* renamed from: s */
    C2830u mo38107s() throws RemoteException;
}
