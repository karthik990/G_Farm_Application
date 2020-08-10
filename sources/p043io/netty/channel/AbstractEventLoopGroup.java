package p043io.netty.channel;

import p043io.netty.util.concurrent.AbstractEventExecutorGroup;

/* renamed from: io.netty.channel.AbstractEventLoopGroup */
public abstract class AbstractEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    public abstract EventLoop next();
}
