package com.rometools.rome.p052io.impl;

import com.google.common.base.Ascii;
import com.mobiroller.constants.Constants;

/* renamed from: com.rometools.rome.io.impl.Base64 */
public class Base64 {
    private static final byte[] ALPHASET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".getBytes();
    private static final int[] CODES = new int[256];
    private static final int I2O6 = 192;
    private static final int I4O4 = 240;
    private static final int I6O2 = 252;
    private static final int O2I6 = 63;
    private static final int O4I4 = 15;
    private static final int O6I2 = 3;

    public static String encode(String str) {
        return new String(encode(str.getBytes()));
    }

    public static String decode(String str) throws IllegalArgumentException {
        String str2 = "";
        return new String(decode(str.replaceAll(Constants.NEW_LINE, str2).replaceAll("\r", str2).getBytes()));
    }

    static {
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = CODES;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = 64;
            i2++;
        }
        while (true) {
            byte[] bArr = ALPHASET;
            if (i < bArr.length) {
                CODES[bArr[i]] = i;
                i++;
            } else {
                return;
            }
        }
    }

    public static byte[] encode(byte[] bArr) {
        char c;
        byte b;
        byte b2;
        int i;
        if (bArr != null) {
            byte[] bArr2 = new byte[(((bArr.length + 2) / 3) * 4)];
            int i2 = 0;
            for (int i3 = 0; i3 < bArr.length; i3 += 3) {
                byte b3 = bArr[i3];
                int i4 = i3 + 1;
                if (i4 < bArr.length) {
                    b2 = bArr[i4];
                    int i5 = i3 + 2;
                    if (i5 < bArr.length) {
                        b = bArr[i5];
                        c = 0;
                    } else {
                        b = 0;
                        c = 1;
                    }
                } else {
                    b2 = 0;
                    b = 0;
                    c = 2;
                }
                byte[] bArr3 = ALPHASET;
                byte b4 = bArr3[(b3 & 252) >> 2];
                byte b5 = bArr3[((b3 & 3) << 4) | ((b2 & 240) >> 4)];
                byte b6 = bArr3[((b2 & Ascii.f1875SI) << 2) | ((b & 192) >> 6)];
                byte b7 = bArr3[b & 63];
                int i6 = i2 + 1;
                bArr2[i2] = (byte) b4;
                int i7 = i6 + 1;
                bArr2[i6] = (byte) b5;
                if (c < 2) {
                    i = i7 + 1;
                    bArr2[i7] = (byte) b6;
                } else {
                    i = i7 + 1;
                    bArr2[i7] = 61;
                }
                if (c < 1) {
                    i2 = i + 1;
                    bArr2[i] = (byte) b7;
                } else {
                    i2 = i + 1;
                    bArr2[i] = 61;
                }
            }
            return bArr2;
        }
        throw new IllegalArgumentException("Cannot encode null");
    }

    public static byte[] decode(byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = (byte[]) bArr.clone();
            int i = 0;
            int i2 = 0;
            for (byte b : bArr) {
                if (b < 256 && CODES[b] < 64) {
                    int i3 = i2 + 1;
                    bArr2[i2] = b;
                    i2 = i3;
                }
            }
            int i4 = (i2 / 4) * 3;
            int i5 = i2 % 4;
            if (i5 == 2) {
                i4++;
            } else if (i5 == 3) {
                i4 += 2;
            }
            byte[] bArr3 = new byte[i4];
            int i6 = 0;
            while (i < bArr.length) {
                int i7 = i + 3;
                if (i7 <= bArr.length) {
                    int[] iArr = CODES;
                    int i8 = iArr[bArr2[i]];
                    int i9 = iArr[bArr2[i + 1]];
                    int i10 = iArr[bArr2[i + 2]];
                    int i11 = iArr[bArr2[i7]];
                    int i12 = i6 + 1;
                    bArr3[i6] = (byte) ((i8 << 2) | (i9 >> 4));
                    if (i12 < bArr3.length) {
                        i6 = i12 + 1;
                        bArr3[i12] = (byte) ((i9 << 4) | (i10 >> 2));
                    } else {
                        i6 = i12;
                    }
                    if (i6 < bArr3.length) {
                        int i13 = i6 + 1;
                        bArr3[i6] = (byte) (i11 | (i10 << 6));
                        i6 = i13;
                    }
                    i += 4;
                } else {
                    throw new IllegalArgumentException("byte array is not a valid com.rometools.rome.io.impl.Base64 encoding");
                }
            }
            return bArr3;
        }
        throw new IllegalArgumentException("Cannot decode null");
    }

    public static void main(String[] strArr) throws Exception {
        System.out.println(decode("\nPGRpdiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94aHRtbCI+V2UncmUgcHJvcG9zaW5nIDxhIGhy\nZWY9Imh0dHA6Ly93d3cuZ29vZ2xlLmNvbS9jb3Jwb3JhdGUvc29mdHdhcmVfcHJpbmNpcGxlcy5odG1sIj5z\nb21lIGd1aWRlbGluZXMgPC9hPnRvIGhlbHAgY3VyYiB0aGUgcHJvYmxlbSBvZiBJbnRlcm5ldCBzb2Z0d2Fy\nZSB0aGF0IGluc3RhbGxzIGl0c2VsZiB3aXRob3V0IHRlbGxpbmcgeW91LCBvciBiZWhhdmVzIGJhZGx5IG9u\nY2UgaXQgZ2V0cyBvbiB5b3VyIGNvbXB1dGVyLiBXZSd2ZSBiZWVuIGhlYXJpbmcgYSBsb3Qgb2YgY29tcGxh\naW50cyBhYm91dCB0aGlzIGxhdGVseSBhbmQgaXQgc2VlbXMgdG8gYmUgZ2V0dGluZyB3b3JzZS4gV2UgdGhp\nbmsgaXQncyBpbXBvcnRhbnQgdGhhdCB5b3UgcmV0YWluIGNvbnRyb2wgb2YgeW91ciBjb21wdXRlciBhbmQg\ndGhhdCB0aGVyZSBiZSBzb21lIGNsZWFyIHN0YW5kYXJkcyBpbiBvdXIgaW5kdXN0cnkuIExldCB1cyBrbm93\nIGlmIHlvdSB0aGluayB0aGVzZSBndWlkZWxpbmVzIGFyZSB1c2VmdWwgb3IgaWYgeW91IGhhdmUgc3VnZ2Vz\ndGlvbnMgdG8gaW1wcm92ZSB0aGVtLgo8YnIgLz4KPGJyIC8+Sm9uYXRoYW4gUm9zZW5iZXJnCjxiciAvPgo8\nL2Rpdj4K\n"));
    }
}
