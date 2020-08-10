package p043io.netty.channel;

import com.braintreepayments.api.models.PostalAddressParser;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.RejectedExecutionException;
import p043io.netty.channel.Channel.Unsafe;
import p043io.netty.channel.MessageSizeEstimator.Handle;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.ResourceLeakDetector;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.EventExecutorGroup;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.DefaultChannelPipeline */
public class DefaultChannelPipeline implements ChannelPipeline {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final String HEAD_NAME = generateName0(HeadContext.class);
    /* access modifiers changed from: private */
    public static final String TAIL_NAME = generateName0(TailContext.class);
    static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
    private static final FastThreadLocal<Map<Class<?>, String>> nameCaches = new FastThreadLocal<Map<Class<?>, String>>() {
        /* access modifiers changed from: protected */
        public Map<Class<?>, String> initialValue() throws Exception {
            return new WeakHashMap();
        }
    };
    /* access modifiers changed from: private */
    public final Channel channel;
    private Map<EventExecutorGroup, EventExecutor> childExecutors;
    private Handle estimatorHandle;
    private boolean firstRegistration = true;
    final AbstractChannelHandlerContext head;
    private PendingHandlerCallback pendingHandlerCallbackHead;
    private boolean registered;
    private final ChannelFuture succeededFuture;
    final AbstractChannelHandlerContext tail;
    private final boolean touch = ResourceLeakDetector.isEnabled();
    private final VoidChannelPromise voidPromise;

    /* renamed from: io.netty.channel.DefaultChannelPipeline$HeadContext */
    final class HeadContext extends AbstractChannelHandlerContext implements ChannelOutboundHandler, ChannelInboundHandler {
        private final Unsafe unsafe;

        public ChannelHandler handler() {
            return this;
        }

        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        HeadContext(DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, DefaultChannelPipeline.HEAD_NAME, false, true);
            this.unsafe = defaultChannelPipeline.channel().unsafe();
            setAddComplete();
        }

        public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
            this.unsafe.bind(socketAddress, channelPromise);
        }

        public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
            this.unsafe.connect(socketAddress, socketAddress2, channelPromise);
        }

        public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.disconnect(channelPromise);
        }

        public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.close(channelPromise);
        }

        public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.deregister(channelPromise);
        }

        public void read(ChannelHandlerContext channelHandlerContext) {
            this.unsafe.beginRead();
        }

        public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
            this.unsafe.write(obj, channelPromise);
        }

        public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.unsafe.flush();
        }

        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            channelHandlerContext.fireExceptionCaught(th);
        }

        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            DefaultChannelPipeline.this.invokeHandlerAddedIfNeeded();
            channelHandlerContext.fireChannelRegistered();
        }

        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelUnregistered();
            if (!DefaultChannelPipeline.this.channel.isOpen()) {
                DefaultChannelPipeline.this.destroy();
            }
        }

        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelActive();
            readIfIsAutoRead();
        }

        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelInactive();
        }

        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            channelHandlerContext.fireChannelRead(obj);
        }

        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelReadComplete();
            readIfIsAutoRead();
        }

        private void readIfIsAutoRead() {
            if (DefaultChannelPipeline.this.channel.config().isAutoRead()) {
                DefaultChannelPipeline.this.channel.read();
            }
        }

        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            channelHandlerContext.fireUserEventTriggered(obj);
        }

        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelWritabilityChanged();
        }
    }

    /* renamed from: io.netty.channel.DefaultChannelPipeline$PendingHandlerAddedTask */
    private final class PendingHandlerAddedTask extends PendingHandlerCallback {
        PendingHandlerAddedTask(AbstractChannelHandlerContext abstractChannelHandlerContext) {
            super(abstractChannelHandlerContext);
        }

        public void run() {
            DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
        }

        /* access modifiers changed from: 0000 */
        public void execute() {
            EventExecutor executor = this.ctx.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), e);
                }
                DefaultChannelPipeline.remove0(this.ctx);
                this.ctx.setRemoved();
            }
        }
    }

    /* renamed from: io.netty.channel.DefaultChannelPipeline$PendingHandlerCallback */
    private static abstract class PendingHandlerCallback implements Runnable {
        final AbstractChannelHandlerContext ctx;
        PendingHandlerCallback next;

        /* access modifiers changed from: 0000 */
        public abstract void execute();

        PendingHandlerCallback(AbstractChannelHandlerContext abstractChannelHandlerContext) {
            this.ctx = abstractChannelHandlerContext;
        }
    }

    /* renamed from: io.netty.channel.DefaultChannelPipeline$PendingHandlerRemovedTask */
    private final class PendingHandlerRemovedTask extends PendingHandlerCallback {
        PendingHandlerRemovedTask(AbstractChannelHandlerContext abstractChannelHandlerContext) {
            super(abstractChannelHandlerContext);
        }

        public void run() {
            DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
        }

        /* access modifiers changed from: 0000 */
        public void execute() {
            EventExecutor executor = this.ctx.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), e);
                }
                this.ctx.setRemoved();
            }
        }
    }

    /* renamed from: io.netty.channel.DefaultChannelPipeline$TailContext */
    final class TailContext extends AbstractChannelHandlerContext implements ChannelInboundHandler {
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public ChannelHandler handler() {
            return this;
        }

        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        TailContext(DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, DefaultChannelPipeline.TAIL_NAME, true, false);
            setAddComplete();
        }

        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            ReferenceCountUtil.release(obj);
        }

        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            DefaultChannelPipeline.this.onUnhandledInboundException(th);
        }

        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            DefaultChannelPipeline.this.onUnhandledInboundMessage(obj);
        }
    }

    protected DefaultChannelPipeline(Channel channel2) {
        this.channel = (Channel) ObjectUtil.checkNotNull(channel2, "channel");
        this.succeededFuture = new SucceededChannelFuture(channel2, null);
        this.voidPromise = new VoidChannelPromise(channel2, true);
        this.tail = new TailContext(this);
        this.head = new HeadContext(this);
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head;
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext;
    }

    /* access modifiers changed from: 0000 */
    public final Handle estimatorHandle() {
        if (this.estimatorHandle == null) {
            this.estimatorHandle = this.channel.config().getMessageSizeEstimator().newHandle();
        }
        return this.estimatorHandle;
    }

    /* access modifiers changed from: 0000 */
    public final Object touch(Object obj, AbstractChannelHandlerContext abstractChannelHandlerContext) {
        return this.touch ? ReferenceCountUtil.touch(obj, abstractChannelHandlerContext) : obj;
    }

    private AbstractChannelHandlerContext newContext(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        return new DefaultChannelHandlerContext(this, childExecutor(eventExecutorGroup), str, channelHandler);
    }

    private EventExecutor childExecutor(EventExecutorGroup eventExecutorGroup) {
        if (eventExecutorGroup == null) {
            return null;
        }
        Boolean bool = (Boolean) this.channel.config().getOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
        if (bool != null && !bool.booleanValue()) {
            return eventExecutorGroup.next();
        }
        Map map = this.childExecutors;
        if (map == null) {
            map = new IdentityHashMap(4);
            this.childExecutors = map;
        }
        EventExecutor eventExecutor = (EventExecutor) map.get(eventExecutorGroup);
        if (eventExecutor == null) {
            eventExecutor = eventExecutorGroup.next();
            map.put(eventExecutorGroup, eventExecutor);
        }
        return eventExecutor;
    }

    public final Channel channel() {
        return this.channel;
    }

    public final ChannelPipeline addFirst(String str, ChannelHandler channelHandler) {
        return addFirst(null, str, channelHandler);
    }

    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        synchronized (this) {
            checkMultiplicity(channelHandler);
            final AbstractChannelHandlerContext newContext = newContext(eventExecutorGroup, filterName(str, channelHandler), channelHandler);
            addFirst0(newContext);
            if (!this.registered) {
                newContext.setAddPending();
                callHandlerCallbackLater(newContext, true);
                return this;
            }
            EventExecutor executor = newContext.executor();
            if (!executor.inEventLoop()) {
                newContext.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newContext);
                    }
                });
                return this;
            }
            callHandlerAdded0(newContext);
            return this;
        }
    }

    private void addFirst0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.head.next;
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = this.head;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext3;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext3.next = abstractChannelHandlerContext;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext;
    }

    public final ChannelPipeline addLast(String str, ChannelHandler channelHandler) {
        return addLast(null, str, channelHandler);
    }

    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        synchronized (this) {
            checkMultiplicity(channelHandler);
            final AbstractChannelHandlerContext newContext = newContext(eventExecutorGroup, filterName(str, channelHandler), channelHandler);
            addLast0(newContext);
            if (!this.registered) {
                newContext.setAddPending();
                callHandlerCallbackLater(newContext, true);
                return this;
            }
            EventExecutor executor = newContext.executor();
            if (!executor.inEventLoop()) {
                newContext.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newContext);
                    }
                });
                return this;
            }
            callHandlerAdded0(newContext);
            return this;
        }
    }

    private void addLast0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail.prev;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = this.tail;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext3;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext;
        abstractChannelHandlerContext3.prev = abstractChannelHandlerContext;
    }

    public final ChannelPipeline addBefore(String str, String str2, ChannelHandler channelHandler) {
        return addBefore(null, str, str2, channelHandler);
    }

    public final ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler) {
        synchronized (this) {
            checkMultiplicity(channelHandler);
            String filterName = filterName(str2, channelHandler);
            AbstractChannelHandlerContext contextOrDie = getContextOrDie(str);
            final AbstractChannelHandlerContext newContext = newContext(eventExecutorGroup, filterName, channelHandler);
            addBefore0(contextOrDie, newContext);
            if (!this.registered) {
                newContext.setAddPending();
                callHandlerCallbackLater(newContext, true);
                return this;
            }
            EventExecutor executor = newContext.executor();
            if (!executor.inEventLoop()) {
                newContext.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newContext);
                    }
                });
                return this;
            }
            callHandlerAdded0(newContext);
            return this;
        }
    }

    private static void addBefore0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext.prev;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext;
        abstractChannelHandlerContext.prev.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
    }

    private String filterName(String str, ChannelHandler channelHandler) {
        if (str == null) {
            return generateName(channelHandler);
        }
        checkDuplicateName(str);
        return str;
    }

    public final ChannelPipeline addAfter(String str, String str2, ChannelHandler channelHandler) {
        return addAfter(null, str, str2, channelHandler);
    }

    public final ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler) {
        synchronized (this) {
            checkMultiplicity(channelHandler);
            String filterName = filterName(str2, channelHandler);
            AbstractChannelHandlerContext contextOrDie = getContextOrDie(str);
            final AbstractChannelHandlerContext newContext = newContext(eventExecutorGroup, filterName, channelHandler);
            addAfter0(contextOrDie, newContext);
            if (!this.registered) {
                newContext.setAddPending();
                callHandlerCallbackLater(newContext, true);
                return this;
            }
            EventExecutor executor = newContext.executor();
            if (!executor.inEventLoop()) {
                newContext.setAddPending();
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newContext);
                    }
                });
                return this;
            }
            callHandlerAdded0(newContext);
            return this;
        }
    }

    private static void addAfter0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext.next.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
    }

    public final ChannelPipeline addFirst(ChannelHandler... channelHandlerArr) {
        return addFirst((EventExecutorGroup) null, channelHandlerArr);
    }

    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr) {
        if (channelHandlerArr != null) {
            if (!(channelHandlerArr.length == 0 || channelHandlerArr[0] == null)) {
                int i = 1;
                while (i < channelHandlerArr.length && channelHandlerArr[i] != null) {
                    i++;
                }
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    addFirst(eventExecutorGroup, null, channelHandlerArr[i2]);
                }
            }
            return this;
        }
        throw new NullPointerException("handlers");
    }

    public final ChannelPipeline addLast(ChannelHandler... channelHandlerArr) {
        return addLast((EventExecutorGroup) null, channelHandlerArr);
    }

    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr) {
        if (channelHandlerArr != null) {
            for (ChannelHandler channelHandler : channelHandlerArr) {
                if (channelHandler == null) {
                    break;
                }
                addLast(eventExecutorGroup, null, channelHandler);
            }
            return this;
        }
        throw new NullPointerException("handlers");
    }

    private String generateName(ChannelHandler channelHandler) {
        Map map = (Map) nameCaches.get();
        Class cls = channelHandler.getClass();
        String str = (String) map.get(cls);
        if (str == null) {
            str = generateName0(cls);
            map.put(cls, str);
        }
        if (context0(str) != null) {
            int i = 1;
            String substring = str.substring(0, str.length() - 1);
            while (true) {
                StringBuilder sb = new StringBuilder();
                sb.append(substring);
                sb.append(i);
                str = sb.toString();
                if (context0(str) == null) {
                    break;
                }
                i++;
            }
        }
        return str;
    }

    private static String generateName0(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(cls));
        sb.append("#0");
        return sb.toString();
    }

    public final ChannelPipeline remove(ChannelHandler channelHandler) {
        remove(getContextOrDie(channelHandler));
        return this;
    }

    public final ChannelHandler remove(String str) {
        return remove(getContextOrDie(str)).handler();
    }

    public final <T extends ChannelHandler> T remove(Class<T> cls) {
        return remove(getContextOrDie(cls)).handler();
    }

    private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        synchronized (this) {
            remove0(abstractChannelHandlerContext);
            if (!this.registered) {
                callHandlerCallbackLater(abstractChannelHandlerContext, false);
                return abstractChannelHandlerContext;
            }
            EventExecutor executor = abstractChannelHandlerContext.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerRemoved0(abstractChannelHandlerContext);
                    }
                });
                return abstractChannelHandlerContext;
            }
            callHandlerRemoved0(abstractChannelHandlerContext);
            return abstractChannelHandlerContext;
        }
    }

    /* access modifiers changed from: private */
    public static void remove0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = abstractChannelHandlerContext.prev;
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext3;
        abstractChannelHandlerContext3.prev = abstractChannelHandlerContext2;
    }

    public final ChannelHandler removeFirst() {
        if (this.head.next != this.tail) {
            return remove(this.head.next).handler();
        }
        throw new NoSuchElementException();
    }

    public final ChannelHandler removeLast() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail;
        if (abstractChannelHandlerContext != abstractChannelHandlerContext2) {
            return remove(abstractChannelHandlerContext2.prev).handler();
        }
        throw new NoSuchElementException();
    }

    public final ChannelPipeline replace(ChannelHandler channelHandler, String str, ChannelHandler channelHandler2) {
        replace(getContextOrDie(channelHandler), str, channelHandler2);
        return this;
    }

    public final ChannelHandler replace(String str, String str2, ChannelHandler channelHandler) {
        return replace(getContextOrDie(str), str2, channelHandler);
    }

    public final <T extends ChannelHandler> T replace(Class<T> cls, String str, ChannelHandler channelHandler) {
        return replace(getContextOrDie(cls), str, channelHandler);
    }

    private ChannelHandler replace(final AbstractChannelHandlerContext abstractChannelHandlerContext, String str, ChannelHandler channelHandler) {
        synchronized (this) {
            checkMultiplicity(channelHandler);
            if (str == null) {
                str = generateName(channelHandler);
            } else if (!abstractChannelHandlerContext.name().equals(str)) {
                checkDuplicateName(str);
            }
            final AbstractChannelHandlerContext newContext = newContext(abstractChannelHandlerContext.executor, str, channelHandler);
            replace0(abstractChannelHandlerContext, newContext);
            if (!this.registered) {
                callHandlerCallbackLater(newContext, true);
                callHandlerCallbackLater(abstractChannelHandlerContext, false);
                ChannelHandler handler = abstractChannelHandlerContext.handler();
                return handler;
            }
            EventExecutor executor = abstractChannelHandlerContext.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.callHandlerAdded0(newContext);
                        DefaultChannelPipeline.this.callHandlerRemoved0(abstractChannelHandlerContext);
                    }
                });
                ChannelHandler handler2 = abstractChannelHandlerContext.handler();
                return handler2;
            }
            callHandlerAdded0(newContext);
            callHandlerRemoved0(abstractChannelHandlerContext);
            return abstractChannelHandlerContext.handler();
        }
    }

    private static void replace0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = abstractChannelHandlerContext.prev;
        AbstractChannelHandlerContext abstractChannelHandlerContext4 = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext3;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext4;
        abstractChannelHandlerContext3.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext4.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
    }

    private static void checkMultiplicity(ChannelHandler channelHandler) {
        if (channelHandler instanceof ChannelHandlerAdapter) {
            ChannelHandlerAdapter channelHandlerAdapter = (ChannelHandlerAdapter) channelHandler;
            if (channelHandlerAdapter.isSharable() || !channelHandlerAdapter.added) {
                channelHandlerAdapter.added = true;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(channelHandlerAdapter.getClass().getName());
            sb.append(" is not a @Sharable handler, so can't be added or removed multiple times.");
            throw new ChannelPipelineException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public void callHandlerAdded0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        boolean z;
        try {
            abstractChannelHandlerContext.handler().handlerAdded(abstractChannelHandlerContext);
            abstractChannelHandlerContext.setAddComplete();
            return;
        } catch (Throwable th) {
            abstractChannelHandlerContext.setRemoved();
            throw th;
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(abstractChannelHandlerContext.handler().getClass().getName());
            sb.append(".handlerAdded() has thrown an exception; removed.");
            fireExceptionCaught((Throwable) new ChannelPipelineException(sb.toString(), th));
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(abstractChannelHandlerContext.handler().getClass().getName());
        sb2.append(".handlerAdded() has thrown an exception; also failed to remove.");
        fireExceptionCaught((Throwable) new ChannelPipelineException(sb2.toString(), th));
    }

    /* access modifiers changed from: private */
    public void callHandlerRemoved0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        try {
            abstractChannelHandlerContext.handler().handlerRemoved(abstractChannelHandlerContext);
            abstractChannelHandlerContext.setRemoved();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append(abstractChannelHandlerContext.handler().getClass().getName());
            sb.append(".handlerRemoved() has thrown an exception.");
            fireExceptionCaught((Throwable) new ChannelPipelineException(sb.toString(), th));
        }
    }

    /* access modifiers changed from: 0000 */
    public final void invokeHandlerAddedIfNeeded() {
        if (this.firstRegistration) {
            this.firstRegistration = false;
            callHandlerAddedForAllHandlers();
        }
    }

    public final ChannelHandler first() {
        ChannelHandlerContext firstContext = firstContext();
        if (firstContext == null) {
            return null;
        }
        return firstContext.handler();
    }

    public final ChannelHandlerContext firstContext() {
        if (this.head.next == this.tail) {
            return null;
        }
        return this.head.next;
    }

    public final ChannelHandler last() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.tail.prev;
        if (abstractChannelHandlerContext == this.head) {
            return null;
        }
        return abstractChannelHandlerContext.handler();
    }

    public final ChannelHandlerContext lastContext() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.tail.prev;
        if (abstractChannelHandlerContext == this.head) {
            return null;
        }
        return abstractChannelHandlerContext;
    }

    public final ChannelHandler get(String str) {
        ChannelHandlerContext context = context(str);
        if (context == null) {
            return null;
        }
        return context.handler();
    }

    public final <T extends ChannelHandler> T get(Class<T> cls) {
        ChannelHandlerContext context = context(cls);
        if (context == null) {
            return null;
        }
        return context.handler();
    }

    public final ChannelHandlerContext context(String str) {
        if (str != null) {
            return context0(str);
        }
        throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
    }

    public final ChannelHandlerContext context(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
                if (abstractChannelHandlerContext.handler() == channelHandler) {
                    return abstractChannelHandlerContext;
                }
            }
            return null;
        }
        throw new NullPointerException("handler");
    }

    public final ChannelHandlerContext context(Class<? extends ChannelHandler> cls) {
        if (cls != null) {
            for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
                if (cls.isAssignableFrom(abstractChannelHandlerContext.handler().getClass())) {
                    return abstractChannelHandlerContext;
                }
            }
            return null;
        }
        throw new NullPointerException("handlerType");
    }

    public final List<String> names() {
        ArrayList arrayList = new ArrayList();
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            arrayList.add(abstractChannelHandlerContext.name());
        }
        return arrayList;
    }

    public final Map<String, ChannelHandler> toMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != this.tail; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            linkedHashMap.put(abstractChannelHandlerContext.name(), abstractChannelHandlerContext.handler());
        }
        return linkedHashMap;
    }

    public final Iterator<Entry<String, ChannelHandler>> iterator() {
        return toMap().entrySet().iterator();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('{');
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != this.tail) {
            sb.append('(');
            sb.append(abstractChannelHandlerContext.name());
            sb.append(" = ");
            sb.append(abstractChannelHandlerContext.handler().getClass().getName());
            sb.append(')');
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
            if (abstractChannelHandlerContext == this.tail) {
                break;
            }
            sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }

    public final ChannelPipeline fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelUnregistered() {
        AbstractChannelHandlerContext.invokeChannelUnregistered(this.head);
        return this;
    }

    /* access modifiers changed from: private */
    public synchronized void destroy() {
        destroyUp(this.head.next, false);
    }

    /* access modifiers changed from: private */
    public void destroyUp(final AbstractChannelHandlerContext abstractChannelHandlerContext, boolean z) {
        Thread currentThread = Thread.currentThread();
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail;
        while (abstractChannelHandlerContext != abstractChannelHandlerContext2) {
            EventExecutor executor = abstractChannelHandlerContext.executor();
            if (z || executor.inEventLoop(currentThread)) {
                abstractChannelHandlerContext = abstractChannelHandlerContext.next;
                z = false;
            } else {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.destroyUp(abstractChannelHandlerContext, true);
                    }
                });
                return;
            }
        }
        destroyDown(currentThread, abstractChannelHandlerContext2.prev, z);
    }

    /* access modifiers changed from: private */
    public void destroyDown(Thread thread, final AbstractChannelHandlerContext abstractChannelHandlerContext, boolean z) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.head;
        while (abstractChannelHandlerContext != abstractChannelHandlerContext2) {
            EventExecutor executor = abstractChannelHandlerContext.executor();
            if (z || executor.inEventLoop(thread)) {
                synchronized (this) {
                    remove0(abstractChannelHandlerContext);
                }
                callHandlerRemoved0(abstractChannelHandlerContext);
                abstractChannelHandlerContext = abstractChannelHandlerContext.prev;
                z = false;
            } else {
                executor.execute(new Runnable() {
                    public void run() {
                        DefaultChannelPipeline.this.destroyDown(Thread.currentThread(), abstractChannelHandlerContext, true);
                    }
                });
                return;
            }
        }
    }

    public final ChannelPipeline fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelInactive() {
        AbstractChannelHandlerContext.invokeChannelInactive(this.head);
        return this;
    }

    public final ChannelPipeline fireExceptionCaught(Throwable th) {
        AbstractChannelHandlerContext.invokeExceptionCaught(this.head, th);
        return this;
    }

    public final ChannelPipeline fireUserEventTriggered(Object obj) {
        AbstractChannelHandlerContext.invokeUserEventTriggered(this.head, obj);
        return this;
    }

    public final ChannelPipeline fireChannelRead(Object obj) {
        AbstractChannelHandlerContext.invokeChannelRead(this.head, obj);
        return this;
    }

    public final ChannelPipeline fireChannelReadComplete() {
        AbstractChannelHandlerContext.invokeChannelReadComplete(this.head);
        return this;
    }

    public final ChannelPipeline fireChannelWritabilityChanged() {
        AbstractChannelHandlerContext.invokeChannelWritabilityChanged(this.head);
        return this;
    }

    public final ChannelFuture bind(SocketAddress socketAddress) {
        return this.tail.bind(socketAddress);
    }

    public final ChannelFuture connect(SocketAddress socketAddress) {
        return this.tail.connect(socketAddress);
    }

    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.tail.connect(socketAddress, socketAddress2);
    }

    public final ChannelFuture disconnect() {
        return this.tail.disconnect();
    }

    public final ChannelFuture close() {
        return this.tail.close();
    }

    public final ChannelFuture deregister() {
        return this.tail.deregister();
    }

    public final ChannelPipeline flush() {
        this.tail.flush();
        return this;
    }

    public final ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.tail.bind(socketAddress, channelPromise);
    }

    public final ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, channelPromise);
    }

    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, socketAddress2, channelPromise);
    }

    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.tail.disconnect(channelPromise);
    }

    public final ChannelFuture close(ChannelPromise channelPromise) {
        return this.tail.close(channelPromise);
    }

    public final ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.tail.deregister(channelPromise);
    }

    public final ChannelPipeline read() {
        this.tail.read();
        return this;
    }

    public final ChannelFuture write(Object obj) {
        return this.tail.write(obj);
    }

    public final ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        return this.tail.write(obj, channelPromise);
    }

    public final ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        return this.tail.writeAndFlush(obj, channelPromise);
    }

    public final ChannelFuture writeAndFlush(Object obj) {
        return this.tail.writeAndFlush(obj);
    }

    public final ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.channel);
    }

    public final ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this.channel);
    }

    public final ChannelFuture newSucceededFuture() {
        return this.succeededFuture;
    }

    public final ChannelFuture newFailedFuture(Throwable th) {
        return new FailedChannelFuture(this.channel, null, th);
    }

    public final ChannelPromise voidPromise() {
        return this.voidPromise;
    }

    private void checkDuplicateName(String str) {
        if (context0(str) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Duplicate handler name: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private AbstractChannelHandlerContext context0(String str) {
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != this.tail; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            if (abstractChannelHandlerContext.name().equals(str)) {
                return abstractChannelHandlerContext;
            }
        }
        return null;
    }

    private AbstractChannelHandlerContext getContextOrDie(String str) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext) context(str);
        if (abstractChannelHandlerContext != null) {
            return abstractChannelHandlerContext;
        }
        throw new NoSuchElementException(str);
    }

    private AbstractChannelHandlerContext getContextOrDie(ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext) context(channelHandler);
        if (abstractChannelHandlerContext != null) {
            return abstractChannelHandlerContext;
        }
        throw new NoSuchElementException(channelHandler.getClass().getName());
    }

    private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> cls) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext) context(cls);
        if (abstractChannelHandlerContext != null) {
            return abstractChannelHandlerContext;
        }
        throw new NoSuchElementException(cls.getName());
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 114 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void callHandlerAddedForAllHandlers() {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = 1
            r2.registered = r0     // Catch:{ all -> 0x0015 }
            io.netty.channel.DefaultChannelPipeline$PendingHandlerCallback r0 = r2.pendingHandlerCallbackHead     // Catch:{ all -> 0x0015 }
            r1 = 0
            r2.pendingHandlerCallbackHead = r1     // Catch:{ all -> 0x0015 }
            monitor-exit(r2)     // Catch:{ all -> 0x0015 }
        L_0x000a:
            if (r0 == 0) goto L_0x0012
            r0.execute()
            io.netty.channel.DefaultChannelPipeline$PendingHandlerCallback r0 = r0.next
            goto L_0x000a
        L_0x0012:
            return
        L_0x0013:
            monitor-exit(r2)     // Catch:{ all -> 0x0015 }
            throw r0
        L_0x0015:
            r0 = move-exception
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.DefaultChannelPipeline.callHandlerAddedForAllHandlers():void");
    }

    private void callHandlerCallbackLater(AbstractChannelHandlerContext abstractChannelHandlerContext, boolean z) {
        PendingHandlerCallback pendingHandlerAddedTask = z ? new PendingHandlerAddedTask(abstractChannelHandlerContext) : new PendingHandlerRemovedTask(abstractChannelHandlerContext);
        PendingHandlerCallback pendingHandlerCallback = this.pendingHandlerCallbackHead;
        if (pendingHandlerCallback == null) {
            this.pendingHandlerCallbackHead = pendingHandlerAddedTask;
            return;
        }
        while (pendingHandlerCallback.next != null) {
            pendingHandlerCallback = pendingHandlerCallback.next;
        }
        pendingHandlerCallback.next = pendingHandlerAddedTask;
    }

    /* access modifiers changed from: protected */
    public void onUnhandledInboundException(Throwable th) {
        try {
            logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", th);
        } finally {
            ReferenceCountUtil.release(th);
        }
    }

    /* access modifiers changed from: protected */
    public void onUnhandledInboundMessage(Object obj) {
        try {
            logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", obj);
        } finally {
            ReferenceCountUtil.release(obj);
        }
    }
}
