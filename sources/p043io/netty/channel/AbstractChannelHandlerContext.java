package p043io.netty.channel;

import androidx.core.app.NotificationCompat;
import com.braintreepayments.api.models.PostalAddressParser;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.Attribute;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.DefaultAttributeMap;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.ResourceLeakHint;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.OrderedEventExecutor;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PromiseNotificationUtil;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.AbstractChannelHandlerContext */
abstract class AbstractChannelHandlerContext extends DefaultAttributeMap implements ChannelHandlerContext, ResourceLeakHint {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ADD_COMPLETE = 2;
    private static final int ADD_PENDING = 1;
    private static final AtomicIntegerFieldUpdater<AbstractChannelHandlerContext> HANDLER_STATE_UPDATER;
    private static final int INIT = 0;
    private static final int REMOVE_COMPLETE = 3;
    private static final InternalLogger logger;
    final EventExecutor executor;
    private volatile int handlerState = 0;
    private final boolean inbound;
    private Runnable invokeChannelReadCompleteTask;
    private Runnable invokeChannelWritableStateChangedTask;
    private Runnable invokeFlushTask;
    private Runnable invokeReadTask;
    private final String name;
    volatile AbstractChannelHandlerContext next;
    private final boolean ordered;
    private final boolean outbound;
    /* access modifiers changed from: private */
    public final DefaultChannelPipeline pipeline;
    volatile AbstractChannelHandlerContext prev;
    private ChannelFuture succeededFuture;

    /* renamed from: io.netty.channel.AbstractChannelHandlerContext$AbstractWriteTask */
    static abstract class AbstractWriteTask implements Runnable {
        private static final boolean ESTIMATE_TASK_SIZE_ON_SUBMIT = SystemPropertyUtil.getBoolean("io.netty.transport.estimateSizeOnSubmit", true);
        private static final int WRITE_TASK_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.writeTaskSizeOverhead", 48);
        private AbstractChannelHandlerContext ctx;
        private final Handle<AbstractWriteTask> handle;
        private Object msg;
        private ChannelPromise promise;
        private int size;

        private AbstractWriteTask(Handle<? extends AbstractWriteTask> handle2) {
            this.handle = handle2;
        }

        protected static void init(AbstractWriteTask abstractWriteTask, AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj, ChannelPromise channelPromise) {
            abstractWriteTask.ctx = abstractChannelHandlerContext;
            abstractWriteTask.msg = obj;
            abstractWriteTask.promise = channelPromise;
            if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
                ChannelOutboundBuffer outboundBuffer = abstractChannelHandlerContext.channel().unsafe().outboundBuffer();
                if (outboundBuffer != null) {
                    abstractWriteTask.size = abstractChannelHandlerContext.pipeline.estimatorHandle().size(obj) + WRITE_TASK_OVERHEAD;
                    outboundBuffer.incrementPendingOutboundBytes((long) abstractWriteTask.size);
                    return;
                }
                abstractWriteTask.size = 0;
                return;
            }
            abstractWriteTask.size = 0;
        }

        public final void run() {
            try {
                ChannelOutboundBuffer outboundBuffer = this.ctx.channel().unsafe().outboundBuffer();
                if (ESTIMATE_TASK_SIZE_ON_SUBMIT && outboundBuffer != null) {
                    outboundBuffer.decrementPendingOutboundBytes((long) this.size);
                }
                write(this.ctx, this.msg, this.promise);
            } finally {
                this.ctx = null;
                this.msg = null;
                this.promise = null;
                this.handle.recycle(this);
            }
        }

        /* access modifiers changed from: protected */
        public void write(AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj, ChannelPromise channelPromise) {
            abstractChannelHandlerContext.invokeWrite(obj, channelPromise);
        }
    }

    /* renamed from: io.netty.channel.AbstractChannelHandlerContext$WriteAndFlushTask */
    static final class WriteAndFlushTask extends AbstractWriteTask {
        private static final Recycler<WriteAndFlushTask> RECYCLER = new Recycler<WriteAndFlushTask>() {
            /* access modifiers changed from: protected */
            public WriteAndFlushTask newObject(Handle<WriteAndFlushTask> handle) {
                return new WriteAndFlushTask(handle);
            }
        };

        /* access modifiers changed from: private */
        public static WriteAndFlushTask newInstance(AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj, ChannelPromise channelPromise) {
            WriteAndFlushTask writeAndFlushTask = (WriteAndFlushTask) RECYCLER.get();
            init(writeAndFlushTask, abstractChannelHandlerContext, obj, channelPromise);
            return writeAndFlushTask;
        }

        private WriteAndFlushTask(Handle<WriteAndFlushTask> handle) {
            super(handle);
        }

        public void write(AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj, ChannelPromise channelPromise) {
            super.write(abstractChannelHandlerContext, obj, channelPromise);
            abstractChannelHandlerContext.invokeFlush();
        }
    }

    /* renamed from: io.netty.channel.AbstractChannelHandlerContext$WriteTask */
    static final class WriteTask extends AbstractWriteTask implements NonWakeupRunnable {
        private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>() {
            /* access modifiers changed from: protected */
            public WriteTask newObject(Handle<WriteTask> handle) {
                return new WriteTask(handle);
            }
        };

        /* access modifiers changed from: private */
        public static WriteTask newInstance(AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj, ChannelPromise channelPromise) {
            WriteTask writeTask = (WriteTask) RECYCLER.get();
            init(writeTask, abstractChannelHandlerContext, obj, channelPromise);
            return writeTask;
        }

        private WriteTask(Handle<WriteTask> handle) {
            super(handle);
        }
    }

    static {
        Class<AbstractChannelHandlerContext> cls = AbstractChannelHandlerContext.class;
        logger = InternalLoggerFactory.getInstance(cls);
        HANDLER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "handlerState");
    }

    AbstractChannelHandlerContext(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String str, boolean z, boolean z2) {
        boolean z3 = false;
        this.name = (String) ObjectUtil.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        this.pipeline = defaultChannelPipeline;
        this.executor = eventExecutor;
        this.inbound = z;
        this.outbound = z2;
        if (eventExecutor == null || (eventExecutor instanceof OrderedEventExecutor)) {
            z3 = true;
        }
        this.ordered = z3;
    }

    public Channel channel() {
        return this.pipeline.channel();
    }

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ByteBufAllocator alloc() {
        return channel().config().getAllocator();
    }

    public EventExecutor executor() {
        EventExecutor eventExecutor = this.executor;
        return eventExecutor == null ? channel().eventLoop() : eventExecutor;
    }

    public String name() {
        return this.name;
    }

    public ChannelHandlerContext fireChannelRegistered() {
        invokeChannelRegistered(findContextInbound());
        return this;
    }

    static void invokeChannelRegistered(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelRegistered();
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelRegistered();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelRegistered() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelRegistered(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelRegistered();
        }
    }

    public ChannelHandlerContext fireChannelUnregistered() {
        invokeChannelUnregistered(findContextInbound());
        return this;
    }

    static void invokeChannelUnregistered(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelUnregistered();
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelUnregistered();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelUnregistered() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelUnregistered(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelUnregistered();
        }
    }

    public ChannelHandlerContext fireChannelActive() {
        invokeChannelActive(findContextInbound());
        return this;
    }

    static void invokeChannelActive(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelActive();
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelActive();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelActive() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelActive(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelActive();
        }
    }

    public ChannelHandlerContext fireChannelInactive() {
        invokeChannelInactive(findContextInbound());
        return this;
    }

    static void invokeChannelInactive(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelInactive();
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelInactive();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelInactive() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelInactive(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelInactive();
        }
    }

    public ChannelHandlerContext fireExceptionCaught(Throwable th) {
        invokeExceptionCaught(this.next, th);
        return this;
    }

    static void invokeExceptionCaught(AbstractChannelHandlerContext abstractChannelHandlerContext, final Throwable th) {
        ObjectUtil.checkNotNull(th, "cause");
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeExceptionCaught(th);
            return;
        }
        try {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeExceptionCaught(th);
                }
            });
        } catch (Throwable th2) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to submit an exceptionCaught() event.", th2);
                logger.warn("The exceptionCaught() event that was failed to submit was:", th);
            }
        }
    }

    /* access modifiers changed from: private */
    public void invokeExceptionCaught(Throwable th) {
        if (invokeHandler()) {
            try {
                handler().exceptionCaught(this, th);
            } catch (Throwable th2) {
                if (logger.isDebugEnabled()) {
                    logger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(th2), th);
                } else if (logger.isWarnEnabled()) {
                    logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", th2, th);
                }
            }
        } else {
            fireExceptionCaught(th);
        }
    }

    public ChannelHandlerContext fireUserEventTriggered(Object obj) {
        invokeUserEventTriggered(findContextInbound(), obj);
        return this;
    }

    static void invokeUserEventTriggered(AbstractChannelHandlerContext abstractChannelHandlerContext, final Object obj) {
        ObjectUtil.checkNotNull(obj, "event");
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeUserEventTriggered(obj);
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeUserEventTriggered(obj);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeUserEventTriggered(Object obj) {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).userEventTriggered(this, obj);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireUserEventTriggered(obj);
        }
    }

    public ChannelHandlerContext fireChannelRead(Object obj) {
        invokeChannelRead(findContextInbound(), obj);
        return this;
    }

    static void invokeChannelRead(AbstractChannelHandlerContext abstractChannelHandlerContext, Object obj) {
        final Object obj2 = abstractChannelHandlerContext.pipeline.touch(ObjectUtil.checkNotNull(obj, NotificationCompat.CATEGORY_MESSAGE), abstractChannelHandlerContext);
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelRead(obj2);
        } else {
            executor2.execute(new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelRead(obj2);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelRead(Object obj) {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelRead(this, obj);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelRead(obj);
        }
    }

    public ChannelHandlerContext fireChannelReadComplete() {
        invokeChannelReadComplete(findContextInbound());
        return this;
    }

    static void invokeChannelReadComplete(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelReadComplete();
            return;
        }
        Runnable runnable = abstractChannelHandlerContext.invokeChannelReadCompleteTask;
        if (runnable == null) {
            runnable = new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelReadComplete();
                }
            };
            abstractChannelHandlerContext.invokeChannelReadCompleteTask = runnable;
        }
        executor2.execute(runnable);
    }

    /* access modifiers changed from: private */
    public void invokeChannelReadComplete() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelReadComplete(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelReadComplete();
        }
    }

    public ChannelHandlerContext fireChannelWritabilityChanged() {
        invokeChannelWritabilityChanged(findContextInbound());
        return this;
    }

    static void invokeChannelWritabilityChanged(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor executor2 = abstractChannelHandlerContext.executor();
        if (executor2.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelWritabilityChanged();
            return;
        }
        Runnable runnable = abstractChannelHandlerContext.invokeChannelWritableStateChangedTask;
        if (runnable == null) {
            runnable = new Runnable(abstractChannelHandlerContext) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelWritabilityChanged();
                }
            };
            abstractChannelHandlerContext.invokeChannelWritableStateChangedTask = runnable;
        }
        executor2.execute(runnable);
    }

    /* access modifiers changed from: private */
    public void invokeChannelWritabilityChanged() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelWritabilityChanged(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            fireChannelWritabilityChanged();
        }
    }

    public ChannelFuture bind(SocketAddress socketAddress) {
        return bind(socketAddress, newPromise());
    }

    public ChannelFuture connect(SocketAddress socketAddress) {
        return connect(socketAddress, newPromise());
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return connect(socketAddress, socketAddress2, newPromise());
    }

    public ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    public ChannelFuture close() {
        return close(newPromise());
    }

    public ChannelFuture deregister() {
        return deregister(newPromise());
    }

    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        } else if (isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        } else {
            final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
            EventExecutor executor2 = findContextOutbound.executor();
            if (executor2.inEventLoop()) {
                findContextOutbound.invokeBind(socketAddress, channelPromise);
            } else {
                safeExecute(executor2, new Runnable() {
                    public void run() {
                        findContextOutbound.invokeBind(socketAddress, channelPromise);
                    }
                }, channelPromise, null);
            }
            return channelPromise;
        }
    }

    /* access modifiers changed from: private */
    public void invokeBind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).bind(this, socketAddress, channelPromise);
            } catch (Throwable th) {
                notifyOutboundHandlerException(th, channelPromise);
            }
        } else {
            bind(socketAddress, channelPromise);
        }
    }

    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return connect(socketAddress, null, channelPromise);
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        } else if (isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        } else {
            final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
            EventExecutor executor2 = findContextOutbound.executor();
            if (executor2.inEventLoop()) {
                findContextOutbound.invokeConnect(socketAddress, socketAddress2, channelPromise);
            } else {
                final SocketAddress socketAddress3 = socketAddress;
                final SocketAddress socketAddress4 = socketAddress2;
                final ChannelPromise channelPromise2 = channelPromise;
                C556311 r1 = new Runnable() {
                    public void run() {
                        findContextOutbound.invokeConnect(socketAddress3, socketAddress4, channelPromise2);
                    }
                };
                safeExecute(executor2, r1, channelPromise, null);
            }
            return channelPromise;
        }
    }

    /* access modifiers changed from: private */
    public void invokeConnect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).connect(this, socketAddress, socketAddress2, channelPromise);
            } catch (Throwable th) {
                notifyOutboundHandlerException(th, channelPromise);
            }
        } else {
            connect(socketAddress, socketAddress2, channelPromise);
        }
    }

    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        if (isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        EventExecutor executor2 = findContextOutbound.executor();
        if (!executor2.inEventLoop()) {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    if (!AbstractChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
                        findContextOutbound.invokeClose(channelPromise);
                    } else {
                        findContextOutbound.invokeDisconnect(channelPromise);
                    }
                }
            }, channelPromise, null);
        } else if (!channel().metadata().hasDisconnect()) {
            findContextOutbound.invokeClose(channelPromise);
        } else {
            findContextOutbound.invokeDisconnect(channelPromise);
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void invokeDisconnect(ChannelPromise channelPromise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).disconnect(this, channelPromise);
            } catch (Throwable th) {
                notifyOutboundHandlerException(th, channelPromise);
            }
        } else {
            disconnect(channelPromise);
        }
    }

    public ChannelFuture close(final ChannelPromise channelPromise) {
        if (isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        EventExecutor executor2 = findContextOutbound.executor();
        if (executor2.inEventLoop()) {
            findContextOutbound.invokeClose(channelPromise);
        } else {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    findContextOutbound.invokeClose(channelPromise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void invokeClose(ChannelPromise channelPromise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).close(this, channelPromise);
            } catch (Throwable th) {
                notifyOutboundHandlerException(th, channelPromise);
            }
        } else {
            close(channelPromise);
        }
    }

    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        if (isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        EventExecutor executor2 = findContextOutbound.executor();
        if (executor2.inEventLoop()) {
            findContextOutbound.invokeDeregister(channelPromise);
        } else {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    findContextOutbound.invokeDeregister(channelPromise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void invokeDeregister(ChannelPromise channelPromise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).deregister(this, channelPromise);
            } catch (Throwable th) {
                notifyOutboundHandlerException(th, channelPromise);
            }
        } else {
            deregister(channelPromise);
        }
    }

    public ChannelHandlerContext read() {
        final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        EventExecutor executor2 = findContextOutbound.executor();
        if (executor2.inEventLoop()) {
            findContextOutbound.invokeRead();
        } else {
            Runnable runnable = findContextOutbound.invokeReadTask;
            if (runnable == null) {
                runnable = new Runnable() {
                    public void run() {
                        findContextOutbound.invokeRead();
                    }
                };
                findContextOutbound.invokeReadTask = runnable;
            }
            executor2.execute(runnable);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public void invokeRead() {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).read(this);
            } catch (Throwable th) {
                notifyHandlerException(th);
            }
        } else {
            read();
        }
    }

    public ChannelFuture write(Object obj) {
        return write(obj, newPromise());
    }

    public ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        if (obj != null) {
            try {
                if (isNotValidPromise(channelPromise, true)) {
                    ReferenceCountUtil.release(obj);
                    return channelPromise;
                }
                write(obj, false, channelPromise);
                return channelPromise;
            } catch (RuntimeException e) {
                ReferenceCountUtil.release(obj);
                throw e;
            }
        } else {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        }
    }

    /* access modifiers changed from: private */
    public void invokeWrite(Object obj, ChannelPromise channelPromise) {
        if (invokeHandler()) {
            invokeWrite0(obj, channelPromise);
        } else {
            write(obj, channelPromise);
        }
    }

    private void invokeWrite0(Object obj, ChannelPromise channelPromise) {
        try {
            ((ChannelOutboundHandler) handler()).write(this, obj, channelPromise);
        } catch (Throwable th) {
            notifyOutboundHandlerException(th, channelPromise);
        }
    }

    public ChannelHandlerContext flush() {
        final AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        EventExecutor executor2 = findContextOutbound.executor();
        if (executor2.inEventLoop()) {
            findContextOutbound.invokeFlush();
        } else {
            Runnable runnable = findContextOutbound.invokeFlushTask;
            if (runnable == null) {
                runnable = new Runnable() {
                    public void run() {
                        findContextOutbound.invokeFlush();
                    }
                };
                findContextOutbound.invokeFlushTask = runnable;
            }
            safeExecute(executor2, runnable, channel().voidPromise(), null);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public void invokeFlush() {
        if (invokeHandler()) {
            invokeFlush0();
        } else {
            flush();
        }
    }

    private void invokeFlush0() {
        try {
            ((ChannelOutboundHandler) handler()).flush(this);
        } catch (Throwable th) {
            notifyHandlerException(th);
        }
    }

    public ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        if (obj == null) {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        } else if (isNotValidPromise(channelPromise, true)) {
            ReferenceCountUtil.release(obj);
            return channelPromise;
        } else {
            write(obj, true, channelPromise);
            return channelPromise;
        }
    }

    private void invokeWriteAndFlush(Object obj, ChannelPromise channelPromise) {
        if (invokeHandler()) {
            invokeWrite0(obj, channelPromise);
            invokeFlush0();
            return;
        }
        writeAndFlush(obj, channelPromise);
    }

    private void write(Object obj, boolean z, ChannelPromise channelPromise) {
        Runnable runnable;
        AbstractChannelHandlerContext findContextOutbound = findContextOutbound();
        Object obj2 = this.pipeline.touch(obj, findContextOutbound);
        EventExecutor executor2 = findContextOutbound.executor();
        if (!executor2.inEventLoop()) {
            if (z) {
                runnable = WriteAndFlushTask.newInstance(findContextOutbound, obj2, channelPromise);
            } else {
                runnable = WriteTask.newInstance(findContextOutbound, obj2, channelPromise);
            }
            safeExecute(executor2, runnable, channelPromise, obj2);
        } else if (z) {
            findContextOutbound.invokeWriteAndFlush(obj2, channelPromise);
        } else {
            findContextOutbound.invokeWrite(obj2, channelPromise);
        }
    }

    public ChannelFuture writeAndFlush(Object obj) {
        return writeAndFlush(obj, newPromise());
    }

    private static void notifyOutboundHandlerException(Throwable th, ChannelPromise channelPromise) {
        PromiseNotificationUtil.tryFailure(channelPromise, th, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    private void notifyHandlerException(Throwable th) {
        if (inExceptionCaught(th)) {
            if (logger.isWarnEnabled()) {
                logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", th);
            }
            return;
        }
        invokeExceptionCaught(th);
    }

    private static boolean inExceptionCaught(Throwable th) {
        do {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement == null) {
                        break;
                    }
                    if ("exceptionCaught".equals(stackTraceElement.getMethodName())) {
                        return true;
                    }
                }
            }
            th = th.getCause();
        } while (th != null);
        return false;
    }

    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(channel(), executor());
    }

    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(channel(), executor());
    }

    public ChannelFuture newSucceededFuture() {
        ChannelFuture channelFuture = this.succeededFuture;
        if (channelFuture != null) {
            return channelFuture;
        }
        SucceededChannelFuture succeededChannelFuture = new SucceededChannelFuture(channel(), executor());
        this.succeededFuture = succeededChannelFuture;
        return succeededChannelFuture;
    }

    public ChannelFuture newFailedFuture(Throwable th) {
        return new FailedChannelFuture(channel(), executor(), th);
    }

    private boolean isNotValidPromise(ChannelPromise channelPromise, boolean z) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        } else if (channelPromise.isDone()) {
            if (channelPromise.isCancelled()) {
                return true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("promise already done: ");
            sb.append(channelPromise);
            throw new IllegalArgumentException(sb.toString());
        } else if (channelPromise.channel() != channel()) {
            throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[]{channelPromise.channel(), channel()}));
        } else if (channelPromise.getClass() == DefaultChannelPromise.class) {
            return false;
        } else {
            if (!z && (channelPromise instanceof VoidChannelPromise)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(StringUtil.simpleClassName(VoidChannelPromise.class));
                sb2.append(" not allowed for this operation");
                throw new IllegalArgumentException(sb2.toString());
            } else if (!(channelPromise instanceof CloseFuture)) {
                return false;
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(StringUtil.simpleClassName(CloseFuture.class));
                sb3.append(" not allowed in a pipeline");
                throw new IllegalArgumentException(sb3.toString());
            }
        }
    }

    private AbstractChannelHandlerContext findContextInbound() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this;
        do {
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        } while (!abstractChannelHandlerContext.inbound);
        return abstractChannelHandlerContext;
    }

    private AbstractChannelHandlerContext findContextOutbound() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this;
        do {
            abstractChannelHandlerContext = abstractChannelHandlerContext.prev;
        } while (!abstractChannelHandlerContext.outbound);
        return abstractChannelHandlerContext;
    }

    public ChannelPromise voidPromise() {
        return channel().voidPromise();
    }

    /* access modifiers changed from: 0000 */
    public final void setRemoved() {
        this.handlerState = 3;
    }

    /* access modifiers changed from: 0000 */
    public final void setAddComplete() {
        int i;
        do {
            i = this.handlerState;
            if (i == 3) {
                return;
            }
        } while (!HANDLER_STATE_UPDATER.compareAndSet(this, i, 2));
    }

    /* access modifiers changed from: 0000 */
    public final void setAddPending() {
        HANDLER_STATE_UPDATER.compareAndSet(this, 0, 1);
    }

    private boolean invokeHandler() {
        int i = this.handlerState;
        if (i == 2) {
            return true;
        }
        if (this.ordered || i != 1) {
            return false;
        }
        return true;
    }

    public boolean isRemoved() {
        return this.handlerState == 3;
    }

    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        return channel().attr(attributeKey);
    }

    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        return channel().hasAttr(attributeKey);
    }

    private static void safeExecute(EventExecutor eventExecutor, Runnable runnable, ChannelPromise channelPromise, Object obj) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable th) {
            if (obj != null) {
                ReferenceCountUtil.release(obj);
            }
            throw th;
        }
    }

    public String toHintString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\'');
        sb.append(this.name);
        sb.append("' will handle the message from this point.");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(ChannelHandlerContext.class));
        sb.append('(');
        sb.append(this.name);
        sb.append(", ");
        sb.append(channel());
        sb.append(')');
        return sb.toString();
    }
}
