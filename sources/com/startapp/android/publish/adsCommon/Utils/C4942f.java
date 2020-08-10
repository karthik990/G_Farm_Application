package com.startapp.android.publish.adsCommon.Utils;

import android.content.Context;
import android.os.Environment;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.common.p092a.C5155g;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.f */
/* compiled from: StartAppSDK */
public class C4942f {

    /* renamed from: a */
    private static boolean f3048a = true;

    /* renamed from: a */
    public static void m2883a(Context context, boolean z) {
        String str = "assets/";
        String str2 = "";
        String str3 = "Error initializing resources";
        String str4 = "ResourceHandler";
        String str5 = "copyDrawables";
        boolean z2 = true;
        Boolean valueOf = Boolean.valueOf(true);
        if (z) {
            f3048a = true;
            C5051k.m3342b(context, str5, valueOf);
        }
        if (f3048a) {
            f3048a = C5051k.m3335a(context, str5, valueOf).booleanValue();
            if (f3048a) {
                String str6 = "drawable-hdpi.zip";
                try {
                    String str7 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
                    String a = m2881a(context);
                    if (!m2884a(context, str7, str2, str6) && !m2884a(context, str7, str, str6) && !m2884a(context, a, str2, str6)) {
                        if (!m2884a(context, a, str, str6)) {
                            z2 = false;
                        }
                    }
                    if (!z2) {
                        C5155g.m3807a(str4, 5, str3);
                    }
                } catch (Exception e) {
                    C5155g.m3808a(str4, 6, str3, e);
                }
            }
        }
    }

    /* renamed from: a */
    private static String m2881a(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/Android/obb/");
        sb.append(context.getPackageName());
        sb.append("/");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("main.1.");
        sb3.append(context.getPackageName());
        sb3.append(".obb");
        String sb4 = sb3.toString();
        File file = new File(sb2);
        if (file.exists() && file.isDirectory()) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("main[.][1-9][0-9]*[.]");
            sb5.append(context.getPackageName());
            sb5.append("[.]obb");
            final Pattern compile = Pattern.compile(sb5.toString());
            File[] listFiles = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return compile.matcher(file.getName()).matches();
                }
            });
            if (listFiles.length > 0) {
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < listFiles.length; i3++) {
                    try {
                        int parseInt = Integer.parseInt(listFiles[i3].getName().split("[.]")[1]);
                        if (parseInt > i2) {
                            i = i3;
                            i2 = parseInt;
                        }
                    } catch (Exception unused) {
                    }
                }
                sb4 = listFiles[i].getName();
            }
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(sb2);
        sb6.append(sb4);
        return sb6.toString();
    }

    /* renamed from: a */
    private static boolean m2884a(Context context, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("Trying to copy resources from ");
        sb.append(str);
        String str4 = " in /";
        sb.append(str4);
        sb.append(str2);
        String str5 = "ResourceHandler";
        C5155g.m3807a(str5, 3, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str3);
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(context.getFilesDir().getPath());
        String str6 = "/";
        sb4.append(str6);
        sb4.append(str3);
        if (!m2885a(str, sb3, sb4.toString())) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Failed copying resources from ");
            sb5.append(str);
            sb5.append(str4);
            sb5.append(str2);
            C5155g.m3807a(str5, 3, sb5.toString());
            return false;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(context.getFilesDir().getPath());
        sb6.append(str6);
        sb6.append(str3);
        m2882a(context, sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append(str2);
        String str7 = "drawable.zip";
        sb7.append(str7);
        String sb8 = sb7.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(context.getFilesDir().getPath());
        sb9.append(str6);
        sb9.append(str7);
        m2885a(str, sb8, sb9.toString());
        StringBuilder sb10 = new StringBuilder();
        sb10.append(context.getFilesDir().getPath());
        sb10.append(str6);
        sb10.append(str7);
        m2882a(context, sb10.toString());
        C5051k.m3342b(context, "copyDrawables", Boolean.valueOf(false));
        StringBuilder sb11 = new StringBuilder();
        sb11.append("Copy from ");
        sb11.append(str);
        sb11.append(str4);
        sb11.append(str2);
        sb11.append(" succeeded");
        C5155g.m3807a(str5, 3, sb11.toString());
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0051, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0052, code lost:
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0058, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x005a, code lost:
        r6 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:54:0x006c */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0058 A[ExcHandler: all (th java.lang.Throwable), PHI: r2 
      PHI: (r2v4 java.util.zip.ZipFile) = (r2v1 java.util.zip.ZipFile), (r2v1 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile), (r2v5 java.util.zip.ZipFile) binds: [B:48:0x0061, B:49:?, B:3:0x0007, B:16:0x0030, B:17:?, B:19:0x0037, B:25:0x0046, B:26:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:3:0x0007] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static boolean m2885a(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r0 = 0
            r1 = 0
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            r2.<init>(r5)     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            java.util.Enumeration r5 = r2.entries()     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
        L_0x000b:
            boolean r3 = r5.hasMoreElements()     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            if (r3 == 0) goto L_0x0029
            java.lang.Object r3 = r5.nextElement()     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            java.util.zip.ZipEntry r3 = (java.util.zip.ZipEntry) r3     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            boolean r4 = r3.isDirectory()     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            if (r4 == 0) goto L_0x001e
            goto L_0x000b
        L_0x001e:
            java.lang.String r4 = r3.getName()     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            boolean r4 = r4.equals(r6)     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            if (r4 == 0) goto L_0x000b
            goto L_0x002a
        L_0x0029:
            r3 = r1
        L_0x002a:
            if (r3 == 0) goto L_0x0054
            java.io.InputStream r5 = r2.getInputStream(r3)     // Catch:{ IOException -> 0x005a, all -> 0x0058 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0051, all -> 0x0058 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0051, all -> 0x0058 }
            r7 = 256(0x100, float:3.59E-43)
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x0052, all -> 0x0058 }
        L_0x0039:
            int r1 = r5.read(r7)     // Catch:{ IOException -> 0x0052, all -> 0x0058 }
            if (r1 <= 0) goto L_0x0043
            r6.write(r7, r0, r1)     // Catch:{ IOException -> 0x0052, all -> 0x0058 }
            goto L_0x0039
        L_0x0043:
            r6.flush()     // Catch:{ IOException -> 0x0052, all -> 0x0058 }
            r5.close()     // Catch:{ IOException -> 0x004c, all -> 0x0058 }
            r6.close()     // Catch:{ IOException -> 0x004c, all -> 0x0058 }
        L_0x004c:
            r5 = 1
            r2.close()     // Catch:{ Exception -> 0x0050 }
        L_0x0050:
            return r5
        L_0x0051:
            r6 = r1
        L_0x0052:
            r1 = r5
            goto L_0x0061
        L_0x0054:
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0057:
            return r0
        L_0x0058:
            r5 = move-exception
            goto L_0x0068
        L_0x005a:
            r6 = r1
            goto L_0x0061
        L_0x005c:
            r5 = move-exception
            r2 = r1
            goto L_0x0068
        L_0x005f:
            r6 = r1
            r2 = r6
        L_0x0061:
            r1.close()     // Catch:{ Exception -> 0x006c, all -> 0x0058 }
            r6.close()     // Catch:{ Exception -> 0x006c, all -> 0x0058 }
            goto L_0x006c
        L_0x0068:
            r2.close()     // Catch:{ Exception -> 0x006b }
        L_0x006b:
            throw r5
        L_0x006c:
            r2.close()     // Catch:{ Exception -> 0x006f }
        L_0x006f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.Utils.C4942f.m2885a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:14|15|27|28|29|30|31) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0058 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x005b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0066 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void m2882a(android.content.Context r7, java.lang.String r8) {
        /*
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r0]
            r2 = 0
            java.util.zip.ZipInputStream r3 = new java.util.zip.ZipInputStream     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x006a, all -> 0x0061 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x006a, all -> 0x0061 }
            r4.<init>(r8)     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x006a, all -> 0x0061 }
            r3.<init>(r4)     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x006a, all -> 0x0061 }
            java.util.zip.ZipEntry r8 = r3.getNextEntry()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
        L_0x0013:
            if (r8 == 0) goto L_0x0058
            java.lang.String r8 = r8.getName()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            r5.<init>()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.io.File r6 = r7.getFilesDir()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.lang.String r6 = r6.getPath()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            r5.append(r6)     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.lang.String r6 = "/"
            r5.append(r6)     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            r5.append(r8)     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            java.lang.String r8 = r5.toString()     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
            r4.<init>(r8)     // Catch:{ FileNotFoundException | IOException -> 0x0058, all -> 0x005f }
        L_0x003a:
            r8 = 0
            int r2 = r3.read(r1, r8, r0)     // Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0055, all -> 0x0052 }
            r5 = -1
            if (r2 <= r5) goto L_0x0046
            r4.write(r1, r8, r2)     // Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0055, all -> 0x0052 }
            goto L_0x003a
        L_0x0046:
            r4.close()     // Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0055, all -> 0x0052 }
            r3.closeEntry()     // Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0055, all -> 0x0052 }
            java.util.zip.ZipEntry r8 = r3.getNextEntry()     // Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0055, all -> 0x0052 }
            r2 = r4
            goto L_0x0013
        L_0x0052:
            r7 = move-exception
            r2 = r4
            goto L_0x0063
        L_0x0055:
            r2 = r4
            goto L_0x0058
        L_0x0057:
            r2 = r4
        L_0x0058:
            r2.close()     // Catch:{ IOException -> 0x005b }
        L_0x005b:
            r3.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x006e
        L_0x005f:
            r7 = move-exception
            goto L_0x0063
        L_0x0061:
            r7 = move-exception
            r3 = r2
        L_0x0063:
            r2.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0066:
            r3.close()     // Catch:{ IOException -> 0x0069 }
        L_0x0069:
            throw r7
        L_0x006a:
            r3 = r2
            goto L_0x0058
        L_0x006c:
            r3 = r2
            goto L_0x0058
        L_0x006e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.Utils.C4942f.m2882a(android.content.Context, java.lang.String):void");
    }
}
