package p043io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import p043io.netty.handler.codec.ByteToMessageDecoder;

/* renamed from: io.netty.handler.codec.compression.LzfDecoder */
public class LzfDecoder extends ByteToMessageDecoder {
    private static final short MAGIC_NUMBER = 23126;
    private int chunkLength;
    private State currentState;
    private ChunkDecoder decoder;
    private boolean isCompressed;
    private int originalLength;
    private BufferRecycler recycler;

    /* renamed from: io.netty.handler.codec.compression.LzfDecoder$1 */
    static /* synthetic */ class C56961 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.handler.codec.compression.LzfDecoder$State[] r0 = p043io.netty.handler.codec.compression.LzfDecoder.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State = r0
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.INIT_BLOCK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.INIT_ORIGINAL_LENGTH     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.DECOMPRESS_DATA     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.CORRUPTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.LzfDecoder.C56961.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.LzfDecoder$State */
    private enum State {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public LzfDecoder() {
        this(false);
    }

    public LzfDecoder(boolean z) {
        ChunkDecoder chunkDecoder;
        this.currentState = State.INIT_BLOCK;
        if (z) {
            chunkDecoder = ChunkDecoderFactory.safeInstance();
        } else {
            chunkDecoder = ChunkDecoderFactory.optimalInstance();
        }
        this.decoder = chunkDecoder;
        this.recycler = BufferRecycler.instance();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0090 A[Catch:{ all -> 0x00f0, Exception -> 0x010b }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0092 A[Catch:{ all -> 0x00f0, Exception -> 0x010b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r12, p043io.netty.buffer.ByteBuf r13, java.util.List<java.lang.Object> r14) throws java.lang.Exception {
        /*
            r11 = this;
            int[] r0 = p043io.netty.handler.codec.compression.LzfDecoder.C56961.$SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State     // Catch:{ Exception -> 0x010b }
            io.netty.handler.codec.compression.LzfDecoder$State r1 = r11.currentState     // Catch:{ Exception -> 0x010b }
            int r1 = r1.ordinal()     // Catch:{ Exception -> 0x010b }
            r0 = r0[r1]     // Catch:{ Exception -> 0x010b }
            r1 = 3
            r2 = 2
            r3 = 0
            r4 = 1
            if (r0 == r4) goto L_0x0026
            if (r0 == r2) goto L_0x0076
            if (r0 == r1) goto L_0x0088
            r12 = 4
            if (r0 != r12) goto L_0x0020
            int r12 = r13.readableBytes()     // Catch:{ Exception -> 0x010b }
            r13.skipBytes(r12)     // Catch:{ Exception -> 0x010b }
            goto L_0x0102
        L_0x0020:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x010b }
            r12.<init>()     // Catch:{ Exception -> 0x010b }
            throw r12     // Catch:{ Exception -> 0x010b }
        L_0x0026:
            int r0 = r13.readableBytes()     // Catch:{ Exception -> 0x010b }
            r5 = 5
            if (r0 >= r5) goto L_0x002f
            goto L_0x0102
        L_0x002f:
            int r0 = r13.readUnsignedShort()     // Catch:{ Exception -> 0x010b }
            r5 = 23126(0x5a56, float:3.2406E-41)
            if (r0 != r5) goto L_0x0103
            byte r0 = r13.readByte()     // Catch:{ Exception -> 0x010b }
            if (r0 == 0) goto L_0x0066
            if (r0 != r4) goto L_0x0046
            r11.isCompressed = r4     // Catch:{ Exception -> 0x010b }
            io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.INIT_ORIGINAL_LENGTH     // Catch:{ Exception -> 0x010b }
            r11.currentState = r1     // Catch:{ Exception -> 0x010b }
            goto L_0x006c
        L_0x0046:
            io.netty.handler.codec.compression.DecompressionException r12 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x010b }
            java.lang.String r13 = "unknown type of chunk: %d (expected: %d or %d)"
            java.lang.Object[] r14 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x010b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x010b }
            r14[r3] = r0     // Catch:{ Exception -> 0x010b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x010b }
            r14[r4] = r0     // Catch:{ Exception -> 0x010b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x010b }
            r14[r2] = r0     // Catch:{ Exception -> 0x010b }
            java.lang.String r13 = java.lang.String.format(r13, r14)     // Catch:{ Exception -> 0x010b }
            r12.<init>(r13)     // Catch:{ Exception -> 0x010b }
            throw r12     // Catch:{ Exception -> 0x010b }
        L_0x0066:
            r11.isCompressed = r3     // Catch:{ Exception -> 0x010b }
            io.netty.handler.codec.compression.LzfDecoder$State r1 = p043io.netty.handler.codec.compression.LzfDecoder.State.DECOMPRESS_DATA     // Catch:{ Exception -> 0x010b }
            r11.currentState = r1     // Catch:{ Exception -> 0x010b }
        L_0x006c:
            int r1 = r13.readUnsignedShort()     // Catch:{ Exception -> 0x010b }
            r11.chunkLength = r1     // Catch:{ Exception -> 0x010b }
            if (r0 == r4) goto L_0x0076
            goto L_0x0102
        L_0x0076:
            int r0 = r13.readableBytes()     // Catch:{ Exception -> 0x010b }
            if (r0 >= r2) goto L_0x007e
            goto L_0x0102
        L_0x007e:
            int r0 = r13.readUnsignedShort()     // Catch:{ Exception -> 0x010b }
            r11.originalLength = r0     // Catch:{ Exception -> 0x010b }
            io.netty.handler.codec.compression.LzfDecoder$State r0 = p043io.netty.handler.codec.compression.LzfDecoder.State.DECOMPRESS_DATA     // Catch:{ Exception -> 0x010b }
            r11.currentState = r0     // Catch:{ Exception -> 0x010b }
        L_0x0088:
            int r0 = r11.chunkLength     // Catch:{ Exception -> 0x010b }
            int r1 = r13.readableBytes()     // Catch:{ Exception -> 0x010b }
            if (r1 >= r0) goto L_0x0092
            goto L_0x0102
        L_0x0092:
            int r1 = r11.originalLength     // Catch:{ Exception -> 0x010b }
            boolean r2 = r11.isCompressed     // Catch:{ Exception -> 0x010b }
            if (r2 == 0) goto L_0x00f5
            int r2 = r13.readerIndex()     // Catch:{ Exception -> 0x010b }
            boolean r4 = r13.hasArray()     // Catch:{ Exception -> 0x010b }
            if (r4 == 0) goto L_0x00ad
            byte[] r3 = r13.array()     // Catch:{ Exception -> 0x010b }
            int r4 = r13.arrayOffset()     // Catch:{ Exception -> 0x010b }
            int r2 = r2 + r4
            r7 = r2
            goto L_0x00b8
        L_0x00ad:
            com.ning.compress.BufferRecycler r4 = r11.recycler     // Catch:{ Exception -> 0x010b }
            byte[] r4 = r4.allocInputBuffer(r0)     // Catch:{ Exception -> 0x010b }
            r13.getBytes(r2, r4, r3, r0)     // Catch:{ Exception -> 0x010b }
            r3 = r4
            r7 = 0
        L_0x00b8:
            io.netty.buffer.ByteBufAllocator r12 = r12.alloc()     // Catch:{ Exception -> 0x010b }
            io.netty.buffer.ByteBuf r12 = r12.heapBuffer(r1, r1)     // Catch:{ Exception -> 0x010b }
            byte[] r8 = r12.array()     // Catch:{ Exception -> 0x010b }
            int r2 = r12.arrayOffset()     // Catch:{ Exception -> 0x010b }
            int r4 = r12.writerIndex()     // Catch:{ Exception -> 0x010b }
            int r9 = r2 + r4
            com.ning.compress.lzf.ChunkDecoder r5 = r11.decoder     // Catch:{ all -> 0x00f0 }
            int r10 = r9 + r1
            r6 = r3
            r5.decodeChunk(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x00f0 }
            int r2 = r12.writerIndex()     // Catch:{ all -> 0x00f0 }
            int r2 = r2 + r1
            r12.writerIndex(r2)     // Catch:{ all -> 0x00f0 }
            r14.add(r12)     // Catch:{ all -> 0x00f0 }
            r13.skipBytes(r0)     // Catch:{ all -> 0x00f0 }
            boolean r12 = r13.hasArray()     // Catch:{ Exception -> 0x010b }
            if (r12 != 0) goto L_0x00fe
            com.ning.compress.BufferRecycler r12 = r11.recycler     // Catch:{ Exception -> 0x010b }
            r12.releaseInputBuffer(r3)     // Catch:{ Exception -> 0x010b }
            goto L_0x00fe
        L_0x00f0:
            r13 = move-exception
            r12.release()     // Catch:{ Exception -> 0x010b }
            throw r13     // Catch:{ Exception -> 0x010b }
        L_0x00f5:
            if (r0 <= 0) goto L_0x00fe
            io.netty.buffer.ByteBuf r12 = r13.readRetainedSlice(r0)     // Catch:{ Exception -> 0x010b }
            r14.add(r12)     // Catch:{ Exception -> 0x010b }
        L_0x00fe:
            io.netty.handler.codec.compression.LzfDecoder$State r12 = p043io.netty.handler.codec.compression.LzfDecoder.State.INIT_BLOCK     // Catch:{ Exception -> 0x010b }
            r11.currentState = r12     // Catch:{ Exception -> 0x010b }
        L_0x0102:
            return
        L_0x0103:
            io.netty.handler.codec.compression.DecompressionException r12 = new io.netty.handler.codec.compression.DecompressionException     // Catch:{ Exception -> 0x010b }
            java.lang.String r13 = "unexpected block identifier"
            r12.<init>(r13)     // Catch:{ Exception -> 0x010b }
            throw r12     // Catch:{ Exception -> 0x010b }
        L_0x010b:
            r12 = move-exception
            io.netty.handler.codec.compression.LzfDecoder$State r13 = p043io.netty.handler.codec.compression.LzfDecoder.State.CORRUPTED
            r11.currentState = r13
            r13 = 0
            r11.decoder = r13
            r11.recycler = r13
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.LzfDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
