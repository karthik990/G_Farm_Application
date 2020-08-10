package p043io.netty.channel;

import p043io.netty.util.concurrent.AbstractEventExecutor;

/* renamed from: io.netty.channel.AbstractEventLoop */
public abstract class AbstractEventLoop extends AbstractEventExecutor implements EventLoop {
    protected AbstractEventLoop() {
    }

    protected AbstractEventLoop(EventLoopGroup eventLoopGroup) {
        super(eventLoopGroup);
    }

    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }
}
