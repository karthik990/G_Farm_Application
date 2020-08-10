package p043io.netty.util.internal;

import com.google.common.base.Ascii;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;
import sun.misc.Unsafe;

/* renamed from: io.netty.util.internal.PlatformDependent0 */
final class PlatformDependent0 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long ADDRESS_FIELD_OFFSET;
    private static final Method ALLOCATE_ARRAY_METHOD;
    private static final long BYTE_ARRAY_BASE_OFFSET;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;
    static final int HASH_CODE_ASCII_SEED = -1028477387;
    static final int HASH_CODE_C1 = 461845907;
    static final int HASH_CODE_C2 = 461845907;
    private static final Object INTERNAL_UNSAFE;
    private static final boolean IS_ANDROID = isAndroid0();
    private static final boolean IS_EXPLICIT_NO_UNSAFE = explicitNoUnsafe0();
    private static final int JAVA_VERSION = javaVersion0();
    private static final boolean UNALIGNED;
    static final Unsafe UNSAFE;
    private static final long UNSAFE_COPY_THRESHOLD = 1048576;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);

    static int hashCodeAsciiSanitize(byte b) {
        return b & Ascii.f1878US;
    }

    static int hashCodeAsciiSanitize(int i) {
        return i & 522133279;
    }

    static int hashCodeAsciiSanitize(short s) {
        return s & 7967;
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x01d5  */
    static {
        /*
            java.lang.Class<byte[]> r0 = byte[].class
            java.lang.Class<io.netty.util.internal.PlatformDependent0> r1 = p043io.netty.util.internal.PlatformDependent0.class
            io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.internal.logging.InternalLoggerFactory.getInstance(r1)
            logger = r1
            boolean r1 = explicitNoUnsafe0()
            IS_EXPLICIT_NO_UNSAFE = r1
            int r1 = javaVersion0()
            JAVA_VERSION = r1
            boolean r1 = isAndroid0()
            IS_ANDROID = r1
            boolean r1 = isExplicitNoUnsafe()
            r2 = 1
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L_0x002b
            r1 = r5
            r6 = r1
            r7 = r6
            goto L_0x00ac
        L_0x002b:
            java.nio.ByteBuffer r1 = java.nio.ByteBuffer.allocateDirect(r4)
            io.netty.util.internal.PlatformDependent0$1 r6 = new io.netty.util.internal.PlatformDependent0$1
            r6.<init>()
            java.lang.Object r6 = java.security.AccessController.doPrivileged(r6)
            boolean r7 = r6 instanceof java.lang.Exception
            if (r7 == 0) goto L_0x0047
            io.netty.util.internal.logging.InternalLogger r7 = logger
            java.lang.Exception r6 = (java.lang.Exception) r6
            java.lang.String r8 = "sun.misc.Unsafe.theUnsafe: unavailable"
            r7.debug(r8, r6)
            r6 = r5
            goto L_0x0050
        L_0x0047:
            sun.misc.Unsafe r6 = (sun.misc.Unsafe) r6
            io.netty.util.internal.logging.InternalLogger r7 = logger
            java.lang.String r8 = "sun.misc.Unsafe.theUnsafe: available"
            r7.debug(r8)
        L_0x0050:
            if (r6 == 0) goto L_0x006f
            io.netty.util.internal.PlatformDependent0$2 r7 = new io.netty.util.internal.PlatformDependent0$2
            r7.<init>(r6)
            java.lang.Object r7 = java.security.AccessController.doPrivileged(r7)
            if (r7 != 0) goto L_0x0065
            io.netty.util.internal.logging.InternalLogger r7 = logger
            java.lang.String r8 = "sun.misc.Unsafe.copyMemory: available"
            r7.debug(r8)
            goto L_0x006f
        L_0x0065:
            io.netty.util.internal.logging.InternalLogger r6 = logger
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            java.lang.String r8 = "sun.misc.Unsafe.copyMemory: unavailable"
            r6.debug(r8, r7)
            r6 = r5
        L_0x006f:
            if (r6 == 0) goto L_0x0094
            io.netty.util.internal.PlatformDependent0$3 r7 = new io.netty.util.internal.PlatformDependent0$3
            r7.<init>(r6, r1)
            java.lang.Object r7 = java.security.AccessController.doPrivileged(r7)
            boolean r8 = r7 instanceof java.lang.reflect.Field
            if (r8 == 0) goto L_0x0088
            java.lang.reflect.Field r7 = (java.lang.reflect.Field) r7
            io.netty.util.internal.logging.InternalLogger r8 = logger
            java.lang.String r9 = "java.nio.Buffer.address: available"
            r8.debug(r9)
            goto L_0x0095
        L_0x0088:
            io.netty.util.internal.logging.InternalLogger r6 = logger
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            java.lang.String r8 = "java.nio.Buffer.address: unavailable"
            r6.debug(r8, r7)
            r6 = r5
            r7 = r6
            goto L_0x0095
        L_0x0094:
            r7 = r5
        L_0x0095:
            if (r6 == 0) goto L_0x00ac
            int r8 = r6.arrayIndexScale(r0)
            long r8 = (long) r8
            int r10 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r10 == 0) goto L_0x00ac
            io.netty.util.internal.logging.InternalLogger r6 = logger
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            java.lang.String r9 = "unsafe.arrayIndexScale is {} (expected: 1). Not using unsafe."
            r6.debug(r9, r8)
            r6 = r5
        L_0x00ac:
            UNSAFE = r6
            r8 = 0
            r9 = -1
            if (r6 != 0) goto L_0x00c0
            ADDRESS_FIELD_OFFSET = r9
            BYTE_ARRAY_BASE_OFFSET = r9
            UNALIGNED = r8
            DIRECT_BUFFER_CONSTRUCTOR = r5
            ALLOCATE_ARRAY_METHOD = r5
            r0 = r5
            goto L_0x01bc
        L_0x00c0:
            io.netty.util.internal.PlatformDependent0$4 r6 = new io.netty.util.internal.PlatformDependent0$4     // Catch:{ all -> 0x01cf }
            r6.<init>(r1)     // Catch:{ all -> 0x01cf }
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r6)     // Catch:{ all -> 0x01cf }
            boolean r6 = r1 instanceof java.lang.reflect.Constructor     // Catch:{ all -> 0x01cf }
            r11 = 2
            if (r6 == 0) goto L_0x00f7
            sun.misc.Unsafe r6 = UNSAFE     // Catch:{ all -> 0x01cf }
            long r2 = r6.allocateMemory(r2)     // Catch:{ all -> 0x01cf }
            r6 = r1
            java.lang.reflect.Constructor r6 = (java.lang.reflect.Constructor) r6     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            java.lang.Long r13 = java.lang.Long.valueOf(r2)     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            r12[r8] = r13     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r4)     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            r12[r4] = r13     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            r6.newInstance(r12)     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            java.lang.reflect.Constructor r1 = (java.lang.reflect.Constructor) r1     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            io.netty.util.internal.logging.InternalLogger r6 = logger     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            java.lang.String r12 = "direct buffer constructor: available"
            r6.debug(r12)     // Catch:{ IllegalAccessException | InstantiationException | InvocationTargetException -> 0x00f5, all -> 0x00f2 }
            goto L_0x0102
        L_0x00f2:
            r0 = move-exception
            goto L_0x01d1
        L_0x00f5:
            r1 = r5
            goto L_0x0102
        L_0x00f7:
            io.netty.util.internal.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x01cf }
            java.lang.String r3 = "direct buffer constructor: unavailable"
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x01cf }
            r2.debug(r3, r1)     // Catch:{ all -> 0x01cf }
            r1 = r5
            r2 = r9
        L_0x0102:
            int r6 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r6 == 0) goto L_0x010b
            sun.misc.Unsafe r6 = UNSAFE
            r6.freeMemory(r2)
        L_0x010b:
            DIRECT_BUFFER_CONSTRUCTOR = r1
            long r1 = objectFieldOffset(r7)
            ADDRESS_FIELD_OFFSET = r1
            sun.misc.Unsafe r1 = UNSAFE
            int r0 = r1.arrayBaseOffset(r0)
            long r0 = (long) r0
            BYTE_ARRAY_BASE_OFFSET = r0
            io.netty.util.internal.PlatformDependent0$5 r0 = new io.netty.util.internal.PlatformDependent0$5
            r0.<init>()
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            boolean r1 = r0 instanceof java.lang.Boolean
            if (r1 == 0) goto L_0x013b
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)
            java.lang.String r3 = "java.nio.Bits.unaligned: available, {}"
            r1.debug(r3, r2)
            goto L_0x0157
        L_0x013b:
            java.lang.String r1 = "os.arch"
            java.lang.String r2 = ""
            java.lang.String r1 = p043io.netty.util.internal.SystemPropertyUtil.get(r1, r2)
            java.lang.String r2 = "^(i[3-6]86|x86(_64)?|x64|amd64)$"
            boolean r1 = r1.matches(r2)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            io.netty.util.internal.logging.InternalLogger r2 = logger
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            java.lang.String r6 = "java.nio.Bits.unaligned: unavailable {}"
            r2.debug(r6, r3, r0)
            r0 = r1
        L_0x0157:
            UNALIGNED = r0
            int r0 = javaVersion()
            r1 = 9
            if (r0 < r1) goto L_0x01b2
            io.netty.util.internal.PlatformDependent0$6 r0 = new io.netty.util.internal.PlatformDependent0$6
            r0.<init>()
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            boolean r1 = r0 instanceof java.lang.Throwable
            if (r1 != 0) goto L_0x019a
            io.netty.util.internal.PlatformDependent0$7 r1 = new io.netty.util.internal.PlatformDependent0$7
            r1.<init>(r0)
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)
            boolean r2 = r1 instanceof java.lang.reflect.Method
            if (r2 == 0) goto L_0x019c
            r2 = r1
            java.lang.reflect.Method r2 = (java.lang.reflect.Method) r2     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            java.lang.Object[] r3 = new java.lang.Object[r11]     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            java.lang.Class r6 = java.lang.Byte.TYPE     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            r3[r8] = r6     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            r6 = 8
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            r3[r4] = r6     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            java.lang.Object r3 = r2.invoke(r0, r3)     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            byte[] r3 = (byte[]) r3     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            byte[] r3 = (byte[]) r3     // Catch:{ IllegalAccessException -> 0x0198, InvocationTargetException -> 0x0196 }
            r5 = r2
            goto L_0x019c
        L_0x0196:
            r1 = move-exception
            goto L_0x019c
        L_0x0198:
            r1 = move-exception
            goto L_0x019c
        L_0x019a:
            r1 = r0
            r0 = r5
        L_0x019c:
            boolean r2 = r1 instanceof java.lang.Throwable
            if (r2 == 0) goto L_0x01aa
            io.netty.util.internal.logging.InternalLogger r2 = logger
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            java.lang.String r3 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable"
            r2.debug(r3, r1)
            goto L_0x01ba
        L_0x01aa:
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.String r2 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): available"
            r1.debug(r2)
            goto L_0x01ba
        L_0x01b2:
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r1 = "jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable prior to Java9"
            r0.debug(r1)
            r0 = r5
        L_0x01ba:
            ALLOCATE_ARRAY_METHOD = r5
        L_0x01bc:
            INTERNAL_UNSAFE = r0
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.reflect.Constructor<?> r1 = DIRECT_BUFFER_CONSTRUCTOR
            if (r1 == 0) goto L_0x01c7
            java.lang.String r1 = "available"
            goto L_0x01c9
        L_0x01c7:
            java.lang.String r1 = "unavailable"
        L_0x01c9:
            java.lang.String r2 = "java.nio.DirectByteBuffer.<init>(long, int): {}"
            r0.debug(r2, r1)
            return
        L_0x01cf:
            r0 = move-exception
            r2 = r9
        L_0x01d1:
            int r1 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r1 == 0) goto L_0x01da
            sun.misc.Unsafe r1 = UNSAFE
            r1.freeMemory(r2)
        L_0x01da:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.PlatformDependent0.<clinit>():void");
    }

    static boolean isExplicitNoUnsafe() {
        return IS_EXPLICIT_NO_UNSAFE;
    }

    private static boolean explicitNoUnsafe0() {
        boolean z;
        boolean z2 = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        logger.debug("-Dio.netty.noUnsafe: {}", (Object) Boolean.valueOf(z2));
        if (z2) {
            logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            return true;
        }
        String str = "io.netty.tryUnsafe";
        if (SystemPropertyUtil.contains(str)) {
            z = SystemPropertyUtil.getBoolean(str, true);
        } else {
            z = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
        }
        if (z) {
            return false;
        }
        logger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
        return true;
    }

    static boolean isUnaligned() {
        return UNALIGNED;
    }

    static boolean hasUnsafe() {
        return UNSAFE != null;
    }

    static boolean unalignedAccess() {
        return UNALIGNED;
    }

    static void throwException(Throwable th) {
        UNSAFE.throwException((Throwable) ObjectUtil.checkNotNull(th, "cause"));
    }

    static boolean hasDirectBufferNoCleanerConstructor() {
        return DIRECT_BUFFER_CONSTRUCTOR != null;
    }

    static ByteBuffer reallocateDirectNoCleaner(ByteBuffer byteBuffer, int i) {
        return newDirectBuffer(UNSAFE.reallocateMemory(directBufferAddress(byteBuffer), (long) i), i);
    }

    static ByteBuffer allocateDirectNoCleaner(int i) {
        return newDirectBuffer(UNSAFE.allocateMemory((long) i), i);
    }

    static boolean hasAllocateArrayMethod() {
        return ALLOCATE_ARRAY_METHOD != null;
    }

    static byte[] allocateUninitializedArray(int i) {
        try {
            return (byte[]) ALLOCATE_ARRAY_METHOD.invoke(INTERNAL_UNSAFE, new Object[]{Byte.TYPE, Integer.valueOf(i)});
        } catch (IllegalAccessException e) {
            throw new Error(e);
        } catch (InvocationTargetException e2) {
            throw new Error(e2);
        }
    }

    static ByteBuffer newDirectBuffer(long j, int i) {
        ObjectUtil.checkPositiveOrZero(i, "capacity");
        try {
            return (ByteBuffer) DIRECT_BUFFER_CONSTRUCTOR.newInstance(new Object[]{Long.valueOf(j), Integer.valueOf(i)});
        } catch (Throwable th) {
            if (th instanceof Error) {
                throw th;
            }
            throw new Error(th);
        }
    }

    static long directBufferAddress(ByteBuffer byteBuffer) {
        return getLong((Object) byteBuffer, ADDRESS_FIELD_OFFSET);
    }

    static long byteArrayBaseOffset() {
        return BYTE_ARRAY_BASE_OFFSET;
    }

    static Object getObject(Object obj, long j) {
        return UNSAFE.getObject(obj, j);
    }

    static int getInt(Object obj, long j) {
        return UNSAFE.getInt(obj, j);
    }

    private static long getLong(Object obj, long j) {
        return UNSAFE.getLong(obj, j);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static byte getByte(long j) {
        return UNSAFE.getByte(j);
    }

    static short getShort(long j) {
        return UNSAFE.getShort(j);
    }

    static int getInt(long j) {
        return UNSAFE.getInt(j);
    }

    static long getLong(long j) {
        return UNSAFE.getLong(j);
    }

    static byte getByte(byte[] bArr, int i) {
        return UNSAFE.getByte(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i));
    }

    static short getShort(byte[] bArr, int i) {
        return UNSAFE.getShort(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i));
    }

    static int getInt(byte[] bArr, int i) {
        return UNSAFE.getInt(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i));
    }

    static long getLong(byte[] bArr, int i) {
        return UNSAFE.getLong(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i));
    }

    static void putByte(long j, byte b) {
        UNSAFE.putByte(j, b);
    }

    static void putShort(long j, short s) {
        UNSAFE.putShort(j, s);
    }

    static void putInt(long j, int i) {
        UNSAFE.putInt(j, i);
    }

    static void putLong(long j, long j2) {
        UNSAFE.putLong(j, j2);
    }

    static void putByte(byte[] bArr, int i, byte b) {
        UNSAFE.putByte(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), b);
    }

    static void putShort(byte[] bArr, int i, short s) {
        UNSAFE.putShort(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), s);
    }

    static void putInt(byte[] bArr, int i, int i2) {
        UNSAFE.putInt(bArr, BYTE_ARRAY_BASE_OFFSET + ((long) i), i2);
    }

    static void putLong(byte[] bArr, int i, long j) {
        UNSAFE.putLong(bArr, ((long) i) + BYTE_ARRAY_BASE_OFFSET, j);
    }

    static void copyMemory(long j, long j2, long j3) {
        while (j3 > 0) {
            long min = Math.min(j3, 1048576);
            UNSAFE.copyMemory(j, j2, min);
            j3 -= min;
            j += min;
            j2 += min;
        }
    }

    static void copyMemory(Object obj, long j, Object obj2, long j2, long j3) {
        long j4 = j;
        long j5 = j2;
        long j6 = j3;
        while (j6 > 0) {
            long min = Math.min(j6, 1048576);
            UNSAFE.copyMemory(obj, j4, obj2, j5, min);
            j6 -= min;
            j4 += min;
            j5 += min;
        }
    }

    static void setMemory(long j, long j2, byte b) {
        UNSAFE.setMemory(j, j2, b);
    }

    static void setMemory(Object obj, long j, long j2, byte b) {
        UNSAFE.setMemory(obj, j, j2, b);
    }

    static boolean equals(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        byte[] bArr3 = bArr;
        int i4 = i;
        byte[] bArr4 = bArr2;
        int i5 = i2;
        int i6 = i3;
        if (i6 <= 0) {
            return true;
        }
        long j = BYTE_ARRAY_BASE_OFFSET;
        long j2 = ((long) i4) + j;
        long j3 = j + ((long) i5);
        int i7 = i6 & 7;
        long j4 = ((long) i7) + j2;
        long j5 = (long) i6;
        long j6 = (j3 - 8) + j5;
        long j7 = (j2 - 8) + j5;
        long j8 = j6;
        while (j7 >= j4) {
            long j9 = j4;
            if (UNSAFE.getLong(bArr3, j7) != UNSAFE.getLong(bArr4, j8)) {
                return false;
            }
            j7 -= 8;
            j8 -= 8;
            j4 = j9;
        }
        if (i7 >= 4) {
            i7 -= 4;
            long j10 = (long) i7;
            if (UNSAFE.getInt(bArr3, j2 + j10) != UNSAFE.getInt(bArr4, j10 + j3)) {
                return false;
            }
        }
        if (i7 >= 2) {
            return UNSAFE.getChar(bArr3, j2) == UNSAFE.getChar(bArr4, j3) && (i7 == 2 || bArr3[i4 + 2] == bArr4[i5 + 2]);
        }
        return bArr3[i4] == bArr4[i5];
    }

    static int equalsConstantTime(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        int i4 = i3;
        long j = BYTE_ARRAY_BASE_OFFSET;
        long j2 = ((long) i) + j;
        long j3 = j + ((long) i2);
        int i5 = i4 & 7;
        long j4 = (long) i4;
        long j5 = (j2 - 8) + j4;
        long j6 = (j3 - 8) + j4;
        long j7 = 0;
        while (j5 >= ((long) i5) + j2) {
            j7 |= UNSAFE.getLong(bArr3, j5) ^ UNSAFE.getLong(bArr4, j6);
            j5 -= 8;
            j6 -= 8;
        }
        switch (i5) {
            case 1:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getByte(bArr3, j2) ^ UNSAFE.getByte(bArr4, j3))) | j7, 0);
            case 2:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getChar(bArr3, j2) ^ UNSAFE.getChar(bArr4, j3))) | j7, 0);
            case 3:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getByte(bArr3, j2) ^ UNSAFE.getByte(bArr4, j3))) | ((long) (UNSAFE.getChar(bArr3, j2 + 1) ^ UNSAFE.getChar(bArr4, 1 + j3))) | j7, 0);
            case 4:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getInt(bArr3, j2) ^ UNSAFE.getInt(bArr4, j3))) | j7, 0);
            case 5:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getByte(bArr3, j2) ^ UNSAFE.getByte(bArr4, j3))) | ((long) (UNSAFE.getInt(bArr3, j2 + 1) ^ UNSAFE.getInt(bArr4, 1 + j3))) | j7, 0);
            case 6:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getChar(bArr3, j2) ^ UNSAFE.getChar(bArr4, j3))) | ((long) (UNSAFE.getInt(bArr3, j2 + 2) ^ UNSAFE.getInt(bArr4, 2 + j3))) | j7, 0);
            case 7:
                return ConstantTimeUtils.equalsConstantTime(((long) (UNSAFE.getByte(bArr3, j2) ^ UNSAFE.getByte(bArr4, j3))) | ((long) (UNSAFE.getChar(bArr3, j2 + 1) ^ UNSAFE.getChar(bArr4, 1 + j3))) | ((long) (UNSAFE.getInt(bArr3, j2 + 3) ^ UNSAFE.getInt(bArr4, 3 + j3))) | j7, 0);
            default:
                return ConstantTimeUtils.equalsConstantTime(j7, 0);
        }
    }

    static boolean isZero(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        int i3 = i;
        int i4 = i2;
        if (i4 <= 0) {
            return true;
        }
        long j = BYTE_ARRAY_BASE_OFFSET + ((long) i3);
        int i5 = i4 & 7;
        long j2 = ((long) i5) + j;
        long j3 = (j - 8) + ((long) i4);
        while (true) {
            boolean z = false;
            if (j3 < j2) {
                if (i5 >= 4) {
                    i5 -= 4;
                    if (UNSAFE.getInt(bArr2, ((long) i5) + j) != 0) {
                        return false;
                    }
                }
                if (i5 >= 2) {
                    if (UNSAFE.getChar(bArr2, j) == 0 && (i5 == 2 || bArr2[i3 + 2] == 0)) {
                        z = true;
                    }
                    return z;
                }
                if (bArr2[i3] == 0) {
                    z = true;
                }
                return z;
            } else if (UNSAFE.getLong(bArr2, j3) != 0) {
                return false;
            } else {
                j3 -= 8;
            }
        }
    }

    static int hashCodeAscii(byte[] bArr, int i, int i2) {
        int i3;
        int hashCodeAsciiSanitize;
        long j = BYTE_ARRAY_BASE_OFFSET + ((long) i);
        int i4 = i2 & 7;
        long j2 = ((long) i4) + j;
        int i5 = HASH_CODE_ASCII_SEED;
        for (long j3 = (j - 8) + ((long) i2); j3 >= j2; j3 -= 8) {
            i5 = hashCodeAsciiCompute(UNSAFE.getLong(bArr, j3), i5);
        }
        switch (i4) {
            case 1:
                i3 = i5 * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getByte(bArr, j));
                break;
            case 2:
                i3 = i5 * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getShort(bArr, j));
                break;
            case 3:
                i3 = ((i5 * 461845907) + hashCodeAsciiSanitize(UNSAFE.getByte(bArr, j))) * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getShort(bArr, j + 1));
                break;
            case 4:
                i3 = i5 * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getInt(bArr, j));
                break;
            case 5:
                i3 = ((i5 * 461845907) + hashCodeAsciiSanitize(UNSAFE.getByte(bArr, j))) * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getInt(bArr, j + 1));
                break;
            case 6:
                i3 = ((i5 * 461845907) + hashCodeAsciiSanitize(UNSAFE.getShort(bArr, j))) * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getInt(bArr, j + 2));
                break;
            case 7:
                i3 = ((((i5 * 461845907) + hashCodeAsciiSanitize(UNSAFE.getByte(bArr, j))) * 461845907) + hashCodeAsciiSanitize(UNSAFE.getShort(bArr, 1 + j))) * 461845907;
                hashCodeAsciiSanitize = hashCodeAsciiSanitize(UNSAFE.getInt(bArr, j + 3));
                break;
            default:
                return i5;
        }
        return i3 + hashCodeAsciiSanitize;
    }

    static int hashCodeAsciiCompute(long j, int i) {
        return (i * 461845907) + (hashCodeAsciiSanitize((int) j) * 461845907) + ((int) ((j & 2242545357458243584L) >>> 32));
    }

    static ClassLoader getClassLoader(final Class<?> cls) {
        if (System.getSecurityManager() == null) {
            return cls.getClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return cls.getClassLoader();
            }
        });
    }

    static ClassLoader getContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return ClassLoader.getSystemClassLoader();
            }
        });
    }

    static int addressSize() {
        return UNSAFE.addressSize();
    }

    static long allocateMemory(long j) {
        return UNSAFE.allocateMemory(j);
    }

    static void freeMemory(long j) {
        UNSAFE.freeMemory(j);
    }

    static long reallocateMemory(long j, long j2) {
        return UNSAFE.reallocateMemory(j, j2);
    }

    static boolean isAndroid() {
        return IS_ANDROID;
    }

    private static boolean isAndroid0() {
        boolean z = false;
        try {
            Class.forName("android.app.Application", false, getSystemClassLoader());
            z = true;
        } catch (Throwable unused) {
        }
        if (z) {
            logger.debug("Platform: Android");
        }
        return z;
    }

    static int javaVersion() {
        return JAVA_VERSION;
    }

    private static int javaVersion0() {
        int i;
        if (isAndroid0()) {
            i = 6;
        } else {
            i = majorVersionFromJavaSpecificationVersion();
        }
        logger.debug("Java version: {}", (Object) Integer.valueOf(i));
        return i;
    }

    static int majorVersionFromJavaSpecificationVersion() {
        return majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
    }

    static int majorVersion(String str) {
        String[] split = str.split("\\.");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            iArr[i] = Integer.parseInt(split[i]);
        }
        if (iArr[0] == 1) {
            return iArr[1];
        }
        return iArr[0];
    }

    private PlatformDependent0() {
    }
}
