package p043io.netty.channel.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.AbstractChannel;
import p043io.netty.channel.Channel;
import p043io.netty.channel.Channel.Unsafe;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ConnectTimeoutException;
import p043io.netty.channel.EventLoop;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.nio.AbstractNioChannel */
public abstract class AbstractNioChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION;
    private static final InternalLogger logger;

    /* renamed from: ch */
    private final SelectableChannel f3704ch;
    private final Runnable clearReadPendingRunnable = new Runnable() {
        public void run() {
            AbstractNioChannel.this.clearReadPending0();
        }
    };
    /* access modifiers changed from: private */
    public ChannelPromise connectPromise;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> connectTimeoutFuture;
    protected final int readInterestOp;
    boolean readPending;
    /* access modifiers changed from: private */
    public SocketAddress requestedRemoteAddress;
    volatile SelectionKey selectionKey;

    /* renamed from: io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe */
    protected abstract class AbstractNioUnsafe extends AbstractUnsafe implements NioUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<AbstractNioChannel> cls = AbstractNioChannel.class;
        }

        protected AbstractNioUnsafe() {
            super();
        }

        /* access modifiers changed from: protected */
        public final void removeReadOp() {
            SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
            if (selectionKey.isValid()) {
                int interestOps = selectionKey.interestOps();
                if ((AbstractNioChannel.this.readInterestOp & interestOps) != 0) {
                    selectionKey.interestOps(interestOps & (AbstractNioChannel.this.readInterestOp ^ -1));
                }
            }
        }

        /* renamed from: ch */
        public final SelectableChannel mo66718ch() {
            return AbstractNioChannel.this.javaChannel();
        }

        public final void connect(final SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    if (AbstractNioChannel.this.connectPromise == null) {
                        boolean isActive = AbstractNioChannel.this.isActive();
                        if (AbstractNioChannel.this.doConnect(socketAddress, socketAddress2)) {
                            fulfillConnectPromise(channelPromise, isActive);
                            return;
                        }
                        AbstractNioChannel.this.connectPromise = channelPromise;
                        AbstractNioChannel.this.requestedRemoteAddress = socketAddress;
                        int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
                        if (connectTimeoutMillis > 0) {
                            AbstractNioChannel.this.connectTimeoutFuture = AbstractNioChannel.this.eventLoop().schedule((Runnable) new Runnable() {
                                public void run() {
                                    ChannelPromise access$200 = AbstractNioChannel.this.connectPromise;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("connection timed out: ");
                                    sb.append(socketAddress);
                                    ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException(sb.toString());
                                    if (access$200 != null && access$200.tryFailure(connectTimeoutException)) {
                                        AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                        abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                    }
                                }
                            }, (long) connectTimeoutMillis, TimeUnit.MILLISECONDS);
                        }
                        channelPromise.addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (channelFuture.isCancelled()) {
                                    if (AbstractNioChannel.this.connectTimeoutFuture != null) {
                                        AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
                                    }
                                    AbstractNioChannel.this.connectPromise = null;
                                    AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                    abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                }
                            }
                        });
                        return;
                    }
                    throw new ConnectionPendingException();
                } catch (Throwable th) {
                    channelPromise.tryFailure(annotateConnectException(th, socketAddress));
                    closeIfClosed();
                }
            }
        }

        private void fulfillConnectPromise(ChannelPromise channelPromise, boolean z) {
            if (channelPromise != null) {
                boolean isActive = AbstractNioChannel.this.isActive();
                boolean trySuccess = channelPromise.trySuccess();
                if (!z && isActive) {
                    AbstractNioChannel.this.pipeline().fireChannelActive();
                }
                if (!trySuccess) {
                    close(voidPromise());
                }
            }
        }

        private void fulfillConnectPromise(ChannelPromise channelPromise, Throwable th) {
            if (channelPromise != null) {
                channelPromise.tryFailure(th);
                closeIfClosed();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
            if (p043io.netty.channel.nio.AbstractNioChannel.access$400(r5.this$0) == null) goto L_0x0027;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x001c, code lost:
            if (p043io.netty.channel.nio.AbstractNioChannel.access$400(r5.this$0) != null) goto L_0x001e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x001e, code lost:
            p043io.netty.channel.nio.AbstractNioChannel.access$400(r5.this$0).cancel(false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0027, code lost:
            p043io.netty.channel.nio.AbstractNioChannel.access$202(r5.this$0, null);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void finishConnect() {
            /*
                r5 = this;
                r0 = 0
                r1 = 0
                io.netty.channel.nio.AbstractNioChannel r2 = p043io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x002d }
                boolean r2 = r2.isActive()     // Catch:{ all -> 0x002d }
                io.netty.channel.nio.AbstractNioChannel r3 = p043io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x002d }
                r3.doFinishConnect()     // Catch:{ all -> 0x002d }
                io.netty.channel.nio.AbstractNioChannel r3 = p043io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x002d }
                io.netty.channel.ChannelPromise r3 = r3.connectPromise     // Catch:{ all -> 0x002d }
                r5.fulfillConnectPromise(r3, r2)     // Catch:{ all -> 0x002d }
                io.netty.channel.nio.AbstractNioChannel r2 = p043io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                if (r2 == 0) goto L_0x0027
            L_0x001e:
                io.netty.channel.nio.AbstractNioChannel r2 = p043io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                r2.cancel(r0)
            L_0x0027:
                io.netty.channel.nio.AbstractNioChannel r0 = p043io.netty.channel.nio.AbstractNioChannel.this
                r0.connectPromise = r1
                goto L_0x004a
            L_0x002d:
                r2 = move-exception
                io.netty.channel.nio.AbstractNioChannel r3 = p043io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x004b }
                io.netty.channel.ChannelPromise r3 = r3.connectPromise     // Catch:{ all -> 0x004b }
                io.netty.channel.nio.AbstractNioChannel r4 = p043io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x004b }
                java.net.SocketAddress r4 = r4.requestedRemoteAddress     // Catch:{ all -> 0x004b }
                java.lang.Throwable r2 = r5.annotateConnectException(r2, r4)     // Catch:{ all -> 0x004b }
                r5.fulfillConnectPromise(r3, r2)     // Catch:{ all -> 0x004b }
                io.netty.channel.nio.AbstractNioChannel r2 = p043io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                if (r2 == 0) goto L_0x0027
                goto L_0x001e
            L_0x004a:
                return
            L_0x004b:
                r2 = move-exception
                io.netty.channel.nio.AbstractNioChannel r3 = p043io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r3 = r3.connectTimeoutFuture
                if (r3 == 0) goto L_0x005d
                io.netty.channel.nio.AbstractNioChannel r3 = p043io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r3 = r3.connectTimeoutFuture
                r3.cancel(r0)
            L_0x005d:
                io.netty.channel.nio.AbstractNioChannel r0 = p043io.netty.channel.nio.AbstractNioChannel.this
                r0.connectPromise = r1
                goto L_0x0064
            L_0x0063:
                throw r2
            L_0x0064:
                goto L_0x0063
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe.finishConnect():void");
        }

        /* access modifiers changed from: protected */
        public final void flush0() {
            if (!isFlushPending()) {
                super.flush0();
            }
        }

        public final void forceFlush() {
            super.flush0();
        }

        private boolean isFlushPending() {
            SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
            return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
        }
    }

    /* renamed from: io.netty.channel.nio.AbstractNioChannel$NioUnsafe */
    public interface NioUnsafe extends Unsafe {
        /* renamed from: ch */
        SelectableChannel mo66718ch();

        void finishConnect();

        void forceFlush();

        void read();
    }

    /* access modifiers changed from: protected */
    public abstract boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doFinishConnect() throws Exception;

    static {
        Class<AbstractNioChannel> cls = AbstractNioChannel.class;
        logger = InternalLoggerFactory.getInstance(cls);
        DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "doClose()");
    }

    protected AbstractNioChannel(Channel channel, SelectableChannel selectableChannel, int i) {
        super(channel);
        this.f3704ch = selectableChannel;
        this.readInterestOp = i;
        try {
            selectableChannel.configureBlocking(false);
        } catch (IOException e) {
            try {
                selectableChannel.close();
            } catch (IOException e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a partially initialized socket.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e);
        }
    }

    public boolean isOpen() {
        return this.f3704ch.isOpen();
    }

    public NioUnsafe unsafe() {
        return (NioUnsafe) super.unsafe();
    }

    /* access modifiers changed from: protected */
    public SelectableChannel javaChannel() {
        return this.f3704ch;
    }

    public NioEventLoop eventLoop() {
        return (NioEventLoop) super.eventLoop();
    }

    /* access modifiers changed from: protected */
    public SelectionKey selectionKey() {
        return this.selectionKey;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean isReadPending() {
        return this.readPending;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setReadPending(final boolean z) {
        if (isRegistered()) {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                setReadPending0(z);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        AbstractNioChannel.this.setReadPending0(z);
                    }
                });
            }
        } else {
            this.readPending = z;
        }
    }

    /* access modifiers changed from: protected */
    public final void clearReadPending() {
        if (isRegistered()) {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                clearReadPending0();
            } else {
                eventLoop.execute(this.clearReadPendingRunnable);
            }
        } else {
            this.readPending = false;
        }
    }

    /* access modifiers changed from: private */
    public void setReadPending0(boolean z) {
        this.readPending = z;
        if (!z) {
            ((AbstractNioUnsafe) unsafe()).removeReadOp();
        }
    }

    /* access modifiers changed from: private */
    public void clearReadPending0() {
        this.readPending = false;
        ((AbstractNioUnsafe) unsafe()).removeReadOp();
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof NioEventLoop;
    }

    /* access modifiers changed from: protected */
    public void doRegister() throws Exception {
        boolean z = false;
        while (true) {
            try {
                this.selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
                return;
            } catch (CancelledKeyException e) {
                if (!z) {
                    eventLoop().selectNow();
                    z = true;
                } else {
                    throw e;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() throws Exception {
        eventLoop().cancel(selectionKey());
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
        SelectionKey selectionKey2 = this.selectionKey;
        if (selectionKey2.isValid()) {
            this.readPending = true;
            int interestOps = selectionKey2.interestOps();
            int i = this.readInterestOp;
            if ((interestOps & i) == 0) {
                selectionKey2.interestOps(interestOps | i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final ByteBuf newDirectBuffer(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(byteBuf);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(byteBuf);
            return directBuffer;
        }
        ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer == null) {
            return byteBuf;
        }
        threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(byteBuf);
        return threadLocalDirectBuffer;
    }

    /* access modifiers changed from: protected */
    public final ByteBuf newDirectBuffer(ReferenceCounted referenceCounted, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return directBuffer;
        }
        ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer != null) {
            threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return threadLocalDirectBuffer;
        }
        if (referenceCounted != byteBuf) {
            byteBuf.retain();
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
        return byteBuf;
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        ChannelPromise channelPromise = this.connectPromise;
        if (channelPromise != null) {
            channelPromise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
            this.connectPromise = null;
        }
        ScheduledFuture<?> scheduledFuture = this.connectTimeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.connectTimeoutFuture = null;
        }
    }
}
