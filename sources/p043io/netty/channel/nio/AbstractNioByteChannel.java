package p043io.netty.channel.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.FileRegion;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.channel.socket.ChannelInputShutdownEvent;
import p043io.netty.channel.socket.ChannelInputShutdownReadComplete;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.nio.AbstractNioByteChannel */
public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private static final String EXPECTED_TYPES;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private Runnable flushTask;

    /* renamed from: io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe */
    protected class NioByteUnsafe extends AbstractNioUnsafe {
        protected NioByteUnsafe() {
            super();
        }

        private void closeOnRead(ChannelPipeline channelPipeline) {
            if (AbstractNioByteChannel.this.isInputShutdown0()) {
                channelPipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            } else if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                AbstractNioByteChannel.this.shutdownInput();
                channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            } else {
                close(voidPromise());
            }
        }

        private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, Handle handle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    AbstractNioByteChannel.this.readPending = false;
                    channelPipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            handle.readComplete();
            channelPipeline.fireChannelReadComplete();
            channelPipeline.fireExceptionCaught(th);
            if (z || (th instanceof IOException)) {
                closeOnRead(channelPipeline);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0034, code lost:
            if (r7.lastBytesRead() >= 0) goto L_0x0048;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x002d, code lost:
            r5.release();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void read() {
            /*
                r9 = this;
                io.netty.channel.nio.AbstractNioByteChannel r0 = p043io.netty.channel.nio.AbstractNioByteChannel.this
                io.netty.channel.ChannelConfig r0 = r0.config()
                io.netty.channel.nio.AbstractNioByteChannel r1 = p043io.netty.channel.nio.AbstractNioByteChannel.this
                io.netty.channel.ChannelPipeline r3 = r1.pipeline()
                io.netty.buffer.ByteBufAllocator r1 = r0.getAllocator()
                io.netty.channel.RecvByteBufAllocator$Handle r7 = r9.recvBufAllocHandle()
                r7.reset(r0)
            L_0x0017:
                r2 = 0
                r4 = 0
                io.netty.buffer.ByteBuf r5 = r7.allocate(r1)     // Catch:{ all -> 0x0069 }
                io.netty.channel.nio.AbstractNioByteChannel r6 = p043io.netty.channel.nio.AbstractNioByteChannel.this     // Catch:{ all -> 0x0064 }
                int r6 = r6.doReadBytes(r5)     // Catch:{ all -> 0x0064 }
                r7.lastBytesRead(r6)     // Catch:{ all -> 0x0064 }
                int r6 = r7.lastBytesRead()     // Catch:{ all -> 0x0064 }
                r8 = 1
                if (r6 > 0) goto L_0x0038
                r5.release()     // Catch:{ all -> 0x0064 }
                int r1 = r7.lastBytesRead()     // Catch:{ all -> 0x0069 }
                if (r1 >= 0) goto L_0x0048
                r2 = 1
                goto L_0x0048
            L_0x0038:
                r7.incMessagesRead(r8)     // Catch:{ all -> 0x0064 }
                io.netty.channel.nio.AbstractNioByteChannel r6 = p043io.netty.channel.nio.AbstractNioByteChannel.this     // Catch:{ all -> 0x0064 }
                r6.readPending = r2     // Catch:{ all -> 0x0064 }
                r3.fireChannelRead(r5)     // Catch:{ all -> 0x0064 }
                boolean r5 = r7.continueReading()     // Catch:{ all -> 0x0069 }
                if (r5 != 0) goto L_0x0017
            L_0x0048:
                r7.readComplete()     // Catch:{ all -> 0x0060 }
                r3.fireChannelReadComplete()     // Catch:{ all -> 0x0060 }
                if (r2 == 0) goto L_0x0053
                r9.closeOnRead(r3)     // Catch:{ all -> 0x0060 }
            L_0x0053:
                io.netty.channel.nio.AbstractNioByteChannel r1 = p043io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r1 = r1.readPending
                if (r1 != 0) goto L_0x007f
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x007f
                goto L_0x007c
            L_0x0060:
                r1 = move-exception
                r5 = r1
                r6 = r2
                goto L_0x006c
            L_0x0064:
                r1 = move-exception
                r4 = r5
                r6 = 0
                r5 = r1
                goto L_0x006c
            L_0x0069:
                r1 = move-exception
                r5 = r1
                r6 = 0
            L_0x006c:
                r2 = r9
                r2.handleReadException(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0080 }
                io.netty.channel.nio.AbstractNioByteChannel r1 = p043io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r1 = r1.readPending
                if (r1 != 0) goto L_0x007f
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x007f
            L_0x007c:
                r9.removeReadOp()
            L_0x007f:
                return
            L_0x0080:
                r1 = move-exception
                io.netty.channel.nio.AbstractNioByteChannel r2 = p043io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r2 = r2.readPending
                if (r2 != 0) goto L_0x0090
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x0090
                r9.removeReadOp()
            L_0x0090:
                goto L_0x0092
            L_0x0091:
                throw r1
            L_0x0092:
                goto L_0x0091
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.nio.AbstractNioByteChannel.NioByteUnsafe.read():void");
        }
    }

    /* access modifiers changed from: protected */
    public abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    /* access modifiers changed from: protected */
    public abstract int doWriteBytes(ByteBuf byteBuf) throws Exception;

    /* access modifiers changed from: protected */
    public abstract long doWriteFileRegion(FileRegion fileRegion) throws Exception;

    /* access modifiers changed from: protected */
    public boolean isInputShutdown0() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract ChannelFuture shutdownInput();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(" (expected: ");
        sb.append(StringUtil.simpleClassName(ByteBuf.class));
        sb.append(", ");
        sb.append(StringUtil.simpleClassName(FileRegion.class));
        sb.append(')');
        EXPECTED_TYPES = sb.toString();
    }

    protected AbstractNioByteChannel(Channel channel, SelectableChannel selectableChannel) {
        super(channel, selectableChannel, 1);
    }

    /* access modifiers changed from: protected */
    public AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        ChannelOutboundBuffer channelOutboundBuffer2 = channelOutboundBuffer;
        int i = -1;
        boolean z = false;
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current == null) {
                clearOpWrite();
                return;
            }
            long j = 0;
            boolean z2 = true;
            if (current instanceof ByteBuf) {
                ByteBuf byteBuf = (ByteBuf) current;
                if (byteBuf.readableBytes() != 0) {
                    if (i == -1) {
                        i = config().getWriteSpinCount();
                    }
                    int i2 = i - 1;
                    while (true) {
                        if (i2 < 0) {
                            break;
                        }
                        int doWriteBytes = doWriteBytes(byteBuf);
                        if (doWriteBytes == 0) {
                            z = true;
                            break;
                        }
                        j += (long) doWriteBytes;
                        if (!byteBuf.isReadable()) {
                            break;
                        }
                        i2--;
                    }
                    z2 = false;
                    channelOutboundBuffer2.progress(j);
                    if (!z2) {
                        break;
                    }
                    channelOutboundBuffer.remove();
                } else {
                    channelOutboundBuffer.remove();
                }
            } else if (current instanceof FileRegion) {
                FileRegion fileRegion = (FileRegion) current;
                boolean z3 = fileRegion.transferred() >= fileRegion.count();
                if (!z3) {
                    if (i == -1) {
                        i = config().getWriteSpinCount();
                    }
                    int i3 = i - 1;
                    long j2 = 0;
                    while (true) {
                        if (i3 < 0) {
                            break;
                        }
                        long doWriteFileRegion = doWriteFileRegion(fileRegion);
                        if (doWriteFileRegion == 0) {
                            z = true;
                            break;
                        }
                        j2 += doWriteFileRegion;
                        if (fileRegion.transferred() >= fileRegion.count()) {
                            z3 = true;
                            break;
                        }
                        i3--;
                    }
                    channelOutboundBuffer2.progress(j2);
                }
                if (!z3) {
                    break;
                }
                channelOutboundBuffer.remove();
            } else {
                throw new Error();
            }
        }
        incompleteWrite(z);
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object obj) {
        if (obj instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) obj;
            if (byteBuf.isDirect()) {
                return obj;
            }
            return newDirectBuffer(byteBuf);
        } else if (obj instanceof FileRegion) {
            return obj;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("unsupported message type: ");
            sb.append(StringUtil.simpleClassName(obj));
            sb.append(EXPECTED_TYPES);
            throw new UnsupportedOperationException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public final void incompleteWrite(boolean z) {
        if (z) {
            setOpWrite();
            return;
        }
        Runnable runnable = this.flushTask;
        if (runnable == null) {
            runnable = new Runnable() {
                public void run() {
                    AbstractNioByteChannel.this.flush();
                }
            };
            this.flushTask = runnable;
        }
        eventLoop().execute(runnable);
    }

    /* access modifiers changed from: protected */
    public final void setOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int interestOps = selectionKey.interestOps();
            if ((interestOps & 4) == 0) {
                selectionKey.interestOps(interestOps | 4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void clearOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int interestOps = selectionKey.interestOps();
            if ((interestOps & 4) != 0) {
                selectionKey.interestOps(interestOps & -5);
            }
        }
    }
}
