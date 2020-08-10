package p043io.netty.channel;

import p043io.netty.util.concurrent.PromiseAggregator;

@Deprecated
/* renamed from: io.netty.channel.ChannelPromiseAggregator */
public final class ChannelPromiseAggregator extends PromiseAggregator<Void, ChannelFuture> implements ChannelFutureListener {
    public ChannelPromiseAggregator(ChannelPromise channelPromise) {
        super(channelPromise);
    }
}
