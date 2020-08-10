package p043io.netty.handler.ssl;

import java.net.SocketAddress;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelOutboundHandler;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.handler.codec.DecoderException;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.AbstractSniHandler */
public abstract class AbstractSniHandler<T> extends ByteToMessageDecoder implements ChannelOutboundHandler {
    private static final int MAX_SSL_RECORDS = 4;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractSniHandler.class);
    private boolean handshakeFailed;
    /* access modifiers changed from: private */
    public boolean readPending;
    /* access modifiers changed from: private */
    public boolean suppressRead;

    /* access modifiers changed from: protected */
    public abstract Future<T> lookup(ChannelHandlerContext channelHandlerContext, String str) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void onLookupComplete(ChannelHandlerContext channelHandlerContext, String str, Future<T> future) throws Exception;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00c0, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r8, p043io.netty.buffer.ByteBuf r9, java.util.List<java.lang.Object> r10) throws java.lang.Exception {
        /*
            r7 = this;
            boolean r10 = r7.suppressRead
            if (r10 != 0) goto L_0x010f
            boolean r10 = r7.handshakeFailed
            if (r10 != 0) goto L_0x010f
            int r10 = r9.writerIndex()
            r0 = 0
        L_0x000d:
            r1 = 4
            if (r0 >= r1) goto L_0x010b
            int r2 = r9.readerIndex()     // Catch:{ all -> 0x00e8 }
            int r3 = r10 - r2
            r4 = 5
            if (r3 >= r4) goto L_0x001a
            return
        L_0x001a:
            short r5 = r9.getUnsignedByte(r2)     // Catch:{ all -> 0x00e8 }
            r6 = 1
            switch(r5) {
                case 20: goto L_0x00aa;
                case 21: goto L_0x00aa;
                case 22: goto L_0x0024;
                default: goto L_0x0022;
            }     // Catch:{ all -> 0x00e8 }
        L_0x0022:
            goto L_0x010b
        L_0x0024:
            int r10 = r2 + 1
            short r10 = r9.getUnsignedByte(r10)     // Catch:{ all -> 0x00e8 }
            r0 = 3
            if (r10 != r0) goto L_0x010b
            int r10 = r2 + 3
            int r10 = r9.getUnsignedShort(r10)     // Catch:{ all -> 0x00e8 }
            int r10 = r10 + r4
            if (r3 >= r10) goto L_0x0037
            return
        L_0x0037:
            int r10 = r10 + r2
            int r2 = r2 + 43
            int r3 = r10 - r2
            r4 = 6
            if (r3 >= r4) goto L_0x0041
            goto L_0x010b
        L_0x0041:
            short r3 = r9.getUnsignedByte(r2)     // Catch:{ all -> 0x00e8 }
            int r3 = r3 + r6
            int r2 = r2 + r3
            int r3 = r9.getUnsignedShort(r2)     // Catch:{ all -> 0x00e8 }
            int r3 = r3 + 2
            int r2 = r2 + r3
            short r3 = r9.getUnsignedByte(r2)     // Catch:{ all -> 0x00e8 }
            int r3 = r3 + r6
            int r2 = r2 + r3
            int r3 = r9.getUnsignedShort(r2)     // Catch:{ all -> 0x00e8 }
            int r2 = r2 + 2
            int r3 = r3 + r2
            if (r3 <= r10) goto L_0x005f
            goto L_0x010b
        L_0x005f:
            int r10 = r3 - r2
            if (r10 >= r1) goto L_0x0065
            goto L_0x010b
        L_0x0065:
            int r10 = r9.getUnsignedShort(r2)     // Catch:{ all -> 0x00e8 }
            int r2 = r2 + 2
            int r4 = r9.getUnsignedShort(r2)     // Catch:{ all -> 0x00e8 }
            int r2 = r2 + 2
            int r5 = r3 - r2
            if (r5 >= r4) goto L_0x0077
            goto L_0x010b
        L_0x0077:
            if (r10 != 0) goto L_0x00a8
            int r2 = r2 + 2
            int r10 = r3 - r2
            if (r10 >= r0) goto L_0x0081
            goto L_0x010b
        L_0x0081:
            short r10 = r9.getUnsignedByte(r2)     // Catch:{ all -> 0x00e8 }
            int r2 = r2 + r6
            if (r10 != 0) goto L_0x010b
            int r10 = r9.getUnsignedShort(r2)     // Catch:{ all -> 0x00e8 }
            int r2 = r2 + 2
            int r3 = r3 - r2
            if (r3 >= r10) goto L_0x0093
            goto L_0x010b
        L_0x0093:
            java.nio.charset.Charset r0 = p043io.netty.util.CharsetUtil.US_ASCII     // Catch:{ all -> 0x00e8 }
            java.lang.String r10 = r9.toString(r2, r10, r0)     // Catch:{ all -> 0x00e8 }
            java.util.Locale r0 = java.util.Locale.US     // Catch:{ all -> 0x00a3 }
            java.lang.String r10 = r10.toLowerCase(r0)     // Catch:{ all -> 0x00a3 }
            r7.select(r8, r10)     // Catch:{ all -> 0x00a3 }
            goto L_0x00a7
        L_0x00a3:
            r10 = move-exception
            p043io.netty.util.internal.PlatformDependent.throwException(r10)     // Catch:{ all -> 0x00e8 }
        L_0x00a7:
            return
        L_0x00a8:
            int r2 = r2 + r4
            goto L_0x005f
        L_0x00aa:
            int r1 = p043io.netty.handler.ssl.SslUtils.getEncryptedPacketLength(r9, r2)     // Catch:{ all -> 0x00e8 }
            r2 = -2
            if (r1 == r2) goto L_0x00c1
            r2 = -1
            if (r1 == r2) goto L_0x00c0
            int r3 = r3 + -5
            if (r3 >= r1) goto L_0x00b9
            goto L_0x00c0
        L_0x00b9:
            r9.skipBytes(r1)     // Catch:{ all -> 0x00e8 }
            int r0 = r0 + 1
            goto L_0x000d
        L_0x00c0:
            return
        L_0x00c1:
            r7.handshakeFailed = r6     // Catch:{ all -> 0x00e8 }
            io.netty.handler.ssl.NotSslRecordException r10 = new io.netty.handler.ssl.NotSslRecordException     // Catch:{ all -> 0x00e8 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e8 }
            r0.<init>()     // Catch:{ all -> 0x00e8 }
            java.lang.String r1 = "not an SSL/TLS record: "
            r0.append(r1)     // Catch:{ all -> 0x00e8 }
            java.lang.String r1 = p043io.netty.buffer.ByteBufUtil.hexDump(r9)     // Catch:{ all -> 0x00e8 }
            r0.append(r1)     // Catch:{ all -> 0x00e8 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00e8 }
            r10.<init>(r0)     // Catch:{ all -> 0x00e8 }
            int r0 = r9.readableBytes()     // Catch:{ all -> 0x00e8 }
            r9.skipBytes(r0)     // Catch:{ all -> 0x00e8 }
            p043io.netty.handler.ssl.SslUtils.notifyHandshakeFailure(r8, r10)     // Catch:{ all -> 0x00e8 }
            throw r10     // Catch:{ all -> 0x00e8 }
        L_0x00e8:
            r10 = move-exception
            io.netty.util.internal.logging.InternalLogger r0 = logger
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x010b
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unexpected client hello packet: "
            r1.append(r2)
            java.lang.String r9 = p043io.netty.buffer.ByteBufUtil.hexDump(r9)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.debug(r9, r10)
        L_0x010b:
            r9 = 0
            r7.select(r8, r9)
        L_0x010f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.AbstractSniHandler.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private void select(final ChannelHandlerContext channelHandlerContext, final String str) throws Exception {
        Future lookup = lookup(channelHandlerContext, str);
        if (lookup.isDone()) {
            onLookupComplete(channelHandlerContext, str, lookup);
            return;
        }
        this.suppressRead = true;
        lookup.addListener(new FutureListener<T>() {
            public void operationComplete(Future<T> future) throws Exception {
                try {
                    AbstractSniHandler.this.suppressRead = false;
                    AbstractSniHandler.this.onLookupComplete(channelHandlerContext, str, future);
                } catch (DecoderException e) {
                    channelHandlerContext.fireExceptionCaught(e);
                } catch (Throwable th) {
                    if (AbstractSniHandler.this.readPending) {
                        AbstractSniHandler.this.readPending = false;
                        channelHandlerContext.read();
                    }
                    throw th;
                }
                if (AbstractSniHandler.this.readPending) {
                    AbstractSniHandler.this.readPending = false;
                    channelHandlerContext.read();
                }
            }
        });
    }

    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.suppressRead) {
            this.readPending = true;
        } else {
            channelHandlerContext.read();
        }
    }

    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(obj, channelPromise);
    }

    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }
}
