package p043io.netty.channel;

import p043io.netty.util.concurrent.PromiseNotifier;

/* renamed from: io.netty.channel.ChannelPromiseNotifier */
public final class ChannelPromiseNotifier extends PromiseNotifier<Void, ChannelFuture> implements ChannelFutureListener {
    public ChannelPromiseNotifier(ChannelPromise... channelPromiseArr) {
        super(channelPromiseArr);
    }

    public ChannelPromiseNotifier(boolean z, ChannelPromise... channelPromiseArr) {
        super(z, channelPromiseArr);
    }
}
