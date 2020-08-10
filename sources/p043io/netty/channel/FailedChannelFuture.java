package p043io.netty.channel;

import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.channel.FailedChannelFuture */
final class FailedChannelFuture extends CompleteChannelFuture {
    private final Throwable cause;

    public boolean isSuccess() {
        return false;
    }

    FailedChannelFuture(Channel channel, EventExecutor eventExecutor, Throwable th) {
        super(channel, eventExecutor);
        if (th != null) {
            this.cause = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    public Throwable cause() {
        return this.cause;
    }

    public ChannelFuture sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
}
