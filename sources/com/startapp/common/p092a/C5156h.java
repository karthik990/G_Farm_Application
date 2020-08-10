package com.startapp.common.p092a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.startapp.common.p095d.C5184a;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.ByteOrder;
import java.util.Map;
import p043io.fabric.sdk.android.services.network.HttpRequest;

/* renamed from: com.startapp.common.a.h */
/* compiled from: StartAppSDK */
public class C5156h {

    /* renamed from: com.startapp.common.a.h$a */
    /* compiled from: StartAppSDK */
    public static class C5157a {

        /* renamed from: a */
        private String f3564a;

        /* renamed from: b */
        private String f3565b;

        /* renamed from: a */
        public String mo62865a() {
            return this.f3564a;
        }

        /* renamed from: a */
        public void mo62866a(String str) {
            this.f3564a = str;
        }

        /* renamed from: b */
        public String mo62867b() {
            return this.f3565b;
        }

        /* renamed from: b */
        public void mo62868b(String str) {
            this.f3565b = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("HttpResult: ");
            sb.append(this.f3565b);
            sb.append(" ");
            sb.append(this.f3564a);
            return sb.toString();
        }
    }

    /* renamed from: com.startapp.common.a.h$b */
    /* compiled from: StartAppSDK */
    public static class C5158b {

        /* renamed from: a */
        private String f3566a;

        /* renamed from: b */
        private String f3567b;

        /* renamed from: c */
        private String f3568c;

        /* renamed from: a */
        public String mo62870a() {
            return this.f3566a;
        }

        /* renamed from: b */
        public String mo62872b() {
            return this.f3567b;
        }

        /* renamed from: c */
        public String mo62874c() {
            return this.f3568c;
        }

        /* renamed from: a */
        public void mo62871a(String str) {
            this.f3566a = str;
        }

        /* renamed from: b */
        public void mo62873b(String str) {
            this.f3567b = str;
        }

        /* renamed from: c */
        public void mo62875c(String str) {
            this.f3568c = str;
        }
    }

    static {
        if (VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    /* renamed from: a */
    public static String m3813a(Context context, String str, byte[] bArr, Map<String, String> map, String str2, boolean z) {
        return m3814a(context, str, bArr, map, str2, z, "application/json");
    }

    /* JADX WARNING: type inference failed for: r5v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:14|(2:18|19)|20|21) */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b6, code lost:
        r6 = th;
        r1 = r1;
        r5 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b8, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00b9, code lost:
        r7 = r6;
        r6 = 0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0027 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0021 A[SYNTHETIC, Splitter:B:18:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b6 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0008] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00e7 A[SYNTHETIC, Splitter:B:70:0x00e7] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00ee  */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3814a(android.content.Context r5, java.lang.String r6, byte[] r7, java.util.Map<java.lang.String, java.lang.String> r8, java.lang.String r9, boolean r10, java.lang.String r11) {
        /*
            r0 = 0
            r1 = 0
            java.net.HttpURLConnection r5 = m3817b(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2, all -> 0x00bf }
            if (r7 == 0) goto L_0x0028
            int r6 = r7.length     // Catch:{ Exception -> 0x00b8, all -> 0x00b6 }
            if (r6 <= 0) goto L_0x0028
            java.io.OutputStream r6 = r5.getOutputStream()     // Catch:{ all -> 0x001d }
            r6.write(r7)     // Catch:{ all -> 0x001b }
            if (r6 == 0) goto L_0x0028
            r6.flush()     // Catch:{ IOException -> 0x0028 }
            r6.close()     // Catch:{ IOException -> 0x0028 }
            goto L_0x0028
        L_0x001b:
            r7 = move-exception
            goto L_0x001f
        L_0x001d:
            r7 = move-exception
            r6 = r1
        L_0x001f:
            if (r6 == 0) goto L_0x0027
            r6.flush()     // Catch:{ IOException -> 0x0027 }
            r6.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            throw r7     // Catch:{ Exception -> 0x00b8, all -> 0x00b6 }
        L_0x0028:
            int r6 = r5.getResponseCode()     // Catch:{ Exception -> 0x00b8, all -> 0x00b6 }
            r7 = 200(0xc8, float:2.8E-43)
            r8 = -1
            java.lang.String r9 = "UTF-8"
            r10 = 1024(0x400, float:1.435E-42)
            if (r6 == r7) goto L_0x0079
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r7.<init>()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            java.lang.String r11 = "Error code = ["
            r7.append(r11)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r7.append(r6)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r11 = 93
            r7.append(r11)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            java.io.InputStream r1 = r5.getErrorStream()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            if (r1 == 0) goto L_0x006f
            java.io.StringWriter r11 = new java.io.StringWriter     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r11.<init>()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            char[] r10 = new char[r10]     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r3.<init>(r1, r9)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
        L_0x005e:
            int r9 = r2.read(r10)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            if (r9 == r8) goto L_0x0068
            r11.write(r10, r0, r9)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            goto L_0x005e
        L_0x0068:
            java.lang.String r8 = r11.toString()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r7.append(r8)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
        L_0x006f:
            java.lang.Exception r8 = new java.lang.Exception     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            r8.<init>(r7)     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            throw r8     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
        L_0x0079:
            java.io.InputStream r7 = r5.getInputStream()     // Catch:{ Exception -> 0x00b4, all -> 0x00b6 }
            if (r7 == 0) goto L_0x00a7
            java.io.StringWriter r11 = new java.io.StringWriter     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r11.<init>()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            char[] r10 = new char[r10]     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r2.<init>(r7, r9)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
        L_0x0090:
            int r9 = r1.read(r10)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            if (r9 == r8) goto L_0x009a
            r11.write(r10, r0, r9)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            goto L_0x0090
        L_0x009a:
            java.lang.String r1 = r11.toString()     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            goto L_0x00a7
        L_0x009f:
            r6 = move-exception
            r1 = r7
            goto L_0x00e5
        L_0x00a2:
            r8 = move-exception
            r1 = r5
            r5 = r7
            r7 = r8
            goto L_0x00c6
        L_0x00a7:
            if (r7 == 0) goto L_0x00ae
            r7.close()     // Catch:{ IOException -> 0x00ad }
            goto L_0x00ae
        L_0x00ad:
        L_0x00ae:
            if (r5 == 0) goto L_0x00b3
            r5.disconnect()
        L_0x00b3:
            return r1
        L_0x00b4:
            r7 = move-exception
            goto L_0x00bb
        L_0x00b6:
            r6 = move-exception
            goto L_0x00e5
        L_0x00b8:
            r6 = move-exception
            r7 = r6
            r6 = 0
        L_0x00bb:
            r4 = r1
            r1 = r5
            r5 = r4
            goto L_0x00c6
        L_0x00bf:
            r6 = move-exception
            r5 = r1
            goto L_0x00e5
        L_0x00c2:
            r6 = move-exception
            r7 = r6
            r5 = r1
            r6 = 0
        L_0x00c6:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e1 }
            r8.<init>()     // Catch:{ all -> 0x00e1 }
            java.lang.String r9 = "Error execute Exception "
            r8.append(r9)     // Catch:{ all -> 0x00e1 }
            java.lang.String r9 = r7.getMessage()     // Catch:{ all -> 0x00e1 }
            r8.append(r9)     // Catch:{ all -> 0x00e1 }
            com.startapp.common.e r9 = new com.startapp.common.e     // Catch:{ all -> 0x00e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00e1 }
            r9.<init>(r8, r7, r6)     // Catch:{ all -> 0x00e1 }
            throw r9     // Catch:{ all -> 0x00e1 }
        L_0x00e1:
            r6 = move-exception
            r4 = r1
            r1 = r5
            r5 = r4
        L_0x00e5:
            if (r1 == 0) goto L_0x00ec
            r1.close()     // Catch:{ IOException -> 0x00eb }
            goto L_0x00ec
        L_0x00eb:
        L_0x00ec:
            if (r5 == 0) goto L_0x00f1
            r5.disconnect()
        L_0x00f1:
            goto L_0x00f3
        L_0x00f2:
            throw r6
        L_0x00f3:
            goto L_0x00f2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p092a.C5156h.m3814a(android.content.Context, java.lang.String, byte[], java.util.Map, java.lang.String, boolean, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r8v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r13v1 */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r13v3 */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r10v7, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r13v4 */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r13v10, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r13v11 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r10v8 */
    /* JADX WARNING: type inference failed for: r13v15 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r13v16 */
    /* JADX WARNING: type inference failed for: r13v17 */
    /* JADX WARNING: type inference failed for: r13v18 */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005f, code lost:
        r11 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0060, code lost:
        r13 = 0;
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ae, code lost:
        r11 = th;
        r8 = r8;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b0, code lost:
        r11 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b1, code lost:
        r13 = 0;
        r12 = 0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v2
      assigns: []
      uses: []
      mth insns count: 98
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ae A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x000e] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00db A[SYNTHETIC, Splitter:B:54:0x00db] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00e2  */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.startapp.common.p092a.C5156h.C5157a m3810a(android.content.Context r10, java.lang.String r11, java.util.Map<java.lang.String, java.lang.String> r12, java.lang.String r13, boolean r14) {
        /*
            r2 = 0
            r6 = 0
            r7 = 1
            r8 = 0
            r9 = 0
            r0 = r10
            r1 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            java.net.HttpURLConnection r10 = m3817b(r0, r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00b8, all -> 0x00b5 }
            int r12 = r10.getResponseCode()     // Catch:{ Exception -> 0x00b0, all -> 0x00ae }
            r13 = 200(0xc8, float:2.8E-43)
            r14 = -1
            java.lang.String r0 = "UTF-8"
            r1 = 1024(0x400, float:1.435E-42)
            if (r12 != r13) goto L_0x0063
            com.startapp.common.p095d.C5184a.m3923b(r10, r11)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            java.io.InputStream r8 = r10.getInputStream()     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            com.startapp.common.a.h$a r11 = new com.startapp.common.a.h$a     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r11.<init>()     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            java.lang.String r13 = r10.getContentType()     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r11.mo62868b(r13)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            if (r8 == 0) goto L_0x0052
            java.io.StringWriter r13 = new java.io.StringWriter     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r13.<init>()     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            char[] r1 = new char[r1]     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r3.<init>(r8, r0)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r2.<init>(r3)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
        L_0x0041:
            int r0 = r2.read(r1)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            if (r0 == r14) goto L_0x004b
            r13.write(r1, r9, r0)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            goto L_0x0041
        L_0x004b:
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
            r11.mo62866a(r13)     // Catch:{ Exception -> 0x005f, all -> 0x00ae }
        L_0x0052:
            if (r8 == 0) goto L_0x0059
            r8.close()     // Catch:{ IOException -> 0x0058 }
            goto L_0x0059
        L_0x0058:
        L_0x0059:
            if (r10 == 0) goto L_0x005e
            r10.disconnect()
        L_0x005e:
            return r11
        L_0x005f:
            r11 = move-exception
            r13 = r8
            r7 = 0
            goto L_0x00b3
        L_0x0063:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            r11.<init>()     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            java.lang.String r13 = "Error sendGetWithResponse code = ["
            r11.append(r13)     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            r11.append(r12)     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            r13 = 93
            r11.append(r13)     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            java.io.InputStream r13 = r10.getErrorStream()     // Catch:{ Exception -> 0x00ab, all -> 0x00ae }
            if (r13 == 0) goto L_0x009d
            java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            r2.<init>()     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            char[] r1 = new char[r1]     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            r4.<init>(r13, r0)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
        L_0x008c:
            int r0 = r3.read(r1)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            if (r0 == r14) goto L_0x0096
            r2.write(r1, r9, r0)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            goto L_0x008c
        L_0x0096:
            java.lang.String r14 = r2.toString()     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            r11.append(r14)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
        L_0x009d:
            com.startapp.common.e r14 = new com.startapp.common.e     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            r14.<init>(r11, r8, r7, r12)     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
            throw r14     // Catch:{ Exception -> 0x00a9, all -> 0x00a7 }
        L_0x00a7:
            r11 = move-exception
            goto L_0x00d8
        L_0x00a9:
            r11 = move-exception
            goto L_0x00b3
        L_0x00ab:
            r11 = move-exception
            r13 = r8
            goto L_0x00b3
        L_0x00ae:
            r11 = move-exception
            goto L_0x00d9
        L_0x00b0:
            r11 = move-exception
            r13 = r8
            r12 = 0
        L_0x00b3:
            r8 = r10
            goto L_0x00bb
        L_0x00b5:
            r11 = move-exception
            r10 = r8
            goto L_0x00d9
        L_0x00b8:
            r11 = move-exception
            r13 = r8
            r12 = 0
        L_0x00bb:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r10.<init>()     // Catch:{ all -> 0x00d6 }
            java.lang.String r14 = "Error execute Exception "
            r10.append(r14)     // Catch:{ all -> 0x00d6 }
            java.lang.String r14 = r11.getMessage()     // Catch:{ all -> 0x00d6 }
            r10.append(r14)     // Catch:{ all -> 0x00d6 }
            com.startapp.common.e r14 = new com.startapp.common.e     // Catch:{ all -> 0x00d6 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00d6 }
            r14.<init>(r10, r11, r7, r12)     // Catch:{ all -> 0x00d6 }
            throw r14     // Catch:{ all -> 0x00d6 }
        L_0x00d6:
            r11 = move-exception
            r10 = r8
        L_0x00d8:
            r8 = r13
        L_0x00d9:
            if (r8 == 0) goto L_0x00e0
            r8.close()     // Catch:{ IOException -> 0x00df }
            goto L_0x00e0
        L_0x00df:
        L_0x00e0:
            if (r10 == 0) goto L_0x00e5
            r10.disconnect()
        L_0x00e5:
            goto L_0x00e7
        L_0x00e6:
            throw r11
        L_0x00e7:
            goto L_0x00e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p092a.C5156h.m3810a(android.content.Context, java.lang.String, java.util.Map, java.lang.String, boolean):com.startapp.common.a.h$a");
    }

    /* renamed from: b */
    private static HttpURLConnection m3817b(Context context, String str, byte[] bArr, Map<String, String> map, String str2, boolean z, String str3) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.addRequestProperty("Cache-Control", "no-cache");
        C5184a.m3921a(httpURLConnection, str);
        if (!(str2 == null || str2.compareTo("-1") == 0)) {
            httpURLConnection.addRequestProperty("User-Agent", str2);
        }
        httpURLConnection.setRequestProperty("Accept", "application/json;text/html;text/plain");
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setConnectTimeout(10000);
        if (bArr != null) {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setFixedLengthStreamingMode(bArr.length);
            httpURLConnection.setRequestProperty("Content-Type", str3);
            if (z) {
                httpURLConnection.setRequestProperty("Content-Encoding", HttpRequest.ENCODING_GZIP);
            }
        } else {
            httpURLConnection.setRequestMethod("GET");
        }
        return httpURLConnection;
    }

    /* renamed from: a */
    public static String m3812a(Context context) {
        String str = "WIFI";
        String str2 = "e100";
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return str2;
            }
            if (!C5146c.m3760a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                return "e105";
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                str = "e102";
            } else {
                String typeName = activeNetworkInfo.getTypeName();
                if (typeName.toLowerCase().compareTo(str.toLowerCase()) != 0) {
                    if (typeName.toLowerCase().compareTo("MOBILE".toLowerCase()) == 0) {
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                        str = telephonyManager != null ? Integer.toString(telephonyManager.getNetworkType()) : "e101";
                    } else {
                        str = str2;
                    }
                }
            }
            return str;
        } catch (Exception unused) {
            return str2;
        }
    }

    /* renamed from: a */
    public static C5158b m3811a(Context context, String str) {
        if (str.toLowerCase().compareTo("WIFI".toLowerCase()) == 0) {
            return m3816b(context);
        }
        return null;
    }

    /* renamed from: b */
    public static C5158b m3816b(Context context) {
        String str = "e100";
        C5158b bVar = new C5158b();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            String str2 = "e105";
            if (connectivityManager != null) {
                if (C5146c.m3760a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                        str2 = "e102";
                    } else if (activeNetworkInfo.getTypeName().compareTo("WIFI") != 0) {
                        str2 = "e103";
                    } else if (C5146c.m3760a(context, "android.permission.ACCESS_WIFI_STATE")) {
                        int rssi = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getRssi();
                        bVar.mo62875c(Integer.toString(WifiManager.calculateSignalLevel(rssi, 5)));
                        bVar.mo62873b(Integer.toString(rssi));
                        str2 = null;
                    }
                }
                str = str2;
            }
        } catch (Exception unused) {
        }
        bVar.mo62871a(str);
        return bVar;
    }

    /* renamed from: c */
    public static Boolean m3818c(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && C5146c.m3760a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return Boolean.valueOf(activeNetworkInfo.isRoaming());
            }
        }
        return null;
    }

    /* renamed from: a */
    public static String m3815a(WifiInfo wifiInfo) {
        int ipAddress = wifiInfo.getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }
        try {
            return InetAddress.getByAddress(BigInteger.valueOf((long) ipAddress).toByteArray()).getHostAddress();
        } catch (Exception unused) {
            return null;
        }
    }
}
