package com.startapp.android.publish.adsCommon.p089i;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.p016a.p017a.p018a.p019a.C0819a;
import com.p016a.p017a.p018a.p019a.C0819a.C0820a;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.startapp.android.publish.adsCommon.i.a */
/* compiled from: StartAppSDK */
public class C5042a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static CountDownLatch f3294a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static C5045b f3295b;

    /* renamed from: com.startapp.android.publish.adsCommon.i.a$a */
    /* compiled from: StartAppSDK */
    private static final class C5044a implements ServiceConnection {

        /* renamed from: a */
        private String f3296a;

        private C5044a(String str) {
            this.f3296a = str;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            C0819a a = C0820a.m85a(iBinder);
            Bundle bundle = new Bundle();
            bundle.putString("package_name", this.f3296a);
            try {
                C5042a.f3295b = new C5045b(a.mo10126a(bundle));
            } catch (RemoteException e) {
                C5155g.m3808a("PlayReferrer", 5, "InstallReferrerServiceConnection.onServiceConnected", e);
            }
            C5042a.f3294a.countDown();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            C5155g.m3807a("PlayReferrer", 5, "InstallReferrerServiceConnection.onServiceDisconnected");
            C5042a.f3294a.countDown();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:17|18|19|20) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0071 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.startapp.android.publish.adsCommon.p089i.C5045b m3317a(android.content.Context r9) {
        /*
            java.lang.String r0 = "com.android.vending"
            java.lang.String r1 = "PlayReferrer"
            com.startapp.android.publish.adsCommon.i.b r2 = f3295b
            if (r2 != 0) goto L_0x0081
            r2 = 5
            java.util.concurrent.CountDownLatch r3 = new java.util.concurrent.CountDownLatch     // Catch:{ all -> 0x007b }
            r4 = 1
            r3.<init>(r4)     // Catch:{ all -> 0x007b }
            f3294a = r3     // Catch:{ all -> 0x007b }
            com.startapp.android.publish.adsCommon.i.a$a r3 = new com.startapp.android.publish.adsCommon.i.a$a     // Catch:{ all -> 0x007b }
            java.lang.String r5 = r9.getPackageName()     // Catch:{ all -> 0x007b }
            r6 = 0
            r3.<init>(r5)     // Catch:{ all -> 0x007b }
            android.content.Intent r5 = new android.content.Intent     // Catch:{ all -> 0x007b }
            java.lang.String r6 = "com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE"
            r5.<init>(r6)     // Catch:{ all -> 0x007b }
            android.content.ComponentName r6 = new android.content.ComponentName     // Catch:{ all -> 0x007b }
            java.lang.String r7 = "com.google.android.finsky.externalreferrer.GetInstallReferrerService"
            r6.<init>(r0, r7)     // Catch:{ all -> 0x007b }
            r5.setComponent(r6)     // Catch:{ all -> 0x007b }
            android.content.pm.PackageManager r6 = r9.getPackageManager()     // Catch:{ all -> 0x007b }
            r7 = 0
            java.util.List r6 = r6.queryIntentServices(r5, r7)     // Catch:{ all -> 0x007b }
            if (r6 == 0) goto L_0x0081
            boolean r8 = r6.isEmpty()     // Catch:{ all -> 0x007b }
            if (r8 != 0) goto L_0x0081
            java.lang.Object r6 = r6.get(r7)     // Catch:{ all -> 0x007b }
            android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6     // Catch:{ all -> 0x007b }
            android.content.pm.ServiceInfo r7 = r6.serviceInfo     // Catch:{ all -> 0x007b }
            if (r7 == 0) goto L_0x0081
            android.content.pm.ServiceInfo r7 = r6.serviceInfo     // Catch:{ all -> 0x007b }
            java.lang.String r7 = r7.packageName     // Catch:{ all -> 0x007b }
            android.content.pm.ServiceInfo r6 = r6.serviceInfo     // Catch:{ all -> 0x007b }
            java.lang.String r6 = r6.name     // Catch:{ all -> 0x007b }
            boolean r0 = r0.equals(r7)     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x0081
            if (r6 == 0) goto L_0x0081
            boolean r0 = m3320b(r9)     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x0081
            android.content.Intent r0 = new android.content.Intent     // Catch:{ all -> 0x007b }
            r0.<init>(r5)     // Catch:{ all -> 0x007b }
            boolean r0 = r9.bindService(r0, r3, r4)     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x0075
            java.util.concurrent.CountDownLatch r0 = f3294a     // Catch:{ InterruptedException -> 0x0071 }
            r4 = 1
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0071 }
            r0.await(r4, r6)     // Catch:{ InterruptedException -> 0x0071 }
        L_0x0071:
            r9.unbindService(r3)     // Catch:{ all -> 0x007b }
            goto L_0x0081
        L_0x0075:
            java.lang.String r9 = "failed to connect to referrer service"
            com.startapp.common.p092a.C5155g.m3807a(r1, r2, r9)     // Catch:{ all -> 0x007b }
            goto L_0x0081
        L_0x007b:
            r9 = move-exception
            java.lang.String r0 = "getReferrerDetails"
            com.startapp.common.p092a.C5155g.m3808a(r1, r2, r0, r9)
        L_0x0081:
            com.startapp.android.publish.adsCommon.i.b r9 = f3295b
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.p089i.C5042a.m3317a(android.content.Context):com.startapp.android.publish.adsCommon.i.b");
    }

    /* renamed from: b */
    private static boolean m3320b(Context context) {
        try {
            if (context.getPackageManager().getPackageInfo("com.android.vending", 128).versionCode >= 80837300) {
                return true;
            }
            return false;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }
}
