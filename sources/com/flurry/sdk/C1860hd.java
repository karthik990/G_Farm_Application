package com.flurry.sdk;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.hd */
public final class C1860hd extends C1927jm {
    public C1860hd(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.SESSION_PROPERTIES;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01de  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01fd  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> m1102a(com.flurry.sdk.C1589bf r11) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            boolean r1 = r11.f646a
            java.lang.String r2 = "SessionPropertiesFrame"
            r3 = 5
            r4 = 0
            if (r1 == 0) goto L_0x0123
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = android.os.SystemClock.elapsedRealtime()
            long r5 = r5 - r7
            java.lang.String r1 = java.lang.Long.toString(r5)
            java.util.Map r1 = m1103a(r1)
            java.lang.String r5 = "boot.time"
            r0.put(r5, r1)
            android.os.StatFs r1 = new android.os.StatFs
            java.io.File r5 = android.os.Environment.getRootDirectory()
            java.lang.String r5 = r5.getAbsolutePath()
            r1.<init>(r5)
            int r5 = android.os.Build.VERSION.SDK_INT
            java.lang.String r6 = "disk.size.available.internal"
            java.lang.String r7 = "disk.size.total.internal"
            r8 = 18
            if (r5 < r8) goto L_0x005e
            long r9 = r1.getAvailableBlocksLong()
            java.lang.String r5 = java.lang.Long.toString(r9)
            java.util.Map r5 = m1103a(r5)
            r0.put(r7, r5)
            long r9 = r1.getAvailableBlocksLong()
            java.lang.String r1 = java.lang.Long.toString(r9)
            java.util.Map r1 = m1103a(r1)
            r0.put(r6, r1)
            goto L_0x007e
        L_0x005e:
            int r5 = r1.getAvailableBlocks()
            long r9 = (long) r5
            java.lang.String r5 = java.lang.Long.toString(r9)
            java.util.Map r5 = m1103a(r5)
            r0.put(r7, r5)
            int r1 = r1.getAvailableBlocks()
            long r9 = (long) r1
            java.lang.String r1 = java.lang.Long.toString(r9)
            java.util.Map r1 = m1103a(r1)
            r0.put(r6, r1)
        L_0x007e:
            com.flurry.sdk.am r1 = r11.f647b
            if (r1 == 0) goto L_0x00e5
            com.flurry.sdk.am r1 = r11.f647b
            boolean r1 = r1.f515a
            if (r1 != 0) goto L_0x00e5
            android.os.StatFs r1 = new android.os.StatFs     // Catch:{ Exception -> 0x00dd }
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x00dd }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x00dd }
            r1.<init>(r5)     // Catch:{ Exception -> 0x00dd }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00dd }
            java.lang.String r6 = "disk.size.available.external"
            java.lang.String r7 = "disk.size.total.external"
            if (r5 < r8) goto L_0x00bc
            long r8 = r1.getAvailableBlocksLong()     // Catch:{ Exception -> 0x00dd }
            java.lang.String r5 = java.lang.Long.toString(r8)     // Catch:{ Exception -> 0x00dd }
            java.util.Map r5 = m1103a(r5)     // Catch:{ Exception -> 0x00dd }
            r0.put(r7, r5)     // Catch:{ Exception -> 0x00dd }
            long r7 = r1.getAvailableBlocksLong()     // Catch:{ Exception -> 0x00dd }
            java.lang.String r1 = java.lang.Long.toString(r7)     // Catch:{ Exception -> 0x00dd }
            java.util.Map r1 = m1103a(r1)     // Catch:{ Exception -> 0x00dd }
            r0.put(r6, r1)     // Catch:{ Exception -> 0x00dd }
            goto L_0x00e5
        L_0x00bc:
            int r5 = r1.getAvailableBlocks()     // Catch:{ Exception -> 0x00dd }
            long r8 = (long) r5     // Catch:{ Exception -> 0x00dd }
            java.lang.String r5 = java.lang.Long.toString(r8)     // Catch:{ Exception -> 0x00dd }
            java.util.Map r5 = m1103a(r5)     // Catch:{ Exception -> 0x00dd }
            r0.put(r7, r5)     // Catch:{ Exception -> 0x00dd }
            int r1 = r1.getAvailableBlocks()     // Catch:{ Exception -> 0x00dd }
            long r7 = (long) r1     // Catch:{ Exception -> 0x00dd }
            java.lang.String r1 = java.lang.Long.toString(r7)     // Catch:{ Exception -> 0x00dd }
            java.util.Map r1 = m1103a(r1)     // Catch:{ Exception -> 0x00dd }
            r0.put(r6, r1)     // Catch:{ Exception -> 0x00dd }
            goto L_0x00e5
        L_0x00dd:
            r1 = move-exception
            java.lang.String r1 = r1.getMessage()
            com.flurry.sdk.C1685cy.m754a(r3, r2, r1)
        L_0x00e5:
            com.flurry.sdk.C1600bk.m536a()
            android.content.Context r1 = com.flurry.sdk.C1576b.m502a()
            java.lang.String r5 = "phone"
            java.lang.Object r1 = r1.getSystemService(r5)
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1
            if (r1 != 0) goto L_0x00f8
            r1 = r4
            goto L_0x00fc
        L_0x00f8:
            java.lang.String r1 = r1.getNetworkOperatorName()
        L_0x00fc:
            java.util.Map r1 = m1103a(r1)
            java.lang.String r6 = "carrier.name"
            r0.put(r6, r1)
            com.flurry.sdk.C1600bk.m536a()
            android.content.Context r1 = com.flurry.sdk.C1576b.m502a()
            java.lang.Object r1 = r1.getSystemService(r5)
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1
            if (r1 != 0) goto L_0x0116
            r1 = r4
            goto L_0x011a
        L_0x0116:
            java.lang.String r1 = r1.getNetworkOperator()
        L_0x011a:
            java.util.Map r1 = m1103a(r1)
            java.lang.String r5 = "carrier.details"
            r0.put(r5, r1)
        L_0x0123:
            android.content.Context r1 = com.flurry.sdk.C1576b.m502a()
            java.lang.String r5 = "activity"
            java.lang.Object r1 = r1.getSystemService(r5)
            android.app.ActivityManager r1 = (android.app.ActivityManager) r1
            android.app.ActivityManager$MemoryInfo r5 = new android.app.ActivityManager$MemoryInfo
            r5.<init>()
            r1.getMemoryInfo(r5)
            long r6 = r5.availMem
            java.lang.String r1 = java.lang.Long.toString(r6)
            java.util.Map r1 = m1103a(r1)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "memory.available"
            r6.<init>(r7)
            boolean r7 = r11.f646a
            java.lang.String r8 = ".start"
            java.lang.String r9 = ".end"
            if (r7 == 0) goto L_0x0152
            r7 = r8
            goto L_0x0153
        L_0x0152:
            r7 = r9
        L_0x0153:
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.put(r6, r1)
            int r1 = android.os.Build.VERSION.SDK_INT
            r6 = 16
            if (r1 < r6) goto L_0x0185
            long r5 = r5.availMem
            java.lang.String r1 = java.lang.Long.toString(r5)
            java.util.Map r1 = m1103a(r1)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "memory.total"
            r5.<init>(r6)
            boolean r6 = r11.f646a
            if (r6 == 0) goto L_0x017a
            r6 = r8
            goto L_0x017b
        L_0x017a:
            r6 = r9
        L_0x017b:
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.put(r5, r1)
        L_0x0185:
            r1 = 0
            r5 = -1
            android.content.IntentFilter r6 = new android.content.IntentFilter     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r7 = "android.intent.action.BATTERY_CHANGED"
            r6.<init>(r7)     // Catch:{ Exception -> 0x01b5 }
            android.content.Context r7 = com.flurry.sdk.C1576b.m502a()     // Catch:{ Exception -> 0x01b5 }
            android.content.Intent r4 = r7.registerReceiver(r4, r6)     // Catch:{ Exception -> 0x01b5 }
            if (r4 == 0) goto L_0x01c5
            java.lang.String r6 = "status"
            int r6 = r4.getIntExtra(r6, r5)     // Catch:{ Exception -> 0x01b5 }
            r7 = 2
            if (r6 == r7) goto L_0x01a4
            if (r6 != r3) goto L_0x01a5
        L_0x01a4:
            r1 = 1
        L_0x01a5:
            java.lang.String r6 = "level"
            int r6 = r4.getIntExtra(r6, r5)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r7 = "scale"
            int r2 = r4.getIntExtra(r7, r5)     // Catch:{ Exception -> 0x01b3 }
            r5 = r6
            goto L_0x01c6
        L_0x01b3:
            r4 = move-exception
            goto L_0x01b7
        L_0x01b5:
            r4 = move-exception
            r6 = -1
        L_0x01b7:
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r7 = "Error getting battery status: "
            java.lang.String r4 = r7.concat(r4)
            com.flurry.sdk.C1685cy.m754a(r3, r2, r4)
            r5 = r6
        L_0x01c5:
            r2 = -1
        L_0x01c6:
            float r3 = (float) r5
            float r2 = (float) r2
            float r3 = r3 / r2
            java.lang.String r1 = java.lang.Boolean.toString(r1)
            java.util.Map r1 = m1103a(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "battery.charging"
            r2.<init>(r4)
            boolean r4 = r11.f646a
            if (r4 == 0) goto L_0x01de
            r4 = r8
            goto L_0x01df
        L_0x01de:
            r4 = r9
        L_0x01df:
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r2, r1)
            java.lang.String r1 = java.lang.Float.toString(r3)
            java.util.Map r1 = m1103a(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "battery.remaining"
            r2.<init>(r3)
            boolean r3 = r11.f646a
            if (r3 == 0) goto L_0x01fd
            goto L_0x01fe
        L_0x01fd:
            r8 = r9
        L_0x01fe:
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r0.put(r2, r1)
            com.flurry.sdk.am r1 = r11.f647b
            if (r1 == 0) goto L_0x021f
            com.flurry.sdk.am r1 = r11.f647b
            boolean r1 = r1.f515a
            if (r1 == 0) goto L_0x021f
            com.flurry.sdk.am r11 = r11.f647b
            java.lang.String r11 = r11.f516b
            java.util.Map r11 = m1103a(r11)
            java.lang.String r1 = "instantapp.name"
            r0.put(r1, r11)
        L_0x021f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1860hd.m1102a(com.flurry.sdk.bf):java.util.Map");
    }

    /* renamed from: a */
    private static Map<String, String> m1103a(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(Param.VALUE, str);
        return hashMap;
    }
}
