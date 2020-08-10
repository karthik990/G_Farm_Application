package p043io.netty.util;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.exoplayer2.C1996C;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.HashedWheelTimer */
public class HashedWheelTimer implements Timer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
    private static final int INSTANCE_COUNT_LIMIT = 64;
    private static final AtomicBoolean WARNED_TOO_MANY_INSTANCES = new AtomicBoolean();
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    public static final int WORKER_STATE_STARTED = 1;
    /* access modifiers changed from: private */
    public static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
    private static final ResourceLeakDetector<HashedWheelTimer> leakDetector;
    static final InternalLogger logger;
    /* access modifiers changed from: private */
    public final Queue<HashedWheelTimeout> cancelledTimeouts;
    private final ResourceLeakTracker<HashedWheelTimer> leak;
    /* access modifiers changed from: private */
    public final int mask;
    private final long maxPendingTimeouts;
    /* access modifiers changed from: private */
    public final AtomicLong pendingTimeouts;
    /* access modifiers changed from: private */
    public volatile long startTime;
    /* access modifiers changed from: private */
    public final CountDownLatch startTimeInitialized;
    /* access modifiers changed from: private */
    public final long tickDuration;
    /* access modifiers changed from: private */
    public final Queue<HashedWheelTimeout> timeouts;
    /* access modifiers changed from: private */
    public final HashedWheelBucket[] wheel;
    private final Worker worker;
    private volatile int workerState;
    private final Thread workerThread;

    /* renamed from: io.netty.util.HashedWheelTimer$HashedWheelBucket */
    private static final class HashedWheelBucket {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;

        static {
            Class<HashedWheelTimer> cls = HashedWheelTimer.class;
        }

        private HashedWheelBucket() {
        }

        public void addTimeout(HashedWheelTimeout hashedWheelTimeout) {
            hashedWheelTimeout.bucket = this;
            if (this.head == null) {
                this.tail = hashedWheelTimeout;
                this.head = hashedWheelTimeout;
                return;
            }
            HashedWheelTimeout hashedWheelTimeout2 = this.tail;
            hashedWheelTimeout2.next = hashedWheelTimeout;
            hashedWheelTimeout.prev = hashedWheelTimeout2;
            this.tail = hashedWheelTimeout;
        }

        public void expireTimeouts(long j) {
            HashedWheelTimeout hashedWheelTimeout = this.head;
            while (hashedWheelTimeout != null) {
                HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
                if (hashedWheelTimeout.remainingRounds <= 0) {
                    hashedWheelTimeout2 = remove(hashedWheelTimeout);
                    if (hashedWheelTimeout.deadline <= j) {
                        hashedWheelTimeout.expire();
                    } else {
                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", new Object[]{Long.valueOf(hashedWheelTimeout.deadline), Long.valueOf(j)}));
                    }
                } else if (hashedWheelTimeout.isCancelled()) {
                    hashedWheelTimeout = remove(hashedWheelTimeout);
                } else {
                    hashedWheelTimeout.remainingRounds--;
                }
                hashedWheelTimeout = hashedWheelTimeout2;
            }
        }

        public HashedWheelTimeout remove(HashedWheelTimeout hashedWheelTimeout) {
            HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
            if (hashedWheelTimeout.prev != null) {
                hashedWheelTimeout.prev.next = hashedWheelTimeout2;
            }
            if (hashedWheelTimeout.next != null) {
                hashedWheelTimeout.next.prev = hashedWheelTimeout.prev;
            }
            if (hashedWheelTimeout == this.head) {
                if (hashedWheelTimeout == this.tail) {
                    this.tail = null;
                    this.head = null;
                } else {
                    this.head = hashedWheelTimeout2;
                }
            } else if (hashedWheelTimeout == this.tail) {
                this.tail = hashedWheelTimeout.prev;
            }
            hashedWheelTimeout.prev = null;
            hashedWheelTimeout.next = null;
            hashedWheelTimeout.bucket = null;
            hashedWheelTimeout.timer.pendingTimeouts.decrementAndGet();
            return hashedWheelTimeout2;
        }

        public void clearTimeouts(Set<Timeout> set) {
            while (true) {
                HashedWheelTimeout pollTimeout = pollTimeout();
                if (pollTimeout != null) {
                    if (!pollTimeout.isExpired() && !pollTimeout.isCancelled()) {
                        set.add(pollTimeout);
                    }
                } else {
                    return;
                }
            }
        }

        private HashedWheelTimeout pollTimeout() {
            HashedWheelTimeout hashedWheelTimeout = this.head;
            if (hashedWheelTimeout == null) {
                return null;
            }
            HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
            if (hashedWheelTimeout2 == null) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = hashedWheelTimeout2;
                hashedWheelTimeout2.prev = null;
            }
            hashedWheelTimeout.next = null;
            hashedWheelTimeout.prev = null;
            hashedWheelTimeout.bucket = null;
            return hashedWheelTimeout;
        }
    }

    /* renamed from: io.netty.util.HashedWheelTimer$HashedWheelTimeout */
    private static final class HashedWheelTimeout implements Timeout {
        private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, PostalAddressParser.REGION_KEY);
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private static final int ST_INIT = 0;
        HashedWheelBucket bucket;
        /* access modifiers changed from: private */
        public final long deadline;
        HashedWheelTimeout next;
        HashedWheelTimeout prev;
        long remainingRounds;
        private volatile int state = 0;
        private final TimerTask task;
        /* access modifiers changed from: private */
        public final HashedWheelTimer timer;

        HashedWheelTimeout(HashedWheelTimer hashedWheelTimer, TimerTask timerTask, long j) {
            this.timer = hashedWheelTimer;
            this.task = timerTask;
            this.deadline = j;
        }

        public Timer timer() {
            return this.timer;
        }

        public TimerTask task() {
            return this.task;
        }

        public boolean cancel() {
            if (!compareAndSetState(0, 1)) {
                return false;
            }
            this.timer.cancelledTimeouts.add(this);
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void remove() {
            HashedWheelBucket hashedWheelBucket = this.bucket;
            if (hashedWheelBucket != null) {
                hashedWheelBucket.remove(this);
            } else {
                this.timer.pendingTimeouts.decrementAndGet();
            }
        }

        public boolean compareAndSetState(int i, int i2) {
            return STATE_UPDATER.compareAndSet(this, i, i2);
        }

        public int state() {
            return this.state;
        }

        public boolean isCancelled() {
            return state() == 1;
        }

        public boolean isExpired() {
            return state() == 2;
        }

        public void expire() {
            if (compareAndSetState(0, 2)) {
                try {
                    this.task.run(this);
                } catch (Throwable th) {
                    if (HashedWheelTimer.logger.isWarnEnabled()) {
                        InternalLogger internalLogger = HashedWheelTimer.logger;
                        StringBuilder sb = new StringBuilder();
                        sb.append("An exception was thrown by ");
                        sb.append(TimerTask.class.getSimpleName());
                        sb.append('.');
                        internalLogger.warn(sb.toString(), th);
                    }
                }
            }
        }

        public String toString() {
            long nanoTime = (this.deadline - System.nanoTime()) + this.timer.startTime;
            StringBuilder sb = new StringBuilder(192);
            sb.append(StringUtil.simpleClassName((Object) this));
            sb.append('(');
            sb.append("deadline: ");
            if (nanoTime > 0) {
                sb.append(nanoTime);
                sb.append(" ns later");
            } else if (nanoTime < 0) {
                sb.append(-nanoTime);
                sb.append(" ns ago");
            } else {
                sb.append("now");
            }
            if (isCancelled()) {
                sb.append(", cancelled");
            }
            sb.append(", task: ");
            sb.append(task());
            sb.append(')');
            return sb.toString();
        }
    }

    /* renamed from: io.netty.util.HashedWheelTimer$Worker */
    private final class Worker implements Runnable {
        private long tick;
        private final Set<Timeout> unprocessedTimeouts;

        private Worker() {
            this.unprocessedTimeouts = new HashSet();
        }

        public void run() {
            HashedWheelTimer.this.startTime = System.nanoTime();
            if (HashedWheelTimer.this.startTime == 0) {
                HashedWheelTimer.this.startTime = 1;
            }
            HashedWheelTimer.this.startTimeInitialized.countDown();
            do {
                long waitForNextTick = waitForNextTick();
                if (waitForNextTick > 0) {
                    int access$400 = (int) (this.tick & ((long) HashedWheelTimer.this.mask));
                    processCancelledTasks();
                    HashedWheelBucket hashedWheelBucket = HashedWheelTimer.this.wheel[access$400];
                    transferTimeoutsToBuckets();
                    hashedWheelBucket.expireTimeouts(waitForNextTick);
                    this.tick++;
                }
            } while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
            for (HashedWheelBucket clearTimeouts : HashedWheelTimer.this.wheel) {
                clearTimeouts.clearTimeouts(this.unprocessedTimeouts);
            }
            while (true) {
                HashedWheelTimeout hashedWheelTimeout = (HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll();
                if (hashedWheelTimeout == null) {
                    processCancelledTasks();
                    return;
                } else if (!hashedWheelTimeout.isCancelled()) {
                    this.unprocessedTimeouts.add(hashedWheelTimeout);
                }
            }
        }

        private void transferTimeoutsToBuckets() {
            int i = 0;
            while (i < 100000) {
                HashedWheelTimeout hashedWheelTimeout = (HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll();
                if (hashedWheelTimeout != null) {
                    if (hashedWheelTimeout.state() != 1) {
                        long access$800 = hashedWheelTimeout.deadline / HashedWheelTimer.this.tickDuration;
                        hashedWheelTimeout.remainingRounds = (access$800 - this.tick) / ((long) HashedWheelTimer.this.wheel.length);
                        HashedWheelTimer.this.wheel[(int) (Math.max(access$800, this.tick) & ((long) HashedWheelTimer.this.mask))].addTimeout(hashedWheelTimeout);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }

        private void processCancelledTasks() {
            while (true) {
                HashedWheelTimeout hashedWheelTimeout = (HashedWheelTimeout) HashedWheelTimer.this.cancelledTimeouts.poll();
                if (hashedWheelTimeout != null) {
                    try {
                        hashedWheelTimeout.remove();
                    } catch (Throwable th) {
                        if (HashedWheelTimer.logger.isWarnEnabled()) {
                            HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", th);
                        }
                    }
                } else {
                    return;
                }
            }
        }

        private long waitForNextTick() {
            long access$900 = HashedWheelTimer.this.tickDuration * (this.tick + 1);
            while (true) {
                long nanoTime = System.nanoTime() - HashedWheelTimer.this.startTime;
                long j = ((access$900 - nanoTime) + 999999) / 1000000;
                if (j <= 0) {
                    return nanoTime == Long.MIN_VALUE ? C1996C.TIME_UNSET : nanoTime;
                }
                if (PlatformDependent.isWindows()) {
                    j = (j / 10) * 10;
                }
                try {
                    Thread.sleep(j);
                } catch (InterruptedException unused) {
                    if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }

        public Set<Timeout> unprocessedTimeouts() {
            return Collections.unmodifiableSet(this.unprocessedTimeouts);
        }
    }

    private static int normalizeTicksPerWheel(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 <<= 1;
        }
        return i2;
    }

    static {
        Class<HashedWheelTimer> cls = HashedWheelTimer.class;
        logger = InternalLoggerFactory.getInstance(cls);
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(cls, 1);
        WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "workerState");
    }

    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }

    public HashedWheelTimer(long j, TimeUnit timeUnit) {
        this(Executors.defaultThreadFactory(), j, timeUnit);
    }

    public HashedWheelTimer(long j, TimeUnit timeUnit, int i) {
        this(Executors.defaultThreadFactory(), j, timeUnit, i);
    }

    public HashedWheelTimer(ThreadFactory threadFactory) {
        this(threadFactory, 100, TimeUnit.MILLISECONDS);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit) {
        this(threadFactory, j, timeUnit, 512);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit, int i) {
        this(threadFactory, j, timeUnit, i, true);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit, int i, boolean z) {
        this(threadFactory, j, timeUnit, i, z, -1);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit, int i, boolean z, long j2) {
        ThreadFactory threadFactory2 = threadFactory;
        long j3 = j;
        TimeUnit timeUnit2 = timeUnit;
        int i2 = i;
        ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker = null;
        this.worker = new Worker();
        this.workerState = 0;
        this.startTimeInitialized = new CountDownLatch(1);
        this.timeouts = PlatformDependent.newMpscQueue();
        this.cancelledTimeouts = PlatformDependent.newMpscQueue();
        this.pendingTimeouts = new AtomicLong(0);
        if (threadFactory2 == null) {
            throw new NullPointerException("threadFactory");
        } else if (timeUnit2 == null) {
            throw new NullPointerException("unit");
        } else if (j3 <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("tickDuration must be greater than 0: ");
            sb.append(j3);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > 0) {
            this.wheel = createWheel(i);
            this.mask = this.wheel.length - 1;
            this.tickDuration = timeUnit2.toNanos(j3);
            if (this.tickDuration < Long.MAX_VALUE / ((long) this.wheel.length)) {
                this.workerThread = threadFactory.newThread(this.worker);
                if (z || !this.workerThread.isDaemon()) {
                    resourceLeakTracker = leakDetector.track(this);
                }
                this.leak = resourceLeakTracker;
                this.maxPendingTimeouts = j2;
                if (INSTANCE_COUNTER.incrementAndGet() > 64 && WARNED_TOO_MANY_INSTANCES.compareAndSet(false, true)) {
                    reportTooManyInstances();
                    return;
                }
                return;
            }
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[]{Long.valueOf(j), Long.valueOf(Long.MAX_VALUE / ((long) this.wheel.length))}));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("ticksPerWheel must be greater than 0: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                INSTANCE_COUNTER.decrementAndGet();
            }
        }
    }

    private static HashedWheelBucket[] createWheel(int i) {
        if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("ticksPerWheel must be greater than 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i <= 1073741824) {
            HashedWheelBucket[] hashedWheelBucketArr = new HashedWheelBucket[normalizeTicksPerWheel(i)];
            for (int i2 = 0; i2 < hashedWheelBucketArr.length; i2++) {
                hashedWheelBucketArr[i2] = new HashedWheelBucket();
            }
            return hashedWheelBucketArr;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("ticksPerWheel may not be greater than 2^30: ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public void start() {
        int i = WORKER_STATE_UPDATER.get(this);
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    throw new Error("Invalid WorkerState");
                }
                throw new IllegalStateException("cannot be started once stopped");
            }
        } else if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
            this.workerThread.start();
        }
        while (this.startTime == 0) {
            try {
                this.startTimeInitialized.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    public Set<Timeout> stop() {
        if (Thread.currentThread() == this.workerThread) {
            StringBuilder sb = new StringBuilder();
            sb.append(HashedWheelTimer.class.getSimpleName());
            sb.append(".stop() cannot be called from ");
            sb.append(TimerTask.class.getSimpleName());
            throw new IllegalStateException(sb.toString());
        } else if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
            if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                INSTANCE_COUNTER.decrementAndGet();
                ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker = this.leak;
                if (resourceLeakTracker != null) {
                    resourceLeakTracker.close(this);
                }
            }
            return Collections.emptySet();
        } else {
            boolean z = false;
            while (this.workerThread.isAlive()) {
                try {
                    this.workerThread.interrupt();
                    try {
                        this.workerThread.join(100);
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                } finally {
                    INSTANCE_COUNTER.decrementAndGet();
                    ResourceLeakTracker<HashedWheelTimer> resourceLeakTracker2 = this.leak;
                    if (resourceLeakTracker2 != null) {
                        resourceLeakTracker2.close(this);
                    }
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            return this.worker.unprocessedTimeouts();
        }
    }

    public Timeout newTimeout(TimerTask timerTask, long j, TimeUnit timeUnit) {
        if (timerTask == null) {
            throw new NullPointerException("task");
        } else if (timeUnit != null) {
            long incrementAndGet = this.pendingTimeouts.incrementAndGet();
            long j2 = this.maxPendingTimeouts;
            if (j2 <= 0 || incrementAndGet <= j2) {
                start();
                HashedWheelTimeout hashedWheelTimeout = new HashedWheelTimeout(this, timerTask, (System.nanoTime() + timeUnit.toNanos(j)) - this.startTime);
                this.timeouts.add(hashedWheelTimeout);
                return hashedWheelTimeout;
            }
            this.pendingTimeouts.decrementAndGet();
            StringBuilder sb = new StringBuilder();
            sb.append("Number of pending timeouts (");
            sb.append(incrementAndGet);
            sb.append(") is greater than or equal to maximum allowed pending timeouts (");
            sb.append(this.maxPendingTimeouts);
            sb.append(")");
            throw new RejectedExecutionException(sb.toString());
        } else {
            throw new NullPointerException("unit");
        }
    }

    public long pendingTimeouts() {
        return this.pendingTimeouts.get();
    }

    private static void reportTooManyInstances() {
        String simpleClassName = StringUtil.simpleClassName(HashedWheelTimer.class);
        InternalLogger internalLogger = logger;
        StringBuilder sb = new StringBuilder();
        sb.append("You are creating too many ");
        sb.append(simpleClassName);
        sb.append(" instances. ");
        sb.append(simpleClassName);
        sb.append(" is a shared resource that must be reused across the JVM,so that only a few instances are created.");
        internalLogger.error(sb.toString());
    }
}
