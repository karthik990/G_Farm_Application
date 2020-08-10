package p043io.netty.util.concurrent;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.GlobalEventExecutor */
public final class GlobalEventExecutor extends AbstractScheduledEventExecutor {
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    private static final long SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
    final ScheduledFutureTask<Void> quietPeriodTask;
    /* access modifiers changed from: private */
    public final AtomicBoolean started;
    final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue();
    private final TaskRunner taskRunner;
    private final Future<?> terminationFuture;
    volatile Thread thread;
    final ThreadFactory threadFactory;

    /* renamed from: io.netty.util.concurrent.GlobalEventExecutor$TaskRunner */
    final class TaskRunner implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<GlobalEventExecutor> cls = GlobalEventExecutor.class;
        }

        TaskRunner() {
        }

        public void run() {
            while (true) {
                Runnable takeTask = GlobalEventExecutor.this.takeTask();
                if (takeTask != null) {
                    try {
                        takeTask.run();
                    } catch (Throwable th) {
                        GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", th);
                    }
                    if (takeTask != GlobalEventExecutor.this.quietPeriodTask) {
                        continue;
                    }
                }
                Queue queue = GlobalEventExecutor.this.scheduledTaskQueue;
                if (GlobalEventExecutor.this.taskQueue.isEmpty() && (queue == null || queue.size() == 1)) {
                    GlobalEventExecutor.this.started.compareAndSet(true, false);
                    if ((GlobalEventExecutor.this.taskQueue.isEmpty() && (queue == null || queue.size() == 1)) || !GlobalEventExecutor.this.started.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    private GlobalEventExecutor() {
        ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask((AbstractScheduledEventExecutor) this, Executors.callable(new Runnable() {
            public void run() {
            }
        }, null), ScheduledFutureTask.deadlineNanos(SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
        this.quietPeriodTask = scheduledFutureTask;
        this.threadFactory = new DefaultThreadFactory(DefaultThreadFactory.toPoolName(getClass()), false, 5, null);
        this.taskRunner = new TaskRunner();
        this.started = new AtomicBoolean();
        this.terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
        scheduledTaskQueue().add(this.quietPeriodTask);
    }

    /* access modifiers changed from: 0000 */
    public Runnable takeTask() {
        Runnable runnable;
        Runnable runnable2;
        BlockingQueue<Runnable> blockingQueue = this.taskQueue;
        do {
            ScheduledFutureTask peekScheduledTask = peekScheduledTask();
            if (peekScheduledTask == null) {
                try {
                    runnable2 = (Runnable) blockingQueue.take();
                } catch (InterruptedException unused) {
                    runnable2 = null;
                }
                return runnable2;
            }
            long delayNanos = peekScheduledTask.delayNanos();
            if (delayNanos > 0) {
                try {
                    runnable = (Runnable) blockingQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
                } catch (InterruptedException unused2) {
                    return null;
                }
            } else {
                runnable = (Runnable) blockingQueue.poll();
            }
            if (runnable == null) {
                fetchFromScheduledTaskQueue();
                runnable = (Runnable) blockingQueue.poll();
                continue;
            }
        } while (runnable == null);
        return runnable;
    }

    private void fetchFromScheduledTaskQueue() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        Runnable pollScheduledTask = pollScheduledTask(nanoTime);
        while (pollScheduledTask != null) {
            this.taskQueue.add(pollScheduledTask);
            pollScheduledTask = pollScheduledTask(nanoTime);
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    private void addTask(Runnable runnable) {
        if (runnable != null) {
            this.taskQueue.add(runnable);
            return;
        }
        throw new NullPointerException("task");
    }

    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean awaitInactivity(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit != null) {
            Thread thread2 = this.thread;
            if (thread2 != null) {
                thread2.join(timeUnit.toMillis(j));
                return !thread2.isAlive();
            }
            throw new IllegalStateException("thread was not started");
        }
        throw new NullPointerException("unit");
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            addTask(runnable);
            if (!inEventLoop()) {
                startThread();
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    private void startThread() {
        if (this.started.compareAndSet(false, true)) {
            Thread newThread = this.threadFactory.newThread(this.taskRunner);
            this.thread = newThread;
            newThread.start();
        }
    }
}
