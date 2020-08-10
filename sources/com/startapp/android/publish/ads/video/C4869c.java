package com.startapp.android.publish.ads.video;

import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4863c;
import java.io.File;

/* renamed from: com.startapp.android.publish.ads.video.c */
/* compiled from: StartAppSDK */
public class C4869c {

    /* renamed from: a */
    private boolean f2781a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C4863c f2782b;

    /* renamed from: c */
    private String f2783c;

    /* renamed from: com.startapp.android.publish.ads.video.c$a */
    /* compiled from: StartAppSDK */
    public interface C4872a {
        /* renamed from: a */
        void mo61663a(String str);
    }

    /* renamed from: com.startapp.android.publish.ads.video.c$b */
    /* compiled from: StartAppSDK */
    private static class C4873b {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C4869c f2789a = new C4869c();
    }

    private C4869c() {
        this.f2781a = true;
        this.f2782b = null;
        this.f2783c = null;
    }

    /* renamed from: a */
    public static C4869c m2627a() {
        return C4873b.f2789a;
    }

    /* renamed from: a */
    public void mo61692a(C4863c cVar) {
        this.f2782b = cVar;
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [java.io.DataInputStream, java.io.FileOutputStream, java.lang.String, java.io.InputStream] */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0125, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0126, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0128, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0129, code lost:
        r7 = null;
        r10 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v0, types: [java.io.DataInputStream, java.io.FileOutputStream, java.lang.String, java.io.InputStream]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [java.lang.String, java.io.InputStream, java.io.DataInputStream, java.io.FileOutputStream]
      mth insns count: 150
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
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0125 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:13:0x006a] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo61691a(android.content.Context r24, java.net.URL r25, java.lang.String r26, com.startapp.android.publish.ads.video.C4869c.C4872a r27) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            r0 = r26
            r4 = r27
            java.lang.String r5 = ".temp"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Downloading video from "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            r7 = 3
            java.lang.String r8 = "StartAppWall.ProgressiveVideoManager"
            com.startapp.common.p092a.C5155g.m3807a(r8, r7, r6)
            java.lang.String r6 = r25.toString()
            r1.f2783c = r6
            r6 = 1
            r1.f2781a = r6
            com.startapp.android.publish.adsCommon.b r9 = com.startapp.android.publish.adsCommon.C4983b.m3032a()
            com.startapp.android.publish.adsCommon.n r9 = r9.mo62154H()
            int r9 = r9.mo62433l()
            r10 = 0
            java.lang.String r11 = com.startapp.android.publish.ads.video.C4923h.m2844a(r2, r0)     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            java.io.File r12 = new java.io.File     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            r12.<init>(r11)     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            boolean r13 = r12.exists()     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            if (r13 == 0) goto L_0x0054
            r1.f2783c = r10     // Catch:{ Exception -> 0x0053 }
            r10.close()     // Catch:{ Exception -> 0x0053 }
            r10.close()     // Catch:{ Exception -> 0x0053 }
            r10.close()     // Catch:{ Exception -> 0x0053 }
        L_0x0053:
            return r11
        L_0x0054:
            java.net.URLConnection r13 = r25.openConnection()     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            r13.connect()     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            int r14 = r13.getContentLength()     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            java.io.InputStream r13 = r13.getInputStream()     // Catch:{ Exception -> 0x0138, all -> 0x0133 }
            java.io.DataInputStream r15 = new java.io.DataInputStream     // Catch:{ Exception -> 0x012f, all -> 0x012c }
            r15.<init>(r13)     // Catch:{ Exception -> 0x012f, all -> 0x012c }
            r6 = 4096(0x1000, float:5.74E-42)
            byte[] r6 = new byte[r6]     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            r10.<init>()     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            r10.append(r0)     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            r10.append(r5)     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0128, all -> 0x0125 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            r0.<init>()     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            r0.append(r11)     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            r0.append(r5)     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            r5 = 0
            java.io.FileOutputStream r7 = r2.openFileOutput(r10, r5)     // Catch:{ Exception -> 0x0122, all -> 0x0125 }
            r17 = 0
            r18 = 0
            r19 = 0
        L_0x0095:
            int r5 = r15.read(r6)     // Catch:{ Exception -> 0x0120 }
            if (r5 <= 0) goto L_0x00d8
            r20 = r11
            boolean r11 = r1.f2781a     // Catch:{ Exception -> 0x0120 }
            if (r11 == 0) goto L_0x00da
            r11 = 0
            r7.write(r6, r11, r5)     // Catch:{ Exception -> 0x0120 }
            int r5 = r17 + r5
            r17 = r12
            double r11 = (double) r5
            r21 = 4636737291354636288(0x4059000000000000, double:100.0)
            java.lang.Double.isNaN(r11)
            double r11 = r11 * r21
            r22 = r5
            r21 = r6
            double r5 = (double) r14
            java.lang.Double.isNaN(r5)
            double r11 = r11 / r5
            int r5 = (int) r11
            if (r5 < r9) goto L_0x00cf
            if (r18 != 0) goto L_0x00c6
            if (r4 == 0) goto L_0x00c6
            r1.m2630a(r4, r0)     // Catch:{ Exception -> 0x0120 }
            r18 = 1
        L_0x00c6:
            int r6 = r19 + 1
            if (r5 < r6) goto L_0x00cf
            r1.m2628a(r5)     // Catch:{ Exception -> 0x0120 }
            r19 = r5
        L_0x00cf:
            r12 = r17
            r11 = r20
            r6 = r21
            r17 = r22
            goto L_0x0095
        L_0x00d8:
            r20 = r11
        L_0x00da:
            r17 = r12
            boolean r0 = r1.f2781a     // Catch:{ Exception -> 0x0120 }
            if (r0 != 0) goto L_0x0103
            if (r5 <= 0) goto L_0x0103
            java.lang.String r0 = "Video downloading disabled"
            r4 = 3
            com.startapp.common.p092a.C5155g.m3807a(r8, r4, r0)     // Catch:{ Exception -> 0x0120 }
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0120 }
            java.lang.String r4 = com.startapp.android.publish.ads.video.C4923h.m2844a(r2, r10)     // Catch:{ Exception -> 0x0120 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0120 }
            r0.delete()     // Catch:{ Exception -> 0x0120 }
            java.lang.String r0 = "downloadInterrupted"
            r2 = 0
            r1.f2783c = r2     // Catch:{ Exception -> 0x0102 }
            r13.close()     // Catch:{ Exception -> 0x0102 }
            r15.close()     // Catch:{ Exception -> 0x0102 }
            r7.close()     // Catch:{ Exception -> 0x0102 }
        L_0x0102:
            return r0
        L_0x0103:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0120 }
            java.lang.String r4 = com.startapp.android.publish.ads.video.C4923h.m2844a(r2, r10)     // Catch:{ Exception -> 0x0120 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0120 }
            r4 = r17
            r1.m2629a(r2, r0, r4)     // Catch:{ Exception -> 0x0120 }
            r2 = 0
            r1.f2783c = r2     // Catch:{ Exception -> 0x011d }
            r13.close()     // Catch:{ Exception -> 0x011d }
            r15.close()     // Catch:{ Exception -> 0x011d }
            r7.close()     // Catch:{ Exception -> 0x011d }
        L_0x011d:
            r16 = r20
            goto L_0x016c
        L_0x0120:
            r0 = move-exception
            goto L_0x013d
        L_0x0122:
            r0 = move-exception
            r7 = 0
            goto L_0x013d
        L_0x0125:
            r0 = move-exception
            r7 = 0
            goto L_0x016e
        L_0x0128:
            r0 = move-exception
            r7 = 0
            r10 = 0
            goto L_0x013d
        L_0x012c:
            r0 = move-exception
            r7 = 0
            goto L_0x0136
        L_0x012f:
            r0 = move-exception
            r7 = 0
            r10 = 0
            goto L_0x013c
        L_0x0133:
            r0 = move-exception
            r7 = 0
            r13 = 0
        L_0x0136:
            r15 = 0
            goto L_0x016e
        L_0x0138:
            r0 = move-exception
            r7 = 0
            r10 = 0
            r13 = 0
        L_0x013c:
            r15 = 0
        L_0x013d:
            r4 = 6
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r5.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r6 = "Error downloading video from "
            r5.append(r6)     // Catch:{ all -> 0x016d }
            r5.append(r3)     // Catch:{ all -> 0x016d }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x016d }
            com.startapp.common.p092a.C5155g.m3808a(r8, r4, r3, r0)     // Catch:{ all -> 0x016d }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x016d }
            java.lang.String r2 = com.startapp.android.publish.ads.video.C4923h.m2844a(r2, r10)     // Catch:{ all -> 0x016d }
            r0.<init>(r2)     // Catch:{ all -> 0x016d }
            r0.delete()     // Catch:{ all -> 0x016d }
            r2 = 0
            r1.f2783c = r2     // Catch:{ Exception -> 0x016a }
            r13.close()     // Catch:{ Exception -> 0x016a }
            r15.close()     // Catch:{ Exception -> 0x016a }
            r7.close()     // Catch:{ Exception -> 0x016a }
        L_0x016a:
            r16 = 0
        L_0x016c:
            return r16
        L_0x016d:
            r0 = move-exception
        L_0x016e:
            r2 = 0
            r1.f2783c = r2     // Catch:{ Exception -> 0x017a }
            r13.close()     // Catch:{ Exception -> 0x017a }
            r15.close()     // Catch:{ Exception -> 0x017a }
            r7.close()     // Catch:{ Exception -> 0x017a }
        L_0x017a:
            goto L_0x017c
        L_0x017b:
            throw r0
        L_0x017c:
            goto L_0x017b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.video.C4869c.mo61691a(android.content.Context, java.net.URL, java.lang.String, com.startapp.android.publish.ads.video.c$a):java.lang.String");
    }

    /* renamed from: a */
    private void m2630a(final C4872a aVar, final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                aVar.mo61663a(str);
            }
        });
    }

    /* renamed from: a */
    private void m2628a(final int i) {
        if (this.f2782b != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (C4869c.this.f2782b != null) {
                        C4869c.this.f2782b.mo61684a(i);
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public void mo61693a(String str) {
        if (str != null && str.equals(this.f2783c)) {
            this.f2781a = false;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0034 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2629a(android.content.Context r4, java.io.File r5, java.io.File r6) {
        /*
            r3 = this;
            r4 = 0
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0033, all -> 0x002a }
            r0.<init>(r5)     // Catch:{ Exception -> 0x0033, all -> 0x002a }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0034, all -> 0x0028 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0034, all -> 0x0028 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0026, all -> 0x0021 }
        L_0x000f:
            int r6 = r0.read(r4)     // Catch:{ Exception -> 0x0026, all -> 0x0021 }
            if (r6 <= 0) goto L_0x001a
            r1 = 0
            r5.write(r4, r1, r6)     // Catch:{ Exception -> 0x0026, all -> 0x0021 }
            goto L_0x000f
        L_0x001a:
            r0.close()     // Catch:{ Exception -> 0x003a }
            r5.close()     // Catch:{ Exception -> 0x003a }
            goto L_0x003a
        L_0x0021:
            r4 = move-exception
            r2 = r5
            r5 = r4
            r4 = r2
            goto L_0x002c
        L_0x0026:
            r4 = r5
            goto L_0x0034
        L_0x0028:
            r5 = move-exception
            goto L_0x002c
        L_0x002a:
            r5 = move-exception
            r0 = r4
        L_0x002c:
            r0.close()     // Catch:{ Exception -> 0x0032 }
            r4.close()     // Catch:{ Exception -> 0x0032 }
        L_0x0032:
            throw r5
        L_0x0033:
            r0 = r4
        L_0x0034:
            r0.close()     // Catch:{ Exception -> 0x003a }
            r4.close()     // Catch:{ Exception -> 0x003a }
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.video.C4869c.m2629a(android.content.Context, java.io.File, java.io.File):void");
    }

    /* renamed from: b */
    public boolean mo61694b(String str) {
        return str != null && str.endsWith(".temp");
    }

    /* renamed from: c */
    public void mo61695c(String str) {
        if (mo61694b(str)) {
            new File(str).delete();
        }
    }
}
