package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.internal.NativeLibraryLoader;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;

/* renamed from: io.netty.handler.ssl.OpenSsl */
public final class OpenSsl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    private static final String LINUX = "linux";
    static final String PROTOCOL_SSL_V2 = "SSLv2";
    static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
    static final String PROTOCOL_SSL_V3 = "SSLv3";
    static final String PROTOCOL_TLS_V1 = "TLSv1";
    static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
    static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    static final Set<String> SUPPORTED_PROTOCOLS_SET;
    private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_OCSP;
    private static final Throwable UNAVAILABILITY_CAUSE;
    private static final String UNKNOWN = "unknown";
    private static final boolean USE_KEYMANAGER_FACTORY;
    private static final InternalLogger logger;

    /* JADX WARNING: Can't wrap try/catch for region: R(3:45|46|47) */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        logger.debug("Hostname Verification not supported.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e2, code lost:
        r11 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        logger.debug("Failed to get useKeyManagerFactory system property.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0111, code lost:
        r14 = true;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00db */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x0107 */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0188 A[LOOP:1: B:102:0x0182->B:104:0x0188, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0200  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x020d  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x012d A[Catch:{ all -> 0x0139 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0132 A[Catch:{ all -> 0x0139 }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0148 A[Catch:{ all -> 0x0151 }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x014d A[Catch:{ all -> 0x0151 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:77:0x0135=Splitter:B:77:0x0135, B:94:0x0158=Splitter:B:94:0x0158} */
    static {
        /*
            java.lang.Class<io.netty.handler.ssl.OpenSsl> r0 = p043io.netty.handler.ssl.OpenSsl.class
            io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.internal.logging.InternalLoggerFactory.getInstance(r0)
            logger = r1
            r1 = 0
            r2 = 0
            java.lang.String r3 = "io.netty.internal.tcnative.SSL"
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0015 }
            java.lang.Class.forName(r3, r2, r0)     // Catch:{ ClassNotFoundException -> 0x0015 }
            r0 = r1
            goto L_0x0037
        L_0x0015:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "netty-tcnative not in the classpath; "
            r4.append(r5)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r5 = p043io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            java.lang.String r5 = " will be unavailable."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.debug(r4)
        L_0x0037:
            if (r0 != 0) goto L_0x008d
            loadTcNative()     // Catch:{ all -> 0x003e }
            r3 = r0
            goto L_0x0061
        L_0x003e:
            r0 = move-exception
            r3 = r0
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to load netty-tcnative; "
            r4.append(r5)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r5 = p043io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            java.lang.String r5 = " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.debug(r4, r3)
        L_0x0061:
            initializeTcNative()     // Catch:{ all -> 0x0066 }
            r0 = r1
            goto L_0x008d
        L_0x0066:
            r0 = move-exception
            r4 = r0
            if (r3 != 0) goto L_0x006b
            r3 = r4
        L_0x006b:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to initialize netty-tcnative; "
            r5.append(r6)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r6 = p043io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r6 = r6.getSimpleName()
            r5.append(r6)
            java.lang.String r6 = " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.debug(r5, r4)
            r0 = r3
        L_0x008d:
            UNAVAILABILITY_CAUSE = r0
            if (r0 != 0) goto L_0x021f
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r3 = io.netty.internal.tcnative.SSL.versionString()
            java.lang.String r4 = "netty-tcnative using native library: {}"
            r0.debug(r4, r3)
            java.util.LinkedHashSet r3 = new java.util.LinkedHashSet
            r0 = 128(0x80, float:1.794E-43)
            r3.<init>(r0)
            r0 = 31
            r4 = 1
            long r5 = io.netty.internal.tcnative.SSLContext.make(r0, r4)     // Catch:{ Exception -> 0x015e }
            java.lang.String r0 = "ALL"
            io.netty.internal.tcnative.SSLContext.setCipherSuite(r5, r0)     // Catch:{ all -> 0x0155 }
            long r7 = io.netty.internal.tcnative.SSL.newSSL(r5, r4)     // Catch:{ all -> 0x0155 }
            r9 = 0
            java.lang.String[] r0 = io.netty.internal.tcnative.SSL.getCiphers(r7)     // Catch:{ all -> 0x013d }
            int r11 = r0.length     // Catch:{ all -> 0x013d }
            r12 = 0
        L_0x00bb:
            if (r12 >= r11) goto L_0x00d4
            r13 = r0[r12]     // Catch:{ all -> 0x013d }
            if (r13 == 0) goto L_0x00d1
            boolean r14 = r13.isEmpty()     // Catch:{ all -> 0x013d }
            if (r14 != 0) goto L_0x00d1
            boolean r14 = r3.contains(r13)     // Catch:{ all -> 0x013d }
            if (r14 == 0) goto L_0x00ce
            goto L_0x00d1
        L_0x00ce:
            r3.add(r13)     // Catch:{ all -> 0x013d }
        L_0x00d1:
            int r12 = r12 + 1
            goto L_0x00bb
        L_0x00d4:
            java.lang.String r0 = "netty.io"
            io.netty.internal.tcnative.SSL.setHostNameValidation(r7, r2, r0)     // Catch:{ all -> 0x00db }
            r11 = 1
            goto L_0x00e3
        L_0x00db:
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x013d }
            java.lang.String r11 = "Hostname Verification not supported."
            r0.debug(r11)     // Catch:{ all -> 0x013d }
            r11 = 0
        L_0x00e3:
            io.netty.handler.ssl.util.SelfSignedCertificate r0 = new io.netty.handler.ssl.util.SelfSignedCertificate     // Catch:{ all -> 0x011a }
            r0.<init>()     // Catch:{ all -> 0x011a }
            java.security.cert.X509Certificate[] r1 = new java.security.cert.X509Certificate[r4]     // Catch:{ all -> 0x0116 }
            java.security.cert.X509Certificate r12 = r0.cert()     // Catch:{ all -> 0x0116 }
            r1[r2] = r12     // Catch:{ all -> 0x0116 }
            long r12 = p043io.netty.handler.ssl.ReferenceCountedOpenSslContext.toBIO(r1)     // Catch:{ all -> 0x0116 }
            io.netty.internal.tcnative.SSL.setCertificateChainBio(r7, r12, r2)     // Catch:{ all -> 0x0113 }
            io.netty.handler.ssl.OpenSsl$1 r1 = new io.netty.handler.ssl.OpenSsl$1     // Catch:{ all -> 0x0107 }
            r1.<init>()     // Catch:{ all -> 0x0107 }
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)     // Catch:{ all -> 0x0107 }
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x0107 }
            boolean r2 = r1.booleanValue()     // Catch:{ all -> 0x0107 }
            goto L_0x010e
        L_0x0107:
            io.netty.util.internal.logging.InternalLogger r1 = logger     // Catch:{ all -> 0x0111 }
            java.lang.String r14 = "Failed to get useKeyManagerFactory system property."
            r1.debug(r14)     // Catch:{ all -> 0x0111 }
        L_0x010e:
            r1 = r2
            r2 = 1
            goto L_0x0126
        L_0x0111:
            r14 = 1
            goto L_0x0114
        L_0x0113:
            r14 = 0
        L_0x0114:
            r1 = r0
            goto L_0x011c
        L_0x0116:
            r14 = 0
            r1 = r0
            r12 = r9
            goto L_0x011c
        L_0x011a:
            r12 = r9
            r14 = 0
        L_0x011c:
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x013b }
            java.lang.String r15 = "KeyManagerFactory not supported."
            r0.debug(r15)     // Catch:{ all -> 0x013b }
            r0 = r1
            r2 = r14
            r1 = 0
        L_0x0126:
            io.netty.internal.tcnative.SSL.freeSSL(r7)     // Catch:{ all -> 0x0139 }
            int r7 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r7 == 0) goto L_0x0130
            io.netty.internal.tcnative.SSL.freeBIO(r12)     // Catch:{ all -> 0x0139 }
        L_0x0130:
            if (r0 == 0) goto L_0x0135
            r0.delete()     // Catch:{ all -> 0x0139 }
        L_0x0135:
            io.netty.internal.tcnative.SSLContext.free(r5)     // Catch:{ Exception -> 0x015c }
            goto L_0x0168
        L_0x0139:
            r0 = move-exception
            goto L_0x0158
        L_0x013b:
            r0 = move-exception
            goto L_0x0141
        L_0x013d:
            r0 = move-exception
            r12 = r9
            r11 = 0
            r14 = 0
        L_0x0141:
            io.netty.internal.tcnative.SSL.freeSSL(r7)     // Catch:{ all -> 0x0151 }
            int r7 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r7 == 0) goto L_0x014b
            io.netty.internal.tcnative.SSL.freeBIO(r12)     // Catch:{ all -> 0x0151 }
        L_0x014b:
            if (r1 == 0) goto L_0x0150
            r1.delete()     // Catch:{ all -> 0x0151 }
        L_0x0150:
            throw r0     // Catch:{ all -> 0x0151 }
        L_0x0151:
            r0 = move-exception
            r2 = r14
            r1 = 0
            goto L_0x0158
        L_0x0155:
            r0 = move-exception
            r1 = 0
            r11 = 0
        L_0x0158:
            io.netty.internal.tcnative.SSLContext.free(r5)     // Catch:{ Exception -> 0x015c }
            throw r0     // Catch:{ Exception -> 0x015c }
        L_0x015c:
            r0 = move-exception
            goto L_0x0161
        L_0x015e:
            r0 = move-exception
            r1 = 0
            r11 = 0
        L_0x0161:
            io.netty.util.internal.logging.InternalLogger r5 = logger
            java.lang.String r6 = "Failed to get the list of available OpenSSL cipher suites."
            r5.warn(r6, r0)
        L_0x0168:
            java.util.Set r0 = java.util.Collections.unmodifiableSet(r3)
            AVAILABLE_OPENSSL_CIPHER_SUITES = r0
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            java.util.Set<java.lang.String> r3 = AVAILABLE_OPENSSL_CIPHER_SUITES
            int r3 = r3.size()
            r5 = 2
            int r3 = r3 * 2
            r0.<init>(r3)
            java.util.Set<java.lang.String> r3 = AVAILABLE_OPENSSL_CIPHER_SUITES
            java.util.Iterator r3 = r3.iterator()
        L_0x0182:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x01a1
            java.lang.Object r6 = r3.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = "TLS"
            java.lang.String r7 = p043io.netty.handler.ssl.CipherSuiteConverter.toJava(r6, r7)
            r0.add(r7)
            java.lang.String r7 = "SSL"
            java.lang.String r6 = p043io.netty.handler.ssl.CipherSuiteConverter.toJava(r6, r7)
            r0.add(r6)
            goto L_0x0182
        L_0x01a1:
            java.util.Set r0 = java.util.Collections.unmodifiableSet(r0)
            AVAILABLE_JAVA_CIPHER_SUITES = r0
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            java.util.Set<java.lang.String> r3 = AVAILABLE_OPENSSL_CIPHER_SUITES
            int r3 = r3.size()
            java.util.Set<java.lang.String> r6 = AVAILABLE_JAVA_CIPHER_SUITES
            int r6 = r6.size()
            int r3 = r3 + r6
            r0.<init>(r3)
            java.util.Set<java.lang.String> r3 = AVAILABLE_OPENSSL_CIPHER_SUITES
            r0.addAll(r3)
            java.util.Set<java.lang.String> r3 = AVAILABLE_JAVA_CIPHER_SUITES
            r0.addAll(r3)
            AVAILABLE_CIPHER_SUITES = r0
            SUPPORTS_KEYMANAGER_FACTORY = r2
            SUPPORTS_HOSTNAME_VALIDATION = r11
            USE_KEYMANAGER_FACTORY = r1
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r1 = 6
            r0.<init>(r1)
            java.lang.String r1 = "SSLv2Hello"
            r0.add(r1)
            boolean r1 = doesSupportProtocol(r4)
            if (r1 == 0) goto L_0x01e1
            java.lang.String r1 = "SSLv2"
            r0.add(r1)
        L_0x01e1:
            boolean r1 = doesSupportProtocol(r5)
            if (r1 == 0) goto L_0x01ec
            java.lang.String r1 = "SSLv3"
            r0.add(r1)
        L_0x01ec:
            r1 = 4
            boolean r1 = doesSupportProtocol(r1)
            if (r1 == 0) goto L_0x01f8
            java.lang.String r1 = "TLSv1"
            r0.add(r1)
        L_0x01f8:
            r1 = 8
            boolean r1 = doesSupportProtocol(r1)
            if (r1 == 0) goto L_0x0205
            java.lang.String r1 = "TLSv1.1"
            r0.add(r1)
        L_0x0205:
            r1 = 16
            boolean r1 = doesSupportProtocol(r1)
            if (r1 == 0) goto L_0x0212
            java.lang.String r1 = "TLSv1.2"
            r0.add(r1)
        L_0x0212:
            java.util.Set r0 = java.util.Collections.unmodifiableSet(r0)
            SUPPORTED_PROTOCOLS_SET = r0
            boolean r0 = doesSupportOcsp()
            SUPPORTS_OCSP = r0
            goto L_0x023f
        L_0x021f:
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_OPENSSL_CIPHER_SUITES = r0
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_JAVA_CIPHER_SUITES = r0
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_CIPHER_SUITES = r0
            SUPPORTS_KEYMANAGER_FACTORY = r2
            SUPPORTS_HOSTNAME_VALIDATION = r2
            USE_KEYMANAGER_FACTORY = r2
            java.util.Set r0 = java.util.Collections.emptySet()
            SUPPORTED_PROTOCOLS_SET = r0
            SUPPORTS_OCSP = r2
        L_0x023f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.OpenSsl.<clinit>():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean doesSupportOcsp() {
        /*
            int r0 = version()
            long r0 = (long) r0
            r2 = 1
            r3 = 0
            r4 = 268443648(0x10002000, double:1.326287843E-315)
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x0037
            r0 = 16
            r4 = -1
            long r0 = io.netty.internal.tcnative.SSLContext.make(r0, r2)     // Catch:{ Exception -> 0x002f, all -> 0x0025 }
            io.netty.internal.tcnative.SSLContext.enableOcsp(r0, r3)     // Catch:{ Exception -> 0x0023, all -> 0x0021 }
            int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x0038
            io.netty.internal.tcnative.SSLContext.free(r0)
            goto L_0x0038
        L_0x0021:
            r2 = move-exception
            goto L_0x0027
        L_0x0023:
            goto L_0x0030
        L_0x0025:
            r2 = move-exception
            r0 = r4
        L_0x0027:
            int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x002e
            io.netty.internal.tcnative.SSLContext.free(r0)
        L_0x002e:
            throw r2
        L_0x002f:
            r0 = r4
        L_0x0030:
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x0037
            io.netty.internal.tcnative.SSLContext.free(r0)
        L_0x0037:
            r2 = 0
        L_0x0038:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.OpenSsl.doesSupportOcsp():boolean");
    }

    private static boolean doesSupportProtocol(int i) {
        try {
            long make = SSLContext.make(i, 2);
            if (make != -1) {
                SSLContext.free(make);
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static boolean isAlpnSupported() {
        return ((long) version()) >= 268443648;
    }

    public static boolean isOcspSupported() {
        return SUPPORTS_OCSP;
    }

    public static int version() {
        if (isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE));
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    @Deprecated
    public static Set<String> availableCipherSuites() {
        return availableOpenSslCipherSuites();
    }

    public static Set<String> availableOpenSslCipherSuites() {
        return AVAILABLE_OPENSSL_CIPHER_SUITES;
    }

    public static Set<String> availableJavaCipherSuites() {
        return AVAILABLE_JAVA_CIPHER_SUITES;
    }

    public static boolean isCipherSuiteAvailable(String str) {
        String openSsl = CipherSuiteConverter.toOpenSsl(str);
        if (openSsl != null) {
            str = openSsl;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(str);
    }

    public static boolean supportsKeyManagerFactory() {
        return SUPPORTS_KEYMANAGER_FACTORY;
    }

    public static boolean supportsHostnameValidation() {
        return SUPPORTS_HOSTNAME_VALIDATION;
    }

    static boolean useKeyManagerFactory() {
        return USE_KEYMANAGER_FACTORY;
    }

    static long memoryAddress(ByteBuf byteBuf) {
        return byteBuf.hasMemoryAddress() ? byteBuf.memoryAddress() : Buffer.address(byteBuf.nioBuffer());
    }

    private OpenSsl() {
    }

    private static void loadTcNative() throws Exception {
        String str = "";
        String normalizeOs = normalizeOs(SystemPropertyUtil.get("os.name", str));
        String normalizeArch = normalizeArch(SystemPropertyUtil.get("os.arch", str));
        LinkedHashSet linkedHashSet = new LinkedHashSet(3);
        StringBuilder sb = new StringBuilder();
        String str2 = "netty-tcnative-";
        sb.append(str2);
        sb.append(normalizeOs);
        sb.append('-');
        sb.append(normalizeArch);
        linkedHashSet.add(sb.toString());
        if (LINUX.equalsIgnoreCase(normalizeOs)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(normalizeOs);
            sb2.append('-');
            sb2.append(normalizeArch);
            sb2.append("-fedora");
            linkedHashSet.add(sb2.toString());
        }
        linkedHashSet.add("netty-tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[]) linkedHashSet.toArray(new String[linkedHashSet.size()]));
    }

    private static boolean initializeTcNative() throws Exception {
        return Library.initialize();
    }

    private static String normalizeOs(String str) {
        String normalize = normalize(str);
        String str2 = "aix";
        if (normalize.startsWith(str2)) {
            return str2;
        }
        String str3 = "hpux";
        if (normalize.startsWith(str3)) {
            return str3;
        }
        String str4 = "os400";
        if (normalize.startsWith(str4) && (normalize.length() <= 5 || !Character.isDigit(normalize.charAt(5)))) {
            return str4;
        }
        String str5 = LINUX;
        if (normalize.startsWith(str5)) {
            return str5;
        }
        String str6 = "osx";
        if (!normalize.startsWith("macosx") && !normalize.startsWith(str6)) {
            String str7 = "freebsd";
            if (normalize.startsWith(str7)) {
                return str7;
            }
            String str8 = "openbsd";
            if (normalize.startsWith(str8)) {
                return str8;
            }
            String str9 = "netbsd";
            if (normalize.startsWith(str9)) {
                return str9;
            }
            str6 = "sunos";
            if (!normalize.startsWith("solaris") && !normalize.startsWith(str6)) {
                String str10 = "windows";
                return normalize.startsWith(str10) ? str10 : "unknown";
            }
        }
        return str6;
    }

    private static String normalizeArch(String str) {
        String normalize = normalize(str);
        if (normalize.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (normalize.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (normalize.matches("^(ia64|itanium64)$")) {
            return "itanium_64";
        }
        if (normalize.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (normalize.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (normalize.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(normalize)) {
            return "aarch_64";
        }
        if (normalize.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if ("ppc64".equals(normalize)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(normalize)) {
            return "ppcle_64";
        }
        if ("s390".equals(normalize)) {
            return "s390_32";
        }
        return "s390x".equals(normalize) ? "s390_64" : "unknown";
    }

    private static String normalize(String str) {
        return str.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    static void releaseIfNeeded(ReferenceCounted referenceCounted) {
        if (referenceCounted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
    }
}
