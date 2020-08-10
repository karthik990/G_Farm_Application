package p043io.netty.channel;

import p043io.netty.util.concurrent.CompleteFuture;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.CompleteChannelFuture */
abstract class CompleteChannelFuture extends CompleteFuture<Void> implements ChannelFuture {
    private final Channel channel;

    public ChannelFuture await() throws InterruptedException {
        return this;
    }

    public ChannelFuture awaitUninterruptibly() {
        return this;
    }

    public Void getNow() {
        return null;
    }

    public boolean isVoid() {
        return false;
    }

    public ChannelFuture sync() throws InterruptedException {
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        return this;
    }

    protected CompleteChannelFuture(Channel channel2, EventExecutor eventExecutor) {
        super(eventExecutor);
        if (channel2 != null) {
            this.channel = channel2;
            return;
        }
        throw new NullPointerException("channel");
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        EventExecutor executor = super.executor();
        return executor == null ? channel().eventLoop() : executor;
    }

    public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }

    public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners(genericFutureListenerArr);
        return this;
    }

    public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }

    public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners(genericFutureListenerArr);
        return this;
    }

    public Channel channel() {
        return this.channel;
    }
}
