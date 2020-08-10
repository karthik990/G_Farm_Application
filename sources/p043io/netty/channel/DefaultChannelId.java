package p043io.netty.channel;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.util.internal.MacAddressUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.DefaultChannelId */
public final class DefaultChannelId implements ChannelId {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] MACHINE_ID;
    private static final int PROCESS_ID;
    private static final int PROCESS_ID_LEN = 4;
    private static final int RANDOM_LEN = 4;
    private static final int SEQUENCE_LEN = 4;
    private static final int TIMESTAMP_LEN = 8;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelId.class);
    private static final AtomicInteger nextSequence = new AtomicInteger();
    private static final long serialVersionUID = 3884076183504074063L;
    private final byte[] data;
    private final int hashCode = Arrays.hashCode(this.data);
    private transient String longValue;
    private transient String shortValue;

    static {
        int i;
        String str = SystemPropertyUtil.get("io.netty.processId");
        int i2 = -1;
        if (str != null) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                i = -1;
            }
            if (i < 0) {
                logger.warn("-Dio.netty.processId: {} (malformed)", (Object) str);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("-Dio.netty.processId: {} (user-set)", (Object) Integer.valueOf(i));
                }
                i2 = i;
            }
        }
        if (i2 < 0) {
            i2 = defaultProcessId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.processId: {} (auto-detected)", (Object) Integer.valueOf(i2));
            }
        }
        PROCESS_ID = i2;
        byte[] bArr = null;
        String str2 = SystemPropertyUtil.get("io.netty.machineId");
        if (str2 != null) {
            try {
                bArr = MacAddressUtil.parseMAC(str2);
            } catch (Exception e) {
                logger.warn("-Dio.netty.machineId: {} (malformed)", str2, e);
            }
            if (bArr != null) {
                logger.debug("-Dio.netty.machineId: {} (user-set)", (Object) str2);
            }
        }
        if (bArr == null) {
            bArr = MacAddressUtil.defaultMachineId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.machineId: {} (auto-detected)", (Object) MacAddressUtil.formatAddress(bArr));
            }
        }
        MACHINE_ID = bArr;
    }

    public static DefaultChannelId newInstance() {
        return new DefaultChannelId();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int defaultProcessId() {
        /*
            r0 = 1
            r1 = 0
            java.lang.Class<io.netty.channel.DefaultChannelId> r2 = p043io.netty.channel.DefaultChannelId.class
            java.lang.ClassLoader r2 = p043io.netty.util.internal.PlatformDependent.getClassLoader(r2)     // Catch:{ all -> 0x0035 }
            java.lang.String r3 = "java.lang.management.ManagementFactory"
            java.lang.Class r3 = java.lang.Class.forName(r3, r0, r2)     // Catch:{ all -> 0x0033 }
            java.lang.String r4 = "java.lang.management.RuntimeMXBean"
            java.lang.Class r4 = java.lang.Class.forName(r4, r0, r2)     // Catch:{ all -> 0x0033 }
            java.lang.String r5 = "getRuntimeMXBean"
            java.lang.Class<?>[] r6 = p043io.netty.util.internal.EmptyArrays.EMPTY_CLASSES     // Catch:{ all -> 0x0033 }
            java.lang.reflect.Method r3 = r3.getMethod(r5, r6)     // Catch:{ all -> 0x0033 }
            java.lang.Object[] r5 = p043io.netty.util.internal.EmptyArrays.EMPTY_OBJECTS     // Catch:{ all -> 0x0033 }
            java.lang.Object r3 = r3.invoke(r1, r5)     // Catch:{ all -> 0x0033 }
            java.lang.String r5 = "getName"
            java.lang.Class<?>[] r6 = p043io.netty.util.internal.EmptyArrays.EMPTY_CLASSES     // Catch:{ all -> 0x0033 }
            java.lang.reflect.Method r4 = r4.getMethod(r5, r6)     // Catch:{ all -> 0x0033 }
            java.lang.Object[] r5 = p043io.netty.util.internal.EmptyArrays.EMPTY_OBJECTS     // Catch:{ all -> 0x0033 }
            java.lang.Object r3 = r4.invoke(r3, r5)     // Catch:{ all -> 0x0033 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0033 }
            goto L_0x0061
        L_0x0033:
            r3 = move-exception
            goto L_0x0037
        L_0x0035:
            r3 = move-exception
            r2 = r1
        L_0x0037:
            io.netty.util.internal.logging.InternalLogger r4 = logger
            java.lang.String r5 = "Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?"
            r4.debug(r5, r3)
            java.lang.String r3 = "android.os.Process"
            java.lang.Class r0 = java.lang.Class.forName(r3, r0, r2)     // Catch:{ all -> 0x0057 }
            java.lang.String r2 = "myPid"
            java.lang.Class<?>[] r3 = p043io.netty.util.internal.EmptyArrays.EMPTY_CLASSES     // Catch:{ all -> 0x0057 }
            java.lang.reflect.Method r0 = r0.getMethod(r2, r3)     // Catch:{ all -> 0x0057 }
            java.lang.Object[] r2 = p043io.netty.util.internal.EmptyArrays.EMPTY_OBJECTS     // Catch:{ all -> 0x0057 }
            java.lang.Object r0 = r0.invoke(r1, r2)     // Catch:{ all -> 0x0057 }
            java.lang.String r3 = r0.toString()     // Catch:{ all -> 0x0057 }
            goto L_0x0061
        L_0x0057:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.String r2 = "Could not invoke Process.myPid(); not Android?"
            r1.debug(r2, r0)
            java.lang.String r3 = ""
        L_0x0061:
            r0 = 64
            int r0 = r3.indexOf(r0)
            if (r0 < 0) goto L_0x006e
            r1 = 0
            java.lang.String r3 = r3.substring(r1, r0)
        L_0x006e:
            int r0 = java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0073 }
            goto L_0x0074
        L_0x0073:
            r0 = -1
        L_0x0074:
            if (r0 >= 0) goto L_0x0089
            java.util.Random r0 = p043io.netty.util.internal.PlatformDependent.threadLocalRandom()
            int r0 = r0.nextInt()
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            java.lang.String r4 = "Failed to find the current process ID from '{}'; using a random value: {}"
            r1.warn(r4, r3, r2)
        L_0x0089:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.DefaultChannelId.defaultProcessId():int");
    }

    private DefaultChannelId() {
        byte[] bArr = MACHINE_ID;
        this.data = new byte[(bArr.length + 4 + 4 + 8 + 4)];
        System.arraycopy(bArr, 0, this.data, 0, bArr.length);
        writeInt(writeLong(writeInt(writeInt(MACHINE_ID.length + 0, PROCESS_ID), nextSequence.getAndIncrement()), Long.reverse(System.nanoTime()) ^ System.currentTimeMillis()), PlatformDependent.threadLocalRandom().nextInt());
    }

    private int writeInt(int i, int i2) {
        byte[] bArr = this.data;
        int i3 = i + 1;
        bArr[i] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i2;
        return i6;
    }

    private int writeLong(int i, long j) {
        byte[] bArr = this.data;
        int i2 = i + 1;
        bArr[i] = (byte) ((int) (j >>> 56));
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) (j >>> 48));
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) (j >>> 40));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) (j >>> 32));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) (j >>> 24));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) (j >>> 16));
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((int) (j >>> 8));
        int i9 = i8 + 1;
        bArr[i8] = (byte) ((int) j);
        return i9;
    }

    public String asShortText() {
        String str = this.shortValue;
        if (str != null) {
            return str;
        }
        byte[] bArr = this.data;
        String hexDump = ByteBufUtil.hexDump(bArr, bArr.length - 4, 4);
        this.shortValue = hexDump;
        return hexDump;
    }

    public String asLongText() {
        String str = this.longValue;
        if (str != null) {
            return str;
        }
        String newLongValue = newLongValue();
        this.longValue = newLongValue;
        return newLongValue;
    }

    private String newLongValue() {
        StringBuilder sb = new StringBuilder((this.data.length * 2) + 5);
        appendHexDumpField(sb, appendHexDumpField(sb, appendHexDumpField(sb, appendHexDumpField(sb, appendHexDumpField(sb, 0, MACHINE_ID.length), 4), 4), 8), 4);
        return sb.substring(0, sb.length() - 1);
    }

    private int appendHexDumpField(StringBuilder sb, int i, int i2) {
        sb.append(ByteBufUtil.hexDump(this.data, i, i2));
        sb.append('-');
        return i + i2;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public int compareTo(ChannelId channelId) {
        if (this == channelId) {
            return 0;
        }
        if (!(channelId instanceof DefaultChannelId)) {
            return asLongText().compareTo(channelId.asLongText());
        }
        byte[] bArr = ((DefaultChannelId) channelId).data;
        int length = this.data.length;
        int length2 = bArr.length;
        int min = Math.min(length, length2);
        for (int i = 0; i < min; i++) {
            byte b = this.data[i];
            byte b2 = bArr[i];
            if (b != b2) {
                return (b & 255) - (b2 & 255);
            }
        }
        return length - length2;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof DefaultChannelId) && Arrays.equals(this.data, ((DefaultChannelId) obj).data));
    }

    public String toString() {
        return asShortText();
    }
}
