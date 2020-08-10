package com.truenet.android.p096a;

import java.io.BufferedReader;
import p000a.p001a.p003b.p004a.C0022a;
import p000a.p001a.p003b.p005b.C0033i;
import p000a.p001a.p003b.p005b.C0037m.C0038a;

/* renamed from: com.truenet.android.a.g */
/* compiled from: StartAppSDK */
public final class C5200g {

    /* renamed from: com.truenet.android.a.g$a */
    /* compiled from: StartAppSDK */
    static final class C5201a extends C0033i implements C0022a<String> {
        final /* synthetic */ C0038a $line;
        final /* synthetic */ BufferedReader $reader;

        C5201a(C0038a aVar, BufferedReader bufferedReader) {
            this.$line = aVar;
            this.$reader = bufferedReader;
            super(0);
        }

        /* renamed from: b */
        public final String mo45a() {
            this.$line.element = this.$reader.readLine();
            return (String) this.$line.element;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0095 A[SYNTHETIC, Splitter:B:32:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a7  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final boolean m3947a(java.net.URL r6, java.lang.String r7, android.content.Context r8) {
        /*
            java.lang.String r0 = "stream closed with error!"
            java.lang.String r1 = "$receiver"
            p000a.p001a.p003b.p005b.C0032h.m44b(r6, r1)
            java.lang.String r1 = "data"
            p000a.p001a.p003b.p005b.C0032h.m44b(r7, r1)
            r1 = 0
            r2 = r1
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2
            java.io.BufferedOutputStream r1 = (java.io.BufferedOutputStream) r1
            r3 = 0
            java.net.URLConnection r4 = r6.openConnection()     // Catch:{ all -> 0x0092 }
            if (r4 == 0) goto L_0x008a
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ all -> 0x0092 }
            java.lang.String r2 = "Cache-Control"
            java.lang.String r5 = "no-cache"
            r4.setRequestProperty(r2, r5)     // Catch:{ all -> 0x0093 }
            if (r8 == 0) goto L_0x002f
            java.lang.String r2 = "User-Agent"
            com.truenet.android.a.i$a r5 = com.truenet.android.p096a.C5203i.f3648a     // Catch:{ all -> 0x0093 }
            java.lang.String r8 = r5.mo62918a(r8)     // Catch:{ all -> 0x0093 }
            r4.setRequestProperty(r2, r8)     // Catch:{ all -> 0x0093 }
        L_0x002f:
            java.lang.String r8 = "PUT"
            r4.setRequestMethod(r8)     // Catch:{ all -> 0x0093 }
            r8 = 1
            r4.setDoOutput(r8)     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = "Content-Type"
            java.lang.String r5 = "text/html"
            r4.setRequestProperty(r2, r5)     // Catch:{ all -> 0x0093 }
            java.nio.charset.Charset r2 = p000a.p001a.p008e.C0054a.f9a     // Catch:{ all -> 0x0093 }
            byte[] r7 = r7.getBytes(r2)     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = "(this as java.lang.String).getBytes(charset)"
            p000a.p001a.p003b.p005b.C0032h.m41a(r7, r2)     // Catch:{ all -> 0x0093 }
            int r2 = r7.length     // Catch:{ all -> 0x0093 }
            r4.setFixedLengthStreamingMode(r2)     // Catch:{ all -> 0x0093 }
            r2 = 50000(0xc350, float:7.0065E-41)
            r4.setConnectTimeout(r2)     // Catch:{ all -> 0x0093 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0093 }
            java.io.OutputStream r5 = r4.getOutputStream()     // Catch:{ all -> 0x0093 }
            r2.<init>(r5)     // Catch:{ all -> 0x0093 }
            r2.write(r7)     // Catch:{ all -> 0x0088 }
            r2.flush()     // Catch:{ all -> 0x0088 }
            r7 = 299(0x12b, float:4.19E-43)
            r1 = 200(0xc8, float:2.8E-43)
            int r5 = r4.getResponseCode()     // Catch:{ all -> 0x0088 }
            if (r1 <= r5) goto L_0x006e
            goto L_0x0071
        L_0x006e:
            if (r7 < r5) goto L_0x0071
            goto L_0x0072
        L_0x0071:
            r8 = 0
        L_0x0072:
            r2.close()     // Catch:{ all -> 0x0076 }
            goto L_0x0082
        L_0x0076:
            r7 = move-exception
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getCanonicalName()
            android.util.Log.e(r6, r0, r7)
        L_0x0082:
            if (r4 == 0) goto L_0x0087
            r4.disconnect()
        L_0x0087:
            return r8
        L_0x0088:
            r1 = r2
            goto L_0x0093
        L_0x008a:
            a.a.h r7 = new a.a.h     // Catch:{ all -> 0x0092 }
            java.lang.String r8 = "null cannot be cast to non-null type java.net.HttpURLConnection"
            r7.<init>(r8)     // Catch:{ all -> 0x0092 }
            throw r7     // Catch:{ all -> 0x0092 }
        L_0x0092:
            r4 = r2
        L_0x0093:
            if (r1 == 0) goto L_0x00a5
            r1.close()     // Catch:{ all -> 0x0099 }
            goto L_0x00a5
        L_0x0099:
            r7 = move-exception
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getCanonicalName()
            android.util.Log.e(r6, r0, r7)
        L_0x00a5:
            if (r4 == 0) goto L_0x00aa
            r4.disconnect()
        L_0x00aa:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.p096a.C5200g.m3947a(java.net.URL, java.lang.String, android.content.Context):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00de A[SYNTHETIC, Splitter:B:42:0x00de] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e6 A[Catch:{ all -> 0x00e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f7  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String m3948b(java.net.URL r7, java.lang.String r8, android.content.Context r9) {
        /*
            java.lang.String r0 = "stream closed with error!"
            java.lang.String r1 = "$receiver"
            p000a.p001a.p003b.p005b.C0032h.m44b(r7, r1)
            java.lang.String r1 = "data"
            p000a.p001a.p003b.p005b.C0032h.m44b(r8, r1)
            r1 = 0
            r2 = r1
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2
            r3 = r1
            java.io.BufferedOutputStream r3 = (java.io.BufferedOutputStream) r3
            r4 = r1
            java.io.BufferedInputStream r4 = (java.io.BufferedInputStream) r4
            java.net.URLConnection r5 = r7.openConnection()     // Catch:{ all -> 0x00db }
            if (r5 == 0) goto L_0x00d3
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ all -> 0x00db }
            java.lang.String r2 = "Cache-Control"
            java.lang.String r6 = "no-cache"
            r5.setRequestProperty(r2, r6)     // Catch:{ all -> 0x00dc }
            if (r9 == 0) goto L_0x0032
            java.lang.String r2 = "User-Agent"
            com.truenet.android.a.i$a r6 = com.truenet.android.p096a.C5203i.f3648a     // Catch:{ all -> 0x00dc }
            java.lang.String r9 = r6.mo62918a(r9)     // Catch:{ all -> 0x00dc }
            r5.setRequestProperty(r2, r9)     // Catch:{ all -> 0x00dc }
        L_0x0032:
            java.lang.String r9 = "POST"
            r5.setRequestMethod(r9)     // Catch:{ all -> 0x00dc }
            r9 = 1
            r5.setDoOutput(r9)     // Catch:{ all -> 0x00dc }
            r5.setDoInput(r9)     // Catch:{ all -> 0x00dc }
            java.nio.charset.Charset r9 = p000a.p001a.p008e.C0054a.f9a     // Catch:{ all -> 0x00dc }
            byte[] r8 = r8.getBytes(r9)     // Catch:{ all -> 0x00dc }
            java.lang.String r9 = "(this as java.lang.String).getBytes(charset)"
            p000a.p001a.p003b.p005b.C0032h.m41a(r8, r9)     // Catch:{ all -> 0x00dc }
            int r9 = r8.length     // Catch:{ all -> 0x00dc }
            r5.setFixedLengthStreamingMode(r9)     // Catch:{ all -> 0x00dc }
            java.lang.String r9 = "Content-Type"
            java.lang.String r2 = "application/json"
            r5.setRequestProperty(r9, r2)     // Catch:{ all -> 0x00dc }
            r9 = 50000(0xc350, float:7.0065E-41)
            r5.setConnectTimeout(r9)     // Catch:{ all -> 0x00dc }
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00dc }
            java.io.OutputStream r2 = r5.getOutputStream()     // Catch:{ all -> 0x00dc }
            r9.<init>(r2)     // Catch:{ all -> 0x00dc }
            r9.write(r8)     // Catch:{ all -> 0x00d1 }
            r9.flush()     // Catch:{ all -> 0x00d1 }
            int r8 = r5.getResponseCode()     // Catch:{ all -> 0x00d1 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r8 != r2) goto L_0x00b5
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d1 }
            r8.<init>()     // Catch:{ all -> 0x00d1 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ all -> 0x00d1 }
            java.io.InputStream r3 = r5.getInputStream()     // Catch:{ all -> 0x00d1 }
            r2.<init>(r3)     // Catch:{ all -> 0x00d1 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x00b2 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ all -> 0x00b2 }
            r6 = r2
            java.io.InputStream r6 = (java.io.InputStream) r6     // Catch:{ all -> 0x00b2 }
            r4.<init>(r6)     // Catch:{ all -> 0x00b2 }
            java.io.Reader r4 = (java.io.Reader) r4     // Catch:{ all -> 0x00b2 }
            r3.<init>(r4)     // Catch:{ all -> 0x00b2 }
            a.a.b.b.m$a r4 = new a.a.b.b.m$a     // Catch:{ all -> 0x00b2 }
            r4.<init>()     // Catch:{ all -> 0x00b2 }
            r6 = r1
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00b2 }
            r4.element = r6     // Catch:{ all -> 0x00b2 }
        L_0x0098:
            com.truenet.android.a.g$a r6 = new com.truenet.android.a.g$a     // Catch:{ all -> 0x00b2 }
            r6.<init>(r4, r3)     // Catch:{ all -> 0x00b2 }
            a.a.b.a.a r6 = (p000a.p001a.p003b.p004a.C0022a) r6     // Catch:{ all -> 0x00b2 }
            java.lang.Object r6 = r6.mo45a()     // Catch:{ all -> 0x00b2 }
            if (r6 == 0) goto L_0x00ad
            T r6 = r4.element     // Catch:{ all -> 0x00b2 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00b2 }
            r8.append(r6)     // Catch:{ all -> 0x00b2 }
            goto L_0x0098
        L_0x00ad:
            java.lang.String r1 = r8.toString()     // Catch:{ all -> 0x00b2 }
            goto L_0x00b6
        L_0x00b2:
            r3 = r9
            r4 = r2
            goto L_0x00dc
        L_0x00b5:
            r2 = r4
        L_0x00b6:
            r9.close()     // Catch:{ all -> 0x00bf }
            if (r2 == 0) goto L_0x00cb
            r2.close()     // Catch:{ all -> 0x00bf }
            goto L_0x00cb
        L_0x00bf:
            r8 = move-exception
            java.lang.Class r7 = r7.getClass()
            java.lang.String r7 = r7.getCanonicalName()
            android.util.Log.e(r7, r0, r8)
        L_0x00cb:
            if (r5 == 0) goto L_0x00d0
            r5.disconnect()
        L_0x00d0:
            return r1
        L_0x00d1:
            r3 = r9
            goto L_0x00dc
        L_0x00d3:
            a.a.h r8 = new a.a.h     // Catch:{ all -> 0x00db }
            java.lang.String r9 = "null cannot be cast to non-null type java.net.HttpURLConnection"
            r8.<init>(r9)     // Catch:{ all -> 0x00db }
            throw r8     // Catch:{ all -> 0x00db }
        L_0x00db:
            r5 = r2
        L_0x00dc:
            if (r3 == 0) goto L_0x00e4
            r3.close()     // Catch:{ all -> 0x00e2 }
            goto L_0x00e4
        L_0x00e2:
            r8 = move-exception
            goto L_0x00ea
        L_0x00e4:
            if (r4 == 0) goto L_0x00f5
            r4.close()     // Catch:{ all -> 0x00e2 }
            goto L_0x00f5
        L_0x00ea:
            java.lang.Class r7 = r7.getClass()
            java.lang.String r7 = r7.getCanonicalName()
            android.util.Log.e(r7, r0, r8)
        L_0x00f5:
            if (r5 == 0) goto L_0x00fa
            r5.disconnect()
        L_0x00fa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.p096a.C5200g.m3948b(java.net.URL, java.lang.String, android.content.Context):java.lang.String");
    }
}
