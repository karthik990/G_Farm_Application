package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/* renamed from: com.flurry.sdk.cr */
public final class C1673cr {

    /* renamed from: a */
    public static final Charset f848a = Charset.forName("UTF-8");

    /* renamed from: d */
    private static String m737d(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getPackageName());
        sb.append(".variants");
        return sb.toString();
    }

    /* renamed from: a */
    public static synchronized boolean m728a(Context context) {
        boolean b;
        synchronized (C1673cr.class) {
            b = m733b(context, m737d(context));
        }
        return b;
    }

    /* renamed from: b */
    private static synchronized boolean m733b(Context context, String str) {
        boolean exists;
        synchronized (C1673cr.class) {
            exists = context.getFileStreamPath(str).exists();
        }
        return exists;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:43|(2:45|46)|47|48) */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x002a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x005d */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0042 A[SYNTHETIC, Splitter:B:26:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0052 A[SYNTHETIC, Splitter:B:38:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x005a A[SYNTHETIC, Splitter:B:45:0x005a] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:13:0x002a=Splitter:B:13:0x002a, B:47:0x005d=Splitter:B:47:0x005d} */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String m732b(android.content.Context r6) {
        /*
            java.lang.Class<com.flurry.sdk.cr> r0 = com.flurry.sdk.C1673cr.class
            monitor-enter(r0)
            r1 = 0
            java.lang.String r2 = m737d(r6)     // Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0037, all -> 0x0032 }
            java.io.FileInputStream r6 = r6.openFileInput(r2)     // Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0037, all -> 0x0032 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            r2.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            r3.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            r2.<init>()     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
        L_0x001b:
            java.lang.String r4 = r3.readLine()     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            if (r4 == 0) goto L_0x0025
            r2.append(r4)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030 }
            goto L_0x001b
        L_0x0025:
            if (r6 == 0) goto L_0x002a
            r6.close()     // Catch:{ IOException -> 0x002a }
        L_0x002a:
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x005e }
            monitor-exit(r0)
            return r6
        L_0x0030:
            r2 = move-exception
            goto L_0x0039
        L_0x0032:
            r6 = move-exception
            r5 = r1
            r1 = r6
            r6 = r5
            goto L_0x0058
        L_0x0037:
            r2 = move-exception
            r6 = r1
        L_0x0039:
            java.lang.String r3 = "ConfigUtils"
            java.lang.String r4 = "Error in reading file!"
            com.flurry.sdk.C1685cy.m757a(r3, r4, r2)     // Catch:{ all -> 0x0057 }
            if (r6 == 0) goto L_0x0045
            r6.close()     // Catch:{ IOException -> 0x0045 }
        L_0x0045:
            monitor-exit(r0)
            return r1
        L_0x0047:
            r6 = r1
        L_0x0048:
            r2 = 5
            java.lang.String r3 = "ConfigUtils"
            java.lang.String r4 = "File not found!"
            com.flurry.sdk.C1685cy.m754a(r2, r3, r4)     // Catch:{ all -> 0x0057 }
            if (r6 == 0) goto L_0x0055
            r6.close()     // Catch:{ IOException -> 0x0055 }
        L_0x0055:
            monitor-exit(r0)
            return r1
        L_0x0057:
            r1 = move-exception
        L_0x0058:
            if (r6 == 0) goto L_0x005d
            r6.close()     // Catch:{ IOException -> 0x005d }
        L_0x005d:
            throw r1     // Catch:{ all -> 0x005e }
        L_0x005e:
            r6 = move-exception
            monitor-exit(r0)
            goto L_0x0062
        L_0x0061:
            throw r6
        L_0x0062:
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1673cr.m732b(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0035, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x003b */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m726a(android.content.Context r4, java.lang.String r5) {
        /*
            java.lang.Class<com.flurry.sdk.cr> r0 = com.flurry.sdk.C1673cr.class
            monitor-enter(r0)
            if (r5 != 0) goto L_0x0007
            monitor-exit(r0)
            return
        L_0x0007:
            r1 = 0
            java.lang.String r2 = m737d(r4)     // Catch:{ IOException -> 0x0023 }
            r3 = 0
            java.io.FileOutputStream r1 = r4.openFileOutput(r2, r3)     // Catch:{ IOException -> 0x0023 }
            byte[] r4 = r5.getBytes()     // Catch:{ IOException -> 0x0023 }
            r1.write(r4)     // Catch:{ IOException -> 0x0023 }
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x001f }
            monitor-exit(r0)
            return
        L_0x001f:
            monitor-exit(r0)
            return
        L_0x0021:
            r4 = move-exception
            goto L_0x0036
        L_0x0023:
            r4 = move-exception
            java.lang.String r5 = "ConfigUtils"
            java.lang.String r2 = "Error in writing data to file"
            com.flurry.sdk.C1685cy.m757a(r5, r2, r4)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x0032 }
            monitor-exit(r0)
            return
        L_0x0032:
            monitor-exit(r0)
            return
        L_0x0034:
            monitor-exit(r0)
            return
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ IOException -> 0x003b }
        L_0x003b:
            throw r4     // Catch:{ all -> 0x003c }
        L_0x003c:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1673cr.m726a(android.content.Context, java.lang.String):void");
    }

    /* renamed from: c */
    public static synchronized boolean m735c(Context context) {
        boolean deleteFile;
        synchronized (C1673cr.class) {
            deleteFile = context.deleteFile(m737d(context));
            if (deleteFile) {
                C1685cy.m754a(5, "ConfigUtils", "File removed from memory");
            } else {
                C1685cy.m754a(5, "ConfigUtils", "Error in clearing data from memory");
            }
        }
        return deleteFile;
    }

    /* renamed from: a */
    public static boolean m730a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String str4 = (String) C1674cs.f849a.get(str);
        if (str4 == null) {
            return false;
        }
        if (str.indexOf("com.flurry.configkey.prod.ec.") != -1) {
            return m736c(str4, str2, str3);
        }
        return m734b(str4, str2, str3);
    }

    /* renamed from: b */
    public static boolean m734b(String str, String str2, String str3) {
        return m731a(str, str2, str3, "RSA", "SHA256withRSA");
    }

    /* renamed from: c */
    public static boolean m736c(String str, String str2, String str3) {
        return m731a(str, str2, str3, "EC", "SHA256withECDSA");
    }

    /* renamed from: a */
    private static boolean m731a(String str, String str2, String str3, String str4, String str5) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance(str4).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
            Signature instance = Signature.getInstance(str5);
            instance.initVerify(generatePublic);
            instance.update(str2.getBytes(f848a));
            return instance.verify(Base64.decode(str3, 0));
        } catch (GeneralSecurityException e) {
            C1685cy.m762b("ConfigUtils", "GeneralSecurityException for Signature: ".concat(String.valueOf(e)));
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m727a() {
        try {
            KeyFactory.getInstance("EC");
            Signature.getInstance("SHA256withECDSA");
            return true;
        } catch (NoSuchAlgorithmException e) {
            C1685cy.m756a("ConfigUtils", "ECDSA encryption is not available: ".concat(String.valueOf(e)));
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m729a(String str) {
        return "com.flurry.configkey.prod.ec.2".equals(str) || "com.flurry.configkey.prod.rot.7".equals(str) || "com.flurry.configkey.prod.fs.0".equals(str);
    }
}
