package p043io.netty.channel;

/* renamed from: io.netty.channel.ThreadPerChannelEventLoop */
public class ThreadPerChannelEventLoop extends SingleThreadEventLoop {
    /* access modifiers changed from: private */

    /* renamed from: ch */
    public Channel f3700ch;
    private final ThreadPerChannelEventLoopGroup parent;

    public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        super((EventLoopGroup) threadPerChannelEventLoopGroup, threadPerChannelEventLoopGroup.executor, true);
        this.parent = threadPerChannelEventLoopGroup;
    }

    public ChannelFuture register(ChannelPromise channelPromise) {
        return super.register(channelPromise).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.this.f3700ch = channelFuture.channel();
                } else {
                    ThreadPerChannelEventLoop.this.deregister();
                }
            }
        });
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return super.register(channel, channelPromise).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.this.f3700ch = channelFuture.channel();
                } else {
                    ThreadPerChannelEventLoop.this.deregister();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void run() {
        while (true) {
            Runnable takeTask = takeTask();
            if (takeTask != null) {
                takeTask.run();
                updateLastExecutionTime();
            }
            Channel channel = this.f3700ch;
            if (isShuttingDown()) {
                if (channel != null) {
                    channel.unsafe().close(channel.unsafe().voidPromise());
                }
                if (confirmShutdown()) {
                    return;
                }
            } else if (channel != null && !channel.isRegistered()) {
                runAllTasks();
                deregister();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void deregister() {
        this.f3700ch = null;
        this.parent.activeChildren.remove(this);
        this.parent.idleChildren.add(this);
    }
}
