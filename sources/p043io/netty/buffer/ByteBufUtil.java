package p043io.netty.buffer;

import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.netty.util.AsciiString;
import p043io.netty.util.ByteProcessor;
import p043io.netty.util.ByteProcessor.IndexOfProcessor;
import p043io.netty.util.CharsetUtil;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.buffer.ByteBufUtil */
public final class ByteBufUtil {
    private static final FastThreadLocal<CharBuffer> CHAR_BUFFERS = new FastThreadLocal<CharBuffer>() {
        /* access modifiers changed from: protected */
        public CharBuffer initialValue() throws Exception {
            return CharBuffer.allocate(1024);
        }
    };
    static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final ByteProcessor FIND_NON_ASCII = new ByteProcessor() {
        public boolean process(byte b) {
            return b >= 0;
        }
    };
    private static final int MAX_BYTES_PER_CHAR_UTF8 = ((int) CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar());
    private static final int MAX_CHAR_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
    /* access modifiers changed from: private */
    public static final int THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
    private static final byte WRITE_UTF_UNKNOWN = 63;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);

    /* renamed from: io.netty.buffer.ByteBufUtil$HexUtil */
    private static final class HexUtil {
        private static final char[] BYTE2CHAR = new char[256];
        private static final String[] BYTE2HEX = new String[256];
        private static final String[] BYTEPADDING = new String[16];
        private static final String[] HEXDUMP_ROWPREFIXES = new String[4096];
        private static final char[] HEXDUMP_TABLE = new char[1024];
        private static final String[] HEXPADDING = new String[16];

        private static int decodeHexNibble(char c) {
            if ('0' <= c && c <= '9') {
                return c - '0';
            }
            char c2 = 'a';
            if ('a' > c || c > 'f') {
                c2 = 'A';
                if ('A' > c || c > 'F') {
                    return -1;
                }
            }
            return (c - c2) + 10;
        }

        private HexUtil() {
        }

        static {
            char[] charArray = "0123456789abcdef".toCharArray();
            int i = 0;
            for (int i2 = 0; i2 < 256; i2++) {
                char[] cArr = HEXDUMP_TABLE;
                int i3 = i2 << 1;
                cArr[i3] = charArray[(i2 >>> 4) & 15];
                cArr[i3 + 1] = charArray[i2 & 15];
            }
            int i4 = 0;
            while (true) {
                String[] strArr = HEXPADDING;
                if (i4 >= strArr.length) {
                    break;
                }
                int length = strArr.length - i4;
                StringBuilder sb = new StringBuilder(length * 3);
                for (int i5 = 0; i5 < length; i5++) {
                    sb.append("   ");
                }
                HEXPADDING[i4] = sb.toString();
                i4++;
            }
            for (int i6 = 0; i6 < HEXDUMP_ROWPREFIXES.length; i6++) {
                StringBuilder sb2 = new StringBuilder(12);
                sb2.append(StringUtil.NEWLINE);
                sb2.append(Long.toHexString((((long) (i6 << 4)) & 4294967295L) | 4294967296L));
                sb2.setCharAt(sb2.length() - 9, '|');
                sb2.append('|');
                HEXDUMP_ROWPREFIXES[i6] = sb2.toString();
            }
            int i7 = 0;
            while (true) {
                String[] strArr2 = BYTE2HEX;
                if (i7 >= strArr2.length) {
                    break;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(' ');
                sb3.append(StringUtil.byteToHexStringPadded(i7));
                strArr2[i7] = sb3.toString();
                i7++;
            }
            int i8 = 0;
            while (true) {
                String[] strArr3 = BYTEPADDING;
                if (i8 >= strArr3.length) {
                    break;
                }
                int length2 = strArr3.length - i8;
                StringBuilder sb4 = new StringBuilder(length2);
                for (int i9 = 0; i9 < length2; i9++) {
                    sb4.append(' ');
                }
                BYTEPADDING[i8] = sb4.toString();
                i8++;
            }
            while (true) {
                char[] cArr2 = BYTE2CHAR;
                if (i < cArr2.length) {
                    if (i <= 31 || i >= 127) {
                        BYTE2CHAR[i] = '.';
                    } else {
                        cArr2[i] = (char) i;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: private */
        public static String hexDump(ByteBuf byteBuf, int i, int i2) {
            if (i2 < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("length: ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (i2 == 0) {
                return "";
            } else {
                int i3 = i + i2;
                char[] cArr = new char[(i2 << 1)];
                int i4 = 0;
                while (i < i3) {
                    System.arraycopy(HEXDUMP_TABLE, byteBuf.getUnsignedByte(i) << 1, cArr, i4, 2);
                    i++;
                    i4 += 2;
                }
                return new String(cArr);
            }
        }

        /* access modifiers changed from: private */
        public static byte decodeHexByte(CharSequence charSequence, int i) {
            int decodeHexNibble = decodeHexNibble(charSequence.charAt(i));
            int decodeHexNibble2 = decodeHexNibble(charSequence.charAt(i + 1));
            if (decodeHexNibble != -1 && decodeHexNibble2 != -1) {
                return (byte) ((decodeHexNibble << 4) + decodeHexNibble2);
            }
            throw new IllegalArgumentException(String.format("invalid hex byte '%s' at index %d of '%s'", new Object[]{charSequence.subSequence(i, i + 2), Integer.valueOf(i), charSequence}));
        }

        /* access modifiers changed from: private */
        public static byte[] decodeHexDump(CharSequence charSequence, int i, int i2) {
            if (i2 < 0 || (i2 & 1) != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("length: ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (i2 == 0) {
                return EmptyArrays.EMPTY_BYTES;
            } else {
                byte[] bArr = new byte[(i2 >>> 1)];
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    bArr[i3 >>> 1] = decodeHexByte(charSequence, i + i3);
                }
                return bArr;
            }
        }

        /* access modifiers changed from: private */
        public static String hexDump(byte[] bArr, int i, int i2) {
            if (i2 < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("length: ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            } else if (i2 == 0) {
                return "";
            } else {
                int i3 = i + i2;
                char[] cArr = new char[(i2 << 1)];
                int i4 = 0;
                while (i < i3) {
                    System.arraycopy(HEXDUMP_TABLE, (bArr[i] & 255) << 1, cArr, i4, 2);
                    i++;
                    i4 += 2;
                }
                return new String(cArr);
            }
        }

        /* access modifiers changed from: private */
        public static String prettyHexDump(ByteBuf byteBuf, int i, int i2) {
            if (i2 == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder(((i2 / 16) + (i2 % 15 == 0 ? 0 : 1) + 4) * 80);
            appendPrettyHexDump(sb, byteBuf, i, i2);
            return sb.toString();
        }

        /* access modifiers changed from: private */
        public static void appendPrettyHexDump(StringBuilder sb, ByteBuf byteBuf, int i, int i2) {
            String str;
            if (MathUtil.isOutOfBounds(i, i2, byteBuf.capacity())) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("expected: 0 <= offset(");
                sb2.append(i);
                sb2.append(") <= offset + length(");
                sb2.append(i2);
                sb2.append(") <= buf.capacity(");
                sb2.append(byteBuf.capacity());
                sb2.append(')');
                throw new IndexOutOfBoundsException(sb2.toString());
            } else if (i2 != 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("         +-------------------------------------------------+");
                sb3.append(StringUtil.NEWLINE);
                sb3.append("         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |");
                sb3.append(StringUtil.NEWLINE);
                String str2 = "+--------+-------------------------------------------------+----------------+";
                sb3.append(str2);
                sb.append(sb3.toString());
                int i3 = i2 >>> 4;
                int i4 = i2 & 15;
                int i5 = 0;
                while (true) {
                    str = " |";
                    if (i5 >= i3) {
                        break;
                    }
                    int i6 = (i5 << 4) + i;
                    appendHexDumpRowPrefix(sb, i5, i6);
                    int i7 = i6 + 16;
                    for (int i8 = i6; i8 < i7; i8++) {
                        sb.append(BYTE2HEX[byteBuf.getUnsignedByte(i8)]);
                    }
                    sb.append(str);
                    while (i6 < i7) {
                        sb.append(BYTE2CHAR[byteBuf.getUnsignedByte(i6)]);
                        i6++;
                    }
                    sb.append('|');
                    i5++;
                }
                if (i4 != 0) {
                    int i9 = (i3 << 4) + i;
                    appendHexDumpRowPrefix(sb, i3, i9);
                    int i10 = i9 + i4;
                    for (int i11 = i9; i11 < i10; i11++) {
                        sb.append(BYTE2HEX[byteBuf.getUnsignedByte(i11)]);
                    }
                    sb.append(HEXPADDING[i4]);
                    sb.append(str);
                    while (i9 < i10) {
                        sb.append(BYTE2CHAR[byteBuf.getUnsignedByte(i9)]);
                        i9++;
                    }
                    sb.append(BYTEPADDING[i4]);
                    sb.append('|');
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(StringUtil.NEWLINE);
                sb4.append(str2);
                sb.append(sb4.toString());
            }
        }

        private static void appendHexDumpRowPrefix(StringBuilder sb, int i, int i2) {
            String[] strArr = HEXDUMP_ROWPREFIXES;
            if (i < strArr.length) {
                sb.append(strArr[i]);
                return;
            }
            sb.append(StringUtil.NEWLINE);
            sb.append(Long.toHexString((((long) i2) & 4294967295L) | 4294967296L));
            sb.setCharAt(sb.length() - 9, '|');
            sb.append('|');
        }
    }

    /* renamed from: io.netty.buffer.ByteBufUtil$ThreadLocalDirectByteBuf */
    static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf {
        private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ThreadLocalDirectByteBuf>() {
            /* access modifiers changed from: protected */
            public ThreadLocalDirectByteBuf newObject(Handle<ThreadLocalDirectByteBuf> handle) {
                return new ThreadLocalDirectByteBuf(handle);
            }
        };
        private final Handle<ThreadLocalDirectByteBuf> handle;

        static ThreadLocalDirectByteBuf newInstance() {
            ThreadLocalDirectByteBuf threadLocalDirectByteBuf = (ThreadLocalDirectByteBuf) RECYCLER.get();
            threadLocalDirectByteBuf.setRefCnt(1);
            return threadLocalDirectByteBuf;
        }

        private ThreadLocalDirectByteBuf(Handle<ThreadLocalDirectByteBuf> handle2) {
            super((ByteBufAllocator) UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle2;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            this.handle.recycle(this);
        }
    }

    /* renamed from: io.netty.buffer.ByteBufUtil$ThreadLocalUnsafeDirectByteBuf */
    static final class ThreadLocalUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
        private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ThreadLocalUnsafeDirectByteBuf>() {
            /* access modifiers changed from: protected */
            public ThreadLocalUnsafeDirectByteBuf newObject(Handle<ThreadLocalUnsafeDirectByteBuf> handle) {
                return new ThreadLocalUnsafeDirectByteBuf(handle);
            }
        };
        private final Handle<ThreadLocalUnsafeDirectByteBuf> handle;

        static ThreadLocalUnsafeDirectByteBuf newInstance() {
            ThreadLocalUnsafeDirectByteBuf threadLocalUnsafeDirectByteBuf = (ThreadLocalUnsafeDirectByteBuf) RECYCLER.get();
            threadLocalUnsafeDirectByteBuf.setRefCnt(1);
            return threadLocalUnsafeDirectByteBuf;
        }

        private ThreadLocalUnsafeDirectByteBuf(Handle<ThreadLocalUnsafeDirectByteBuf> handle2) {
            super((ByteBufAllocator) UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle2;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            this.handle.recycle(this);
        }
    }

    public static int swapMedium(int i) {
        int i2 = ((i >>> 16) & 255) | ((i << 16) & 16711680) | (65280 & i);
        return (8388608 & i2) != 0 ? i2 | ViewCompat.MEASURED_STATE_MASK : i2;
    }

    static {
        ByteBufAllocator byteBufAllocator;
        String str = "unpooled";
        String str2 = "pooled";
        String trim = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? str : str2).toLowerCase(Locale.US).trim();
        String str3 = "-Dio.netty.allocator.type: {}";
        if (str.equals(trim)) {
            byteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
            logger.debug(str3, (Object) trim);
        } else if (str2.equals(trim)) {
            byteBufAllocator = PooledByteBufAllocator.DEFAULT;
            logger.debug(str3, (Object) trim);
        } else {
            byteBufAllocator = PooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: pooled (unknown: {})", (Object) trim);
        }
        DEFAULT_ALLOCATOR = byteBufAllocator;
        logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", (Object) Integer.valueOf(THREAD_LOCAL_BUFFER_SIZE));
        logger.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", (Object) Integer.valueOf(MAX_CHAR_BUFFER_SIZE));
    }

    public static String hexDump(ByteBuf byteBuf) {
        return hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String hexDump(ByteBuf byteBuf, int i, int i2) {
        return HexUtil.hexDump(byteBuf, i, i2);
    }

    public static String hexDump(byte[] bArr) {
        return hexDump(bArr, 0, bArr.length);
    }

    public static String hexDump(byte[] bArr, int i, int i2) {
        return HexUtil.hexDump(bArr, i, i2);
    }

    public static byte decodeHexByte(CharSequence charSequence, int i) {
        return HexUtil.decodeHexByte(charSequence, i);
    }

    public static byte[] decodeHexDump(CharSequence charSequence) {
        return HexUtil.decodeHexDump(charSequence, 0, charSequence.length());
    }

    public static byte[] decodeHexDump(CharSequence charSequence, int i, int i2) {
        return HexUtil.decodeHexDump(charSequence, i, i2);
    }

    public static int hashCode(ByteBuf byteBuf) {
        int i;
        int i2;
        int readableBytes = byteBuf.readableBytes();
        int i3 = readableBytes >>> 2;
        int i4 = readableBytes & 3;
        int readerIndex = byteBuf.readerIndex();
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            i = readerIndex;
            i2 = 1;
            while (i3 > 0) {
                i2 = (i2 * 31) + byteBuf.getInt(i);
                i += 4;
                i3--;
            }
        } else {
            int i5 = readerIndex;
            int i6 = 1;
            while (i3 > 0) {
                i6 = (i2 * 31) + swapInt(byteBuf.getInt(i));
                i5 = i + 4;
                i3--;
            }
        }
        while (i4 > 0) {
            i2 = (i2 * 31) + byteBuf.getByte(i);
            i4--;
            i++;
        }
        if (i2 == 0) {
            return 1;
        }
        return i2;
    }

    public static int indexOf(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int readableBytes = (byteBuf2.readableBytes() - byteBuf.readableBytes()) + 1;
        for (int i = 0; i < readableBytes; i++) {
            if (equals(byteBuf, byteBuf.readerIndex(), byteBuf2, byteBuf2.readerIndex() + i, byteBuf.readableBytes())) {
                return byteBuf2.readerIndex() + i;
            }
        }
        return -1;
    }

    public static boolean equals(ByteBuf byteBuf, int i, ByteBuf byteBuf2, int i2, int i3) {
        if (i < 0 || i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("All indexes and lengths must be non-negative");
        } else if (byteBuf.writerIndex() - i3 < i || byteBuf2.writerIndex() - i3 < i2) {
            return false;
        } else {
            int i4 = i3 >>> 3;
            if (byteBuf.order() == byteBuf2.order()) {
                while (i4 > 0) {
                    if (byteBuf.getLong(i) != byteBuf2.getLong(i2)) {
                        return false;
                    }
                    i += 8;
                    i2 += 8;
                    i4--;
                }
            } else {
                while (i4 > 0) {
                    if (byteBuf.getLong(i) != swapLong(byteBuf2.getLong(i2))) {
                        return false;
                    }
                    i += 8;
                    i2 += 8;
                    i4--;
                }
            }
            for (int i5 = i3 & 7; i5 > 0; i5--) {
                if (byteBuf.getByte(i) != byteBuf2.getByte(i2)) {
                    return false;
                }
                i++;
                i2++;
            }
            return true;
        }
    }

    public static boolean equals(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != byteBuf2.readableBytes()) {
            return false;
        }
        return equals(byteBuf, byteBuf.readerIndex(), byteBuf2, byteBuf2.readerIndex(), readableBytes);
    }

    public static int compare(ByteBuf byteBuf, ByteBuf byteBuf2) {
        long j;
        int readableBytes = byteBuf.readableBytes();
        int readableBytes2 = byteBuf2.readableBytes();
        int min = Math.min(readableBytes, readableBytes2);
        int i = min >>> 2;
        int i2 = min & 3;
        int readerIndex = byteBuf.readerIndex();
        int readerIndex2 = byteBuf2.readerIndex();
        if (i > 0) {
            boolean z = byteBuf.order() == ByteOrder.BIG_ENDIAN;
            int i3 = i << 2;
            if (byteBuf.order() == byteBuf2.order()) {
                if (z) {
                    j = compareUintBigEndian(byteBuf, byteBuf2, readerIndex, readerIndex2, i3);
                } else {
                    j = compareUintLittleEndian(byteBuf, byteBuf2, readerIndex, readerIndex2, i3);
                }
            } else if (z) {
                j = compareUintBigEndianA(byteBuf, byteBuf2, readerIndex, readerIndex2, i3);
            } else {
                j = compareUintBigEndianB(byteBuf, byteBuf2, readerIndex, readerIndex2, i3);
            }
            if (j != 0) {
                return (int) Math.min(2147483647L, Math.max(-2147483648L, j));
            }
            readerIndex += i3;
            readerIndex2 += i3;
        }
        int i4 = i2 + readerIndex;
        while (readerIndex < i4) {
            int unsignedByte = byteBuf.getUnsignedByte(readerIndex) - byteBuf2.getUnsignedByte(readerIndex2);
            if (unsignedByte != 0) {
                return unsignedByte;
            }
            readerIndex++;
            readerIndex2++;
        }
        return readableBytes - readableBytes2;
    }

    private static long compareUintBigEndian(ByteBuf byteBuf, ByteBuf byteBuf2, int i, int i2, int i3) {
        int i4 = i3 + i;
        while (i < i4) {
            long unsignedInt = byteBuf.getUnsignedInt(i) - byteBuf2.getUnsignedInt(i2);
            if (unsignedInt != 0) {
                return unsignedInt;
            }
            i += 4;
            i2 += 4;
        }
        return 0;
    }

    private static long compareUintLittleEndian(ByteBuf byteBuf, ByteBuf byteBuf2, int i, int i2, int i3) {
        int i4 = i3 + i;
        while (i < i4) {
            long unsignedIntLE = byteBuf.getUnsignedIntLE(i) - byteBuf2.getUnsignedIntLE(i2);
            if (unsignedIntLE != 0) {
                return unsignedIntLE;
            }
            i += 4;
            i2 += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianA(ByteBuf byteBuf, ByteBuf byteBuf2, int i, int i2, int i3) {
        int i4 = i3 + i;
        while (i < i4) {
            long unsignedInt = byteBuf.getUnsignedInt(i) - byteBuf2.getUnsignedIntLE(i2);
            if (unsignedInt != 0) {
                return unsignedInt;
            }
            i += 4;
            i2 += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianB(ByteBuf byteBuf, ByteBuf byteBuf2, int i, int i2, int i3) {
        int i4 = i3 + i;
        while (i < i4) {
            long unsignedIntLE = byteBuf.getUnsignedIntLE(i) - byteBuf2.getUnsignedInt(i2);
            if (unsignedIntLE != 0) {
                return unsignedIntLE;
            }
            i += 4;
            i2 += 4;
        }
        return 0;
    }

    public static int indexOf(ByteBuf byteBuf, int i, int i2, byte b) {
        if (i <= i2) {
            return firstIndexOf(byteBuf, i, i2, b);
        }
        return lastIndexOf(byteBuf, i, i2, b);
    }

    public static short swapShort(short s) {
        return Short.reverseBytes(s);
    }

    public static int swapInt(int i) {
        return Integer.reverseBytes(i);
    }

    public static long swapLong(long j) {
        return Long.reverseBytes(j);
    }

    public static ByteBuf readBytes(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int i) {
        ByteBuf buffer = byteBufAllocator.buffer(i);
        try {
            byteBuf.readBytes(buffer);
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    private static int firstIndexOf(ByteBuf byteBuf, int i, int i2, byte b) {
        int max = Math.max(i, 0);
        if (max >= i2 || byteBuf.capacity() == 0) {
            return -1;
        }
        return byteBuf.forEachByte(max, i2 - max, new IndexOfProcessor(b));
    }

    private static int lastIndexOf(ByteBuf byteBuf, int i, int i2, byte b) {
        int min = Math.min(i, byteBuf.capacity());
        if (min < 0 || byteBuf.capacity() == 0) {
            return -1;
        }
        return byteBuf.forEachByteDesc(i2, min - i2, new IndexOfProcessor(b));
    }

    public static ByteBuf writeUtf8(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf buffer = byteBufAllocator.buffer(utf8MaxBytes(charSequence));
        writeUtf8(buffer, charSequence);
        return buffer;
    }

    public static int writeUtf8(ByteBuf byteBuf, CharSequence charSequence) {
        int length = charSequence.length();
        byteBuf.ensureWritable(utf8MaxBytes(charSequence));
        while (!(byteBuf instanceof AbstractByteBuf)) {
            if (byteBuf instanceof WrappedByteBuf) {
                byteBuf = byteBuf.unwrap();
            } else {
                byte[] bytes = charSequence.toString().getBytes(CharsetUtil.UTF_8);
                byteBuf.writeBytes(bytes);
                return bytes.length;
            }
        }
        AbstractByteBuf abstractByteBuf = (AbstractByteBuf) byteBuf;
        int writeUtf8 = writeUtf8(abstractByteBuf, abstractByteBuf.writerIndex, charSequence, length);
        abstractByteBuf.writerIndex += writeUtf8;
        return writeUtf8;
    }

    static int writeUtf8(AbstractByteBuf abstractByteBuf, int i, CharSequence charSequence, int i2) {
        int i3;
        int i4 = 0;
        int i5 = i;
        while (i4 < i2) {
            char charAt = charSequence.charAt(i4);
            if (charAt < 128) {
                int i6 = i5 + 1;
                abstractByteBuf._setByte(i5, (byte) charAt);
                i5 = i6;
            } else if (charAt < 2048) {
                int i7 = i5 + 1;
                abstractByteBuf._setByte(i5, (byte) ((charAt >> 6) | 192));
                i5 = i7 + 1;
                abstractByteBuf._setByte(i7, (byte) ((charAt & '?') | 128));
            } else if (!StringUtil.isSurrogate(charAt)) {
                int i8 = i5 + 1;
                abstractByteBuf._setByte(i5, (byte) ((charAt >> 12) | 224));
                int i9 = i8 + 1;
                abstractByteBuf._setByte(i8, (byte) ((63 & (charAt >> 6)) | 128));
                int i10 = i9 + 1;
                abstractByteBuf._setByte(i9, (byte) ((charAt & '?') | 128));
                i5 = i10;
            } else if (!Character.isHighSurrogate(charAt)) {
                int i11 = i5 + 1;
                abstractByteBuf._setByte(i5, 63);
                i5 = i11;
            } else {
                i4++;
                try {
                    char charAt2 = charSequence.charAt(i4);
                    if (!Character.isLowSurrogate(charAt2)) {
                        int i12 = i5 + 1;
                        abstractByteBuf._setByte(i5, 63);
                        i5 = i12 + 1;
                        if (Character.isHighSurrogate(charAt2)) {
                            charAt2 = '?';
                        }
                        abstractByteBuf._setByte(i12, charAt2);
                    } else {
                        int codePoint = Character.toCodePoint(charAt, charAt2);
                        int i13 = i5 + 1;
                        abstractByteBuf._setByte(i5, (byte) ((codePoint >> 18) | PsExtractor.VIDEO_STREAM_MASK));
                        int i14 = i13 + 1;
                        abstractByteBuf._setByte(i13, (byte) (((codePoint >> 12) & 63) | 128));
                        int i15 = i14 + 1;
                        abstractByteBuf._setByte(i14, (byte) (((codePoint >> 6) & 63) | 128));
                        i5 = i15 + 1;
                        abstractByteBuf._setByte(i15, (byte) ((codePoint & 63) | 128));
                    }
                } catch (IndexOutOfBoundsException unused) {
                    i3 = i5 + 1;
                    abstractByteBuf._setByte(i5, 63);
                }
            }
            i4++;
        }
        i3 = i5;
        return i3 - i;
    }

    public static int utf8MaxBytes(CharSequence charSequence) {
        return charSequence.length() * MAX_BYTES_PER_CHAR_UTF8;
    }

    public static ByteBuf writeAscii(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf buffer = byteBufAllocator.buffer(charSequence.length());
        writeAscii(buffer, charSequence);
        return buffer;
    }

    public static int writeAscii(ByteBuf byteBuf, CharSequence charSequence) {
        int length = charSequence.length();
        byteBuf.ensureWritable(length);
        if (charSequence instanceof AsciiString) {
            AsciiString asciiString = (AsciiString) charSequence;
            byteBuf.writeBytes(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
            return length;
        }
        while (!(byteBuf instanceof AbstractByteBuf)) {
            if (byteBuf instanceof WrappedByteBuf) {
                byteBuf = byteBuf.unwrap();
            } else {
                byteBuf.writeBytes(charSequence.toString().getBytes(CharsetUtil.US_ASCII));
            }
        }
        AbstractByteBuf abstractByteBuf = (AbstractByteBuf) byteBuf;
        int writeAscii = writeAscii(abstractByteBuf, abstractByteBuf.writerIndex, charSequence, length);
        abstractByteBuf.writerIndex += writeAscii;
        return writeAscii;
    }

    static int writeAscii(AbstractByteBuf abstractByteBuf, int i, CharSequence charSequence, int i2) {
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i + 1;
            abstractByteBuf._setByte(i, (byte) charSequence.charAt(i3));
            i3++;
            i = i4;
        }
        return i2;
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset) {
        return encodeString0(byteBufAllocator, false, charBuffer, charset, 0);
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset, int i) {
        return encodeString0(byteBufAllocator, false, charBuffer, charset, i);
    }

    static ByteBuf encodeString0(ByteBufAllocator byteBufAllocator, boolean z, CharBuffer charBuffer, Charset charset, int i) {
        ByteBuf byteBuf;
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        double remaining = (double) charBuffer.remaining();
        double maxBytesPerChar = (double) encoder.maxBytesPerChar();
        Double.isNaN(remaining);
        Double.isNaN(maxBytesPerChar);
        int i2 = ((int) (remaining * maxBytesPerChar)) + i;
        if (z) {
            byteBuf = byteBufAllocator.heapBuffer(i2);
        } else {
            byteBuf = byteBufAllocator.buffer(i2);
        }
        try {
            ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), i2);
            int position = internalNioBuffer.position();
            CoderResult encode = encoder.encode(charBuffer, internalNioBuffer, true);
            if (!encode.isUnderflow()) {
                encode.throwException();
            }
            CoderResult flush = encoder.flush(internalNioBuffer);
            if (!flush.isUnderflow()) {
                flush.throwException();
            }
            byteBuf.writerIndex((byteBuf.writerIndex() + internalNioBuffer.position()) - position);
            return byteBuf;
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        } catch (Throwable th) {
            byteBuf.release();
            throw th;
        }
    }

    static String decodeString(ByteBuf byteBuf, int i, int i2, Charset charset) {
        if (i2 == 0) {
            return "";
        }
        CharsetDecoder decoder = CharsetUtil.decoder(charset);
        double d = (double) i2;
        double maxCharsPerByte = (double) decoder.maxCharsPerByte();
        Double.isNaN(d);
        Double.isNaN(maxCharsPerByte);
        int i3 = (int) (d * maxCharsPerByte);
        CharBuffer charBuffer = (CharBuffer) CHAR_BUFFERS.get();
        if (charBuffer.length() < i3) {
            charBuffer = CharBuffer.allocate(i3);
            if (i3 <= MAX_CHAR_BUFFER_SIZE) {
                CHAR_BUFFERS.set(charBuffer);
            }
        } else {
            charBuffer.clear();
        }
        if (byteBuf.nioBufferCount() == 1) {
            decodeString(decoder, byteBuf.internalNioBuffer(i, i2), charBuffer);
        } else {
            ByteBuf heapBuffer = byteBuf.alloc().heapBuffer(i2);
            try {
                heapBuffer.writeBytes(byteBuf, i, i2);
                decodeString(decoder, heapBuffer.internalNioBuffer(heapBuffer.readerIndex(), i2), charBuffer);
            } finally {
                heapBuffer.release();
            }
        }
        return charBuffer.flip().toString();
    }

    private static void decodeString(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) {
        try {
            CoderResult decode = charsetDecoder.decode(byteBuffer, charBuffer, true);
            if (!decode.isUnderflow()) {
                decode.throwException();
            }
            CoderResult flush = charsetDecoder.flush(charBuffer);
            if (!flush.isUnderflow()) {
                flush.throwException();
            }
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ByteBuf threadLocalDirectBuffer() {
        if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return ThreadLocalUnsafeDirectByteBuf.newInstance();
        }
        return ThreadLocalDirectByteBuf.newInstance();
    }

    public static byte[] getBytes(ByteBuf byteBuf) {
        return getBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static byte[] getBytes(ByteBuf byteBuf, int i, int i2) {
        return getBytes(byteBuf, i, i2, true);
    }

    public static byte[] getBytes(ByteBuf byteBuf, int i, int i2, boolean z) {
        if (MathUtil.isOutOfBounds(i, i2, byteBuf.capacity())) {
            StringBuilder sb = new StringBuilder();
            sb.append("expected: 0 <= start(");
            sb.append(i);
            sb.append(") <= start + length(");
            sb.append(i2);
            sb.append(") <= buf.capacity(");
            sb.append(byteBuf.capacity());
            sb.append(')');
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (!byteBuf.hasArray()) {
            byte[] bArr = new byte[i2];
            byteBuf.getBytes(i, bArr);
            return bArr;
        } else if (!z && i == 0 && i2 == byteBuf.capacity()) {
            return byteBuf.array();
        } else {
            int arrayOffset = byteBuf.arrayOffset() + i;
            return Arrays.copyOfRange(byteBuf.array(), arrayOffset, i2 + arrayOffset);
        }
    }

    public static void copy(AsciiString asciiString, int i, ByteBuf byteBuf, int i2, int i3) {
        if (!MathUtil.isOutOfBounds(i, i3, asciiString.length())) {
            ((ByteBuf) ObjectUtil.checkNotNull(byteBuf, "dst")).setBytes(i2, asciiString.array(), i + asciiString.arrayOffset(), i3);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= srcIdx(");
        sb.append(i);
        sb.append(") <= srcIdx + length(");
        sb.append(i3);
        sb.append(") <= srcLen(");
        sb.append(asciiString.length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public static void copy(AsciiString asciiString, int i, ByteBuf byteBuf, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, asciiString.length())) {
            ((ByteBuf) ObjectUtil.checkNotNull(byteBuf, "dst")).writeBytes(asciiString.array(), i + asciiString.arrayOffset(), i2);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= srcIdx(");
        sb.append(i);
        sb.append(") <= srcIdx + length(");
        sb.append(i2);
        sb.append(") <= srcLen(");
        sb.append(asciiString.length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public static String prettyHexDump(ByteBuf byteBuf) {
        return prettyHexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String prettyHexDump(ByteBuf byteBuf, int i, int i2) {
        return HexUtil.prettyHexDump(byteBuf, i, i2);
    }

    public static void appendPrettyHexDump(StringBuilder sb, ByteBuf byteBuf) {
        appendPrettyHexDump(sb, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static void appendPrettyHexDump(StringBuilder sb, ByteBuf byteBuf, int i, int i2) {
        HexUtil.appendPrettyHexDump(sb, byteBuf, i, i2);
    }

    public static boolean isText(ByteBuf byteBuf, Charset charset) {
        return isText(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), charset);
    }

    public static boolean isText(ByteBuf byteBuf, int i, int i2, Charset charset) {
        ByteBuf heapBuffer;
        ObjectUtil.checkNotNull(byteBuf, "buf");
        ObjectUtil.checkNotNull(charset, HttpRequest.PARAM_CHARSET);
        int readerIndex = byteBuf.readerIndex() + byteBuf.readableBytes();
        if (i < 0 || i2 < 0 || i > readerIndex - i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("index: ");
            sb.append(i);
            sb.append(" length: ");
            sb.append(i2);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (charset.equals(CharsetUtil.UTF_8)) {
            return isUtf8(byteBuf, i, i2);
        } else {
            if (charset.equals(CharsetUtil.US_ASCII)) {
                return isAscii(byteBuf, i, i2);
            }
            CharsetDecoder decoder = CharsetUtil.decoder(charset, CodingErrorAction.REPORT, CodingErrorAction.REPORT);
            try {
                if (byteBuf.nioBufferCount() == 1) {
                    decoder.decode(byteBuf.internalNioBuffer(i, i2));
                } else {
                    heapBuffer = byteBuf.alloc().heapBuffer(i2);
                    heapBuffer.writeBytes(byteBuf, i, i2);
                    decoder.decode(heapBuffer.internalNioBuffer(heapBuffer.readerIndex(), i2));
                    heapBuffer.release();
                }
                return true;
            } catch (CharacterCodingException unused) {
                return false;
            } catch (Throwable th) {
                heapBuffer.release();
                throw th;
            }
        }
    }

    private static boolean isAscii(ByteBuf byteBuf, int i, int i2) {
        return byteBuf.forEachByte(i, i2, FIND_NON_ASCII) == -1;
    }

    private static boolean isUtf8(ByteBuf byteBuf, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            int i4 = i + 1;
            byte b = byteBuf.getByte(i);
            if ((b & 128) == 0) {
                i = i4;
            } else if ((b & 224) == 192) {
                if (i4 >= i3) {
                    return false;
                }
                int i5 = i4 + 1;
                if ((byteBuf.getByte(i4) & 192) != 128 || (b & 255) < 194) {
                    return false;
                }
                i = i5;
            } else if ((b & 240) == 224) {
                if (i4 > i3 - 2) {
                    return false;
                }
                int i6 = i4 + 1;
                byte b2 = byteBuf.getByte(i4);
                int i7 = i6 + 1;
                byte b3 = byteBuf.getByte(i6);
                if ((b2 & 192) != 128 || (b3 & 192) != 128) {
                    return false;
                }
                byte b4 = b & Ascii.f1875SI;
                if (b4 == 0 && (b2 & 255) < 160) {
                    return false;
                }
                if (b4 == 13 && (b2 & 255) > 159) {
                    return false;
                }
                i = i7;
            } else if ((b & 248) != 240 || i4 > i3 - 3) {
                return false;
            } else {
                int i8 = i4 + 1;
                byte b5 = byteBuf.getByte(i4);
                int i9 = i8 + 1;
                byte b6 = byteBuf.getByte(i8);
                int i10 = i9 + 1;
                byte b7 = byteBuf.getByte(i9);
                if ((b5 & 192) == 128 && (b6 & 192) == 128 && (b7 & 192) == 128) {
                    byte b8 = b & 255;
                    if (b8 <= 244 && ((b8 != 240 || (b5 & 255) >= 144) && (b8 != 244 || (b5 & 255) <= 143))) {
                        i = i10;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private ByteBufUtil() {
    }
}
