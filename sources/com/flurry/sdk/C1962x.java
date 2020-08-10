package com.flurry.sdk;

import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Environment;
import android.os.StatFs;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.x */
public final class C1962x {
    /* renamed from: a */
    public static Map<String, String> m1243a() {
        HashMap hashMap = new HashMap();
        m1244a(hashMap);
        m1245b(hashMap);
        m1247d(hashMap);
        m1250g(hashMap);
        m1251h(hashMap);
        m1246c(hashMap);
        m1248e(hashMap);
        m1249f(hashMap);
        return hashMap;
    }

    /* renamed from: a */
    private static void m1244a(Map<String, String> map) {
        try {
            map.put("mem.java.max", Long.toString(Runtime.getRuntime().maxMemory()));
        } catch (RuntimeException e) {
            C1685cy.m755a(6, "CrashParameterCollector", "Error retrieving max memory", e);
        }
    }

    /* renamed from: b */
    private static void m1245b(Map<String, String> map) {
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            map.put("mem.pss", Long.toString((long) (memoryInfo.getTotalPss() * 1024)));
        } catch (RuntimeException e) {
            C1685cy.m755a(6, "CrashParameterCollector", "Error retrieving pss memory", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0096 A[Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a7 A[Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:63:0x00d8=Splitter:B:63:0x00d8, B:58:0x00cf=Splitter:B:58:0x00cf} */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void m1246c(java.util.Map<java.lang.String, java.lang.String> r10) {
        /*
            java.lang.String r0 = "^Vm(RSS|Size|Peak):\\s+(\\d+)\\s+kB$"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "/proc/"
            r2.<init>(r3)
            int r3 = android.os.Process.myPid()
            java.lang.String r3 = java.lang.Integer.toString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "status"
            r2.<init>(r1, r3)
            r1 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00d6, IOException -> 0x00cd, all -> 0x00c9 }
            r3.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00d6, IOException -> 0x00cd, all -> 0x00c9 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x00c5, IOException -> 0x00c1, all -> 0x00be }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x00c5, IOException -> 0x00c1, all -> 0x00be }
            r4.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00c5, IOException -> 0x00c1, all -> 0x00be }
            r2.<init>(r4)     // Catch:{ FileNotFoundException -> 0x00c5, IOException -> 0x00c1, all -> 0x00be }
            java.lang.String r1 = r2.readLine()     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
        L_0x003d:
            if (r1 == 0) goto L_0x00b1
            java.util.regex.Matcher r1 = r0.matcher(r1)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            boolean r4 = r1.find()     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r4 == 0) goto L_0x00ac
            r4 = 1
            java.lang.String r5 = r1.group(r4)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            r6 = 2
            java.lang.String r1 = r1.group(r6)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            boolean r7 = android.text.TextUtils.isEmpty(r5)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r7 != 0) goto L_0x00ac
            boolean r7 = android.text.TextUtils.isEmpty(r1)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r7 == 0) goto L_0x0060
            goto L_0x00ac
        L_0x0060:
            r7 = -1
            int r8 = r5.hashCode()     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            r9 = 81458(0x13e32, float:1.14147E-40)
            if (r8 == r9) goto L_0x0089
            r9 = 2483455(0x25e4ff, float:3.480062E-39)
            if (r8 == r9) goto L_0x007f
            r9 = 2577441(0x275421, float:3.611764E-39)
            if (r8 == r9) goto L_0x0075
            goto L_0x0093
        L_0x0075:
            java.lang.String r8 = "Size"
            boolean r5 = r5.equals(r8)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r5 == 0) goto L_0x0093
            r5 = 1
            goto L_0x0094
        L_0x007f:
            java.lang.String r8 = "Peak"
            boolean r5 = r5.equals(r8)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r5 == 0) goto L_0x0093
            r5 = 2
            goto L_0x0094
        L_0x0089:
            java.lang.String r8 = "RSS"
            boolean r5 = r5.equals(r8)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            if (r5 == 0) goto L_0x0093
            r5 = 0
            goto L_0x0094
        L_0x0093:
            r5 = -1
        L_0x0094:
            if (r5 == 0) goto L_0x00a7
            if (r5 == r4) goto L_0x00a1
            if (r5 == r6) goto L_0x009b
            goto L_0x00ac
        L_0x009b:
            java.lang.String r4 = "mem.virt.max"
            r10.put(r4, r1)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            goto L_0x00ac
        L_0x00a1:
            java.lang.String r4 = "mem.virt"
            r10.put(r4, r1)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            goto L_0x00ac
        L_0x00a7:
            java.lang.String r4 = "mem.rss"
            r10.put(r4, r1)     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
        L_0x00ac:
            java.lang.String r1 = r2.readLine()     // Catch:{ FileNotFoundException -> 0x00bc, IOException -> 0x00ba, all -> 0x00b8 }
            goto L_0x003d
        L_0x00b1:
            com.flurry.sdk.C1734dz.m871a(r3)
        L_0x00b4:
            com.flurry.sdk.C1734dz.m871a(r2)
            return
        L_0x00b8:
            r10 = move-exception
            goto L_0x00de
        L_0x00ba:
            r10 = move-exception
            goto L_0x00c3
        L_0x00bc:
            r10 = move-exception
            goto L_0x00c7
        L_0x00be:
            r10 = move-exception
            r2 = r1
            goto L_0x00de
        L_0x00c1:
            r10 = move-exception
            r2 = r1
        L_0x00c3:
            r1 = r3
            goto L_0x00cf
        L_0x00c5:
            r10 = move-exception
            r2 = r1
        L_0x00c7:
            r1 = r3
            goto L_0x00d8
        L_0x00c9:
            r10 = move-exception
            r2 = r1
            r3 = r2
            goto L_0x00de
        L_0x00cd:
            r10 = move-exception
            r2 = r1
        L_0x00cf:
            r10.printStackTrace()     // Catch:{ all -> 0x00dc }
        L_0x00d2:
            com.flurry.sdk.C1734dz.m871a(r1)
            goto L_0x00b4
        L_0x00d6:
            r10 = move-exception
            r2 = r1
        L_0x00d8:
            r10.printStackTrace()     // Catch:{ all -> 0x00dc }
            goto L_0x00d2
        L_0x00dc:
            r10 = move-exception
            r3 = r1
        L_0x00de:
            com.flurry.sdk.C1734dz.m871a(r3)
            com.flurry.sdk.C1734dz.m871a(r2)
            goto L_0x00e6
        L_0x00e5:
            throw r10
        L_0x00e6:
            goto L_0x00e5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1962x.m1246c(java.util.Map):void");
    }

    /* renamed from: d */
    private static void m1247d(Map<String, String> map) {
        map.put("application.state", Integer.toString(C1948n.m1229a().f1423i.mo16243a().f1435d));
    }

    /* renamed from: e */
    private static void m1248e(Map<String, String> map) {
        long j;
        long j2;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            j = statFs.getBlockSizeLong();
        } else {
            j = (long) statFs.getBlockSize();
        }
        if (VERSION.SDK_INT >= 18) {
            j2 = statFs.getAvailableBlocksLong();
        } else {
            j2 = (long) statFs.getAvailableBlocks();
        }
        map.put("disk.size.free", Long.toString(j2 * j));
    }

    /* renamed from: f */
    private static void m1249f(Map<String, String> map) {
        long j;
        long j2;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            j = statFs.getBlockSizeLong();
        } else {
            j = (long) statFs.getBlockSize();
        }
        if (VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockCountLong();
        } else {
            j2 = (long) statFs.getBlockCount();
        }
        map.put("disk.size.total", Long.toString(j2 * j));
    }

    /* renamed from: g */
    private static void m1250g(Map<String, String> map) {
        map.put("net.status", Integer.toString(C1948n.m1229a().f1416b.getNetworkType().ordinal()));
    }

    /* renamed from: h */
    private static void m1251h(Map<String, String> map) {
        map.put("orientation", Integer.toString(C1948n.m1229a().f1417c.f540a));
    }
}
