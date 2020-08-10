package p043io.netty.handler.codec.compression;

import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.xxhash.XXHashFactory;
import p043io.netty.handler.codec.ByteToMessageDecoder;

/* renamed from: io.netty.handler.codec.compression.Lz4FrameDecoder */
public class Lz4FrameDecoder extends ByteToMessageDecoder {
    private int blockType;
    private ByteBufChecksum checksum;
    private int compressedLength;
    private int currentChecksum;
    private State currentState;
    private int decompressedLength;
    private LZ4FastDecompressor decompressor;

    /* renamed from: io.netty.handler.codec.compression.Lz4FrameDecoder$1 */
    static /* synthetic */ class C56921 {

        /* renamed from: $SwitchMap$io$netty$handler$codec$compression$Lz4FrameDecoder$State */
        static final /* synthetic */ int[] f3725xd73b9784 = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.handler.codec.compression.Lz4FrameDecoder$State[] r0 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3725xd73b9784 = r0
                int[] r0 = f3725xd73b9784     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.Lz4FrameDecoder$State r1 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.INIT_BLOCK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3725xd73b9784     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.Lz4FrameDecoder$State r1 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.DECOMPRESS_DATA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3725xd73b9784     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.Lz4FrameDecoder$State r1 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.FINISHED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3725xd73b9784     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.Lz4FrameDecoder$State r1 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.CORRUPTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Lz4FrameDecoder.C56921.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.Lz4FrameDecoder$State */
    private enum State {
        INIT_BLOCK,
        DECOMPRESS_DATA,
        FINISHED,
        CORRUPTED
    }

    public Lz4FrameDecoder() {
        this(false);
    }

    public Lz4FrameDecoder(boolean z) {
        this(LZ4Factory.fastestInstance(), z);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, boolean z) {
        this(lZ4Factory, z ? XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum() : null);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, Checksum checksum2) {
        ByteBufChecksum byteBufChecksum;
        this.currentState = State.INIT_BLOCK;
        if (lZ4Factory != null) {
            this.decompressor = lZ4Factory.fastDecompressor();
            if (checksum2 == null) {
                byteBufChecksum = null;
            } else {
                byteBufChecksum = ByteBufChecksum.wrapChecksum(checksum2);
            }
            this.checksum = byteBufChecksum;
            return;
        }
        throw new NullPointerException("factory");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x013d A[SYNTHETIC, Splitter:B:73:0x013d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r13, p043io.netty.buffer.ByteBuf r14, java.util.List<java.lang.Object> r15) throws java.lang.Exception {
        /*
            r12 = this;
            int[] r0 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.C56921.f3725xd73b9784     // Catch:{ Exception -> 0x017d }
            io.netty.handler.codec.compression.Lz4FrameDecoder$State r1 = r12.currentState     // Catch:{ Exception -> 0x017d }
            int r1 = r1.ordinal()     // Catch:{ Exception -> 0x017d }
            r0 = r0[r1]     // Catch:{ Exception -> 0x017d }
            r1 = 3
            r2 = 16
            r3 = 0
            r4 = 0
            r5 = 2
            r6 = 1
            if (r0 == r6) goto L_0x002a
            if (r0 == r5) goto L_0x00ba
            if (r0 == r1) goto L_0x0021
            r13 = 4
            if (r0 != r13) goto L_0x001b
            goto L_0x0021
        L_0x001b:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x017d }
            r13.<init>()     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x0021:
            int r13 = r14.readableBytes()     // Catch:{ Exception -> 0x017d }
            r14.skipBytes(r13)     // Catch:{ Exception -> 0x017d }
            goto L_0x0134
        L_0x002a:
            int r0 = r14.readableBytes()     // Catch:{ Exception -> 0x017d }
            r7 = 21
            if (r0 >= r7) goto L_0x0034
            goto L_0x0134
        L_0x0034:
            long r7 = r14.readLong()     // Catch:{ Exception -> 0x017d }
            r9 = 5501767354678207339(0x4c5a34426c6f636b, double:6.579441740982069E59)
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r0 != 0) goto L_0x0175
            byte r0 = r14.readByte()     // Catch:{ Exception -> 0x017d }
            r7 = r0 & 15
            int r7 = r7 + 10
            r0 = r0 & 240(0xf0, float:3.36E-43)
            int r8 = r14.readInt()     // Catch:{ Exception -> 0x017d }
            int r8 = java.lang.Integer.reverseBytes(r8)     // Catch:{ Exception -> 0x017d }
            r9 = 33554432(0x2000000, float:9.403955E-38)
            if (r8 < 0) goto L_0x015b
            if (r8 > r9) goto L_0x015b
            int r9 = r14.readInt()     // Catch:{ Exception -> 0x017d }
            int r9 = java.lang.Integer.reverseBytes(r9)     // Catch:{ Exception -> 0x017d }
            int r7 = r6 << r7
            if (r9 < 0) goto L_0x0141
            if (r9 > r7) goto L_0x0141
            if (r9 != 0) goto L_0x006b
            if (r8 != 0) goto L_0x0074
        L_0x006b:
            if (r9 == 0) goto L_0x006f
            if (r8 == 0) goto L_0x0074
        L_0x006f:
            if (r0 != r2) goto L_0x008e
            if (r9 != r8) goto L_0x0074
            goto L_0x008e
        L_0x0074:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = "stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch"
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x017d }
            r15[r3] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x017d }
            r15[r6] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = java.lang.String.format(r14, r15)     // Catch:{ Exception -> 0x017d }
            r13.<init>(r14)     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x008e:
            int r7 = r14.readInt()     // Catch:{ Exception -> 0x017d }
            int r7 = java.lang.Integer.reverseBytes(r7)     // Catch:{ Exception -> 0x017d }
            if (r9 != 0) goto L_0x00ae
            if (r8 != 0) goto L_0x00ae
            if (r7 != 0) goto L_0x00a6
            io.netty.handler.codec.compression.Lz4FrameDecoder$State r13 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.FINISHED     // Catch:{ Exception -> 0x017d }
            r12.currentState = r13     // Catch:{ Exception -> 0x017d }
            r12.decompressor = r4     // Catch:{ Exception -> 0x017d }
            r12.checksum = r4     // Catch:{ Exception -> 0x017d }
            goto L_0x0134
        L_0x00a6:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = "stream corrupted: checksum error"
            r13.<init>(r14)     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x00ae:
            r12.blockType = r0     // Catch:{ Exception -> 0x017d }
            r12.compressedLength = r8     // Catch:{ Exception -> 0x017d }
            r12.decompressedLength = r9     // Catch:{ Exception -> 0x017d }
            r12.currentChecksum = r7     // Catch:{ Exception -> 0x017d }
            io.netty.handler.codec.compression.Lz4FrameDecoder$State r0 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.DECOMPRESS_DATA     // Catch:{ Exception -> 0x017d }
            r12.currentState = r0     // Catch:{ Exception -> 0x017d }
        L_0x00ba:
            int r0 = r12.blockType     // Catch:{ Exception -> 0x017d }
            int r7 = r12.compressedLength     // Catch:{ Exception -> 0x017d }
            int r8 = r12.decompressedLength     // Catch:{ Exception -> 0x017d }
            int r9 = r12.currentChecksum     // Catch:{ Exception -> 0x017d }
            int r10 = r14.readableBytes()     // Catch:{ Exception -> 0x017d }
            if (r10 >= r7) goto L_0x00c9
            goto L_0x0134
        L_0x00c9:
            io.netty.handler.codec.compression.ByteBufChecksum r10 = r12.checksum     // Catch:{ Exception -> 0x017d }
            if (r0 == r2) goto L_0x011d
            r11 = 32
            if (r0 != r11) goto L_0x00f9
            io.netty.buffer.ByteBufAllocator r13 = r13.alloc()     // Catch:{ LZ4Exception -> 0x011b }
            io.netty.buffer.ByteBuf r13 = r13.buffer(r8, r8)     // Catch:{ LZ4Exception -> 0x011b }
            net.jpountz.lz4.LZ4FastDecompressor r0 = r12.decompressor     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            java.nio.ByteBuffer r1 = p043io.netty.handler.codec.compression.CompressionUtil.safeNioBuffer(r14)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            int r2 = r13.writerIndex()     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            java.nio.ByteBuffer r2 = r13.internalNioBuffer(r2, r8)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            r0.decompress(r1, r2)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            int r0 = r13.writerIndex()     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            int r0 = r0 + r8
            r13.writerIndex(r0)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            goto L_0x0125
        L_0x00f3:
            r14 = move-exception
            r4 = r13
            goto L_0x013b
        L_0x00f6:
            r14 = move-exception
            r4 = r13
            goto L_0x0135
        L_0x00f9:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ LZ4Exception -> 0x011b }
            java.lang.String r14 = "unexpected blockType: %d (expected: %d or %d)"
            java.lang.Object[] r15 = new java.lang.Object[r1]     // Catch:{ LZ4Exception -> 0x011b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ LZ4Exception -> 0x011b }
            r15[r3] = r0     // Catch:{ LZ4Exception -> 0x011b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)     // Catch:{ LZ4Exception -> 0x011b }
            r15[r6] = r0     // Catch:{ LZ4Exception -> 0x011b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r11)     // Catch:{ LZ4Exception -> 0x011b }
            r15[r5] = r0     // Catch:{ LZ4Exception -> 0x011b }
            java.lang.String r14 = java.lang.String.format(r14, r15)     // Catch:{ LZ4Exception -> 0x011b }
            r13.<init>(r14)     // Catch:{ LZ4Exception -> 0x011b }
            throw r13     // Catch:{ LZ4Exception -> 0x011b }
        L_0x0119:
            r14 = move-exception
            goto L_0x013b
        L_0x011b:
            r14 = move-exception
            goto L_0x0135
        L_0x011d:
            int r13 = r14.readerIndex()     // Catch:{ LZ4Exception -> 0x011b }
            io.netty.buffer.ByteBuf r13 = r14.retainedSlice(r13, r8)     // Catch:{ LZ4Exception -> 0x011b }
        L_0x0125:
            r14.skipBytes(r7)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            if (r10 == 0) goto L_0x012d
            p043io.netty.handler.codec.compression.CompressionUtil.checkChecksum(r10, r13, r9)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
        L_0x012d:
            r15.add(r13)     // Catch:{ LZ4Exception -> 0x00f6, all -> 0x00f3 }
            io.netty.handler.codec.compression.Lz4FrameDecoder$State r13 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.INIT_BLOCK     // Catch:{ LZ4Exception -> 0x011b }
            r12.currentState = r13     // Catch:{ LZ4Exception -> 0x011b }
        L_0x0134:
            return
        L_0x0135:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ all -> 0x0119 }
            r13.<init>(r14)     // Catch:{ all -> 0x0119 }
            throw r13     // Catch:{ all -> 0x0119 }
        L_0x013b:
            if (r4 == 0) goto L_0x0140
            r4.release()     // Catch:{ Exception -> 0x017d }
        L_0x0140:
            throw r14     // Catch:{ Exception -> 0x017d }
        L_0x0141:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = "invalid decompressedLength: %d (expected: 0-%d)"
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x017d }
            r15[r3] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x017d }
            r15[r6] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = java.lang.String.format(r14, r15)     // Catch:{ Exception -> 0x017d }
            r13.<init>(r14)     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x015b:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = "invalid compressedLength: %d (expected: 0-%d)"
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x017d }
            r15[r3] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x017d }
            r15[r6] = r0     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = java.lang.String.format(r14, r15)     // Catch:{ Exception -> 0x017d }
            r13.<init>(r14)     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x0175:
            io.netty.handler.codec.compression.DecompressionException r13 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x017d }
            java.lang.String r14 = "unexpected block identifier"
            r13.<init>(r14)     // Catch:{ Exception -> 0x017d }
            throw r13     // Catch:{ Exception -> 0x017d }
        L_0x017d:
            r13 = move-exception
            io.netty.handler.codec.compression.Lz4FrameDecoder$State r14 = p043io.netty.handler.codec.compression.Lz4FrameDecoder.State.CORRUPTED
            r12.currentState = r14
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Lz4FrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    public boolean isClosed() {
        return this.currentState == State.FINISHED;
    }
}
