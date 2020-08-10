package com.startapp.p054a.p055a.p060e;

/* renamed from: com.startapp.a.a.e.b */
/* compiled from: StartAppSDK */
public class C4713b {

    /* renamed from: a */
    private final C4714c f2419a = new C4714c();

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0056 A[SYNTHETIC, Splitter:B:31:0x0056] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo61119a(com.startapp.p054a.p055a.p056a.C4698c r12) {
        /*
            r11 = this;
            int r0 = r12.mo61094b()
            int r1 = r12.mo61095c()
            r2 = 0
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004b }
            r3.<init>()     // Catch:{ Exception -> 0x004b }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r4 = 0
            r5 = r0
            r0 = 0
        L_0x0016:
            if (r0 >= r1) goto L_0x0035
            long[] r6 = r12.mo61093a(r0)     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r7 = r5
            r5 = 0
        L_0x001e:
            r8 = 4096(0x1000, float:5.74E-42)
            if (r5 >= r8) goto L_0x0031
            int r8 = r7 + -1
            if (r7 <= 0) goto L_0x002f
            r9 = r6[r5]     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r2.writeLong(r9)     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            int r5 = r5 + 1
            r7 = r8
            goto L_0x001e
        L_0x002f:
            r5 = r8
            goto L_0x0032
        L_0x0031:
            r5 = r7
        L_0x0032:
            int r0 = r0 + 1
            goto L_0x0016
        L_0x0035:
            r3.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0038:
            com.startapp.a.a.e.c r12 = r11.f2419a
            byte[] r0 = r3.toByteArray()
            java.lang.String r12 = r12.mo61120a(r0)
            return r12
        L_0x0043:
            r12 = move-exception
            r2 = r3
            goto L_0x0054
        L_0x0046:
            r12 = move-exception
            r2 = r3
            goto L_0x004c
        L_0x0049:
            r12 = move-exception
            goto L_0x0054
        L_0x004b:
            r12 = move-exception
        L_0x004c:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x0049 }
            java.lang.String r1 = "problem serializing bitSet"
            r0.<init>(r1, r12)     // Catch:{ all -> 0x0049 }
            throw r0     // Catch:{ all -> 0x0049 }
        L_0x0054:
            if (r2 == 0) goto L_0x0059
            r2.close()     // Catch:{ IOException -> 0x0059 }
        L_0x0059:
            goto L_0x005b
        L_0x005a:
            throw r12
        L_0x005b:
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.p054a.p055a.p060e.C4713b.mo61119a(com.startapp.a.a.a.c):java.lang.String");
    }
}
