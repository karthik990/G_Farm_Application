package p043io.netty.channel;

import p043io.netty.util.concurrent.OrderedEventExecutor;

/* renamed from: io.netty.channel.EventLoop */
public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
    EventLoopGroup parent();
}
