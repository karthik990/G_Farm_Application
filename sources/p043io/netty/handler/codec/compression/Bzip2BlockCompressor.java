package p043io.netty.handler.codec.compression;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.util.ByteProcessor;

/* renamed from: io.netty.handler.codec.compression.Bzip2BlockCompressor */
final class Bzip2BlockCompressor {
    private final byte[] block;
    private int blockLength;
    private final int blockLengthLimit;
    private final boolean[] blockValuesPresent = new boolean[256];
    private final int[] bwtBlock;
    private final Crc32 crc = new Crc32();
    private int rleCurrentValue = -1;
    private int rleLength;
    private final ByteProcessor writeProcessor = new ByteProcessor() {
        public boolean process(byte b) throws Exception {
            return Bzip2BlockCompressor.this.write(b);
        }
    };
    private final Bzip2BitWriter writer;

    Bzip2BlockCompressor(Bzip2BitWriter bzip2BitWriter, int i) {
        this.writer = bzip2BitWriter;
        int i2 = i + 1;
        this.block = new byte[i2];
        this.bwtBlock = new int[i2];
        this.blockLengthLimit = i - 6;
    }

    private void writeSymbolMap(ByteBuf byteBuf) {
        Bzip2BitWriter bzip2BitWriter = this.writer;
        boolean[] zArr = this.blockValuesPresent;
        boolean[] zArr2 = new boolean[16];
        for (int i = 0; i < zArr2.length; i++) {
            int i2 = i << 4;
            int i3 = 0;
            while (i3 < 16) {
                if (zArr[i2]) {
                    zArr2[i] = true;
                }
                i3++;
                i2++;
            }
        }
        for (boolean writeBoolean : zArr2) {
            bzip2BitWriter.writeBoolean(byteBuf, writeBoolean);
        }
        for (int i4 = 0; i4 < zArr2.length; i4++) {
            if (zArr2[i4]) {
                int i5 = i4 << 4;
                int i6 = 0;
                while (i6 < 16) {
                    bzip2BitWriter.writeBoolean(byteBuf, zArr[i5]);
                    i6++;
                    i5++;
                }
            }
        }
    }

    private void writeRun(int i, int i2) {
        int i3 = this.blockLength;
        byte[] bArr = this.block;
        this.blockValuesPresent[i] = true;
        this.crc.updateCRC(i, i2);
        byte b = (byte) i;
        if (i2 == 1) {
            bArr[i3] = b;
            this.blockLength = i3 + 1;
        } else if (i2 == 2) {
            bArr[i3] = b;
            bArr[i3 + 1] = b;
            this.blockLength = i3 + 2;
        } else if (i2 != 3) {
            int i4 = i2 - 4;
            this.blockValuesPresent[i4] = true;
            bArr[i3] = b;
            bArr[i3 + 1] = b;
            bArr[i3 + 2] = b;
            bArr[i3 + 3] = b;
            bArr[i3 + 4] = (byte) i4;
            this.blockLength = i3 + 5;
        } else {
            bArr[i3] = b;
            bArr[i3 + 1] = b;
            bArr[i3 + 2] = b;
            this.blockLength = i3 + 3;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean write(int i) {
        if (this.blockLength > this.blockLengthLimit) {
            return false;
        }
        int i2 = this.rleCurrentValue;
        int i3 = this.rleLength;
        if (i3 == 0) {
            this.rleCurrentValue = i;
            this.rleLength = 1;
        } else if (i2 != i) {
            writeRun(i2 & 255, i3);
            this.rleCurrentValue = i;
            this.rleLength = 1;
        } else if (i3 == 254) {
            writeRun(i2 & 255, 255);
            this.rleLength = 0;
        } else {
            this.rleLength = i3 + 1;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public int write(ByteBuf byteBuf, int i, int i2) {
        int forEachByte = byteBuf.forEachByte(i, i2, this.writeProcessor);
        return forEachByte == -1 ? i2 : forEachByte - i;
    }

    /* access modifiers changed from: 0000 */
    public void close(ByteBuf byteBuf) {
        int i = this.rleLength;
        if (i > 0) {
            writeRun(this.rleCurrentValue & 255, i);
        }
        byte[] bArr = this.block;
        int i2 = this.blockLength;
        bArr[i2] = bArr[0];
        int bwt = new Bzip2DivSufSort(bArr, this.bwtBlock, i2).bwt();
        Bzip2BitWriter bzip2BitWriter = this.writer;
        bzip2BitWriter.writeBits(byteBuf, 24, 3227993);
        bzip2BitWriter.writeBits(byteBuf, 24, 2511705);
        bzip2BitWriter.writeInt(byteBuf, this.crc.getCRC());
        bzip2BitWriter.writeBoolean(byteBuf, false);
        bzip2BitWriter.writeBits(byteBuf, 24, (long) bwt);
        writeSymbolMap(byteBuf);
        Bzip2MTFAndRLE2StageEncoder bzip2MTFAndRLE2StageEncoder = new Bzip2MTFAndRLE2StageEncoder(this.bwtBlock, this.blockLength, this.blockValuesPresent);
        bzip2MTFAndRLE2StageEncoder.encode();
        Bzip2HuffmanStageEncoder bzip2HuffmanStageEncoder = new Bzip2HuffmanStageEncoder(bzip2BitWriter, bzip2MTFAndRLE2StageEncoder.mtfBlock(), bzip2MTFAndRLE2StageEncoder.mtfLength(), bzip2MTFAndRLE2StageEncoder.mtfAlphabetSize(), bzip2MTFAndRLE2StageEncoder.mtfSymbolFrequencies());
        bzip2HuffmanStageEncoder.encode(byteBuf);
    }

    /* access modifiers changed from: 0000 */
    public int availableSize() {
        int i = this.blockLength;
        if (i == 0) {
            return this.blockLengthLimit + 2;
        }
        return (this.blockLengthLimit - i) + 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean isFull() {
        return this.blockLength > this.blockLengthLimit;
    }

    /* access modifiers changed from: 0000 */
    public boolean isEmpty() {
        return this.blockLength == 0 && this.rleLength == 0;
    }

    /* access modifiers changed from: 0000 */
    public int crc() {
        return this.crc.getCRC();
    }
}
