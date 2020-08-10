package p043io.netty.handler.codec.compression;

import androidx.core.app.FrameMetricsAggregator;
import java.lang.reflect.Array;
import java.util.Arrays;
import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.compression.Bzip2HuffmanStageEncoder */
final class Bzip2HuffmanStageEncoder {
    private static final int HUFFMAN_HIGH_SYMBOL_COST = 15;
    private final int[][] huffmanCodeLengths;
    private final int[][] huffmanMergedCodeSymbols;
    private final int mtfAlphabetSize;
    private final char[] mtfBlock;
    private final int mtfLength;
    private final int[] mtfSymbolFrequencies;
    private final byte[] selectors;
    private final Bzip2BitWriter writer;

    private static int selectTableCount(int i) {
        if (i >= 2400) {
            return 6;
        }
        if (i >= 1200) {
            return 5;
        }
        if (i >= 600) {
            return 4;
        }
        return i >= 200 ? 3 : 2;
    }

    Bzip2HuffmanStageEncoder(Bzip2BitWriter bzip2BitWriter, char[] cArr, int i, int i2, int[] iArr) {
        Class<int> cls = int.class;
        this.writer = bzip2BitWriter;
        this.mtfBlock = cArr;
        this.mtfLength = i;
        this.mtfAlphabetSize = i2;
        this.mtfSymbolFrequencies = iArr;
        int selectTableCount = selectTableCount(i);
        this.huffmanCodeLengths = (int[][]) Array.newInstance(cls, new int[]{selectTableCount, i2});
        this.huffmanMergedCodeSymbols = (int[][]) Array.newInstance(cls, new int[]{selectTableCount, i2});
        this.selectors = new byte[(((i + 50) - 1) / 50)];
    }

    private static void generateHuffmanCodeLengths(int i, int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[i];
        int[] iArr4 = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr3[i2] = (iArr[i2] << 9) | i2;
        }
        Arrays.sort(iArr3);
        for (int i3 = 0; i3 < i; i3++) {
            iArr4[i3] = iArr3[i3] >>> 9;
        }
        Bzip2HuffmanAllocator.allocateHuffmanCodeLengths(iArr4, 20);
        for (int i4 = 0; i4 < i; i4++) {
            iArr2[iArr3[i4] & FrameMetricsAggregator.EVERY_DURATION] = iArr4[i4];
        }
    }

    private void generateHuffmanOptimisationSeeds() {
        int i;
        int[][] iArr = this.huffmanCodeLengths;
        int[] iArr2 = this.mtfSymbolFrequencies;
        int i2 = this.mtfAlphabetSize;
        int length = iArr.length;
        int i3 = this.mtfLength;
        int i4 = -1;
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = length - i5;
            int i7 = i3 / i6;
            int i8 = i4 + 1;
            int i9 = i4;
            int i10 = 0;
            while (i10 < i7 && i9 < i2 - 1) {
                i9++;
                i10 += iArr2[i9];
            }
            if (i9 <= i8 || i5 == 0 || i5 == length - 1 || (i6 & 1) != 0) {
                i = i10;
                i4 = i9;
            } else {
                int i11 = i9 - 1;
                i = i10 - iArr2[i9];
                i4 = i11;
            }
            int[] iArr3 = iArr[i5];
            for (int i12 = 0; i12 < i2; i12++) {
                if (i12 < i8 || i12 > i4) {
                    iArr3[i12] = 15;
                }
            }
            i3 -= i;
        }
    }

    private void optimiseSelectorsAndHuffmanTables(boolean z) {
        char[] cArr = this.mtfBlock;
        byte[] bArr = this.selectors;
        int[][] iArr = this.huffmanCodeLengths;
        int i = this.mtfLength;
        int i2 = this.mtfAlphabetSize;
        int length = iArr.length;
        int[][] iArr2 = (int[][]) Array.newInstance(int.class, new int[]{length, i2});
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int min = Math.min(i3 + 50, i) - 1;
            short[] sArr = new short[length];
            for (int i5 = i3; i5 <= min; i5++) {
                char c = cArr[i5];
                for (int i6 = 0; i6 < length; i6++) {
                    sArr[i6] = (short) (sArr[i6] + iArr[i6][c]);
                }
            }
            short s = sArr[0];
            byte b = 0;
            for (byte b2 = 1; b2 < length; b2 = (byte) (b2 + 1)) {
                short s2 = sArr[b2];
                if (s2 < s) {
                    s = s2;
                    b = b2;
                }
            }
            int[] iArr3 = iArr2[b];
            while (i3 <= min) {
                char c2 = cArr[i3];
                iArr3[c2] = iArr3[c2] + 1;
                i3++;
            }
            if (z) {
                int i7 = i4 + 1;
                bArr[i4] = b;
                i4 = i7;
            }
            i3 = min + 1;
        }
        for (int i8 = 0; i8 < length; i8++) {
            generateHuffmanCodeLengths(i2, iArr2[i8], iArr[i8]);
        }
    }

    private void assignHuffmanCodeSymbols() {
        int[][] iArr = this.huffmanMergedCodeSymbols;
        int[][] iArr2 = this.huffmanCodeLengths;
        int i = this.mtfAlphabetSize;
        int length = iArr2.length;
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr3 = iArr2[i2];
            int i3 = 32;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = iArr3[i5];
                if (i6 > i4) {
                    i4 = i6;
                }
                if (i6 < i3) {
                    i3 = i6;
                }
            }
            int i7 = 0;
            while (i3 <= i4) {
                int i8 = i7;
                for (int i9 = 0; i9 < i; i9++) {
                    if ((iArr2[i2][i9] & 255) == i3) {
                        iArr[i2][i9] = (i3 << 24) | i8;
                        i8++;
                    }
                }
                i7 = i8 << 1;
                i3++;
            }
        }
    }

    private void writeSelectorsAndHuffmanTables(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = byteBuf;
        Bzip2BitWriter bzip2BitWriter = this.writer;
        byte[] bArr = this.selectors;
        int length = bArr.length;
        int[][] iArr = this.huffmanCodeLengths;
        int length2 = iArr.length;
        int i = this.mtfAlphabetSize;
        bzip2BitWriter.writeBits(byteBuf2, 3, (long) length2);
        bzip2BitWriter.writeBits(byteBuf2, 15, (long) length);
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = new Bzip2MoveToFrontTable();
        for (byte valueToFront : bArr) {
            bzip2BitWriter.writeUnary(byteBuf2, bzip2MoveToFrontTable.valueToFront(valueToFront));
        }
        for (int[] iArr2 : iArr) {
            int i2 = iArr2[0];
            bzip2BitWriter.writeBits(byteBuf2, 5, (long) i2);
            int i3 = i2;
            int i4 = 0;
            while (i4 < i) {
                int i5 = iArr2[i4];
                int i6 = i3 < i5 ? 2 : 3;
                int abs = Math.abs(i5 - i3);
                while (true) {
                    int i7 = abs - 1;
                    if (abs <= 0) {
                        break;
                    }
                    int i8 = i;
                    bzip2BitWriter.writeBits(byteBuf2, 2, (long) i6);
                    i = i8;
                    abs = i7;
                }
                int i9 = i;
                bzip2BitWriter.writeBoolean(byteBuf2, false);
                i4++;
                i3 = i5;
            }
            int i10 = i;
        }
    }

    private void writeBlockData(ByteBuf byteBuf) {
        Bzip2BitWriter bzip2BitWriter = this.writer;
        int[][] iArr = this.huffmanMergedCodeSymbols;
        byte[] bArr = this.selectors;
        char[] cArr = this.mtfBlock;
        int i = this.mtfLength;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int min = Math.min(i2 + 50, i) - 1;
            int i4 = i3 + 1;
            int[] iArr2 = iArr[bArr[i3]];
            while (i2 <= min) {
                int i5 = i2 + 1;
                int i6 = iArr2[cArr[i2]];
                bzip2BitWriter.writeBits(byteBuf, i6 >>> 24, (long) i6);
                i2 = i5;
            }
            i3 = i4;
        }
    }

    /* access modifiers changed from: 0000 */
    public void encode(ByteBuf byteBuf) {
        generateHuffmanOptimisationSeeds();
        int i = 3;
        while (i >= 0) {
            optimiseSelectorsAndHuffmanTables(i == 0);
            i--;
        }
        assignHuffmanCodeSymbols();
        writeSelectorsAndHuffmanTables(byteBuf);
        writeBlockData(byteBuf);
    }
}
