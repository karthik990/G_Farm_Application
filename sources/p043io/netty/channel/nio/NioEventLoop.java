package p043io.netty.channel.nio;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.EventLoopException;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.SelectStrategy;
import p043io.netty.channel.SingleThreadEventLoop;
import p043io.netty.channel.nio.AbstractNioChannel.NioUnsafe;
import p043io.netty.util.IntSupplier;
import p043io.netty.util.concurrent.RejectedExecutionHandler;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ReflectionUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.nio.NioEventLoop */
public final class NioEventLoop extends SingleThreadEventLoop {
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
    private int cancelledKeys;
    private volatile int ioRatio = 50;
    private boolean needsToSelectAgain;
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        public Integer call() throws Exception {
            return Integer.valueOf(NioEventLoop.super.pendingTasks());
        }
    };
    private final SelectorProvider provider;
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        public int get() throws Exception {
            return NioEventLoop.this.selectNow();
        }
    };
    private final SelectStrategy selectStrategy;
    private SelectedSelectionKeySet selectedKeys;
    private Selector selector;
    private Selector unwrappedSelector;
    private final AtomicBoolean wakenUp = new AtomicBoolean();

    /* renamed from: io.netty.channel.nio.NioEventLoop$SelectorTuple */
    private static final class SelectorTuple {
        final Selector selector;
        final Selector unwrappedSelector;

        SelectorTuple(Selector selector2) {
            this.unwrappedSelector = selector2;
            this.selector = selector2;
        }

        SelectorTuple(Selector selector2, Selector selector3) {
            this.unwrappedSelector = selector2;
            this.selector = selector3;
        }
    }

    static {
        int i = 0;
        if (SystemPropertyUtil.get("sun.nio.ch.bugLevel") == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        System.setProperty("sun.nio.ch.bugLevel", "");
                        return null;
                    }
                });
            } catch (SecurityException e) {
                logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", (Throwable) e);
            }
        }
        int i2 = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (i2 >= 3) {
            i = i2;
        }
        SELECTOR_AUTO_REBUILD_THRESHOLD = i;
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noKeySetOptimization: {}", (Object) Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
            logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", (Object) Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
        }
    }

    NioEventLoop(NioEventLoopGroup nioEventLoopGroup, Executor executor, SelectorProvider selectorProvider, SelectStrategy selectStrategy2, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventLoopGroup) nioEventLoopGroup, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        } else if (selectStrategy2 != null) {
            this.provider = selectorProvider;
            SelectorTuple openSelector = openSelector();
            this.selector = openSelector.selector;
            this.unwrappedSelector = openSelector.unwrappedSelector;
            this.selectStrategy = selectStrategy2;
        } else {
            throw new NullPointerException("selectStrategy");
        }
    }

    private SelectorTuple openSelector() {
        try {
            final AbstractSelector openSelector = this.provider.openSelector();
            if (DISABLE_KEYSET_OPTIMIZATION) {
                return new SelectorTuple(openSelector);
            }
            final SelectedSelectionKeySet selectedSelectionKeySet = new SelectedSelectionKeySet();
            Object doPrivileged = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    try {
                        return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                    } catch (Throwable th) {
                        return th;
                    }
                }
            });
            String str = "failed to instrument a special java.util.Set into: {}";
            if (doPrivileged instanceof Class) {
                final Class cls = (Class) doPrivileged;
                if (cls.isAssignableFrom(openSelector.getClass())) {
                    Object doPrivileged2 = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                        public Object run() {
                            try {
                                Field declaredField = cls.getDeclaredField("selectedKeys");
                                Field declaredField2 = cls.getDeclaredField("publicSelectedKeys");
                                Throwable trySetAccessible = ReflectionUtil.trySetAccessible(declaredField);
                                if (trySetAccessible != null) {
                                    return trySetAccessible;
                                }
                                Throwable trySetAccessible2 = ReflectionUtil.trySetAccessible(declaredField2);
                                if (trySetAccessible2 != null) {
                                    return trySetAccessible2;
                                }
                                declaredField.set(openSelector, selectedSelectionKeySet);
                                declaredField2.set(openSelector, selectedSelectionKeySet);
                                return null;
                            } catch (NoSuchFieldException e) {
                                return e;
                            } catch (IllegalAccessException e2) {
                                return e2;
                            }
                        }
                    });
                    if (doPrivileged2 instanceof Exception) {
                        this.selectedKeys = null;
                        logger.trace(str, openSelector, (Exception) doPrivileged2);
                        return new SelectorTuple(openSelector);
                    }
                    this.selectedKeys = selectedSelectionKeySet;
                    logger.trace("instrumented a special java.util.Set into: {}", (Object) openSelector);
                    return new SelectorTuple(openSelector, new SelectedSelectionKeySetSelector(openSelector, selectedSelectionKeySet));
                }
            }
            if (doPrivileged instanceof Throwable) {
                logger.trace(str, openSelector, (Throwable) doPrivileged);
            }
            return new SelectorTuple(openSelector);
        } catch (IOException e) {
            throw new ChannelException("failed to open a new selector", e);
        }
    }

    public SelectorProvider selectorProvider() {
        return this.provider;
    }

    /* access modifiers changed from: protected */
    public Queue<Runnable> newTaskQueue(int i) {
        return PlatformDependent.newMpscQueue(i);
    }

    public int pendingTasks() {
        if (inEventLoop()) {
            return super.pendingTasks();
        }
        return ((Integer) submit(this.pendingTasksCallable).syncUninterruptibly().getNow()).intValue();
    }

    public void register(SelectableChannel selectableChannel, int i, NioTask<?> nioTask) {
        if (selectableChannel == null) {
            throw new NullPointerException("ch");
        } else if (i == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        } else if (((selectableChannel.validOps() ^ -1) & i) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid interestOps: ");
            sb.append(i);
            sb.append("(validOps: ");
            sb.append(selectableChannel.validOps());
            sb.append(')');
            throw new IllegalArgumentException(sb.toString());
        } else if (nioTask == null) {
            throw new NullPointerException("task");
        } else if (!isShutdown()) {
            try {
                selectableChannel.register(this.selector, i, nioTask);
            } catch (Exception e) {
                throw new EventLoopException("failed to register a channel", e);
            }
        } else {
            throw new IllegalStateException("event loop shut down");
        }
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int i) {
        if (i <= 0 || i > 100) {
            StringBuilder sb = new StringBuilder();
            sb.append("ioRatio: ");
            sb.append(i);
            sb.append(" (expected: 0 < ioRatio <= 100)");
            throw new IllegalArgumentException(sb.toString());
        }
        this.ioRatio = i;
    }

    public void rebuildSelector() {
        if (!inEventLoop()) {
            execute(new Runnable() {
                public void run() {
                    NioEventLoop.this.rebuildSelector0();
                }
            });
        } else {
            rebuildSelector0();
        }
    }

    /* access modifiers changed from: private */
    public void rebuildSelector0() {
        Selector selector2 = this.selector;
        if (selector2 != null) {
            try {
                SelectorTuple openSelector = openSelector();
                int i = 0;
                for (SelectionKey selectionKey : selector2.keys()) {
                    Object attachment = selectionKey.attachment();
                    try {
                        if (selectionKey.isValid()) {
                            if (selectionKey.channel().keyFor(openSelector.unwrappedSelector) == null) {
                                int interestOps = selectionKey.interestOps();
                                selectionKey.cancel();
                                SelectionKey register = selectionKey.channel().register(openSelector.unwrappedSelector, interestOps, attachment);
                                if (attachment instanceof AbstractNioChannel) {
                                    ((AbstractNioChannel) attachment).selectionKey = register;
                                }
                                i++;
                            }
                        }
                    } catch (Exception e) {
                        logger.warn("Failed to re-register a Channel to the new Selector.", (Throwable) e);
                        if (attachment instanceof AbstractNioChannel) {
                            AbstractNioChannel abstractNioChannel = (AbstractNioChannel) attachment;
                            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
                        } else {
                            invokeChannelUnregistered((NioTask) attachment, selectionKey, e);
                        }
                    }
                }
                this.selector = openSelector.selector;
                this.unwrappedSelector = openSelector.unwrappedSelector;
                try {
                    selector2.close();
                } catch (Throwable th) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Failed to close the old Selector.", th);
                    }
                }
                InternalLogger internalLogger = logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Migrated ");
                sb.append(i);
                sb.append(" channel(s) to the new Selector.");
                internalLogger.info(sb.toString());
            } catch (Exception e2) {
                logger.warn("Failed to create a new Selector.", (Throwable) e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0070 A[Catch:{ all -> 0x007a }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0000 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r7 = this;
        L_0x0000:
            io.netty.channel.SelectStrategy r0 = r7.selectStrategy     // Catch:{ all -> 0x0066 }
            io.netty.util.IntSupplier r1 = r7.selectNowSupplier     // Catch:{ all -> 0x0066 }
            boolean r2 = r7.hasTasks()     // Catch:{ all -> 0x0066 }
            int r0 = r0.calculateStrategy(r1, r2)     // Catch:{ all -> 0x0066 }
            r1 = -2
            if (r0 == r1) goto L_0x0000
            r1 = -1
            r2 = 0
            if (r0 == r1) goto L_0x0014
            goto L_0x002a
        L_0x0014:
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.wakenUp     // Catch:{ all -> 0x0066 }
            boolean r0 = r0.getAndSet(r2)     // Catch:{ all -> 0x0066 }
            r7.select(r0)     // Catch:{ all -> 0x0066 }
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.wakenUp     // Catch:{ all -> 0x0066 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0066 }
            if (r0 == 0) goto L_0x002a
            java.nio.channels.Selector r0 = r7.selector     // Catch:{ all -> 0x0066 }
            r0.wakeup()     // Catch:{ all -> 0x0066 }
        L_0x002a:
            r7.cancelledKeys = r2     // Catch:{ all -> 0x0066 }
            r7.needsToSelectAgain = r2     // Catch:{ all -> 0x0066 }
            int r0 = r7.ioRatio     // Catch:{ all -> 0x0066 }
            r1 = 100
            if (r0 != r1) goto L_0x0040
            r7.processSelectedKeys()     // Catch:{ all -> 0x003b }
            r7.runAllTasks()     // Catch:{ all -> 0x0066 }
            goto L_0x006a
        L_0x003b:
            r0 = move-exception
            r7.runAllTasks()     // Catch:{ all -> 0x0066 }
            throw r0     // Catch:{ all -> 0x0066 }
        L_0x0040:
            long r2 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0066 }
            r7.processSelectedKeys()     // Catch:{ all -> 0x0056 }
            long r4 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0066 }
            long r4 = r4 - r2
            int r1 = r1 - r0
            long r1 = (long) r1     // Catch:{ all -> 0x0066 }
            long r4 = r4 * r1
            long r0 = (long) r0     // Catch:{ all -> 0x0066 }
            long r4 = r4 / r0
            r7.runAllTasks(r4)     // Catch:{ all -> 0x0066 }
            goto L_0x006a
        L_0x0056:
            r4 = move-exception
            long r5 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0066 }
            long r5 = r5 - r2
            int r1 = r1 - r0
            long r1 = (long) r1     // Catch:{ all -> 0x0066 }
            long r5 = r5 * r1
            long r0 = (long) r0     // Catch:{ all -> 0x0066 }
            long r5 = r5 / r0
            r7.runAllTasks(r5)     // Catch:{ all -> 0x0066 }
            throw r4     // Catch:{ all -> 0x0066 }
        L_0x0066:
            r0 = move-exception
            handleLoopException(r0)
        L_0x006a:
            boolean r0 = r7.isShuttingDown()     // Catch:{ all -> 0x007a }
            if (r0 == 0) goto L_0x0000
            r7.closeAll()     // Catch:{ all -> 0x007a }
            boolean r0 = r7.confirmShutdown()     // Catch:{ all -> 0x007a }
            if (r0 == 0) goto L_0x0000
            return
        L_0x007a:
            r0 = move-exception
            handleLoopException(r0)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.nio.NioEventLoop.run():void");
    }

    private static void handleLoopException(Throwable th) {
        logger.warn("Unexpected exception in the selector loop.", th);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException unused) {
        }
    }

    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            processSelectedKeysOptimized();
        } else {
            processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }

    /* access modifiers changed from: protected */
    public void cleanup() {
        try {
            this.selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a selector.", (Throwable) e);
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancel(SelectionKey selectionKey) {
        selectionKey.cancel();
        this.cancelledKeys++;
        if (this.cancelledKeys >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }

    /* access modifiers changed from: protected */
    public Runnable pollTask() {
        Runnable pollTask = super.pollTask();
        if (this.needsToSelectAgain) {
            selectAgain();
        }
        return pollTask;
    }

    private void processSelectedKeysPlain(Set<SelectionKey> set) {
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (true) {
                SelectionKey selectionKey = (SelectionKey) it.next();
                Object attachment = selectionKey.attachment();
                it.remove();
                if (attachment instanceof AbstractNioChannel) {
                    processSelectedKey(selectionKey, (AbstractNioChannel) attachment);
                } else {
                    processSelectedKey(selectionKey, (NioTask) attachment);
                }
                if (!it.hasNext()) {
                    break;
                } else if (this.needsToSelectAgain) {
                    selectAgain();
                    Set selectedKeys2 = this.selector.selectedKeys();
                    if (selectedKeys2.isEmpty()) {
                        break;
                    }
                    it = selectedKeys2.iterator();
                }
            }
        }
    }

    private void processSelectedKeysOptimized() {
        int i = 0;
        while (i < this.selectedKeys.size) {
            SelectionKey selectionKey = this.selectedKeys.keys[i];
            this.selectedKeys.keys[i] = null;
            Object attachment = selectionKey.attachment();
            if (attachment instanceof AbstractNioChannel) {
                processSelectedKey(selectionKey, (AbstractNioChannel) attachment);
            } else {
                processSelectedKey(selectionKey, (NioTask) attachment);
            }
            if (this.needsToSelectAgain) {
                this.selectedKeys.reset(i + 1);
                selectAgain();
                i = -1;
            }
            i++;
        }
    }

    private void processSelectedKey(SelectionKey selectionKey, AbstractNioChannel abstractNioChannel) {
        NioUnsafe unsafe = abstractNioChannel.unsafe();
        if (!selectionKey.isValid()) {
            try {
                NioEventLoop eventLoop = abstractNioChannel.eventLoop();
                if (eventLoop == this && eventLoop != null) {
                    unsafe.close(unsafe.voidPromise());
                }
            } catch (Throwable unused) {
            }
            return;
        }
        try {
            int readyOps = selectionKey.readyOps();
            if ((readyOps & 8) != 0) {
                selectionKey.interestOps(selectionKey.interestOps() & -9);
                unsafe.finishConnect();
            }
            if ((readyOps & 4) != 0) {
                abstractNioChannel.unsafe().forceFlush();
            }
            if ((readyOps & 17) != 0 || readyOps == 0) {
                unsafe.read();
            }
        } catch (CancelledKeyException unused2) {
            unsafe.close(unsafe.voidPromise());
        }
    }

    private static void processSelectedKey(SelectionKey selectionKey, NioTask<SelectableChannel> nioTask) {
        try {
            nioTask.channelReady(selectionKey.channel(), selectionKey);
            if (!selectionKey.isValid()) {
                invokeChannelUnregistered(nioTask, selectionKey, null);
            }
        } catch (Exception e) {
            selectionKey.cancel();
            invokeChannelUnregistered(nioTask, selectionKey, e);
        } catch (Throwable th) {
            selectionKey.cancel();
            invokeChannelUnregistered(nioTask, selectionKey, null);
            throw th;
        }
    }

    private void closeAll() {
        selectAgain();
        Set<SelectionKey> keys = this.selector.keys();
        ArrayList<AbstractNioChannel> arrayList = new ArrayList<>(keys.size());
        for (SelectionKey selectionKey : keys) {
            Object attachment = selectionKey.attachment();
            if (attachment instanceof AbstractNioChannel) {
                arrayList.add((AbstractNioChannel) attachment);
            } else {
                selectionKey.cancel();
                invokeChannelUnregistered((NioTask) attachment, selectionKey, null);
            }
        }
        for (AbstractNioChannel abstractNioChannel : arrayList) {
            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> nioTask, SelectionKey selectionKey, Throwable th) {
        try {
            nioTask.channelUnregistered(selectionKey.channel(), th);
        } catch (Exception e) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void wakeup(boolean z) {
        if (!z && this.wakenUp.compareAndSet(false, true)) {
            this.selector.wakeup();
        }
    }

    /* access modifiers changed from: 0000 */
    public Selector unwrappedSelector() {
        return this.unwrappedSelector;
    }

    /* access modifiers changed from: 0000 */
    public int selectNow() throws IOException {
        try {
            return this.selector.selectNow();
        } finally {
            if (this.wakenUp.get()) {
                this.selector.wakeup();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0023, code lost:
        r6 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void select(boolean r14) throws java.io.IOException {
        /*
            r13 = this;
            java.nio.channels.Selector r0 = r13.selector
            long r1 = java.lang.System.nanoTime()     // Catch:{ CancelledKeyException -> 0x00b6 }
            long r3 = r13.delayNanos(r1)     // Catch:{ CancelledKeyException -> 0x00b6 }
            long r3 = r3 + r1
            r5 = 0
            r6 = 0
        L_0x000d:
            long r7 = r3 - r1
            r9 = 500000(0x7a120, double:2.47033E-318)
            long r7 = r7 + r9
            r9 = 1000000(0xf4240, double:4.940656E-318)
            long r7 = r7 / r9
            r9 = 0
            r11 = 1
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r12 > 0) goto L_0x0026
            if (r6 != 0) goto L_0x009e
            r0.selectNow()     // Catch:{ CancelledKeyException -> 0x00b6 }
        L_0x0023:
            r6 = 1
            goto L_0x009e
        L_0x0026:
            boolean r9 = r13.hasTasks()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 == 0) goto L_0x0038
            java.util.concurrent.atomic.AtomicBoolean r9 = r13.wakenUp     // Catch:{ CancelledKeyException -> 0x00b6 }
            boolean r9 = r9.compareAndSet(r5, r11)     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 == 0) goto L_0x0038
            r0.selectNow()     // Catch:{ CancelledKeyException -> 0x00b6 }
            goto L_0x0023
        L_0x0038:
            int r9 = r0.select(r7)     // Catch:{ CancelledKeyException -> 0x00b6 }
            int r6 = r6 + 1
            if (r9 != 0) goto L_0x009e
            if (r14 != 0) goto L_0x009e
            java.util.concurrent.atomic.AtomicBoolean r9 = r13.wakenUp     // Catch:{ CancelledKeyException -> 0x00b6 }
            boolean r9 = r9.get()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 != 0) goto L_0x009e
            boolean r9 = r13.hasTasks()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 != 0) goto L_0x009e
            boolean r9 = r13.hasScheduledTasks()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 == 0) goto L_0x0057
            goto L_0x009e
        L_0x0057:
            boolean r9 = java.lang.Thread.interrupted()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r9 == 0) goto L_0x006d
            io.netty.util.internal.logging.InternalLogger r14 = logger     // Catch:{ CancelledKeyException -> 0x00b6 }
            boolean r14 = r14.isDebugEnabled()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r14 == 0) goto L_0x0023
            io.netty.util.internal.logging.InternalLogger r14 = logger     // Catch:{ CancelledKeyException -> 0x00b6 }
            java.lang.String r1 = "Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop."
            r14.debug(r1)     // Catch:{ CancelledKeyException -> 0x00b6 }
            goto L_0x0023
        L_0x006d:
            long r9 = java.lang.System.nanoTime()     // Catch:{ CancelledKeyException -> 0x00b6 }
            java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ CancelledKeyException -> 0x00b6 }
            long r7 = r12.toNanos(r7)     // Catch:{ CancelledKeyException -> 0x00b6 }
            long r7 = r9 - r7
            int r12 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r12 < 0) goto L_0x007f
            r6 = 1
            goto L_0x009b
        L_0x007f:
            int r1 = SELECTOR_AUTO_REBUILD_THRESHOLD     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r1 <= 0) goto L_0x009b
            int r1 = SELECTOR_AUTO_REBUILD_THRESHOLD     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r6 < r1) goto L_0x009b
            io.netty.util.internal.logging.InternalLogger r14 = logger     // Catch:{ CancelledKeyException -> 0x00b6 }
            java.lang.String r1 = "Selector.select() returned prematurely {} times in a row; rebuilding Selector {}."
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ CancelledKeyException -> 0x00b6 }
            r14.warn(r1, r2, r0)     // Catch:{ CancelledKeyException -> 0x00b6 }
            r13.rebuildSelector()     // Catch:{ CancelledKeyException -> 0x00b6 }
            java.nio.channels.Selector r0 = r13.selector     // Catch:{ CancelledKeyException -> 0x00b6 }
            r0.selectNow()     // Catch:{ CancelledKeyException -> 0x00b6 }
            goto L_0x0023
        L_0x009b:
            r1 = r9
            goto L_0x000d
        L_0x009e:
            r14 = 3
            if (r6 <= r14) goto L_0x00db
            io.netty.util.internal.logging.InternalLogger r14 = logger     // Catch:{ CancelledKeyException -> 0x00b6 }
            boolean r14 = r14.isDebugEnabled()     // Catch:{ CancelledKeyException -> 0x00b6 }
            if (r14 == 0) goto L_0x00db
            io.netty.util.internal.logging.InternalLogger r14 = logger     // Catch:{ CancelledKeyException -> 0x00b6 }
            java.lang.String r1 = "Selector.select() returned prematurely {} times in a row for Selector {}."
            int r6 = r6 - r11
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ CancelledKeyException -> 0x00b6 }
            r14.debug(r1, r2, r0)     // Catch:{ CancelledKeyException -> 0x00b6 }
            goto L_0x00db
        L_0x00b6:
            r14 = move-exception
            io.netty.util.internal.logging.InternalLogger r1 = logger
            boolean r1 = r1.isDebugEnabled()
            if (r1 == 0) goto L_0x00db
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.Class<java.nio.channels.CancelledKeyException> r3 = java.nio.channels.CancelledKeyException.class
            java.lang.String r3 = r3.getSimpleName()
            r2.append(r3)
            java.lang.String r3 = " raised by a Selector {} - JDK bug?"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.debug(r2, r0, r14)
        L_0x00db:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.nio.NioEventLoop.select(boolean):void");
    }

    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        } catch (Throwable th) {
            logger.warn("Failed to update SelectionKeys.", th);
        }
    }
}
