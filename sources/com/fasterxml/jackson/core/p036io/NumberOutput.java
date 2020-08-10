package com.fasterxml.jackson.core.p036io;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import org.objectweb.asm.Opcodes;

/* renamed from: com.fasterxml.jackson.core.io.NumberOutput */
public final class NumberOutput {
    private static int BILLION = 1000000000;
    private static long BILLION_L = 1000000000;
    private static long MAX_INT_AS_LONG = 2147483647L;
    private static int MILLION = 1000000;
    private static long MIN_INT_AS_LONG = -2147483648L;
    static final String SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
    static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
    private static final int[] TRIPLET_TO_CHARS = new int[1000];
    private static final String[] sSmallIntStrs = {"0", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] sSmallIntStrs2 = {"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};

    static {
        int i = 0;
        int i2 = 0;
        while (i < 10) {
            int i3 = i2;
            int i4 = 0;
            while (i4 < 10) {
                int i5 = i3;
                int i6 = 0;
                while (i6 < 10) {
                    int i7 = i5 + 1;
                    TRIPLET_TO_CHARS[i5] = ((i + 48) << 16) | ((i4 + 48) << 8) | (i6 + 48);
                    i6++;
                    i5 = i7;
                }
                i4++;
                i3 = i5;
            }
            i++;
            i2 = i3;
        }
    }

    public static int outputInt(int i, char[] cArr, int i2) {
        int i3;
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return _outputSmallestI(cArr, i2);
            }
            int i4 = i2 + 1;
            cArr[i2] = '-';
            i = -i;
            i2 = i4;
        }
        if (i >= MILLION) {
            int i5 = BILLION;
            if (i >= i5) {
                int i6 = i - i5;
                if (i6 >= i5) {
                    i6 -= i5;
                    i3 = i2 + 1;
                    cArr[i2] = '2';
                } else {
                    i3 = i2 + 1;
                    cArr[i2] = '1';
                }
                return _outputFullBillion(i6, cArr, i3);
            }
            int i7 = i / 1000;
            int i8 = i7 / 1000;
            return _full3(i - (i7 * 1000), cArr, _full3(i7 - (i8 * 1000), cArr, _leading3(i8, cArr, i2)));
        } else if (i >= 1000) {
            int i9 = i / 1000;
            return _full3(i - (i9 * 1000), cArr, _leading3(i9, cArr, i2));
        } else if (i >= 10) {
            return _leading3(i, cArr, i2);
        } else {
            cArr[i2] = (char) (i + 48);
            return i2 + 1;
        }
    }

    public static int outputInt(int i, byte[] bArr, int i2) {
        int i3;
        int i4;
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return _outputSmallestI(bArr, i2);
            }
            int i5 = i2 + 1;
            bArr[i2] = 45;
            i = -i;
            i2 = i5;
        }
        if (i < MILLION) {
            if (i >= 1000) {
                int i6 = i / 1000;
                i4 = _full3(i - (i6 * 1000), bArr, _leading3(i6, bArr, i2));
            } else if (i < 10) {
                i4 = i2 + 1;
                bArr[i2] = (byte) (i + 48);
            } else {
                i4 = _leading3(i, bArr, i2);
            }
            return i4;
        }
        int i7 = BILLION;
        if (i >= i7) {
            int i8 = i - i7;
            if (i8 >= i7) {
                i8 -= i7;
                i3 = i2 + 1;
                bArr[i2] = 50;
            } else {
                i3 = i2 + 1;
                bArr[i2] = 49;
            }
            return _outputFullBillion(i8, bArr, i3);
        }
        int i9 = i / 1000;
        int i10 = i9 / 1000;
        return _full3(i - (i9 * 1000), bArr, _full3(i9 - (i10 * 1000), bArr, _leading3(i10, bArr, i2)));
    }

    public static int outputLong(long j, char[] cArr, int i) {
        int i2;
        if (j < 0) {
            if (j > MIN_INT_AS_LONG) {
                return outputInt((int) j, cArr, i);
            }
            if (j == Long.MIN_VALUE) {
                return _outputSmallestL(cArr, i);
            }
            int i3 = i + 1;
            cArr[i] = '-';
            j = -j;
            i = i3;
        } else if (j <= MAX_INT_AS_LONG) {
            return outputInt((int) j, cArr, i);
        }
        long j2 = BILLION_L;
        long j3 = j / j2;
        long j4 = j - (j3 * j2);
        if (j3 < j2) {
            i2 = _outputUptoBillion((int) j3, cArr, i);
        } else {
            long j5 = j3 / j2;
            long j6 = j3 - (j2 * j5);
            i2 = _outputFullBillion((int) j6, cArr, _leading3((int) j5, cArr, i));
        }
        return _outputFullBillion((int) j4, cArr, i2);
    }

    public static int outputLong(long j, byte[] bArr, int i) {
        int i2;
        if (j < 0) {
            if (j > MIN_INT_AS_LONG) {
                return outputInt((int) j, bArr, i);
            }
            if (j == Long.MIN_VALUE) {
                return _outputSmallestL(bArr, i);
            }
            int i3 = i + 1;
            bArr[i] = 45;
            j = -j;
            i = i3;
        } else if (j <= MAX_INT_AS_LONG) {
            return outputInt((int) j, bArr, i);
        }
        long j2 = BILLION_L;
        long j3 = j / j2;
        long j4 = j - (j3 * j2);
        if (j3 < j2) {
            i2 = _outputUptoBillion((int) j3, bArr, i);
        } else {
            long j5 = j3 / j2;
            long j6 = j3 - (j2 * j5);
            i2 = _outputFullBillion((int) j6, bArr, _leading3((int) j5, bArr, i));
        }
        return _outputFullBillion((int) j4, bArr, i2);
    }

    public static String toString(int i) {
        String[] strArr = sSmallIntStrs;
        if (i < strArr.length) {
            if (i >= 0) {
                return strArr[i];
            }
            int i2 = (-i) - 1;
            String[] strArr2 = sSmallIntStrs2;
            if (i2 < strArr2.length) {
                return strArr2[i2];
            }
        }
        return Integer.toString(i);
    }

    public static String toString(long j) {
        if (j > 2147483647L || j < -2147483648L) {
            return Long.toString(j);
        }
        return toString((int) j);
    }

    public static String toString(double d) {
        return Double.toString(d);
    }

    public static String toString(float f) {
        return Float.toString(f);
    }

    private static int _outputUptoBillion(int i, char[] cArr, int i2) {
        if (i >= MILLION) {
            int i3 = i / 1000;
            int i4 = i - (i3 * 1000);
            int i5 = i3 / 1000;
            int i6 = i3 - (i5 * 1000);
            int _leading3 = _leading3(i5, cArr, i2);
            int[] iArr = TRIPLET_TO_CHARS;
            int i7 = iArr[i6];
            int i8 = _leading3 + 1;
            cArr[_leading3] = (char) (i7 >> 16);
            int i9 = i8 + 1;
            cArr[i8] = (char) ((i7 >> 8) & Opcodes.LAND);
            int i10 = i9 + 1;
            cArr[i9] = (char) (i7 & Opcodes.LAND);
            int i11 = iArr[i4];
            int i12 = i10 + 1;
            cArr[i10] = (char) (i11 >> 16);
            int i13 = i12 + 1;
            cArr[i12] = (char) ((i11 >> 8) & Opcodes.LAND);
            int i14 = i13 + 1;
            cArr[i13] = (char) (i11 & Opcodes.LAND);
            return i14;
        } else if (i < 1000) {
            return _leading3(i, cArr, i2);
        } else {
            int i15 = i / 1000;
            return _outputUptoMillion(cArr, i2, i15, i - (i15 * 1000));
        }
    }

    private static int _outputFullBillion(int i, char[] cArr, int i2) {
        int i3 = i / 1000;
        int i4 = i - (i3 * 1000);
        int i5 = i3 / 1000;
        int[] iArr = TRIPLET_TO_CHARS;
        int i6 = iArr[i5];
        int i7 = i2 + 1;
        cArr[i2] = (char) (i6 >> 16);
        int i8 = i7 + 1;
        cArr[i7] = (char) ((i6 >> 8) & Opcodes.LAND);
        int i9 = i8 + 1;
        cArr[i8] = (char) (i6 & Opcodes.LAND);
        int i10 = iArr[i3 - (i5 * 1000)];
        int i11 = i9 + 1;
        cArr[i9] = (char) (i10 >> 16);
        int i12 = i11 + 1;
        cArr[i11] = (char) ((i10 >> 8) & Opcodes.LAND);
        int i13 = i12 + 1;
        cArr[i12] = (char) (i10 & Opcodes.LAND);
        int i14 = iArr[i4];
        int i15 = i13 + 1;
        cArr[i13] = (char) (i14 >> 16);
        int i16 = i15 + 1;
        cArr[i15] = (char) ((i14 >> 8) & Opcodes.LAND);
        int i17 = i16 + 1;
        cArr[i16] = (char) (i14 & Opcodes.LAND);
        return i17;
    }

    private static int _outputUptoBillion(int i, byte[] bArr, int i2) {
        if (i >= MILLION) {
            int i3 = i / 1000;
            int i4 = i - (i3 * 1000);
            int i5 = i3 / 1000;
            int i6 = i3 - (i5 * 1000);
            int _leading3 = _leading3(i5, bArr, i2);
            int[] iArr = TRIPLET_TO_CHARS;
            int i7 = iArr[i6];
            int i8 = _leading3 + 1;
            bArr[_leading3] = (byte) (i7 >> 16);
            int i9 = i8 + 1;
            bArr[i8] = (byte) (i7 >> 8);
            int i10 = i9 + 1;
            bArr[i9] = (byte) i7;
            int i11 = iArr[i4];
            int i12 = i10 + 1;
            bArr[i10] = (byte) (i11 >> 16);
            int i13 = i12 + 1;
            bArr[i12] = (byte) (i11 >> 8);
            int i14 = i13 + 1;
            bArr[i13] = (byte) i11;
            return i14;
        } else if (i < 1000) {
            return _leading3(i, bArr, i2);
        } else {
            int i15 = i / 1000;
            return _outputUptoMillion(bArr, i2, i15, i - (i15 * 1000));
        }
    }

    private static int _outputFullBillion(int i, byte[] bArr, int i2) {
        int i3 = i / 1000;
        int i4 = i - (i3 * 1000);
        int i5 = i3 / 1000;
        int i6 = i3 - (i5 * 1000);
        int[] iArr = TRIPLET_TO_CHARS;
        int i7 = iArr[i5];
        int i8 = i2 + 1;
        bArr[i2] = (byte) (i7 >> 16);
        int i9 = i8 + 1;
        bArr[i8] = (byte) (i7 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) i7;
        int i11 = iArr[i6];
        int i12 = i10 + 1;
        bArr[i10] = (byte) (i11 >> 16);
        int i13 = i12 + 1;
        bArr[i12] = (byte) (i11 >> 8);
        int i14 = i13 + 1;
        bArr[i13] = (byte) i11;
        int i15 = iArr[i4];
        int i16 = i14 + 1;
        bArr[i14] = (byte) (i15 >> 16);
        int i17 = i16 + 1;
        bArr[i16] = (byte) (i15 >> 8);
        int i18 = i17 + 1;
        bArr[i17] = (byte) i15;
        return i18;
    }

    private static int _outputUptoMillion(char[] cArr, int i, int i2, int i3) {
        int i4 = TRIPLET_TO_CHARS[i2];
        if (i2 > 9) {
            if (i2 > 99) {
                int i5 = i + 1;
                cArr[i] = (char) (i4 >> 16);
                i = i5;
            }
            int i6 = i + 1;
            cArr[i] = (char) ((i4 >> 8) & Opcodes.LAND);
            i = i6;
        }
        int i7 = i + 1;
        cArr[i] = (char) (i4 & Opcodes.LAND);
        int i8 = TRIPLET_TO_CHARS[i3];
        int i9 = i7 + 1;
        cArr[i7] = (char) (i8 >> 16);
        int i10 = i9 + 1;
        cArr[i9] = (char) ((i8 >> 8) & Opcodes.LAND);
        int i11 = i10 + 1;
        cArr[i10] = (char) (i8 & Opcodes.LAND);
        return i11;
    }

    private static int _outputUptoMillion(byte[] bArr, int i, int i2, int i3) {
        int i4 = TRIPLET_TO_CHARS[i2];
        if (i2 > 9) {
            if (i2 > 99) {
                int i5 = i + 1;
                bArr[i] = (byte) (i4 >> 16);
                i = i5;
            }
            int i6 = i + 1;
            bArr[i] = (byte) (i4 >> 8);
            i = i6;
        }
        int i7 = i + 1;
        bArr[i] = (byte) i4;
        int i8 = TRIPLET_TO_CHARS[i3];
        int i9 = i7 + 1;
        bArr[i7] = (byte) (i8 >> 16);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 8);
        int i11 = i10 + 1;
        bArr[i10] = (byte) i8;
        return i11;
    }

    private static int _leading3(int i, char[] cArr, int i2) {
        int i3;
        int i4 = TRIPLET_TO_CHARS[i];
        if (i > 9) {
            if (i > 99) {
                i3 = i2 + 1;
                cArr[i2] = (char) (i4 >> 16);
            } else {
                i3 = i2;
            }
            i2 = i3 + 1;
            cArr[i3] = (char) ((i4 >> 8) & Opcodes.LAND);
        }
        int i5 = i2 + 1;
        cArr[i2] = (char) (i4 & Opcodes.LAND);
        return i5;
    }

    private static int _leading3(int i, byte[] bArr, int i2) {
        int i3;
        int i4 = TRIPLET_TO_CHARS[i];
        if (i > 9) {
            if (i > 99) {
                i3 = i2 + 1;
                bArr[i2] = (byte) (i4 >> 16);
            } else {
                i3 = i2;
            }
            i2 = i3 + 1;
            bArr[i3] = (byte) (i4 >> 8);
        }
        int i5 = i2 + 1;
        bArr[i2] = (byte) i4;
        return i5;
    }

    private static int _full3(int i, char[] cArr, int i2) {
        int i3 = TRIPLET_TO_CHARS[i];
        int i4 = i2 + 1;
        cArr[i2] = (char) (i3 >> 16);
        int i5 = i4 + 1;
        cArr[i4] = (char) ((i3 >> 8) & Opcodes.LAND);
        int i6 = i5 + 1;
        cArr[i5] = (char) (i3 & Opcodes.LAND);
        return i6;
    }

    private static int _full3(int i, byte[] bArr, int i2) {
        int i3 = TRIPLET_TO_CHARS[i];
        int i4 = i2 + 1;
        bArr[i2] = (byte) (i3 >> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i3 >> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i3;
        return i6;
    }

    private static int _outputSmallestL(char[] cArr, int i) {
        int length = SMALLEST_LONG.length();
        SMALLEST_LONG.getChars(0, length, cArr, i);
        return i + length;
    }

    private static int _outputSmallestL(byte[] bArr, int i) {
        int length = SMALLEST_LONG.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            bArr[i] = (byte) SMALLEST_LONG.charAt(i2);
            i2++;
            i = i3;
        }
        return i;
    }

    private static int _outputSmallestI(char[] cArr, int i) {
        int length = SMALLEST_INT.length();
        SMALLEST_INT.getChars(0, length, cArr, i);
        return i + length;
    }

    private static int _outputSmallestI(byte[] bArr, int i) {
        int length = SMALLEST_INT.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            bArr[i] = (byte) SMALLEST_INT.charAt(i2);
            i2++;
            i = i3;
        }
        return i;
    }
}
