package p043io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import java.util.concurrent.TimeUnit;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ChannelPromiseNotifier;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.internal.EmptyArrays;

/* renamed from: io.netty.handler.codec.compression.JZlibEncoder */
public class JZlibEncoder extends ZlibEncoder {
    private volatile ChannelHandlerContext ctx;
    private volatile boolean finished;
    private final int wrapperOverhead;

    /* renamed from: z */
    private final Deflater f3723z;

    public JZlibEncoder() {
        this(6);
    }

    public JZlibEncoder(int i) {
        this(ZlibWrapper.ZLIB, i);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int i) {
        this(zlibWrapper, i, 15, 8);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int i, int i2, int i3) {
        this.f3723z = new Deflater();
        if (i < 0 || i > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("compressionLevel: ");
            sb.append(i);
            sb.append(" (expected: 0-9)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < 9 || i2 > 15) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("windowBits: ");
            sb2.append(i2);
            sb2.append(" (expected: 9-15)");
            throw new IllegalArgumentException(sb2.toString());
        } else if (i3 < 1 || i3 > 9) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("memLevel: ");
            sb3.append(i3);
            sb3.append(" (expected: 1-9)");
            throw new IllegalArgumentException(sb3.toString());
        } else if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (zlibWrapper != ZlibWrapper.ZLIB_OR_NONE) {
            int init = this.f3723z.init(i, i2, i3, ZlibUtil.convertWrapperType(zlibWrapper));
            if (init != 0) {
                ZlibUtil.fail(this.f3723z, "initialization failure", init);
            }
            this.wrapperOverhead = ZlibUtil.wrapperOverhead(zlibWrapper);
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("wrapper '");
            sb4.append(ZlibWrapper.ZLIB_OR_NONE);
            sb4.append("' is not allowed for compression.");
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    public JZlibEncoder(byte[] bArr) {
        this(6, bArr);
    }

    public JZlibEncoder(int i, byte[] bArr) {
        this(i, 15, 8, bArr);
    }

    public JZlibEncoder(int i, int i2, int i3, byte[] bArr) {
        this.f3723z = new Deflater();
        if (i < 0 || i > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("compressionLevel: ");
            sb.append(i);
            sb.append(" (expected: 0-9)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < 9 || i2 > 15) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("windowBits: ");
            sb2.append(i2);
            sb2.append(" (expected: 9-15)");
            throw new IllegalArgumentException(sb2.toString());
        } else if (i3 < 1 || i3 > 9) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("memLevel: ");
            sb3.append(i3);
            sb3.append(" (expected: 1-9)");
            throw new IllegalArgumentException(sb3.toString());
        } else if (bArr != null) {
            int deflateInit = this.f3723z.deflateInit(i, i2, i3, JZlib.W_ZLIB);
            if (deflateInit != 0) {
                ZlibUtil.fail(this.f3723z, "initialization failure", deflateInit);
            } else {
                int deflateSetDictionary = this.f3723z.deflateSetDictionary(bArr, bArr.length);
                if (deflateSetDictionary != 0) {
                    ZlibUtil.fail(this.f3723z, "failed to set the dictionary", deflateSetDictionary);
                }
            }
            this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
        } else {
            throw new NullPointerException("dictionary");
        }
    }

    public ChannelFuture close() {
        return close(ctx().channel().newPromise());
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
                JZlibEncoder jZlibEncoder = JZlibEncoder.this;
                jZlibEncoder.finishEncode(jZlibEncoder.ctx(), newPromise).addListener(new ChannelPromiseNotifier(channelPromise));
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
        if (this.finished) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            try {
                boolean hasArray = byteBuf.hasArray();
                this.f3723z.avail_in = readableBytes;
                if (hasArray) {
                    this.f3723z.next_in = byteBuf.array();
                    this.f3723z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.f3723z.next_in = bArr;
                    this.f3723z.next_in_index = 0;
                }
                i = this.f3723z.next_in_index;
                double d = (double) readableBytes;
                Double.isNaN(d);
                int ceil = ((int) Math.ceil(d * 1.001d)) + 12 + this.wrapperOverhead;
                byteBuf2.ensureWritable(ceil);
                this.f3723z.avail_out = ceil;
                this.f3723z.next_out = byteBuf2.array();
                this.f3723z.next_out_index = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
                int i2 = this.f3723z.next_out_index;
                int deflate = this.f3723z.deflate(2);
                byteBuf.skipBytes(this.f3723z.next_in_index - i);
                if (deflate != 0) {
                    ZlibUtil.fail(this.f3723z, "compression failure", deflate);
                }
                int i3 = this.f3723z.next_out_index - i2;
                if (i3 > 0) {
                    byteBuf2.writerIndex(byteBuf2.writerIndex() + i3);
                }
                Deflater deflater = this.f3723z;
                deflater.next_in = null;
                deflater.next_out = null;
            } catch (Throwable th) {
                Deflater deflater2 = this.f3723z;
                deflater2.next_in = null;
                deflater2.next_out = null;
                throw th;
            }
        }
    }

    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
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
        ByteBuf byteBuf;
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        try {
            this.f3723z.next_in = EmptyArrays.EMPTY_BYTES;
            this.f3723z.next_in_index = 0;
            this.f3723z.avail_in = 0;
            byte[] bArr = new byte[32];
            this.f3723z.next_out = bArr;
            this.f3723z.next_out_index = 0;
            this.f3723z.avail_out = bArr.length;
            int deflate = this.f3723z.deflate(4);
            if (deflate == 0 || deflate == 1) {
                if (this.f3723z.next_out_index != 0) {
                    byteBuf = Unpooled.wrappedBuffer(bArr, 0, this.f3723z.next_out_index);
                } else {
                    byteBuf = Unpooled.EMPTY_BUFFER;
                }
                this.f3723z.deflateEnd();
                Deflater deflater = this.f3723z;
                deflater.next_in = null;
                deflater.next_out = null;
                return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
            }
            channelPromise.setFailure(ZlibUtil.deflaterException(this.f3723z, "compression failure", deflate));
            return channelPromise;
        } finally {
            this.f3723z.deflateEnd();
            Deflater deflater2 = this.f3723z;
            deflater2.next_in = null;
            deflater2.next_out = null;
        }
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }
}
