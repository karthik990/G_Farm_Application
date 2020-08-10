package p043io.netty.channel;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.Channel.Unsafe;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.util.DefaultAttributeMap;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.GenericFutureListener;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.AbstractChannel */
public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {
    /* access modifiers changed from: private */
    public static final ClosedChannelException CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "close(...)"));
    /* access modifiers changed from: private */
    public static final ClosedChannelException ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "ensureOpen(...)"));
    /* access modifiers changed from: private */
    public static final ClosedChannelException FLUSH0_CLOSED_CHANNEL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final NotYetConnectedException FLUSH0_NOT_YET_CONNECTED_EXCEPTION;
    /* access modifiers changed from: private */
    public static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "write(...)"));
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
    /* access modifiers changed from: private */
    public final CloseFuture closeFuture = new CloseFuture(this);
    /* access modifiers changed from: private */
    public volatile EventLoop eventLoop;

    /* renamed from: id */
    private final ChannelId f3698id;
    private volatile SocketAddress localAddress;
    private final Channel parent;
    /* access modifiers changed from: private */
    public final DefaultChannelPipeline pipeline;
    /* access modifiers changed from: private */
    public volatile boolean registered;
    private volatile SocketAddress remoteAddress;
    private String strVal;
    private boolean strValActive;
    private final Unsafe unsafe;
    /* access modifiers changed from: private */
    public final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);

    /* renamed from: io.netty.channel.AbstractChannel$AbstractUnsafe */
    protected abstract class AbstractUnsafe implements Unsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean inFlush0;
        private boolean neverRegistered = true;
        private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
        private Handle recvHandle;

        private void assertEventLoop() {
        }

        /* access modifiers changed from: protected */
        public Executor prepareToClose() {
            return null;
        }

        static {
            Class<AbstractChannel> cls = AbstractChannel.class;
        }

        protected AbstractUnsafe() {
        }

        public Handle recvBufAllocHandle() {
            if (this.recvHandle == null) {
                this.recvHandle = AbstractChannel.this.config().getRecvByteBufAllocator().newHandle();
            }
            return this.recvHandle;
        }

        public final ChannelOutboundBuffer outboundBuffer() {
            return this.outboundBuffer;
        }

        public final SocketAddress localAddress() {
            return AbstractChannel.this.localAddress0();
        }

        public final SocketAddress remoteAddress() {
            return AbstractChannel.this.remoteAddress0();
        }

        public final void register(EventLoop eventLoop, final ChannelPromise channelPromise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            } else if (AbstractChannel.this.isRegistered()) {
                channelPromise.setFailure(new IllegalStateException("registered to an event loop already"));
            } else if (!AbstractChannel.this.isCompatible(eventLoop)) {
                StringBuilder sb = new StringBuilder();
                sb.append("incompatible event loop type: ");
                sb.append(eventLoop.getClass().getName());
                channelPromise.setFailure(new IllegalStateException(sb.toString()));
            } else {
                AbstractChannel.this.eventLoop = eventLoop;
                if (eventLoop.inEventLoop()) {
                    register0(channelPromise);
                } else {
                    try {
                        eventLoop.execute(new Runnable() {
                            public void run() {
                                AbstractUnsafe.this.register0(channelPromise);
                            }
                        });
                    } catch (Throwable th) {
                        AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, th);
                        closeForcibly();
                        AbstractChannel.this.closeFuture.setClosed();
                        safeSetFailure(channelPromise, th);
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void register0(ChannelPromise channelPromise) {
            try {
                if (channelPromise.setUncancellable()) {
                    if (ensureOpen(channelPromise)) {
                        boolean z = this.neverRegistered;
                        AbstractChannel.this.doRegister();
                        this.neverRegistered = false;
                        AbstractChannel.this.registered = true;
                        AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
                        safeSetSuccess(channelPromise);
                        AbstractChannel.this.pipeline.fireChannelRegistered();
                        if (AbstractChannel.this.isActive()) {
                            if (z) {
                                AbstractChannel.this.pipeline.fireChannelActive();
                            } else if (AbstractChannel.this.config().isAutoRead()) {
                                beginRead();
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                closeForcibly();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(channelPromise, th);
            }
        }

        public final void bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            assertEventLoop();
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                if (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && (socketAddress instanceof InetSocketAddress) && !((InetSocketAddress) socketAddress).getAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
                    InternalLogger access$300 = AbstractChannel.logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (");
                    sb.append(socketAddress);
                    sb.append(") anyway as requested.");
                    access$300.warn(sb.toString());
                }
                boolean isActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doBind(socketAddress);
                    if (!isActive && AbstractChannel.this.isActive()) {
                        invokeLater(new Runnable() {
                            public void run() {
                                AbstractChannel.this.pipeline.fireChannelActive();
                            }
                        });
                    }
                    safeSetSuccess(channelPromise);
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, th);
                    closeIfClosed();
                }
            }
        }

        public final void disconnect(ChannelPromise channelPromise) {
            assertEventLoop();
            if (channelPromise.setUncancellable()) {
                boolean isActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doDisconnect();
                    if (isActive && !AbstractChannel.this.isActive()) {
                        invokeLater(new Runnable() {
                            public void run() {
                                AbstractChannel.this.pipeline.fireChannelInactive();
                            }
                        });
                    }
                    safeSetSuccess(channelPromise);
                    closeIfClosed();
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, th);
                    closeIfClosed();
                }
            }
        }

        public final void close(ChannelPromise channelPromise) {
            assertEventLoop();
            close(channelPromise, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, false);
        }

        /* JADX INFO: finally extract failed */
        private void close(final ChannelPromise channelPromise, Throwable th, ClosedChannelException closedChannelException, boolean z) {
            if (channelPromise.setUncancellable()) {
                final ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
                if (channelOutboundBuffer == null) {
                    if (!(channelPromise instanceof VoidChannelPromise)) {
                        AbstractChannel.this.closeFuture.addListener((GenericFutureListener) new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                channelPromise.setSuccess();
                            }
                        });
                    }
                } else if (AbstractChannel.this.closeFuture.isDone()) {
                    safeSetSuccess(channelPromise);
                } else {
                    final boolean isActive = AbstractChannel.this.isActive();
                    this.outboundBuffer = null;
                    Executor prepareToClose = prepareToClose();
                    if (prepareToClose != null) {
                        final ChannelPromise channelPromise2 = channelPromise;
                        final Throwable th2 = th;
                        final boolean z2 = z;
                        final ClosedChannelException closedChannelException2 = closedChannelException;
                        C55565 r1 = new Runnable() {
                            public void run() {
                                try {
                                    AbstractUnsafe.this.doClose0(channelPromise2);
                                } finally {
                                    AbstractUnsafe.this.invokeLater(new Runnable() {
                                        public void run() {
                                            channelOutboundBuffer.failFlushed(th2, z2);
                                            channelOutboundBuffer.close(closedChannelException2);
                                            AbstractUnsafe.this.fireChannelInactiveAndDeregister(isActive);
                                        }
                                    });
                                }
                            }
                        };
                        prepareToClose.execute(r1);
                    } else {
                        try {
                            doClose0(channelPromise);
                            channelOutboundBuffer.failFlushed(th, z);
                            channelOutboundBuffer.close(closedChannelException);
                            if (this.inFlush0) {
                                invokeLater(new Runnable() {
                                    public void run() {
                                        AbstractUnsafe.this.fireChannelInactiveAndDeregister(isActive);
                                    }
                                });
                            } else {
                                fireChannelInactiveAndDeregister(isActive);
                            }
                        } catch (Throwable th3) {
                            channelOutboundBuffer.failFlushed(th, z);
                            channelOutboundBuffer.close(closedChannelException);
                            throw th3;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void doClose0(ChannelPromise channelPromise) {
            try {
                AbstractChannel.this.doClose();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetSuccess(channelPromise);
            } catch (Throwable th) {
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(channelPromise, th);
            }
        }

        /* access modifiers changed from: private */
        public void fireChannelInactiveAndDeregister(boolean z) {
            deregister(voidPromise(), z && !AbstractChannel.this.isActive());
        }

        public final void closeForcibly() {
            assertEventLoop();
            try {
                AbstractChannel.this.doClose();
            } catch (Exception e) {
                AbstractChannel.logger.warn("Failed to close a channel.", (Throwable) e);
            }
        }

        public final void deregister(ChannelPromise channelPromise) {
            assertEventLoop();
            deregister(channelPromise, false);
        }

        private void deregister(final ChannelPromise channelPromise, final boolean z) {
            if (channelPromise.setUncancellable()) {
                if (!AbstractChannel.this.registered) {
                    safeSetSuccess(channelPromise);
                } else {
                    invokeLater(new Runnable() {
                        /* JADX WARNING: Code restructure failed: missing block: B:17:0x005c, code lost:
                            if (p043io.netty.channel.AbstractChannel.access$000(r4.this$1.this$0) == false) goto L_0x0033;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
                            return;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001f, code lost:
                            if (p043io.netty.channel.AbstractChannel.access$000(r4.this$1.this$0) != false) goto L_0x0021;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
                            p043io.netty.channel.AbstractChannel.access$002(r4.this$1.this$0, false);
                            p043io.netty.channel.AbstractChannel.access$500(r4.this$1.this$0).fireChannelUnregistered();
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0033, code lost:
                            r4.this$1.safeSetSuccess(r2);
                         */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public void run() {
                            /*
                                r4 = this;
                                r0 = 0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this     // Catch:{ all -> 0x003b }
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this     // Catch:{ all -> 0x003b }
                                r1.doDeregister()     // Catch:{ all -> 0x003b }
                                boolean r1 = r3
                                if (r1 == 0) goto L_0x0017
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r1 = r1.pipeline
                                r1.fireChannelInactive()
                            L_0x0017:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this
                                boolean r1 = r1.registered
                                if (r1 == 0) goto L_0x0033
                            L_0x0021:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this
                                r1.registered = r0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r0 = p043io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r0 = r0.pipeline
                                r0.fireChannelUnregistered()
                            L_0x0033:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.ChannelPromise r1 = r2
                                r0.safeSetSuccess(r1)
                                goto L_0x005f
                            L_0x003b:
                                r1 = move-exception
                                io.netty.util.internal.logging.InternalLogger r2 = p043io.netty.channel.AbstractChannel.logger     // Catch:{ all -> 0x0060 }
                                java.lang.String r3 = "Unexpected exception occurred while deregistering a channel."
                                r2.warn(r3, r1)     // Catch:{ all -> 0x0060 }
                                boolean r1 = r3
                                if (r1 == 0) goto L_0x0054
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r1 = r1.pipeline
                                r1.fireChannelInactive()
                            L_0x0054:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = p043io.netty.channel.AbstractChannel.this
                                boolean r1 = r1.registered
                                if (r1 == 0) goto L_0x0033
                                goto L_0x0021
                            L_0x005f:
                                return
                            L_0x0060:
                                r1 = move-exception
                                boolean r2 = r3
                                if (r2 == 0) goto L_0x0070
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = p043io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r2 = r2.pipeline
                                r2.fireChannelInactive()
                            L_0x0070:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = p043io.netty.channel.AbstractChannel.this
                                boolean r2 = r2.registered
                                if (r2 == 0) goto L_0x008c
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = p043io.netty.channel.AbstractChannel.this
                                r2.registered = r0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r0 = p043io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r0 = r0.pipeline
                                r0.fireChannelUnregistered()
                            L_0x008c:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = p043io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.ChannelPromise r2 = r2
                                r0.safeSetSuccess(r2)
                                goto L_0x0095
                            L_0x0094:
                                throw r1
                            L_0x0095:
                                goto L_0x0094
                            */
                            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.AbstractChannel.AbstractUnsafe.C55597.run():void");
                        }
                    });
                }
            }
        }

        public final void beginRead() {
            assertEventLoop();
            if (AbstractChannel.this.isActive()) {
                try {
                    AbstractChannel.this.doBeginRead();
                } catch (Exception e) {
                    invokeLater(new Runnable() {
                        public void run() {
                            AbstractChannel.this.pipeline.fireExceptionCaught((Throwable) e);
                        }
                    });
                    close(voidPromise());
                }
            }
        }

        public final void write(Object obj, ChannelPromise channelPromise) {
            assertEventLoop();
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer == null) {
                safeSetFailure(channelPromise, AbstractChannel.WRITE_CLOSED_CHANNEL_EXCEPTION);
                ReferenceCountUtil.release(obj);
                return;
            }
            try {
                Object filterOutboundMessage = AbstractChannel.this.filterOutboundMessage(obj);
                int size = AbstractChannel.this.pipeline.estimatorHandle().size(filterOutboundMessage);
                if (size < 0) {
                    size = 0;
                }
                channelOutboundBuffer.addMessage(filterOutboundMessage, size, channelPromise);
            } catch (Throwable th) {
                safeSetFailure(channelPromise, th);
                ReferenceCountUtil.release(obj);
            }
        }

        public final void flush() {
            assertEventLoop();
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer != null) {
                channelOutboundBuffer.addFlush();
                flush0();
            }
        }

        /* access modifiers changed from: protected */
        public void flush0() {
            if (!this.inFlush0) {
                ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
                if (channelOutboundBuffer != null && !channelOutboundBuffer.isEmpty()) {
                    this.inFlush0 = true;
                    if (!AbstractChannel.this.isActive()) {
                        try {
                            if (AbstractChannel.this.isOpen()) {
                                channelOutboundBuffer.failFlushed(AbstractChannel.FLUSH0_NOT_YET_CONNECTED_EXCEPTION, true);
                            } else {
                                channelOutboundBuffer.failFlushed(AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
                            }
                        } finally {
                            this.inFlush0 = false;
                        }
                    } else {
                        try {
                            AbstractChannel.this.doWrite(channelOutboundBuffer);
                        } catch (Throwable th) {
                            this.inFlush0 = false;
                            throw th;
                        }
                        this.inFlush0 = false;
                    }
                }
            }
        }

        public final ChannelPromise voidPromise() {
            assertEventLoop();
            return AbstractChannel.this.unsafeVoidPromise;
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public final boolean ensureOpen(ChannelPromise channelPromise) {
            if (AbstractChannel.this.isOpen()) {
                return true;
            }
            safeSetFailure(channelPromise, AbstractChannel.ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION);
            return false;
        }

        /* access modifiers changed from: protected */
        public final void safeSetSuccess(ChannelPromise channelPromise) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.trySuccess()) {
                AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", (Object) channelPromise);
            }
        }

        /* access modifiers changed from: protected */
        public final void safeSetFailure(ChannelPromise channelPromise, Throwable th) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(th)) {
                AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, th);
            }
        }

        /* access modifiers changed from: protected */
        public final void closeIfClosed() {
            if (!AbstractChannel.this.isOpen()) {
                close(voidPromise());
            }
        }

        /* access modifiers changed from: private */
        public void invokeLater(Runnable runnable) {
            try {
                AbstractChannel.this.eventLoop().execute(runnable);
            } catch (RejectedExecutionException e) {
                AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", (Throwable) e);
            }
        }

        /* access modifiers changed from: protected */
        public final Throwable annotateConnectException(Throwable th, SocketAddress socketAddress) {
            if (th instanceof ConnectException) {
                return new AnnotatedConnectException((ConnectException) th, socketAddress);
            }
            if (th instanceof NoRouteToHostException) {
                return new AnnotatedNoRouteToHostException((NoRouteToHostException) th, socketAddress);
            }
            return th instanceof SocketException ? new AnnotatedSocketException((SocketException) th, socketAddress) : th;
        }
    }

    /* renamed from: io.netty.channel.AbstractChannel$AnnotatedConnectException */
    private static final class AnnotatedConnectException extends ConnectException {
        private static final long serialVersionUID = 3901958112696433556L;

        public Throwable fillInStackTrace() {
            return this;
        }

        AnnotatedConnectException(ConnectException connectException, SocketAddress socketAddress) {
            StringBuilder sb = new StringBuilder();
            sb.append(connectException.getMessage());
            sb.append(": ");
            sb.append(socketAddress);
            super(sb.toString());
            initCause(connectException);
            setStackTrace(connectException.getStackTrace());
        }
    }

    /* renamed from: io.netty.channel.AbstractChannel$AnnotatedNoRouteToHostException */
    private static final class AnnotatedNoRouteToHostException extends NoRouteToHostException {
        private static final long serialVersionUID = -6801433937592080623L;

        public Throwable fillInStackTrace() {
            return this;
        }

        AnnotatedNoRouteToHostException(NoRouteToHostException noRouteToHostException, SocketAddress socketAddress) {
            StringBuilder sb = new StringBuilder();
            sb.append(noRouteToHostException.getMessage());
            sb.append(": ");
            sb.append(socketAddress);
            super(sb.toString());
            initCause(noRouteToHostException);
            setStackTrace(noRouteToHostException.getStackTrace());
        }
    }

    /* renamed from: io.netty.channel.AbstractChannel$AnnotatedSocketException */
    private static final class AnnotatedSocketException extends SocketException {
        private static final long serialVersionUID = 3896743275010454039L;

        public Throwable fillInStackTrace() {
            return this;
        }

        AnnotatedSocketException(SocketException socketException, SocketAddress socketAddress) {
            StringBuilder sb = new StringBuilder();
            sb.append(socketException.getMessage());
            sb.append(": ");
            sb.append(socketAddress);
            super(sb.toString());
            initCause(socketException);
            setStackTrace(socketException.getStackTrace());
        }
    }

    /* renamed from: io.netty.channel.AbstractChannel$CloseFuture */
    static final class CloseFuture extends DefaultChannelPromise {
        CloseFuture(AbstractChannel abstractChannel) {
            super(abstractChannel);
        }

        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }

        public ChannelPromise setFailure(Throwable th) {
            throw new IllegalStateException();
        }

        public boolean trySuccess() {
            throw new IllegalStateException();
        }

        public boolean tryFailure(Throwable th) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public boolean setClosed() {
            return super.trySuccess();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void doBeginRead() throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doBind(SocketAddress socketAddress) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doClose() throws Exception;

    /* access modifiers changed from: protected */
    public void doDeregister() throws Exception {
    }

    /* access modifiers changed from: protected */
    public abstract void doDisconnect() throws Exception;

    /* access modifiers changed from: protected */
    public void doRegister() throws Exception {
    }

    /* access modifiers changed from: protected */
    public abstract void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    public final boolean equals(Object obj) {
        return this == obj;
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object obj) throws Exception {
        return obj;
    }

    /* access modifiers changed from: protected */
    public abstract boolean isCompatible(EventLoop eventLoop2);

    /* access modifiers changed from: protected */
    public abstract SocketAddress localAddress0();

    /* access modifiers changed from: protected */
    public abstract AbstractUnsafe newUnsafe();

    /* access modifiers changed from: protected */
    public abstract SocketAddress remoteAddress0();

    static {
        String str = "flush0()";
        FLUSH0_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, str);
        FLUSH0_NOT_YET_CONNECTED_EXCEPTION = (NotYetConnectedException) ThrowableUtil.unknownStackTrace(new NotYetConnectedException(), AbstractUnsafe.class, str);
    }

    protected AbstractChannel(Channel channel) {
        this.parent = channel;
        this.f3698id = newId();
        this.unsafe = newUnsafe();
        this.pipeline = newChannelPipeline();
    }

    protected AbstractChannel(Channel channel, ChannelId channelId) {
        this.parent = channel;
        this.f3698id = channelId;
        this.unsafe = newUnsafe();
        this.pipeline = newChannelPipeline();
    }

    /* renamed from: id */
    public final ChannelId mo66192id() {
        return this.f3698id;
    }

    /* access modifiers changed from: protected */
    public ChannelId newId() {
        return DefaultChannelId.newInstance();
    }

    /* access modifiers changed from: protected */
    public DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    public boolean isWritable() {
        ChannelOutboundBuffer outboundBuffer = this.unsafe.outboundBuffer();
        return outboundBuffer != null && outboundBuffer.isWritable();
    }

    public long bytesBeforeUnwritable() {
        ChannelOutboundBuffer outboundBuffer = this.unsafe.outboundBuffer();
        if (outboundBuffer != null) {
            return outboundBuffer.bytesBeforeUnwritable();
        }
        return 0;
    }

    public long bytesBeforeWritable() {
        ChannelOutboundBuffer outboundBuffer = this.unsafe.outboundBuffer();
        if (outboundBuffer != null) {
            return outboundBuffer.bytesBeforeWritable();
        }
        return Long.MAX_VALUE;
    }

    public Channel parent() {
        return this.parent;
    }

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ByteBufAllocator alloc() {
        return config().getAllocator();
    }

    public EventLoop eventLoop() {
        EventLoop eventLoop2 = this.eventLoop;
        if (eventLoop2 != null) {
            return eventLoop2;
        }
        throw new IllegalStateException("channel not registered to an event loop");
    }

    public SocketAddress localAddress() {
        SocketAddress socketAddress = this.localAddress;
        if (socketAddress != null) {
            return socketAddress;
        }
        try {
            SocketAddress localAddress2 = unsafe().localAddress();
            this.localAddress = localAddress2;
            return localAddress2;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void invalidateLocalAddress() {
        this.localAddress = null;
    }

    public SocketAddress remoteAddress() {
        SocketAddress socketAddress = this.remoteAddress;
        if (socketAddress != null) {
            return socketAddress;
        }
        try {
            SocketAddress remoteAddress2 = unsafe().remoteAddress();
            this.remoteAddress = remoteAddress2;
            return remoteAddress2;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public ChannelFuture bind(SocketAddress socketAddress) {
        return this.pipeline.bind(socketAddress);
    }

    public ChannelFuture connect(SocketAddress socketAddress) {
        return this.pipeline.connect(socketAddress);
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.pipeline.connect(socketAddress, socketAddress2);
    }

    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }

    public ChannelFuture close() {
        return this.pipeline.close();
    }

    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }

    public Channel flush() {
        this.pipeline.flush();
        return this;
    }

    public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.pipeline.bind(socketAddress, channelPromise);
    }

    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, channelPromise);
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, socketAddress2, channelPromise);
    }

    public ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.pipeline.disconnect(channelPromise);
    }

    public ChannelFuture close(ChannelPromise channelPromise) {
        return this.pipeline.close(channelPromise);
    }

    public ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.pipeline.deregister(channelPromise);
    }

    public Channel read() {
        this.pipeline.read();
        return this;
    }

    public ChannelFuture write(Object obj) {
        return this.pipeline.write(obj);
    }

    public ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        return this.pipeline.write(obj, channelPromise);
    }

    public ChannelFuture writeAndFlush(Object obj) {
        return this.pipeline.writeAndFlush(obj);
    }

    public ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        return this.pipeline.writeAndFlush(obj, channelPromise);
    }

    public ChannelPromise newPromise() {
        return this.pipeline.newPromise();
    }

    public ChannelProgressivePromise newProgressivePromise() {
        return this.pipeline.newProgressivePromise();
    }

    public ChannelFuture newSucceededFuture() {
        return this.pipeline.newSucceededFuture();
    }

    public ChannelFuture newFailedFuture(Throwable th) {
        return this.pipeline.newFailedFuture(th);
    }

    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }

    public Unsafe unsafe() {
        return this.unsafe;
    }

    public final int hashCode() {
        return this.f3698id.hashCode();
    }

    public final int compareTo(Channel channel) {
        if (this == channel) {
            return 0;
        }
        return mo66192id().compareTo(channel.mo66192id());
    }

    public String toString() {
        boolean isActive = isActive();
        if (this.strValActive == isActive) {
            String str = this.strVal;
            if (str != null) {
                return str;
            }
        }
        SocketAddress remoteAddress2 = remoteAddress();
        SocketAddress localAddress2 = localAddress();
        String str2 = ", L:";
        String str3 = "[id: 0x";
        if (remoteAddress2 != null) {
            StringBuilder sb = new StringBuilder(96);
            sb.append(str3);
            sb.append(this.f3698id.asShortText());
            sb.append(str2);
            sb.append(localAddress2);
            sb.append(isActive ? " - " : " ! ");
            sb.append("R:");
            sb.append(remoteAddress2);
            sb.append(']');
            this.strVal = sb.toString();
        } else if (localAddress2 != null) {
            StringBuilder sb2 = new StringBuilder(64);
            sb2.append(str3);
            sb2.append(this.f3698id.asShortText());
            sb2.append(str2);
            sb2.append(localAddress2);
            sb2.append(']');
            this.strVal = sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder(16);
            sb3.append(str3);
            sb3.append(this.f3698id.asShortText());
            sb3.append(']');
            this.strVal = sb3.toString();
        }
        this.strValActive = isActive;
        return this.strVal;
    }

    public final ChannelPromise voidPromise() {
        return this.pipeline.voidPromise();
    }
}
