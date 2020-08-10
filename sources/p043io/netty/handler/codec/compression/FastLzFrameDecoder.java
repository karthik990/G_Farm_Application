package p043io.netty.handler.codec.compression;

import java.util.zip.Adler32;
import java.util.zip.Checksum;
import p043io.netty.handler.codec.ByteToMessageDecoder;

/* renamed from: io.netty.handler.codec.compression.FastLzFrameDecoder */
public class FastLzFrameDecoder extends ByteToMessageDecoder {
    private final Checksum checksum;
    private int chunkLength;
    private int currentChecksum;
    private State currentState;
    private boolean hasChecksum;
    private boolean isCompressed;
    private int originalLength;

    /* renamed from: io.netty.handler.codec.compression.FastLzFrameDecoder$1 */
    static /* synthetic */ class C56831 {

        /* renamed from: $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State */
        static final /* synthetic */ int[] f3721x8db84534 = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.handler.codec.compression.FastLzFrameDecoder$State[] r0 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3721x8db84534 = r0
                int[] r0 = f3721x8db84534     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.FastLzFrameDecoder$State r1 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.INIT_BLOCK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3721x8db84534     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.FastLzFrameDecoder$State r1 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.INIT_BLOCK_PARAMS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3721x8db84534     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.FastLzFrameDecoder$State r1 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.DECOMPRESS_DATA     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3721x8db84534     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.FastLzFrameDecoder$State r1 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.CORRUPTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.FastLzFrameDecoder.C56831.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.FastLzFrameDecoder$State */
    private enum State {
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public FastLzFrameDecoder() {
        this(false);
    }

    public FastLzFrameDecoder(boolean z) {
        this((Checksum) z ? new Adler32() : null);
    }

    public FastLzFrameDecoder(Checksum checksum2) {
        this.currentState = State.INIT_BLOCK;
        this.checksum = checksum2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0098 A[Catch:{ Exception -> 0x0165 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x009a A[Catch:{ Exception -> 0x0165 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r17, p043io.netty.buffer.ByteBuf r18, java.util.List<java.lang.Object> r19) throws java.lang.Exception {
        /*
            r16 = this;
            r1 = r16
            r0 = r18
            int[] r2 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.C56831.f3721x8db84534     // Catch:{ Exception -> 0x0165 }
            io.netty.handler.codec.compression.FastLzFrameDecoder$State r3 = r1.currentState     // Catch:{ Exception -> 0x0165 }
            int r3 = r3.ordinal()     // Catch:{ Exception -> 0x0165 }
            r2 = r2[r3]     // Catch:{ Exception -> 0x0165 }
            r3 = 4
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == r5) goto L_0x002a
            if (r2 == r4) goto L_0x0056
            r7 = 3
            if (r2 == r7) goto L_0x0090
            if (r2 != r3) goto L_0x0024
            int r2 = r18.readableBytes()     // Catch:{ Exception -> 0x0165 }
            r0.skipBytes(r2)     // Catch:{ Exception -> 0x0165 }
            goto L_0x0154
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0165 }
            r0.<init>()     // Catch:{ Exception -> 0x0165 }
            throw r0     // Catch:{ Exception -> 0x0165 }
        L_0x002a:
            int r2 = r18.readableBytes()     // Catch:{ Exception -> 0x0165 }
            if (r2 >= r3) goto L_0x0032
            goto L_0x0154
        L_0x0032:
            int r2 = r18.readUnsignedMedium()     // Catch:{ Exception -> 0x0165 }
            r7 = 4607066(0x464c5a, float:6.455875E-39)
            if (r2 != r7) goto L_0x015d
            byte r2 = r18.readByte()     // Catch:{ Exception -> 0x0165 }
            r7 = r2 & 1
            if (r7 != r5) goto L_0x0045
            r7 = 1
            goto L_0x0046
        L_0x0045:
            r7 = 0
        L_0x0046:
            r1.isCompressed = r7     // Catch:{ Exception -> 0x0165 }
            r7 = 16
            r2 = r2 & r7
            if (r2 != r7) goto L_0x004f
            r2 = 1
            goto L_0x0050
        L_0x004f:
            r2 = 0
        L_0x0050:
            r1.hasChecksum = r2     // Catch:{ Exception -> 0x0165 }
            io.netty.handler.codec.compression.FastLzFrameDecoder$State r2 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.INIT_BLOCK_PARAMS     // Catch:{ Exception -> 0x0165 }
            r1.currentState = r2     // Catch:{ Exception -> 0x0165 }
        L_0x0056:
            int r2 = r18.readableBytes()     // Catch:{ Exception -> 0x0165 }
            boolean r7 = r1.isCompressed     // Catch:{ Exception -> 0x0165 }
            if (r7 == 0) goto L_0x0060
            r7 = 2
            goto L_0x0061
        L_0x0060:
            r7 = 0
        L_0x0061:
            int r7 = r7 + r4
            boolean r8 = r1.hasChecksum     // Catch:{ Exception -> 0x0165 }
            if (r8 == 0) goto L_0x0067
            goto L_0x0068
        L_0x0067:
            r3 = 0
        L_0x0068:
            int r7 = r7 + r3
            if (r2 >= r7) goto L_0x006d
            goto L_0x0154
        L_0x006d:
            boolean r2 = r1.hasChecksum     // Catch:{ Exception -> 0x0165 }
            if (r2 == 0) goto L_0x0076
            int r2 = r18.readInt()     // Catch:{ Exception -> 0x0165 }
            goto L_0x0077
        L_0x0076:
            r2 = 0
        L_0x0077:
            r1.currentChecksum = r2     // Catch:{ Exception -> 0x0165 }
            int r2 = r18.readUnsignedShort()     // Catch:{ Exception -> 0x0165 }
            r1.chunkLength = r2     // Catch:{ Exception -> 0x0165 }
            boolean r2 = r1.isCompressed     // Catch:{ Exception -> 0x0165 }
            if (r2 == 0) goto L_0x0088
            int r2 = r18.readUnsignedShort()     // Catch:{ Exception -> 0x0165 }
            goto L_0x008a
        L_0x0088:
            int r2 = r1.chunkLength     // Catch:{ Exception -> 0x0165 }
        L_0x008a:
            r1.originalLength = r2     // Catch:{ Exception -> 0x0165 }
            io.netty.handler.codec.compression.FastLzFrameDecoder$State r2 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.DECOMPRESS_DATA     // Catch:{ Exception -> 0x0165 }
            r1.currentState = r2     // Catch:{ Exception -> 0x0165 }
        L_0x0090:
            int r2 = r1.chunkLength     // Catch:{ Exception -> 0x0165 }
            int r3 = r18.readableBytes()     // Catch:{ Exception -> 0x0165 }
            if (r3 >= r2) goto L_0x009a
            goto L_0x0154
        L_0x009a:
            int r3 = r18.readerIndex()     // Catch:{ Exception -> 0x0165 }
            int r13 = r1.originalLength     // Catch:{ Exception -> 0x0165 }
            if (r13 == 0) goto L_0x00bb
            io.netty.buffer.ByteBufAllocator r7 = r17.alloc()     // Catch:{ Exception -> 0x0165 }
            io.netty.buffer.ByteBuf r7 = r7.heapBuffer(r13, r13)     // Catch:{ Exception -> 0x0165 }
            byte[] r8 = r7.array()     // Catch:{ Exception -> 0x0165 }
            int r9 = r7.arrayOffset()     // Catch:{ Exception -> 0x0165 }
            int r10 = r7.writerIndex()     // Catch:{ Exception -> 0x0165 }
            int r9 = r9 + r10
            r12 = r7
            r14 = r8
            r15 = r9
            goto L_0x00c1
        L_0x00bb:
            r7 = 0
            byte[] r8 = p043io.netty.util.internal.EmptyArrays.EMPTY_BYTES     // Catch:{ Exception -> 0x0165 }
            r12 = r7
            r14 = r8
            r15 = 0
        L_0x00c1:
            boolean r7 = r1.isCompressed     // Catch:{ all -> 0x0157 }
            if (r7 == 0) goto L_0x0106
            boolean r7 = r18.hasArray()     // Catch:{ all -> 0x0157 }
            if (r7 == 0) goto L_0x00d6
            byte[] r7 = r18.array()     // Catch:{ all -> 0x0157 }
            int r8 = r18.arrayOffset()     // Catch:{ all -> 0x0157 }
            int r3 = r3 + r8
            r8 = r3
            goto L_0x00dc
        L_0x00d6:
            byte[] r7 = new byte[r2]     // Catch:{ all -> 0x0157 }
            r0.getBytes(r3, r7)     // Catch:{ all -> 0x0157 }
            r8 = 0
        L_0x00dc:
            r9 = r2
            r10 = r14
            r11 = r15
            r3 = r12
            r12 = r13
            int r7 = p043io.netty.handler.codec.compression.FastLz.decompress(r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0103 }
            if (r13 != r7) goto L_0x00e9
            r7 = r3
            goto L_0x010a
        L_0x00e9:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ all -> 0x0103 }
            java.lang.String r2 = "stream corrupted: originalLength(%d) and actual length(%d) mismatch"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0103 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0103 }
            r4[r6] = r8     // Catch:{ all -> 0x0103 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0103 }
            r4[r5] = r6     // Catch:{ all -> 0x0103 }
            java.lang.String r2 = java.lang.String.format(r2, r4)     // Catch:{ all -> 0x0103 }
            r0.<init>(r2)     // Catch:{ all -> 0x0103 }
            throw r0     // Catch:{ all -> 0x0103 }
        L_0x0103:
            r0 = move-exception
            r7 = r3
            goto L_0x0159
        L_0x0106:
            r7 = r12
            r0.getBytes(r3, r14, r15, r2)     // Catch:{ all -> 0x0155 }
        L_0x010a:
            java.util.zip.Checksum r3 = r1.checksum     // Catch:{ all -> 0x0155 }
            boolean r8 = r1.hasChecksum     // Catch:{ all -> 0x0155 }
            if (r8 == 0) goto L_0x013e
            if (r3 == 0) goto L_0x013e
            r3.reset()     // Catch:{ all -> 0x0155 }
            r3.update(r14, r15, r13)     // Catch:{ all -> 0x0155 }
            long r8 = r3.getValue()     // Catch:{ all -> 0x0155 }
            int r3 = (int) r8     // Catch:{ all -> 0x0155 }
            int r8 = r1.currentChecksum     // Catch:{ all -> 0x0155 }
            if (r3 != r8) goto L_0x0122
            goto L_0x013e
        L_0x0122:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ all -> 0x0155 }
            java.lang.String r2 = "stream corrupted: mismatching checksum: %d (expected: %d)"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0155 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0155 }
            r4[r6] = r3     // Catch:{ all -> 0x0155 }
            int r3 = r1.currentChecksum     // Catch:{ all -> 0x0155 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0155 }
            r4[r5] = r3     // Catch:{ all -> 0x0155 }
            java.lang.String r2 = java.lang.String.format(r2, r4)     // Catch:{ all -> 0x0155 }
            r0.<init>(r2)     // Catch:{ all -> 0x0155 }
            throw r0     // Catch:{ all -> 0x0155 }
        L_0x013e:
            if (r7 == 0) goto L_0x014d
            int r3 = r7.writerIndex()     // Catch:{ all -> 0x0155 }
            int r3 = r3 + r13
            r7.writerIndex(r3)     // Catch:{ all -> 0x0155 }
            r3 = r19
            r3.add(r7)     // Catch:{ all -> 0x0155 }
        L_0x014d:
            r0.skipBytes(r2)     // Catch:{ all -> 0x0155 }
            io.netty.handler.codec.compression.FastLzFrameDecoder$State r0 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.INIT_BLOCK     // Catch:{ all -> 0x0155 }
            r1.currentState = r0     // Catch:{ all -> 0x0155 }
        L_0x0154:
            return
        L_0x0155:
            r0 = move-exception
            goto L_0x0159
        L_0x0157:
            r0 = move-exception
            r7 = r12
        L_0x0159:
            r7.release()     // Catch:{ Exception -> 0x0165 }
            throw r0     // Catch:{ Exception -> 0x0165 }
        L_0x015d:
            io.netty.handler.codec.compression.DecompressionException r0 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x0165 }
            java.lang.String r2 = "unexpected block identifier"
            r0.<init>(r2)     // Catch:{ Exception -> 0x0165 }
            throw r0     // Catch:{ Exception -> 0x0165 }
        L_0x0165:
            r0 = move-exception
            io.netty.handler.codec.compression.FastLzFrameDecoder$State r2 = p043io.netty.handler.codec.compression.FastLzFrameDecoder.State.CORRUPTED
            r1.currentState = r2
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.FastLzFrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
