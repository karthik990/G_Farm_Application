package p043io.netty.handler.codec.compression;

/* renamed from: io.netty.handler.codec.compression.Bzip2BlockDecompressor */
final class Bzip2BlockDecompressor {
    private final int blockCRC;
    private final boolean blockRandomised;
    private final byte[] bwtBlock;
    private int bwtBlockLength;
    private final int[] bwtByteCounts = new int[256];
    private int bwtBytesDecoded;
    private int bwtCurrentMergedPointer;
    private int[] bwtMergedPointers;
    private final int bwtStartPointer;
    private final Crc32 crc = new Crc32();
    int huffmanEndOfBlockSymbol;
    int huffmanInUse16;
    final byte[] huffmanSymbolMap = new byte[256];
    private int mtfValue;
    private int randomCount = (Bzip2Rand.rNums(0) - 1);
    private int randomIndex;
    private final Bzip2BitReader reader;
    private int repeatCount;
    private int repeatIncrement = 1;
    private int rleAccumulator;
    private int rleLastDecodedByte = -1;
    private int rleRepeat;
    private final Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();

    Bzip2BlockDecompressor(int i, int i2, boolean z, int i3, Bzip2BitReader bzip2BitReader) {
        this.bwtBlock = new byte[i];
        this.blockCRC = i2;
        this.blockRandomised = z;
        this.bwtStartPointer = i3;
        this.reader = bzip2BitReader;
    }

    /* access modifiers changed from: 0000 */
    public boolean decodeHuffmanData(Bzip2HuffmanStageDecoder bzip2HuffmanStageDecoder) {
        Bzip2BitReader bzip2BitReader = this.reader;
        byte[] bArr = this.bwtBlock;
        byte[] bArr2 = this.huffmanSymbolMap;
        int length = bArr.length;
        int i = this.huffmanEndOfBlockSymbol;
        int[] iArr = this.bwtByteCounts;
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = this.symbolMTF;
        int i2 = this.bwtBlockLength;
        int i3 = this.repeatCount;
        int i4 = this.repeatIncrement;
        int i5 = this.mtfValue;
        while (bzip2BitReader.hasReadableBits(23)) {
            int nextSymbol = bzip2HuffmanStageDecoder.nextSymbol();
            if (nextSymbol == 0) {
                i3 += i4;
                i4 <<= 1;
            } else if (nextSymbol == 1) {
                i4 <<= 1;
                i3 += i4;
            } else {
                String str = "block exceeds declared block size";
                if (i3 > 0) {
                    if (i2 + i3 <= length) {
                        byte b = bArr2[i5];
                        byte b2 = b & 255;
                        iArr[b2] = iArr[b2] + i3;
                        while (true) {
                            i3--;
                            if (i3 < 0) {
                                break;
                            }
                            int i6 = i2 + 1;
                            bArr[i2] = b;
                            i2 = i6;
                        }
                        i3 = 0;
                        i4 = 1;
                    } else {
                        throw new DecompressionException(str);
                    }
                }
                if (nextSymbol == i) {
                    this.bwtBlockLength = i2;
                    initialiseInverseBWT();
                    return true;
                } else if (i2 < length) {
                    i5 = bzip2MoveToFrontTable.indexToFront(nextSymbol - 1) & 255;
                    byte b3 = bArr2[i5];
                    byte b4 = b3 & 255;
                    iArr[b4] = iArr[b4] + 1;
                    int i7 = i2 + 1;
                    bArr[i2] = b3;
                    i2 = i7;
                } else {
                    throw new DecompressionException(str);
                }
            }
        }
        this.bwtBlockLength = i2;
        this.repeatCount = i3;
        this.repeatIncrement = i4;
        this.mtfValue = i5;
        return false;
    }

    private void initialiseInverseBWT() {
        int i = this.bwtStartPointer;
        byte[] bArr = this.bwtBlock;
        int i2 = this.bwtBlockLength;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[256];
        if (i < 0 || i >= i2) {
            throw new DecompressionException("start pointer invalid");
        }
        System.arraycopy(this.bwtByteCounts, 0, iArr2, 1, 255);
        for (int i3 = 2; i3 <= 255; i3++) {
            iArr2[i3] = iArr2[i3] + iArr2[i3 - 1];
        }
        for (int i4 = 0; i4 < this.bwtBlockLength; i4++) {
            byte b = bArr[i4] & 255;
            int i5 = iArr2[b];
            iArr2[b] = i5 + 1;
            iArr[i5] = (i4 << 8) + b;
        }
        this.bwtMergedPointers = iArr;
        this.bwtCurrentMergedPointer = iArr[i];
    }

    public int read() {
        while (true) {
            int i = this.rleRepeat;
            if (i >= 1) {
                this.rleRepeat = i - 1;
                return this.rleLastDecodedByte;
            } else if (this.bwtBytesDecoded == this.bwtBlockLength) {
                return -1;
            } else {
                int decodeNextBWTByte = decodeNextBWTByte();
                if (decodeNextBWTByte != this.rleLastDecodedByte) {
                    this.rleLastDecodedByte = decodeNextBWTByte;
                    this.rleRepeat = 1;
                    this.rleAccumulator = 1;
                    this.crc.updateCRC(decodeNextBWTByte);
                } else {
                    int i2 = this.rleAccumulator + 1;
                    this.rleAccumulator = i2;
                    if (i2 == 4) {
                        int decodeNextBWTByte2 = decodeNextBWTByte() + 1;
                        this.rleRepeat = decodeNextBWTByte2;
                        this.rleAccumulator = 0;
                        this.crc.updateCRC(decodeNextBWTByte, decodeNextBWTByte2);
                    } else {
                        this.rleRepeat = 1;
                        this.crc.updateCRC(decodeNextBWTByte);
                    }
                }
            }
        }
    }

    private int decodeNextBWTByte() {
        int i = this.bwtCurrentMergedPointer;
        int i2 = i & 255;
        this.bwtCurrentMergedPointer = this.bwtMergedPointers[i >>> 8];
        if (this.blockRandomised) {
            int i3 = this.randomCount - 1;
            this.randomCount = i3;
            if (i3 == 0) {
                i2 ^= 1;
                this.randomIndex = (this.randomIndex + 1) % 512;
                this.randomCount = Bzip2Rand.rNums(this.randomIndex);
            }
        }
        this.bwtBytesDecoded++;
        return i2;
    }

    public int blockLength() {
        return this.bwtBlockLength;
    }

    /* access modifiers changed from: 0000 */
    public int checkCRC() {
        int crc2 = this.crc.getCRC();
        if (this.blockCRC == crc2) {
            return crc2;
        }
        throw new DecompressionException("block CRC error");
    }
}
