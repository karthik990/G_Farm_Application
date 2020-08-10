package p043io.netty.channel.local;

import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import p043io.netty.channel.AbstractChannel;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.SingleThreadEventLoop;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.SingleThreadEventExecutor;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.local.LocalChannel */
public class LocalChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
    private static final AtomicReferenceFieldUpdater<LocalChannel, Future> FINISH_READ_FUTURE_UPDATER;
    private static final int MAX_READER_STACK_DEPTH = 8;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    /* access modifiers changed from: private */
    public volatile ChannelPromise connectPromise;
    private volatile Future<?> finishReadFuture;
    final Queue<Object> inboundBuffer = PlatformDependent.newSpscQueue();
    private volatile LocalAddress localAddress;
    /* access modifiers changed from: private */
    public volatile LocalChannel peer;
    private volatile boolean readInProgress;
    private final Runnable readTask = new Runnable() {
        public void run() {
            ChannelPipeline pipeline = LocalChannel.this.pipeline();
            while (true) {
                Object poll = LocalChannel.this.inboundBuffer.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile boolean registerInProgress;
    private volatile LocalAddress remoteAddress;
    private final Runnable shutdownHook = new Runnable() {
        public void run() {
            LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
        }
    };
    /* access modifiers changed from: private */
    public volatile State state;
    private volatile boolean writeInProgress;

    /* renamed from: io.netty.channel.local.LocalChannel$6 */
    static /* synthetic */ class C56196 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$channel$local$LocalChannel$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.channel.local.LocalChannel$State[] r0 = p043io.netty.channel.local.LocalChannel.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$channel$local$LocalChannel$State = r0
                int[] r0 = $SwitchMap$io$netty$channel$local$LocalChannel$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.channel.local.LocalChannel$State r1 = p043io.netty.channel.local.LocalChannel.State.OPEN     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$channel$local$LocalChannel$State     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.channel.local.LocalChannel$State r1 = p043io.netty.channel.local.LocalChannel.State.BOUND     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$channel$local$LocalChannel$State     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.channel.local.LocalChannel$State r1 = p043io.netty.channel.local.LocalChannel.State.CLOSED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$channel$local$LocalChannel$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.channel.local.LocalChannel$State r1 = p043io.netty.channel.local.LocalChannel.State.CONNECTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.local.LocalChannel.C56196.<clinit>():void");
        }
    }

    /* renamed from: io.netty.channel.local.LocalChannel$LocalUnsafe */
    private class LocalUnsafe extends AbstractUnsafe {
        private LocalUnsafe() {
            super();
        }

        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                if (LocalChannel.this.state == State.CONNECTED) {
                    AlreadyConnectedException alreadyConnectedException = new AlreadyConnectedException();
                    safeSetFailure(channelPromise, alreadyConnectedException);
                    LocalChannel.this.pipeline().fireExceptionCaught(alreadyConnectedException);
                } else if (LocalChannel.this.connectPromise == null) {
                    LocalChannel.this.connectPromise = channelPromise;
                    if (LocalChannel.this.state != State.BOUND && socketAddress2 == null) {
                        socketAddress2 = new LocalAddress((Channel) LocalChannel.this);
                    }
                    if (socketAddress2 != null) {
                        try {
                            LocalChannel.this.doBind(socketAddress2);
                        } catch (Throwable th) {
                            safeSetFailure(channelPromise, th);
                            close(voidPromise());
                            return;
                        }
                    }
                    Channel channel = LocalChannelRegistry.get(socketAddress);
                    if (!(channel instanceof LocalServerChannel)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("connection refused: ");
                        sb.append(socketAddress);
                        safeSetFailure(channelPromise, new ConnectException(sb.toString()));
                        close(voidPromise());
                        return;
                    }
                    LocalServerChannel localServerChannel = (LocalServerChannel) channel;
                    LocalChannel localChannel = LocalChannel.this;
                    localChannel.peer = localServerChannel.serve(localChannel);
                } else {
                    throw new ConnectionPendingException();
                }
            }
        }
    }

    /* renamed from: io.netty.channel.local.LocalChannel$State */
    private enum State {
        OPEN,
        BOUND,
        CONNECTED,
        CLOSED
    }

    static {
        Class<LocalChannel> cls = LocalChannel.class;
        logger = InternalLoggerFactory.getInstance(cls);
        FINISH_READ_FUTURE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(cls, Future.class, "finishReadFuture");
        DO_WRITE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "doWrite(...)");
        DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "doClose()");
    }

    public LocalChannel() {
        super(null);
        config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
    }

    protected LocalChannel(LocalServerChannel localServerChannel, LocalChannel localChannel) {
        super(localServerChannel);
        config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
        this.peer = localChannel;
        this.localAddress = localServerChannel.localAddress();
        this.remoteAddress = localChannel.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public LocalServerChannel parent() {
        return (LocalServerChannel) super.parent();
    }

    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    public boolean isActive() {
        return this.state == State.CONNECTED;
    }

    /* access modifiers changed from: protected */
    public AbstractUnsafe newUnsafe() {
        return new LocalUnsafe();
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.localAddress;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }

    /* access modifiers changed from: protected */
    public void doRegister() throws Exception {
        if (!(this.peer == null || parent() == null)) {
            final LocalChannel localChannel = this.peer;
            this.registerInProgress = true;
            this.state = State.CONNECTED;
            localChannel.remoteAddress = parent() == null ? null : parent().localAddress();
            localChannel.state = State.CONNECTED;
            localChannel.eventLoop().execute(new Runnable() {
                public void run() {
                    LocalChannel.this.registerInProgress = false;
                    ChannelPromise access$200 = localChannel.connectPromise;
                    if (access$200 != null && access$200.trySuccess()) {
                        localChannel.pipeline().fireChannelActive();
                    }
                }
            });
        }
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = State.BOUND;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        EventLoop eventLoop;
        final LocalChannel localChannel = this.peer;
        State state2 = this.state;
        try {
            if (state2 != State.CLOSED) {
                if (this.localAddress != null) {
                    if (parent() == null) {
                        LocalChannelRegistry.unregister(this.localAddress);
                    }
                    this.localAddress = null;
                }
                this.state = State.CLOSED;
                finishPeerRead(this);
                ChannelPromise channelPromise = this.connectPromise;
                if (channelPromise != null) {
                    channelPromise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                    this.connectPromise = null;
                }
            }
            if (localChannel != null) {
                this.peer = null;
                eventLoop = localChannel.eventLoop();
                final boolean isActive = localChannel.isActive();
                if (!eventLoop.inEventLoop() || this.registerInProgress) {
                    eventLoop.execute(new Runnable() {
                        public void run() {
                            localChannel.tryClose(isActive);
                        }
                    });
                } else {
                    localChannel.tryClose(isActive);
                }
            }
        } catch (Throwable th) {
            if (!(state2 == null || state2 == State.CLOSED)) {
                releaseInboundBuffers();
            }
            throw th;
        }
        if (state2 != null && state2 != State.CLOSED) {
            releaseInboundBuffers();
        }
    }

    /* access modifiers changed from: private */
    public void tryClose(boolean z) {
        if (z) {
            unsafe().close(unsafe().voidPromise());
        } else {
            releaseInboundBuffers();
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
        if (!this.readInProgress) {
            ChannelPipeline pipeline = pipeline();
            Queue<Object> queue = this.inboundBuffer;
            if (queue.isEmpty()) {
                this.readInProgress = true;
                return;
            }
            InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
            Integer valueOf = Integer.valueOf(internalThreadLocalMap.localChannelReaderStackDepth());
            if (valueOf.intValue() < 8) {
                internalThreadLocalMap.setLocalChannelReaderStackDepth(valueOf.intValue() + 1);
                while (true) {
                    try {
                        Object poll = queue.poll();
                        if (poll == null) {
                            break;
                        }
                        pipeline.fireChannelRead(poll);
                    } finally {
                        internalThreadLocalMap.setLocalChannelReaderStackDepth(valueOf.intValue());
                    }
                }
                pipeline.fireChannelReadComplete();
            } else {
                try {
                    eventLoop().execute(this.readTask);
                } catch (Throwable th) {
                    logger.warn("Closing Local channels {}-{} because exception occurred!", this, this.peer, th);
                    close();
                    this.peer.close();
                    PlatformDependent.throwException(th);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        int i = C56196.$SwitchMap$io$netty$channel$local$LocalChannel$State[this.state.ordinal()];
        if (i == 1 || i == 2) {
            throw new NotYetConnectedException();
        } else if (i != 3) {
            LocalChannel localChannel = this.peer;
            this.writeInProgress = true;
            while (true) {
                try {
                    Object current = channelOutboundBuffer.current();
                    if (current == null) {
                        this.writeInProgress = false;
                        finishPeerRead(localChannel);
                        return;
                    } else if (localChannel.state == State.CONNECTED) {
                        localChannel.inboundBuffer.add(ReferenceCountUtil.retain(current));
                        channelOutboundBuffer.remove();
                    } else {
                        channelOutboundBuffer.remove(DO_WRITE_CLOSED_CHANNEL_EXCEPTION);
                    }
                } catch (Throwable th) {
                    this.writeInProgress = false;
                    throw th;
                }
            }
        } else {
            throw DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
        }
    }

    private void finishPeerRead(LocalChannel localChannel) {
        if (localChannel.eventLoop() != eventLoop() || localChannel.writeInProgress) {
            runFinishPeerReadTask(localChannel);
        } else {
            finishPeerRead0(localChannel);
        }
    }

    private void runFinishPeerReadTask(final LocalChannel localChannel) {
        C56185 r0 = new Runnable() {
            public void run() {
                LocalChannel.this.finishPeerRead0(localChannel);
            }
        };
        try {
            if (localChannel.writeInProgress) {
                localChannel.finishReadFuture = localChannel.eventLoop().submit((Runnable) r0);
            } else {
                localChannel.eventLoop().execute(r0);
            }
        } catch (Throwable th) {
            logger.warn("Closing Local channels {}-{} because exception occurred!", this, localChannel, th);
            close();
            localChannel.close();
            PlatformDependent.throwException(th);
        }
    }

    private void releaseInboundBuffers() {
        this.readInProgress = false;
        Queue<Object> queue = this.inboundBuffer;
        while (true) {
            Object poll = queue.poll();
            if (poll != null) {
                ReferenceCountUtil.release(poll);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void finishPeerRead0(LocalChannel localChannel) {
        Future<?> future = localChannel.finishReadFuture;
        if (future != null) {
            if (!future.isDone()) {
                runFinishPeerReadTask(localChannel);
                return;
            }
            FINISH_READ_FUTURE_UPDATER.compareAndSet(localChannel, future, null);
        }
        ChannelPipeline pipeline = localChannel.pipeline();
        if (localChannel.readInProgress) {
            localChannel.readInProgress = false;
            while (true) {
                Object poll = localChannel.inboundBuffer.poll();
                if (poll == null) {
                    break;
                }
                pipeline.fireChannelRead(poll);
            }
            pipeline.fireChannelReadComplete();
        }
    }
}
