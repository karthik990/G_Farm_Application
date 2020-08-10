package org.apache.http.util;

import java.util.ArrayList;
import java.util.Map;

public class VersionInfo {
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    private final String infoClassloader;
    private final String infoModule;
    private final String infoPackage;
    private final String infoRelease;
    private final String infoTimestamp;

    protected VersionInfo(String str, String str2, String str3, String str4, String str5) {
        Args.notNull(str, "Package identifier");
        this.infoPackage = str;
        String str6 = UNAVAILABLE;
        if (str2 == null) {
            str2 = str6;
        }
        this.infoModule = str2;
        if (str3 == null) {
            str3 = str6;
        }
        this.infoRelease = str3;
        if (str4 == null) {
            str4 = str6;
        }
        this.infoTimestamp = str4;
        if (str5 != null) {
            str6 = str5;
        }
        this.infoClassloader = str6;
    }

    public final String getPackage() {
        return this.infoPackage;
    }

    public final String getModule() {
        return this.infoModule;
    }

    public final String getRelease() {
        return this.infoRelease;
    }

    public final String getTimestamp() {
        return this.infoTimestamp;
    }

    public final String getClassloader() {
        return this.infoClassloader;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.infoPackage.length() + 20 + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        sb.append("VersionInfo(");
        sb.append(this.infoPackage);
        sb.append(':');
        sb.append(this.infoModule);
        String str = this.infoRelease;
        String str2 = UNAVAILABLE;
        if (!str2.equals(str)) {
            sb.append(':');
            sb.append(this.infoRelease);
        }
        if (!str2.equals(this.infoTimestamp)) {
            sb.append(':');
            sb.append(this.infoTimestamp);
        }
        sb.append(')');
        if (!str2.equals(this.infoClassloader)) {
            sb.append('@');
            sb.append(this.infoClassloader);
        }
        return sb.toString();
    }

    public static VersionInfo[] loadVersionInfo(String[] strArr, ClassLoader classLoader) {
        Args.notNull(strArr, "Package identifier array");
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String loadVersionInfo : strArr) {
            VersionInfo loadVersionInfo2 = loadVersionInfo(loadVersionInfo, classLoader);
            if (loadVersionInfo2 != null) {
                arrayList.add(loadVersionInfo2);
            }
        }
        return (VersionInfo[]) arrayList.toArray(new VersionInfo[arrayList.size()]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.http.util.VersionInfo loadVersionInfo(java.lang.String r4, java.lang.ClassLoader r5) {
        /*
            java.lang.String r0 = "Package identifier"
            org.apache.http.util.Args.notNull(r4, r0)
            if (r5 == 0) goto L_0x0008
            goto L_0x0010
        L_0x0008:
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r5 = r5.getContextClassLoader()
        L_0x0010:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0048 }
            r1.<init>()     // Catch:{ IOException -> 0x0048 }
            r2 = 46
            r3 = 47
            java.lang.String r2 = r4.replace(r2, r3)     // Catch:{ IOException -> 0x0048 }
            r1.append(r2)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r2 = "/"
            r1.append(r2)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r2 = "version.properties"
            r1.append(r2)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0048 }
            java.io.InputStream r1 = r5.getResourceAsStream(r1)     // Catch:{ IOException -> 0x0048 }
            if (r1 == 0) goto L_0x0048
            java.util.Properties r2 = new java.util.Properties     // Catch:{ all -> 0x0043 }
            r2.<init>()     // Catch:{ all -> 0x0043 }
            r2.load(r1)     // Catch:{ all -> 0x0043 }
            r1.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x0049
        L_0x0041:
            goto L_0x0049
        L_0x0043:
            r2 = move-exception
            r1.close()     // Catch:{ IOException -> 0x0048 }
            throw r2     // Catch:{ IOException -> 0x0048 }
        L_0x0048:
            r2 = r0
        L_0x0049:
            if (r2 == 0) goto L_0x004f
            org.apache.http.util.VersionInfo r0 = fromMap(r4, r2, r5)
        L_0x004f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.util.VersionInfo.loadVersionInfo(java.lang.String, java.lang.ClassLoader):org.apache.http.util.VersionInfo");
    }

    protected static VersionInfo fromMap(String str, Map<?, ?> map, ClassLoader classLoader) {
        String str2;
        String str3;
        String str4;
        Args.notNull(str, "Package identifier");
        String str5 = null;
        if (map != null) {
            String str6 = (String) map.get(PROPERTY_MODULE);
            if (str6 != null && str6.length() < 1) {
                str6 = null;
            }
            String str7 = (String) map.get(PROPERTY_RELEASE);
            if (str7 != null && (str7.length() < 1 || str7.equals("${pom.version}"))) {
                str7 = null;
            }
            String str8 = (String) map.get(PROPERTY_TIMESTAMP);
            str2 = (str8 == null || (str8.length() >= 1 && !str8.equals("${mvn.timestamp}"))) ? str8 : null;
            str4 = str6;
            str3 = str7;
        } else {
            str4 = null;
            str3 = null;
            str2 = null;
        }
        if (classLoader != null) {
            str5 = classLoader.toString();
        }
        VersionInfo versionInfo = new VersionInfo(str, str4, str3, str2, str5);
        return versionInfo;
    }

    public static String getUserAgent(String str, String str2, Class<?> cls) {
        VersionInfo loadVersionInfo = loadVersionInfo(str2, cls.getClassLoader());
        return String.format("%s/%s (Java/%s)", new Object[]{str, loadVersionInfo != null ? loadVersionInfo.getRelease() : UNAVAILABLE, System.getProperty("java.version")});
    }
}
