package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import com.esotericsoftware.kryo.util.Util;
import java.io.OutputStream;
import java.nio.ByteOrder;
import org.objectweb.asm.Opcodes;

/* renamed from: com.esotericsoftware.kryo.io.UnsafeOutput */
public final class UnsafeOutput extends Output {
    private static final boolean isLittleEndian = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
    private boolean supportVarInts;

    public UnsafeOutput() {
        this.supportVarInts = false;
    }

    public UnsafeOutput(int i) {
        this(i, i);
    }

    public UnsafeOutput(int i, int i2) {
        super(i, i2);
        this.supportVarInts = false;
    }

    public UnsafeOutput(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public UnsafeOutput(byte[] bArr, int i) {
        super(bArr, i);
        this.supportVarInts = false;
    }

    public UnsafeOutput(OutputStream outputStream) {
        super(outputStream);
        this.supportVarInts = false;
    }

    public UnsafeOutput(OutputStream outputStream, int i) {
        super(outputStream, i);
        this.supportVarInts = false;
    }

    public final void writeInt(int i) throws KryoException {
        require(4);
        UnsafeUtil.unsafe().putInt(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), i);
        this.position += 4;
    }

    private final void writeLittleEndianInt(int i) {
        if (isLittleEndian) {
            writeInt(i);
        } else {
            writeInt(Util.swapInt(i));
        }
    }

    public final void writeFloat(float f) throws KryoException {
        require(4);
        UnsafeUtil.unsafe().putFloat(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), f);
        this.position += 4;
    }

    public final void writeShort(int i) throws KryoException {
        require(2);
        UnsafeUtil.unsafe().putShort(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), (short) i);
        this.position += 2;
    }

    public final void writeLong(long j) throws KryoException {
        require(8);
        UnsafeUtil.unsafe().putLong(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), j);
        this.position += 8;
    }

    private final void writeLittleEndianLong(long j) {
        if (isLittleEndian) {
            writeLong(j);
        } else {
            writeLong(Util.swapLong(j));
        }
    }

    public final void writeDouble(double d) throws KryoException {
        require(8);
        UnsafeUtil.unsafe().putDouble(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), d);
        this.position += 8;
    }

    public final void writeChar(char c) throws KryoException {
        require(2);
        UnsafeUtil.unsafe().putChar(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), c);
        this.position += 2;
    }

    public final int writeInt(int i, boolean z) throws KryoException {
        if (this.supportVarInts) {
            return writeVarInt(i, z);
        }
        writeInt(i);
        return 4;
    }

    public final int writeLong(long j, boolean z) throws KryoException {
        if (this.supportVarInts) {
            return writeVarLong(j, z);
        }
        writeLong(j);
        return 8;
    }

    public final int writeVarInt(int i, boolean z) throws KryoException {
        if (!z) {
            i = (i >> 31) ^ (i << 1);
        }
        int i2 = i & Opcodes.LAND;
        int i3 = i >>> 7;
        if (i3 == 0) {
            write(i2);
            return 1;
        }
        int i4 = i2 | 128 | ((i3 & Opcodes.LAND) << 8);
        int i5 = i3 >>> 7;
        if (i5 == 0) {
            writeLittleEndianInt(i4);
            this.position -= 2;
            return 2;
        }
        int i6 = i4 | 32768 | ((i5 & Opcodes.LAND) << 16);
        int i7 = i5 >>> 7;
        if (i7 == 0) {
            writeLittleEndianInt(i6);
            this.position--;
            return 3;
        }
        int i8 = i6 | 8388608 | ((i7 & Opcodes.LAND) << 24);
        int i9 = i7 >>> 7;
        if (i9 == 0) {
            writeLittleEndianInt(i8);
            this.position += 0;
            return 4;
        }
        writeLittleEndianLong(((((long) (i9 & Opcodes.LAND)) << 32) | ((long) i8) | 2147483648L) & 68719476735L);
        this.position -= 3;
        return 5;
    }

    public final int writeVarLong(long j, boolean z) throws KryoException {
        long j2 = !z ? (j << 1) ^ (j >> 63) : j;
        int i = (int) (j2 & 127);
        long j3 = j2 >>> 7;
        if (j3 == 0) {
            writeByte(i);
            return 1;
        }
        int i2 = (int) (((long) (i | 128)) | ((j3 & 127) << 8));
        long j4 = j3 >>> 7;
        if (j4 == 0) {
            writeLittleEndianInt(i2);
            this.position -= 2;
            return 2;
        }
        int i3 = (int) (((long) (i2 | 32768)) | ((j4 & 127) << 16));
        long j5 = j4 >>> 7;
        if (j5 == 0) {
            writeLittleEndianInt(i3);
            this.position--;
            return 3;
        }
        int i4 = (int) (((long) (i3 | 8388608)) | ((j5 & 127) << 24));
        long j6 = j5 >>> 7;
        if (j6 == 0) {
            writeLittleEndianInt(i4);
            this.position += 0;
            return 4;
        }
        long j7 = (((long) i4) & 4294967295L) | 2147483648L | ((j6 & 127) << 32);
        long j8 = j6 >>> 7;
        if (j8 == 0) {
            writeLittleEndianLong(j7);
            this.position -= 3;
            return 5;
        }
        long j9 = j7 | 549755813888L | ((j8 & 127) << 40);
        long j10 = j8 >>> 7;
        if (j10 == 0) {
            writeLittleEndianLong(j9);
            this.position -= 2;
            return 6;
        }
        long j11 = 140737488355328L | j9 | ((j10 & 127) << 48);
        long j12 = j10 >>> 7;
        if (j12 == 0) {
            writeLittleEndianLong(j11);
            this.position--;
            return 7;
        }
        long j13 = ((127 & j12) << 56) | j11 | 36028797018963968L;
        long j14 = j12 >>> 7;
        if (j14 == 0) {
            writeLittleEndianLong(j13);
            return 8;
        }
        writeLittleEndianLong(j13 | Long.MIN_VALUE);
        write((int) (j14 & 255));
        return 9;
    }

    public final void writeInts(int[] iArr, boolean z) throws KryoException {
        if (!this.supportVarInts) {
            int[] iArr2 = iArr;
            writeBytes(iArr2, UnsafeUtil.intArrayBaseOffset, 0, (long) (iArr.length << 2));
            return;
        }
        super.writeInts(iArr, z);
    }

    public final void writeLongs(long[] jArr, boolean z) throws KryoException {
        if (!this.supportVarInts) {
            long[] jArr2 = jArr;
            writeBytes(jArr2, UnsafeUtil.longArrayBaseOffset, 0, (long) (jArr.length << 3));
            return;
        }
        super.writeLongs(jArr, z);
    }

    public final void writeInts(int[] iArr) throws KryoException {
        int[] iArr2 = iArr;
        writeBytes(iArr2, UnsafeUtil.intArrayBaseOffset, 0, (long) (iArr.length << 2));
    }

    public final void writeLongs(long[] jArr) throws KryoException {
        long[] jArr2 = jArr;
        writeBytes(jArr2, UnsafeUtil.longArrayBaseOffset, 0, (long) (jArr.length << 3));
    }

    public final void writeFloats(float[] fArr) throws KryoException {
        float[] fArr2 = fArr;
        writeBytes(fArr2, UnsafeUtil.floatArrayBaseOffset, 0, (long) (fArr.length << 2));
    }

    public final void writeShorts(short[] sArr) throws KryoException {
        short[] sArr2 = sArr;
        writeBytes(sArr2, UnsafeUtil.shortArrayBaseOffset, 0, (long) (sArr.length << 1));
    }

    public final void writeChars(char[] cArr) throws KryoException {
        char[] cArr2 = cArr;
        writeBytes(cArr2, UnsafeUtil.charArrayBaseOffset, 0, (long) (cArr.length << 1));
    }

    public final void writeDoubles(double[] dArr) throws KryoException {
        double[] dArr2 = dArr;
        writeBytes(dArr2, UnsafeUtil.doubleArrayBaseOffset, 0, (long) (dArr.length << 3));
    }

    public final void writeBytes(Object obj, long j, long j2) throws KryoException {
        writeBytes(obj, 0, j, j2);
    }

    private final void writeBytes(Object obj, long j, long j2, long j3) throws KryoException {
        long j4 = j3;
        long j5 = j4;
        int min = Math.min(this.capacity - this.position, (int) j4);
        long j6 = j2;
        while (true) {
            long j7 = (long) min;
            long j8 = j7;
            UnsafeUtil.unsafe().copyMemory(obj, j + j6, this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), j7);
            this.position += min;
            j5 -= j8;
            if (j5 != 0) {
                j6 += j8;
                min = Math.min(this.capacity, (int) j5);
                require(min);
            } else {
                return;
            }
        }
    }

    public boolean supportVarInts() {
        return this.supportVarInts;
    }

    public void supportVarInts(boolean z) {
        this.supportVarInts = z;
    }
}
