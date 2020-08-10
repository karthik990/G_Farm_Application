package com.google.android.gms.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.iid.zzk;

@Deprecated
public class GcmReceiver extends WakefulBroadcastReceiver {
    private static boolean zzq = false;
    private static zzk zzr;
    private static zzk zzs;

    private final synchronized zzk zzd(Context context, String str) {
        if ("com.google.android.c2dm.intent.RECEIVE".equals(str)) {
            if (zzs == null) {
                zzs = new zzk(context, str);
            }
            return zzs;
        }
        if (zzr == null) {
            zzr = new zzk(context, str);
        }
        return zzr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00be A[Catch:{ SecurityException -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c3 A[Catch:{ SecurityException -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ce A[Catch:{ SecurityException -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00df A[Catch:{ SecurityException -> 0x00ea }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzd(android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            boolean r0 = r5.isOrderedBroadcast()
            if (r0 == 0) goto L_0x000b
            r0 = 500(0x1f4, float:7.0E-43)
            r5.setResultCode(r0)
        L_0x000b:
            android.content.pm.PackageManager r0 = r6.getPackageManager()
            r1 = 0
            android.content.pm.ResolveInfo r0 = r0.resolveService(r7, r1)
            java.lang.String r1 = "GcmReceiver"
            if (r0 == 0) goto L_0x00b1
            android.content.pm.ServiceInfo r2 = r0.serviceInfo
            if (r2 != 0) goto L_0x001e
            goto L_0x00b1
        L_0x001e:
            android.content.pm.ServiceInfo r0 = r0.serviceInfo
            java.lang.String r2 = r6.getPackageName()
            java.lang.String r3 = r0.packageName
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0080
            java.lang.String r2 = r0.name
            if (r2 != 0) goto L_0x0031
            goto L_0x0080
        L_0x0031:
            java.lang.String r0 = r0.name
            java.lang.String r2 = "."
            boolean r2 = r0.startsWith(r2)
            if (r2 == 0) goto L_0x0057
            java.lang.String r2 = r6.getPackageName()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r3 = r0.length()
            if (r3 == 0) goto L_0x0052
            java.lang.String r0 = r2.concat(r0)
            goto L_0x0057
        L_0x0052:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0057:
            r2 = 3
            boolean r2 = android.util.Log.isLoggable(r1, r2)
            if (r2 == 0) goto L_0x0078
            java.lang.String r2 = "Restricting intent to a specific service: "
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r4 = r3.length()
            if (r4 == 0) goto L_0x006f
            java.lang.String r2 = r2.concat(r3)
            goto L_0x0075
        L_0x006f:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r2)
            r2 = r3
        L_0x0075:
            android.util.Log.d(r1, r2)
        L_0x0078:
            java.lang.String r2 = r6.getPackageName()
            r7.setClassName(r2, r0)
            goto L_0x00b6
        L_0x0080:
            java.lang.String r2 = r0.packageName
            java.lang.String r0 = r0.name
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 94
            java.lang.String r4 = java.lang.String.valueOf(r0)
            int r4 = r4.length()
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "Error resolving target intent service, skipping classname enforcement. Resolved service was: "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = "/"
            r4.append(r2)
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            goto L_0x00b3
        L_0x00b1:
            java.lang.String r0 = "Failed to resolve target intent service, skipping classname enforcement"
        L_0x00b3:
            android.util.Log.e(r1, r0)
        L_0x00b6:
            java.lang.String r0 = "android.permission.WAKE_LOCK"
            int r0 = r6.checkCallingOrSelfPermission(r0)     // Catch:{ SecurityException -> 0x00ea }
            if (r0 != 0) goto L_0x00c3
            android.content.ComponentName r6 = startWakefulService(r6, r7)     // Catch:{ SecurityException -> 0x00ea }
            goto L_0x00cc
        L_0x00c3:
            android.content.ComponentName r6 = r6.startService(r7)     // Catch:{ SecurityException -> 0x00ea }
            java.lang.String r7 = "Missing wake lock permission, service start may be delayed"
            android.util.Log.d(r1, r7)     // Catch:{ SecurityException -> 0x00ea }
        L_0x00cc:
            if (r6 != 0) goto L_0x00df
            java.lang.String r6 = "Error while delivering the message: ServiceIntent not found."
            android.util.Log.e(r1, r6)     // Catch:{ SecurityException -> 0x00ea }
            boolean r6 = r5.isOrderedBroadcast()     // Catch:{ SecurityException -> 0x00ea }
            if (r6 == 0) goto L_0x00e9
            r6 = 404(0x194, float:5.66E-43)
            r5.setResultCode(r6)     // Catch:{ SecurityException -> 0x00ea }
            return
        L_0x00df:
            boolean r6 = r5.isOrderedBroadcast()     // Catch:{ SecurityException -> 0x00ea }
            if (r6 == 0) goto L_0x00e9
            r6 = -1
            r5.setResultCode(r6)     // Catch:{ SecurityException -> 0x00ea }
        L_0x00e9:
            return
        L_0x00ea:
            r6 = move-exception
            java.lang.String r7 = "Error while delivering the message to the serviceIntent"
            android.util.Log.e(r1, r7, r6)
            boolean r6 = r5.isOrderedBroadcast()
            if (r6 == 0) goto L_0x00fb
            r6 = 401(0x191, float:5.62E-43)
            r5.setResultCode(r6)
        L_0x00fb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmReceiver.zzd(android.content.Context, android.content.Intent):void");
    }

    public void onReceive(Context context, Intent intent) {
        String str = "GcmReceiver";
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "received new intent");
        }
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        if ("google.com/iid".equals(intent.getStringExtra("from"))) {
            intent.setAction("com.google.android.gms.iid.InstanceID");
        }
        String str2 = "gcm.rawData64";
        String stringExtra = intent.getStringExtra(str2);
        boolean z = false;
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra(str2);
        }
        if (PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26) {
            z = true;
        }
        if (z) {
            if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
            zzd(context, intent.getAction()).zzd(intent, goAsync());
            return;
        }
        boolean equals = "com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction());
        zzd(context, intent);
        if (isOrderedBroadcast() && getResultCode() == 0) {
            setResultCode(-1);
        }
    }
}
