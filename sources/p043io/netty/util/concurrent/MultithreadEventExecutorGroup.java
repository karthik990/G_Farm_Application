package p043io.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser;

/* renamed from: io.netty.util.concurrent.MultithreadEventExecutorGroup */
public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
    /* access modifiers changed from: private */
    public final EventExecutor[] children;
    private final EventExecutorChooser chooser;
    private final Set<EventExecutor> readonlyChildren;
    /* access modifiers changed from: private */
    public final AtomicInteger terminatedChildren;
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture;

    /* access modifiers changed from: protected */
    public abstract EventExecutor newChild(Executor executor, Object... objArr) throws Exception;

    protected MultithreadEventExecutorGroup(int i, ThreadFactory threadFactory, Object... objArr) {
        this(i, threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory), objArr);
    }

    protected MultithreadEventExecutorGroup(int i, Executor executor, Object... objArr) {
        this(i, executor, DefaultEventExecutorChooserFactory.INSTANCE, objArr);
    }

    protected MultithreadEventExecutorGroup(int i, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, Object... objArr) {
        this.terminatedChildren = new AtomicInteger();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        int i2 = 0;
        if (i > 0) {
            if (executor == null) {
                executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
            }
            this.children = new EventExecutor[i];
            int i3 = 0;
            while (i3 < i) {
                try {
                    this.children[i3] = newChild(executor, objArr);
                    i3++;
                } catch (Exception e) {
                    throw new IllegalStateException("failed to create a child event loop", e);
                } catch (Throwable th) {
                    for (int i4 = 0; i4 < i3; i4++) {
                        this.children[i4].shutdownGracefully();
                    }
                    while (i2 < i3) {
                        EventExecutor eventExecutor = this.children[i2];
                        while (!eventExecutor.isTerminated()) {
                            try {
                                eventExecutor.awaitTermination(2147483647L, TimeUnit.SECONDS);
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                                throw th;
                            }
                        }
                        i2++;
                    }
                    throw th;
                }
            }
            this.chooser = eventExecutorChooserFactory.newChooser(this.children);
            C58331 r5 = new FutureListener<Object>() {
                public void operationComplete(Future<Object> future) throws Exception {
                    if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
                        MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
                    }
                }
            };
            EventExecutor[] eventExecutorArr = this.children;
            int length = eventExecutorArr.length;
            while (i2 < length) {
                eventExecutorArr[i2].terminationFuture().addListener(r5);
                i2++;
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet(this.children.length);
            Collections.addAll(linkedHashSet, this.children);
            this.readonlyChildren = Collections.unmodifiableSet(linkedHashSet);
            return;
        }
        throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[]{Integer.valueOf(i)}));
    }

    /* access modifiers changed from: protected */
    public ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass());
    }

    public EventExecutor next() {
        return this.chooser.next();
    }

    public Iterator<EventExecutor> iterator() {
        return this.readonlyChildren.iterator();
    }

    public final int executorCount() {
        return this.children.length;
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        for (EventExecutor shutdownGracefully : this.children) {
            shutdownGracefully.shutdownGracefully(j, j2, timeUnit);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        for (EventExecutor shutdown : this.children) {
            shutdown.shutdown();
        }
    }

    public boolean isShuttingDown() {
        for (EventExecutor isShuttingDown : this.children) {
            if (!isShuttingDown.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (EventExecutor isShutdown : this.children) {
            if (!isShutdown.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (EventExecutor isTerminated : this.children) {
            if (!isTerminated.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        EventExecutor[] eventExecutorArr;
        long nanoTime;
        long nanoTime2 = System.nanoTime() + timeUnit.toNanos(j);
        loop0:
        for (EventExecutor eventExecutor : this.children) {
            do {
                nanoTime = nanoTime2 - System.nanoTime();
                if (nanoTime <= 0) {
                    break loop0;
                }
            } while (!eventExecutor.awaitTermination(nanoTime, TimeUnit.NANOSECONDS));
        }
        return isTerminated();
    }
}
