package p043io.netty.handler.codec.compression;

import com.google.common.base.Ascii;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ChannelPromiseNotifier;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.handler.codec.compression.JdkZlibEncoder */
public class JdkZlibEncoder extends ZlibEncoder {
    private static final byte[] gzipHeader = {Ascii.f1878US, -117, 8, 0, 0, 0, 0, 0, 0, 0};
    private final CRC32 crc;
    private volatile ChannelHandlerContext ctx;
    private final Deflater deflater;
    private volatile boolean finished;
    private final ZlibWrapper wrapper;
    private boolean writeHeader;

    /* renamed from: io.netty.handler.codec.compression.JdkZlibEncoder$4 */
    static /* synthetic */ class C56914 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = new int[ZlibWrapper.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                io.netty.handler.codec.compression.ZlibWrapper[] r0 = p043io.netty.handler.codec.compression.ZlibWrapper.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = r0
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.GZIP     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.ZLIB     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.JdkZlibEncoder.C56914.<clinit>():void");
        }
    }

    public JdkZlibEncoder() {
        this(6);
    }

    public JdkZlibEncoder(int i) {
        this(ZlibWrapper.ZLIB, i);
    }

    public JdkZlibEncoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }

    public JdkZlibEncoder(ZlibWrapper zlibWrapper, int i) {
        this.crc = new CRC32();
        boolean z = true;
        this.writeHeader = true;
        if (i < 0 || i > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("compressionLevel: ");
            sb.append(i);
            sb.append(" (expected: 0-9)");
            throw new IllegalArgumentException(sb.toString());
        } else if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (zlibWrapper != ZlibWrapper.ZLIB_OR_NONE) {
            this.wrapper = zlibWrapper;
            if (zlibWrapper == ZlibWrapper.ZLIB) {
                z = false;
            }
            this.deflater = new Deflater(i, z);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("wrapper '");
            sb2.append(ZlibWrapper.ZLIB_OR_NONE);
            sb2.append("' is not allowed for compression.");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public JdkZlibEncoder(byte[] bArr) {
        this(6, bArr);
    }

    public JdkZlibEncoder(int i, byte[] bArr) {
        this.crc = new CRC32();
        this.writeHeader = true;
        if (i < 0 || i > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("compressionLevel: ");
            sb.append(i);
            sb.append(" (expected: 0-9)");
            throw new IllegalArgumentException(sb.toString());
        } else if (bArr != null) {
            this.wrapper = ZlibWrapper.ZLIB;
            this.deflater = new Deflater(i);
            this.deflater.setDictionary(bArr);
        } else {
            throw new NullPointerException("dictionary");
        }
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
        final ChannelPromise newPromise = ctx2.newPromise();
        executor.execute(new Runnable() {
            public void run() {
                JdkZlibEncoder jdkZlibEncoder = JdkZlibEncoder.this;
                jdkZlibEncoder.finishEncode(jdkZlibEncoder.ctx(), newPromise).addListener(new ChannelPromiseNotifier(channelPromise));
            }
        });
        return newPromise;
    }

    /* access modifiers changed from: private */
    public ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i;
        byte[] bArr;
        if (this.finished) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            if (byteBuf.hasArray()) {
                bArr = byteBuf.array();
                i = byteBuf.arrayOffset() + byteBuf.readerIndex();
                byteBuf.skipBytes(readableBytes);
            } else {
                bArr = new byte[readableBytes];
                byteBuf.readBytes(bArr);
                i = 0;
            }
            if (this.writeHeader) {
                this.writeHeader = false;
                if (this.wrapper == ZlibWrapper.GZIP) {
                    byteBuf2.writeBytes(gzipHeader);
                }
            }
            if (this.wrapper == ZlibWrapper.GZIP) {
                this.crc.update(bArr, i, readableBytes);
            }
            this.deflater.setInput(bArr, i, readableBytes);
            while (!this.deflater.needsInput()) {
                deflate(byteBuf2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean z) throws Exception {
        double readableBytes = (double) byteBuf.readableBytes();
        Double.isNaN(readableBytes);
        int ceil = ((int) Math.ceil(readableBytes * 1.001d)) + 12;
        if (this.writeHeader) {
            int i = C56914.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[this.wrapper.ordinal()];
            if (i == 1) {
                ceil += gzipHeader.length;
            } else if (i == 2) {
                ceil += 2;
            }
        }
        return channelHandlerContext.alloc().heapBuffer(ceil);
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

    /* access modifiers changed from: private */
    public ChannelFuture finishEncode(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer();
        if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
            this.writeHeader = false;
            heapBuffer.writeBytes(gzipHeader);
        }
        this.deflater.finish();
        while (!this.deflater.finished()) {
            deflate(heapBuffer);
            if (!heapBuffer.isWritable()) {
                channelHandlerContext.write(heapBuffer);
                heapBuffer = channelHandlerContext.alloc().heapBuffer();
            }
        }
        if (this.wrapper == ZlibWrapper.GZIP) {
            int value = (int) this.crc.getValue();
            int totalIn = this.deflater.getTotalIn();
            heapBuffer.writeByte(value);
            heapBuffer.writeByte(value >>> 8);
            heapBuffer.writeByte(value >>> 16);
            heapBuffer.writeByte(value >>> 24);
            heapBuffer.writeByte(totalIn);
            heapBuffer.writeByte(totalIn >>> 8);
            heapBuffer.writeByte(totalIn >>> 16);
            heapBuffer.writeByte(totalIn >>> 24);
        }
        this.deflater.end();
        return channelHandlerContext.writeAndFlush(heapBuffer, channelPromise);
    }

    private void deflate(ByteBuf byteBuf) {
        int deflate;
        do {
            int writerIndex = byteBuf.writerIndex();
            deflate = this.deflater.deflate(byteBuf.array(), byteBuf.arrayOffset() + writerIndex, byteBuf.writableBytes(), 2);
            byteBuf.writerIndex(writerIndex + deflate);
        } while (deflate > 0);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }
}
