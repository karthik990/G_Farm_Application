package org.objectweb.asm;

public class ByteVector {

    /* renamed from: a */
    byte[] f4567a;

    /* renamed from: b */
    int f4568b;

    public ByteVector() {
        this.f4567a = new byte[64];
    }

    public ByteVector(int i) {
        this.f4567a = new byte[i];
    }

    /* renamed from: a */
    private void m4151a(int i) {
        int length = this.f4567a.length * 2;
        int i2 = i + this.f4568b;
        if (length > i2) {
            i2 = length;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.f4567a, 0, bArr, 0, this.f4568b);
        this.f4567a = bArr;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public ByteVector mo76705a(int i, int i2) {
        int i3 = this.f4568b;
        if (i3 + 2 > this.f4567a.length) {
            m4151a(2);
        }
        byte[] bArr = this.f4567a;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        int i5 = i4 + 1;
        bArr[i4] = (byte) i2;
        this.f4568b = i5;
        return this;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public ByteVector mo76706b(int i, int i2) {
        int i3 = this.f4568b;
        if (i3 + 3 > this.f4567a.length) {
            m4151a(3);
        }
        byte[] bArr = this.f4567a;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i2;
        this.f4568b = i6;
        return this;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public ByteVector mo76707c(String str, int i, int i2) {
        int i3;
        int length = str.length();
        int i4 = i;
        int i5 = i4;
        while (i4 < length) {
            char charAt = str.charAt(i4);
            i5 = (charAt < 1 || charAt > 127) ? charAt > 2047 ? i5 + 3 : i5 + 2 : i5 + 1;
            i4++;
        }
        if (i5 <= i2) {
            int i6 = (this.f4568b - i) - 2;
            if (i6 >= 0) {
                byte[] bArr = this.f4567a;
                bArr[i6] = (byte) (i5 >>> 8);
                bArr[i6 + 1] = (byte) i5;
            }
            if ((this.f4568b + i5) - i > this.f4567a.length) {
                m4151a(i5 - i);
            }
            int i7 = this.f4568b;
            while (i < length) {
                char charAt2 = str.charAt(i);
                if (charAt2 < 1 || charAt2 > 127) {
                    byte[] bArr2 = this.f4567a;
                    int i8 = i7 + 1;
                    if (charAt2 > 2047) {
                        bArr2[i7] = (byte) (((charAt2 >> 12) & 15) | 224);
                        int i9 = i8 + 1;
                        bArr2[i8] = (byte) (((charAt2 >> 6) & 63) | 128);
                        i3 = i9 + 1;
                        bArr2[i9] = (byte) ((charAt2 & '?') | 128);
                    } else {
                        bArr2[i7] = (byte) (((charAt2 >> 6) & 31) | 192);
                        i7 = i8 + 1;
                        bArr2[i8] = (byte) ((charAt2 & '?') | 128);
                        i++;
                    }
                } else {
                    i3 = i7 + 1;
                    this.f4567a[i7] = (byte) charAt2;
                }
                i7 = i3;
                i++;
            }
            this.f4568b = i7;
            return this;
        }
        throw new IllegalArgumentException();
    }

    public ByteVector putByte(int i) {
        int i2 = this.f4568b;
        int i3 = i2 + 1;
        if (i3 > this.f4567a.length) {
            m4151a(1);
        }
        this.f4567a[i2] = (byte) i;
        this.f4568b = i3;
        return this;
    }

    public ByteVector putByteArray(byte[] bArr, int i, int i2) {
        if (this.f4568b + i2 > this.f4567a.length) {
            m4151a(i2);
        }
        if (bArr != null) {
            System.arraycopy(bArr, i, this.f4567a, this.f4568b, i2);
        }
        this.f4568b += i2;
        return this;
    }

    public ByteVector putInt(int i) {
        int i2 = this.f4568b;
        if (i2 + 4 > this.f4567a.length) {
            m4151a(4);
        }
        byte[] bArr = this.f4567a;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i;
        this.f4568b = i6;
        return this;
    }

    public ByteVector putLong(long j) {
        int i = this.f4568b;
        if (i + 8 > this.f4567a.length) {
            m4151a(8);
        }
        byte[] bArr = this.f4567a;
        int i2 = (int) (j >>> 32);
        int i3 = i + 1;
        bArr[i] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i2;
        int i7 = (int) j;
        int i8 = i6 + 1;
        bArr[i6] = (byte) (i7 >>> 24);
        int i9 = i8 + 1;
        bArr[i8] = (byte) (i7 >>> 16);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i7 >>> 8);
        int i11 = i10 + 1;
        bArr[i10] = (byte) i7;
        this.f4568b = i11;
        return this;
    }

    public ByteVector putShort(int i) {
        int i2 = this.f4568b;
        if (i2 + 2 > this.f4567a.length) {
            m4151a(2);
        }
        byte[] bArr = this.f4567a;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 8);
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        this.f4568b = i4;
        return this;
    }

    public ByteVector putUTF8(String str) {
        int length = str.length();
        if (length <= 65535) {
            int i = this.f4568b;
            if (i + 2 + length > this.f4567a.length) {
                m4151a(length + 2);
            }
            byte[] bArr = this.f4567a;
            int i2 = i + 1;
            bArr[i] = (byte) (length >>> 8);
            int i3 = i2 + 1;
            bArr[i2] = (byte) length;
            int i4 = 0;
            while (i4 < length) {
                char charAt = str.charAt(i4);
                if (charAt < 1 || charAt > 127) {
                    this.f4568b = i3;
                    return mo76707c(str, i4, 65535);
                }
                int i5 = i3 + 1;
                bArr[i3] = (byte) charAt;
                i4++;
                i3 = i5;
            }
            this.f4568b = i3;
            return this;
        }
        throw new IllegalArgumentException();
    }
}
