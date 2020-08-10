package p043io.netty.handler.stream;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelProgressivePromise;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.stream.ChunkedWriteHandler */
public class ChunkedWriteHandler extends ChannelDuplexHandler {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    private volatile ChannelHandlerContext ctx;
    private PendingWrite currentWrite;
    private final Queue<PendingWrite> queue = new ArrayDeque();

    /* renamed from: io.netty.handler.stream.ChunkedWriteHandler$PendingWrite */
    private static final class PendingWrite {
        final Object msg;
        final ChannelPromise promise;

        PendingWrite(Object obj, ChannelPromise channelPromise) {
            this.msg = obj;
            this.promise = channelPromise;
        }

        /* access modifiers changed from: 0000 */
        public void fail(Throwable th) {
            ReferenceCountUtil.release(this.msg);
            this.promise.tryFailure(th);
        }

        /* access modifiers changed from: 0000 */
        public void success(long j) {
            if (!this.promise.isDone()) {
                ChannelPromise channelPromise = this.promise;
                if (channelPromise instanceof ChannelProgressivePromise) {
                    ((ChannelProgressivePromise) channelPromise).tryProgress(j, j);
                }
                this.promise.trySuccess();
            }
        }

        /* access modifiers changed from: 0000 */
        public void progress(long j, long j2) {
            ChannelPromise channelPromise = this.promise;
            if (channelPromise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise) channelPromise).tryProgress(j, j2);
            }
        }
    }

    public ChunkedWriteHandler() {
    }

    @Deprecated
    public ChunkedWriteHandler(int i) {
        if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxPendingWrites: ");
            sb.append(i);
            sb.append(" (expected: > 0)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    public void resumeTransfer() {
        final ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null) {
            if (channelHandlerContext.executor().inEventLoop()) {
                try {
                    doFlush(channelHandlerContext);
                } catch (Exception e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Unexpected exception while sending chunks.", (Throwable) e);
                    }
                }
            } else {
                channelHandlerContext.executor().execute(new Runnable() {
                    public void run() {
                        try {
                            ChunkedWriteHandler.this.doFlush(channelHandlerContext);
                        } catch (Exception e) {
                            if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                                ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", (Throwable) e);
                            }
                        }
                    }
                });
            }
        }
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        this.queue.add(new PendingWrite(obj, channelPromise));
    }

    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        doFlush(channelHandlerContext);
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        doFlush(channelHandlerContext);
        channelHandlerContext.fireChannelInactive();
    }

    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isWritable()) {
            doFlush(channelHandlerContext);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    private void discard(Throwable th) {
        while (true) {
            PendingWrite pendingWrite = this.currentWrite;
            if (pendingWrite == null) {
                pendingWrite = (PendingWrite) this.queue.poll();
            } else {
                this.currentWrite = null;
            }
            if (pendingWrite != null) {
                Object obj = pendingWrite.msg;
                if (obj instanceof ChunkedInput) {
                    ChunkedInput chunkedInput = (ChunkedInput) obj;
                    try {
                        if (!chunkedInput.isEndOfInput()) {
                            if (th == null) {
                                th = new ClosedChannelException();
                            }
                            pendingWrite.fail(th);
                        } else {
                            pendingWrite.success(chunkedInput.length());
                        }
                        closeInput(chunkedInput);
                    } catch (Exception e) {
                        pendingWrite.fail(e);
                        InternalLogger internalLogger = logger;
                        StringBuilder sb = new StringBuilder();
                        sb.append(ChunkedInput.class.getSimpleName());
                        sb.append(".isEndOfInput() failed");
                        internalLogger.warn(sb.toString(), (Throwable) e);
                        closeInput(chunkedInput);
                    }
                } else {
                    if (th == null) {
                        th = new ClosedChannelException();
                    }
                    pendingWrite.fail(th);
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doFlush(p043io.netty.channel.ChannelHandlerContext r14) throws java.lang.Exception {
        /*
            r13 = this;
            io.netty.channel.Channel r6 = r14.channel()
            boolean r0 = r6.isActive()
            r7 = 0
            if (r0 != 0) goto L_0x000f
            r13.discard(r7)
            return
        L_0x000f:
            io.netty.buffer.ByteBufAllocator r8 = r14.alloc()
            r9 = 1
            r0 = 1
        L_0x0015:
            boolean r1 = r6.isWritable()
            if (r1 == 0) goto L_0x00a6
            io.netty.handler.stream.ChunkedWriteHandler$PendingWrite r1 = r13.currentWrite
            if (r1 != 0) goto L_0x0029
            java.util.Queue<io.netty.handler.stream.ChunkedWriteHandler$PendingWrite> r1 = r13.queue
            java.lang.Object r1 = r1.poll()
            io.netty.handler.stream.ChunkedWriteHandler$PendingWrite r1 = (p043io.netty.handler.stream.ChunkedWriteHandler.PendingWrite) r1
            r13.currentWrite = r1
        L_0x0029:
            io.netty.handler.stream.ChunkedWriteHandler$PendingWrite r3 = r13.currentWrite
            if (r3 != 0) goto L_0x002f
            goto L_0x00a6
        L_0x002f:
            java.lang.Object r2 = r3.msg
            boolean r1 = r2 instanceof p043io.netty.handler.stream.ChunkedInput
            r10 = 0
            if (r1 == 0) goto L_0x0090
            r4 = r2
            io.netty.handler.stream.ChunkedInput r4 = (p043io.netty.handler.stream.ChunkedInput) r4
            java.lang.Object r1 = r4.readChunk(r8)     // Catch:{ all -> 0x0080 }
            boolean r5 = r4.isEndOfInput()     // Catch:{ all -> 0x007e }
            if (r1 != 0) goto L_0x0046
            r11 = r5 ^ 1
            goto L_0x0047
        L_0x0046:
            r11 = 0
        L_0x0047:
            if (r11 == 0) goto L_0x004a
            goto L_0x00a6
        L_0x004a:
            if (r1 != 0) goto L_0x004e
            io.netty.buffer.ByteBuf r1 = p043io.netty.buffer.Unpooled.EMPTY_BUFFER
        L_0x004e:
            io.netty.channel.ChannelFuture r11 = r14.write(r1)
            if (r5 == 0) goto L_0x005f
            r13.currentWrite = r7
            io.netty.handler.stream.ChunkedWriteHandler$2 r0 = new io.netty.handler.stream.ChunkedWriteHandler$2
            r0.<init>(r3, r4)
            r11.addListener(r0)
            goto L_0x0079
        L_0x005f:
            boolean r0 = r6.isWritable()
            if (r0 == 0) goto L_0x006e
            io.netty.handler.stream.ChunkedWriteHandler$3 r0 = new io.netty.handler.stream.ChunkedWriteHandler$3
            r0.<init>(r2, r3, r4)
            r11.addListener(r0)
            goto L_0x0079
        L_0x006e:
            io.netty.handler.stream.ChunkedWriteHandler$4 r12 = new io.netty.handler.stream.ChunkedWriteHandler$4
            r0 = r12
            r1 = r13
            r5 = r6
            r0.<init>(r2, r3, r4, r5)
            r11.addListener(r12)
        L_0x0079:
            r14.flush()
            r0 = 0
            goto L_0x0098
        L_0x007e:
            r2 = move-exception
            goto L_0x0082
        L_0x0080:
            r2 = move-exception
            r1 = r7
        L_0x0082:
            r13.currentWrite = r7
            if (r1 == 0) goto L_0x0089
            p043io.netty.util.ReferenceCountUtil.release(r1)
        L_0x0089:
            r3.fail(r2)
            closeInput(r4)
            goto L_0x00a6
        L_0x0090:
            io.netty.channel.ChannelPromise r0 = r3.promise
            r14.write(r2, r0)
            r13.currentWrite = r7
            r0 = 1
        L_0x0098:
            boolean r1 = r6.isActive()
            if (r1 != 0) goto L_0x0015
            java.nio.channels.ClosedChannelException r1 = new java.nio.channels.ClosedChannelException
            r1.<init>()
            r13.discard(r1)
        L_0x00a6:
            if (r0 == 0) goto L_0x00ab
            r14.flush()
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.stream.ChunkedWriteHandler.doFlush(io.netty.channel.ChannelHandlerContext):void");
    }

    static void closeInput(ChunkedInput<?> chunkedInput) {
        try {
            chunkedInput.close();
        } catch (Throwable th) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close a chunked input.", th);
            }
        }
    }
}
