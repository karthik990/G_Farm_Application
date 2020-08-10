package p043io.netty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import p043io.netty.util.concurrent.DefaultThreadFactory;

/* renamed from: io.netty.channel.DefaultEventLoop */
public class DefaultEventLoop extends SingleThreadEventLoop {
    public DefaultEventLoop() {
        this((EventLoopGroup) null);
    }

    public DefaultEventLoop(ThreadFactory threadFactory) {
        this((EventLoopGroup) null, threadFactory);
    }

    public DefaultEventLoop(Executor executor) {
        this((EventLoopGroup) null, executor);
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup) {
        this(eventLoopGroup, (ThreadFactory) new DefaultThreadFactory(DefaultEventLoop.class));
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory) {
        super(eventLoopGroup, threadFactory, true);
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup, Executor executor) {
        super(eventLoopGroup, executor, true);
    }

    /* access modifiers changed from: protected */
    public void run() {
        do {
            Runnable takeTask = takeTask();
            if (takeTask != null) {
                takeTask.run();
                updateLastExecutionTime();
            }
        } while (!confirmShutdown());
    }
}
