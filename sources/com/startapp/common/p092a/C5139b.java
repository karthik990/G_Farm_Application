package com.startapp.common.p092a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.startapp.common.a.b */
/* compiled from: StartAppSDK */
public final class C5139b {

    /* renamed from: a */
    private static volatile C5143c f3546a;

    /* renamed from: com.startapp.common.a.b$a */
    /* compiled from: StartAppSDK */
    public static final class C5141a {

        /* renamed from: a */
        private final String f3549a;

        /* renamed from: b */
        private final boolean f3550b;

        /* renamed from: c */
        private final String f3551c;

        C5141a(String str, boolean z, String str2) {
            this.f3549a = str;
            this.f3550b = z;
            this.f3551c = str2;
        }

        /* renamed from: a */
        public String mo62843a() {
            return this.f3549a;
        }

        /* renamed from: b */
        public boolean mo62844b() {
            return this.f3550b;
        }

        /* renamed from: c */
        public String mo62845c() {
            return this.f3551c;
        }
    }

    /* renamed from: com.startapp.common.a.b$b */
    /* compiled from: StartAppSDK */
    protected static final class C5142b implements ServiceConnection {

        /* renamed from: a */
        boolean f3552a = false;

        /* renamed from: b */
        private final LinkedBlockingQueue<IBinder> f3553b = new LinkedBlockingQueue<>(1);

        public void onServiceDisconnected(ComponentName componentName) {
        }

        protected C5142b() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f3553b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        /* renamed from: a */
        public IBinder mo62846a() {
            if (!this.f3552a) {
                this.f3552a = true;
                return (IBinder) this.f3553b.take();
            }
            throw new IllegalStateException();
        }
    }

    /* renamed from: com.startapp.common.a.b$c */
    /* compiled from: StartAppSDK */
    public static class C5143c {

        /* renamed from: a */
        private String f3554a;

        /* renamed from: b */
        private String f3555b;

        /* renamed from: c */
        private boolean f3556c = false;

        public C5143c() {
            String str = "";
            this.f3554a = str;
            this.f3555b = str;
        }

        /* renamed from: a */
        public synchronized String mo62849a() {
            return this.f3554a;
        }

        /* renamed from: a */
        public synchronized void mo62850a(String str) {
            this.f3554a = str;
        }

        /* renamed from: b */
        public synchronized boolean mo62853b() {
            return this.f3556c;
        }

        /* renamed from: a */
        public synchronized void mo62851a(boolean z) {
            this.f3556c = z;
        }

        /* renamed from: c */
        public synchronized String mo62854c() {
            return this.f3555b;
        }

        /* renamed from: b */
        public synchronized void mo62852b(String str) {
            this.f3555b = str;
        }
    }

    /* renamed from: com.startapp.common.a.b$d */
    /* compiled from: StartAppSDK */
    private static final class C5144d implements IInterface {

        /* renamed from: a */
        private IBinder f3557a;

        public C5144d(IBinder iBinder) {
            this.f3557a = iBinder;
        }

        public IBinder asBinder() {
            return this.f3557a;
        }

        /* renamed from: a */
        public String mo62855a() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken(AdvertisingInterface.ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                this.f3557a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        /* renamed from: a */
        public boolean mo62856a(boolean z) {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken(AdvertisingInterface.ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                boolean z2 = true;
                obtain.writeInt(z ? 1 : 0);
                this.f3557a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z2 = false;
                }
                return z2;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    /* renamed from: com.startapp.common.a.b$e */
    /* compiled from: StartAppSDK */
    private static class C5145e {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C5139b f3558a = new C5139b();
    }

    /* renamed from: a */
    public static boolean m3720a(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static C5139b m3719a() {
        return C5145e.f3558a;
    }

    /* renamed from: a */
    public synchronized C5143c mo62840a(final Context context) {
        if (f3546a == null) {
            f3546a = new C5143c();
            try {
                mo62841a(m3721b(context));
            } catch (Exception e) {
                for (int i = 0; i < e.getStackTrace().length; i++) {
                }
                f3546a.mo62850a("0");
            }
        } else {
            C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
                public void run() {
                    C5141a aVar;
                    try {
                        aVar = C5139b.m3721b(context);
                    } catch (Exception unused) {
                        aVar = new C5141a("0", false, "");
                    }
                    C5139b.this.mo62841a(aVar);
                }
            });
        }
        return f3546a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public synchronized void mo62841a(C5141a aVar) {
        f3546a.mo62850a(aVar.mo62843a());
        f3546a.mo62851a(aVar.mo62844b());
        f3546a.mo62852b(aVar.mo62845c());
    }

    /* renamed from: b */
    public static C5141a m3721b(Context context) {
        if (m3722b()) {
            return m3723c(context);
        }
        return m3724d(context);
    }

    /* renamed from: c */
    private static C5141a m3723c(Context context) {
        try {
            Object invoke = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{context.getApplicationContext()});
            return new C5141a((String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]), ((Boolean) invoke.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(invoke, new Object[0])).booleanValue(), "APP");
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    /* renamed from: d */
    private static C5141a m3724d(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 0);
                C5142b bVar = new C5142b();
                Intent intent = new Intent(AdvertisingInfoServiceStrategy.GOOGLE_PLAY_SERVICES_INTENT);
                intent.setPackage("com.google.android.gms");
                if (context.getApplicationContext().bindService(intent, bVar, 1)) {
                    try {
                        C5144d dVar = new C5144d(bVar.mo62846a());
                        C5141a aVar = new C5141a(dVar.mo62855a(), dVar.mo62856a(true), "DEVICE");
                        context.getApplicationContext().unbindService(bVar);
                        return aVar;
                    } catch (Exception e) {
                        throw e;
                    } catch (Throwable th) {
                        context.getApplicationContext().unbindService(bVar);
                        throw th;
                    }
                } else {
                    throw new IOException("Google Play connection failed");
                }
            } catch (Exception e2) {
                throw e2;
            }
        } else {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
    }

    /* renamed from: b */
    public static boolean m3722b() {
        return m3720a("com.google.android.gms.ads.identifier.AdvertisingIdClient");
    }
}
