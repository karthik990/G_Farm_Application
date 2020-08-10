package com.google.cloud.datastore.core.number;

import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import java.util.Arrays;
import org.objectweb.asm.Opcodes;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class NumberIndexEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] ENCODED_NAN = {0, 96};
    private static final byte[] ENCODED_NEGATIVE_INFINITY = {0, Byte.MIN_VALUE};
    private static final byte[] ENCODED_POSITIVE_INFINITY = {-1};
    private static final byte[] ENCODED_ZERO = {Byte.MIN_VALUE};
    private static final int EXP1_END = 4;
    private static final int EXP2_END = 20;
    private static final int EXP3_END = 148;
    private static final int EXP4_END = 1172;
    private static final int MAX_ENCODED_BYTES = 11;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class DecodedNumberParts {
        private final int bytesRead;
        private final NumberParts parts;

        private DecodedNumberParts(int i, NumberParts numberParts) {
            this.bytesRead = i;
            this.parts = numberParts;
        }

        public int bytesRead() {
            return this.bytesRead;
        }

        public NumberParts parts() {
            return this.parts;
        }

        static DecodedNumberParts create(int i, NumberParts numberParts) {
            return new DecodedNumberParts(i, numberParts);
        }
    }

    private static long decodeTrailingSignificandByte(int i, int i2) {
        return ((long) (i & 254)) << (i2 - 1);
    }

    private static int topSignificandByte(long j) {
        return ((int) (j >>> 56)) & 254;
    }

    public static byte[] encodeDouble(double d) {
        return encode(NumberParts.fromDouble(d));
    }

    public static byte[] encodeLong(long j) {
        return encode(NumberParts.fromLong(j));
    }

    public static byte[] encode(NumberParts numberParts) {
        int i;
        int i2;
        long j;
        int i3;
        byte[] bArr;
        if (numberParts.isZero()) {
            return copyOf(ENCODED_ZERO);
        }
        if (numberParts.isNaN()) {
            return copyOf(ENCODED_NAN);
        }
        if (numberParts.isInfinite()) {
            if (numberParts.negative()) {
                bArr = copyOf(ENCODED_NEGATIVE_INFINITY);
            } else {
                bArr = copyOf(ENCODED_POSITIVE_INFINITY);
            }
            return bArr;
        }
        int exponent = numberParts.exponent();
        long significand = numberParts.significand();
        byte[] bArr2 = new byte[11];
        int i4 = 0;
        int i5 = numberParts.negative() ? 255 : 0;
        if (exponent < 0) {
            exponent = -exponent;
            i = 255;
        } else {
            i = 0;
        }
        if (exponent < 4) {
            int i6 = exponent + 1;
            int i7 = 1 << i6;
            i2 = (((int) (significand >>> (64 - i6))) & (i7 - 2)) | i7 | 192;
            j = significand << exponent;
            if (i != 0) {
                i2 ^= (-1 << i6) & Opcodes.IAND;
            }
        } else if (exponent < 20) {
            bArr2[0] = (byte) (((exponent - 4) | 224) ^ ((i & Opcodes.LAND) ^ i5));
            i2 = topSignificandByte(significand);
            j = significand << 7;
            i4 = 1;
        } else {
            if (exponent < 148) {
                int i8 = exponent - 20;
                bArr2[0] = (byte) (((i8 >>> 4) | PsExtractor.VIDEO_STREAM_MASK) ^ ((i & Opcodes.LAND) ^ i5));
                significand <<= 4;
                bArr2[1] = (byte) ((((i8 << 4) & PsExtractor.VIDEO_STREAM_MASK) | ((int) (significand >>> 60))) ^ ((i & PsExtractor.VIDEO_STREAM_MASK) ^ i5));
                i3 = topSignificandByte(significand);
            } else if (exponent < EXP4_END) {
                int i9 = exponent - 148;
                bArr2[0] = (byte) ((248 | (i9 >>> 8)) ^ ((i & Opcodes.LAND) ^ i5));
                bArr2[1] = (byte) ((i9 & 255) ^ ((i & 255) ^ i5));
                i3 = topSignificandByte(significand);
            } else {
                throw new IllegalStateException("unimplemented");
            }
            i2 = i3;
            j = significand << 7;
            i4 = 2;
        }
        while (j != 0) {
            int i10 = i4 + 1;
            bArr2[i4] = (byte) ((i2 | 1) ^ i5);
            i2 = topSignificandByte(j);
            j <<= 7;
            i4 = i10;
        }
        int i11 = i4 + 1;
        bArr2[i4] = (byte) (i5 ^ i2);
        return Arrays.copyOf(bArr2, i11);
    }

    public static double decodeDouble(byte[] bArr) {
        return decode(bArr).parts().asDouble();
    }

    public static long decodeLong(byte[] bArr) {
        return decode(bArr).parts().asLong();
    }

    public static DecodedNumberParts decode(byte[] bArr) {
        int i;
        int i2;
        int i3;
        byte b;
        long j;
        byte b2;
        int i4;
        NumberParts numberParts;
        byte[] bArr2 = bArr;
        String str = "Invalid encoded byte array";
        int i5 = 1;
        if (bArr2.length >= 1) {
            byte b3 = bArr2[0] & 255;
            boolean z = (b3 & 128) == 0;
            byte b4 = z ? (byte) 255 : 0;
            byte b5 = b3 ^ b4;
            boolean z2 = (b5 & SignedBytes.MAX_POWER_OF_TWO) == 0;
            byte b6 = z2 ? (byte) 255 : 0;
            byte b7 = b5 ^ b6;
            int decodeMarker = decodeMarker(b7);
            long j2 = 0;
            int i6 = 3;
            if (decodeMarker == -4) {
                i = 64;
                if (!z2) {
                    b = b5;
                    i3 = 0;
                    i6 = 1;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid encoded number ");
                    sb.append(Arrays.toString(bArr));
                    sb.append(": exponent negative zero is invalid");
                    throw new IllegalArgumentException(sb.toString());
                }
            } else if (decodeMarker == -3 || decodeMarker == -2 || decodeMarker == -1) {
                int i7 = decodeMarker + 4;
                int i8 = 64 - i7;
                j2 = 0 | (((long) (((-1 ^ (-1 << (i7 + 1))) & 126) & b5)) << (i8 - 1));
                i = i8;
                i6 = 1;
                byte b8 = b5;
                i3 = i7;
                b = b8;
            } else {
                if (decodeMarker != 1) {
                    if (decodeMarker != 2) {
                        if (decodeMarker != 3) {
                            if (decodeMarker == 6) {
                                if (z) {
                                    if (z2) {
                                        numberParts = NumberParts.create(true, Integer.MIN_VALUE, 0);
                                    } else if (bArr2.length >= 2) {
                                        byte b9 = bArr2[1] & 255;
                                        if (b9 == 128) {
                                            numberParts = NumberParts.create(true, Integer.MAX_VALUE, 0);
                                        } else if (b9 == 96) {
                                            numberParts = NumberParts.create(true, Integer.MAX_VALUE, 1);
                                        } else {
                                            throw new IllegalArgumentException(str);
                                        }
                                        i5 = 2;
                                    } else {
                                        throw new IllegalArgumentException(str);
                                    }
                                } else if (z2) {
                                    numberParts = NumberParts.create(false, Integer.MIN_VALUE, 0);
                                } else {
                                    numberParts = NumberParts.create(false, Integer.MAX_VALUE, 0);
                                }
                                return DecodedNumberParts.create(i5, numberParts);
                            }
                            throw new IllegalArgumentException(str);
                        } else if (bArr2.length >= 3) {
                            i3 = (((b7 & 3) << 8) | (((bArr2[1] & 255) ^ b4) ^ b6)) + 148;
                            b = (bArr2[2] & 255) ^ b4;
                            j2 = 0 | decodeTrailingSignificandByte(b, 57);
                        } else {
                            throw new IllegalArgumentException(str);
                        }
                    } else if (bArr2.length >= 3) {
                        byte b10 = (bArr2[1] & 255) ^ b4;
                        i3 = (((b7 & 7) << 4) | ((b6 ^ b10) >>> 4)) + 20;
                        b = (bArr2[2] & 255) ^ b4;
                        j2 = decodeTrailingSignificandByte(b, 53) | ((((long) b10) & 15) << 60) | 0;
                        i = 53;
                    } else {
                        throw new IllegalArgumentException(str);
                    }
                } else if (bArr2.length >= 2) {
                    i3 = (b7 & Ascii.f1875SI) + 4;
                    byte b11 = (bArr2[1] & 255) ^ b4;
                    j2 = 0 | decodeTrailingSignificandByte(b11, 57);
                    b = b11;
                    i6 = 2;
                } else {
                    throw new IllegalArgumentException(str);
                }
                i = 57;
            }
            while ((b & 1) != 0) {
                if (i2 < bArr2.length) {
                    int i9 = i2 + 1;
                    byte b12 = (bArr2[i2] & 255) ^ b4;
                    int i10 = i - 7;
                    if (i10 >= 0) {
                        j = j2 | decodeTrailingSignificandByte(b12, i10);
                        i2 = i9;
                        b2 = b12;
                        i4 = i10;
                    } else {
                        j = j2 | (((long) (b12 & 254)) >>> (-(i10 - 1)));
                        if ((b12 & 1) == 0) {
                            i2 = i9;
                            b2 = b12;
                            i4 = 0;
                        } else {
                            throw new IllegalArgumentException("Invalid encoded byte array: overlong sequence");
                        }
                    }
                } else {
                    throw new IllegalArgumentException(str);
                }
            }
            if (z2) {
                i3 = -i3;
            }
            return DecodedNumberParts.create(i2, NumberParts.create(z, i3, j2));
        }
        throw new IllegalArgumentException(str);
    }

    static int decodeMarker(int i) {
        boolean z = (i & 32) != 0;
        if (z) {
            i ^= 255;
        }
        int numberOfLeadingZeros = 5 - (31 - Integer.numberOfLeadingZeros(i & 63));
        return z ? numberOfLeadingZeros : -numberOfLeadingZeros;
    }

    private static byte[] copyOf(byte[] bArr) {
        return (byte[]) bArr.clone();
    }
}
