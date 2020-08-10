package p043io.netty.channel;

import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.channel.DefaultChannelHandlerContext */
final class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {
    private final ChannelHandler handler;

    DefaultChannelHandlerContext(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String str, ChannelHandler channelHandler) {
        super(defaultChannelPipeline, eventExecutor, str, isInbound(channelHandler), isOutbound(channelHandler));
        if (channelHandler != null) {
            this.handler = channelHandler;
            return;
        }
        throw new NullPointerException("handler");
    }

    public ChannelHandler handler() {
        return this.handler;
    }

    private static boolean isInbound(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelInboundHandler;
    }

    private static boolean isOutbound(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelOutboundHandler;
    }
}
