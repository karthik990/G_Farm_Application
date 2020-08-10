package p043io.netty.buffer;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import kotlin.UShort;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnsafeByteBufUtil */
final class UnsafeByteBufUtil {
    private static final boolean UNALIGNED = PlatformDependent.isUnaligned();
    private static final byte ZERO = 0;

    static byte getByte(long j) {
        return PlatformDependent.getByte(j);
    }

    static short getShort(long j) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(j);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            return s;
        }
        return (short) ((PlatformDependent.getByte(j + 1) & 255) | (PlatformDependent.getByte(j) << 8));
    }

    static short getShortLE(long j) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(j);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            return s;
        }
        return (short) ((PlatformDependent.getByte(j + 1) << 8) | (PlatformDependent.getByte(j) & 255));
    }

    static int getUnsignedMedium(long j) {
        int i;
        byte b;
        short s;
        if (UNALIGNED) {
            i = (PlatformDependent.getByte(j) & 255) << Ascii.DLE;
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = PlatformDependent.getShort(j + 1);
            } else {
                s = Short.reverseBytes(PlatformDependent.getShort(j + 1));
            }
            b = s & 65535;
        } else {
            i = ((PlatformDependent.getByte(j) & 255) << Ascii.DLE) | ((PlatformDependent.getByte(1 + j) & 255) << 8);
            b = PlatformDependent.getByte(j + 2) & 255;
        }
        return b | i;
    }

    static int getUnsignedMediumLE(long j) {
        byte b;
        int i;
        short s;
        if (UNALIGNED) {
            b = PlatformDependent.getByte(j) & 255;
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(PlatformDependent.getShort(j + 1));
            } else {
                s = PlatformDependent.getShort(j + 1);
            }
            i = (s & UShort.MAX_VALUE) << 8;
        } else {
            b = (PlatformDependent.getByte(j) & 255) | ((PlatformDependent.getByte(1 + j) & 255) << 8);
            i = (PlatformDependent.getByte(j + 2) & 255) << Ascii.DLE;
        }
        return i | b;
    }

    static int getInt(long j) {
        if (UNALIGNED) {
            int i = PlatformDependent.getInt(j);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            return i;
        }
        return (PlatformDependent.getByte(j + 3) & 255) | (PlatformDependent.getByte(j) << Ascii.CAN) | ((PlatformDependent.getByte(1 + j) & 255) << Ascii.DLE) | ((PlatformDependent.getByte(2 + j) & 255) << 8);
    }

    static int getIntLE(long j) {
        if (UNALIGNED) {
            int i = PlatformDependent.getInt(j);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            return i;
        }
        return (PlatformDependent.getByte(j + 3) << Ascii.CAN) | (PlatformDependent.getByte(j) & 255) | ((PlatformDependent.getByte(1 + j) & 255) << 8) | ((PlatformDependent.getByte(2 + j) & 255) << Ascii.DLE);
    }

    static long getLong(long j) {
        if (UNALIGNED) {
            long j2 = PlatformDependent.getLong(j);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            return j2;
        }
        return (((long) PlatformDependent.getByte(j + 7)) & 255) | (((long) PlatformDependent.getByte(j)) << 56) | ((((long) PlatformDependent.getByte(1 + j)) & 255) << 48) | ((((long) PlatformDependent.getByte(2 + j)) & 255) << 40) | ((((long) PlatformDependent.getByte(3 + j)) & 255) << 32) | ((((long) PlatformDependent.getByte(4 + j)) & 255) << 24) | ((((long) PlatformDependent.getByte(5 + j)) & 255) << 16) | ((((long) PlatformDependent.getByte(6 + j)) & 255) << 8);
    }

    static long getLongLE(long j) {
        if (UNALIGNED) {
            long j2 = PlatformDependent.getLong(j);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            return j2;
        }
        return (((long) PlatformDependent.getByte(j + 7)) << 56) | (((long) PlatformDependent.getByte(j)) & 255) | ((((long) PlatformDependent.getByte(1 + j)) & 255) << 8) | ((((long) PlatformDependent.getByte(2 + j)) & 255) << 16) | ((((long) PlatformDependent.getByte(3 + j)) & 255) << 24) | ((((long) PlatformDependent.getByte(4 + j)) & 255) << 32) | ((((long) PlatformDependent.getByte(5 + j)) & 255) << 40) | ((255 & ((long) PlatformDependent.getByte(6 + j))) << 48);
    }

    static void setByte(long j, int i) {
        PlatformDependent.putByte(j, (byte) i);
    }

    static void setShort(long j, int i) {
        if (UNALIGNED) {
            short s = (short) i;
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            PlatformDependent.putShort(j, s);
            return;
        }
        PlatformDependent.putByte(j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 1, (byte) i);
    }

    static void setShortLE(long j, int i) {
        if (UNALIGNED) {
            PlatformDependent.putShort(j, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) i) : (short) i);
            return;
        }
        PlatformDependent.putByte(j, (byte) i);
        PlatformDependent.putByte(j + 1, (byte) (i >>> 8));
    }

    static void setMedium(long j, int i) {
        PlatformDependent.putByte(j, (byte) (i >>> 16));
        if (UNALIGNED) {
            long j2 = j + 1;
            short s = (short) i;
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            PlatformDependent.putShort(j2, s);
            return;
        }
        PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 2, (byte) i);
    }

    static void setMediumLE(long j, int i) {
        PlatformDependent.putByte(j, (byte) i);
        if (UNALIGNED) {
            long j2 = j + 1;
            short s = (short) (i >>> 8);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            PlatformDependent.putShort(j2, s);
            return;
        }
        PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 2, (byte) (i >>> 16));
    }

    static void setInt(long j, int i) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            PlatformDependent.putInt(j, i);
            return;
        }
        PlatformDependent.putByte(j, (byte) (i >>> 24));
        PlatformDependent.putByte(1 + j, (byte) (i >>> 16));
        PlatformDependent.putByte(2 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 3, (byte) i);
    }

    static void setIntLE(long j, int i) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            PlatformDependent.putInt(j, i);
            return;
        }
        PlatformDependent.putByte(j, (byte) i);
        PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(2 + j, (byte) (i >>> 16));
        PlatformDependent.putByte(j + 3, (byte) (i >>> 24));
    }

    static void setLong(long j, long j2) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            PlatformDependent.putLong(j, j2);
            return;
        }
        PlatformDependent.putByte(j, (byte) ((int) (j2 >>> 56)));
        PlatformDependent.putByte(1 + j, (byte) ((int) (j2 >>> 48)));
        PlatformDependent.putByte(2 + j, (byte) ((int) (j2 >>> 40)));
        PlatformDependent.putByte(3 + j, (byte) ((int) (j2 >>> 32)));
        PlatformDependent.putByte(4 + j, (byte) ((int) (j2 >>> 24)));
        PlatformDependent.putByte(5 + j, (byte) ((int) (j2 >>> 16)));
        PlatformDependent.putByte(6 + j, (byte) ((int) (j2 >>> 8)));
        PlatformDependent.putByte(j + 7, (byte) ((int) j2));
    }

    static void setLongLE(long j, long j2) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            PlatformDependent.putLong(j, j2);
            return;
        }
        PlatformDependent.putByte(j, (byte) ((int) j2));
        PlatformDependent.putByte(1 + j, (byte) ((int) (j2 >>> 8)));
        PlatformDependent.putByte(2 + j, (byte) ((int) (j2 >>> 16)));
        PlatformDependent.putByte(3 + j, (byte) ((int) (j2 >>> 24)));
        PlatformDependent.putByte(4 + j, (byte) ((int) (j2 >>> 32)));
        PlatformDependent.putByte(5 + j, (byte) ((int) (j2 >>> 40)));
        PlatformDependent.putByte(6 + j, (byte) ((int) (j2 >>> 48)));
        PlatformDependent.putByte(j + 7, (byte) ((int) (j2 >>> 56)));
    }

    static byte getByte(byte[] bArr, int i) {
        return PlatformDependent.getByte(bArr, i);
    }

    static short getShort(byte[] bArr, int i) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(bArr, i);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            return s;
        }
        return (short) ((PlatformDependent.getByte(bArr, i + 1) & 255) | (PlatformDependent.getByte(bArr, i) << 8));
    }

    static short getShortLE(byte[] bArr, int i) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(bArr, i);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            return s;
        }
        return (short) ((PlatformDependent.getByte(bArr, i + 1) << 8) | (PlatformDependent.getByte(bArr, i) & 255));
    }

    static int getUnsignedMedium(byte[] bArr, int i) {
        int i2;
        byte b;
        short s;
        if (UNALIGNED) {
            i2 = (PlatformDependent.getByte(bArr, i) & 255) << Ascii.DLE;
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = PlatformDependent.getShort(bArr, i + 1);
            } else {
                s = Short.reverseBytes(PlatformDependent.getShort(bArr, i + 1));
            }
            b = s & 65535;
        } else {
            i2 = ((PlatformDependent.getByte(bArr, i) & 255) << Ascii.DLE) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8);
            b = PlatformDependent.getByte(bArr, i + 2) & 255;
        }
        return b | i2;
    }

    static int getUnsignedMediumLE(byte[] bArr, int i) {
        byte b;
        int i2;
        short s;
        if (UNALIGNED) {
            b = PlatformDependent.getByte(bArr, i) & 255;
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(PlatformDependent.getShort(bArr, i + 1));
            } else {
                s = PlatformDependent.getShort(bArr, i + 1);
            }
            i2 = (s & UShort.MAX_VALUE) << 8;
        } else {
            b = (PlatformDependent.getByte(bArr, i) & 255) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8);
            i2 = (PlatformDependent.getByte(bArr, i + 2) & 255) << Ascii.DLE;
        }
        return i2 | b;
    }

    static int getInt(byte[] bArr, int i) {
        if (UNALIGNED) {
            int i2 = PlatformDependent.getInt(bArr, i);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            return i2;
        }
        return (PlatformDependent.getByte(bArr, i + 3) & 255) | (PlatformDependent.getByte(bArr, i) << Ascii.CAN) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << Ascii.DLE) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << 8);
    }

    static int getIntLE(byte[] bArr, int i) {
        if (UNALIGNED) {
            int i2 = PlatformDependent.getInt(bArr, i);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            return i2;
        }
        return (PlatformDependent.getByte(bArr, i + 3) << Ascii.CAN) | (PlatformDependent.getByte(bArr, i) & 255) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << Ascii.DLE);
    }

    static long getLong(byte[] bArr, int i) {
        if (UNALIGNED) {
            long j = PlatformDependent.getLong(bArr, i);
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            return j;
        }
        return (((long) PlatformDependent.getByte(bArr, i + 7)) & 255) | (((long) PlatformDependent.getByte(bArr, i)) << 56) | ((((long) PlatformDependent.getByte(bArr, i + 1)) & 255) << 48) | ((((long) PlatformDependent.getByte(bArr, i + 2)) & 255) << 40) | ((((long) PlatformDependent.getByte(bArr, i + 3)) & 255) << 32) | ((((long) PlatformDependent.getByte(bArr, i + 4)) & 255) << 24) | ((((long) PlatformDependent.getByte(bArr, i + 5)) & 255) << 16) | ((((long) PlatformDependent.getByte(bArr, i + 6)) & 255) << 8);
    }

    static long getLongLE(byte[] bArr, int i) {
        if (UNALIGNED) {
            long j = PlatformDependent.getLong(bArr, i);
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            return j;
        }
        return (((long) PlatformDependent.getByte(bArr, i + 7)) << 56) | (((long) PlatformDependent.getByte(bArr, i)) & 255) | ((((long) PlatformDependent.getByte(bArr, i + 1)) & 255) << 8) | ((((long) PlatformDependent.getByte(bArr, i + 2)) & 255) << 16) | ((((long) PlatformDependent.getByte(bArr, i + 3)) & 255) << 24) | ((((long) PlatformDependent.getByte(bArr, i + 4)) & 255) << 32) | ((((long) PlatformDependent.getByte(bArr, i + 5)) & 255) << 40) | ((255 & ((long) PlatformDependent.getByte(bArr, i + 6))) << 48);
    }

    static void setByte(byte[] bArr, int i, int i2) {
        PlatformDependent.putByte(bArr, i, (byte) i2);
    }

    static void setShort(byte[] bArr, int i, int i2) {
        if (UNALIGNED) {
            short s = (short) i2;
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            PlatformDependent.putShort(bArr, i, s);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 1, (byte) i2);
    }

    static void setShortLE(byte[] bArr, int i, int i2) {
        if (UNALIGNED) {
            PlatformDependent.putShort(bArr, i, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) i2) : (short) i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) i2);
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
    }

    static void setMedium(byte[] bArr, int i, int i2) {
        PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 16));
        if (UNALIGNED) {
            int i3 = i + 1;
            short s = (short) i2;
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                s = Short.reverseBytes(s);
            }
            PlatformDependent.putShort(bArr, i3, s);
            return;
        }
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 2, (byte) i2);
    }

    static void setMediumLE(byte[] bArr, int i, int i2) {
        PlatformDependent.putByte(bArr, i, (byte) i2);
        if (UNALIGNED) {
            PlatformDependent.putShort(bArr, i + 1, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) (i2 >>> 8)) : (short) (i2 >>> 8));
            return;
        }
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 16));
    }

    static void setInt(byte[] bArr, int i, int i2) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            PlatformDependent.putInt(bArr, i, i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 24));
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 16));
        PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 3, (byte) i2);
    }

    static void setIntLE(byte[] bArr, int i, int i2) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            PlatformDependent.putInt(bArr, i, i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) i2);
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 16));
        PlatformDependent.putByte(bArr, i + 3, (byte) (i2 >>> 24));
    }

    static void setLong(byte[] bArr, int i, long j) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            PlatformDependent.putLong(bArr, i, j);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) ((int) (j >>> 56)));
        PlatformDependent.putByte(bArr, i + 1, (byte) ((int) (j >>> 48)));
        PlatformDependent.putByte(bArr, i + 2, (byte) ((int) (j >>> 40)));
        PlatformDependent.putByte(bArr, i + 3, (byte) ((int) (j >>> 32)));
        PlatformDependent.putByte(bArr, i + 4, (byte) ((int) (j >>> 24)));
        PlatformDependent.putByte(bArr, i + 5, (byte) ((int) (j >>> 16)));
        PlatformDependent.putByte(bArr, i + 6, (byte) ((int) (j >>> 8)));
        PlatformDependent.putByte(bArr, i + 7, (byte) ((int) j));
    }

    static void setLongLE(byte[] bArr, int i, long j) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            PlatformDependent.putLong(bArr, i, j);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) ((int) j));
        PlatformDependent.putByte(bArr, i + 1, (byte) ((int) (j >>> 8)));
        PlatformDependent.putByte(bArr, i + 2, (byte) ((int) (j >>> 16)));
        PlatformDependent.putByte(bArr, i + 3, (byte) ((int) (j >>> 24)));
        PlatformDependent.putByte(bArr, i + 4, (byte) ((int) (j >>> 32)));
        PlatformDependent.putByte(bArr, i + 5, (byte) ((int) (j >>> 40)));
        PlatformDependent.putByte(bArr, i + 6, (byte) ((int) (j >>> 48)));
        PlatformDependent.putByte(bArr, i + 7, (byte) ((int) (j >>> 56)));
    }

    static void setZero(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            PlatformDependent.setMemory(bArr, i, (long) i2, 0);
        }
    }

    static ByteBuf copy(AbstractByteBuf abstractByteBuf, long j, int i, int i2) {
        abstractByteBuf.checkIndex(i, i2);
        ByteBuf directBuffer = abstractByteBuf.alloc().directBuffer(i2, abstractByteBuf.maxCapacity());
        if (i2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(j, directBuffer.memoryAddress(), (long) i2);
                directBuffer.setIndex(0, i2);
            } else {
                directBuffer.writeBytes((ByteBuf) abstractByteBuf, i, i2);
            }
        }
        return directBuffer;
    }

    static int setBytes(AbstractByteBuf abstractByteBuf, long j, int i, InputStream inputStream, int i2) throws IOException {
        abstractByteBuf.checkIndex(i, i2);
        ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(i2);
        try {
            byte[] array = heapBuffer.array();
            int arrayOffset = heapBuffer.arrayOffset();
            int read = inputStream.read(array, arrayOffset, i2);
            if (read > 0) {
                PlatformDependent.copyMemory(array, arrayOffset, j, (long) read);
            }
            return read;
        } finally {
            heapBuffer.release();
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuf byteBuf, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "dst");
        if (MathUtil.isOutOfBounds(i2, i3, byteBuf.capacity())) {
            StringBuilder sb = new StringBuilder();
            sb.append("dstIndex: ");
            sb.append(i2);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(j, byteBuf.memoryAddress() + ((long) i2), (long) i3);
        } else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(j, byteBuf.array(), byteBuf.arrayOffset() + i2, (long) i3);
        } else {
            byteBuf.setBytes(i2, (ByteBuf) abstractByteBuf, i, i3);
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long j, int i, byte[] bArr, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(bArr, "dst");
        if (MathUtil.isOutOfBounds(i2, i3, bArr.length)) {
            StringBuilder sb = new StringBuilder();
            sb.append("dstIndex: ");
            sb.append(i2);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i3 != 0) {
            PlatformDependent.copyMemory(j, bArr, i2, (long) i3);
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuffer byteBuffer) {
        abstractByteBuf.checkIndex(i, byteBuffer.remaining());
        if (byteBuffer.remaining() != 0) {
            if (byteBuffer.isDirect()) {
                if (!byteBuffer.isReadOnly()) {
                    PlatformDependent.copyMemory(j, PlatformDependent.directBufferAddress(byteBuffer) + ((long) byteBuffer.position()), (long) byteBuffer.remaining());
                    byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
                } else {
                    throw new ReadOnlyBufferException();
                }
            } else if (byteBuffer.hasArray()) {
                PlatformDependent.copyMemory(j, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), (long) byteBuffer.remaining());
                byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
            } else {
                byteBuffer.put(abstractByteBuf.nioBuffer());
            }
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuf byteBuf, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "src");
        if (MathUtil.isOutOfBounds(i2, i3, byteBuf.capacity())) {
            StringBuilder sb = new StringBuilder();
            sb.append("srcIndex: ");
            sb.append(i2);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i3 == 0) {
        } else {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(byteBuf.memoryAddress() + ((long) i2), j, (long) i3);
            } else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(byteBuf.array(), byteBuf.arrayOffset() + i2, j, (long) i3);
            } else {
                byteBuf.getBytes(i2, (ByteBuf) abstractByteBuf, i, i3);
            }
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long j, int i, byte[] bArr, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        if (i3 != 0) {
            PlatformDependent.copyMemory(bArr, i2, j, (long) i3);
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuffer byteBuffer) {
        abstractByteBuf.checkIndex(i, byteBuffer.remaining());
        int remaining = byteBuffer.remaining();
        if (remaining != 0) {
            if (byteBuffer.isDirect()) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(byteBuffer) + ((long) byteBuffer.position()), j, (long) byteBuffer.remaining());
                byteBuffer.position(byteBuffer.position() + remaining);
            } else if (byteBuffer.hasArray()) {
                PlatformDependent.copyMemory(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), j, (long) remaining);
                byteBuffer.position(byteBuffer.position() + remaining);
            } else {
                ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(remaining);
                try {
                    byte[] array = heapBuffer.array();
                    byteBuffer.get(array, heapBuffer.arrayOffset(), remaining);
                    PlatformDependent.copyMemory(array, heapBuffer.arrayOffset(), j, (long) remaining);
                } finally {
                    heapBuffer.release();
                }
            }
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long j, int i, OutputStream outputStream, int i2) throws IOException {
        abstractByteBuf.checkIndex(i, i2);
        if (i2 != 0) {
            ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(i2);
            try {
                byte[] array = heapBuffer.array();
                int arrayOffset = heapBuffer.arrayOffset();
                PlatformDependent.copyMemory(j, array, arrayOffset, (long) i2);
                outputStream.write(array, arrayOffset, i2);
            } finally {
                heapBuffer.release();
            }
        }
    }

    static void setZero(AbstractByteBuf abstractByteBuf, long j, int i, int i2) {
        if (i2 != 0) {
            abstractByteBuf.checkIndex(i, i2);
            PlatformDependent.setMemory(j, (long) i2, 0);
        }
    }

    static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new UnpooledUnsafeNoCleanerDirectByteBuf(byteBufAllocator, i, i2);
        }
        return new UnpooledUnsafeDirectByteBuf(byteBufAllocator, i, i2);
    }

    private UnsafeByteBufUtil() {
    }
}
