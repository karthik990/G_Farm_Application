package com.flurry.sdk;

import android.content.Context;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* renamed from: com.flurry.sdk.dy */
public final class C1732dy {

    /* renamed from: a */
    private static String f989a = "FileUtil";

    /* renamed from: a */
    public static File m859a() {
        return C1576b.m502a().getFilesDir();
    }

    /* renamed from: b */
    public static File m863b() {
        File file;
        Context a = C1576b.m502a();
        if (C1734dz.m872a(21)) {
            file = a.getNoBackupFilesDir();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(a.getFilesDir().getPath());
            sb.append(File.separator);
            sb.append("no_backup");
            file = new File(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getPath());
        sb2.append(File.separator);
        sb2.append(".flurryNoBackup");
        return new File(sb2.toString());
    }

    /* renamed from: a */
    public static boolean m861a(File file) {
        if (file == null || file.getAbsoluteFile() == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null || parentFile.mkdirs() || parentFile.isDirectory()) {
            return true;
        }
        C1685cy.m754a(6, f989a, "Unable to create persistent dir: ".concat(String.valueOf(parentFile)));
        return false;
    }

    /* renamed from: b */
    public static boolean m864b(File file) {
        if (file == null || !file.isDirectory()) {
            return false;
        }
        for (String file2 : file.list()) {
            if (!m864b(new File(file, file2))) {
                return false;
            }
        }
        return file.delete();
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x005b A[RETURN] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m865c(java.io.File r6) {
        /*
            r0 = 4
            r1 = 0
            if (r6 == 0) goto L_0x0061
            boolean r2 = r6.exists()
            if (r2 != 0) goto L_0x000b
            goto L_0x0061
        L_0x000b:
            java.lang.String r2 = f989a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Loading persistent data: "
            r3.<init>(r4)
            java.lang.String r4 = r6.getAbsolutePath()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.flurry.sdk.C1685cy.m754a(r0, r2, r3)
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ all -> 0x0046 }
            r0.<init>(r6)     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0044 }
            r6.<init>()     // Catch:{ all -> 0x0044 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0044 }
        L_0x0030:
            int r3 = r0.read(r2)     // Catch:{ all -> 0x0044 }
            if (r3 <= 0) goto L_0x0040
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x0044 }
            r5 = 0
            r4.<init>(r2, r5, r3)     // Catch:{ all -> 0x0044 }
            r6.append(r4)     // Catch:{ all -> 0x0044 }
            goto L_0x0030
        L_0x0040:
            com.flurry.sdk.C1734dz.m871a(r0)
            goto L_0x0054
        L_0x0044:
            r6 = move-exception
            goto L_0x0048
        L_0x0046:
            r6 = move-exception
            r0 = r1
        L_0x0048:
            r2 = 6
            java.lang.String r3 = f989a     // Catch:{ all -> 0x005c }
            java.lang.String r4 = "Error when loading persistent file"
            com.flurry.sdk.C1685cy.m755a(r2, r3, r4, r6)     // Catch:{ all -> 0x005c }
            com.flurry.sdk.C1734dz.m871a(r0)
            r6 = r1
        L_0x0054:
            if (r6 == 0) goto L_0x005b
            java.lang.String r6 = r6.toString()
            return r6
        L_0x005b:
            return r1
        L_0x005c:
            r6 = move-exception
            com.flurry.sdk.C1734dz.m871a(r0)
            throw r6
        L_0x0061:
            java.lang.String r6 = f989a
            java.lang.String r2 = "Persistent file doesn't exist."
            com.flurry.sdk.C1685cy.m754a(r0, r6, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1732dy.m865c(java.io.File):java.lang.String");
    }

    /* renamed from: a */
    public static void m860a(File file, String str) {
        if (file == null) {
            C1685cy.m754a(4, f989a, "No persistent file specified.");
        } else if (str == null) {
            String str2 = f989a;
            StringBuilder sb = new StringBuilder("No data specified; deleting persistent file: ");
            sb.append(file.getAbsolutePath());
            C1685cy.m754a(4, str2, sb.toString());
            file.delete();
        } else {
            String str3 = f989a;
            StringBuilder sb2 = new StringBuilder("Writing persistent data: ");
            sb2.append(file.getAbsolutePath());
            C1685cy.m754a(4, str3, sb2.toString());
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(str.getBytes());
                    C1734dz.m871a((Closeable) fileOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    try {
                        C1685cy.m755a(6, f989a, "Error writing persistent file", th);
                    } finally {
                        C1734dz.m871a((Closeable) fileOutputStream);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                C1685cy.m755a(6, f989a, "Error writing persistent file", th);
            }
        }
    }

    /* renamed from: a */
    public static String[] m862a(File file, final Pattern pattern) {
        if (!file.exists()) {
            return new String[0];
        }
        String[] list = file.list(new FilenameFilter() {
            public final boolean accept(File file, String str) {
                return pattern.matcher(str).matches();
            }
        });
        if (list == null) {
            list = new String[0];
        }
        return list;
    }
}
