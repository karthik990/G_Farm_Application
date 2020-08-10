package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import java.io.IOException;
import java.io.OutputStream;
import org.objectweb.asm.Opcodes;

/* renamed from: com.esotericsoftware.kryo.io.Output */
public class Output extends OutputStream {
    protected byte[] buffer;
    protected int capacity;
    protected int maxCapacity;
    protected OutputStream outputStream;
    protected int position;
    protected long total;

    public static int intLength(int i, boolean z) {
        if (!z) {
            i = (i >> 31) ^ (i << 1);
        }
        if ((i >>> 7) == 0) {
            return 1;
        }
        if ((i >>> 14) == 0) {
            return 2;
        }
        if ((i >>> 21) == 0) {
            return 3;
        }
        return (i >>> 28) == 0 ? 4 : 5;
    }

    public static int longLength(long j, boolean z) {
        if (!z) {
            j = (j >> 63) ^ (j << 1);
        }
        if ((j >>> 7) == 0) {
            return 1;
        }
        if ((j >>> 14) == 0) {
            return 2;
        }
        if ((j >>> 21) == 0) {
            return 3;
        }
        if ((j >>> 28) == 0) {
            return 4;
        }
        if ((j >>> 35) == 0) {
            return 5;
        }
        if ((j >>> 42) == 0) {
            return 6;
        }
        if ((j >>> 49) == 0) {
            return 7;
        }
        return (j >>> 56) == 0 ? 8 : 9;
    }

    public Output() {
    }

    public Output(int i) {
        this(i, i);
    }

    public Output(int i, int i2) {
        if (i > i2 && i2 != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("bufferSize: ");
            sb.append(i);
            sb.append(" cannot be greater than maxBufferSize: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 >= -1) {
            this.capacity = i;
            if (i2 == -1) {
                i2 = 2147483639;
            }
            this.maxCapacity = i2;
            this.buffer = new byte[i];
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("maxBufferSize cannot be < -1: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public Output(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public Output(byte[] bArr, int i) {
        if (bArr != null) {
            setBuffer(bArr, i);
            return;
        }
        throw new IllegalArgumentException("buffer cannot be null.");
    }

    public Output(OutputStream outputStream2) {
        this(4096, 4096);
        if (outputStream2 != null) {
            this.outputStream = outputStream2;
            return;
        }
        throw new IllegalArgumentException("outputStream cannot be null.");
    }

    public Output(OutputStream outputStream2, int i) {
        this(i, i);
        if (outputStream2 != null) {
            this.outputStream = outputStream2;
            return;
        }
        throw new IllegalArgumentException("outputStream cannot be null.");
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(OutputStream outputStream2) {
        this.outputStream = outputStream2;
        this.position = 0;
        this.total = 0;
    }

    public void setBuffer(byte[] bArr) {
        setBuffer(bArr, bArr.length);
    }

    public void setBuffer(byte[] bArr, int i) {
        if (bArr == null) {
            throw new IllegalArgumentException("buffer cannot be null.");
        } else if (bArr.length > i && i != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("buffer has length: ");
            sb.append(bArr.length);
            sb.append(" cannot be greater than maxBufferSize: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= -1) {
            this.buffer = bArr;
            if (i == -1) {
                i = 2147483639;
            }
            this.maxCapacity = i;
            this.capacity = bArr.length;
            this.position = 0;
            this.total = 0;
            this.outputStream = null;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("maxBufferSize cannot be < -1: ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public byte[] toBytes() {
        int i = this.position;
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, 0, bArr, 0, i);
        return bArr;
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public long total() {
        return this.total + ((long) this.position);
    }

    public void clear() {
        this.position = 0;
        this.total = 0;
    }

    /* access modifiers changed from: protected */
    public boolean require(int i) throws KryoException {
        if (this.capacity - this.position >= i) {
            return false;
        }
        String str = ", required: ";
        if (i <= this.maxCapacity) {
            flush();
            while (true) {
                int i2 = this.capacity;
                if (i2 - this.position >= i) {
                    return true;
                }
                if (i2 != this.maxCapacity) {
                    if (i2 == 0) {
                        this.capacity = 1;
                    }
                    this.capacity = Math.min(this.capacity * 2, this.maxCapacity);
                    if (this.capacity < 0) {
                        this.capacity = this.maxCapacity;
                    }
                    byte[] bArr = new byte[this.capacity];
                    System.arraycopy(this.buffer, 0, bArr, 0, this.position);
                    this.buffer = bArr;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Buffer overflow. Available: ");
                    sb.append(this.capacity - this.position);
                    sb.append(str);
                    sb.append(i);
                    throw new KryoException(sb.toString());
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Buffer overflow. Max capacity: ");
            sb2.append(this.maxCapacity);
            sb2.append(str);
            sb2.append(i);
            throw new KryoException(sb2.toString());
        }
    }

    public void flush() throws KryoException {
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 != null) {
            try {
                outputStream2.write(this.buffer, 0, this.position);
                this.outputStream.flush();
                this.total += (long) this.position;
                this.position = 0;
            } catch (IOException e) {
                throw new KryoException((Throwable) e);
            }
        }
    }

    public void close() throws KryoException {
        flush();
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 != null) {
            try {
                outputStream2.close();
            } catch (IOException unused) {
            }
        }
    }

    public void write(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) i;
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
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    public void writeByte(int i) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) i;
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
                System.arraycopy(bArr, i, this.buffer, this.position, min);
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
        byte[] bArr = this.buffer;
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        int i3 = this.position;
        this.position = i3 + 1;
        bArr[i3] = (byte) (i >> 16);
        int i4 = this.position;
        this.position = i4 + 1;
        bArr[i4] = (byte) (i >> 8);
        int i5 = this.position;
        this.position = i5 + 1;
        bArr[i5] = (byte) i;
    }

    public int writeInt(int i, boolean z) throws KryoException {
        return writeVarInt(i, z);
    }

    public int writeVarInt(int i, boolean z) throws KryoException {
        if (!z) {
            i = (i >> 31) ^ (i << 1);
        }
        int i2 = i >>> 7;
        if (i2 == 0) {
            require(1);
            byte[] bArr = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr[i3] = (byte) i;
            return 1;
        }
        int i4 = i >>> 14;
        if (i4 == 0) {
            require(2);
            byte[] bArr2 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr2[i5] = (byte) ((i & Opcodes.LAND) | 128);
            int i6 = this.position;
            this.position = i6 + 1;
            bArr2[i6] = (byte) i2;
            return 2;
        }
        int i7 = i >>> 21;
        if (i7 == 0) {
            require(3);
            byte[] bArr3 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            bArr3[i8] = (byte) ((i & Opcodes.LAND) | 128);
            int i9 = this.position;
            this.position = i9 + 1;
            bArr3[i9] = (byte) (i2 | 128);
            int i10 = this.position;
            this.position = i10 + 1;
            bArr3[i10] = (byte) i4;
            return 3;
        }
        int i11 = i >>> 28;
        if (i11 == 0) {
            require(4);
            byte[] bArr4 = this.buffer;
            int i12 = this.position;
            this.position = i12 + 1;
            bArr4[i12] = (byte) ((i & Opcodes.LAND) | 128);
            int i13 = this.position;
            this.position = i13 + 1;
            bArr4[i13] = (byte) (i2 | 128);
            int i14 = this.position;
            this.position = i14 + 1;
            bArr4[i14] = (byte) (i4 | 128);
            int i15 = this.position;
            this.position = i15 + 1;
            bArr4[i15] = (byte) i7;
            return 4;
        }
        require(5);
        byte[] bArr5 = this.buffer;
        int i16 = this.position;
        this.position = i16 + 1;
        bArr5[i16] = (byte) ((i & Opcodes.LAND) | 128);
        int i17 = this.position;
        this.position = i17 + 1;
        bArr5[i17] = (byte) (i2 | 128);
        int i18 = this.position;
        this.position = i18 + 1;
        bArr5[i18] = (byte) (i4 | 128);
        int i19 = this.position;
        this.position = i19 + 1;
        bArr5[i19] = (byte) (i7 | 128);
        int i20 = this.position;
        this.position = i20 + 1;
        bArr5[i20] = (byte) i11;
        return 5;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeString(java.lang.String r8) throws com.esotericsoftware.kryo.KryoException {
        /*
            r7 = this;
            r0 = 128(0x80, float:1.794E-43)
            if (r8 != 0) goto L_0x0008
            r7.writeByte(r0)
            return
        L_0x0008:
            int r1 = r8.length()
            if (r1 != 0) goto L_0x0014
            r8 = 129(0x81, float:1.81E-43)
            r7.writeByte(r8)
            return
        L_0x0014:
            r2 = 127(0x7f, float:1.78E-43)
            r3 = 0
            r4 = 1
            if (r1 <= r4) goto L_0x002d
            r5 = 64
            if (r1 >= r5) goto L_0x002d
            r5 = 0
        L_0x001f:
            if (r5 >= r1) goto L_0x002b
            char r6 = r8.charAt(r5)
            if (r6 <= r2) goto L_0x0028
            goto L_0x002d
        L_0x0028:
            int r5 = r5 + 1
            goto L_0x001f
        L_0x002b:
            r5 = 1
            goto L_0x002e
        L_0x002d:
            r5 = 0
        L_0x002e:
            if (r5 == 0) goto L_0x0051
            int r2 = r7.capacity
            int r5 = r7.position
            int r2 = r2 - r5
            if (r2 >= r1) goto L_0x003b
            r7.writeAscii_slow(r8, r1)
            goto L_0x0045
        L_0x003b:
            byte[] r2 = r7.buffer
            r8.getBytes(r3, r1, r2, r5)
            int r8 = r7.position
            int r8 = r8 + r1
            r7.position = r8
        L_0x0045:
            byte[] r8 = r7.buffer
            int r1 = r7.position
            int r1 = r1 - r4
            byte r2 = r8[r1]
            r0 = r0 | r2
            byte r0 = (byte) r0
            r8[r1] = r0
            goto L_0x0078
        L_0x0051:
            int r0 = r1 + 1
            r7.writeUtf8Length(r0)
            int r0 = r7.capacity
            int r4 = r7.position
            int r0 = r0 - r4
            if (r0 < r1) goto L_0x0073
            byte[] r0 = r7.buffer
        L_0x005f:
            if (r3 >= r1) goto L_0x0071
            char r5 = r8.charAt(r3)
            if (r5 <= r2) goto L_0x0068
            goto L_0x0071
        L_0x0068:
            int r6 = r4 + 1
            byte r5 = (byte) r5
            r0[r4] = r5
            int r3 = r3 + 1
            r4 = r6
            goto L_0x005f
        L_0x0071:
            r7.position = r4
        L_0x0073:
            if (r3 >= r1) goto L_0x0078
            r7.writeString_slow(r8, r1, r3)
        L_0x0078:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.p035io.Output.writeString(java.lang.String):void");
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
        int i2 = this.capacity;
        int i3 = this.position;
        if (i2 - i3 >= length) {
            byte[] bArr = this.buffer;
            while (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt > 127) {
                    break;
                }
                int i4 = i3 + 1;
                bArr[i3] = (byte) charAt;
                i++;
                i3 = i4;
            }
            this.position = i3;
        }
        if (i < length) {
            writeString_slow(charSequence, length, i);
        }
    }

    public void writeAscii(String str) throws KryoException {
        if (str == null) {
            writeByte(128);
            return;
        }
        int length = str.length();
        if (length == 0) {
            writeByte(129);
        } else if (length != 1) {
            int i = this.capacity;
            int i2 = this.position;
            if (i - i2 < length) {
                writeAscii_slow(str, length);
            } else {
                str.getBytes(0, length, this.buffer, i2);
                this.position += length;
            }
            byte[] bArr = this.buffer;
            int i3 = this.position - 1;
            bArr[i3] = (byte) (128 | bArr[i3]);
        } else {
            writeByte(130);
            writeByte((int) str.charAt(0));
        }
    }

    private void writeUtf8Length(int i) {
        int i2 = i >>> 6;
        if (i2 == 0) {
            require(1);
            byte[] bArr = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr[i3] = (byte) (i | 128);
            return;
        }
        int i4 = i >>> 13;
        if (i4 == 0) {
            require(2);
            byte[] bArr2 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr2[i5] = (byte) (i | 64 | 128);
            int i6 = this.position;
            this.position = i6 + 1;
            bArr2[i6] = (byte) i2;
            return;
        }
        int i7 = i >>> 20;
        if (i7 == 0) {
            require(3);
            byte[] bArr3 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            bArr3[i8] = (byte) (i | 64 | 128);
            int i9 = this.position;
            this.position = i9 + 1;
            bArr3[i9] = (byte) (i2 | 128);
            int i10 = this.position;
            this.position = i10 + 1;
            bArr3[i10] = (byte) i4;
            return;
        }
        int i11 = i >>> 27;
        if (i11 == 0) {
            require(4);
            byte[] bArr4 = this.buffer;
            int i12 = this.position;
            this.position = i12 + 1;
            bArr4[i12] = (byte) (i | 64 | 128);
            int i13 = this.position;
            this.position = i13 + 1;
            bArr4[i13] = (byte) (i2 | 128);
            int i14 = this.position;
            this.position = i14 + 1;
            bArr4[i14] = (byte) (i4 | 128);
            int i15 = this.position;
            this.position = i15 + 1;
            bArr4[i15] = (byte) i7;
            return;
        }
        require(5);
        byte[] bArr5 = this.buffer;
        int i16 = this.position;
        this.position = i16 + 1;
        bArr5[i16] = (byte) (i | 64 | 128);
        int i17 = this.position;
        this.position = i17 + 1;
        bArr5[i17] = (byte) (i2 | 128);
        int i18 = this.position;
        this.position = i18 + 1;
        bArr5[i18] = (byte) (i4 | 128);
        int i19 = this.position;
        this.position = i19 + 1;
        bArr5[i19] = (byte) (i7 | 128);
        int i20 = this.position;
        this.position = i20 + 1;
        bArr5[i20] = (byte) i11;
    }

    private void writeString_slow(CharSequence charSequence, int i, int i2) {
        while (i2 < i) {
            int i3 = this.position;
            int i4 = this.capacity;
            if (i3 == i4) {
                require(Math.min(i4, i - i2));
            }
            char charAt = charSequence.charAt(i2);
            if (charAt <= 127) {
                byte[] bArr = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr[i5] = (byte) charAt;
            } else if (charAt > 2047) {
                byte[] bArr2 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr2[i6] = (byte) (((charAt >> 12) & 15) | 224);
                require(2);
                byte[] bArr3 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr3[i7] = (byte) (((charAt >> 6) & 63) | 128);
                int i8 = this.position;
                this.position = i8 + 1;
                bArr3[i8] = (byte) ((charAt & '?') | 128);
            } else {
                byte[] bArr4 = this.buffer;
                int i9 = this.position;
                this.position = i9 + 1;
                bArr4[i9] = (byte) (((charAt >> 6) & 31) | 192);
                require(1);
                byte[] bArr5 = this.buffer;
                int i10 = this.position;
                this.position = i10 + 1;
                bArr5[i10] = (byte) ((charAt & '?') | 128);
            }
            i2++;
        }
    }

    private void writeAscii_slow(String str, int i) throws KryoException {
        if (i != 0) {
            if (this.capacity == 0) {
                require(1);
            }
            int i2 = 0;
            byte[] bArr = this.buffer;
            int min = Math.min(i, this.capacity - this.position);
            while (i2 < i) {
                int i3 = i2 + min;
                str.getBytes(i2, i3, bArr, this.position);
                this.position += min;
                min = Math.min(i - i3, this.capacity);
                if (require(min)) {
                    bArr = this.buffer;
                }
                i2 = i3;
            }
        }
    }

    public void writeFloat(float f) throws KryoException {
        writeInt(Float.floatToIntBits(f));
    }

    public int writeFloat(float f, float f2, boolean z) throws KryoException {
        return writeInt((int) (f * f2), z);
    }

    public void writeShort(int i) throws KryoException {
        require(2);
        byte[] bArr = this.buffer;
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) (i >>> 8);
        int i3 = this.position;
        this.position = i3 + 1;
        bArr[i3] = (byte) i;
    }

    public void writeLong(long j) throws KryoException {
        require(8);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = (byte) ((int) (j >>> 56));
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) ((int) (j >>> 48));
        int i3 = this.position;
        this.position = i3 + 1;
        bArr[i3] = (byte) ((int) (j >>> 40));
        int i4 = this.position;
        this.position = i4 + 1;
        bArr[i4] = (byte) ((int) (j >>> 32));
        int i5 = this.position;
        this.position = i5 + 1;
        bArr[i5] = (byte) ((int) (j >>> 24));
        int i6 = this.position;
        this.position = i6 + 1;
        bArr[i6] = (byte) ((int) (j >>> 16));
        int i7 = this.position;
        this.position = i7 + 1;
        bArr[i7] = (byte) ((int) (j >>> 8));
        int i8 = this.position;
        this.position = i8 + 1;
        bArr[i8] = (byte) ((int) j);
    }

    public int writeLong(long j, boolean z) throws KryoException {
        return writeVarLong(j, z);
    }

    public int writeVarLong(long j, boolean z) throws KryoException {
        long j2 = !z ? (j << 1) ^ (j >> 63) : j;
        long j3 = j2 >>> 7;
        if (j3 == 0) {
            require(1);
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) ((int) j2);
            return 1;
        }
        long j4 = j2 >>> 14;
        if (j4 == 0) {
            require(2);
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr2[i2] = (byte) ((int) ((j2 & 127) | 128));
            int i3 = this.position;
            this.position = i3 + 1;
            bArr2[i3] = (byte) ((int) j3);
            return 2;
        }
        long j5 = j3;
        long j6 = j2 >>> 21;
        if (j6 == 0) {
            require(3);
            byte[] bArr3 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr3[i4] = (byte) ((int) ((j2 & 127) | 128));
            int i5 = this.position;
            this.position = i5 + 1;
            bArr3[i5] = (byte) ((int) (j5 | 128));
            int i6 = this.position;
            this.position = i6 + 1;
            bArr3[i6] = (byte) ((int) j4);
            return 3;
        }
        long j7 = j2 >>> 28;
        if (j7 == 0) {
            require(4);
            byte[] bArr4 = this.buffer;
            int i7 = this.position;
            this.position = i7 + 1;
            bArr4[i7] = (byte) ((int) ((j2 & 127) | 128));
            int i8 = this.position;
            this.position = i8 + 1;
            bArr4[i8] = (byte) ((int) (j5 | 128));
            int i9 = this.position;
            this.position = i9 + 1;
            bArr4[i9] = (byte) ((int) (j4 | 128));
            int i10 = this.position;
            this.position = i10 + 1;
            bArr4[i10] = (byte) ((int) j6);
            return 4;
        }
        long j8 = j2 >>> 35;
        if (j8 == 0) {
            require(5);
            byte[] bArr5 = this.buffer;
            int i11 = this.position;
            this.position = i11 + 1;
            bArr5[i11] = (byte) ((int) ((j2 & 127) | 128));
            int i12 = this.position;
            this.position = i12 + 1;
            bArr5[i12] = (byte) ((int) (j5 | 128));
            int i13 = this.position;
            this.position = i13 + 1;
            bArr5[i13] = (byte) ((int) (j4 | 128));
            int i14 = this.position;
            this.position = i14 + 1;
            bArr5[i14] = (byte) ((int) (j6 | 128));
            int i15 = this.position;
            this.position = i15 + 1;
            bArr5[i15] = (byte) ((int) j7);
            return 5;
        }
        long j9 = j8;
        long j10 = j2 >>> 42;
        if (j10 == 0) {
            require(6);
            byte[] bArr6 = this.buffer;
            int i16 = this.position;
            this.position = i16 + 1;
            bArr6[i16] = (byte) ((int) ((j2 & 127) | 128));
            int i17 = this.position;
            this.position = i17 + 1;
            bArr6[i17] = (byte) ((int) (j5 | 128));
            int i18 = this.position;
            this.position = i18 + 1;
            bArr6[i18] = (byte) ((int) (j4 | 128));
            int i19 = this.position;
            this.position = i19 + 1;
            bArr6[i19] = (byte) ((int) (j6 | 128));
            int i20 = this.position;
            this.position = i20 + 1;
            bArr6[i20] = (byte) ((int) (j7 | 128));
            int i21 = this.position;
            this.position = i21 + 1;
            bArr6[i21] = (byte) ((int) j9);
            return 6;
        }
        long j11 = j10;
        long j12 = j2 >>> 49;
        if (j12 == 0) {
            require(7);
            byte[] bArr7 = this.buffer;
            int i22 = this.position;
            this.position = i22 + 1;
            bArr7[i22] = (byte) ((int) ((j2 & 127) | 128));
            int i23 = this.position;
            this.position = i23 + 1;
            bArr7[i23] = (byte) ((int) (j5 | 128));
            int i24 = this.position;
            this.position = i24 + 1;
            bArr7[i24] = (byte) ((int) (j4 | 128));
            int i25 = this.position;
            this.position = i25 + 1;
            bArr7[i25] = (byte) ((int) (j6 | 128));
            int i26 = this.position;
            this.position = i26 + 1;
            bArr7[i26] = (byte) ((int) (j7 | 128));
            int i27 = this.position;
            this.position = i27 + 1;
            bArr7[i27] = (byte) ((int) (j9 | 128));
            int i28 = this.position;
            this.position = i28 + 1;
            bArr7[i28] = (byte) ((int) j11);
            return 7;
        }
        long j13 = j12;
        long j14 = j2 >>> 56;
        if (j14 == 0) {
            require(8);
            byte[] bArr8 = this.buffer;
            int i29 = this.position;
            this.position = i29 + 1;
            bArr8[i29] = (byte) ((int) ((j2 & 127) | 128));
            int i30 = this.position;
            this.position = i30 + 1;
            bArr8[i30] = (byte) ((int) (j5 | 128));
            int i31 = this.position;
            this.position = i31 + 1;
            bArr8[i31] = (byte) ((int) (j4 | 128));
            int i32 = this.position;
            this.position = i32 + 1;
            bArr8[i32] = (byte) ((int) (j6 | 128));
            int i33 = this.position;
            this.position = i33 + 1;
            bArr8[i33] = (byte) ((int) (j7 | 128));
            int i34 = this.position;
            this.position = i34 + 1;
            bArr8[i34] = (byte) ((int) (j9 | 128));
            int i35 = this.position;
            this.position = i35 + 1;
            bArr8[i35] = (byte) ((int) (j11 | 128));
            int i36 = this.position;
            this.position = i36 + 1;
            bArr8[i36] = (byte) ((int) j13);
            return 8;
        }
        long j15 = j13;
        require(9);
        byte[] bArr9 = this.buffer;
        int i37 = this.position;
        this.position = i37 + 1;
        bArr9[i37] = (byte) ((int) ((j2 & 127) | 128));
        int i38 = this.position;
        this.position = i38 + 1;
        bArr9[i38] = (byte) ((int) (j5 | 128));
        int i39 = this.position;
        this.position = i39 + 1;
        bArr9[i39] = (byte) ((int) (j4 | 128));
        int i40 = this.position;
        this.position = i40 + 1;
        bArr9[i40] = (byte) ((int) (j6 | 128));
        int i41 = this.position;
        this.position = i41 + 1;
        bArr9[i41] = (byte) ((int) (j7 | 128));
        int i42 = this.position;
        this.position = i42 + 1;
        bArr9[i42] = (byte) ((int) (j9 | 128));
        int i43 = this.position;
        this.position = i43 + 1;
        bArr9[i43] = (byte) ((int) (j11 | 128));
        int i44 = this.position;
        this.position = i44 + 1;
        bArr9[i44] = (byte) ((int) (j15 | 128));
        int i45 = this.position;
        this.position = i45 + 1;
        bArr9[i45] = (byte) ((int) j14);
        return 9;
    }

    public void writeBoolean(boolean z) throws KryoException {
        if (this.position == this.capacity) {
            require(1);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = z ? (byte) 1 : 0;
    }

    public void writeChar(char c) throws KryoException {
        require(2);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = (byte) (c >>> 8);
        int i2 = this.position;
        this.position = i2 + 1;
        bArr[i2] = (byte) c;
    }

    public void writeDouble(double d) throws KryoException {
        writeLong(Double.doubleToLongBits(d));
    }

    public int writeDouble(double d, double d2, boolean z) throws KryoException {
        return writeLong((long) (d * d2), z);
    }

    public void writeInts(int[] iArr, boolean z) throws KryoException {
        for (int writeInt : iArr) {
            writeInt(writeInt, z);
        }
    }

    public void writeLongs(long[] jArr, boolean z) throws KryoException {
        for (long writeLong : jArr) {
            writeLong(writeLong, z);
        }
    }

    public void writeInts(int[] iArr) throws KryoException {
        for (int writeInt : iArr) {
            writeInt(writeInt);
        }
    }

    public void writeLongs(long[] jArr) throws KryoException {
        for (long writeLong : jArr) {
            writeLong(writeLong);
        }
    }

    public void writeFloats(float[] fArr) throws KryoException {
        for (float writeFloat : fArr) {
            writeFloat(writeFloat);
        }
    }

    public void writeShorts(short[] sArr) throws KryoException {
        for (short writeShort : sArr) {
            writeShort(writeShort);
        }
    }

    public void writeChars(char[] cArr) throws KryoException {
        for (char writeChar : cArr) {
            writeChar(writeChar);
        }
    }

    public void writeDoubles(double[] dArr) throws KryoException {
        for (double writeDouble : dArr) {
            writeDouble(writeDouble);
        }
    }
}
