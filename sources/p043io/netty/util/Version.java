package p043io.netty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/* renamed from: io.netty.util.Version */
public final class Version {
    private static final String PROP_BUILD_DATE = ".buildDate";
    private static final String PROP_COMMIT_DATE = ".commitDate";
    private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
    private static final String PROP_REPO_STATUS = ".repoStatus";
    private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
    private static final String PROP_VERSION = ".version";
    private final String artifactId;
    private final String artifactVersion;
    private final long buildTimeMillis;
    private final long commitTimeMillis;
    private final String longCommitHash;
    private final String repositoryStatus;
    private final String shortCommitHash;

    public static Map<String, Version> identify() {
        return identify(null);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:14|15|16|17|18|19) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0030 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.String, p043io.netty.util.Version> identify(java.lang.ClassLoader r21) {
        /*
            if (r21 != 0) goto L_0x0007
            java.lang.ClassLoader r0 = p043io.netty.util.internal.PlatformDependent.getContextClassLoader()
            goto L_0x0009
        L_0x0007:
            r0 = r21
        L_0x0009:
            java.util.Properties r1 = new java.util.Properties
            r1.<init>()
            java.lang.String r2 = "META-INF/io.netty.versions.properties"
            java.util.Enumeration r0 = r0.getResources(r2)     // Catch:{ Exception -> 0x0031 }
        L_0x0014:
            boolean r2 = r0.hasMoreElements()     // Catch:{ Exception -> 0x0031 }
            if (r2 == 0) goto L_0x0031
            java.lang.Object r2 = r0.nextElement()     // Catch:{ Exception -> 0x0031 }
            java.net.URL r2 = (java.net.URL) r2     // Catch:{ Exception -> 0x0031 }
            java.io.InputStream r2 = r2.openStream()     // Catch:{ Exception -> 0x0031 }
            r1.load(r2)     // Catch:{ all -> 0x002b }
            r2.close()     // Catch:{ Exception -> 0x0014 }
            goto L_0x0014
        L_0x002b:
            r0 = move-exception
            r3 = r0
            r2.close()     // Catch:{ Exception -> 0x0030 }
        L_0x0030:
            throw r3     // Catch:{ Exception -> 0x0031 }
        L_0x0031:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Set r2 = r1.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x003e:
            boolean r3 = r2.hasNext()
            java.lang.String r4 = ".repoStatus"
            java.lang.String r5 = ".longCommitHash"
            java.lang.String r6 = ".shortCommitHash"
            java.lang.String r7 = ".commitDate"
            java.lang.String r8 = ".buildDate"
            java.lang.String r9 = ".version"
            if (r3 == 0) goto L_0x00e9
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            r10 = 46
            int r10 = r3.indexOf(r10)
            if (r10 > 0) goto L_0x005f
            goto L_0x003e
        L_0x005f:
            r11 = 0
            java.lang.String r3 = r3.substring(r11, r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            boolean r9 = r1.containsKey(r9)
            if (r9 == 0) goto L_0x003e
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            boolean r8 = r1.containsKey(r8)
            if (r8 == 0) goto L_0x003e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            boolean r7 = r1.containsKey(r7)
            if (r7 == 0) goto L_0x003e
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r3)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            boolean r6 = r1.containsKey(r6)
            if (r6 == 0) goto L_0x003e
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r3)
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            boolean r5 = r1.containsKey(r5)
            if (r5 == 0) goto L_0x003e
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            boolean r4 = r1.containsKey(r4)
            if (r4 != 0) goto L_0x00e4
            goto L_0x003e
        L_0x00e4:
            r0.add(r3)
            goto L_0x003e
        L_0x00e9:
            java.util.TreeMap r2 = new java.util.TreeMap
            r2.<init>()
            java.util.Iterator r0 = r0.iterator()
        L_0x00f2:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0191
            java.lang.Object r3 = r0.next()
            java.lang.String r3 = (java.lang.String) r3
            io.netty.util.Version r15 = new io.netty.util.Version
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r9)
            java.lang.String r10 = r10.toString()
            java.lang.String r12 = r1.getProperty(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r8)
            java.lang.String r10 = r10.toString()
            java.lang.String r10 = r1.getProperty(r10)
            long r13 = parseIso8601(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r7)
            java.lang.String r10 = r10.toString()
            java.lang.String r10 = r1.getProperty(r10)
            long r16 = parseIso8601(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r6)
            java.lang.String r10 = r10.toString()
            java.lang.String r18 = r1.getProperty(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r5)
            java.lang.String r10 = r10.toString()
            java.lang.String r19 = r1.getProperty(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r3)
            r10.append(r4)
            java.lang.String r10 = r10.toString()
            java.lang.String r20 = r1.getProperty(r10)
            r10 = r15
            r11 = r3
            r21 = r0
            r0 = r15
            r15 = r16
            r17 = r18
            r18 = r19
            r19 = r20
            r10.<init>(r11, r12, r13, r15, r17, r18, r19)
            r2.put(r3, r0)
            r0 = r21
            goto L_0x00f2
        L_0x0191:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.Version.identify(java.lang.ClassLoader):java.util.Map");
    }

    private static long parseIso8601(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(str).getTime();
        } catch (ParseException unused) {
            return 0;
        }
    }

    public static void main(String[] strArr) {
        for (Version println : identify().values()) {
            System.err.println(println);
        }
    }

    private Version(String str, String str2, long j, long j2, String str3, String str4, String str5) {
        this.artifactId = str;
        this.artifactVersion = str2;
        this.buildTimeMillis = j;
        this.commitTimeMillis = j2;
        this.shortCommitHash = str3;
        this.longCommitHash = str4;
        this.repositoryStatus = str5;
    }

    public String artifactId() {
        return this.artifactId;
    }

    public String artifactVersion() {
        return this.artifactVersion;
    }

    public long buildTimeMillis() {
        return this.buildTimeMillis;
    }

    public long commitTimeMillis() {
        return this.commitTimeMillis;
    }

    public String shortCommitHash() {
        return this.shortCommitHash;
    }

    public String longCommitHash() {
        return this.longCommitHash;
    }

    public String repositoryStatus() {
        return this.repositoryStatus;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.artifactId);
        sb.append('-');
        sb.append(this.artifactVersion);
        sb.append('.');
        sb.append(this.shortCommitHash);
        if ("clean".equals(this.repositoryStatus)) {
            str = "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" (repository: ");
            sb2.append(this.repositoryStatus);
            sb2.append(')');
            str = sb2.toString();
        }
        sb.append(str);
        return sb.toString();
    }
}
