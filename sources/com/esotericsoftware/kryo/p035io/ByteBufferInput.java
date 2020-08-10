package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.UnsafeUtil;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.esotericsoftware.kryo.io.ByteBufferInput */
public class ByteBufferInput extends Input {
    protected static final ByteOrder nativeOrder = ByteOrder.nativeOrder();
    ByteOrder byteOrder;
    protected ByteBuffer niobuffer;
    protected boolean varIntsEnabled;

    public ByteBufferInput() {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
    }

    public ByteBufferInput(int i) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        this.capacity = i;
        this.niobuffer = ByteBuffer.allocateDirect(i);
        this.niobuffer.order(this.byteOrder);
    }

    public ByteBufferInput(byte[] bArr) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        setBuffer(bArr);
    }

    public ByteBufferInput(ByteBuffer byteBuffer) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        setBuffer(byteBuffer);
    }

    public ByteBufferInput(InputStream inputStream) {
        this(4096);
        if (inputStream != null) {
            this.inputStream = inputStream;
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null.");
    }

    public ByteBufferInput(InputStream inputStream, int i) {
        this(i);
        if (inputStream != null) {
            this.inputStream = inputStream;
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null.");
    }

    public ByteOrder order() {
        return this.byteOrder;
    }

    public void order(ByteOrder byteOrder2) {
        this.byteOrder = byteOrder2;
    }

    public void setBuffer(byte[] bArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bArr.length);
        allocateDirect.put(bArr);
        allocateDirect.position(0);
        allocateDirect.limit(bArr.length);
        allocateDirect.order(this.byteOrder);
        setBuffer(allocateDirect);
    }

    public void setBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            this.niobuffer = byteBuffer;
            this.position = byteBuffer.position();
            this.limit = byteBuffer.limit();
            this.capacity = byteBuffer.capacity();
            this.byteOrder = byteBuffer.order();
            this.total = 0;
            this.inputStream = null;
            return;
        }
        throw new IllegalArgumentException("buffer cannot be null.");
    }

    public void release() {
        close();
        UnsafeUtil.releaseBuffer(this.niobuffer);
        this.niobuffer = null;
    }

    public ByteBufferInput(long j, int i) {
        this.varIntsEnabled = true;
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        setBuffer(UnsafeUtil.getDirectBufferAt(j, i));
    }

    public ByteBuffer getByteBuffer() {
        return this.niobuffer;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.limit = 0;
        rewind();
    }

    public void rewind() {
        super.rewind();
        this.niobuffer.position(0);
    }

    /* access modifiers changed from: protected */
    public int fill(ByteBuffer byteBuffer, int i, int i2) throws KryoException {
        if (this.inputStream == null) {
            return -1;
        }
        try {
            byte[] bArr = new byte[i2];
            int read = this.inputStream.read(bArr, 0, i2);
            byteBuffer.position(i);
            if (read >= 0) {
                byteBuffer.put(bArr, 0, read);
                byteBuffer.position(i);
            }
            return read;
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public final int require(int i) throws KryoException {
        int i2 = this.limit - this.position;
        if (i2 >= i) {
            return i2;
        }
        if (i <= this.capacity) {
            String str = "Buffer underflow.";
            if (i2 > 0) {
                int fill = fill(this.niobuffer, this.limit, this.capacity - this.limit);
                if (fill != -1) {
                    i2 += fill;
                    if (i2 >= i) {
                        this.limit += fill;
                        return i2;
                    }
                } else {
                    throw new KryoException(str);
                }
            }
            this.niobuffer.position(this.position);
            this.niobuffer.compact();
            this.total += (long) this.position;
            this.position = 0;
            while (true) {
                int fill2 = fill(this.niobuffer, i2, this.capacity - i2);
                if (fill2 != -1) {
                    i2 += fill2;
                    if (i2 >= i) {
                        break;
                    }
                } else if (i2 >= i) {
                    this.limit = i2;
                    this.niobuffer.position(0);
                } else {
                    throw new KryoException(str);
                }
            }
            this.limit = i2;
            this.niobuffer.position(0);
            return i2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Buffer too small: capacity: ");
        sb.append(this.capacity);
        sb.append(", required: ");
        sb.append(i);
        throw new KryoException(sb.toString());
    }

    private int optional(int i) throws KryoException {
        int i2 = this.limit - this.position;
        if (i2 >= i) {
            return i;
        }
        int min = Math.min(i, this.capacity);
        int fill = fill(this.niobuffer, this.limit, this.capacity - this.limit);
        int i3 = -1;
        if (fill == -1) {
            if (i2 != 0) {
                i3 = Math.min(i2, min);
            }
            return i3;
        }
        int i4 = i2 + fill;
        if (i4 >= min) {
            this.limit += fill;
            return min;
        }
        this.niobuffer.compact();
        this.total += (long) this.position;
        this.position = 0;
        do {
            int fill2 = fill(this.niobuffer, i4, this.capacity - i4);
            if (fill2 == -1) {
                break;
            }
            i4 += fill2;
        } while (i4 < min);
        this.limit = i4;
        this.niobuffer.position(this.position);
        if (i4 != 0) {
            i3 = Math.min(i4, min);
        }
        return i3;
    }

    public int read() throws KryoException {
        if (optional(1) <= 0) {
            return -1;
        }
        this.niobuffer.position(this.position);
        this.position++;
        return this.niobuffer.get() & 255;
    }

    public int read(byte[] bArr) throws KryoException {
        this.niobuffer.position(this.position);
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws KryoException {
        this.niobuffer.position(this.position);
        if (bArr != null) {
            int min = Math.min(this.limit - this.position, i2);
            int i3 = i2;
            while (true) {
                this.niobuffer.get(bArr, i, min);
                this.position += min;
                i3 -= min;
                if (i3 != 0) {
                    i += min;
                    min = optional(i3);
                    if (min != -1) {
                        if (this.position == this.limit) {
                            break;
                        }
                    } else if (i2 == i3) {
                        return -1;
                    }
                } else {
                    break;
                }
            }
            return i2 - i3;
        }
        throw new IllegalArgumentException("bytes cannot be null.");
    }

    public void setPosition(int i) {
        this.position = i;
        this.niobuffer.position(i);
    }

    public void setLimit(int i) {
        this.limit = i;
        this.niobuffer.limit(i);
    }

    public void skip(int i) throws KryoException {
        super.skip(i);
        this.niobuffer.position(position());
    }

    public long skip(long j) throws KryoException {
        long j2 = j;
        while (j2 > 0) {
            int min = (int) Math.min(2147483639, j2);
            skip(min);
            j2 -= (long) min;
        }
        return j;
    }

    public void close() throws KryoException {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public byte readByte() throws KryoException {
        this.niobuffer.position(this.position);
        require(1);
        this.position++;
        return this.niobuffer.get();
    }

    public int readByteUnsigned() throws KryoException {
        require(1);
        this.position++;
        return this.niobuffer.get() & 255;
    }

    public byte[] readBytes(int i) throws KryoException {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, i);
        return bArr;
    }

    public void readBytes(byte[] bArr) throws KryoException {
        readBytes(bArr, 0, bArr.length);
    }

    public void readBytes(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr != null) {
            int min = Math.min(this.limit - this.position, i2);
            while (true) {
                this.niobuffer.get(bArr, i, min);
                this.position += min;
                i2 -= min;
                if (i2 != 0) {
                    i += min;
                    min = Math.min(i2, this.capacity);
                    require(min);
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("bytes cannot be null.");
        }
    }

    public int readInt() throws KryoException {
        require(4);
        this.position += 4;
        return this.niobuffer.getInt();
    }

    public int readInt(boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return readVarInt(z);
        }
        return readInt();
    }

    public int readVarInt(boolean z) throws KryoException {
        this.niobuffer.position(this.position);
        if (require(1) < 5) {
            return readInt_slow(z);
        }
        this.position++;
        byte b = this.niobuffer.get();
        byte b2 = b & Byte.MAX_VALUE;
        if ((b & 128) != 0) {
            this.position++;
            byte b3 = this.niobuffer.get();
            b2 |= (b3 & Byte.MAX_VALUE) << 7;
            if ((b3 & 128) != 0) {
                this.position++;
                byte b4 = this.niobuffer.get();
                b2 |= (b4 & Byte.MAX_VALUE) << Ascii.f1876SO;
                if ((b4 & 128) != 0) {
                    this.position++;
                    byte b5 = this.niobuffer.get();
                    b2 |= (b5 & Byte.MAX_VALUE) << Ascii.NAK;
                    if ((b5 & 128) != 0) {
                        this.position++;
                        b2 |= (this.niobuffer.get() & Byte.MAX_VALUE) << Ascii.f1869FS;
                    }
                }
            }
        }
        if (!z) {
            b2 = (b2 >>> 1) ^ (-(b2 & 1));
        }
        return b2;
    }

    private int readInt_slow(boolean z) {
        this.position++;
        byte b = this.niobuffer.get();
        byte b2 = b & Byte.MAX_VALUE;
        if ((b & 128) != 0) {
            require(1);
            this.position++;
            byte b3 = this.niobuffer.get();
            b2 |= (b3 & Byte.MAX_VALUE) << 7;
            if ((b3 & 128) != 0) {
                require(1);
                this.position++;
                byte b4 = this.niobuffer.get();
                b2 |= (b4 & Byte.MAX_VALUE) << Ascii.f1876SO;
                if ((b4 & 128) != 0) {
                    require(1);
                    this.position++;
                    byte b5 = this.niobuffer.get();
                    b2 |= (b5 & Byte.MAX_VALUE) << Ascii.NAK;
                    if ((b5 & 128) != 0) {
                        require(1);
                        this.position++;
                        b2 |= (this.niobuffer.get() & Byte.MAX_VALUE) << Ascii.f1869FS;
                    }
                }
            }
        }
        return z ? b2 : (b2 >>> 1) ^ (-(b2 & 1));
    }

    public boolean canReadInt() throws KryoException {
        if (this.limit - this.position >= 5) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = i + 1;
        if ((this.niobuffer.get(i) & 128) == 0) {
            return true;
        }
        if (i2 == this.limit) {
            return false;
        }
        int i3 = i2 + 1;
        if ((this.niobuffer.get(i2) & 128) == 0) {
            return true;
        }
        if (i3 == this.limit) {
            return false;
        }
        int i4 = i3 + 1;
        if ((this.niobuffer.get(i3) & 128) == 0) {
            return true;
        }
        if (i4 == this.limit) {
            return false;
        }
        int i5 = i4 + 1;
        if ((this.niobuffer.get(i4) & 128) != 0 && i5 == this.limit) {
            return false;
        }
        return true;
    }

    public boolean canReadLong() throws KryoException {
        if (this.limit - this.position >= 9) {
            return true;
        }
        if (optional(5) <= 0) {
            return false;
        }
        int i = this.position;
        int i2 = i + 1;
        if ((this.niobuffer.get(i) & 128) == 0) {
            return true;
        }
        if (i2 == this.limit) {
            return false;
        }
        int i3 = i2 + 1;
        if ((this.niobuffer.get(i2) & 128) == 0) {
            return true;
        }
        if (i3 == this.limit) {
            return false;
        }
        int i4 = i3 + 1;
        if ((this.niobuffer.get(i3) & 128) == 0) {
            return true;
        }
        if (i4 == this.limit) {
            return false;
        }
        int i5 = i4 + 1;
        if ((this.niobuffer.get(i4) & 128) == 0) {
            return true;
        }
        if (i5 == this.limit) {
            return false;
        }
        int i6 = i5 + 1;
        if ((this.niobuffer.get(i5) & 128) == 0) {
            return true;
        }
        if (i6 == this.limit) {
            return false;
        }
        int i7 = i6 + 1;
        if ((this.niobuffer.get(i6) & 128) == 0) {
            return true;
        }
        if (i7 == this.limit) {
            return false;
        }
        int i8 = i7 + 1;
        if ((this.niobuffer.get(i7) & 128) == 0) {
            return true;
        }
        if (i8 == this.limit) {
            return false;
        }
        int i9 = i8 + 1;
        if ((this.niobuffer.get(i8) & 128) != 0 && i9 == this.limit) {
            return false;
        }
        return true;
    }

    public String readString() {
        this.niobuffer.position(this.position);
        int require = require(1);
        this.position++;
        byte b = this.niobuffer.get();
        if ((b & 128) == 0) {
            return readAscii();
        }
        int readUtf8Length = require >= 5 ? readUtf8Length(b) : readUtf8Length_slow(b);
        if (readUtf8Length == 0) {
            return null;
        }
        if (readUtf8Length == 1) {
            return "";
        }
        int i = readUtf8Length - 1;
        if (this.chars.length < i) {
            this.chars = new char[i];
        }
        readUtf8(i);
        return new String(this.chars, 0, i);
    }

    private int readUtf8Length(int i) {
        int i2 = i & 63;
        if ((i & 64) == 0) {
            return i2;
        }
        this.position++;
        byte b = this.niobuffer.get();
        int i3 = i2 | ((b & Byte.MAX_VALUE) << 6);
        if ((b & 128) == 0) {
            return i3;
        }
        this.position++;
        byte b2 = this.niobuffer.get();
        int i4 = i3 | ((b2 & Byte.MAX_VALUE) << Ascii.f1866CR);
        if ((b2 & 128) == 0) {
            return i4;
        }
        this.position++;
        byte b3 = this.niobuffer.get();
        int i5 = i4 | ((b3 & Byte.MAX_VALUE) << Ascii.DC4);
        if ((b3 & 128) == 0) {
            return i5;
        }
        this.position++;
        return i5 | ((this.niobuffer.get() & Byte.MAX_VALUE) << Ascii.ESC);
    }

    private int readUtf8Length_slow(int i) {
        int i2 = i & 63;
        if ((i & 64) == 0) {
            return i2;
        }
        require(1);
        this.position++;
        byte b = this.niobuffer.get();
        int i3 = i2 | ((b & Byte.MAX_VALUE) << 6);
        if ((b & 128) == 0) {
            return i3;
        }
        require(1);
        this.position++;
        byte b2 = this.niobuffer.get();
        int i4 = i3 | ((b2 & Byte.MAX_VALUE) << Ascii.f1866CR);
        if ((b2 & 128) == 0) {
            return i4;
        }
        require(1);
        this.position++;
        byte b3 = this.niobuffer.get();
        int i5 = i4 | ((b3 & Byte.MAX_VALUE) << Ascii.DC4);
        if ((b3 & 128) == 0) {
            return i5;
        }
        require(1);
        this.position++;
        return i5 | ((this.niobuffer.get() & Byte.MAX_VALUE) << Ascii.ESC);
    }

    private void readUtf8(int i) {
        char[] cArr = this.chars;
        int min = Math.min(require(1), i);
        int i2 = this.position;
        int i3 = 0;
        while (true) {
            if (i3 >= min) {
                break;
            }
            i2++;
            byte b = this.niobuffer.get();
            if (b < 0) {
                i2--;
                break;
            }
            int i4 = i3 + 1;
            cArr[i3] = (char) b;
            i3 = i4;
        }
        this.position = i2;
        if (i3 < i) {
            this.niobuffer.position(i2);
            readUtf8_slow(i, i3);
        }
    }

    private void readUtf8_slow(int i, int i2) {
        char[] cArr = this.chars;
        while (i2 < i) {
            if (this.position == this.limit) {
                require(1);
            }
            this.position++;
            byte b = this.niobuffer.get() & 255;
            switch (b >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    cArr[i2] = (char) b;
                    break;
                case 12:
                case 13:
                    if (this.position == this.limit) {
                        require(1);
                    }
                    this.position++;
                    cArr[i2] = (char) (((b & Ascii.f1878US) << 6) | (this.niobuffer.get() & 63));
                    break;
                case 14:
                    require(2);
                    this.position += 2;
                    cArr[i2] = (char) (((b & Ascii.f1875SI) << Ascii.f1868FF) | ((this.niobuffer.get() & 63) << 6) | (this.niobuffer.get() & 63));
                    break;
            }
            i2++;
        }
    }

    private String readAscii() {
        int i = this.position;
        int i2 = i - 1;
        int i3 = this.limit;
        while (i != i3) {
            i++;
            if ((this.niobuffer.get() & 128) != 0) {
                ByteBuffer byteBuffer = this.niobuffer;
                int i4 = i - 1;
                byteBuffer.put(i4, (byte) (byteBuffer.get(i4) & Byte.MAX_VALUE));
                int i5 = i - i2;
                byte[] bArr = new byte[i5];
                this.niobuffer.position(i2);
                this.niobuffer.get(bArr);
                String str = new String(bArr, 0, 0, i5);
                ByteBuffer byteBuffer2 = this.niobuffer;
                byteBuffer2.put(i4, (byte) (byteBuffer2.get(i4) | 128));
                this.position = i;
                this.niobuffer.position(this.position);
                return str;
            }
        }
        return readAscii_slow();
    }

    private String readAscii_slow() {
        this.position--;
        int i = this.limit - this.position;
        if (i > this.chars.length) {
            this.chars = new char[(i * 2)];
        }
        char[] cArr = this.chars;
        int i2 = this.position;
        int i3 = this.limit;
        int i4 = 0;
        while (i2 < i3) {
            cArr[i4] = (char) this.niobuffer.get(i2);
            i2++;
            i4++;
        }
        this.position = this.limit;
        while (true) {
            require(1);
            this.position++;
            byte b = this.niobuffer.get();
            if (i == cArr.length) {
                char[] cArr2 = new char[(i * 2)];
                System.arraycopy(cArr, 0, cArr2, 0, i);
                this.chars = cArr2;
                cArr = cArr2;
            }
            if ((b & 128) == 128) {
                int i5 = i + 1;
                cArr[i] = (char) (b & Byte.MAX_VALUE);
                return new String(cArr, 0, i5);
            }
            int i6 = i + 1;
            cArr[i] = (char) b;
            i = i6;
        }
    }

    public StringBuilder readStringBuilder() {
        this.niobuffer.position(this.position);
        int require = require(1);
        this.position++;
        byte b = this.niobuffer.get();
        if ((b & 128) == 0) {
            return new StringBuilder(readAscii());
        }
        int readUtf8Length = require >= 5 ? readUtf8Length(b) : readUtf8Length_slow(b);
        if (readUtf8Length == 0) {
            return null;
        }
        if (readUtf8Length == 1) {
            return new StringBuilder("");
        }
        int i = readUtf8Length - 1;
        if (this.chars.length < i) {
            this.chars = new char[i];
        }
        readUtf8(i);
        StringBuilder sb = new StringBuilder(i);
        sb.append(this.chars, 0, i);
        return sb;
    }

    public float readFloat() throws KryoException {
        require(4);
        this.position += 4;
        return this.niobuffer.getFloat();
    }

    public float readFloat(float f, boolean z) throws KryoException {
        return ((float) readInt(z)) / f;
    }

    public short readShort() throws KryoException {
        require(2);
        this.position += 2;
        return this.niobuffer.getShort();
    }

    public int readShortUnsigned() throws KryoException {
        require(2);
        this.position += 2;
        return this.niobuffer.getShort();
    }

    public long readLong() throws KryoException {
        require(8);
        this.position += 8;
        return this.niobuffer.getLong();
    }

    public long readLong(boolean z) throws KryoException {
        if (this.varIntsEnabled) {
            return readVarLong(z);
        }
        return readLong();
    }

    public long readVarLong(boolean z) throws KryoException {
        this.niobuffer.position(this.position);
        if (require(1) < 9) {
            return readLong_slow(z);
        }
        this.position++;
        byte b = this.niobuffer.get();
        long j = (long) (b & Byte.MAX_VALUE);
        if ((b & 128) != 0) {
            this.position++;
            byte b2 = this.niobuffer.get();
            j |= (long) ((b2 & Byte.MAX_VALUE) << 7);
            if ((b2 & 128) != 0) {
                this.position++;
                byte b3 = this.niobuffer.get();
                j |= (long) ((b3 & Byte.MAX_VALUE) << Ascii.f1876SO);
                if ((b3 & 128) != 0) {
                    this.position++;
                    byte b4 = this.niobuffer.get();
                    j |= (long) ((b4 & Byte.MAX_VALUE) << Ascii.NAK);
                    if ((b4 & 128) != 0) {
                        this.position++;
                        byte b5 = this.niobuffer.get();
                        j |= ((long) (b5 & Byte.MAX_VALUE)) << 28;
                        if ((b5 & 128) != 0) {
                            this.position++;
                            byte b6 = this.niobuffer.get();
                            j |= ((long) (b6 & Byte.MAX_VALUE)) << 35;
                            if ((b6 & 128) != 0) {
                                this.position++;
                                byte b7 = this.niobuffer.get();
                                j |= ((long) (b7 & Byte.MAX_VALUE)) << 42;
                                if ((b7 & 128) != 0) {
                                    this.position++;
                                    byte b8 = this.niobuffer.get();
                                    j |= ((long) (b8 & Byte.MAX_VALUE)) << 49;
                                    if ((b8 & 128) != 0) {
                                        this.position++;
                                        j |= ((long) this.niobuffer.get()) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!z) {
            j = (-(j & 1)) ^ (j >>> 1);
        }
        return j;
    }

    private long readLong_slow(boolean z) {
        this.position++;
        byte b = this.niobuffer.get();
        long j = (long) (b & Byte.MAX_VALUE);
        if ((b & 128) != 0) {
            require(1);
            this.position++;
            byte b2 = this.niobuffer.get();
            j |= (long) ((b2 & Byte.MAX_VALUE) << 7);
            if ((b2 & 128) != 0) {
                require(1);
                this.position++;
                byte b3 = this.niobuffer.get();
                j |= (long) ((b3 & Byte.MAX_VALUE) << Ascii.f1876SO);
                if ((b3 & 128) != 0) {
                    require(1);
                    this.position++;
                    byte b4 = this.niobuffer.get();
                    j |= (long) ((b4 & Byte.MAX_VALUE) << Ascii.NAK);
                    if ((b4 & 128) != 0) {
                        require(1);
                        this.position++;
                        byte b5 = this.niobuffer.get();
                        j |= ((long) (b5 & Byte.MAX_VALUE)) << 28;
                        if ((b5 & 128) != 0) {
                            require(1);
                            this.position++;
                            byte b6 = this.niobuffer.get();
                            j |= ((long) (b6 & Byte.MAX_VALUE)) << 35;
                            if ((b6 & 128) != 0) {
                                require(1);
                                this.position++;
                                byte b7 = this.niobuffer.get();
                                j |= ((long) (b7 & Byte.MAX_VALUE)) << 42;
                                if ((b7 & 128) != 0) {
                                    require(1);
                                    this.position++;
                                    byte b8 = this.niobuffer.get();
                                    j |= ((long) (b8 & Byte.MAX_VALUE)) << 49;
                                    if ((b8 & 128) != 0) {
                                        require(1);
                                        this.position++;
                                        j |= ((long) this.niobuffer.get()) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (z) {
            return j;
        }
        return (-(j & 1)) ^ (j >>> 1);
    }

    public boolean readBoolean() throws KryoException {
        require(1);
        this.position++;
        if (this.niobuffer.get() == 1) {
            return true;
        }
        return false;
    }

    public char readChar() throws KryoException {
        require(2);
        this.position += 2;
        return this.niobuffer.getChar();
    }

    public double readDouble() throws KryoException {
        require(8);
        this.position += 8;
        return this.niobuffer.getDouble();
    }

    public double readDouble(double d, boolean z) throws KryoException {
        double readLong = (double) readLong(z);
        Double.isNaN(readLong);
        return readLong / d;
    }

    public int[] readInts(int i) throws KryoException {
        int i2 = i * 4;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readInts(i);
        }
        int[] iArr = new int[i];
        this.niobuffer.asIntBuffer().get(iArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return iArr;
    }

    public long[] readLongs(int i) throws KryoException {
        int i2 = i * 8;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readLongs(i);
        }
        long[] jArr = new long[i];
        this.niobuffer.asLongBuffer().get(jArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return jArr;
    }

    public float[] readFloats(int i) throws KryoException {
        int i2 = i * 4;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readFloats(i);
        }
        float[] fArr = new float[i];
        this.niobuffer.asFloatBuffer().get(fArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return fArr;
    }

    public short[] readShorts(int i) throws KryoException {
        int i2 = i * 2;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readShorts(i);
        }
        short[] sArr = new short[i];
        this.niobuffer.asShortBuffer().get(sArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return sArr;
    }

    public char[] readChars(int i) throws KryoException {
        int i2 = i * 2;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readChars(i);
        }
        char[] cArr = new char[i];
        this.niobuffer.asCharBuffer().get(cArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return cArr;
    }

    public double[] readDoubles(int i) throws KryoException {
        int i2 = i * 8;
        if (this.capacity - this.position < i2 || !isNativeOrder()) {
            return super.readDoubles(i);
        }
        double[] dArr = new double[i];
        this.niobuffer.asDoubleBuffer().get(dArr);
        this.position += i2;
        this.niobuffer.position(this.position);
        return dArr;
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
