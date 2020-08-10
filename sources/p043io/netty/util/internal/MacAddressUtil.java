package p043io.netty.util.internal;

import java.net.InetAddress;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.internal.MacAddressUtil */
public final class MacAddressUtil {
    private static final int EUI48_MAC_ADDRESS_LENGTH = 6;
    private static final int EUI64_MAC_ADDRESS_LENGTH = 8;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MacAddressUtil.class);

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007e, code lost:
        if (r0.length < r5.length) goto L_0x0082;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] bestAvailableMac() {
        /*
            byte[] r0 = p043io.netty.util.internal.EmptyArrays.EMPTY_BYTES
            java.net.Inet4Address r1 = p043io.netty.util.NetUtil.LOCALHOST4
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.util.Enumeration r3 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch:{ SocketException -> 0x0035 }
            if (r3 == 0) goto L_0x003d
        L_0x000f:
            boolean r4 = r3.hasMoreElements()     // Catch:{ SocketException -> 0x0035 }
            if (r4 == 0) goto L_0x003d
            java.lang.Object r4 = r3.nextElement()     // Catch:{ SocketException -> 0x0035 }
            java.net.NetworkInterface r4 = (java.net.NetworkInterface) r4     // Catch:{ SocketException -> 0x0035 }
            java.util.Enumeration r5 = p043io.netty.util.internal.SocketUtils.addressesFromNetworkInterface(r4)     // Catch:{ SocketException -> 0x0035 }
            boolean r6 = r5.hasMoreElements()     // Catch:{ SocketException -> 0x0035 }
            if (r6 == 0) goto L_0x000f
            java.lang.Object r5 = r5.nextElement()     // Catch:{ SocketException -> 0x0035 }
            java.net.InetAddress r5 = (java.net.InetAddress) r5     // Catch:{ SocketException -> 0x0035 }
            boolean r6 = r5.isLoopbackAddress()     // Catch:{ SocketException -> 0x0035 }
            if (r6 != 0) goto L_0x000f
            r2.put(r4, r5)     // Catch:{ SocketException -> 0x0035 }
            goto L_0x000f
        L_0x0035:
            r3 = move-exception
            io.netty.util.internal.logging.InternalLogger r4 = logger
            java.lang.String r5 = "Failed to retrieve the list of available network interfaces"
            r4.warn(r5, r3)
        L_0x003d:
            java.util.Set r2 = r2.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0045:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x0090
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r5 = r3.getKey()
            java.net.NetworkInterface r5 = (java.net.NetworkInterface) r5
            java.lang.Object r3 = r3.getValue()
            java.net.InetAddress r3 = (java.net.InetAddress) r3
            boolean r6 = r5.isVirtual()
            if (r6 == 0) goto L_0x0065
            goto L_0x0045
        L_0x0065:
            byte[] r5 = p043io.netty.util.internal.SocketUtils.hardwareAddressFromNetworkInterface(r5)     // Catch:{ SocketException -> 0x0087 }
            int r6 = compareAddresses(r0, r5)
            r7 = 1
            if (r6 >= 0) goto L_0x0071
            goto L_0x0082
        L_0x0071:
            if (r6 != 0) goto L_0x0081
            int r6 = compareAddresses(r1, r3)
            if (r6 >= 0) goto L_0x007a
            goto L_0x0082
        L_0x007a:
            if (r6 != 0) goto L_0x0081
            int r6 = r0.length
            int r8 = r5.length
            if (r6 >= r8) goto L_0x0081
            goto L_0x0082
        L_0x0081:
            r7 = 0
        L_0x0082:
            if (r7 == 0) goto L_0x0045
            r1 = r3
            r0 = r5
            goto L_0x0045
        L_0x0087:
            r3 = move-exception
            io.netty.util.internal.logging.InternalLogger r4 = logger
            java.lang.String r6 = "Failed to get the hardware address of a network interface: {}"
            r4.debug(r6, r5, r3)
            goto L_0x0045
        L_0x0090:
            byte[] r1 = p043io.netty.util.internal.EmptyArrays.EMPTY_BYTES
            if (r0 != r1) goto L_0x0096
            r0 = 0
            return r0
        L_0x0096:
            int r1 = r0.length
            r2 = 6
            r3 = 8
            if (r1 == r2) goto L_0x00a1
            byte[] r0 = java.util.Arrays.copyOf(r0, r3)
            goto L_0x00b3
        L_0x00a1:
            byte[] r1 = new byte[r3]
            r2 = 3
            java.lang.System.arraycopy(r0, r4, r1, r4, r2)
            r3 = -1
            r1[r2] = r3
            r3 = 4
            r4 = -2
            r1[r3] = r4
            r3 = 5
            java.lang.System.arraycopy(r0, r2, r1, r3, r2)
            r0 = r1
        L_0x00b3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.MacAddressUtil.bestAvailableMac():byte[]");
    }

    public static byte[] defaultMachineId() {
        byte[] bestAvailableMac = bestAvailableMac();
        if (bestAvailableMac != null) {
            return bestAvailableMac;
        }
        byte[] bArr = new byte[8];
        PlatformDependent.threadLocalRandom().nextBytes(bArr);
        logger.warn("Failed to find a usable hardware address from the network interfaces; using random bytes: {}", (Object) formatAddress(bArr));
        return bArr;
    }

    public static byte[] parseMAC(String str) {
        byte[] bArr;
        char c;
        int length = str.length();
        if (length == 17) {
            c = str.charAt(2);
            validateMacSeparator(c);
            bArr = new byte[6];
        } else if (length == 23) {
            c = str.charAt(2);
            validateMacSeparator(c);
            bArr = new byte[8];
        } else {
            throw new IllegalArgumentException("value is not supported [MAC-48, EUI-48, EUI-64]");
        }
        int length2 = bArr.length - 1;
        int i = 0;
        int i2 = 0;
        while (i < length2) {
            int i3 = i2 + 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i3), 16);
            if (str.charAt(i3) == c) {
                i++;
                i2 += 3;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("expected separator '");
                sb.append(c);
                sb.append(" but got '");
                sb.append(str.charAt(i3));
                sb.append("' at index: ");
                sb.append(i3);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        bArr[length2] = (byte) Integer.parseInt(str.substring(i2, str.length()), 16);
        return bArr;
    }

    private static void validateMacSeparator(char c) {
        if (c != ':' && c != '-') {
            StringBuilder sb = new StringBuilder();
            sb.append("unsupported separator: ");
            sb.append(c);
            sb.append(" (expected: [:-])");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static String formatAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder(24);
        for (byte b : bArr) {
            sb.append(String.format("%02x:", new Object[]{Integer.valueOf(b & 255)}));
        }
        return sb.substring(0, sb.length() - 1);
    }

    static int compareAddresses(byte[] bArr, byte[] bArr2) {
        boolean z;
        if (bArr2 == null || bArr2.length < 6) {
            return 1;
        }
        int length = bArr2.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            }
            byte b = bArr2[i];
            if (b != 0 && b != 1) {
                z = false;
                break;
            }
            i++;
        }
        if (z || (bArr2[0] & 1) != 0) {
            return 1;
        }
        if ((bArr2[0] & 2) == 0) {
            if (bArr.length == 0 || (bArr[0] & 2) != 0) {
                return -1;
            }
            return 0;
        } else if (bArr.length == 0 || (bArr[0] & 2) != 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private static int compareAddresses(InetAddress inetAddress, InetAddress inetAddress2) {
        return scoreAddress(inetAddress) - scoreAddress(inetAddress2);
    }

    private static int scoreAddress(InetAddress inetAddress) {
        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()) {
            return 0;
        }
        if (inetAddress.isMulticastAddress()) {
            return 1;
        }
        if (inetAddress.isLinkLocalAddress()) {
            return 2;
        }
        return inetAddress.isSiteLocalAddress() ? 3 : 4;
    }

    private MacAddressUtil() {
    }
}
