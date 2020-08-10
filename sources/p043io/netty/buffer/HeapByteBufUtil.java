package p043io.netty.buffer;

import com.google.common.base.Ascii;

/* renamed from: io.netty.buffer.HeapByteBufUtil */
final class HeapByteBufUtil {
    static byte getByte(byte[] bArr, int i) {
        return bArr[i];
    }

    static short getShort(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] & 255) | (bArr[i] << 8));
    }

    static short getShortLE(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] << 8) | (bArr[i] & 255));
    }

    static int getUnsignedMedium(byte[] bArr, int i) {
        return (bArr[i + 2] & 255) | ((bArr[i] & 255) << Ascii.DLE) | ((bArr[i + 1] & 255) << 8);
    }

    static int getUnsignedMediumLE(byte[] bArr, int i) {
        return ((bArr[i + 2] & 255) << Ascii.DLE) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8);
    }

    static int getInt(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
    }

    static int getIntLE(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
    }

    static long getLong(byte[] bArr, int i) {
        return (((long) bArr[i + 7]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i + 1]) & 255) << 48) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 6]) & 255) << 8);
    }

    static long getLongLE(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static void setByte(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
    }

    static void setShort(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    static void setShortLE(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
    }

    static void setMedium(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 16);
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) i2;
    }

    static void setMediumLE(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) (i2 >>> 16);
    }

    static void setInt(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 24);
        bArr[i + 1] = (byte) (i2 >>> 16);
        bArr[i + 2] = (byte) (i2 >>> 8);
        bArr[i + 3] = (byte) i2;
    }

    static void setIntLE(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) (i2 >>> 16);
        bArr[i + 3] = (byte) (i2 >>> 24);
    }

    static void setLong(byte[] bArr, int i, long j) {
        bArr[i] = (byte) ((int) (j >>> 56));
        bArr[i + 1] = (byte) ((int) (j >>> 48));
        bArr[i + 2] = (byte) ((int) (j >>> 40));
        bArr[i + 3] = (byte) ((int) (j >>> 32));
        bArr[i + 4] = (byte) ((int) (j >>> 24));
        bArr[i + 5] = (byte) ((int) (j >>> 16));
        bArr[i + 6] = (byte) ((int) (j >>> 8));
        bArr[i + 7] = (byte) ((int) j);
    }

    static void setLongLE(byte[] bArr, int i, long j) {
        bArr[i] = (byte) ((int) j);
        bArr[i + 1] = (byte) ((int) (j >>> 8));
        bArr[i + 2] = (byte) ((int) (j >>> 16));
        bArr[i + 3] = (byte) ((int) (j >>> 24));
        bArr[i + 4] = (byte) ((int) (j >>> 32));
        bArr[i + 5] = (byte) ((int) (j >>> 40));
        bArr[i + 6] = (byte) ((int) (j >>> 48));
        bArr[i + 7] = (byte) ((int) (j >>> 56));
    }

    private HeapByteBufUtil() {
    }
}
