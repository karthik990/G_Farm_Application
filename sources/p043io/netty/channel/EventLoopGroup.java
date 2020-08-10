package p043io.netty.channel;

import p043io.netty.util.concurrent.EventExecutorGroup;

/* renamed from: io.netty.channel.EventLoopGroup */
public interface EventLoopGroup extends EventExecutorGroup {
    EventLoop next();

    ChannelFuture register(Channel channel);

    @Deprecated
    ChannelFuture register(Channel channel, ChannelPromise channelPromise);

    ChannelFuture register(ChannelPromise channelPromise);
}
