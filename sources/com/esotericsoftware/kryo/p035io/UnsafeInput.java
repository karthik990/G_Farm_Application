package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import java.io.InputStream;

/* renamed from: com.esotericsoftware.kryo.io.UnsafeInput */
public final class UnsafeInput extends Input {
    private boolean varIntsEnabled = false;

    public UnsafeInput() {
    }

    public UnsafeInput(int i) {
        super(i);
    }

    public UnsafeInput(byte[] bArr) {
        super(bArr);
    }

    public UnsafeInput(byte[] bArr, int i, int i2) {
        super(bArr, i, i2);
    }

    public UnsafeInput(InputStream inputStream) {
        super(inputStream);
    }

    public UnsafeInput(InputStream inputStream, int i) {
        super(inputStream, i);
    }

    public int readInt() throws KryoException {
        require(4);
        int i = UnsafeUtil.unsafe().getInt(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 4;
        return i;
    }

    public float readFloat() throws KryoException {
        require(4);
        float f = UnsafeUtil.unsafe().getFloat(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 4;
        return f;
    }

    public short readShort() throws KryoException {
        require(2);
        short s = UnsafeUtil.unsafe().getShort(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 2;
        return s;
    }

    public long readLong() throws KryoException {
        require(8);
        long j = UnsafeUtil.unsafe().getLong(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 8;
        return j;
    }

    public double readDouble() throws KryoException {
        require(8);
        double d = UnsafeUtil.unsafe().getDouble(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 8;
        return d;
    }

    public char readChar() throws KryoException {
        require(2);
        char c = UnsafeUtil.unsafe().getChar(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position));
        this.position += 2;
        return c;
    }

    public int readInt(boolean z) throws KryoException {
        if (!this.varIntsEnabled) {
            return readInt();
        }
        return super.readInt(z);
    }

    public long readLong(boolean z) throws KryoException {
        if (!this.varIntsEnabled) {
            return readLong();
        }
        return super.readLong(z);
    }

    public final int[] readInts(int i, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return super.readInts(i, z);
        }
        int i2 = i << 2;
        int[] iArr = new int[i];
        readBytes(iArr, UnsafeUtil.intArrayBaseOffset, 0, i2);
        return iArr;
    }

    public final long[] readLongs(int i, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return super.readLongs(i, z);
        }
        int i2 = i << 3;
        long[] jArr = new long[i];
        readBytes(jArr, UnsafeUtil.longArrayBaseOffset, 0, i2);
        return jArr;
    }

    public final int[] readInts(int i) throws KryoException {
        int i2 = i << 2;
        int[] iArr = new int[i];
        readBytes(iArr, UnsafeUtil.intArrayBaseOffset, 0, i2);
        return iArr;
    }

    public final long[] readLongs(int i) throws KryoException {
        int i2 = i << 3;
        long[] jArr = new long[i];
        readBytes(jArr, UnsafeUtil.longArrayBaseOffset, 0, i2);
        return jArr;
    }

    public final float[] readFloats(int i) throws KryoException {
        int i2 = i << 2;
        float[] fArr = new float[i];
        readBytes(fArr, UnsafeUtil.floatArrayBaseOffset, 0, i2);
        return fArr;
    }

    public final short[] readShorts(int i) throws KryoException {
        int i2 = i << 1;
        short[] sArr = new short[i];
        readBytes(sArr, UnsafeUtil.shortArrayBaseOffset, 0, i2);
        return sArr;
    }

    public final char[] readChars(int i) throws KryoException {
        int i2 = i << 1;
        char[] cArr = new char[i];
        readBytes(cArr, UnsafeUtil.charArrayBaseOffset, 0, i2);
        return cArr;
    }

    public final double[] readDoubles(int i) throws KryoException {
        int i2 = i << 3;
        double[] dArr = new double[i];
        readBytes(dArr, UnsafeUtil.doubleArrayBaseOffset, 0, i2);
        return dArr;
    }

    public final void readBytes(Object obj, long j, long j2) throws KryoException {
        if (obj.getClass().isArray()) {
            readBytes(obj, 0, j, (int) j2);
            return;
        }
        throw new KryoException("Only bulk reads of arrays is supported");
    }

    private final void readBytes(Object obj, long j, long j2, int i) throws KryoException {
        int i2 = i;
        int min = Math.min(this.limit - this.position, i2);
        int i3 = i2;
        long j3 = j2;
        while (true) {
            long j4 = (long) min;
            UnsafeUtil.unsafe().copyMemory(this.buffer, UnsafeUtil.byteArrayBaseOffset + ((long) this.position), obj, j + j3, j4);
            this.position += min;
            i3 -= min;
            if (i3 != 0) {
                j3 += j4;
                min = Math.min(i3, this.capacity);
                require(min);
            } else {
                return;
            }
        }
    }

    public boolean getVarIntsEnabled() {
        return this.varIntsEnabled;
    }

    public void setVarIntsEnabled(boolean z) {
        this.varIntsEnabled = z;
    }
}
