package p043io.netty.handler.codec.compression;

import java.util.concurrent.TimeUnit;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ChannelPromiseNotifier;
import p043io.netty.handler.codec.MessageToByteEncoder;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.handler.codec.compression.Bzip2Encoder */
public class Bzip2Encoder extends MessageToByteEncoder<ByteBuf> {
    private Bzip2BlockCompressor blockCompressor;
    private volatile ChannelHandlerContext ctx;
    private State currentState;
    private volatile boolean finished;
    private final int streamBlockSize;
    private int streamCRC;
    private final Bzip2BitWriter writer;

    /* renamed from: io.netty.handler.codec.compression.Bzip2Encoder$4 */
    static /* synthetic */ class C56824 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.handler.codec.compression.Bzip2Encoder$State[] r0 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State = r0
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.Bzip2Encoder$State r1 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.INIT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.Bzip2Encoder$State r1 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.INIT_BLOCK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.Bzip2Encoder$State r1 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.WRITE_DATA     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.Bzip2Encoder$State r1 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.CLOSE_BLOCK     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2Encoder.C56824.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.Bzip2Encoder$State */
    private enum State {
        INIT,
        INIT_BLOCK,
        WRITE_DATA,
        CLOSE_BLOCK
    }

    public Bzip2Encoder() {
        this(9);
    }

    public Bzip2Encoder(int i) {
        this.currentState = State.INIT;
        this.writer = new Bzip2BitWriter();
        if (i < 1 || i > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("blockSizeMultiplier: ");
            sb.append(i);
            sb.append(" (expected: 1-9)");
            throw new IllegalArgumentException(sb.toString());
        }
        this.streamBlockSize = i * 100000;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0052 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.channel.ChannelHandlerContext r3, p043io.netty.buffer.ByteBuf r4, p043io.netty.buffer.ByteBuf r5) throws java.lang.Exception {
        /*
            r2 = this;
            boolean r3 = r2.finished
            if (r3 == 0) goto L_0x0008
            r5.writeBytes(r4)
            return
        L_0x0008:
            int[] r3 = p043io.netty.handler.codec.compression.Bzip2Encoder.C56824.$SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State
            io.netty.handler.codec.compression.Bzip2Encoder$State r0 = r2.currentState
            int r0 = r0.ordinal()
            r3 = r3[r0]
            r0 = 1
            r1 = 4
            if (r3 == r0) goto L_0x0025
            r0 = 2
            if (r3 == r0) goto L_0x003d
            r0 = 3
            if (r3 == r0) goto L_0x004c
            if (r3 != r1) goto L_0x001f
            goto L_0x007e
        L_0x001f:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            throw r3
        L_0x0025:
            r5.ensureWritable(r1)
            r3 = 4348520(0x425a68, float:6.093574E-39)
            r5.writeMedium(r3)
            int r3 = r2.streamBlockSize
            r0 = 100000(0x186a0, float:1.4013E-40)
            int r3 = r3 / r0
            int r3 = r3 + 48
            r5.writeByte(r3)
            io.netty.handler.codec.compression.Bzip2Encoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.INIT_BLOCK
            r2.currentState = r3
        L_0x003d:
            io.netty.handler.codec.compression.Bzip2BlockCompressor r3 = new io.netty.handler.codec.compression.Bzip2BlockCompressor
            io.netty.handler.codec.compression.Bzip2BitWriter r0 = r2.writer
            int r1 = r2.streamBlockSize
            r3.<init>(r0, r1)
            r2.blockCompressor = r3
            io.netty.handler.codec.compression.Bzip2Encoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.WRITE_DATA
            r2.currentState = r3
        L_0x004c:
            boolean r3 = r4.isReadable()
            if (r3 != 0) goto L_0x0053
            return
        L_0x0053:
            io.netty.handler.codec.compression.Bzip2BlockCompressor r3 = r2.blockCompressor
            int r0 = r4.readableBytes()
            int r1 = r3.availableSize()
            int r0 = java.lang.Math.min(r0, r1)
            int r1 = r4.readerIndex()
            int r0 = r3.write(r4, r1, r0)
            r4.skipBytes(r0)
            boolean r3 = r3.isFull()
            if (r3 != 0) goto L_0x007a
            boolean r3 = r4.isReadable()
            if (r3 == 0) goto L_0x0079
            goto L_0x0008
        L_0x0079:
            return
        L_0x007a:
            io.netty.handler.codec.compression.Bzip2Encoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.CLOSE_BLOCK
            r2.currentState = r3
        L_0x007e:
            r2.closeBlock(r5)
            io.netty.handler.codec.compression.Bzip2Encoder$State r3 = p043io.netty.handler.codec.compression.Bzip2Encoder.State.INIT_BLOCK
            r2.currentState = r3
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2Encoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }

    private void closeBlock(ByteBuf byteBuf) {
        Bzip2BlockCompressor bzip2BlockCompressor = this.blockCompressor;
        if (!bzip2BlockCompressor.isEmpty()) {
            bzip2BlockCompressor.close(byteBuf);
            int crc = bzip2BlockCompressor.crc();
            int i = this.streamCRC;
            this.streamCRC = crc ^ ((i >>> 31) | (i << 1));
        }
    }

    public boolean isClosed() {
        return this.finished;
    }

    public ChannelFuture close() {
        return close(ctx().newPromise());
    }

    public ChannelFuture close(final ChannelPromise channelPromise) {
        ChannelHandlerContext ctx2 = ctx();
        EventExecutor executor = ctx2.executor();
        if (executor.inEventLoop()) {
            return finishEncode(ctx2, channelPromise);
        }
        executor.execute(new Runnable() {
            public void run() {
                Bzip2Encoder bzip2Encoder = Bzip2Encoder.this;
                bzip2Encoder.finishEncode(bzip2Encoder.ctx(), channelPromise).addListener(new ChannelPromiseNotifier(channelPromise));
            }
        });
        return channelPromise;
    }

    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        ChannelFuture finishEncode = finishEncode(channelHandlerContext, channelHandlerContext.newPromise());
        finishEncode.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                channelHandlerContext.close(channelPromise);
            }
        });
        if (!finishEncode.isDone()) {
            channelHandlerContext.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, 10, TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public ChannelFuture finishEncode(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        closeBlock(buffer);
        int i = this.streamCRC;
        Bzip2BitWriter bzip2BitWriter = this.writer;
        try {
            bzip2BitWriter.writeBits(buffer, 24, 1536581);
            bzip2BitWriter.writeBits(buffer, 24, 3690640);
            bzip2BitWriter.writeInt(buffer, i);
            bzip2BitWriter.flush(buffer);
            this.blockCompressor = null;
            return channelHandlerContext.writeAndFlush(buffer, channelPromise);
        } catch (Throwable th) {
            this.blockCompressor = null;
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }
}
