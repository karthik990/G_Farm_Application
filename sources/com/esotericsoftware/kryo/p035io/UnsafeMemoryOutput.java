package com.esotericsoftware.kryo.p035io;

import android.support.p009v4.media.session.PlaybackStateCompat;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import com.esotericsoftware.kryo.util.Util;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.esotericsoftware.kryo.io.UnsafeMemoryOutput */
public final class UnsafeMemoryOutput extends ByteBufferOutput {
    private static final boolean isLittleEndian = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
    private long bufaddress;

    public UnsafeMemoryOutput() {
        this.varIntsEnabled = false;
    }

    public UnsafeMemoryOutput(int i) {
        this(i, i);
    }

    public UnsafeMemoryOutput(int i, int i2) {
        super(i, i2);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryOutput(OutputStream outputStream) {
        super(outputStream);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryOutput(OutputStream outputStream, int i) {
        super(outputStream, i);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryOutput(long j, int i) {
        super(j, i);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public void setBuffer(ByteBuffer byteBuffer, int i) {
        super.setBuffer(byteBuffer, i);
        updateBufferAddress();
    }

    private void updateBufferAddress() {
        this.bufaddress = this.niobuffer.address();
    }

    public final void writeInt(int i) throws KryoException {
        require(4);
        UnsafeUtil.unsafe().putInt(this.bufaddress + ((long) this.position), i);
        this.position += 4;
    }

    public final void writeFloat(float f) throws KryoException {
        require(4);
        UnsafeUtil.unsafe().putFloat(this.bufaddress + ((long) this.position), f);
        this.position += 4;
    }

    public final void writeShort(int i) throws KryoException {
        require(2);
        UnsafeUtil.unsafe().putShort(this.bufaddress + ((long) this.position), (short) i);
        this.position += 2;
    }

    public final void writeLong(long j) throws KryoException {
        require(8);
        UnsafeUtil.unsafe().putLong(this.bufaddress + ((long) this.position), j);
        this.position += 8;
    }

    public final void writeByte(int i) throws KryoException {
        this.niobuffer.position(this.position);
        super.writeByte(i);
    }

    public void writeByte(byte b) throws KryoException {
        this.niobuffer.position(this.position);
        super.writeByte(b);
    }

    public final void writeBoolean(boolean z) throws KryoException {
        this.niobuffer.position(this.position);
        super.writeBoolean(z);
    }

    public final void writeChar(char c) throws KryoException {
        require(2);
        UnsafeUtil.unsafe().putChar(this.bufaddress + ((long) this.position), c);
        this.position += 2;
    }

    public final void writeDouble(double d) throws KryoException {
        require(8);
        UnsafeUtil.unsafe().putDouble(this.bufaddress + ((long) this.position), d);
        this.position += 8;
    }

    public final int writeInt(int i, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return writeVarInt(i, z);
        }
        writeInt(i);
        return 4;
    }

    public final int writeLong(long j, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return writeVarLong(j, z);
        }
        writeLong(j);
        return 8;
    }

    public final int writeVarInt(int i, boolean z) throws KryoException {
        long j = (long) i;
        if (!z) {
            j = (j >> 31) ^ (j << 1);
        }
        long j2 = j & 127;
        long j3 = j >>> 7;
        if (j3 == 0) {
            writeByte((byte) ((int) j2));
            return 1;
        }
        long j4 = j2 | 128 | ((j3 & 127) << 8);
        long j5 = j3 >>> 7;
        if (j5 == 0) {
            writeLittleEndianInt((int) j4);
            this.position -= 2;
            return 2;
        }
        long j6 = j4 | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID | ((j5 & 127) << 16);
        long j7 = j5 >>> 7;
        if (j7 == 0) {
            writeLittleEndianInt((int) j6);
            this.position--;
            return 3;
        }
        long j8 = j6 | 8388608 | ((j7 & 127) << 24);
        long j9 = j7 >>> 7;
        if (j9 == 0) {
            writeLittleEndianInt((int) j8);
            this.position += 0;
            return 4;
        }
        writeLittleEndianLong((((j9 & 127) << 32) | 2147483648L | j8) & 68719476735L);
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
        long j7 = (((long) ((int) (((long) i4) | 2147483648L))) & 4294967295L) | ((j6 & 127) << 32);
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
        writeByte((int) (j14 & 255));
        return 9;
    }

    private final void writeLittleEndianInt(int i) {
        if (isLittleEndian) {
            writeInt(i);
        } else {
            writeInt(Util.swapInt(i));
        }
    }

    private final void writeLittleEndianLong(long j) {
        if (isLittleEndian) {
            writeLong(j);
        } else {
            writeLong(Util.swapLong(j));
        }
    }

    public final void writeInts(int[] iArr, boolean z) throws KryoException {
        if (!this.varIntsEnabled) {
            int[] iArr2 = iArr;
            writeBytes(iArr2, UnsafeUtil.intArrayBaseOffset, 0, (long) (iArr.length << 2));
            return;
        }
        super.writeInts(iArr, z);
    }

    public final void writeLongs(long[] jArr, boolean z) throws KryoException {
        if (!this.varIntsEnabled) {
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

    public void writeBytes(byte[] bArr) throws KryoException {
        if (bArr != null) {
            writeBytes(bArr, 0, (long) bArr.length);
            return;
        }
        throw new IllegalArgumentException("bytes cannot be null.");
    }

    public final void writeBytes(Object obj, long j, long j2) throws KryoException {
        writeBytes(obj, UnsafeUtil.byteArrayBaseOffset, j, j2);
    }

    private final void writeBytes(Object obj, long j, long j2, long j3) throws KryoException {
        long j4 = j3;
        long j5 = j4;
        int min = Math.min(this.capacity - this.position, (int) j4);
        long j6 = j2;
        while (true) {
            long j7 = (long) min;
            long j8 = j7;
            UnsafeUtil.unsafe().copyMemory(obj, j + j6, null, this.bufaddress + ((long) this.position), j7);
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
}
