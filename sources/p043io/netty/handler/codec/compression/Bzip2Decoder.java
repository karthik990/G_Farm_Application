package p043io.netty.handler.codec.compression;

import p043io.netty.handler.codec.ByteToMessageDecoder;

/* renamed from: io.netty.handler.codec.compression.Bzip2Decoder */
public class Bzip2Decoder extends ByteToMessageDecoder {
    private int blockCRC;
    private Bzip2BlockDecompressor blockDecompressor;
    private int blockSize;
    private State currentState = State.INIT;
    private Bzip2HuffmanStageDecoder huffmanStageDecoder;
    private final Bzip2BitReader reader = new Bzip2BitReader();
    private int streamCRC;

    /* renamed from: io.netty.handler.codec.compression.Bzip2Decoder$State */
    private enum State {
        INIT,
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        RECEIVE_HUFFMAN_USED_MAP,
        RECEIVE_HUFFMAN_USED_BITMAPS,
        RECEIVE_SELECTORS_NUMBER,
        RECEIVE_SELECTORS,
        RECEIVE_HUFFMAN_LENGTH,
        DECODE_HUFFMAN_DATA,
        EOF
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01aa, code lost:
        if (r8 != false) goto L_0x01bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01b0, code lost:
        if (r2.readBoolean() == false) goto L_0x01b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01b3, code lost:
        r5[r9][r13] = (byte) r7;
        r13 = r13 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01bf, code lost:
        if (r2.isReadable() != false) goto L_0x01c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01c1, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01c7, code lost:
        if (r2.readBoolean() == false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01c9, code lost:
        r8 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01cb, code lost:
        r8 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01cc, code lost:
        r7 = r7 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01d1, code lost:
        if (r2.isReadable() != false) goto L_0x01d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01d3, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01d5, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01d7, code lost:
        r3.currentAlpha = 0;
        r9 = r9 + 1;
        r7 = -1;
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01de, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01df, code lost:
        if (r11 == false) goto L_0x01ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01e1, code lost:
        r3.currentGroup = r9;
        r3.currentLength = r7;
        r3.currentAlpha = r13;
        r3.modifyLength = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01e9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x01ea, code lost:
        r3.createHuffmanDecodingTables();
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.DECODE_HUFFMAN_DATA;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01f1, code lost:
        r3 = r1.blockDecompressor;
        r4 = r18.readerIndex();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01fd, code lost:
        if (r3.decodeHuffmanData(r1.huffmanStageDecoder) != false) goto L_0x0200;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01ff, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0204, code lost:
        if (r18.readerIndex() != r4) goto L_0x020f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x020a, code lost:
        if (r18.isReadable() == false) goto L_0x020f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x020c, code lost:
        r2.refill();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x020f, code lost:
        r4 = r17.alloc().buffer(r3.blockLength());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:?, code lost:
        r5 = r3.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x021f, code lost:
        if (r5 < 0) goto L_0x0225;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0221, code lost:
        r4.writeByte(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0225, code lost:
        r1.streamCRC = r3.checkCRC() ^ ((r1.streamCRC << 1) | (r1.streamCRC >>> 31));
        r19.add(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0239, code lost:
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x023f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0240, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0243, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x024b, code lost:
        throw new p043io.netty.handler.codec.compression.DecompressionException("incorrect selectors number");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0253, code lost:
        throw new p043io.netty.handler.codec.compression.DecompressionException("incorrect alphabet size");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x025b, code lost:
        throw new p043io.netty.handler.codec.compression.DecompressionException("incorrect huffman groups number");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x026b, code lost:
        throw new p043io.netty.handler.codec.compression.DecompressionException("block size is invalid");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0062, code lost:
        if (r2.hasReadableBytes(10) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0064, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0065, code lost:
        r3 = r2.readBits(24);
        r5 = r2.readBits(24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0070, code lost:
        if (r3 != 1536581) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0075, code lost:
        if (r5 != 3690640) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007d, code lost:
        if (r2.readInt() != r1.streamCRC) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007f, code lost:
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.EOF;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008b, code lost:
        throw new p043io.netty.handler.codec.compression.DecompressionException("stream CRC error");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008f, code lost:
        if (r3 != 3227993) goto L_0x025c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0094, code lost:
        if (r5 != 2511705) goto L_0x025c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0096, code lost:
        r1.blockCRC = r2.readInt();
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK_PARAMS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a6, code lost:
        if (r2.hasReadableBits(25) != false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a8, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a9, code lost:
        r3 = new p043io.netty.handler.codec.compression.Bzip2BlockDecompressor(r1.blockSize, r1.blockCRC, r2.readBoolean(), r2.readBits(24), r2);
        r1.blockDecompressor = r3;
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_MAP;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c6, code lost:
        if (r2.hasReadableBits(16) != false) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c8, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c9, code lost:
        r1.blockDecompressor.huffmanInUse16 = r2.readBits(16);
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_BITMAPS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d5, code lost:
        r3 = r1.blockDecompressor;
        r4 = r3.huffmanInUse16;
        r5 = java.lang.Integer.bitCount(r4);
        r6 = r3.huffmanSymbolMap;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e7, code lost:
        if (r2.hasReadableBits((r5 * 16) + 3) != false) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ea, code lost:
        if (r5 <= 0) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ec, code lost:
        r5 = 0;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00ee, code lost:
        if (r5 >= r10) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f5, code lost:
        if (((32768 >>> r5) & r4) == 0) goto L_0x0112;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f7, code lost:
        r14 = r5 << 4;
        r13 = r7;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00fc, code lost:
        if (r7 >= r10) goto L_0x0111;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0102, code lost:
        if (r2.readBoolean() == false) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0104, code lost:
        r15 = r13 + 1;
        r6[r13] = (byte) r14;
        r13 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x010a, code lost:
        r7 = r7 + 1;
        r14 = r14 + 1;
        r10 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0111, code lost:
        r7 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0112, code lost:
        r5 = r5 + 1;
        r10 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0117, code lost:
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0118, code lost:
        r3.huffmanEndOfBlockSymbol = r7 + 1;
        r3 = r2.readBits(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0121, code lost:
        if (r3 < 2) goto L_0x0254;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0123, code lost:
        if (r3 > 6) goto L_0x0254;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0125, code lost:
        r7 = r7 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0129, code lost:
        if (r7 > 258) goto L_0x024c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012b, code lost:
        r1.huffmanStageDecoder = new p043io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder(r2, r3, r7);
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS_NUMBER;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013c, code lost:
        if (r2.hasReadableBits(15) != false) goto L_0x013f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x013e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x013f, code lost:
        r3 = r2.readBits(15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0143, code lost:
        if (r3 < 1) goto L_0x0244;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0147, code lost:
        if (r3 > 18002) goto L_0x0244;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0149, code lost:
        r1.huffmanStageDecoder.selectors = new byte[r3];
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0153, code lost:
        r3 = r1.huffmanStageDecoder;
        r4 = r3.selectors;
        r5 = r4.length;
        r6 = r3.tableMTF;
        r7 = r3.currentSelector;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x015c, code lost:
        if (r7 >= r5) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0162, code lost:
        if (r2.hasReadableBits(6) != false) goto L_0x0167;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0164, code lost:
        r3.currentSelector = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0166, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0167, code lost:
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x016c, code lost:
        if (r2.readBoolean() == false) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x016e, code lost:
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0171, code lost:
        r4[r7] = r6.indexToFront(r8);
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x017a, code lost:
        r1.currentState = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_LENGTH;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x017e, code lost:
        r3 = r1.huffmanStageDecoder;
        r4 = r3.totalTables;
        r5 = r3.tableCodeLengths;
        r6 = r3.alphabetSize;
        r7 = r3.currentLength;
        r8 = r3.modifyLength;
        r9 = r3.currentGroup;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x018d, code lost:
        if (r9 >= r4) goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0194, code lost:
        if (r2.hasReadableBits(5) != false) goto L_0x0198;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0196, code lost:
        r11 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0198, code lost:
        if (r7 >= 0) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x019a, code lost:
        r7 = r2.readBits(5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x019e, code lost:
        r13 = r3.currentAlpha;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01a0, code lost:
        if (r13 >= r6) goto L_0x01d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01a6, code lost:
        if (r2.isReadable() != false) goto L_0x01aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01a8, code lost:
        r11 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r17, p043io.netty.buffer.ByteBuf r18, java.util.List<java.lang.Object> r19) throws java.lang.Exception {
        /*
            r16 = this;
            r1 = r16
            r0 = r18
            boolean r2 = r18.isReadable()
            if (r2 != 0) goto L_0x000b
            return
        L_0x000b:
            io.netty.handler.codec.compression.Bzip2BitReader r2 = r1.reader
            r2.setByteBuf(r0)
        L_0x0010:
            int[] r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.C56781.$SwitchMap$io$netty$handler$codec$compression$Bzip2Decoder$State
            io.netty.handler.codec.compression.Bzip2Decoder$State r4 = r1.currentState
            int r4 = r4.ordinal()
            r3 = r3[r4]
            r9 = 6
            r4 = 24
            r10 = 16
            r11 = 0
            r12 = 1
            switch(r3) {
                case 1: goto L_0x0032;
                case 2: goto L_0x005c;
                case 3: goto L_0x00a0;
                case 4: goto L_0x00c2;
                case 5: goto L_0x00d5;
                case 6: goto L_0x0136;
                case 7: goto L_0x0153;
                case 8: goto L_0x017e;
                case 9: goto L_0x01f1;
                case 10: goto L_0x002a;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x002a:
            int r2 = r18.readableBytes()
            r0.skipBytes(r2)
            return
        L_0x0032:
            int r3 = r18.readableBytes()
            r5 = 4
            if (r3 >= r5) goto L_0x003a
            return
        L_0x003a:
            int r3 = r18.readUnsignedMedium()
            r5 = 4348520(0x425a68, float:6.093574E-39)
            if (r3 != r5) goto L_0x026c
            byte r3 = r18.readByte()
            int r3 = r3 + -48
            if (r3 < r12) goto L_0x0264
            r5 = 9
            if (r3 > r5) goto L_0x0264
            r5 = 100000(0x186a0, float:1.4013E-40)
            int r3 = r3 * r5
            r1.blockSize = r3
            r1.streamCRC = r11
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK
            r1.currentState = r3
        L_0x005c:
            r3 = 10
            boolean r3 = r2.hasReadableBytes(r3)
            if (r3 != 0) goto L_0x0065
            return
        L_0x0065:
            int r3 = r2.readBits(r4)
            int r5 = r2.readBits(r4)
            r6 = 1536581(0x177245, float:2.153209E-39)
            if (r3 != r6) goto L_0x008c
            r6 = 3690640(0x385090, float:5.171688E-39)
            if (r5 != r6) goto L_0x008c
            int r3 = r2.readInt()
            int r4 = r1.streamCRC
            if (r3 != r4) goto L_0x0084
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.EOF
            r1.currentState = r3
            goto L_0x0010
        L_0x0084:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "stream CRC error"
            r0.<init>(r2)
            throw r0
        L_0x008c:
            r6 = 3227993(0x314159, float:4.523382E-39)
            if (r3 != r6) goto L_0x025c
            r3 = 2511705(0x265359, float:3.519648E-39)
            if (r5 != r3) goto L_0x025c
            int r3 = r2.readInt()
            r1.blockCRC = r3
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK_PARAMS
            r1.currentState = r3
        L_0x00a0:
            r3 = 25
            boolean r3 = r2.hasReadableBits(r3)
            if (r3 != 0) goto L_0x00a9
            return
        L_0x00a9:
            boolean r6 = r2.readBoolean()
            int r7 = r2.readBits(r4)
            io.netty.handler.codec.compression.Bzip2BlockDecompressor r13 = new io.netty.handler.codec.compression.Bzip2BlockDecompressor
            int r4 = r1.blockSize
            int r5 = r1.blockCRC
            r3 = r13
            r8 = r2
            r3.<init>(r4, r5, r6, r7, r8)
            r1.blockDecompressor = r13
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_MAP
            r1.currentState = r3
        L_0x00c2:
            boolean r3 = r2.hasReadableBits(r10)
            if (r3 != 0) goto L_0x00c9
            return
        L_0x00c9:
            io.netty.handler.codec.compression.Bzip2BlockDecompressor r3 = r1.blockDecompressor
            int r4 = r2.readBits(r10)
            r3.huffmanInUse16 = r4
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_BITMAPS
            r1.currentState = r3
        L_0x00d5:
            io.netty.handler.codec.compression.Bzip2BlockDecompressor r3 = r1.blockDecompressor
            int r4 = r3.huffmanInUse16
            int r5 = java.lang.Integer.bitCount(r4)
            byte[] r6 = r3.huffmanSymbolMap
            int r7 = r5 * 16
            r8 = 3
            int r7 = r7 + r8
            boolean r7 = r2.hasReadableBits(r7)
            if (r7 != 0) goto L_0x00ea
            return
        L_0x00ea:
            if (r5 <= 0) goto L_0x0117
            r5 = 0
            r7 = 0
        L_0x00ee:
            if (r5 >= r10) goto L_0x0118
            r13 = 32768(0x8000, float:4.5918E-41)
            int r13 = r13 >>> r5
            r13 = r13 & r4
            if (r13 == 0) goto L_0x0112
            int r13 = r5 << 4
            r14 = r13
            r13 = r7
            r7 = 0
        L_0x00fc:
            if (r7 >= r10) goto L_0x0111
            boolean r15 = r2.readBoolean()
            if (r15 == 0) goto L_0x010a
            int r15 = r13 + 1
            byte r10 = (byte) r14
            r6[r13] = r10
            r13 = r15
        L_0x010a:
            int r7 = r7 + 1
            int r14 = r14 + 1
            r10 = 16
            goto L_0x00fc
        L_0x0111:
            r7 = r13
        L_0x0112:
            int r5 = r5 + 1
            r10 = 16
            goto L_0x00ee
        L_0x0117:
            r7 = 0
        L_0x0118:
            int r4 = r7 + 1
            r3.huffmanEndOfBlockSymbol = r4
            int r3 = r2.readBits(r8)
            r4 = 2
            if (r3 < r4) goto L_0x0254
            if (r3 > r9) goto L_0x0254
            int r7 = r7 + 2
            r4 = 258(0x102, float:3.62E-43)
            if (r7 > r4) goto L_0x024c
            io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder r4 = new io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder
            r4.<init>(r2, r3, r7)
            r1.huffmanStageDecoder = r4
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS_NUMBER
            r1.currentState = r3
        L_0x0136:
            r3 = 15
            boolean r4 = r2.hasReadableBits(r3)
            if (r4 != 0) goto L_0x013f
            return
        L_0x013f:
            int r3 = r2.readBits(r3)
            if (r3 < r12) goto L_0x0244
            r4 = 18002(0x4652, float:2.5226E-41)
            if (r3 > r4) goto L_0x0244
            io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder r4 = r1.huffmanStageDecoder
            byte[] r3 = new byte[r3]
            r4.selectors = r3
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS
            r1.currentState = r3
        L_0x0153:
            io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder r3 = r1.huffmanStageDecoder
            byte[] r4 = r3.selectors
            int r5 = r4.length
            io.netty.handler.codec.compression.Bzip2MoveToFrontTable r6 = r3.tableMTF
            int r7 = r3.currentSelector
        L_0x015c:
            if (r7 >= r5) goto L_0x017a
            boolean r8 = r2.hasReadableBits(r9)
            if (r8 != 0) goto L_0x0167
            r3.currentSelector = r7
            return
        L_0x0167:
            r8 = 0
        L_0x0168:
            boolean r10 = r2.readBoolean()
            if (r10 == 0) goto L_0x0171
            int r8 = r8 + 1
            goto L_0x0168
        L_0x0171:
            byte r8 = r6.indexToFront(r8)
            r4[r7] = r8
            int r7 = r7 + 1
            goto L_0x015c
        L_0x017a:
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_LENGTH
            r1.currentState = r3
        L_0x017e:
            io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder r3 = r1.huffmanStageDecoder
            int r4 = r3.totalTables
            byte[][] r5 = r3.tableCodeLengths
            int r6 = r3.alphabetSize
            int r7 = r3.currentLength
            boolean r8 = r3.modifyLength
            int r9 = r3.currentGroup
            r10 = -1
        L_0x018d:
            if (r9 >= r4) goto L_0x01de
            r13 = 5
            boolean r14 = r2.hasReadableBits(r13)
            if (r14 != 0) goto L_0x0198
            r11 = 1
            goto L_0x01de
        L_0x0198:
            if (r7 >= 0) goto L_0x019e
            int r7 = r2.readBits(r13)
        L_0x019e:
            int r13 = r3.currentAlpha
        L_0x01a0:
            if (r13 >= r6) goto L_0x01d7
            boolean r14 = r2.isReadable()
            if (r14 != 0) goto L_0x01aa
        L_0x01a8:
            r11 = 1
            goto L_0x01df
        L_0x01aa:
            if (r8 != 0) goto L_0x01bb
            boolean r14 = r2.readBoolean()
            if (r14 == 0) goto L_0x01b3
            goto L_0x01bb
        L_0x01b3:
            r14 = r5[r9]
            byte r15 = (byte) r7
            r14[r13] = r15
            int r13 = r13 + 1
            goto L_0x01a0
        L_0x01bb:
            boolean r8 = r2.isReadable()
            if (r8 != 0) goto L_0x01c3
            r8 = 1
            goto L_0x01a8
        L_0x01c3:
            boolean r8 = r2.readBoolean()
            if (r8 == 0) goto L_0x01cb
            r8 = -1
            goto L_0x01cc
        L_0x01cb:
            r8 = 1
        L_0x01cc:
            int r7 = r7 + r8
            boolean r8 = r2.isReadable()
            if (r8 != 0) goto L_0x01d5
            r8 = 0
            goto L_0x01a8
        L_0x01d5:
            r8 = 0
            goto L_0x01aa
        L_0x01d7:
            r3.currentAlpha = r11
            int r9 = r9 + 1
            r7 = -1
            r8 = 0
            goto L_0x018d
        L_0x01de:
            r13 = 0
        L_0x01df:
            if (r11 == 0) goto L_0x01ea
            r3.currentGroup = r9
            r3.currentLength = r7
            r3.currentAlpha = r13
            r3.modifyLength = r8
            return
        L_0x01ea:
            r3.createHuffmanDecodingTables()
            io.netty.handler.codec.compression.Bzip2Decoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.DECODE_HUFFMAN_DATA
            r1.currentState = r3
        L_0x01f1:
            io.netty.handler.codec.compression.Bzip2BlockDecompressor r3 = r1.blockDecompressor
            int r4 = r18.readerIndex()
            io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder r5 = r1.huffmanStageDecoder
            boolean r5 = r3.decodeHuffmanData(r5)
            if (r5 != 0) goto L_0x0200
            return
        L_0x0200:
            int r5 = r18.readerIndex()
            if (r5 != r4) goto L_0x020f
            boolean r4 = r18.isReadable()
            if (r4 == 0) goto L_0x020f
            r2.refill()
        L_0x020f:
            int r4 = r3.blockLength()
            io.netty.buffer.ByteBufAllocator r5 = r17.alloc()
            io.netty.buffer.ByteBuf r4 = r5.buffer(r4)
        L_0x021b:
            int r5 = r3.read()     // Catch:{ all -> 0x023f }
            if (r5 < 0) goto L_0x0225
            r4.writeByte(r5)     // Catch:{ all -> 0x023f }
            goto L_0x021b
        L_0x0225:
            int r3 = r3.checkCRC()     // Catch:{ all -> 0x023f }
            int r5 = r1.streamCRC     // Catch:{ all -> 0x023f }
            int r5 = r5 << r12
            int r6 = r1.streamCRC     // Catch:{ all -> 0x023f }
            int r6 = r6 >>> 31
            r5 = r5 | r6
            r3 = r3 ^ r5
            r1.streamCRC = r3     // Catch:{ all -> 0x023f }
            r3 = r19
            r3.add(r4)     // Catch:{ all -> 0x023f }
            io.netty.handler.codec.compression.Bzip2Decoder$State r4 = p043io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK
            r1.currentState = r4
            goto L_0x0010
        L_0x023f:
            r0 = move-exception
            r4.release()
            throw r0
        L_0x0244:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "incorrect selectors number"
            r0.<init>(r2)
            throw r0
        L_0x024c:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "incorrect alphabet size"
            r0.<init>(r2)
            throw r0
        L_0x0254:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "incorrect huffman groups number"
            r0.<init>(r2)
            throw r0
        L_0x025c:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "bad block header"
            r0.<init>(r2)
            throw r0
        L_0x0264:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "block size is invalid"
            r0.<init>(r2)
            throw r0
        L_0x026c:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "Unexpected stream identifier contents. Mismatched bzip2 protocol version?"
            r0.<init>(r2)
            goto L_0x0275
        L_0x0274:
            throw r0
        L_0x0275:
            goto L_0x0274
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2Decoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    public boolean isClosed() {
        return this.currentState == State.EOF;
    }
}
