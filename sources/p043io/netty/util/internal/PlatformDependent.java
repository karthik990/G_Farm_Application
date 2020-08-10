package p043io.netty.util.internal;

import com.google.common.base.Ascii;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Deque;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;
import p043io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import p043io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue;
import p043io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue;
import p043io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue;
import p043io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscLinkedAtomicQueue;
import p043io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.PlatformDependent */
public final class PlatformDependent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ADDRESS_SIZE = addressSize0();
    public static final boolean BIG_ENDIAN_NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final int BIT_MODE = bitMode0();
    private static final long BYTE_ARRAY_BASE_OFFSET = byteArrayBaseOffset0();
    private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = (!isAndroid());
    private static final Cleaner CLEANER;
    private static final int DEFAULT_MAX_MPSC_CAPACITY = 1048576;
    private static final boolean DIRECT_BUFFER_PREFERRED = (HAS_UNSAFE && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false));
    private static final AtomicLong DIRECT_MEMORY_COUNTER;
    private static final long DIRECT_MEMORY_LIMIT;
    private static final boolean HAS_UNSAFE = hasUnsafe0();
    private static final boolean IS_WINDOWS = isWindows0();
    private static final int MAX_ALLOWED_MPSC_CAPACITY = 1073741824;
    private static final long MAX_DIRECT_MEMORY = maxDirectMemory0();
    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
    private static final boolean MAYBE_SUPER_USER = maybeSuperUser0();
    private static final int MIN_MAX_MPSC_CAPACITY = 2048;
    private static final int MPSC_CHUNK_SIZE = 1024;
    private static final Cleaner NOOP = new Cleaner() {
        public void freeDirectBuffer(ByteBuffer byteBuffer) {
        }
    };
    private static final ThreadLocalRandomProvider RANDOM_PROVIDER;
    private static final File TMPDIR = tmpdir0();
    private static final int UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
    private static final boolean USE_DIRECT_BUFFER_NO_CLEANER;
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);

    /* renamed from: io.netty.util.internal.PlatformDependent$AtomicLongCounter */
    private static final class AtomicLongCounter extends AtomicLong implements LongCounter {
        private static final long serialVersionUID = 4074772784610639305L;

        private AtomicLongCounter() {
        }

        public void add(long j) {
            addAndGet(j);
        }

        public void increment() {
            incrementAndGet();
        }

        public void decrement() {
            decrementAndGet();
        }

        public long value() {
            return get();
        }
    }

    /* renamed from: io.netty.util.internal.PlatformDependent$Mpsc */
    private static final class Mpsc {
        private static final boolean USE_MPSC_CHUNKED_ARRAY_QUEUE;

        private Mpsc() {
        }

        static {
            if ((PlatformDependent.hasUnsafe() ? AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    return UnsafeAccess.UNSAFE;
                }
            }) : null) == null) {
                PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: unavailable");
                USE_MPSC_CHUNKED_ARRAY_QUEUE = false;
                return;
            }
            PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: available");
            USE_MPSC_CHUNKED_ARRAY_QUEUE = true;
        }

        static <T> Queue<T> newMpscQueue(int i) {
            if (USE_MPSC_CHUNKED_ARRAY_QUEUE) {
                return new MpscChunkedArrayQueue(1024, Math.max(Math.min(i, 1073741824), 2048));
            }
            return new MpscLinkedAtomicQueue();
        }
    }

    /* renamed from: io.netty.util.internal.PlatformDependent$ThreadLocalRandomProvider */
    private interface ThreadLocalRandomProvider {
        Random current();
    }

    private static int hashCodeAsciiSanitizeByte(char c) {
        return c & 31;
    }

    static {
        if (javaVersion() >= 7) {
            RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
                public Random current() {
                    return ThreadLocalRandom.current();
                }
            };
        } else {
            RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
                public Random current() {
                    return ThreadLocalRandom.current();
                }
            };
        }
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noPreferDirect: {}", (Object) Boolean.valueOf(!DIRECT_BUFFER_PREFERRED));
        }
        if (!hasUnsafe() && !isAndroid() && !PlatformDependent0.isExplicitNoUnsafe()) {
            logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system instability.");
        }
        long j = SystemPropertyUtil.getLong("io.netty.maxDirectMemory", -1);
        if (j == 0 || !hasUnsafe() || !PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            USE_DIRECT_BUFFER_NO_CLEANER = false;
            DIRECT_MEMORY_COUNTER = null;
        } else {
            USE_DIRECT_BUFFER_NO_CLEANER = true;
            if (j < 0) {
                j = maxDirectMemory0();
                if (j <= 0) {
                    DIRECT_MEMORY_COUNTER = null;
                } else {
                    DIRECT_MEMORY_COUNTER = new AtomicLong();
                }
            } else {
                DIRECT_MEMORY_COUNTER = new AtomicLong();
            }
        }
        DIRECT_MEMORY_LIMIT = j;
        logger.debug("-Dio.netty.maxDirectMemory: {} bytes", (Object) Long.valueOf(j));
        int i = SystemPropertyUtil.getInt("io.netty.uninitializedArrayAllocationThreshold", 1024);
        if (javaVersion() < 9 || !PlatformDependent0.hasAllocateArrayMethod()) {
            i = -1;
        }
        UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD = i;
        logger.debug("-Dio.netty.uninitializedArrayAllocationThreshold: {}", (Object) Integer.valueOf(UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD));
        if (isAndroid() || !hasUnsafe()) {
            CLEANER = NOOP;
        } else if (javaVersion() >= 9) {
            CLEANER = CleanerJava9.isSupported() ? new CleanerJava9() : NOOP;
        } else {
            CLEANER = CleanerJava6.isSupported() ? new CleanerJava6() : NOOP;
        }
    }

    public static byte[] allocateUninitializedArray(int i) {
        int i2 = UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
        if (i2 < 0 || i2 > i) {
            return new byte[i];
        }
        return PlatformDependent0.allocateUninitializedArray(i);
    }

    public static boolean isAndroid() {
        return PlatformDependent0.isAndroid();
    }

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    public static boolean maybeSuperUser() {
        return MAYBE_SUPER_USER;
    }

    public static int javaVersion() {
        return PlatformDependent0.javaVersion();
    }

    public static boolean canEnableTcpNoDelayByDefault() {
        return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    }

    public static boolean hasUnsafe() {
        return HAS_UNSAFE;
    }

    public static boolean isUnaligned() {
        return PlatformDependent0.isUnaligned();
    }

    public static boolean directBufferPreferred() {
        return DIRECT_BUFFER_PREFERRED;
    }

    public static long maxDirectMemory() {
        return MAX_DIRECT_MEMORY;
    }

    public static File tmpdir() {
        return TMPDIR;
    }

    public static int bitMode() {
        return BIT_MODE;
    }

    public static int addressSize() {
        return ADDRESS_SIZE;
    }

    public static long allocateMemory(long j) {
        return PlatformDependent0.allocateMemory(j);
    }

    public static void freeMemory(long j) {
        PlatformDependent0.freeMemory(j);
    }

    public static long reallocateMemory(long j, long j2) {
        return PlatformDependent0.reallocateMemory(j, j2);
    }

    public static void throwException(Throwable th) {
        if (hasUnsafe()) {
            PlatformDependent0.throwException(th);
        } else {
            throwException0(th);
        }
    }

    private static <E extends Throwable> void throwException0(Throwable th) throws Throwable {
        throw th;
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static LongCounter newLongCounter() {
        if (javaVersion() >= 8) {
            return new LongAdderCounter();
        }
        return new AtomicLongCounter();
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i) {
        return new ConcurrentHashMap(i);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i, float f) {
        return new ConcurrentHashMap(i, f);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i, float f, int i2) {
        return new ConcurrentHashMap(i, f, i2);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
        return new ConcurrentHashMap(map);
    }

    public static void freeDirectBuffer(ByteBuffer byteBuffer) {
        CLEANER.freeDirectBuffer(byteBuffer);
    }

    public static long directBufferAddress(ByteBuffer byteBuffer) {
        return PlatformDependent0.directBufferAddress(byteBuffer);
    }

    public static ByteBuffer directBuffer(long j, int i) {
        if (PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            return PlatformDependent0.newDirectBuffer(j, i);
        }
        throw new UnsupportedOperationException("sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    public static int getInt(Object obj, long j) {
        return PlatformDependent0.getInt(obj, j);
    }

    public static byte getByte(long j) {
        return PlatformDependent0.getByte(j);
    }

    public static short getShort(long j) {
        return PlatformDependent0.getShort(j);
    }

    public static int getInt(long j) {
        return PlatformDependent0.getInt(j);
    }

    public static long getLong(long j) {
        return PlatformDependent0.getLong(j);
    }

    public static byte getByte(byte[] bArr, int i) {
        return PlatformDependent0.getByte(bArr, i);
    }

    public static short getShort(byte[] bArr, int i) {
        return PlatformDependent0.getShort(bArr, i);
    }

    public static int getInt(byte[] bArr, int i) {
        return PlatformDependent0.getInt(bArr, i);
    }

    public static long getLong(byte[] bArr, int i) {
        return PlatformDependent0.getLong(bArr, i);
    }

    private static long getLongSafe(byte[] bArr, int i) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (((long) bArr[i + 7]) & 255) | (((long) bArr[i]) << 56) | ((((long) bArr[i + 1]) & 255) << 48) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 6]) & 255) << 8);
        }
        return (((long) bArr[i + 7]) << 56) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 2]) & 255) << 16) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 40);
    }

    private static int getIntSafe(byte[] bArr, int i) {
        byte b;
        int i2;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            b = (bArr[i] << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
            i2 = bArr[i + 3] & 255;
        } else {
            b = (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
            i2 = bArr[i + 3] << Ascii.CAN;
        }
        return i2 | b;
    }

    private static short getShortSafe(byte[] bArr, int i) {
        int i2;
        int i3;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            i2 = bArr[i] << 8;
            i3 = bArr[i + 1] & 255;
        } else {
            i2 = bArr[i] & 255;
            i3 = bArr[i + 1] << 8;
        }
        return (short) (i3 | i2);
    }

    private static int hashCodeAsciiCompute(CharSequence charSequence, int i, int i2) {
        int hashCodeAsciiSanitizeInt;
        int hashCodeAsciiSanitizeInt2;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            hashCodeAsciiSanitizeInt = (i2 * 461845907) + (hashCodeAsciiSanitizeInt(charSequence, i + 4) * 461845907);
            hashCodeAsciiSanitizeInt2 = hashCodeAsciiSanitizeInt(charSequence, i);
        } else {
            hashCodeAsciiSanitizeInt = (i2 * 461845907) + (hashCodeAsciiSanitizeInt(charSequence, i) * 461845907);
            hashCodeAsciiSanitizeInt2 = hashCodeAsciiSanitizeInt(charSequence, i + 4);
        }
        return hashCodeAsciiSanitizeInt + hashCodeAsciiSanitizeInt2;
    }

    private static int hashCodeAsciiSanitizeInt(CharSequence charSequence, int i) {
        char charAt;
        int charAt2;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            charAt = (charSequence.charAt(i + 3) & 31) | ((charSequence.charAt(i + 2) & 31) << 8) | ((charSequence.charAt(i + 1) & 31) << 16);
            charAt2 = (charSequence.charAt(i) & 31) << 24;
        } else {
            charAt = ((charSequence.charAt(i + 3) & 31) << 24) | ((charSequence.charAt(i + 2) & 31) << 16) | ((charSequence.charAt(i + 1) & 31) << 8);
            charAt2 = charSequence.charAt(i) & 31;
        }
        return charAt2 | charAt;
    }

    private static int hashCodeAsciiSanitizeShort(CharSequence charSequence, int i) {
        int charAt;
        int charAt2;
        if (BIG_ENDIAN_NATIVE_ORDER) {
            charAt = charSequence.charAt(i + 1) & 31;
            charAt2 = (charSequence.charAt(i) & 31) << 8;
        } else {
            charAt = (charSequence.charAt(i + 1) & 31) << 8;
            charAt2 = charSequence.charAt(i) & 31;
        }
        return charAt2 | charAt;
    }

    public static void putByte(long j, byte b) {
        PlatformDependent0.putByte(j, b);
    }

    public static void putShort(long j, short s) {
        PlatformDependent0.putShort(j, s);
    }

    public static void putInt(long j, int i) {
        PlatformDependent0.putInt(j, i);
    }

    public static void putLong(long j, long j2) {
        PlatformDependent0.putLong(j, j2);
    }

    public static void putByte(byte[] bArr, int i, byte b) {
        PlatformDependent0.putByte(bArr, i, b);
    }

    public static void putShort(byte[] bArr, int i, short s) {
        PlatformDependent0.putShort(bArr, i, s);
    }

    public static void putInt(byte[] bArr, int i, int i2) {
        PlatformDependent0.putInt(bArr, i, i2);
    }

    public static void putLong(byte[] bArr, int i, long j) {
        PlatformDependent0.putLong(bArr, i, j);
    }

    public static void copyMemory(long j, long j2, long j3) {
        PlatformDependent0.copyMemory(j, j2, j3);
    }

    public static void copyMemory(byte[] bArr, int i, long j, long j2) {
        PlatformDependent0.copyMemory(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), null, j, j2);
    }

    public static void copyMemory(long j, byte[] bArr, int i, long j2) {
        PlatformDependent0.copyMemory(null, j, bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), j2);
    }

    public static void setMemory(byte[] bArr, int i, long j, byte b) {
        PlatformDependent0.setMemory(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), j, b);
    }

    public static void setMemory(long j, long j2, byte b) {
        PlatformDependent0.setMemory(j, j2, b);
    }

    public static ByteBuffer allocateDirectNoCleaner(int i) {
        incrementMemoryCounter(i);
        try {
            return PlatformDependent0.allocateDirectNoCleaner(i);
        } catch (Throwable th) {
            decrementMemoryCounter(i);
            throwException(th);
            return null;
        }
    }

    public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer byteBuffer, int i) {
        int capacity = i - byteBuffer.capacity();
        incrementMemoryCounter(capacity);
        try {
            return PlatformDependent0.reallocateDirectNoCleaner(byteBuffer, i);
        } catch (Throwable th) {
            decrementMemoryCounter(capacity);
            throwException(th);
            return null;
        }
    }

    public static void freeDirectNoCleaner(ByteBuffer byteBuffer) {
        int capacity = byteBuffer.capacity();
        PlatformDependent0.freeMemory(PlatformDependent0.directBufferAddress(byteBuffer));
        decrementMemoryCounter(capacity);
    }

    private static void incrementMemoryCounter(int i) {
        long j;
        long j2;
        if (DIRECT_MEMORY_COUNTER != null) {
            do {
                j = DIRECT_MEMORY_COUNTER.get();
                j2 = ((long) i) + j;
                if (j2 > DIRECT_MEMORY_LIMIT) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("failed to allocate ");
                    sb.append(i);
                    sb.append(" byte(s) of direct memory (used: ");
                    sb.append(j);
                    sb.append(", max: ");
                    sb.append(DIRECT_MEMORY_LIMIT);
                    sb.append(')');
                    throw new OutOfDirectMemoryError(sb.toString());
                }
            } while (!DIRECT_MEMORY_COUNTER.compareAndSet(j, j2));
        }
    }

    private static void decrementMemoryCounter(int i) {
        AtomicLong atomicLong = DIRECT_MEMORY_COUNTER;
        if (atomicLong != null) {
            atomicLong.addAndGet((long) (-i));
        }
    }

    public static boolean useDirectBufferNoCleaner() {
        return USE_DIRECT_BUFFER_NO_CLEANER;
    }

    public static boolean equals(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) {
            return equalsSafe(bArr, i, bArr2, i2, i3);
        }
        return PlatformDependent0.equals(bArr, i, bArr2, i2, i3);
    }

    public static boolean isZero(byte[] bArr, int i, int i2) {
        if (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) {
            return isZeroSafe(bArr, i, i2);
        }
        return PlatformDependent0.isZero(bArr, i, i2);
    }

    public static int equalsConstantTime(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) {
            return ConstantTimeUtils.equalsConstantTime(bArr, i, bArr2, i2, i3);
        }
        return PlatformDependent0.equalsConstantTime(bArr, i, bArr2, i2, i3);
    }

    public static int hashCodeAscii(byte[] bArr, int i, int i2) {
        if (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) {
            return hashCodeAsciiSafe(bArr, i, i2);
        }
        return PlatformDependent0.hashCodeAscii(bArr, i, i2);
    }

    public static int hashCodeAscii(CharSequence charSequence) {
        int i;
        int hashCodeAsciiSanitizeByte;
        int length = charSequence.length() & 7;
        int i2 = -1028477387;
        switch (charSequence.length()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                i2 = hashCodeAsciiCompute(charSequence, charSequence.length() - 8, -1028477387);
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                i2 = hashCodeAsciiCompute(charSequence, charSequence.length() - 16, hashCodeAsciiCompute(charSequence, charSequence.length() - 8, -1028477387));
                break;
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                i2 = hashCodeAsciiCompute(charSequence, charSequence.length() - 24, hashCodeAsciiCompute(charSequence, charSequence.length() - 16, hashCodeAsciiCompute(charSequence, charSequence.length() - 8, -1028477387)));
                break;
            default:
                for (int length2 = charSequence.length() - 8; length2 >= length; length2 -= 8) {
                    i2 = hashCodeAsciiCompute(charSequence, length2, i2);
                }
                break;
        }
        switch (length) {
            case 1:
                i = i2 * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeByte(charSequence.charAt(0));
                break;
            case 2:
                i = i2 * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeShort(charSequence, 0);
                break;
            case 3:
                i = ((i2 * 461845907) + hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeShort(charSequence, 1);
                break;
            case 4:
                i = i2 * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeInt(charSequence, 0);
                break;
            case 5:
                i = ((i2 * 461845907) + hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeInt(charSequence, 1);
                break;
            case 6:
                i = ((i2 * 461845907) + hashCodeAsciiSanitizeShort(charSequence, 0)) * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeInt(charSequence, 2);
                break;
            case 7:
                i = ((((i2 * 461845907) + hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907) + hashCodeAsciiSanitizeShort(charSequence, 1)) * 461845907;
                hashCodeAsciiSanitizeByte = hashCodeAsciiSanitizeInt(charSequence, 3);
                break;
            default:
                return i2;
        }
        return i + hashCodeAsciiSanitizeByte;
    }

    public static <T> Queue<T> newMpscQueue() {
        return newMpscQueue(1048576);
    }

    public static <T> Queue<T> newMpscQueue(int i) {
        return Mpsc.newMpscQueue(i);
    }

    public static <T> Queue<T> newSpscQueue() {
        return hasUnsafe() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue();
    }

    public static <T> Queue<T> newFixedMpscQueue(int i) {
        return hasUnsafe() ? new MpscArrayQueue(i) : new MpscAtomicArrayQueue(i);
    }

    public static ClassLoader getClassLoader(Class<?> cls) {
        return PlatformDependent0.getClassLoader(cls);
    }

    public static ClassLoader getContextClassLoader() {
        return PlatformDependent0.getContextClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return PlatformDependent0.getSystemClassLoader();
    }

    public static <C> Deque<C> newConcurrentDeque() {
        if (javaVersion() < 7) {
            return new LinkedBlockingDeque();
        }
        return new ConcurrentLinkedDeque();
    }

    public static Random threadLocalRandom() {
        return RANDOM_PROVIDER.current();
    }

    private static boolean isWindows0() {
        boolean contains = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
        if (contains) {
            logger.debug("Platform: Windows");
        }
        return contains;
    }

    private static boolean maybeSuperUser0() {
        String str = SystemPropertyUtil.get("user.name");
        if (isWindows()) {
            return "Administrator".equals(str);
        }
        return "root".equals(str) || "toor".equals(str);
    }

    private static boolean hasUnsafe0() {
        if (isAndroid()) {
            logger.debug("sun.misc.Unsafe: unavailable (Android)");
            return false;
        } else if (PlatformDependent0.isExplicitNoUnsafe()) {
            return false;
        } else {
            try {
                boolean hasUnsafe = PlatformDependent0.hasUnsafe();
                logger.debug("sun.misc.Unsafe: {}", (Object) hasUnsafe ? "available" : "unavailable");
                return hasUnsafe;
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0098, code lost:
        if (r0 != 'm') goto L_0x00a9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long maxDirectMemory0() {
        /*
            r0 = 0
            r1 = 0
            r3 = 1
            r4 = 0
            java.lang.ClassLoader r5 = getSystemClassLoader()     // Catch:{ all -> 0x0024 }
            java.lang.String r6 = "sun.misc.VM"
            java.lang.Class r6 = java.lang.Class.forName(r6, r3, r5)     // Catch:{ all -> 0x0025 }
            java.lang.String r7 = "maxDirectMemory"
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ all -> 0x0025 }
            java.lang.reflect.Method r6 = r6.getDeclaredMethod(r7, r8)     // Catch:{ all -> 0x0025 }
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0025 }
            java.lang.Object r6 = r6.invoke(r0, r7)     // Catch:{ all -> 0x0025 }
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ all -> 0x0025 }
            long r6 = r6.longValue()     // Catch:{ all -> 0x0025 }
            goto L_0x0026
        L_0x0024:
            r5 = r0
        L_0x0025:
            r6 = r1
        L_0x0026:
            int r8 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r8 <= 0) goto L_0x002b
            return r6
        L_0x002b:
            java.lang.String r8 = "java.lang.management.ManagementFactory"
            java.lang.Class r8 = java.lang.Class.forName(r8, r3, r5)     // Catch:{ all -> 0x00a8 }
            java.lang.String r9 = "java.lang.management.RuntimeMXBean"
            java.lang.Class r5 = java.lang.Class.forName(r9, r3, r5)     // Catch:{ all -> 0x00a8 }
            java.lang.String r9 = "getRuntimeMXBean"
            java.lang.Class[] r10 = new java.lang.Class[r4]     // Catch:{ all -> 0x00a8 }
            java.lang.reflect.Method r8 = r8.getDeclaredMethod(r9, r10)     // Catch:{ all -> 0x00a8 }
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ all -> 0x00a8 }
            java.lang.Object r0 = r8.invoke(r0, r9)     // Catch:{ all -> 0x00a8 }
            java.lang.String r8 = "getInputArguments"
            java.lang.Class[] r9 = new java.lang.Class[r4]     // Catch:{ all -> 0x00a8 }
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r8, r9)     // Catch:{ all -> 0x00a8 }
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ all -> 0x00a8 }
            java.lang.Object r0 = r5.invoke(r0, r8)     // Catch:{ all -> 0x00a8 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x00a8 }
            int r5 = r0.size()     // Catch:{ all -> 0x00a8 }
            int r5 = r5 - r3
        L_0x005a:
            if (r5 < 0) goto L_0x00a9
            java.util.regex.Pattern r8 = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN     // Catch:{ all -> 0x00a8 }
            java.lang.Object r9 = r0.get(r5)     // Catch:{ all -> 0x00a8 }
            java.lang.CharSequence r9 = (java.lang.CharSequence) r9     // Catch:{ all -> 0x00a8 }
            java.util.regex.Matcher r8 = r8.matcher(r9)     // Catch:{ all -> 0x00a8 }
            boolean r9 = r8.matches()     // Catch:{ all -> 0x00a8 }
            if (r9 != 0) goto L_0x0071
            int r5 = r5 + -1
            goto L_0x005a
        L_0x0071:
            java.lang.String r0 = r8.group(r3)     // Catch:{ all -> 0x00a8 }
            long r6 = java.lang.Long.parseLong(r0)     // Catch:{ all -> 0x00a8 }
            r0 = 2
            java.lang.String r0 = r8.group(r0)     // Catch:{ all -> 0x00a8 }
            char r0 = r0.charAt(r4)     // Catch:{ all -> 0x00a8 }
            r3 = 71
            if (r0 == r3) goto L_0x00a2
            r3 = 75
            if (r0 == r3) goto L_0x009f
            r3 = 77
            if (r0 == r3) goto L_0x009b
            r3 = 103(0x67, float:1.44E-43)
            if (r0 == r3) goto L_0x00a2
            r3 = 107(0x6b, float:1.5E-43)
            if (r0 == r3) goto L_0x009f
            r3 = 109(0x6d, float:1.53E-43)
            if (r0 == r3) goto L_0x009b
            goto L_0x00a9
        L_0x009b:
            r3 = 1048576(0x100000, double:5.180654E-318)
            goto L_0x00a5
        L_0x009f:
            r3 = 1024(0x400, double:5.06E-321)
            goto L_0x00a5
        L_0x00a2:
            r3 = 1073741824(0x40000000, double:5.304989477E-315)
        L_0x00a5:
            long r6 = r6 * r3
            goto L_0x00a9
        L_0x00a8:
        L_0x00a9:
            int r0 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r0 > 0) goto L_0x00c1
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()
            long r6 = r0.maxMemory()
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.Long r1 = java.lang.Long.valueOf(r6)
            java.lang.String r2 = "maxDirectMemory: {} bytes (maybe)"
            r0.debug(r2, r1)
            goto L_0x00cc
        L_0x00c1:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.Long r1 = java.lang.Long.valueOf(r6)
            java.lang.String r2 = "maxDirectMemory: {} bytes"
            r0.debug(r2, r1)
        L_0x00cc:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.PlatformDependent.maxDirectMemory0():long");
    }

    private static File tmpdir0() {
        File file;
        try {
            File directory = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
            if (directory != null) {
                logger.debug("-Dio.netty.tmpdir: {}", (Object) directory);
                return directory;
            }
            File directory2 = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
            if (directory2 != null) {
                logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", (Object) directory2);
                return directory2;
            }
            if (isWindows()) {
                File directory3 = toDirectory(System.getenv("TEMP"));
                if (directory3 != null) {
                    logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", (Object) directory3);
                    return directory3;
                }
                String str = System.getenv("USERPROFILE");
                if (str != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("\\AppData\\Local\\Temp");
                    File directory4 = toDirectory(sb.toString());
                    if (directory4 != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", (Object) directory4);
                        return directory4;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("\\Local Settings\\Temp");
                    File directory5 = toDirectory(sb2.toString());
                    if (directory5 != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", (Object) directory5);
                        return directory5;
                    }
                }
            } else {
                File directory6 = toDirectory(System.getenv("TMPDIR"));
                if (directory6 != null) {
                    logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", (Object) directory6);
                    return directory6;
                }
            }
            if (isWindows()) {
                file = new File("C:\\Windows\\Temp");
            } else {
                file = new File("/tmp");
            }
            logger.warn("Failed to get the temporary directory; falling back to: {}", (Object) file);
            return file;
        } catch (Throwable unused) {
        }
    }

    private static File toDirectory(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        try {
            return file.getAbsoluteFile();
        } catch (Exception unused) {
            return file;
        }
    }

    private static int bitMode0() {
        int i = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
        if (i > 0) {
            logger.debug("-Dio.netty.bitMode: {}", (Object) Integer.valueOf(i));
            return i;
        }
        int i2 = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
        if (i2 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", (Object) Integer.valueOf(i2));
            return i2;
        }
        int i3 = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
        if (i3 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", (Object) Integer.valueOf(i3));
            return i3;
        }
        String str = "";
        String trim = SystemPropertyUtil.get("os.arch", str).toLowerCase(Locale.US).trim();
        if ("amd64".equals(trim) || "x86_64".equals(trim)) {
            i3 = 64;
        } else if ("i386".equals(trim) || "i486".equals(trim) || "i586".equals(trim) || "i686".equals(trim)) {
            i3 = 32;
        }
        if (i3 > 0) {
            logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(i3), trim);
        }
        Matcher matcher = Pattern.compile("([1-9][0-9]+)-?bit").matcher(SystemPropertyUtil.get("java.vm.name", str).toLowerCase(Locale.US));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 64;
    }

    private static int addressSize0() {
        if (!hasUnsafe()) {
            return -1;
        }
        return PlatformDependent0.addressSize();
    }

    private static long byteArrayBaseOffset0() {
        if (!hasUnsafe()) {
            return -1;
        }
        return PlatformDependent0.byteArrayBaseOffset();
    }

    private static boolean equalsSafe(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i3 + i;
        while (i < i4) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    private static boolean isZeroSafe(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            if (bArr[i] != 0) {
                return false;
            }
            i++;
        }
        return true;
    }

    static int hashCodeAsciiSafe(byte[] bArr, int i, int i2) {
        int i3;
        int hashCodeAsciiSanitize;
        int i4 = i2 & 7;
        int i5 = i + i4;
        int i6 = -1028477387;
        for (int i7 = (i - 8) + i2; i7 >= i5; i7 -= 8) {
            i6 = PlatformDependent0.hashCodeAsciiCompute(getLongSafe(bArr, i7), i6);
        }
        switch (i4) {
            case 1:
                i3 = i6 * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(bArr[i]);
                break;
            case 2:
                i3 = i6 * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bArr, i));
                break;
            case 3:
                i3 = ((i6 * 461845907) + PlatformDependent0.hashCodeAsciiSanitize(bArr[i])) * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bArr, i + 1));
                break;
            case 4:
                i3 = i6 * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bArr, i));
                break;
            case 5:
                i3 = ((i6 * 461845907) + PlatformDependent0.hashCodeAsciiSanitize(bArr[i])) * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bArr, i + 1));
                break;
            case 6:
                i3 = ((i6 * 461845907) + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bArr, i))) * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bArr, i + 2));
                break;
            case 7:
                i3 = ((((i6 * 461845907) + PlatformDependent0.hashCodeAsciiSanitize(bArr[i])) * 461845907) + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bArr, i + 1))) * 461845907;
                hashCodeAsciiSanitize = PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bArr, i + 3));
                break;
            default:
                return i6;
        }
        return i3 + hashCodeAsciiSanitize;
    }

    private PlatformDependent() {
    }
}
