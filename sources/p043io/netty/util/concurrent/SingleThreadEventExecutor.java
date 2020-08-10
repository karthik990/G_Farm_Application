package p043io.netty.util.concurrent;

import com.braintreepayments.api.models.PostalAddressParser;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.SingleThreadEventExecutor */
public abstract class SingleThreadEventExecutor extends AbstractScheduledEventExecutor implements OrderedEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE));
    private static final Runnable NOOP_TASK = new Runnable() {
        public void run() {
        }
    };
    private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> PROPERTIES_UPDATER;
    private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_STARTED = 2;
    private static final int ST_TERMINATED = 5;
    private static final Runnable WAKEUP_TASK = new Runnable() {
        public void run() {
        }
    };
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    private final boolean addTaskWakesUp;
    private final Executor executor;
    private volatile long gracefulShutdownQuietPeriod;
    /* access modifiers changed from: private */
    public long gracefulShutdownStartTime;
    private volatile long gracefulShutdownTimeout;
    /* access modifiers changed from: private */
    public volatile boolean interrupted;
    private long lastExecutionTime;
    private final int maxPendingTasks;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    /* access modifiers changed from: private */
    public final Set<Runnable> shutdownHooks;
    /* access modifiers changed from: private */
    public volatile int state;
    /* access modifiers changed from: private */
    public final Queue<Runnable> taskQueue;
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture;
    /* access modifiers changed from: private */
    public volatile Thread thread;
    /* access modifiers changed from: private */
    public final Semaphore threadLock;
    private volatile ThreadProperties threadProperties;

    /* renamed from: io.netty.util.concurrent.SingleThreadEventExecutor$DefaultThreadProperties */
    private static final class DefaultThreadProperties implements ThreadProperties {

        /* renamed from: t */
        private final Thread f3747t;

        DefaultThreadProperties(Thread thread) {
            this.f3747t = thread;
        }

        public State state() {
            return this.f3747t.getState();
        }

        public int priority() {
            return this.f3747t.getPriority();
        }

        public boolean isInterrupted() {
            return this.f3747t.isInterrupted();
        }

        public boolean isDaemon() {
            return this.f3747t.isDaemon();
        }

        public String name() {
            return this.f3747t.getName();
        }

        /* renamed from: id */
        public long mo68685id() {
            return this.f3747t.getId();
        }

        public StackTraceElement[] stackTrace() {
            return this.f3747t.getStackTrace();
        }

        public boolean isAlive() {
            return this.f3747t.isAlive();
        }
    }

    /* access modifiers changed from: protected */
    public void afterRunningAllTasks() {
    }

    /* access modifiers changed from: protected */
    public void cleanup() {
    }

    /* access modifiers changed from: protected */
    public abstract void run();

    /* access modifiers changed from: protected */
    public boolean wakesUpForTask(Runnable runnable) {
        return true;
    }

    static {
        Class<SingleThreadEventExecutor> cls = SingleThreadEventExecutor.class;
        logger = InternalLoggerFactory.getInstance(cls);
        STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, PostalAddressParser.REGION_KEY);
        PROPERTIES_UPDATER = AtomicReferenceFieldUpdater.newUpdater(cls, ThreadProperties.class, "threadProperties");
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, ThreadFactory threadFactory, boolean z) {
        this(eventExecutorGroup, (Executor) new ThreadPerTaskExecutor(threadFactory), z);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, ThreadFactory threadFactory, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler2) {
        this(eventExecutorGroup, (Executor) new ThreadPerTaskExecutor(threadFactory), z, i, rejectedExecutionHandler2);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, Executor executor2, boolean z) {
        this(eventExecutorGroup, executor2, z, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, Executor executor2, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler2) {
        super(eventExecutorGroup);
        this.threadLock = new Semaphore(0);
        this.shutdownHooks = new LinkedHashSet();
        this.state = 1;
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.addTaskWakesUp = z;
        this.maxPendingTasks = Math.max(16, i);
        this.executor = (Executor) ObjectUtil.checkNotNull(executor2, "executor");
        this.taskQueue = newTaskQueue(this.maxPendingTasks);
        this.rejectedExecutionHandler = (RejectedExecutionHandler) ObjectUtil.checkNotNull(rejectedExecutionHandler2, "rejectedHandler");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Queue<Runnable> newTaskQueue() {
        return newTaskQueue(this.maxPendingTasks);
    }

    /* access modifiers changed from: protected */
    public Queue<Runnable> newTaskQueue(int i) {
        return new LinkedBlockingQueue(i);
    }

    /* access modifiers changed from: protected */
    public void interruptThread() {
        Thread thread2 = this.thread;
        if (thread2 == null) {
            this.interrupted = true;
        } else {
            thread2.interrupt();
        }
    }

    /* access modifiers changed from: protected */
    public Runnable pollTask() {
        return pollTaskFrom(this.taskQueue);
    }

    protected static Runnable pollTaskFrom(Queue<Runnable> queue) {
        Runnable runnable;
        do {
            runnable = (Runnable) queue.poll();
        } while (runnable == WAKEUP_TASK);
        return runnable;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        if (r0 == WAKEUP_TASK) goto L_0x0019;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Runnable takeTask() {
        /*
            r7 = this;
            java.util.Queue<java.lang.Runnable> r0 = r7.taskQueue
            boolean r1 = r0 instanceof java.util.concurrent.BlockingQueue
            if (r1 == 0) goto L_0x003e
            java.util.concurrent.BlockingQueue r0 = (java.util.concurrent.BlockingQueue) r0
        L_0x0008:
            io.netty.util.concurrent.ScheduledFutureTask r1 = r7.peekScheduledTask()
            r2 = 0
            if (r1 != 0) goto L_0x001b
            java.lang.Object r0 = r0.take()     // Catch:{ InterruptedException -> 0x0019 }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ InterruptedException -> 0x0019 }
            java.lang.Runnable r1 = WAKEUP_TASK     // Catch:{ InterruptedException -> 0x001a }
            if (r0 != r1) goto L_0x001a
        L_0x0019:
            r0 = r2
        L_0x001a:
            return r0
        L_0x001b:
            long r3 = r1.delayNanos()
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x002f
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x002e }
            java.lang.Object r1 = r0.poll(r3, r1)     // Catch:{ InterruptedException -> 0x002e }
            java.lang.Runnable r1 = (java.lang.Runnable) r1     // Catch:{ InterruptedException -> 0x002e }
            goto L_0x0030
        L_0x002e:
            return r2
        L_0x002f:
            r1 = r2
        L_0x0030:
            if (r1 != 0) goto L_0x003b
            r7.fetchFromScheduledTaskQueue()
            java.lang.Object r1 = r0.poll()
            java.lang.Runnable r1 = (java.lang.Runnable) r1
        L_0x003b:
            if (r1 == 0) goto L_0x0008
            return r1
        L_0x003e:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>()
            goto L_0x0045
        L_0x0044:
            throw r0
        L_0x0045:
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.concurrent.SingleThreadEventExecutor.takeTask():java.lang.Runnable");
    }

    private boolean fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable pollScheduledTask = pollScheduledTask(nanoTime);
        while (pollScheduledTask != null) {
            if (!this.taskQueue.offer(pollScheduledTask)) {
                scheduledTaskQueue().add((ScheduledFutureTask) pollScheduledTask);
                return false;
            }
            pollScheduledTask = pollScheduledTask(nanoTime);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public Runnable peekTask() {
        return (Runnable) this.taskQueue.peek();
    }

    /* access modifiers changed from: protected */
    public boolean hasTasks() {
        return !this.taskQueue.isEmpty();
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    /* access modifiers changed from: protected */
    public void addTask(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        } else if (!offerTask(runnable)) {
            reject(runnable);
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean offerTask(Runnable runnable) {
        if (isShutdown()) {
            reject();
        }
        return this.taskQueue.offer(runnable);
    }

    /* access modifiers changed from: protected */
    public boolean removeTask(Runnable runnable) {
        if (runnable != null) {
            return this.taskQueue.remove(runnable);
        }
        throw new NullPointerException("task");
    }

    /* access modifiers changed from: protected */
    public boolean runAllTasks() {
        boolean fetchFromScheduledTaskQueue;
        boolean z = false;
        do {
            fetchFromScheduledTaskQueue = fetchFromScheduledTaskQueue();
            if (runAllTasksFrom(this.taskQueue)) {
                z = true;
                continue;
            }
        } while (!fetchFromScheduledTaskQueue);
        if (z) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        afterRunningAllTasks();
        return z;
    }

    /* access modifiers changed from: protected */
    public final boolean runAllTasksFrom(Queue<Runnable> queue) {
        Runnable pollTaskFrom = pollTaskFrom(queue);
        if (pollTaskFrom == null) {
            return false;
        }
        do {
            safeExecute(pollTaskFrom);
            pollTaskFrom = pollTaskFrom(queue);
        } while (pollTaskFrom != null);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean runAllTasks(long j) {
        long j2;
        fetchFromScheduledTaskQueue();
        Runnable pollTask = pollTask();
        if (pollTask == null) {
            afterRunningAllTasks();
            return false;
        }
        long nanoTime = ScheduledFutureTask.nanoTime() + j;
        long j3 = 0;
        while (true) {
            safeExecute(pollTask);
            j3++;
            if ((63 & j3) == 0) {
                j2 = ScheduledFutureTask.nanoTime();
                if (j2 >= nanoTime) {
                    break;
                }
            }
            pollTask = pollTask();
            if (pollTask == null) {
                j2 = ScheduledFutureTask.nanoTime();
                break;
            }
        }
        afterRunningAllTasks();
        this.lastExecutionTime = j2;
        return true;
    }

    /* access modifiers changed from: protected */
    public long delayNanos(long j) {
        ScheduledFutureTask peekScheduledTask = peekScheduledTask();
        if (peekScheduledTask == null) {
            return SCHEDULE_PURGE_INTERVAL;
        }
        return peekScheduledTask.delayNanos(j);
    }

    /* access modifiers changed from: protected */
    public void updateLastExecutionTime() {
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
    }

    /* access modifiers changed from: protected */
    public void wakeup(boolean z) {
        if (!z || this.state == 3) {
            this.taskQueue.offer(WAKEUP_TASK);
        }
    }

    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    public void addShutdownHook(final Runnable runnable) {
        if (inEventLoop()) {
            this.shutdownHooks.add(runnable);
        } else {
            execute(new Runnable() {
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.add(runnable);
                }
            });
        }
    }

    public void removeShutdownHook(final Runnable runnable) {
        if (inEventLoop()) {
            this.shutdownHooks.remove(runnable);
        } else {
            execute(new Runnable() {
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.remove(runnable);
                }
            });
        }
    }

    private boolean runShutdownHooks() {
        boolean z = false;
        while (!this.shutdownHooks.isEmpty()) {
            ArrayList arrayList = new ArrayList(this.shutdownHooks);
            this.shutdownHooks.clear();
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    try {
                        ((Runnable) it.next()).run();
                    } catch (Throwable th) {
                        logger.warn("Shutdown hook raised an exception.", th);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        return z;
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        boolean z;
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("quietPeriod: ");
            sb.append(j);
            sb.append(" (expected >= 0)");
            throw new IllegalArgumentException(sb.toString());
        } else if (j2 < j) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("timeout: ");
            sb2.append(j2);
            sb2.append(" (expected >= quietPeriod (");
            sb2.append(j);
            sb2.append("))");
            throw new IllegalArgumentException(sb2.toString());
        } else if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (isShuttingDown()) {
            return terminationFuture();
        } else {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int i = this.state;
                int i2 = 3;
                if (inEventLoop || i == 1 || i == 2) {
                    z = true;
                } else {
                    i2 = i;
                    z = false;
                }
                if (STATE_UPDATER.compareAndSet(this, i, i2)) {
                    this.gracefulShutdownQuietPeriod = timeUnit.toNanos(j);
                    this.gracefulShutdownTimeout = timeUnit.toNanos(j2);
                    if (i == 1) {
                        doStartThread();
                    }
                    if (z) {
                        wakeup(inEventLoop);
                    }
                    return terminationFuture();
                }
            }
            return terminationFuture();
        }
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        boolean z;
        if (!isShutdown()) {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int i = this.state;
                int i2 = 4;
                if (inEventLoop || i == 1 || i == 2 || i == 3) {
                    z = true;
                } else {
                    i2 = i;
                    z = false;
                }
                if (STATE_UPDATER.compareAndSet(this, i, i2)) {
                    if (i == 1) {
                        doStartThread();
                    }
                    if (z) {
                        wakeup(inEventLoop);
                    }
                    return;
                }
            }
        }
    }

    public boolean isShuttingDown() {
        return this.state >= 3;
    }

    public boolean isShutdown() {
        return this.state >= 4;
    }

    public boolean isTerminated() {
        return this.state == 5;
    }

    /* access modifiers changed from: protected */
    public boolean confirmShutdown() {
        if (!isShuttingDown()) {
            return false;
        }
        if (inEventLoop()) {
            cancelScheduledTasks();
            if (this.gracefulShutdownStartTime == 0) {
                this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
            }
            if (!runAllTasks() && !runShutdownHooks()) {
                long nanoTime = ScheduledFutureTask.nanoTime();
                if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout || nanoTime - this.lastExecutionTime > this.gracefulShutdownQuietPeriod) {
                    return true;
                }
                wakeup(true);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException unused) {
                }
                return false;
            } else if (isShutdown() || this.gracefulShutdownQuietPeriod == 0) {
                return true;
            } else {
                wakeup(true);
                return false;
            }
        } else {
            throw new IllegalStateException("must be invoked from an event loop");
        }
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (!inEventLoop()) {
            if (this.threadLock.tryAcquire(j, timeUnit)) {
                this.threadLock.release();
            }
            return isTerminated();
        } else {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            boolean inEventLoop = inEventLoop();
            if (inEventLoop) {
                addTask(runnable);
            } else {
                startThread();
                addTask(runnable);
                if (isShutdown() && removeTask(runnable)) {
                    reject();
                }
            }
            if (!this.addTaskWakesUp && wakesUpForTask(runnable)) {
                wakeup(inEventLoop);
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(collection);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        throwIfInEventLoop("invokeAny");
        return super.invokeAny(collection, j, timeUnit);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(collection);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        throwIfInEventLoop("invokeAll");
        return super.invokeAll(collection, j, timeUnit);
    }

    private void throwIfInEventLoop(String str) {
        if (inEventLoop()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calling ");
            sb.append(str);
            sb.append(" from within the EventLoop is not allowed");
            throw new RejectedExecutionException(sb.toString());
        }
    }

    public final ThreadProperties threadProperties() {
        ThreadProperties threadProperties2 = this.threadProperties;
        if (threadProperties2 != null) {
            return threadProperties2;
        }
        Thread thread2 = this.thread;
        if (thread2 == null) {
            submit(NOOP_TASK).syncUninterruptibly();
            thread2 = this.thread;
        }
        DefaultThreadProperties defaultThreadProperties = new DefaultThreadProperties(thread2);
        return !PROPERTIES_UPDATER.compareAndSet(this, null, defaultThreadProperties) ? this.threadProperties : defaultThreadProperties;
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    /* access modifiers changed from: protected */
    public final void reject(Runnable runnable) {
        this.rejectedExecutionHandler.rejected(runnable, this);
    }

    private void startThread() {
        if (this.state == 1 && STATE_UPDATER.compareAndSet(this, 1, 2)) {
            doStartThread();
        }
    }

    private void doStartThread() {
        this.executor.execute(new Runnable() {
            /* JADX WARNING: CFG modification limit reached, blocks count: 217 */
            /* JADX WARNING: Removed duplicated region for block: B:91:0x033c  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r9 = this;
                    java.lang.String r0 = "An event executor terminated with non-empty task queue ("
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.lang.Thread r2 = java.lang.Thread.currentThread()
                    r1.thread = r2
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    boolean r1 = r1.interrupted
                    if (r1 == 0) goto L_0x001c
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.lang.Thread r1 = r1.thread
                    r1.interrupt()
                L_0x001c:
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r1.updateLastExecutionTime()
                    r1 = 3
                    r2 = 41
                    r3 = 0
                    r4 = 5
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x01be }
                    r5.run()     // Catch:{ all -> 0x01be }
                L_0x002b:
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    int r5 = r5.state
                    if (r5 >= r1) goto L_0x003f
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r7 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    boolean r5 = r6.compareAndSet(r7, r5, r1)
                    if (r5 == 0) goto L_0x002b
                L_0x003f:
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    long r5 = r1.gracefulShutdownStartTime
                    r7 = 0
                    int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                    if (r1 != 0) goto L_0x007c
                    io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    java.lang.String r6 = "Buggy "
                    r5.append(r6)
                    java.lang.Class<io.netty.util.concurrent.EventExecutor> r6 = p043io.netty.util.concurrent.EventExecutor.class
                    java.lang.String r6 = r6.getSimpleName()
                    r5.append(r6)
                    java.lang.String r6 = " implementation; "
                    r5.append(r6)
                    java.lang.Class<io.netty.util.concurrent.SingleThreadEventExecutor> r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.class
                    java.lang.String r6 = r6.getSimpleName()
                    r5.append(r6)
                    java.lang.String r6 = ".confirmShutdown() must be called before run() implementation terminates."
                    r5.append(r6)
                    java.lang.String r5 = r5.toString()
                    r1.error(r5)
                L_0x007c:
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0121 }
                    boolean r1 = r1.confirmShutdown()     // Catch:{ all -> 0x0121 }
                    if (r1 == 0) goto L_0x007c
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x00d5 }
                    r1.cleanup()     // Catch:{ all -> 0x00d5 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r1.set(r5, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r1 = r1.threadLock
                    r1.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r1 = r1.taskQueue
                    boolean r1 = r1.isEmpty()
                    if (r1 != 0) goto L_0x00ca
                    io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    r4.<init>()
                L_0x00b0:
                    r4.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r4.append(r0)
                    r4.append(r2)
                    java.lang.String r0 = r4.toString()
                    r1.warn(r0)
                L_0x00ca:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    goto L_0x0212
                L_0x00d5:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x0117
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x0117:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x0121:
                    r1 = move-exception
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0172 }
                    r5.cleanup()     // Catch:{ all -> 0x0172 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x0168
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x0168:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x0172:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x01b4
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x01b4:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x01be:
                    r5 = move-exception
                    io.netty.util.internal.logging.InternalLogger r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger     // Catch:{ all -> 0x02fc }
                    java.lang.String r7 = "Unexpected exception from an event executor: "
                    r6.warn(r7, r5)     // Catch:{ all -> 0x02fc }
                L_0x01c8:
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    int r5 = r5.state
                    if (r5 >= r1) goto L_0x01dc
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r7 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    boolean r5 = r6.compareAndSet(r7, r5, r1)
                    if (r5 == 0) goto L_0x01c8
                L_0x01dc:
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x025f }
                    boolean r1 = r1.confirmShutdown()     // Catch:{ all -> 0x025f }
                    if (r1 == 0) goto L_0x01dc
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0213 }
                    r1.cleanup()     // Catch:{ all -> 0x0213 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r1.set(r5, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r1 = r1.threadLock
                    r1.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r1 = r1.taskQueue
                    boolean r1 = r1.isEmpty()
                    if (r1 != 0) goto L_0x00ca
                    io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    r4.<init>()
                    goto L_0x00b0
                L_0x0212:
                    return
                L_0x0213:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x0255
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x0255:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x025f:
                    r1 = move-exception
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x02b0 }
                    r5.cleanup()     // Catch:{ all -> 0x02b0 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x02a6
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x02a6:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x02b0:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x02f2
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x02f2:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x02fc:
                    r5 = move-exception
                L_0x02fd:
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    int r6 = r6.state
                    if (r6 >= r1) goto L_0x0311
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r7 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r8 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    boolean r6 = r7.compareAndSet(r8, r6, r1)
                    if (r6 == 0) goto L_0x02fd
                L_0x0311:
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x03b5 }
                    boolean r1 = r1.confirmShutdown()     // Catch:{ all -> 0x03b5 }
                    if (r1 == 0) goto L_0x0311
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0369 }
                    r1.cleanup()     // Catch:{ all -> 0x0369 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r1.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r1 = r1.threadLock
                    r1.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r1 = r1.taskQueue
                    boolean r1 = r1.isEmpty()
                    if (r1 != 0) goto L_0x035f
                    io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    r4.<init>()
                    r4.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r4.append(r0)
                    r4.append(r2)
                    java.lang.String r0 = r4.toString()
                    r1.warn(r0)
                L_0x035f:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r5
                L_0x0369:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x03ab
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x03ab:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x03b5:
                    r1 = move-exception
                    io.netty.util.concurrent.SingleThreadEventExecutor r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this     // Catch:{ all -> 0x0406 }
                    r5.cleanup()     // Catch:{ all -> 0x0406 }
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x03fc
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x03fc:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    throw r1
                L_0x0406:
                    r1 = move-exception
                    java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = p043io.netty.util.concurrent.SingleThreadEventExecutor.STATE_UPDATER
                    io.netty.util.concurrent.SingleThreadEventExecutor r6 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    r5.set(r6, r4)
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.concurrent.Semaphore r4 = r4.threadLock
                    r4.release()
                    io.netty.util.concurrent.SingleThreadEventExecutor r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r4 = r4.taskQueue
                    boolean r4 = r4.isEmpty()
                    if (r4 != 0) goto L_0x0448
                    io.netty.util.internal.logging.InternalLogger r4 = p043io.netty.util.concurrent.SingleThreadEventExecutor.logger
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    r5.append(r0)
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    java.util.Queue r0 = r0.taskQueue
                    int r0 = r0.size()
                    r5.append(r0)
                    r5.append(r2)
                    java.lang.String r0 = r5.toString()
                    r4.warn(r0)
                L_0x0448:
                    io.netty.util.concurrent.SingleThreadEventExecutor r0 = p043io.netty.util.concurrent.SingleThreadEventExecutor.this
                    io.netty.util.concurrent.Promise r0 = r0.terminationFuture
                    r0.setSuccess(r3)
                    goto L_0x0453
                L_0x0452:
                    throw r1
                L_0x0453:
                    goto L_0x0452
                */
                throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.concurrent.SingleThreadEventExecutor.C58425.run():void");
            }
        });
    }
}
