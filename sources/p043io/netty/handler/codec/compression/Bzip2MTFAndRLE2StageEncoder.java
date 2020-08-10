package p043io.netty.handler.codec.compression;

/* renamed from: io.netty.handler.codec.compression.Bzip2MTFAndRLE2StageEncoder */
final class Bzip2MTFAndRLE2StageEncoder {
    private int alphabetSize;
    private final int[] bwtBlock;
    private final int bwtLength;
    private final boolean[] bwtValuesPresent;
    private final char[] mtfBlock;
    private int mtfLength;
    private final int[] mtfSymbolFrequencies = new int[258];

    Bzip2MTFAndRLE2StageEncoder(int[] iArr, int i, boolean[] zArr) {
        this.bwtBlock = iArr;
        this.bwtLength = i;
        this.bwtValuesPresent = zArr;
        this.mtfBlock = new char[(i + 1)];
    }

    /* access modifiers changed from: 0000 */
    public void encode() {
        int i;
        int i2;
        int i3 = this.bwtLength;
        boolean[] zArr = this.bwtValuesPresent;
        int[] iArr = this.bwtBlock;
        char[] cArr = this.mtfBlock;
        int[] iArr2 = this.mtfSymbolFrequencies;
        byte[] bArr = new byte[256];
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = new Bzip2MoveToFrontTable();
        char c = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < bArr.length; i5++) {
            if (zArr[i5]) {
                int i6 = i4 + 1;
                bArr[i5] = (byte) i4;
                i4 = i6;
            }
        }
        int i7 = i4 + 1;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i8 < i3) {
            int valueToFront = bzip2MoveToFrontTable.valueToFront(bArr[iArr[i8] & 255]);
            if (valueToFront == 0) {
                i9++;
            } else {
                if (i9 > 0) {
                    int i13 = i9 - 1;
                    while (true) {
                        if ((i13 & 1) == 0) {
                            i2 = i10 + 1;
                            cArr[i10] = c;
                            i11++;
                        } else {
                            i2 = i10 + 1;
                            cArr[i10] = 1;
                            i12++;
                        }
                        i10 = i2;
                        if (i13 <= 1) {
                            break;
                        }
                        i13 = (i13 - 2) >>> 1;
                    }
                    i9 = 0;
                }
                int i14 = i10 + 1;
                int i15 = valueToFront + 1;
                cArr[i10] = (char) i15;
                iArr2[i15] = iArr2[i15] + 1;
                i10 = i14;
            }
            i8++;
            c = 0;
        }
        if (i9 > 0) {
            int i16 = i9 - 1;
            while (true) {
                if ((i16 & 1) == 0) {
                    i = i10 + 1;
                    cArr[i10] = 0;
                    i11++;
                } else {
                    i = i10 + 1;
                    cArr[i10] = 1;
                    i12++;
                }
                i10 = i;
                if (i16 <= 1) {
                    break;
                }
                i16 = (i16 - 2) >>> 1;
            }
        }
        cArr[i10] = (char) i7;
        iArr2[i7] = iArr2[i7] + 1;
        iArr2[0] = iArr2[0] + i11;
        iArr2[1] = iArr2[1] + i12;
        this.mtfLength = i10 + 1;
        this.alphabetSize = i7 + 1;
    }

    /* access modifiers changed from: 0000 */
    public char[] mtfBlock() {
        return this.mtfBlock;
    }

    /* access modifiers changed from: 0000 */
    public int mtfLength() {
        return this.mtfLength;
    }

    /* access modifiers changed from: 0000 */
    public int mtfAlphabetSize() {
        return this.alphabetSize;
    }

    /* access modifiers changed from: 0000 */
    public int[] mtfSymbolFrequencies() {
        return this.mtfSymbolFrequencies;
    }
}
