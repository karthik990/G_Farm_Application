package p043io.netty.channel.nio;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ServerChannel;

/* renamed from: io.netty.channel.nio.AbstractNioMessageChannel */
public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
    boolean inputShutdown;

    /* renamed from: io.netty.channel.nio.AbstractNioMessageChannel$NioMessageUnsafe */
    private final class NioMessageUnsafe extends AbstractNioUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<Object> readBuf;

        static {
            Class<AbstractNioMessageChannel> cls = AbstractNioMessageChannel.class;
        }

        private NioMessageUnsafe() {
            super();
            this.readBuf = new ArrayList();
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0040 A[Catch:{ all -> 0x008b }, LOOP:1: B:14:0x003e->B:15:0x0040, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x005d A[Catch:{ all -> 0x008b }] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0068 A[Catch:{ all -> 0x008b }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void read() {
            /*
                r10 = this;
                io.netty.channel.nio.AbstractNioMessageChannel r0 = p043io.netty.channel.nio.AbstractNioMessageChannel.this
                io.netty.channel.ChannelConfig r0 = r0.config()
                io.netty.channel.nio.AbstractNioMessageChannel r1 = p043io.netty.channel.nio.AbstractNioMessageChannel.this
                io.netty.channel.ChannelPipeline r1 = r1.pipeline()
                io.netty.channel.nio.AbstractNioMessageChannel r2 = p043io.netty.channel.nio.AbstractNioMessageChannel.this
                io.netty.channel.nio.AbstractNioChannel$NioUnsafe r2 = r2.unsafe()
                io.netty.channel.RecvByteBufAllocator$Handle r2 = r2.recvBufAllocHandle()
                r2.reset(r0)
                r3 = 0
            L_0x001a:
                r4 = 1
                r5 = 0
                io.netty.channel.nio.AbstractNioMessageChannel r6 = p043io.netty.channel.nio.AbstractNioMessageChannel.this     // Catch:{ all -> 0x0035 }
                java.util.List<java.lang.Object> r7 = r10.readBuf     // Catch:{ all -> 0x0035 }
                int r6 = r6.doReadMessages(r7)     // Catch:{ all -> 0x0035 }
                if (r6 != 0) goto L_0x0027
                goto L_0x0034
            L_0x0027:
                if (r6 >= 0) goto L_0x002b
                r6 = 1
                goto L_0x0037
            L_0x002b:
                r2.incMessagesRead(r6)     // Catch:{ all -> 0x0035 }
                boolean r6 = r2.continueReading()     // Catch:{ all -> 0x0035 }
                if (r6 != 0) goto L_0x001a
            L_0x0034:
                goto L_0x0036
            L_0x0035:
                r3 = move-exception
            L_0x0036:
                r6 = 0
            L_0x0037:
                java.util.List<java.lang.Object> r7 = r10.readBuf     // Catch:{ all -> 0x008b }
                int r7 = r7.size()     // Catch:{ all -> 0x008b }
                r8 = 0
            L_0x003e:
                if (r8 >= r7) goto L_0x0050
                io.netty.channel.nio.AbstractNioMessageChannel r9 = p043io.netty.channel.nio.AbstractNioMessageChannel.this     // Catch:{ all -> 0x008b }
                r9.readPending = r5     // Catch:{ all -> 0x008b }
                java.util.List<java.lang.Object> r9 = r10.readBuf     // Catch:{ all -> 0x008b }
                java.lang.Object r9 = r9.get(r8)     // Catch:{ all -> 0x008b }
                r1.fireChannelRead(r9)     // Catch:{ all -> 0x008b }
                int r8 = r8 + 1
                goto L_0x003e
            L_0x0050:
                java.util.List<java.lang.Object> r5 = r10.readBuf     // Catch:{ all -> 0x008b }
                r5.clear()     // Catch:{ all -> 0x008b }
                r2.readComplete()     // Catch:{ all -> 0x008b }
                r1.fireChannelReadComplete()     // Catch:{ all -> 0x008b }
                if (r3 == 0) goto L_0x0066
                io.netty.channel.nio.AbstractNioMessageChannel r2 = p043io.netty.channel.nio.AbstractNioMessageChannel.this     // Catch:{ all -> 0x008b }
                boolean r6 = r2.closeOnReadError(r3)     // Catch:{ all -> 0x008b }
                r1.fireExceptionCaught(r3)     // Catch:{ all -> 0x008b }
            L_0x0066:
                if (r6 == 0) goto L_0x007b
                io.netty.channel.nio.AbstractNioMessageChannel r1 = p043io.netty.channel.nio.AbstractNioMessageChannel.this     // Catch:{ all -> 0x008b }
                r1.inputShutdown = r4     // Catch:{ all -> 0x008b }
                io.netty.channel.nio.AbstractNioMessageChannel r1 = p043io.netty.channel.nio.AbstractNioMessageChannel.this     // Catch:{ all -> 0x008b }
                boolean r1 = r1.isOpen()     // Catch:{ all -> 0x008b }
                if (r1 == 0) goto L_0x007b
                io.netty.channel.ChannelPromise r1 = r10.voidPromise()     // Catch:{ all -> 0x008b }
                r10.close(r1)     // Catch:{ all -> 0x008b }
            L_0x007b:
                io.netty.channel.nio.AbstractNioMessageChannel r1 = p043io.netty.channel.nio.AbstractNioMessageChannel.this
                boolean r1 = r1.readPending
                if (r1 != 0) goto L_0x008a
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x008a
                r10.removeReadOp()
            L_0x008a:
                return
            L_0x008b:
                r1 = move-exception
                io.netty.channel.nio.AbstractNioMessageChannel r2 = p043io.netty.channel.nio.AbstractNioMessageChannel.this
                boolean r2 = r2.readPending
                if (r2 != 0) goto L_0x009b
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x009b
                r10.removeReadOp()
            L_0x009b:
                goto L_0x009d
            L_0x009c:
                throw r1
            L_0x009d:
                goto L_0x009c
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.nio.AbstractNioMessageChannel.NioMessageUnsafe.read():void");
        }
    }

    /* access modifiers changed from: protected */
    public boolean continueOnWriteError() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract int doReadMessages(List<Object> list) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    protected AbstractNioMessageChannel(Channel channel, SelectableChannel selectableChannel, int i) {
        super(channel, selectableChannel, i);
    }

    /* access modifiers changed from: protected */
    public AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe();
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
        if (!this.inputShutdown) {
            super.doBeginRead();
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SelectionKey selectionKey = selectionKey();
        int interestOps = selectionKey.interestOps();
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                boolean z = false;
                try {
                    int writeSpinCount = config().getWriteSpinCount() - 1;
                    while (true) {
                        if (writeSpinCount < 0) {
                            break;
                        } else if (doWriteMessage(current, channelOutboundBuffer)) {
                            z = true;
                            break;
                        } else {
                            writeSpinCount--;
                        }
                    }
                    if (z) {
                        channelOutboundBuffer.remove();
                    } else if ((interestOps & 4) == 0) {
                        selectionKey.interestOps(interestOps | 4);
                        return;
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    if (continueOnWriteError()) {
                        channelOutboundBuffer.remove(e);
                    } else {
                        throw e;
                    }
                }
            } else if ((interestOps & 4) != 0) {
                selectionKey.interestOps(interestOps & -5);
                return;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean closeOnReadError(Throwable th) {
        return (th instanceof IOException) && !(th instanceof PortUnreachableException) && !(this instanceof ServerChannel);
    }
}
