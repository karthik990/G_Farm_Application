package com.startapp.android.publish.ads.video;

import android.content.Context;
import android.support.p009v4.media.session.PlaybackStateCompat;
import com.startapp.android.publish.ads.video.p071a.C4850a;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.activities.FullScreenActivity;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.cache.C5085g;

/* renamed from: com.startapp.android.publish.ads.video.h */
/* compiled from: StartAppSDK */
public class C4923h {

    /* renamed from: com.startapp.android.publish.ads.video.h$a */
    /* compiled from: StartAppSDK */
    public enum C4924a {
        ELIGIBLE(""),
        INELIGIBLE_NO_STORAGE("Not enough storage for video"),
        INELIGIBLE_MISSING_ACTIVITY("FullScreenActivity not declared in AndroidManifest.xml"),
        INELIGIBLE_ERRORS_THRESHOLD_REACHED("Video errors threshold reached.");
        
        private String desctiption;

        private C4924a(String str) {
            this.desctiption = str;
        }

        /* renamed from: a */
        public String mo61817a() {
            return this.desctiption;
        }
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.DataInputStream, java.io.FileOutputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.io.DataInputStream] */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r8v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.DataInputStream] */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r3v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r5v7, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r6v8, types: [java.io.DataInputStream] */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r8v8, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: type inference failed for: r6v11 */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r6v12 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r8v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2, types: [java.io.DataInputStream, java.io.FileOutputStream, java.io.InputStream]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [?[OBJECT, ARRAY], java.io.InputStream, java.io.DataInputStream, java.io.FileOutputStream]
      mth insns count: 102
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
    /* JADX WARNING: Unknown variable types count: 20 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m2845a(android.content.Context r11, java.net.URL r12, java.lang.String r13) {
        /*
            java.lang.String r0 = ".temp"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Downloading video from "
            r1.append(r2)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "StartAppWall.VideoUtil"
            r3 = 3
            com.startapp.common.p092a.C5155g.m3807a(r2, r3, r1)
            r1 = 0
            java.lang.String r3 = m2844a(r11, r13)     // Catch:{ Exception -> 0x0097, all -> 0x0092 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0097, all -> 0x0092 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0097, all -> 0x0092 }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x0097, all -> 0x0092 }
            if (r5 == 0) goto L_0x0033
            r1.close()     // Catch:{ Exception -> 0x0032 }
            r1.close()     // Catch:{ Exception -> 0x0032 }
            r1.close()     // Catch:{ Exception -> 0x0032 }
        L_0x0032:
            return r3
        L_0x0033:
            java.io.InputStream r5 = r12.openStream()     // Catch:{ Exception -> 0x0097, all -> 0x0092 }
            java.io.DataInputStream r6 = new java.io.DataInputStream     // Catch:{ Exception -> 0x008f, all -> 0x008c }
            r6.<init>(r5)     // Catch:{ Exception -> 0x008f, all -> 0x008c }
            r7 = 4096(0x1000, float:5.74E-42)
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            r8.<init>()     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            r8.append(r13)     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            r8.append(r0)     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
            r9 = 0
            java.io.FileOutputStream r8 = r11.openFileOutput(r8, r9)     // Catch:{ Exception -> 0x0089, all -> 0x0086 }
        L_0x0054:
            int r10 = r6.read(r7)     // Catch:{ Exception -> 0x0084 }
            if (r10 <= 0) goto L_0x005e
            r8.write(r7, r9, r10)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0054
        L_0x005e:
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0084 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0084 }
            r9.<init>()     // Catch:{ Exception -> 0x0084 }
            r9.append(r13)     // Catch:{ Exception -> 0x0084 }
            r9.append(r0)     // Catch:{ Exception -> 0x0084 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0084 }
            java.lang.String r9 = m2844a(r11, r9)     // Catch:{ Exception -> 0x0084 }
            r7.<init>(r9)     // Catch:{ Exception -> 0x0084 }
            r7.renameTo(r4)     // Catch:{ Exception -> 0x0084 }
            r5.close()     // Catch:{ Exception -> 0x0082 }
            r6.close()     // Catch:{ Exception -> 0x0082 }
            r8.close()     // Catch:{ Exception -> 0x0082 }
        L_0x0082:
            r1 = r3
            goto L_0x00d3
        L_0x0084:
            r3 = move-exception
            goto L_0x009b
        L_0x0086:
            r11 = move-exception
            r8 = r1
            goto L_0x00d5
        L_0x0089:
            r3 = move-exception
            r8 = r1
            goto L_0x009b
        L_0x008c:
            r11 = move-exception
            r6 = r1
            goto L_0x0095
        L_0x008f:
            r3 = move-exception
            r6 = r1
            goto L_0x009a
        L_0x0092:
            r11 = move-exception
            r5 = r1
            r6 = r5
        L_0x0095:
            r8 = r6
            goto L_0x00d5
        L_0x0097:
            r3 = move-exception
            r5 = r1
            r6 = r5
        L_0x009a:
            r8 = r6
        L_0x009b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            r4.<init>()     // Catch:{ all -> 0x00d4 }
            java.lang.String r7 = "Error downloading video from "
            r4.append(r7)     // Catch:{ all -> 0x00d4 }
            r4.append(r12)     // Catch:{ all -> 0x00d4 }
            java.lang.String r12 = r4.toString()     // Catch:{ all -> 0x00d4 }
            android.util.Log.e(r2, r12, r3)     // Catch:{ all -> 0x00d4 }
            java.io.File r12 = new java.io.File     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            r2.<init>()     // Catch:{ all -> 0x00d4 }
            r2.append(r13)     // Catch:{ all -> 0x00d4 }
            r2.append(r0)     // Catch:{ all -> 0x00d4 }
            java.lang.String r13 = r2.toString()     // Catch:{ all -> 0x00d4 }
            java.lang.String r11 = m2844a(r11, r13)     // Catch:{ all -> 0x00d4 }
            r12.<init>(r11)     // Catch:{ all -> 0x00d4 }
            r12.delete()     // Catch:{ all -> 0x00d4 }
            r5.close()     // Catch:{ Exception -> 0x00d3 }
            r6.close()     // Catch:{ Exception -> 0x00d3 }
            r8.close()     // Catch:{ Exception -> 0x00d3 }
        L_0x00d3:
            return r1
        L_0x00d4:
            r11 = move-exception
        L_0x00d5:
            r5.close()     // Catch:{ Exception -> 0x00de }
            r6.close()     // Catch:{ Exception -> 0x00de }
            r8.close()     // Catch:{ Exception -> 0x00de }
        L_0x00de:
            goto L_0x00e0
        L_0x00df:
            throw r11
        L_0x00e0:
            goto L_0x00df
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.video.C4923h.m2845a(android.content.Context, java.net.URL, java.lang.String):java.lang.String");
    }

    /* renamed from: a */
    public static C4924a m2843a(Context context) {
        C4924a aVar = C4924a.ELIGIBLE;
        if (m2849c(context)) {
            aVar = C4924a.INELIGIBLE_ERRORS_THRESHOLD_REACHED;
        }
        if (!C4946i.m2925a(context, FullScreenActivity.class)) {
            aVar = C4924a.INELIGIBLE_MISSING_ACTIVITY;
        }
        return !m2850d(context) ? C4924a.INELIGIBLE_NO_STORAGE : aVar;
    }

    /* renamed from: b */
    public static void m2848b(Context context) {
        String str = "videoErrorsCount";
        C5051k.m3344b(context, str, Integer.valueOf(C5051k.m3337a(context, str, Integer.valueOf(0)).intValue() + 1));
    }

    /* renamed from: a */
    public static void m2846a(Context context, C4850a aVar) {
        if (aVar != null) {
            for (String b : aVar.mo61654a()) {
                C4988c.m3123b(context, b);
            }
        }
    }

    /* renamed from: a */
    public static boolean m2847a(String str) {
        for (C5085g gVar : C5071a.m3485a().mo62473d()) {
            if (gVar.mo62502b() instanceof C4893e) {
                C4893e eVar = (C4893e) gVar.mo62502b();
                if (!(eVar.mo61770d() == null || eVar.mo61770d().getLocalVideoPath() == null || !eVar.mo61770d().getLocalVideoPath().equals(str))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    static String m2844a(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/");
        sb.append(str);
        return sb.toString();
    }

    /* renamed from: c */
    private static boolean m2849c(Context context) {
        boolean z = false;
        if (C4983b.m3032a().mo62154H().mo62426e() < 0) {
            return false;
        }
        if (C5051k.m3337a(context, "videoErrorsCount", Integer.valueOf(0)).intValue() >= C4983b.m3032a().mo62154H().mo62426e()) {
            z = true;
        }
        return z;
    }

    /* renamed from: d */
    private static boolean m2850d(Context context) {
        long a = C4946i.m2902a(context.getFilesDir(), -1);
        boolean z = false;
        if (a < 0) {
            return false;
        }
        if (a / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID > C4983b.m3032a().mo62154H().mo62424c() * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            z = true;
        }
        return z;
    }
}
