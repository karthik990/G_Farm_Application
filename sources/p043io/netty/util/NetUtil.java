package p043io.netty.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.NetUtil */
public final class NetUtil {
    private static final int IPV4_MAX_CHAR_BETWEEN_SEPARATOR = 3;
    private static final boolean IPV4_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv4Stack", false);
    private static final int IPV4_SEPARATORS = 3;
    private static final boolean IPV6_ADDRESSES_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv6Addresses", false);
    private static final int IPV6_BYTE_COUNT = 16;
    private static final int IPV6_MAX_CHAR_BETWEEN_SEPARATOR = 4;
    private static final int IPV6_MAX_CHAR_COUNT = 39;
    private static final int IPV6_MAX_SEPARATORS = 8;
    private static final int IPV6_MIN_SEPARATORS = 2;
    private static final int IPV6_WORD_COUNT = 8;
    public static final InetAddress LOCALHOST;
    public static final Inet4Address LOCALHOST4;
    public static final Inet6Address LOCALHOST6;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN = ((Integer) AccessController.doPrivileged(new PrivilegedAction<Integer>() {
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x007f, code lost:
            if (r4 != null) goto L_0x0081;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x009e, code lost:
            if (r4 != null) goto L_0x0081;
         */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00a8 A[SYNTHETIC, Splitter:B:43:0x00a8] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Integer run() {
            /*
                r9 = this;
                java.lang.String r0 = "Failed to get SOMAXCONN from sysctl and file {}. Default: {}"
                boolean r1 = p043io.netty.util.internal.PlatformDependent.isWindows()
                if (r1 == 0) goto L_0x000b
                r1 = 200(0xc8, float:2.8E-43)
                goto L_0x000d
            L_0x000b:
                r1 = 128(0x80, float:1.794E-43)
            L_0x000d:
                java.io.File r2 = new java.io.File
                java.lang.String r3 = "/proc/sys/net/core/somaxconn"
                r2.<init>(r3)
                r3 = 0
                r4 = 0
                boolean r5 = r2.exists()     // Catch:{ Exception -> 0x0087 }
                if (r5 == 0) goto L_0x004f
                java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0087 }
                java.io.FileReader r6 = new java.io.FileReader     // Catch:{ Exception -> 0x0087 }
                r6.<init>(r2)     // Catch:{ Exception -> 0x0087 }
                r5.<init>(r6)     // Catch:{ Exception -> 0x0087 }
                java.lang.String r4 = r5.readLine()     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                int r1 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.NetUtil.logger     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                boolean r4 = r4.isDebugEnabled()     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                if (r4 == 0) goto L_0x0045
                io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.NetUtil.logger     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                java.lang.String r6 = "{}: {}"
                java.lang.Integer r7 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
                r4.debug(r6, r2, r7)     // Catch:{ Exception -> 0x004a, all -> 0x0047 }
            L_0x0045:
                r4 = r5
                goto L_0x007f
            L_0x0047:
                r0 = move-exception
                r4 = r5
                goto L_0x00a6
            L_0x004a:
                r4 = move-exception
                r8 = r5
                r5 = r4
                r4 = r8
                goto L_0x0088
            L_0x004f:
                java.lang.String r5 = "io.netty.net.somaxconn.trySysctl"
                boolean r5 = p043io.netty.util.internal.SystemPropertyUtil.getBoolean(r5, r3)     // Catch:{ Exception -> 0x0087 }
                if (r5 == 0) goto L_0x0071
                java.lang.String r5 = "kern.ipc.somaxconn"
                java.lang.Integer r5 = p043io.netty.util.NetUtil.sysctlGetInt(r5)     // Catch:{ Exception -> 0x0087 }
                if (r5 != 0) goto L_0x006c
                java.lang.String r5 = "kern.ipc.soacceptqueue"
                java.lang.Integer r5 = p043io.netty.util.NetUtil.sysctlGetInt(r5)     // Catch:{ Exception -> 0x0087 }
                if (r5 == 0) goto L_0x0072
                int r1 = r5.intValue()     // Catch:{ Exception -> 0x0087 }
                goto L_0x0072
            L_0x006c:
                int r1 = r5.intValue()     // Catch:{ Exception -> 0x0087 }
                goto L_0x0072
            L_0x0071:
                r5 = r4
            L_0x0072:
                if (r5 != 0) goto L_0x007f
                io.netty.util.internal.logging.InternalLogger r5 = p043io.netty.util.NetUtil.logger     // Catch:{ Exception -> 0x0087 }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0087 }
                r5.debug(r0, r2, r6)     // Catch:{ Exception -> 0x0087 }
            L_0x007f:
                if (r4 == 0) goto L_0x00a1
            L_0x0081:
                r4.close()     // Catch:{ Exception -> 0x00a1 }
                goto L_0x00a1
            L_0x0085:
                r0 = move-exception
                goto L_0x00a6
            L_0x0087:
                r5 = move-exception
            L_0x0088:
                io.netty.util.internal.logging.InternalLogger r6 = p043io.netty.util.NetUtil.logger     // Catch:{ all -> 0x0085 }
                r7 = 3
                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0085 }
                r7[r3] = r2     // Catch:{ all -> 0x0085 }
                r2 = 1
                java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0085 }
                r7[r2] = r3     // Catch:{ all -> 0x0085 }
                r2 = 2
                r7[r2] = r5     // Catch:{ all -> 0x0085 }
                r6.debug(r0, r7)     // Catch:{ all -> 0x0085 }
                if (r4 == 0) goto L_0x00a1
                goto L_0x0081
            L_0x00a1:
                java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
                return r0
            L_0x00a6:
                if (r4 == 0) goto L_0x00ab
                r4.close()     // Catch:{ Exception -> 0x00ab }
            L_0x00ab:
                goto L_0x00ad
            L_0x00ac:
                throw r0
            L_0x00ad:
                goto L_0x00ac
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.NetUtil.C57851.run():java.lang.Integer");
        }
    })).intValue();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);

    private static int getIntValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'A';
        if (c < 'A' || c > 'F') {
            c2 = 'a';
            if (c < 'a' || c > 'f') {
                return 0;
            }
        }
        return (c - c2) + 10;
    }

    private static boolean inRangeEndExclusive(int i, int i2, int i3) {
        return i >= i2 && i < i3;
    }

    private static boolean isValidHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    private static boolean isValidIPv4MappedChar(char c) {
        return c == 'f' || c == 'F';
    }

    private static boolean isValidIPv4MappedSeparators(byte b, byte b2, boolean z) {
        return b == b2 && (b == 0 || (!z && b2 == -1));
    }

    private static boolean isValidNumericChar(char c) {
        return c >= '0' && c <= '9';
    }

    /* JADX WARNING: type inference failed for: r4v10, types: [java.lang.Object, java.net.Inet4Address] */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.net.Inet6Address, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r10v0 */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r10v2, types: [java.net.InetAddress] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.net.InetAddress] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r5v13, types: [java.net.InetAddress] */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r10v9, types: [java.net.InetAddress] */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v13, types: [java.net.Inet6Address] */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v14, types: [java.net.Inet4Address] */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e1, code lost:
        r10 = (java.net.InetAddress) r8.nextElement();
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0129, code lost:
        if (r1 == 0) goto L_0x0138;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0136, code lost:
        if (r10 != 0) goto L_0x013f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0138, code lost:
        logger.debug(r2, (java.lang.Object) r4);
        r1 = r4;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v3
      assigns: []
      uses: []
      mth insns count: 136
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
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0116  */
    /* JADX WARNING: Unknown variable types count: 8 */
    static {
        /*
            java.lang.String r0 = "Failed to find the loopback interface"
            java.lang.String r1 = "localhost"
            java.lang.String r2 = "Using hard-coded IPv4 localhost address: {}"
            r3 = 0
            java.lang.String r4 = "java.net.preferIPv4Stack"
            boolean r4 = p043io.netty.util.internal.SystemPropertyUtil.getBoolean(r4, r3)
            IPV4_PREFERRED = r4
            java.lang.String r4 = "java.net.preferIPv6Addresses"
            boolean r4 = p043io.netty.util.internal.SystemPropertyUtil.getBoolean(r4, r3)
            IPV6_ADDRESSES_PREFERRED = r4
            java.lang.Class<io.netty.util.NetUtil> r4 = p043io.netty.util.NetUtil.class
            io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.internal.logging.InternalLoggerFactory.getInstance(r4)
            logger = r4
            io.netty.util.internal.logging.InternalLogger r4 = logger
            boolean r5 = IPV4_PREFERRED
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            java.lang.String r6 = "-Djava.net.preferIPv4Stack: {}"
            r4.debug(r6, r5)
            io.netty.util.internal.logging.InternalLogger r4 = logger
            boolean r5 = IPV6_ADDRESSES_PREFERRED
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            java.lang.String r6 = "-Djava.net.preferIPv6Addresses: {}"
            r4.debug(r6, r5)
            r4 = 4
            byte[] r4 = new byte[r4]
            r4 = {127, 0, 0, 1} // fill-array
            r5 = 16
            byte[] r5 = new byte[r5]
            r5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1} // fill-array
            r6 = 0
            java.net.InetAddress r4 = java.net.InetAddress.getByAddress(r1, r4)     // Catch:{ Exception -> 0x004e }
            java.net.Inet4Address r4 = (java.net.Inet4Address) r4     // Catch:{ Exception -> 0x004e }
            goto L_0x0053
        L_0x004e:
            r4 = move-exception
            p043io.netty.util.internal.PlatformDependent.throwException(r4)
            r4 = r6
        L_0x0053:
            LOCALHOST4 = r4
            java.net.InetAddress r1 = java.net.InetAddress.getByAddress(r1, r5)     // Catch:{ Exception -> 0x005c }
            java.net.Inet6Address r1 = (java.net.Inet6Address) r1     // Catch:{ Exception -> 0x005c }
            goto L_0x0061
        L_0x005c:
            r1 = move-exception
            p043io.netty.util.internal.PlatformDependent.throwException(r1)
            r1 = r6
        L_0x0061:
            LOCALHOST6 = r1
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Enumeration r7 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch:{ SocketException -> 0x0088 }
            if (r7 == 0) goto L_0x0090
        L_0x006e:
            boolean r8 = r7.hasMoreElements()     // Catch:{ SocketException -> 0x0088 }
            if (r8 == 0) goto L_0x0090
            java.lang.Object r8 = r7.nextElement()     // Catch:{ SocketException -> 0x0088 }
            java.net.NetworkInterface r8 = (java.net.NetworkInterface) r8     // Catch:{ SocketException -> 0x0088 }
            java.util.Enumeration r9 = p043io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r8)     // Catch:{ SocketException -> 0x0088 }
            boolean r9 = r9.hasMoreElements()     // Catch:{ SocketException -> 0x0088 }
            if (r9 == 0) goto L_0x006e
            r5.add(r8)     // Catch:{ SocketException -> 0x0088 }
            goto L_0x006e
        L_0x0088:
            r7 = move-exception
            io.netty.util.internal.logging.InternalLogger r8 = logger
            java.lang.String r9 = "Failed to retrieve the list of available network interfaces"
            r8.warn(r9, r7)
        L_0x0090:
            java.util.Iterator r7 = r5.iterator()
        L_0x0094:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00b8
            java.lang.Object r8 = r7.next()
            java.net.NetworkInterface r8 = (java.net.NetworkInterface) r8
            java.util.Enumeration r9 = p043io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r8)
        L_0x00a4:
            boolean r10 = r9.hasMoreElements()
            if (r10 == 0) goto L_0x0094
            java.lang.Object r10 = r9.nextElement()
            java.net.InetAddress r10 = (java.net.InetAddress) r10
            boolean r11 = r10.isLoopbackAddress()
            if (r11 == 0) goto L_0x00a4
            r6 = r8
            goto L_0x00b9
        L_0x00b8:
            r10 = r6
        L_0x00b9:
            if (r6 != 0) goto L_0x00f5
            java.util.Iterator r5 = r5.iterator()     // Catch:{ SocketException -> 0x00ef }
        L_0x00bf:
            boolean r7 = r5.hasNext()     // Catch:{ SocketException -> 0x00ef }
            if (r7 == 0) goto L_0x00e7
            java.lang.Object r7 = r5.next()     // Catch:{ SocketException -> 0x00ef }
            java.net.NetworkInterface r7 = (java.net.NetworkInterface) r7     // Catch:{ SocketException -> 0x00ef }
            boolean r8 = r7.isLoopback()     // Catch:{ SocketException -> 0x00ef }
            if (r8 == 0) goto L_0x00bf
            java.util.Enumeration r8 = p043io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r7)     // Catch:{ SocketException -> 0x00ef }
            boolean r9 = r8.hasMoreElements()     // Catch:{ SocketException -> 0x00ef }
            if (r9 == 0) goto L_0x00bf
            java.lang.Object r5 = r8.nextElement()     // Catch:{ SocketException -> 0x00e4 }
            java.net.InetAddress r5 = (java.net.InetAddress) r5     // Catch:{ SocketException -> 0x00e4 }
            r10 = r5
            r6 = r7
            goto L_0x00e7
        L_0x00e4:
            r5 = move-exception
            r6 = r7
            goto L_0x00f0
        L_0x00e7:
            if (r6 != 0) goto L_0x00f5
            io.netty.util.internal.logging.InternalLogger r5 = logger     // Catch:{ SocketException -> 0x00ef }
            r5.warn(r0)     // Catch:{ SocketException -> 0x00ef }
            goto L_0x00f5
        L_0x00ef:
            r5 = move-exception
        L_0x00f0:
            io.netty.util.internal.logging.InternalLogger r7 = logger
            r7.warn(r0, r5)
        L_0x00f5:
            if (r6 == 0) goto L_0x0116
            io.netty.util.internal.logging.InternalLogger r0 = logger
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = r6.getName()
            r1[r3] = r2
            r2 = 1
            java.lang.String r3 = r6.getDisplayName()
            r1[r2] = r3
            r2 = 2
            java.lang.String r3 = r10.getHostAddress()
            r1[r2] = r3
            java.lang.String r2 = "Loopback interface: {} ({}, {})"
            r0.debug(r2, r1)
            goto L_0x013f
        L_0x0116:
            if (r10 != 0) goto L_0x013f
            java.net.Inet6Address r0 = LOCALHOST6     // Catch:{ Exception -> 0x0135, all -> 0x012c }
            java.net.NetworkInterface r0 = java.net.NetworkInterface.getByInetAddress(r0)     // Catch:{ Exception -> 0x0135, all -> 0x012c }
            if (r0 == 0) goto L_0x0128
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ Exception -> 0x0135, all -> 0x012c }
            java.lang.String r3 = "Using hard-coded IPv6 localhost address: {}"
            r0.debug(r3, r1)     // Catch:{ Exception -> 0x0135, all -> 0x012c }
            goto L_0x0129
        L_0x0128:
            r1 = r10
        L_0x0129:
            if (r1 != 0) goto L_0x0140
            goto L_0x0138
        L_0x012c:
            r0 = move-exception
            if (r10 != 0) goto L_0x0134
            io.netty.util.internal.logging.InternalLogger r1 = logger
            r1.debug(r2, r4)
        L_0x0134:
            throw r0
        L_0x0135:
            if (r10 != 0) goto L_0x013f
        L_0x0138:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            r0.debug(r2, r4)
            r1 = r4
            goto L_0x0140
        L_0x013f:
            r1 = r10
        L_0x0140:
            LOOPBACK_IF = r6
            LOCALHOST = r1
            io.netty.util.NetUtil$1 r0 = new io.netty.util.NetUtil$1
            r0.<init>()
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            SOMAXCONN = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.NetUtil.<clinit>():void");
    }

    /* access modifiers changed from: private */
    public static Integer sysctlGetInt(String str) throws IOException {
        BufferedReader bufferedReader;
        Process start = new ProcessBuilder(new String[]{"sysctl", str}).start();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
            String readLine = bufferedReader.readLine();
            if (readLine.startsWith(str)) {
                for (int length = readLine.length() - 1; length > str.length(); length--) {
                    if (!Character.isDigit(readLine.charAt(length))) {
                        Integer valueOf = Integer.valueOf(readLine.substring(length + 1, readLine.length()));
                        bufferedReader.close();
                        if (start != null) {
                            start.destroy();
                        }
                        return valueOf;
                    }
                }
            }
            bufferedReader.close();
            if (start != null) {
                start.destroy();
            }
            return null;
        } catch (Throwable th) {
            if (start != null) {
                start.destroy();
            }
            throw th;
        }
    }

    public static boolean isIpV4StackPreferred() {
        return IPV4_PREFERRED;
    }

    public static boolean isIpV6AddressesPreferred() {
        return IPV6_ADDRESSES_PREFERRED;
    }

    public static byte[] createByteArrayFromIpAddressString(String str) {
        if (isValidIpV4Address(str)) {
            return validIpV4ToBytes(str);
        }
        if (!isValidIpV6Address(str)) {
            return null;
        }
        if (str.charAt(0) == '[') {
            str = str.substring(1, str.length() - 1);
        }
        int indexOf = str.indexOf(37);
        if (indexOf >= 0) {
            str = str.substring(0, indexOf);
        }
        return getIPv6ByName(str, true);
    }

    private static int decimalDigit(String str, int i) {
        return str.charAt(i) - '0';
    }

    private static byte ipv4WordToByte(String str, int i, int i2) {
        int decimalDigit = decimalDigit(str, i);
        int i3 = i + 1;
        if (i3 == i2) {
            return (byte) decimalDigit;
        }
        int decimalDigit2 = (decimalDigit * 10) + decimalDigit(str, i3);
        int i4 = i3 + 1;
        if (i4 == i2) {
            return (byte) decimalDigit2;
        }
        return (byte) ((decimalDigit2 * 10) + decimalDigit(str, i4));
    }

    static byte[] validIpV4ToBytes(String str) {
        int indexOf = str.indexOf(46, 1);
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(46, indexOf + 2);
        int i2 = indexOf2 + 1;
        int indexOf3 = str.indexOf(46, indexOf2 + 2);
        return new byte[]{ipv4WordToByte(str, 0, indexOf), ipv4WordToByte(str, i, indexOf2), ipv4WordToByte(str, i2, indexOf3), ipv4WordToByte(str, indexOf3 + 1, str.length())};
    }

    public static String intToIpAddress(int i) {
        StringBuilder sb = new StringBuilder(15);
        sb.append((i >> 24) & 255);
        sb.append('.');
        sb.append((i >> 16) & 255);
        sb.append('.');
        sb.append((i >> 8) & 255);
        sb.append('.');
        sb.append(i & 255);
        return sb.toString();
    }

    public static String bytesToIpAddress(byte[] bArr) {
        return bytesToIpAddress(bArr, 0, bArr.length);
    }

    public static String bytesToIpAddress(byte[] bArr, int i, int i2) {
        if (i2 == 4) {
            StringBuilder sb = new StringBuilder(15);
            sb.append(bArr[i] & 255);
            sb.append('.');
            sb.append(bArr[i + 1] & 255);
            sb.append('.');
            sb.append(bArr[i + 2] & 255);
            sb.append('.');
            sb.append(bArr[i + 3] & 255);
            return sb.toString();
        } else if (i2 == 16) {
            return toAddressString(bArr, i, false);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("length: ");
            sb2.append(i2);
            sb2.append(" (expected: 4 or 16)");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static boolean isValidIpV6Address(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int length = str.length();
        boolean z = false;
        if (length < 2) {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt == '[') {
            int i5 = length - 1;
            if (str.charAt(i5) != ']') {
                return false;
            }
            charAt = str.charAt(1);
            i = i5;
            i2 = 1;
        } else {
            i = length;
            i2 = 0;
        }
        if (charAt != ':') {
            i4 = -1;
            i3 = 0;
        } else if (str.charAt(i2 + 1) != ':') {
            return false;
        } else {
            i4 = i2;
            i2 += 2;
            i3 = 2;
        }
        int i6 = i4;
        int i7 = i3;
        int i8 = 0;
        int i9 = i2;
        while (true) {
            if (i9 >= i) {
                i9 = i;
                break;
            }
            char charAt2 = str.charAt(i9);
            if (!isValidHexChar(charAt2)) {
                if (charAt2 == '%') {
                    break;
                } else if (charAt2 != '.') {
                    if (charAt2 != ':' || i7 > 7) {
                        return false;
                    }
                    int i10 = i9 - 1;
                    if (str.charAt(i10) != ':') {
                        i8 = 0;
                    } else if (i6 >= 0) {
                        return false;
                    } else {
                        i6 = i10;
                    }
                    i7++;
                } else if ((i6 < 0 && i7 != 6) || ((i7 == 7 && i6 >= i2) || i7 > 7)) {
                    return false;
                } else {
                    int i11 = i9 - i8;
                    int i12 = i11 - 2;
                    if (isValidIPv4MappedChar(str.charAt(i12))) {
                        if (!isValidIPv4MappedChar(str.charAt(i12 - 1)) || !isValidIPv4MappedChar(str.charAt(i12 - 2)) || !isValidIPv4MappedChar(str.charAt(i12 - 3))) {
                            return false;
                        }
                        i12 -= 5;
                    }
                    while (i12 >= i2) {
                        char charAt3 = str.charAt(i12);
                        if (charAt3 != '0' && charAt3 != ':') {
                            return false;
                        }
                        i12--;
                    }
                    int indexOf = str.indexOf(37, i11 + 7);
                    if (indexOf < 0) {
                        indexOf = i;
                    }
                    return isValidIpV4Address(str, i11, indexOf);
                }
            } else if (i8 >= 4) {
                return false;
            } else {
                i8++;
            }
            i9++;
        }
        if (i6 < 0) {
            if (i7 == 7 && i8 > 0) {
                z = true;
            }
            return z;
        }
        if (i6 + 2 == i9 || (i8 > 0 && (i7 < 8 || i6 <= i2))) {
            z = true;
        }
        return z;
    }

    private static boolean isValidIpV4Word(CharSequence charSequence, int i, int i2) {
        int i3 = i2 - i;
        boolean z = false;
        if (i3 >= 1 && i3 <= 3) {
            char charAt = charSequence.charAt(i);
            if (charAt >= '0') {
                if (i3 == 3) {
                    char charAt2 = charSequence.charAt(i + 1);
                    if (charAt2 >= '0') {
                        char charAt3 = charSequence.charAt(i + 2);
                        if (charAt3 >= '0' && ((charAt <= '1' && charAt2 <= '9' && charAt3 <= '9') || (charAt == '2' && charAt2 <= '5' && (charAt3 <= '5' || (charAt2 < '5' && charAt3 <= '9'))))) {
                            z = true;
                        }
                    }
                    return z;
                } else if (charAt <= '9' && (i3 == 1 || isValidNumericChar(charSequence.charAt(i + 1)))) {
                    z = true;
                }
            }
        }
        return z;
    }

    private static boolean isValidIPv4Mapped(byte[] bArr, int i, int i2, int i3) {
        boolean z = i3 + i2 >= 14;
        if (i > 12 || i < 2 || ((z && i2 >= 12) || !isValidIPv4MappedSeparators(bArr[i - 1], bArr[i - 2], z) || !PlatformDependent.isZero(bArr, 0, i - 3))) {
            return false;
        }
        return true;
    }

    public static boolean isValidIpV4Address(String str) {
        return isValidIpV4Address(str, 0, str.length());
    }

    private static boolean isValidIpV4Address(String str, int i, int i2) {
        int i3 = i2 - i;
        if (i3 <= 15 && i3 >= 7) {
            int indexOf = str.indexOf(46, i + 1);
            if (indexOf > 0 && isValidIpV4Word(str, i, indexOf)) {
                int i4 = indexOf + 2;
                int indexOf2 = str.indexOf(46, i4);
                if (indexOf2 > 0 && isValidIpV4Word(str, i4 - 1, indexOf2)) {
                    int i5 = indexOf2 + 2;
                    int indexOf3 = str.indexOf(46, i5);
                    if (indexOf3 > 0 && isValidIpV4Word(str, i5 - 1, indexOf3) && isValidIpV4Word(str, indexOf3 + 1, i2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Inet6Address getByName(CharSequence charSequence) {
        return getByName(charSequence, true);
    }

    public static Inet6Address getByName(CharSequence charSequence, boolean z) {
        byte[] iPv6ByName = getIPv6ByName(charSequence, z);
        if (iPv6ByName == null) {
            return null;
        }
        try {
            return Inet6Address.getByAddress(null, iPv6ByName, -1);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x017f, code lost:
        if (r0.charAt(0) == ':') goto L_0x0185;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0192, code lost:
        if (r6 <= 2) goto L_0x0194;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0162, code lost:
        if ((r5 - r8) <= 3) goto L_0x0166;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] getIPv6ByName(java.lang.CharSequence r17, boolean r18) {
        /*
            r0 = r17
            r1 = 16
            byte[] r1 = new byte[r1]
            int r2 = r17.length()
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = -1
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
        L_0x0013:
            r16 = 0
            r15 = 58
            r3 = 4
            if (r5 >= r2) goto L_0x0156
            char r14 = r0.charAt(r5)
            r4 = 46
            if (r14 == r4) goto L_0x00b0
            if (r14 == r15) goto L_0x004a
            boolean r4 = isValidHexChar(r14)
            if (r4 == 0) goto L_0x0049
            if (r7 <= 0) goto L_0x0033
            boolean r4 = isValidNumericChar(r14)
            if (r4 != 0) goto L_0x0033
            goto L_0x0049
        L_0x0033:
            if (r8 >= 0) goto L_0x0037
            r8 = r5
            goto L_0x003c
        L_0x0037:
            int r4 = r5 - r8
            if (r4 <= r3) goto L_0x003c
            return r16
        L_0x003c:
            int r3 = getIntValue(r14)
            int r4 = r5 - r8
            r14 = 2
            int r4 = r4 << r14
            int r3 = r3 << r4
            int r11 = r11 + r3
            r3 = 1
            goto L_0x0152
        L_0x0049:
            return r16
        L_0x004a:
            int r9 = r9 + 1
            int r4 = r5 - r8
            if (r4 > r3) goto L_0x00af
            if (r7 > 0) goto L_0x00af
            r8 = 8
            if (r9 > r8) goto L_0x00af
            int r8 = r10 + 1
            int r14 = r1.length
            if (r8 < r14) goto L_0x005c
            goto L_0x00af
        L_0x005c:
            int r4 = 4 - r4
            r14 = 2
            int r4 = r4 << r14
            int r4 = r11 << r4
            if (r12 <= 0) goto L_0x0066
            int r12 = r12 + -2
        L_0x0066:
            r11 = r4 & 15
            int r11 = r11 << r3
            int r14 = r4 >> 4
            r14 = r14 & 15
            r11 = r11 | r14
            byte r11 = (byte) r11
            r1[r10] = r11
            int r10 = r8 + 1
            int r11 = r4 >> 8
            r11 = r11 & 15
            int r3 = r11 << 4
            int r11 = r4 >> 12
            r11 = r11 & 15
            r3 = r3 | r11
            byte r3 = (byte) r3
            r1[r8] = r3
            int r3 = r5 + 1
            if (r3 >= r2) goto L_0x00aa
            char r8 = r0.charAt(r3)
            if (r8 != r15) goto L_0x00aa
            int r5 = r3 + 1
            if (r6 != 0) goto L_0x00a9
            if (r5 >= r2) goto L_0x0098
            char r5 = r0.charAt(r5)
            if (r5 != r15) goto L_0x0098
            goto L_0x00a9
        L_0x0098:
            int r9 = r9 + 1
            r5 = 2
            if (r9 != r5) goto L_0x00a1
            if (r4 != 0) goto L_0x00a1
            r13 = 1
            goto L_0x00a2
        L_0x00a1:
            r13 = 0
        L_0x00a2:
            int r4 = r1.length
            int r4 = r4 - r10
            int r12 = r4 + -2
            r5 = r3
            r6 = r10
            goto L_0x00aa
        L_0x00a9:
            return r16
        L_0x00aa:
            r3 = 1
            r8 = -1
            r11 = 0
            goto L_0x0152
        L_0x00af:
            return r16
        L_0x00b0:
            int r7 = r7 + 1
            int r3 = r5 - r8
            r4 = 3
            if (r3 > r4) goto L_0x0155
            if (r8 < 0) goto L_0x0155
            if (r7 > r4) goto L_0x0155
            if (r9 <= 0) goto L_0x00c3
            int r4 = r10 + r12
            r8 = 12
            if (r4 < r8) goto L_0x0155
        L_0x00c3:
            int r4 = r5 + 1
            if (r4 >= r2) goto L_0x0155
            int r4 = r1.length
            if (r10 >= r4) goto L_0x0155
            r4 = 1
            if (r7 != r4) goto L_0x0129
            if (r18 == 0) goto L_0x0155
            if (r10 == 0) goto L_0x00d7
            boolean r4 = isValidIPv4Mapped(r1, r10, r6, r12)
            if (r4 == 0) goto L_0x0155
        L_0x00d7:
            r4 = 3
            if (r3 != r4) goto L_0x00fe
            int r4 = r5 + -1
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 == 0) goto L_0x0155
            int r4 = r5 + -2
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 == 0) goto L_0x0155
            int r4 = r5 + -3
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 == 0) goto L_0x0155
        L_0x00fe:
            r4 = 2
            if (r3 != r4) goto L_0x0119
            int r4 = r5 + -1
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 == 0) goto L_0x0155
            int r4 = r5 + -2
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 == 0) goto L_0x0155
        L_0x0119:
            r4 = 1
            if (r3 != r4) goto L_0x0129
            int r4 = r5 + -1
            char r4 = r0.charAt(r4)
            boolean r4 = isValidNumericChar(r4)
            if (r4 != 0) goto L_0x0129
            goto L_0x0155
        L_0x0129:
            r4 = 3
            int r4 = r4 - r3
            r3 = 2
            int r3 = r4 << 2
            int r3 = r11 << r3
            r4 = r3 & 15
            int r4 = r4 * 100
            int r8 = r3 >> 4
            r8 = r8 & 15
            r11 = 10
            int r8 = r8 * 10
            int r4 = r4 + r8
            r8 = 8
            int r3 = r3 >> r8
            r3 = r3 & 15
            int r4 = r4 + r3
            if (r4 < 0) goto L_0x0155
            r3 = 255(0xff, float:3.57E-43)
            if (r4 <= r3) goto L_0x014a
            goto L_0x0155
        L_0x014a:
            int r3 = r10 + 1
            byte r4 = (byte) r4
            r1[r10] = r4
            r10 = r3
            goto L_0x00aa
        L_0x0152:
            int r5 = r5 + r3
            goto L_0x0013
        L_0x0155:
            return r16
        L_0x0156:
            if (r6 <= 0) goto L_0x015a
            r4 = 1
            goto L_0x015b
        L_0x015a:
            r4 = 0
        L_0x015b:
            if (r7 <= 0) goto L_0x01c0
            if (r8 <= 0) goto L_0x0165
            int r2 = r5 - r8
            r3 = 3
            if (r2 > r3) goto L_0x01bf
            goto L_0x0166
        L_0x0165:
            r3 = 3
        L_0x0166:
            if (r7 != r3) goto L_0x01bf
            int r2 = r1.length
            if (r10 < r2) goto L_0x016c
            goto L_0x01bf
        L_0x016c:
            if (r9 != 0) goto L_0x0172
            r0 = 2
            r12 = 12
            goto L_0x0196
        L_0x0172:
            r2 = 2
            if (r9 < r2) goto L_0x01bf
            if (r4 != 0) goto L_0x0184
            r2 = 6
            if (r9 != r2) goto L_0x0184
            r2 = 0
            char r3 = r0.charAt(r2)
            if (r3 != r15) goto L_0x0182
            goto L_0x0185
        L_0x0182:
            r0 = 2
            goto L_0x0194
        L_0x0184:
            r2 = 0
        L_0x0185:
            if (r4 == 0) goto L_0x01bf
            r3 = 8
            if (r9 >= r3) goto L_0x01bf
            char r0 = r0.charAt(r2)
            if (r0 != r15) goto L_0x0182
            r0 = 2
            if (r6 > r0) goto L_0x01bf
        L_0x0194:
            int r12 = r12 + -2
        L_0x0196:
            int r5 = r5 - r8
            r2 = 3
            int r4 = 3 - r5
            int r0 = r4 << 2
            int r0 = r11 << r0
            r2 = r0 & 15
            int r2 = r2 * 100
            int r3 = r0 >> 4
            r3 = r3 & 15
            r4 = 10
            int r3 = r3 * 10
            int r2 = r2 + r3
            r3 = 8
            int r0 = r0 >> r3
            r0 = r0 & 15
            int r2 = r2 + r0
            if (r2 < 0) goto L_0x01bf
            r0 = 255(0xff, float:3.57E-43)
            if (r2 <= r0) goto L_0x01b8
            goto L_0x01bf
        L_0x01b8:
            int r0 = r10 + 1
            byte r2 = (byte) r2
            r1[r10] = r2
            goto L_0x0241
        L_0x01bf:
            return r16
        L_0x01c0:
            r14 = 1
            int r2 = r2 - r14
            if (r8 <= 0) goto L_0x01c8
            int r14 = r5 - r8
            if (r14 > r3) goto L_0x0287
        L_0x01c8:
            r14 = 2
            if (r9 < r14) goto L_0x0287
            if (r4 != 0) goto L_0x01e0
            int r14 = r9 + 1
            r3 = 8
            if (r14 != r3) goto L_0x0287
            r14 = 0
            char r3 = r0.charAt(r14)
            if (r3 == r15) goto L_0x0287
            char r3 = r0.charAt(r2)
            if (r3 == r15) goto L_0x0287
        L_0x01e0:
            if (r4 == 0) goto L_0x01fc
            r3 = 8
            if (r9 > r3) goto L_0x0287
            if (r9 != r3) goto L_0x01fc
            r3 = 2
            if (r6 > r3) goto L_0x01f2
            r3 = 0
            char r4 = r0.charAt(r3)
            if (r4 != r15) goto L_0x0287
        L_0x01f2:
            r3 = 14
            if (r6 < r3) goto L_0x01fc
            char r3 = r0.charAt(r2)
            if (r3 != r15) goto L_0x0287
        L_0x01fc:
            int r3 = r10 + 1
            int r4 = r1.length
            if (r3 >= r4) goto L_0x0287
            if (r8 >= 0) goto L_0x020b
            r4 = 1
            int r2 = r2 - r4
            char r2 = r0.charAt(r2)
            if (r2 != r15) goto L_0x0287
        L_0x020b:
            r2 = 2
            if (r6 <= r2) goto L_0x0217
            r4 = 0
            char r0 = r0.charAt(r4)
            if (r0 != r15) goto L_0x0217
            goto L_0x0287
        L_0x0217:
            if (r8 < 0) goto L_0x0223
            int r5 = r5 - r8
            r0 = 4
            if (r5 > r0) goto L_0x0224
            int r4 = 4 - r5
            int r2 = r4 << 2
            int r11 = r11 << r2
            goto L_0x0224
        L_0x0223:
            r0 = 4
        L_0x0224:
            r2 = r11 & 15
            int r2 = r2 << r0
            int r0 = r11 >> 4
            r0 = r0 & 15
            r0 = r0 | r2
            byte r0 = (byte) r0
            r1[r10] = r0
            int r0 = r3 + 1
            int r2 = r11 >> 8
            r2 = r2 & 15
            r4 = 4
            int r2 = r2 << r4
            r4 = 12
            int r4 = r11 >> 12
            r4 = r4 & 15
            r2 = r2 | r4
            byte r2 = (byte) r2
            r1[r3] = r2
        L_0x0241:
            int r2 = r0 + r12
            if (r13 != 0) goto L_0x025d
            int r3 = r1.length
            if (r2 < r3) goto L_0x0249
            goto L_0x025d
        L_0x0249:
            r0 = 0
        L_0x024a:
            if (r0 >= r12) goto L_0x027b
            int r2 = r0 + r6
            int r3 = r2 + r12
            int r4 = r1.length
            if (r3 >= r4) goto L_0x027b
            byte r4 = r1[r2]
            r1[r3] = r4
            r3 = 0
            r1[r2] = r3
            int r0 = r0 + 1
            goto L_0x024a
        L_0x025d:
            int r3 = r1.length
            if (r2 < r3) goto L_0x0262
            int r6 = r6 + 1
        L_0x0262:
            int r2 = r1.length
            if (r0 >= r2) goto L_0x027b
            int r2 = r1.length
            r3 = 1
            int r2 = r2 - r3
        L_0x0268:
            if (r2 < r6) goto L_0x0273
            int r4 = r2 + -1
            byte r4 = r1[r4]
            r1[r2] = r4
            int r2 = r2 + -1
            goto L_0x0268
        L_0x0273:
            r4 = 0
            r1[r2] = r4
            int r6 = r6 + 1
            int r0 = r0 + 1
            goto L_0x0262
        L_0x027b:
            if (r7 <= 0) goto L_0x0286
            r0 = 11
            r2 = -1
            r1[r0] = r2
            r0 = 10
            r1[r0] = r2
        L_0x0286:
            return r1
        L_0x0287:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.NetUtil.getIPv6ByName(java.lang.CharSequence, boolean):byte[]");
    }

    public static String toSocketAddressString(InetSocketAddress inetSocketAddress) {
        StringBuilder sb;
        String valueOf = String.valueOf(inetSocketAddress.getPort());
        if (inetSocketAddress.isUnresolved()) {
            String hostString = PlatformDependent.javaVersion() >= 7 ? inetSocketAddress.getHostString() : inetSocketAddress.getHostName();
            sb = newSocketAddressStringBuilder(hostString, valueOf, !isValidIpV6Address(hostString));
        } else {
            InetAddress address = inetSocketAddress.getAddress();
            sb = newSocketAddressStringBuilder(toAddressString(address), valueOf, address instanceof Inet4Address);
        }
        sb.append(':');
        sb.append(valueOf);
        return sb.toString();
    }

    public static String toSocketAddressString(String str, int i) {
        String valueOf = String.valueOf(i);
        StringBuilder newSocketAddressStringBuilder = newSocketAddressStringBuilder(str, valueOf, !isValidIpV6Address(str));
        newSocketAddressStringBuilder.append(':');
        newSocketAddressStringBuilder.append(valueOf);
        return newSocketAddressStringBuilder.toString();
    }

    private static StringBuilder newSocketAddressStringBuilder(String str, String str2, boolean z) {
        int length = str.length();
        if (z) {
            StringBuilder sb = new StringBuilder(length + 1 + str2.length());
            sb.append(str);
            return sb;
        }
        StringBuilder sb2 = new StringBuilder(length + 3 + str2.length());
        if (length > 1 && str.charAt(0) == '[' && str.charAt(length - 1) == ']') {
            sb2.append(str);
            return sb2;
        }
        sb2.append('[');
        sb2.append(str);
        sb2.append(']');
        return sb2;
    }

    public static String toAddressString(InetAddress inetAddress) {
        return toAddressString(inetAddress, false);
    }

    public static String toAddressString(InetAddress inetAddress, boolean z) {
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        if (inetAddress instanceof Inet6Address) {
            return toAddressString(inetAddress.getAddress(), 0, z);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unhandled type: ");
        sb.append(inetAddress);
        throw new IllegalArgumentException(sb.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003d, code lost:
        if (r2 > r5) goto L_0x0042;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String toAddressString(byte[] r9, int r10, boolean r11) {
        /*
            r0 = 8
            int[] r1 = new int[r0]
            int r2 = r1.length
            int r2 = r2 + r10
        L_0x0006:
            r3 = 1
            if (r10 >= r2) goto L_0x001b
            int r4 = r10 << 1
            byte r5 = r9[r4]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << r0
            int r4 = r4 + r3
            byte r3 = r9[r4]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r3 = r3 | r5
            r1[r10] = r3
            int r10 = r10 + 1
            goto L_0x0006
        L_0x001b:
            r9 = -1
            r10 = 0
            r2 = 0
            r4 = -1
            r5 = 0
            r6 = -1
        L_0x0021:
            int r7 = r1.length
            if (r2 >= r7) goto L_0x003a
            r7 = r1[r2]
            if (r7 != 0) goto L_0x002c
            if (r4 >= 0) goto L_0x0037
            r4 = r2
            goto L_0x0037
        L_0x002c:
            if (r4 < 0) goto L_0x0037
            int r7 = r2 - r4
            if (r7 <= r5) goto L_0x0034
            r5 = r7
            goto L_0x0035
        L_0x0034:
            r4 = r6
        L_0x0035:
            r6 = r4
            r4 = -1
        L_0x0037:
            int r2 = r2 + 1
            goto L_0x0021
        L_0x003a:
            if (r4 < 0) goto L_0x0040
            int r2 = r2 - r4
            if (r2 <= r5) goto L_0x0040
            goto L_0x0042
        L_0x0040:
            r2 = r5
            r4 = r6
        L_0x0042:
            if (r2 != r3) goto L_0x0046
            r2 = 0
            goto L_0x0047
        L_0x0046:
            r9 = r4
        L_0x0047:
            int r2 = r2 + r9
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r5 = 39
            r4.<init>(r5)
            r5 = 58
            if (r2 >= 0) goto L_0x006e
            r9 = r1[r10]
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r4.append(r9)
        L_0x005c:
            int r9 = r1.length
            if (r3 >= r9) goto L_0x00dd
            r4.append(r5)
            r9 = r1[r3]
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r4.append(r9)
            int r3 = r3 + 1
            goto L_0x005c
        L_0x006e:
            boolean r6 = inRangeEndExclusive(r10, r9, r2)
            java.lang.String r7 = "::"
            r8 = 5
            if (r6 == 0) goto L_0x0087
            r4.append(r7)
            if (r11 == 0) goto L_0x0090
            if (r2 != r8) goto L_0x0090
            r11 = r1[r8]
            r6 = 65535(0xffff, float:9.1834E-41)
            if (r11 != r6) goto L_0x0090
            r10 = 1
            goto L_0x0090
        L_0x0087:
            r11 = r1[r10]
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r4.append(r11)
        L_0x0090:
            int r11 = r1.length
            if (r3 >= r11) goto L_0x00dd
            boolean r11 = inRangeEndExclusive(r3, r9, r2)
            if (r11 != 0) goto L_0x00cf
            int r11 = r3 + -1
            boolean r11 = inRangeEndExclusive(r11, r9, r2)
            r6 = 46
            if (r11 != 0) goto L_0x00b0
            if (r10 == 0) goto L_0x00ad
            r11 = 6
            if (r3 != r11) goto L_0x00a9
            goto L_0x00ad
        L_0x00a9:
            r4.append(r6)
            goto L_0x00b0
        L_0x00ad:
            r4.append(r5)
        L_0x00b0:
            if (r10 == 0) goto L_0x00c5
            if (r3 <= r8) goto L_0x00c5
            r11 = r1[r3]
            int r11 = r11 >> r0
            r4.append(r11)
            r4.append(r6)
            r11 = r1[r3]
            r11 = r11 & 255(0xff, float:3.57E-43)
            r4.append(r11)
            goto L_0x00da
        L_0x00c5:
            r11 = r1[r3]
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r4.append(r11)
            goto L_0x00da
        L_0x00cf:
            int r11 = r3 + -1
            boolean r11 = inRangeEndExclusive(r11, r9, r2)
            if (r11 != 0) goto L_0x00da
            r4.append(r7)
        L_0x00da:
            int r3 = r3 + 1
            goto L_0x0090
        L_0x00dd:
            java.lang.String r9 = r4.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.NetUtil.toAddressString(byte[], int, boolean):java.lang.String");
    }

    private NetUtil() {
    }
}
