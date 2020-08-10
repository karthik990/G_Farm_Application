package p043io.netty.channel;

import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import p043io.netty.util.concurrent.AbstractEventExecutorGroup;
import p043io.netty.util.concurrent.DefaultPromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.GlobalEventExecutor;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.concurrent.ThreadPerTaskExecutor;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ReadOnlyIterator;
import p043io.netty.util.internal.ThrowableUtil;

/* renamed from: io.netty.channel.ThreadPerChannelEventLoopGroup */
public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    final Set<EventLoop> activeChildren;
    private final Object[] childArgs;
    private final FutureListener<Object> childTerminationListener;
    final Executor executor;
    final Queue<EventLoop> idleChildren;
    private final int maxChannels;
    private volatile boolean shuttingDown;
    /* access modifiers changed from: private */
    public final Promise<?> terminationFuture;
    private final ChannelException tooManyChannels;

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int i) {
        this(i, Executors.defaultThreadFactory(), new Object[0]);
    }

    protected ThreadPerChannelEventLoopGroup(int i, ThreadFactory threadFactory, Object... objArr) {
        this(i, (Executor) new ThreadPerTaskExecutor(threadFactory), objArr);
    }

    protected ThreadPerChannelEventLoopGroup(int i, Executor executor2, Object... objArr) {
        this.activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
        this.idleChildren = new ConcurrentLinkedQueue();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.childTerminationListener = new FutureListener<Object>() {
            public void operationComplete(Future<Object> future) throws Exception {
                if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
                    ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
                }
            }
        };
        if (i < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", new Object[]{Integer.valueOf(i)}));
        } else if (executor2 != null) {
            if (objArr == null) {
                this.childArgs = EmptyArrays.EMPTY_OBJECTS;
            } else {
                this.childArgs = (Object[]) objArr.clone();
            }
            this.maxChannels = i;
            this.executor = executor2;
            StringBuilder sb = new StringBuilder();
            sb.append("too many channels (max: ");
            sb.append(i);
            sb.append(')');
            this.tooManyChannels = (ChannelException) ThrowableUtil.unknownStackTrace(new ChannelException(sb.toString()), ThreadPerChannelEventLoopGroup.class, "nextChild()");
        } else {
            throw new NullPointerException("executor");
        }
    }

    /* access modifiers changed from: protected */
    public EventLoop newChild(Object... objArr) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }

    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator(this.activeChildren.iterator());
    }

    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        this.shuttingDown = true;
        for (EventLoop shutdownGracefully : this.activeChildren) {
            shutdownGracefully.shutdownGracefully(j, j2, timeUnit);
        }
        for (EventLoop shutdownGracefully2 : this.idleChildren) {
            shutdownGracefully2.shutdownGracefully(j, j2, timeUnit);
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        this.shuttingDown = true;
        for (EventLoop shutdown : this.activeChildren) {
            shutdown.shutdown();
        }
        for (EventLoop shutdown2 : this.idleChildren) {
            shutdown2.shutdown();
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
    }

    public boolean isShuttingDown() {
        for (EventLoop isShuttingDown : this.activeChildren) {
            if (!isShuttingDown.isShuttingDown()) {
                return false;
            }
        }
        for (EventLoop isShuttingDown2 : this.idleChildren) {
            if (!isShuttingDown2.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (EventLoop isShutdown : this.activeChildren) {
            if (!isShutdown.isShutdown()) {
                return false;
            }
        }
        for (EventLoop isShutdown2 : this.idleChildren) {
            if (!isShutdown2.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (EventLoop isTerminated : this.activeChildren) {
            if (!isTerminated.isTerminated()) {
                return false;
            }
        }
        for (EventLoop isTerminated2 : this.idleChildren) {
            if (!isTerminated2.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        long nanoTime = System.nanoTime() + timeUnit.toNanos(j);
        for (EventLoop eventLoop : this.activeChildren) {
            while (true) {
                long nanoTime2 = nanoTime - System.nanoTime();
                if (nanoTime2 <= 0) {
                    return isTerminated();
                }
                if (eventLoop.awaitTermination(nanoTime2, TimeUnit.NANOSECONDS)) {
                }
            }
        }
        for (EventLoop eventLoop2 : this.idleChildren) {
            while (true) {
                long nanoTime3 = nanoTime - System.nanoTime();
                if (nanoTime3 <= 0) {
                    return isTerminated();
                }
                if (eventLoop2.awaitTermination(nanoTime3, TimeUnit.NANOSECONDS)) {
                }
            }
        }
        return isTerminated();
    }

    public ChannelFuture register(Channel channel) {
        if (channel != null) {
            try {
                EventLoop nextChild = nextChild();
                return nextChild.register((ChannelPromise) new DefaultChannelPromise(channel, nextChild));
            } catch (Throwable th) {
                return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, th);
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    public ChannelFuture register(ChannelPromise channelPromise) {
        try {
            return nextChild().register(channelPromise);
        } catch (Throwable th) {
            channelPromise.setFailure(th);
            return channelPromise;
        }
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel != null) {
            try {
                return nextChild().register(channel, channelPromise);
            } catch (Throwable th) {
                channelPromise.setFailure(th);
                return channelPromise;
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    private EventLoop nextChild() throws Exception {
        if (!this.shuttingDown) {
            EventLoop eventLoop = (EventLoop) this.idleChildren.poll();
            if (eventLoop == null) {
                if (this.maxChannels <= 0 || this.activeChildren.size() < this.maxChannels) {
                    eventLoop = newChild(this.childArgs);
                    eventLoop.terminationFuture().addListener(this.childTerminationListener);
                } else {
                    throw this.tooManyChannels;
                }
            }
            this.activeChildren.add(eventLoop);
            return eventLoop;
        }
        throw new RejectedExecutionException("shutting down");
    }
}
