package p043io.netty.channel;

import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.channel.SucceededChannelFuture */
final class SucceededChannelFuture extends CompleteChannelFuture {
    public Throwable cause() {
        return null;
    }

    public boolean isSuccess() {
        return true;
    }

    SucceededChannelFuture(Channel channel, EventExecutor eventExecutor) {
        super(channel, eventExecutor);
    }
}
