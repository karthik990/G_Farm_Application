package p043io.netty.channel.nio;

import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import p043io.netty.channel.DefaultSelectStrategyFactory;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.MultithreadEventLoopGroup;
import p043io.netty.channel.SelectStrategyFactory;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.EventExecutorChooserFactory;
import p043io.netty.util.concurrent.RejectedExecutionHandler;
import p043io.netty.util.concurrent.RejectedExecutionHandlers;

/* renamed from: io.netty.channel.nio.NioEventLoopGroup */
public class NioEventLoopGroup extends MultithreadEventLoopGroup {
    public NioEventLoopGroup() {
        this(0);
    }

    public NioEventLoopGroup(int i) {
        this(i, (Executor) null);
    }

    public NioEventLoopGroup(int i, ThreadFactory threadFactory) {
        this(i, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int i, Executor executor) {
        this(i, executor, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int i, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(i, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int i, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(i, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int i, Executor executor, SelectorProvider selectorProvider) {
        this(i, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int i, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(i, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int i, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(i, executor, eventExecutorChooserFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int i, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, executor, eventExecutorChooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }

    public void setIoRatio(int i) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((NioEventLoop) ((EventExecutor) it.next())).setIoRatio(i);
        }
    }

    public void rebuildSelectors() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((NioEventLoop) ((EventExecutor) it.next())).rebuildSelector();
        }
    }

    /* access modifiers changed from: protected */
    public EventLoop newChild(Executor executor, Object... objArr) throws Exception {
        NioEventLoop nioEventLoop = new NioEventLoop(this, executor, (SelectorProvider) objArr[0], objArr[1].newSelectStrategy(), (RejectedExecutionHandler) objArr[2]);
        return nioEventLoop;
    }
}
