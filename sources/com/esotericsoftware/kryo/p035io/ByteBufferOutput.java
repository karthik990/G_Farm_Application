package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.objectweb.asm.Opcodes;

/* renamed from: com.esotericsoftware.kryo.io.ByteBufferOutput */
public class ByteBufferOutput extends Output {
    protected static final ByteOrder nativeOrder = ByteOrder.nativeOrder();
    ByteOrder byteOrder;
    protected ByteBuffer niobuffer;
    protected boolean varIntsEnabled;

    public ByteBufferOutput() {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
    }

    public ByteBufferOutput(int i) {
        this(i, i);
    }

    public ByteBufferOutput(int i, int i2) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        if (i2 >= -1) {
            this.capacity = i;
            if (i2 == -1) {
                i2 = 2147483639;
            }
            this.maxCapacity = i2;
            this.niobuffer = ByteBuffer.allocateDirect(i);
            this.niobuffer.order(this.byteOrder);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maxBufferSize cannot be < -1: ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    public ByteBufferOutput(OutputStream outputStream) {
        this(4096, 4096);
        if (outputStream != null) {
            this.outputStream = outputStream;
            return;
        }
        throw new IllegalArgumentException("outputStream cannot be null.");
    }

    public ByteBufferOutput(OutputStream outputStream, int i) {
        this(i, i);
        if (outputStream != null) {
            this.outputStream = outputStream;
            return;
        }
        throw new IllegalArgumentException("outputStream cannot be null.");
    }

    public ByteBufferOutput(ByteBuffer byteBuffer) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        setBuffer(byteBuffer);
    }

    public ByteBufferOutput(ByteBuffer byteBuffer, int i) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        setBuffer(byteBuffer, i);
    }

    public ByteBufferOutput(long j, int i) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        this.niobuffer = UnsafeUtil.getDirectBufferAt(j, i);
        setBuffer(this.niobuffer, i);
    }

    public void release() {
        clear();
        UnsafeUtil.releaseBuffer(this.niobuffer);
        this.niobuffer = null;
    }

    public ByteOrder order() {
        return this.byteOrder;
    }

    public void order(ByteOrder byteOrder2) {
        this.byteOrder = byteOrder2;
        this.niobuffer.order(byteOrder2);
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.position = 0;
        this.total = 0;
    }

    public void setBuffer(ByteBuffer byteBuffer) {
        setBuffer(byteBuffer, byteBuffer.capacity());
    }

    public void setBuffer(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        } else if (i >= -1) {
            this.niobuffer = byteBuffer;
            if (i == -1) {
                i = 2147483639;
            }
            this.maxCapacity = i;
            this.byteOrder = byteBuffer.order();
            this.capacity = byteBuffer.capacity();
            this.position = byteBuffer.position();
            this.total = 0;
            this.outputStream = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("maxBufferSize cannot be < -1: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public ByteBuffer getByteBuffer() {
        this.niobuffer.position(this.position);
        return this.niobuffer;
    }

    public byte[] toBytes() {
        byte[] bArr = new byte[this.position];
        this.niobuffer.position(0);
        this.niobuffer.get(bArr, 0, this.position);
        return bArr;
    }

    public void setPosition(int i) {
        this.position = i;
        this.niobuffer.position(i);
    }

    public void clear() {
        this.niobuffer.clear();
        this.position = 0;
        this.total = 0;
    }

    /* access modifiers changed from: protected */
    public boolean require(int i) throws KryoException {
        ByteBuffer byteBuffer;
        if (this.capacity - this.position >= i) {
            return false;
        }
        String str = ", required: ";
        if (i <= this.maxCapacity) {
            flush();
            while (this.capacity - this.position < i) {
                if (this.capacity != this.maxCapacity) {
                    if (this.capacity == 0) {
                        this.capacity = 1;
                    }
                    this.capacity = Math.min(this.capacity * 2, this.maxCapacity);
                    if (this.capacity < 0) {
                        this.capacity = this.maxCapacity;
                    }
                    ByteBuffer byteBuffer2 = this.niobuffer;
                    if (byteBuffer2 == null || byteBuffer2.isDirect()) {
                        byteBuffer = ByteBuffer.allocateDirect(this.capacity);
                    } else {
                        byteBuffer = ByteBuffer.allocate(this.capacity);
                    }
                    this.niobuffer.position(0);
                    this.niobuffer.limit(this.position);
                    byteBuffer.put(this.niobuffer);
                    byteBuffer.order(this.niobuffer.order());
                    ByteOrder byteOrder2 = this.byteOrder;
                    setBuffer(byteBuffer, this.maxCapacity);
                    this.byteOrder = byteOrder2;
                } else {
                    this.niobuffer.order(this.byteOrder);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Buffer overflow. Available: ");
                    sb.append(this.capacity - this.position);
                    sb.append(str);
                    sb.append(i);
                    throw new KryoException(sb.toString());
                }
            }
            return true;
        }
        this.niobuffer.order(this.byteOrder);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Buffer overflow. Max capacity: ");
        sb2.append(this.maxCapacity);
        sb2.append(str);
        sb2.append(i);
        throw new KryoException(sb2.toString());
    }

    public void flush() throws KryoException {
        if (this.outputStream != null) {
            try {
                byte[] bArr = new byte[this.position];
                this.niobuffer.position(0);
                this.niobuffer.get(bArr);
                this.niobuffer.position(0);
                this.outputStream.write(bArr, 0, this.position);
                this.total += (long) this.position;
                this.position = 0;
            } catch (IOException e) {
                throw new KryoException((Throwable) e);
            }
        }
    }

    public void close() throws KryoException {
        flush();
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public void write(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        this.niobuffer.put((byte) i);
        this.position++;
    }

    public void write(byte[] bArr) throws KryoException {
        if (bArr != null) {
            writeBytes(bArr, 0, bArr.length);
            return;
        }
        throw new IllegalArgumentException("bytes cannot be null.");
    }

    public void write(byte[] bArr, int i, int i2) throws KryoException {
        writeBytes(bArr, i, i2);
    }

    public void writeByte(byte b) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        this.niobuffer.put(b);
        this.position++;
    }

    public void writeByte(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        this.niobuffer.put((byte) i);
        this.position++;
    }

    public void writeBytes(byte[] bArr) throws KryoException {
        if (bArr != null) {
            writeBytes(bArr, 0, bArr.length);
            return;
        }
        throw new IllegalArgumentException("bytes cannot be null.");
    }

    public void writeBytes(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr != null) {
            int min = Math.min(this.capacity - this.position, i2);
            while (true) {
                this.niobuffer.put(bArr, i, min);
                this.position += min;
                i2 -= min;
                if (i2 != 0) {
                    i += min;
                    min = Math.min(this.capacity, i2);
                    require(min);
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
    }

    public void writeInt(int i) throws KryoException {
        require(4);
        this.niobuffer.putInt(i);
        this.position += 4;
    }

    public int writeInt(int i, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return writeVarInt(i, z);
        }
        writeInt(i);
        return 4;
    }

    public int writeVarInt(int i, boolean z) throws KryoException {
        this.niobuffer.position(this.position);
        if (!z) {
            i = (i >> 31) ^ (i << 1);
        }
        int i2 = i & Opcodes.LAND;
        int i3 = i >>> 7;
        if (i3 == 0) {
            writeByte(i2);
            return 1;
        }
        int i4 = i2 | 128 | ((i3 & Opcodes.LAND) << 8);
        int i5 = i3 >>> 7;
        if (i5 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i4);
            this.niobuffer.order(this.byteOrder);
            this.position -= 2;
            this.niobuffer.position(this.position);
            return 2;
        }
        int i6 = i4 | 32768 | ((i5 & Opcodes.LAND) << 16);
        int i7 = i5 >>> 7;
        if (i7 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i6);
            this.niobuffer.order(this.byteOrder);
            this.position--;
            this.niobuffer.position(this.position);
            return 3;
        }
        int i8 = i6 | 8388608 | ((i7 & Opcodes.LAND) << 24);
        int i9 = i7 >>> 7;
        if (i9 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i8);
            this.niobuffer.order(this.byteOrder);
            this.position += 0;
            return 4;
        }
        long j = (((long) i9) << 32) | (((long) (i8 | Integer.MIN_VALUE)) & 4294967295L);
        this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
        writeLong(j);
        this.niobuffer.order(this.byteOrder);
        this.position -= 3;
        this.niobuffer.position(this.position);
        return 5;
    }

    public void writeString(String str) throws KryoException {
        boolean z;
        this.niobuffer.position(this.position);
        if (str == null) {
            writeByte(128);
            return;
        }
        int length = str.length();
        if (length == 0) {
            writeByte(129);
            return;
        }
        int i = 0;
        if (length > 1 && length < 64) {
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                } else if (str.charAt(i2) > 127) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        z = false;
        if (z) {
            if (this.capacity - this.position < length) {
                writeAscii_slow(str, length);
            } else {
                byte[] bytes = str.getBytes();
                this.niobuffer.put(bytes, 0, bytes.length);
                this.position += length;
            }
            this.niobuffer.put(this.position - 1, (byte) (128 | this.niobuffer.get(this.position - 1)));
        } else {
            writeUtf8Length(length + 1);
            if (this.capacity - this.position >= length) {
                int i3 = this.position;
                while (i < length) {
                    char charAt = str.charAt(i);
                    if (charAt > 127) {
                        break;
                    }
                    int i4 = i3 + 1;
                    this.niobuffer.put(i3, (byte) charAt);
                    i++;
                    i3 = i4;
                }
                this.position = i3;
                this.niobuffer.position(i3);
            }
            if (i < length) {
                writeString_slow(str, length, i);
            }
            this.niobuffer.position(this.position);
        }
    }

    public void writeString(CharSequence charSequence) throws KryoException {
        if (charSequence == null) {
            writeByte(128);
            return;
        }
        int length = charSequence.length();
        if (length == 0) {
            writeByte(129);
            return;
        }
        writeUtf8Length(length + 1);
        int i = 0;
        if (this.capacity - this.position >= length) {
            int i2 = this.position;
            while (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt > 127) {
                    break;
                }
                int i3 = i2 + 1;
                this.niobuffer.put(i2, (byte) charAt);
                i++;
                i2 = i3;
            }
            this.position = i2;
            this.niobuffer.position(i2);
        }
        if (i < length) {
            writeString_slow(charSequence, length, i);
        }
        this.niobuffer.position(this.position);
    }

    public void writeAscii(String str) throws KryoException {
        if (str == null) {
            writeByte(128);
            return;
        }
        int length = str.length();
        if (length == 0) {
            writeByte(129);
            return;
        }
        if (this.capacity - this.position < length) {
            writeAscii_slow(str, length);
        } else {
            byte[] bytes = str.getBytes();
            this.niobuffer.put(bytes, 0, bytes.length);
            this.position += length;
        }
        this.niobuffer.put(this.position - 1, (byte) (128 | this.niobuffer.get(this.position - 1)));
    }

    private void writeUtf8Length(int i) {
        int i2 = i >>> 6;
        if (i2 == 0) {
            require(1);
            this.niobuffer.put((byte) (i | 128));
            this.position++;
            return;
        }
        int i3 = i >>> 13;
        if (i3 == 0) {
            require(2);
            this.niobuffer.put((byte) (i | 64 | 128));
            this.niobuffer.put((byte) i2);
            this.position += 2;
            return;
        }
        int i4 = i >>> 20;
        if (i4 == 0) {
            require(3);
            this.niobuffer.put((byte) (i | 64 | 128));
            this.niobuffer.put((byte) (i2 | 128));
            this.niobuffer.put((byte) i3);
            this.position += 3;
            return;
        }
        int i5 = i >>> 27;
        if (i5 == 0) {
            require(4);
            this.niobuffer.put((byte) (i | 64 | 128));
            this.niobuffer.put((byte) (i2 | 128));
            this.niobuffer.put((byte) (i3 | 128));
            this.niobuffer.put((byte) i4);
            this.position += 4;
            return;
        }
        require(5);
        this.niobuffer.put((byte) (i | 64 | 128));
        this.niobuffer.put((byte) (i2 | 128));
        this.niobuffer.put((byte) (i3 | 128));
        this.niobuffer.put((byte) (i4 | 128));
        this.niobuffer.put((byte) i5);
        this.position += 5;
    }

    private void writeString_slow(CharSequence charSequence, int i, int i2) {
        while (i2 < i) {
            if (this.position == this.capacity) {
                require(Math.min(this.capacity, i - i2));
            }
            char charAt = charSequence.charAt(i2);
            if (charAt <= 127) {
                ByteBuffer byteBuffer = this.niobuffer;
                int i3 = this.position;
                this.position = i3 + 1;
                byteBuffer.put(i3, (byte) charAt);
            } else if (charAt > 2047) {
                ByteBuffer byteBuffer2 = this.niobuffer;
                int i4 = this.position;
                this.position = i4 + 1;
                byteBuffer2.put(i4, (byte) (((charAt >> 12) & 15) | 224));
                require(2);
                ByteBuffer byteBuffer3 = this.niobuffer;
                int i5 = this.position;
                this.position = i5 + 1;
                byteBuffer3.put(i5, (byte) (((charAt >> 6) & 63) | 128));
                ByteBuffer byteBuffer4 = this.niobuffer;
                int i6 = this.position;
                this.position = i6 + 1;
                byteBuffer4.put(i6, (byte) ((charAt & '?') | 128));
            } else {
                ByteBuffer byteBuffer5 = this.niobuffer;
                int i7 = this.position;
                this.position = i7 + 1;
                byteBuffer5.put(i7, (byte) (((charAt >> 6) & 31) | 192));
                require(1);
                ByteBuffer byteBuffer6 = this.niobuffer;
                int i8 = this.position;
                this.position = i8 + 1;
                byteBuffer6.put(i8, (byte) ((charAt & '?') | 128));
            }
            i2++;
        }
    }

    private void writeAscii_slow(String str, int i) throws KryoException {
        ByteBuffer byteBuffer = this.niobuffer;
        int min = Math.min(i, this.capacity - this.position);
        ByteBuffer byteBuffer2 = byteBuffer;
        int i2 = 0;
        while (i2 < i) {
            byte[] bArr = new byte[i];
            int i3 = i2 + min;
            str.getBytes(i2, i3, bArr, 0);
            byteBuffer2.put(bArr, 0, min);
            this.position += min;
            min = Math.min(i - i3, this.capacity);
            if (require(min)) {
                byteBuffer2 = this.niobuffer;
            }
            i2 = i3;
        }
    }

    public void writeFloat(float f) throws KryoException {
        require(4);
        this.niobuffer.putFloat(f);
        this.position += 4;
    }

    public int writeFloat(float f, float f2, boolean z) throws KryoException {
        return writeInt((int) (f * f2), z);
    }

    public void writeShort(int i) throws KryoException {
        require(2);
        this.niobuffer.putShort((short) i);
        this.position += 2;
    }

    public void writeLong(long j) throws KryoException {
        require(8);
        this.niobuffer.putLong(j);
        this.position += 8;
    }

    public int writeLong(long j, boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return writeVarLong(j, z);
        }
        writeLong(j);
        return 8;
    }

    public int writeVarLong(long j, boolean z) throws KryoException {
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
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i2);
            this.niobuffer.order(this.byteOrder);
            this.position -= 2;
            this.niobuffer.position(this.position);
            return 2;
        }
        int i3 = (int) (((long) (i2 | 32768)) | ((j4 & 127) << 16));
        long j5 = j4 >>> 7;
        if (j5 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i3);
            this.niobuffer.order(this.byteOrder);
            this.position--;
            this.niobuffer.position(this.position);
            return 3;
        }
        int i4 = (int) (((long) (i3 | 8388608)) | ((j5 & 127) << 24));
        long j6 = j5 >>> 7;
        if (j6 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeInt(i4);
            this.niobuffer.order(this.byteOrder);
            this.position += 0;
            return 4;
        }
        long j7 = (((long) (Integer.MIN_VALUE | i4)) & 4294967295L) | ((j6 & 127) << 32);
        long j8 = j6 >>> 7;
        if (j8 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeLong(j7);
            this.niobuffer.order(this.byteOrder);
            this.position -= 3;
            this.niobuffer.position(this.position);
            return 5;
        }
        long j9 = j7 | 549755813888L | ((j8 & 127) << 40);
        long j10 = j8 >>> 7;
        if (j10 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeLong(j9);
            this.niobuffer.order(this.byteOrder);
            this.position -= 2;
            this.niobuffer.position(this.position);
            return 6;
        }
        long j11 = 140737488355328L | j9 | ((j10 & 127) << 48);
        long j12 = j10 >>> 7;
        if (j12 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeLong(j11);
            this.niobuffer.order(this.byteOrder);
            this.position--;
            this.niobuffer.position(this.position);
            return 7;
        }
        long j13 = ((127 & j12) << 56) | j11 | 36028797018963968L;
        long j14 = j12 >>> 7;
        if (j14 == 0) {
            this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
            writeLong(j13);
            this.niobuffer.order(this.byteOrder);
            return 8;
        }
        long j15 = j13 | Long.MIN_VALUE;
        this.niobuffer.order(ByteOrder.LITTLE_ENDIAN);
        writeLong(j15);
        this.niobuffer.order(this.byteOrder);
        write((int) (byte) ((int) j14));
        return 9;
    }

    public int writeLongS(long j, boolean z) throws KryoException {
        long j2 = !z ? (j << 1) ^ (j >> 63) : j;
        long j3 = j2 >>> 7;
        if (j3 == 0) {
            require(1);
            this.niobuffer.put((byte) ((int) j2));
            this.position++;
            return 1;
        }
        long j4 = j2 >>> 14;
        if (j4 == 0) {
            require(2);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) j3));
            this.position += 2;
            return 2;
        }
        long j5 = j3;
        long j6 = j2 >>> 21;
        if (j6 == 0) {
            require(3);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) j4));
            this.position += 3;
            return 3;
        }
        long j7 = j2 >>> 28;
        if (j7 == 0) {
            require(4);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) (128 | j4)));
            this.niobuffer.put((byte) ((int) j6));
            this.position += 4;
            return 4;
        }
        long j8 = j2 >>> 35;
        if (j8 == 0) {
            require(5);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) (j4 | 128)));
            this.niobuffer.put((byte) ((int) (j6 | 128)));
            this.niobuffer.put((byte) ((int) j7));
            this.position += 5;
            return 5;
        }
        long j9 = j8;
        long j10 = j2 >>> 42;
        if (j10 == 0) {
            require(6);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) (j4 | 128)));
            this.niobuffer.put((byte) ((int) (j6 | 128)));
            this.niobuffer.put((byte) ((int) (j7 | 128)));
            this.niobuffer.put((byte) ((int) j9));
            this.position += 6;
            return 6;
        }
        long j11 = j10;
        long j12 = j2 >>> 49;
        if (j12 == 0) {
            require(7);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) (j4 | 128)));
            this.niobuffer.put((byte) ((int) (j6 | 128)));
            this.niobuffer.put((byte) ((int) (j7 | 128)));
            this.niobuffer.put((byte) ((int) (j9 | 128)));
            this.niobuffer.put((byte) ((int) j11));
            this.position += 7;
            return 7;
        }
        long j13 = j12;
        long j14 = j2 >>> 56;
        if (j14 == 0) {
            require(8);
            this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
            this.niobuffer.put((byte) ((int) (j5 | 128)));
            this.niobuffer.put((byte) ((int) (j4 | 128)));
            this.niobuffer.put((byte) ((int) (j6 | 128)));
            this.niobuffer.put((byte) ((int) (j7 | 128)));
            this.niobuffer.put((byte) ((int) (j9 | 128)));
            this.niobuffer.put((byte) ((int) (j11 | 128)));
            this.niobuffer.put((byte) ((int) j13));
            this.position += 8;
            return 8;
        }
        long j15 = j13;
        require(9);
        this.niobuffer.put((byte) ((int) ((j2 & 127) | 128)));
        this.niobuffer.put((byte) ((int) (j5 | 128)));
        this.niobuffer.put((byte) ((int) (j4 | 128)));
        this.niobuffer.put((byte) ((int) (j6 | 128)));
        this.niobuffer.put((byte) ((int) (j7 | 128)));
        this.niobuffer.put((byte) ((int) (j9 | 128)));
        this.niobuffer.put((byte) ((int) (j11 | 128)));
        this.niobuffer.put((byte) ((int) (j15 | 128)));
        this.niobuffer.put((byte) ((int) j14));
        this.position += 9;
        return 9;
    }

    public void writeBoolean(boolean z) throws KryoException {
        require(1);
        this.niobuffer.put(z ? (byte) 1 : 0);
        this.position++;
    }

    public void writeChar(char c) throws KryoException {
        require(2);
        this.niobuffer.putChar(c);
        this.position += 2;
    }

    public void writeDouble(double d) throws KryoException {
        require(8);
        this.niobuffer.putDouble(d);
        this.position += 8;
    }

    public int writeDouble(double d, double d2, boolean z) throws KryoException {
        return writeLong((long) (d * d2), z);
    }

    public void writeInts(int[] iArr) throws KryoException {
        if (this.capacity - this.position < iArr.length * 4 || !isNativeOrder()) {
            super.writeInts(iArr);
            return;
        }
        this.niobuffer.asIntBuffer().put(iArr);
        this.position += iArr.length * 4;
    }

    public void writeLongs(long[] jArr) throws KryoException {
        if (this.capacity - this.position < jArr.length * 8 || !isNativeOrder()) {
            super.writeLongs(jArr);
            return;
        }
        this.niobuffer.asLongBuffer().put(jArr);
        this.position += jArr.length * 8;
    }

    public void writeFloats(float[] fArr) throws KryoException {
        if (this.capacity - this.position < fArr.length * 4 || !isNativeOrder()) {
            super.writeFloats(fArr);
            return;
        }
        this.niobuffer.asFloatBuffer().put(fArr);
        this.position += fArr.length * 4;
    }

    public void writeShorts(short[] sArr) throws KryoException {
        if (this.capacity - this.position < sArr.length * 2 || !isNativeOrder()) {
            super.writeShorts(sArr);
            return;
        }
        this.niobuffer.asShortBuffer().put(sArr);
        this.position += sArr.length * 2;
    }

    public void writeChars(char[] cArr) throws KryoException {
        if (this.capacity - this.position < cArr.length * 2 || !isNativeOrder()) {
            super.writeChars(cArr);
            return;
        }
        this.niobuffer.asCharBuffer().put(cArr);
        this.position += cArr.length * 2;
    }

    public void writeDoubles(double[] dArr) throws KryoException {
        if (this.capacity - this.position < dArr.length * 8 || !isNativeOrder()) {
            super.writeDoubles(dArr);
            return;
        }
        this.niobuffer.asDoubleBuffer().put(dArr);
        this.position += dArr.length * 8;
    }

    private boolean isNativeOrder() {
        return this.byteOrder == nativeOrder;
    }

    public boolean getVarIntsEnabled() {
        return this.varIntsEnabled;
    }

    public void setVarIntsEnabled(boolean z) {
        this.varIntsEnabled = z;
    }
}
