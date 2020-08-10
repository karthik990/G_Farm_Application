package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.esotericsoftware.kryo.io.Input */
public class Input extends InputStream {
    protected byte[] buffer;
    protected int capacity;
    protected char[] chars;
    protected InputStream inputStream;
    protected int limit;
    protected int position;
    protected long total;

    public Input() {
        this.chars = new char[32];
    }

    public Input(int i) {
        this.chars = new char[32];
        this.capacity = i;
        this.buffer = new byte[i];
    }

    public Input(byte[] bArr) {
        this.chars = new char[32];
        setBuffer(bArr, 0, bArr.length);
    }

    public Input(byte[] bArr, int i, int i2) {
        this.chars = new char[32];
        setBuffer(bArr, i, i2);
    }

    public Input(InputStream inputStream2) {
        this(4096);
        if (inputStream2 != null) {
            this.inputStream = inputStream2;
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null.");
    }

    public Input(InputStream inputStream2, int i) {
        this(i);
        if (inputStream2 != null) {
            this.inputStream = inputStream2;
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null.");
    }

    public void setBuffer(byte[] bArr) {
        setBuffer(bArr, 0, bArr.length);
    }

    public void setBuffer(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            this.buffer = bArr;
            this.position = i;
            this.limit = i + i2;
            this.capacity = bArr.length;
            this.total = 0;
            this.inputStream = null;
            return;
        }
        throw new IllegalArgumentException("bytes cannot be null.");
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
        this.limit = 0;
        rewind();
    }

    public long total() {
        return this.total + ((long) this.position);
    }

    public void setTotal(long j) {
        this.total = j;
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int i) {
        this.limit = i;
    }

    public void rewind() {
        this.position = 0;
        this.total = 0;
    }

    public void skip(int i) throws KryoException {
        int min = Math.min(this.limit - this.position, i);
        while (true) {
            this.position += min;
            i -= min;
            if (i != 0) {
                min = Math.min(i, this.capacity);
                require(min);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int fill(byte[] bArr, int i, int i2) throws KryoException {
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 == null) {
            return -1;
        }
        try {
            return inputStream2.read(bArr, i, i2);
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public int require(int i) throws KryoException {
        int i2 = this.limit;
        int i3 = i2 - this.position;
        if (i3 >= i) {
            return i3;
        }
        int i4 = this.capacity;
        if (i <= i4) {
            String str = "Buffer underflow.";
            if (i3 > 0) {
                int fill = fill(this.buffer, i2, i4 - i2);
                if (fill != -1) {
                    i3 += fill;
                    if (i3 >= i) {
                        this.limit += fill;
                        return i3;
                    }
                } else {
                    throw new KryoException(str);
                }
            }
            byte[] bArr = this.buffer;
            System.arraycopy(bArr, this.position, bArr, 0, i3);
            this.total += (long) this.position;
            this.position = 0;
            while (true) {
                int fill2 = fill(this.buffer, i3, this.capacity - i3);
                if (fill2 != -1) {
                    i3 += fill2;
                    if (i3 >= i) {
                        break;
                    }
                } else if (i3 >= i) {
                    this.limit = i3;
                } else {
                    throw new KryoException(str);
                }
            }
            this.limit = i3;
            return i3;
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
        byte[] bArr = this.buffer;
        int i3 = this.limit;
        int fill = fill(bArr, i3, this.capacity - i3);
        int i4 = -1;
        if (fill == -1) {
            if (i2 != 0) {
                i4 = Math.min(i2, min);
            }
            return i4;
        }
        int i5 = i2 + fill;
        if (i5 >= min) {
            this.limit += fill;
            return min;
        }
        byte[] bArr2 = this.buffer;
        System.arraycopy(bArr2, this.position, bArr2, 0, i5);
        this.total += (long) this.position;
        this.position = 0;
        do {
            int fill2 = fill(this.buffer, i5, this.capacity - i5);
            if (fill2 == -1) {
                break;
            }
            i5 += fill2;
        } while (i5 < min);
        this.limit = i5;
        if (i5 != 0) {
            i4 = Math.min(i5, min);
        }
        return i4;
    }

    public boolean eof() {
        return optional(1) <= 0;
    }

    public int available() throws IOException {
        int i = this.limit - this.position;
        InputStream inputStream2 = this.inputStream;
        return i + (inputStream2 != null ? inputStream2.available() : 0);
    }

    public int read() throws KryoException {
        if (optional(1) <= 0) {
            return -1;
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] bArr) throws KryoException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws KryoException {
        if (bArr != null) {
            int min = Math.min(this.limit - this.position, i2);
            int i3 = i2;
            while (true) {
                System.arraycopy(this.buffer, this.position, bArr, i, min);
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
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException unused) {
            }
        }
    }

    public byte readByte() throws KryoException {
        require(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i];
    }

    public int readByteUnsigned() throws KryoException {
        require(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & 255;
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
                System.arraycopy(this.buffer, this.position, bArr, i, min);
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
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 4;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
    }

    public int readInt(boolean z) throws KryoException {
        return readVarInt(z);
    }

    public int readVarInt(boolean z) throws KryoException {
        if (require(1) < 5) {
            return readInt_slow(z);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
        byte b2 = b & Byte.MAX_VALUE;
        if ((b & 128) != 0) {
            int i2 = this.position;
            this.position = i2 + 1;
            byte b3 = bArr[i2];
            b2 |= (b3 & Byte.MAX_VALUE) << 7;
            if ((b3 & 128) != 0) {
                int i3 = this.position;
                this.position = i3 + 1;
                byte b4 = bArr[i3];
                b2 |= (b4 & Byte.MAX_VALUE) << Ascii.f1876SO;
                if ((b4 & 128) != 0) {
                    int i4 = this.position;
                    this.position = i4 + 1;
                    byte b5 = bArr[i4];
                    b2 |= (b5 & Byte.MAX_VALUE) << Ascii.NAK;
                    if ((b5 & 128) != 0) {
                        int i5 = this.position;
                        this.position = i5 + 1;
                        b2 |= (bArr[i5] & Byte.MAX_VALUE) << Ascii.f1869FS;
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
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
        byte b2 = b & Byte.MAX_VALUE;
        if ((b & 128) != 0) {
            require(1);
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            byte b3 = bArr2[i2];
            b2 |= (b3 & Byte.MAX_VALUE) << 7;
            if ((b3 & 128) != 0) {
                require(1);
                int i3 = this.position;
                this.position = i3 + 1;
                byte b4 = bArr2[i3];
                b2 |= (b4 & Byte.MAX_VALUE) << Ascii.f1876SO;
                if ((b4 & 128) != 0) {
                    require(1);
                    int i4 = this.position;
                    this.position = i4 + 1;
                    byte b5 = bArr2[i4];
                    b2 |= (b5 & Byte.MAX_VALUE) << Ascii.NAK;
                    if ((b5 & 128) != 0) {
                        require(1);
                        int i5 = this.position;
                        this.position = i5 + 1;
                        b2 |= (bArr2[i5] & Byte.MAX_VALUE) << Ascii.f1869FS;
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
        byte[] bArr = this.buffer;
        int i2 = i + 1;
        if ((bArr[i] & 128) == 0) {
            return true;
        }
        int i3 = this.limit;
        if (i2 == i3) {
            return false;
        }
        int i4 = i2 + 1;
        if ((bArr[i2] & 128) == 0) {
            return true;
        }
        if (i4 == i3) {
            return false;
        }
        int i5 = i4 + 1;
        if ((bArr[i4] & 128) == 0) {
            return true;
        }
        if (i5 == i3) {
            return false;
        }
        int i6 = i5 + 1;
        if ((bArr[i5] & 128) != 0 && i6 == i3) {
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
        byte[] bArr = this.buffer;
        int i2 = i + 1;
        if ((bArr[i] & 128) == 0) {
            return true;
        }
        int i3 = this.limit;
        if (i2 == i3) {
            return false;
        }
        int i4 = i2 + 1;
        if ((bArr[i2] & 128) == 0) {
            return true;
        }
        if (i4 == i3) {
            return false;
        }
        int i5 = i4 + 1;
        if ((bArr[i4] & 128) == 0) {
            return true;
        }
        if (i5 == i3) {
            return false;
        }
        int i6 = i5 + 1;
        if ((bArr[i5] & 128) == 0) {
            return true;
        }
        if (i6 == i3) {
            return false;
        }
        int i7 = i6 + 1;
        if ((bArr[i6] & 128) == 0) {
            return true;
        }
        if (i7 == i3) {
            return false;
        }
        int i8 = i7 + 1;
        if ((bArr[i7] & 128) == 0) {
            return true;
        }
        if (i8 == i3) {
            return false;
        }
        int i9 = i8 + 1;
        if ((bArr[i8] & 128) == 0) {
            return true;
        }
        if (i9 == i3) {
            return false;
        }
        int i10 = i9 + 1;
        if ((bArr[i9] & 128) != 0 && i10 == i3) {
            return false;
        }
        return true;
    }

    public String readString() {
        int require = require(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
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
        int i2 = readUtf8Length - 1;
        if (this.chars.length < i2) {
            this.chars = new char[i2];
        }
        readUtf8(i2);
        return new String(this.chars, 0, i2);
    }

    private int readUtf8Length(int i) {
        int i2 = i & 63;
        if ((i & 64) == 0) {
            return i2;
        }
        byte[] bArr = this.buffer;
        int i3 = this.position;
        this.position = i3 + 1;
        byte b = bArr[i3];
        int i4 = i2 | ((b & Byte.MAX_VALUE) << 6);
        if ((b & 128) == 0) {
            return i4;
        }
        int i5 = this.position;
        this.position = i5 + 1;
        byte b2 = bArr[i5];
        int i6 = i4 | ((b2 & Byte.MAX_VALUE) << Ascii.f1866CR);
        if ((b2 & 128) == 0) {
            return i6;
        }
        int i7 = this.position;
        this.position = i7 + 1;
        byte b3 = bArr[i7];
        int i8 = i6 | ((b3 & Byte.MAX_VALUE) << Ascii.DC4);
        if ((b3 & 128) == 0) {
            return i8;
        }
        int i9 = this.position;
        this.position = i9 + 1;
        return i8 | ((bArr[i9] & Byte.MAX_VALUE) << Ascii.ESC);
    }

    private int readUtf8Length_slow(int i) {
        int i2 = i & 63;
        if ((i & 64) == 0) {
            return i2;
        }
        require(1);
        byte[] bArr = this.buffer;
        int i3 = this.position;
        this.position = i3 + 1;
        byte b = bArr[i3];
        int i4 = i2 | ((b & Byte.MAX_VALUE) << 6);
        if ((b & 128) == 0) {
            return i4;
        }
        require(1);
        int i5 = this.position;
        this.position = i5 + 1;
        byte b2 = bArr[i5];
        int i6 = i4 | ((b2 & Byte.MAX_VALUE) << Ascii.f1866CR);
        if ((b2 & 128) == 0) {
            return i6;
        }
        require(1);
        int i7 = this.position;
        this.position = i7 + 1;
        byte b3 = bArr[i7];
        int i8 = i6 | ((b3 & Byte.MAX_VALUE) << Ascii.DC4);
        if ((b3 & 128) == 0) {
            return i8;
        }
        require(1);
        int i9 = this.position;
        this.position = i9 + 1;
        return i8 | ((bArr[i9] & Byte.MAX_VALUE) << Ascii.ESC);
    }

    private void readUtf8(int i) {
        byte[] bArr = this.buffer;
        char[] cArr = this.chars;
        int min = Math.min(require(1), i);
        int i2 = this.position;
        int i3 = 0;
        while (true) {
            if (i3 >= min) {
                break;
            }
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b < 0) {
                i2 = i4 - 1;
                break;
            }
            int i5 = i3 + 1;
            cArr[i3] = (char) b;
            i2 = i4;
            i3 = i5;
        }
        this.position = i2;
        if (i3 < i) {
            readUtf8_slow(i, i3);
        }
    }

    private void readUtf8_slow(int i, int i2) {
        char[] cArr = this.chars;
        byte[] bArr = this.buffer;
        while (i2 < i) {
            if (this.position == this.limit) {
                require(1);
            }
            int i3 = this.position;
            this.position = i3 + 1;
            byte b = bArr[i3] & 255;
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
                    int i4 = (b & Ascii.f1878US) << 6;
                    int i5 = this.position;
                    this.position = i5 + 1;
                    cArr[i2] = (char) (i4 | (bArr[i5] & 63));
                    break;
                case 14:
                    require(2);
                    int i6 = (b & Ascii.f1875SI) << Ascii.f1868FF;
                    int i7 = this.position;
                    this.position = i7 + 1;
                    byte b2 = i6 | ((bArr[i7] & 63) << 6);
                    int i8 = this.position;
                    this.position = i8 + 1;
                    cArr[i2] = (char) (b2 | (bArr[i8] & 63));
                    break;
            }
            i2++;
        }
    }

    private String readAscii() {
        byte[] bArr = this.buffer;
        int i = this.position;
        int i2 = i - 1;
        int i3 = this.limit;
        while (i != i3) {
            int i4 = i + 1;
            if ((bArr[i] & 128) != 0) {
                int i5 = i4 - 1;
                bArr[i5] = (byte) (bArr[i5] & Byte.MAX_VALUE);
                String str = new String(bArr, 0, i2, i4 - i2);
                bArr[i5] = (byte) (bArr[i5] | 128);
                this.position = i4;
                return str;
            }
            i = i4;
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
        byte[] bArr = this.buffer;
        int i2 = this.position;
        int i3 = this.limit;
        int i4 = 0;
        while (i2 < i3) {
            cArr[i4] = (char) bArr[i2];
            i2++;
            i4++;
        }
        this.position = this.limit;
        while (true) {
            require(1);
            int i5 = this.position;
            this.position = i5 + 1;
            byte b = bArr[i5];
            if (i == cArr.length) {
                char[] cArr2 = new char[(i * 2)];
                System.arraycopy(cArr, 0, cArr2, 0, i);
                this.chars = cArr2;
                cArr = cArr2;
            }
            if ((b & 128) == 128) {
                int i6 = i + 1;
                cArr[i] = (char) (b & Byte.MAX_VALUE);
                return new String(cArr, 0, i6);
            }
            int i7 = i + 1;
            cArr[i] = (char) b;
            i = i7;
        }
    }

    public StringBuilder readStringBuilder() {
        int require = require(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
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
        int i2 = readUtf8Length - 1;
        if (this.chars.length < i2) {
            this.chars = new char[i2];
        }
        readUtf8(i2);
        StringBuilder sb = new StringBuilder(i2);
        sb.append(this.chars, 0, i2);
        return sb;
    }

    public float readFloat() throws KryoException {
        return Float.intBitsToFloat(readInt());
    }

    public float readFloat(float f, boolean z) throws KryoException {
        return ((float) readInt(z)) / f;
    }

    public short readShort() throws KryoException {
        require(2);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        int i3 = this.position;
        this.position = i3 + 1;
        return (short) ((bArr[i3] & 255) | i2);
    }

    public int readShortUnsigned() throws KryoException {
        require(2);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        int i3 = this.position;
        this.position = i3 + 1;
        return (bArr[i3] & 255) | i2;
    }

    public long readLong() throws KryoException {
        require(8);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        long j = ((long) bArr[i]) << 56;
        int i2 = this.position;
        this.position = i2 + 1;
        long j2 = j | (((long) (bArr[i2] & 255)) << 48);
        int i3 = this.position;
        this.position = i3 + 1;
        long j3 = j2 | (((long) (bArr[i3] & 255)) << 40);
        int i4 = this.position;
        this.position = i4 + 1;
        long j4 = j3 | (((long) (bArr[i4] & 255)) << 32);
        int i5 = this.position;
        this.position = i5 + 1;
        long j5 = j4 | (((long) (bArr[i5] & 255)) << 24);
        int i6 = this.position;
        this.position = i6 + 1;
        long j6 = j5 | ((long) ((bArr[i6] & 255) << Ascii.DLE));
        int i7 = this.position;
        this.position = i7 + 1;
        long j7 = j6 | ((long) ((bArr[i7] & 255) << 8));
        int i8 = this.position;
        this.position = i8 + 1;
        return ((long) (bArr[i8] & 255)) | j7;
    }

    public long readLong(boolean z) throws KryoException {
        return readVarLong(z);
    }

    public long readVarLong(boolean z) throws KryoException {
        if (require(1) < 9) {
            return readLong_slow(z);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
        long j = (long) (b & Byte.MAX_VALUE);
        if ((b & 128) != 0) {
            int i2 = this.position;
            this.position = i2 + 1;
            byte b2 = bArr[i2];
            j |= (long) ((b2 & Byte.MAX_VALUE) << 7);
            if ((b2 & 128) != 0) {
                int i3 = this.position;
                this.position = i3 + 1;
                byte b3 = bArr[i3];
                j |= (long) ((b3 & Byte.MAX_VALUE) << Ascii.f1876SO);
                if ((b3 & 128) != 0) {
                    int i4 = this.position;
                    this.position = i4 + 1;
                    byte b4 = bArr[i4];
                    j |= (long) ((b4 & Byte.MAX_VALUE) << Ascii.NAK);
                    if ((b4 & 128) != 0) {
                        int i5 = this.position;
                        this.position = i5 + 1;
                        byte b5 = bArr[i5];
                        j |= ((long) (b5 & Byte.MAX_VALUE)) << 28;
                        if ((b5 & 128) != 0) {
                            int i6 = this.position;
                            this.position = i6 + 1;
                            byte b6 = bArr[i6];
                            j |= ((long) (b6 & Byte.MAX_VALUE)) << 35;
                            if ((b6 & 128) != 0) {
                                int i7 = this.position;
                                this.position = i7 + 1;
                                byte b7 = bArr[i7];
                                j |= ((long) (b7 & Byte.MAX_VALUE)) << 42;
                                if ((b7 & 128) != 0) {
                                    int i8 = this.position;
                                    this.position = i8 + 1;
                                    byte b8 = bArr[i8];
                                    j |= ((long) (b8 & Byte.MAX_VALUE)) << 49;
                                    if ((b8 & 128) != 0) {
                                        int i9 = this.position;
                                        this.position = i9 + 1;
                                        j |= ((long) bArr[i9]) << 56;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return !z ? (j >>> 1) ^ (-(j & 1)) : j;
    }

    private long readLong_slow(boolean z) {
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i];
        long j = (long) (b & Byte.MAX_VALUE);
        if ((b & 128) != 0) {
            require(1);
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            byte b2 = bArr2[i2];
            j |= (long) ((b2 & Byte.MAX_VALUE) << 7);
            if ((b2 & 128) != 0) {
                require(1);
                int i3 = this.position;
                this.position = i3 + 1;
                byte b3 = bArr2[i3];
                j |= (long) ((b3 & Byte.MAX_VALUE) << Ascii.f1876SO);
                if ((b3 & 128) != 0) {
                    require(1);
                    int i4 = this.position;
                    this.position = i4 + 1;
                    byte b4 = bArr2[i4];
                    j |= (long) ((b4 & Byte.MAX_VALUE) << Ascii.NAK);
                    if ((b4 & 128) != 0) {
                        require(1);
                        int i5 = this.position;
                        this.position = i5 + 1;
                        byte b5 = bArr2[i5];
                        j |= ((long) (b5 & Byte.MAX_VALUE)) << 28;
                        if ((b5 & 128) != 0) {
                            require(1);
                            int i6 = this.position;
                            this.position = i6 + 1;
                            byte b6 = bArr2[i6];
                            j |= ((long) (b6 & Byte.MAX_VALUE)) << 35;
                            if ((b6 & 128) != 0) {
                                require(1);
                                int i7 = this.position;
                                this.position = i7 + 1;
                                byte b7 = bArr2[i7];
                                j |= ((long) (b7 & Byte.MAX_VALUE)) << 42;
                                if ((b7 & 128) != 0) {
                                    require(1);
                                    int i8 = this.position;
                                    this.position = i8 + 1;
                                    byte b8 = bArr2[i8];
                                    j |= ((long) (b8 & Byte.MAX_VALUE)) << 49;
                                    if ((b8 & 128) != 0) {
                                        require(1);
                                        int i9 = this.position;
                                        this.position = i9 + 1;
                                        j |= ((long) bArr2[i9]) << 56;
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
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        if (bArr[i] == 1) {
            return true;
        }
        return false;
    }

    public char readChar() throws KryoException {
        require(2);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        int i3 = this.position;
        this.position = i3 + 1;
        return (char) ((bArr[i3] & 255) | i2);
    }

    public double readDouble() throws KryoException {
        return Double.longBitsToDouble(readLong());
    }

    public double readDouble(double d, boolean z) throws KryoException {
        double readLong = (double) readLong(z);
        Double.isNaN(readLong);
        return readLong / d;
    }

    public int[] readInts(int i, boolean z) throws KryoException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt(z);
        }
        return iArr;
    }

    public long[] readLongs(int i, boolean z) throws KryoException {
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readLong(z);
        }
        return jArr;
    }

    public int[] readInts(int i) throws KryoException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt();
        }
        return iArr;
    }

    public long[] readLongs(int i) throws KryoException {
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readLong();
        }
        return jArr;
    }

    public float[] readFloats(int i) throws KryoException {
        float[] fArr = new float[i];
        for (int i2 = 0; i2 < i; i2++) {
            fArr[i2] = readFloat();
        }
        return fArr;
    }

    public short[] readShorts(int i) throws KryoException {
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = readShort();
        }
        return sArr;
    }

    public char[] readChars(int i) throws KryoException {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = readChar();
        }
        return cArr;
    }

    public double[] readDoubles(int i) throws KryoException {
        double[] dArr = new double[i];
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = readDouble();
        }
        return dArr;
    }
}
