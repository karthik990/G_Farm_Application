package com.truenet.android.p096a;

/* renamed from: com.truenet.android.a.a */
/* compiled from: StartAppSDK */
public final class C5194a {
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0087 A[SYNTHETIC, Splitter:B:29:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0099  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final boolean m3936a(android.graphics.Bitmap r7, java.lang.String r8) {
        /*
            java.lang.String r0 = "stream closed with error!"
            java.lang.String r1 = "$receiver"
            p000a.p001a.p003b.p005b.C0032h.m44b(r7, r1)
            java.lang.String r1 = "url"
            p000a.p001a.p003b.p005b.C0032h.m44b(r8, r1)
            r1 = 0
            r2 = r1
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2
            java.io.ByteArrayOutputStream r1 = (java.io.ByteArrayOutputStream) r1
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ all -> 0x0084 }
            r4.<init>(r8)     // Catch:{ all -> 0x0084 }
            java.net.URLConnection r8 = r4.openConnection()     // Catch:{ all -> 0x0084 }
            if (r8 == 0) goto L_0x007c
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ all -> 0x0084 }
            r2 = 1
            r8.setDoOutput(r2)     // Catch:{ all -> 0x0085 }
            java.lang.String r4 = "PUT"
            r8.setRequestMethod(r4)     // Catch:{ all -> 0x0085 }
            java.lang.String r4 = "Content-Type"
            java.lang.String r5 = "image/jpeg"
            r8.setRequestProperty(r4, r5)     // Catch:{ all -> 0x0085 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0085 }
            r4.<init>()     // Catch:{ all -> 0x0085 }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ all -> 0x007a }
            r5 = 100
            r6 = r4
            java.io.OutputStream r6 = (java.io.OutputStream) r6     // Catch:{ all -> 0x007a }
            r7.compress(r1, r5, r6)     // Catch:{ all -> 0x007a }
            byte[] r1 = r4.toByteArray()     // Catch:{ all -> 0x007a }
            int r5 = r1.length     // Catch:{ all -> 0x007a }
            r8.setFixedLengthStreamingMode(r5)     // Catch:{ all -> 0x007a }
            java.io.OutputStream r5 = r8.getOutputStream()     // Catch:{ all -> 0x007a }
            r5.write(r1)     // Catch:{ all -> 0x007a }
            java.io.OutputStream r1 = r8.getOutputStream()     // Catch:{ all -> 0x007a }
            r1.flush()     // Catch:{ all -> 0x007a }
            r1 = 299(0x12b, float:4.19E-43)
            r5 = 200(0xc8, float:2.8E-43)
            int r6 = r8.getResponseCode()     // Catch:{ all -> 0x007a }
            if (r5 <= r6) goto L_0x0060
            goto L_0x0063
        L_0x0060:
            if (r1 < r6) goto L_0x0063
            goto L_0x0064
        L_0x0063:
            r2 = 0
        L_0x0064:
            r4.close()     // Catch:{ all -> 0x0068 }
            goto L_0x0074
        L_0x0068:
            r1 = move-exception
            java.lang.Class r7 = r7.getClass()
            java.lang.String r7 = r7.getCanonicalName()
            android.util.Log.e(r7, r0, r1)
        L_0x0074:
            if (r8 == 0) goto L_0x0079
            r8.disconnect()
        L_0x0079:
            return r2
        L_0x007a:
            r1 = r4
            goto L_0x0085
        L_0x007c:
            a.a.h r8 = new a.a.h     // Catch:{ all -> 0x0084 }
            java.lang.String r4 = "null cannot be cast to non-null type java.net.HttpURLConnection"
            r8.<init>(r4)     // Catch:{ all -> 0x0084 }
            throw r8     // Catch:{ all -> 0x0084 }
        L_0x0084:
            r8 = r2
        L_0x0085:
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ all -> 0x008b }
            goto L_0x0097
        L_0x008b:
            r1 = move-exception
            java.lang.Class r7 = r7.getClass()
            java.lang.String r7 = r7.getCanonicalName()
            android.util.Log.e(r7, r0, r1)
        L_0x0097:
            if (r8 == 0) goto L_0x009c
            r8.disconnect()
        L_0x009c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.p096a.C5194a.m3936a(android.graphics.Bitmap, java.lang.String):boolean");
    }
}
