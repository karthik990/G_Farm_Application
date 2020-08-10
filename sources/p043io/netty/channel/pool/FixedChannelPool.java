package p043io.netty.channel.pool;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import p043io.netty.bootstrap.Bootstrap;
import p043io.netty.channel.Channel;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ThrowableUtil;

/* renamed from: io.netty.channel.pool.FixedChannelPool */
public class FixedChannelPool extends SimpleChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final IllegalStateException FULL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final TimeoutException TIMEOUT_EXCEPTION;
    /* access modifiers changed from: private */
    public final long acquireTimeoutNanos;
    /* access modifiers changed from: private */
    public int acquiredChannelCount;
    /* access modifiers changed from: private */
    public boolean closed;
    /* access modifiers changed from: private */
    public final EventExecutor executor;
    private final int maxConnections;
    private final int maxPendingAcquires;
    /* access modifiers changed from: private */
    public int pendingAcquireCount;
    /* access modifiers changed from: private */
    public final Queue<AcquireTask> pendingAcquireQueue;
    private final Runnable timeoutTask;

    /* renamed from: io.netty.channel.pool.FixedChannelPool$6 */
    static /* synthetic */ class C56456 {

        /* renamed from: $SwitchMap$io$netty$channel$pool$FixedChannelPool$AcquireTimeoutAction */
        static final /* synthetic */ int[] f3707xa1ee8ab3 = new int[AcquireTimeoutAction.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                io.netty.channel.pool.FixedChannelPool$AcquireTimeoutAction[] r0 = p043io.netty.channel.pool.FixedChannelPool.AcquireTimeoutAction.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3707xa1ee8ab3 = r0
                int[] r0 = f3707xa1ee8ab3     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.channel.pool.FixedChannelPool$AcquireTimeoutAction r1 = p043io.netty.channel.pool.FixedChannelPool.AcquireTimeoutAction.FAIL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3707xa1ee8ab3     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.channel.pool.FixedChannelPool$AcquireTimeoutAction r1 = p043io.netty.channel.pool.FixedChannelPool.AcquireTimeoutAction.NEW     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.pool.FixedChannelPool.C56456.<clinit>():void");
        }
    }

    /* renamed from: io.netty.channel.pool.FixedChannelPool$AcquireListener */
    private class AcquireListener implements FutureListener<Channel> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected boolean acquired;
        private final Promise<Channel> originalPromise;

        static {
            Class<FixedChannelPool> cls = FixedChannelPool.class;
        }

        AcquireListener(Promise<Channel> promise) {
            this.originalPromise = promise;
        }

        public void operationComplete(Future<Channel> future) throws Exception {
            if (FixedChannelPool.this.closed) {
                this.originalPromise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
                return;
            }
            if (future.isSuccess()) {
                this.originalPromise.setSuccess(future.getNow());
            } else {
                if (this.acquired) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                } else {
                    FixedChannelPool.this.runTaskQueue();
                }
                this.originalPromise.setFailure(future.cause());
            }
        }

        public void acquired() {
            if (!this.acquired) {
                FixedChannelPool.this.acquiredChannelCount = FixedChannelPool.this.acquiredChannelCount + 1;
                this.acquired = true;
            }
        }
    }

    /* renamed from: io.netty.channel.pool.FixedChannelPool$AcquireTask */
    private final class AcquireTask extends AcquireListener {
        final long expireNanoTime = (System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos);
        final Promise<Channel> promise;
        ScheduledFuture<?> timeoutFuture;

        public AcquireTask(Promise<Channel> promise2) {
            super(promise2);
            this.promise = FixedChannelPool.this.executor.newPromise().addListener(this);
        }
    }

    /* renamed from: io.netty.channel.pool.FixedChannelPool$AcquireTimeoutAction */
    public enum AcquireTimeoutAction {
        NEW,
        FAIL
    }

    /* renamed from: io.netty.channel.pool.FixedChannelPool$TimeoutTask */
    private abstract class TimeoutTask implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public abstract void onTimeout(AcquireTask acquireTask);

        static {
            Class<FixedChannelPool> cls = FixedChannelPool.class;
        }

        private TimeoutTask() {
        }

        public final void run() {
            long nanoTime = System.nanoTime();
            while (true) {
                AcquireTask acquireTask = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.peek();
                if (acquireTask != null && nanoTime - acquireTask.expireNanoTime >= 0) {
                    FixedChannelPool.this.pendingAcquireQueue.remove();
                    FixedChannelPool.access$906(FixedChannelPool.this);
                    onTimeout(acquireTask);
                } else {
                    return;
                }
            }
        }
    }

    static /* synthetic */ int access$906(FixedChannelPool fixedChannelPool) {
        int i = fixedChannelPool.pendingAcquireCount - 1;
        fixedChannelPool.pendingAcquireCount = i;
        return i;
    }

    static {
        Class<FixedChannelPool> cls = FixedChannelPool.class;
        FULL_EXCEPTION = (IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("Too many outstanding acquire operations"), cls, "acquire0(...)");
        TIMEOUT_EXCEPTION = (TimeoutException) ThrowableUtil.unknownStackTrace(new TimeoutException("Acquire operation took longer then configured maximum time"), cls, "<init>(...)");
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int i) {
        this(bootstrap, channelPoolHandler, i, Integer.MAX_VALUE);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int i, int i2) {
        this(bootstrap, channelPoolHandler, ChannelHealthChecker.ACTIVE, null, -1, i, i2);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long j, int i, int i2) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, acquireTimeoutAction, j, i, i2, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long j, int i, int i2, boolean z) {
        super(bootstrap, channelPoolHandler, channelHealthChecker, z);
        this.pendingAcquireQueue = new ArrayDeque();
        String str = " (expected: >= 1)";
        if (i < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxConnections: ");
            sb.append(i);
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 >= 1) {
            if (acquireTimeoutAction == null && j == -1) {
                this.timeoutTask = null;
                this.acquireTimeoutNanos = -1;
            } else if (acquireTimeoutAction == null && j != -1) {
                throw new NullPointerException("action");
            } else if (acquireTimeoutAction == null || j >= 0) {
                this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(j);
                int i3 = C56456.f3707xa1ee8ab3[acquireTimeoutAction.ordinal()];
                if (i3 == 1) {
                    this.timeoutTask = new TimeoutTask() {
                        public void onTimeout(AcquireTask acquireTask) {
                            acquireTask.promise.setFailure(FixedChannelPool.TIMEOUT_EXCEPTION);
                        }
                    };
                } else if (i3 == 2) {
                    this.timeoutTask = new TimeoutTask() {
                        public void onTimeout(AcquireTask acquireTask) {
                            acquireTask.acquired();
                            FixedChannelPool.super.acquire(acquireTask.promise);
                        }
                    };
                } else {
                    throw new Error();
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("acquireTimeoutMillis: ");
                sb2.append(j);
                sb2.append(str);
                throw new IllegalArgumentException(sb2.toString());
            }
            this.executor = bootstrap.config().group().next();
            this.maxConnections = i;
            this.maxPendingAcquires = i2;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("maxPendingAcquires: ");
            sb3.append(i2);
            sb3.append(str);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public Future<Channel> acquire(final Promise<Channel> promise) {
        try {
            if (this.executor.inEventLoop()) {
                acquire0(promise);
            } else {
                this.executor.execute(new Runnable() {
                    public void run() {
                        FixedChannelPool.this.acquire0(promise);
                    }
                });
            }
        } catch (Throwable th) {
            promise.setFailure(th);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void acquire0(Promise<Channel> promise) {
        if (this.closed) {
            promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
            return;
        }
        if (this.acquiredChannelCount < this.maxConnections) {
            Promise newPromise = this.executor.newPromise();
            AcquireListener acquireListener = new AcquireListener(promise);
            acquireListener.acquired();
            newPromise.addListener(acquireListener);
            super.acquire(newPromise);
        } else if (this.pendingAcquireCount >= this.maxPendingAcquires) {
            promise.setFailure(FULL_EXCEPTION);
        } else {
            AcquireTask acquireTask = new AcquireTask(promise);
            if (this.pendingAcquireQueue.offer(acquireTask)) {
                this.pendingAcquireCount++;
                Runnable runnable = this.timeoutTask;
                if (runnable != null) {
                    acquireTask.timeoutFuture = this.executor.schedule(runnable, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
                }
            } else {
                promise.setFailure(FULL_EXCEPTION);
            }
        }
    }

    public Future<Void> release(Channel channel, final Promise<Void> promise) {
        Promise newPromise = this.executor.newPromise();
        super.release(channel, newPromise.addListener(new FutureListener<Void>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<FixedChannelPool> cls = FixedChannelPool.class;
            }

            public void operationComplete(Future<Void> future) throws Exception {
                if (FixedChannelPool.this.closed) {
                    promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
                    return;
                }
                if (future.isSuccess()) {
                    FixedChannelPool.this.decrementAndRunTaskQueue();
                    promise.setSuccess(null);
                } else {
                    if (!(future.cause() instanceof IllegalArgumentException)) {
                        FixedChannelPool.this.decrementAndRunTaskQueue();
                    }
                    promise.setFailure(future.cause());
                }
            }
        }));
        return newPromise;
    }

    /* access modifiers changed from: private */
    public void decrementAndRunTaskQueue() {
        this.acquiredChannelCount--;
        runTaskQueue();
    }

    /* access modifiers changed from: private */
    public void runTaskQueue() {
        while (this.acquiredChannelCount < this.maxConnections) {
            AcquireTask acquireTask = (AcquireTask) this.pendingAcquireQueue.poll();
            if (acquireTask != null) {
                ScheduledFuture<?> scheduledFuture = acquireTask.timeoutFuture;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                this.pendingAcquireCount--;
                acquireTask.acquired();
                super.acquire(acquireTask.promise);
            } else {
                return;
            }
        }
    }

    public void close() {
        this.executor.execute(new Runnable() {
            public void run() {
                if (!FixedChannelPool.this.closed) {
                    FixedChannelPool.this.closed = true;
                    while (true) {
                        AcquireTask acquireTask = (AcquireTask) FixedChannelPool.this.pendingAcquireQueue.poll();
                        if (acquireTask == null) {
                            FixedChannelPool.this.acquiredChannelCount = 0;
                            FixedChannelPool.this.pendingAcquireCount = 0;
                            FixedChannelPool.super.close();
                            return;
                        }
                        ScheduledFuture<?> scheduledFuture = acquireTask.timeoutFuture;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(false);
                        }
                        acquireTask.promise.setFailure(new ClosedChannelException());
                    }
                }
            }
        });
    }
}
