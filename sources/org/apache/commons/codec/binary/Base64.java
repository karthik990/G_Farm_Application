package org.apache.commons.codec.binary;

import com.google.common.base.Ascii;
import java.math.BigInteger;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;

public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    static final byte[] CHUNK_SEPARATOR = {Ascii.f1866CR, 10};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.f1879VT, Ascii.f1868FF, Ascii.f1866CR, Ascii.f1876SO, Ascii.f1875SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f1867EM, -1, -1, -1, -1, 63, -1, Ascii.SUB, Ascii.ESC, Ascii.f1869FS, Ascii.f1870GS, Ascii.f1874RS, Ascii.f1878US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, ClassDefinitionUtils.OPS_aload_0, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    private static final int MASK_6BITS = 63;
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, ClassDefinitionUtils.OPS_dup, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, ClassDefinitionUtils.OPS_dup, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base64() {
        this(0);
    }

    public Base64(boolean z) {
        this(76, CHUNK_SEPARATOR, z);
    }

    public Base64(int i) {
        this(i, CHUNK_SEPARATOR);
    }

    public Base64(int i, byte[] bArr) {
        this(i, bArr, false);
    }

    public Base64(int i, byte[] bArr, boolean z) {
        super(3, 4, i, bArr == null ? 0 : bArr.length);
        this.decodeTable = DECODE_TABLE;
        if (bArr == null) {
            this.encodeSize = 4;
            this.lineSeparator = null;
        } else if (containsAlphabetOrPad(bArr)) {
            String newStringUtf8 = StringUtils.newStringUtf8(bArr);
            StringBuilder sb = new StringBuilder();
            sb.append("lineSeparator must not contain base64 characters: [");
            sb.append(newStringUtf8);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        } else if (i > 0) {
            this.encodeSize = bArr.length + 4;
            this.lineSeparator = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.lineSeparator, 0, bArr.length);
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = z ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    /* JADX WARNING: type inference failed for: r2v26 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r2v2, types: [int, byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(byte[] r8, int r9, int r10, org.apache.commons.codec.binary.BaseNCodec.Context r11) {
        /*
            r7 = this;
            boolean r0 = r11.eof
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 1
            if (r10 >= 0) goto L_0x00e7
            r11.eof = r1
            int r8 = r11.modulus
            if (r8 != 0) goto L_0x0014
            int r8 = r7.lineLength
            if (r8 != 0) goto L_0x0014
            return
        L_0x0014:
            int r8 = r7.encodeSize
            byte[] r8 = r7.ensureBufferSize(r8, r11)
            int r9 = r11.pos
            int r10 = r11.modulus
            if (r10 == 0) goto L_0x00c3
            r2 = 2
            if (r10 == r1) goto L_0x0085
            if (r10 != r2) goto L_0x006c
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte[] r1 = r7.encodeTable
            int r3 = r11.ibitWorkArea
            int r3 = r3 >> 10
            r3 = r3 & 63
            byte r1 = r1[r3]
            r8[r10] = r1
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte[] r1 = r7.encodeTable
            int r3 = r11.ibitWorkArea
            int r3 = r3 >> 4
            r3 = r3 & 63
            byte r1 = r1[r3]
            r8[r10] = r1
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte[] r1 = r7.encodeTable
            int r3 = r11.ibitWorkArea
            int r2 = r3 << 2
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            byte[] r10 = r7.encodeTable
            byte[] r1 = STANDARD_ENCODE_TABLE
            if (r10 != r1) goto L_0x00c3
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte r1 = r7.pad
            r8[r10] = r1
            goto L_0x00c3
        L_0x006c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Impossible modulus "
            r9.append(r10)
            int r10 = r11.modulus
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0085:
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte[] r1 = r7.encodeTable
            int r3 = r11.ibitWorkArea
            int r2 = r3 >> 2
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte[] r1 = r7.encodeTable
            int r2 = r11.ibitWorkArea
            int r2 = r2 << 4
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            byte[] r10 = r7.encodeTable
            byte[] r1 = STANDARD_ENCODE_TABLE
            if (r10 != r1) goto L_0x00c3
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte r1 = r7.pad
            r8[r10] = r1
            int r10 = r11.pos
            int r1 = r10 + 1
            r11.pos = r1
            byte r1 = r7.pad
            r8[r10] = r1
        L_0x00c3:
            int r10 = r11.currentLinePos
            int r1 = r11.pos
            int r1 = r1 - r9
            int r10 = r10 + r1
            r11.currentLinePos = r10
            int r9 = r7.lineLength
            if (r9 <= 0) goto L_0x017a
            int r9 = r11.currentLinePos
            if (r9 <= 0) goto L_0x017a
            byte[] r9 = r7.lineSeparator
            int r10 = r11.pos
            byte[] r1 = r7.lineSeparator
            int r1 = r1.length
            java.lang.System.arraycopy(r9, r0, r8, r10, r1)
            int r8 = r11.pos
            byte[] r9 = r7.lineSeparator
            int r9 = r9.length
            int r8 = r8 + r9
            r11.pos = r8
            goto L_0x017a
        L_0x00e7:
            r2 = r9
            r9 = 0
        L_0x00e9:
            if (r9 >= r10) goto L_0x017a
            int r3 = r7.encodeSize
            byte[] r3 = r7.ensureBufferSize(r3, r11)
            int r4 = r11.modulus
            int r4 = r4 + r1
            int r4 = r4 % 3
            r11.modulus = r4
            int r4 = r2 + 1
            byte r2 = r8[r2]
            if (r2 >= 0) goto L_0x0100
            int r2 = r2 + 256
        L_0x0100:
            int r5 = r11.ibitWorkArea
            int r5 = r5 << 8
            int r5 = r5 + r2
            r11.ibitWorkArea = r5
            int r2 = r11.modulus
            if (r2 != 0) goto L_0x0175
            int r2 = r11.pos
            int r5 = r2 + 1
            r11.pos = r5
            byte[] r5 = r7.encodeTable
            int r6 = r11.ibitWorkArea
            int r6 = r6 >> 18
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.pos
            int r5 = r2 + 1
            r11.pos = r5
            byte[] r5 = r7.encodeTable
            int r6 = r11.ibitWorkArea
            int r6 = r6 >> 12
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.pos
            int r5 = r2 + 1
            r11.pos = r5
            byte[] r5 = r7.encodeTable
            int r6 = r11.ibitWorkArea
            int r6 = r6 >> 6
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.pos
            int r5 = r2 + 1
            r11.pos = r5
            byte[] r5 = r7.encodeTable
            int r6 = r11.ibitWorkArea
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.currentLinePos
            int r2 = r2 + 4
            r11.currentLinePos = r2
            int r2 = r7.lineLength
            if (r2 <= 0) goto L_0x0175
            int r2 = r7.lineLength
            int r5 = r11.currentLinePos
            if (r2 > r5) goto L_0x0175
            byte[] r2 = r7.lineSeparator
            int r5 = r11.pos
            byte[] r6 = r7.lineSeparator
            int r6 = r6.length
            java.lang.System.arraycopy(r2, r0, r3, r5, r6)
            int r2 = r11.pos
            byte[] r3 = r7.lineSeparator
            int r3 = r3.length
            int r2 = r2 + r3
            r11.pos = r2
            r11.currentLinePos = r0
        L_0x0175:
            int r9 = r9 + 1
            r2 = r4
            goto L_0x00e9
        L_0x017a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.Base64.encode(byte[], int, int, org.apache.commons.codec.binary.BaseNCodec$Context):void");
    }

    /* access modifiers changed from: 0000 */
    public void decode(byte[] bArr, int i, int i2, Context context) {
        if (!context.eof) {
            if (i2 < 0) {
                context.eof = true;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    break;
                }
                byte[] ensureBufferSize = ensureBufferSize(this.decodeSize, context);
                int i4 = i + 1;
                byte b = bArr[i];
                if (b == this.pad) {
                    context.eof = true;
                    break;
                }
                if (b >= 0) {
                    byte[] bArr2 = DECODE_TABLE;
                    if (b < bArr2.length) {
                        byte b2 = bArr2[b];
                        if (b2 >= 0) {
                            context.modulus = (context.modulus + 1) % 4;
                            context.ibitWorkArea = (context.ibitWorkArea << 6) + b2;
                            if (context.modulus == 0) {
                                int i5 = context.pos;
                                context.pos = i5 + 1;
                                ensureBufferSize[i5] = (byte) ((context.ibitWorkArea >> 16) & 255);
                                int i6 = context.pos;
                                context.pos = i6 + 1;
                                ensureBufferSize[i6] = (byte) ((context.ibitWorkArea >> 8) & 255);
                                int i7 = context.pos;
                                context.pos = i7 + 1;
                                ensureBufferSize[i7] = (byte) (context.ibitWorkArea & 255);
                            }
                        }
                    }
                }
                i3++;
                i = i4;
            }
            if (context.eof && context.modulus != 0) {
                byte[] ensureBufferSize2 = ensureBufferSize(this.decodeSize, context);
                int i8 = context.modulus;
                if (i8 != 1) {
                    if (i8 == 2) {
                        context.ibitWorkArea >>= 4;
                        int i9 = context.pos;
                        context.pos = i9 + 1;
                        ensureBufferSize2[i9] = (byte) (context.ibitWorkArea & 255);
                    } else if (i8 == 3) {
                        context.ibitWorkArea >>= 2;
                        int i10 = context.pos;
                        context.pos = i10 + 1;
                        ensureBufferSize2[i10] = (byte) ((context.ibitWorkArea >> 8) & 255);
                        int i11 = context.pos;
                        context.pos = i11 + 1;
                        ensureBufferSize2[i11] = (byte) (context.ibitWorkArea & 255);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Impossible modulus ");
                        sb.append(context.modulus);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            }
        }
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] bArr) {
        return isBase64(bArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000e, code lost:
        if (r0[r2] != -1) goto L_0x0013;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isBase64(byte r2) {
        /*
            r0 = 61
            if (r2 == r0) goto L_0x0013
            if (r2 < 0) goto L_0x0011
            byte[] r0 = DECODE_TABLE
            int r1 = r0.length
            if (r2 >= r1) goto L_0x0011
            byte r2 = r0[r2]
            r0 = -1
            if (r2 == r0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r2 = 0
            goto L_0x0014
        L_0x0013:
            r2 = 1
        L_0x0014:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.Base64.isBase64(byte):boolean");
    }

    public static boolean isBase64(String str) {
        return isBase64(StringUtils.getBytesUtf8(str));
    }

    public static boolean isBase64(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (!isBase64(bArr[i]) && !isWhiteSpace(bArr[i])) {
                return false;
            }
        }
        return true;
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return encodeBase64(bArr, false);
    }

    public static String encodeBase64String(byte[] bArr) {
        return StringUtils.newStringUsAscii(encodeBase64(bArr, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] bArr) {
        return encodeBase64(bArr, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] bArr) {
        return StringUtils.newStringUsAscii(encodeBase64(bArr, false, true));
    }

    public static byte[] encodeBase64Chunked(byte[] bArr) {
        return encodeBase64(bArr, true);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z) {
        return encodeBase64(bArr, z, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2) {
        return encodeBase64(bArr, z, z2, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2, int i) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        Base64 base64 = z ? new Base64(z2) : new Base64(0, CHUNK_SEPARATOR, z2);
        long encodedLength = base64.getEncodedLength(bArr);
        if (encodedLength <= ((long) i)) {
            return base64.encode(bArr);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Input array too big, the output array would be bigger (");
        sb.append(encodedLength);
        sb.append(") than the specified maximum size of ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public static byte[] decodeBase64(String str) {
        return new Base64().decode(str);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return new Base64().decode(bArr);
    }

    public static BigInteger decodeInteger(byte[] bArr) {
        return new BigInteger(1, decodeBase64(bArr));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger != null) {
            return encodeBase64(toIntegerBytes(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    static byte[] toIntegerBytes(BigInteger bigInteger) {
        int bitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == bitLength / 8) {
            return byteArray;
        }
        int i = 0;
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
            i = 1;
        }
        int i2 = bitLength / 8;
        int i3 = i2 - length;
        byte[] bArr = new byte[i2];
        System.arraycopy(byteArray, i, bArr, i3, length);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public boolean isInAlphabet(byte b) {
        if (b >= 0) {
            byte[] bArr = this.decodeTable;
            if (b < bArr.length && bArr[b] != -1) {
                return true;
            }
        }
        return false;
    }
}
