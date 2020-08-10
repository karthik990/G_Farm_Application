package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* renamed from: com.esotericsoftware.kryo.io.UnsafeMemoryInput */
public final class UnsafeMemoryInput extends ByteBufferInput {
    private long bufaddress;

    public UnsafeMemoryInput() {
        this.varIntsEnabled = false;
    }

    public UnsafeMemoryInput(int i) {
        super(i);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryInput(byte[] bArr) {
        super(bArr);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryInput(ByteBuffer byteBuffer) {
        super(byteBuffer);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryInput(long j, int i) {
        super(j, i);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryInput(InputStream inputStream) {
        super(inputStream);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public UnsafeMemoryInput(InputStream inputStream, int i) {
        super(inputStream, i);
        this.varIntsEnabled = false;
        updateBufferAddress();
    }

    public void setBuffer(ByteBuffer byteBuffer) {
        super.setBuffer(byteBuffer);
        updateBufferAddress();
    }

    private void updateBufferAddress() {
        this.bufaddress = this.niobuffer.address();
    }

    public int readInt() throws KryoException {
        require(4);
        int i = UnsafeUtil.unsafe().getInt(this.bufaddress + ((long) this.position));
        this.position += 4;
        return i;
    }

    public float readFloat() throws KryoException {
        require(4);
        float f = UnsafeUtil.unsafe().getFloat(this.bufaddress + ((long) this.position));
        this.position += 4;
        return f;
    }

    public short readShort() throws KryoException {
        require(2);
        short s = UnsafeUtil.unsafe().getShort(this.bufaddress + ((long) this.position));
        this.position += 2;
        return s;
    }

    public long readLong() throws KryoException {
        require(8);
        long j = UnsafeUtil.unsafe().getLong(this.bufaddress + ((long) this.position));
        this.position += 8;
        return j;
    }

    public boolean readBoolean() throws KryoException {
        this.niobuffer.position(this.position);
        return super.readBoolean();
    }

    public byte readByte() throws KryoException {
        this.niobuffer.position(this.position);
        return super.readByte();
    }

    public char readChar() throws KryoException {
        require(2);
        char c = UnsafeUtil.unsafe().getChar(this.bufaddress + ((long) this.position));
        this.position += 2;
        return c;
    }

    public double readDouble() throws KryoException {
        require(8);
        double d = UnsafeUtil.unsafe().getDouble(this.bufaddress + ((long) this.position));
        this.position += 8;
        return d;
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

    public byte[] readBytes(int i) throws KryoException {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, (long) bArr.length);
        return bArr;
    }

    public final void readBytes(Object obj, long j, long j2) throws KryoException {
        if (obj.getClass().isArray()) {
            readBytes(obj, UnsafeUtil.byteArrayBaseOffset, j, (int) j2);
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
            UnsafeUtil.unsafe().copyMemory(null, this.bufaddress + ((long) this.position), obj, j + j3, j4);
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
}
