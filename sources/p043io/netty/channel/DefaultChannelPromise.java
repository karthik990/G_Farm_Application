package p043io.netty.channel;

import p043io.netty.util.concurrent.DefaultPromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.DefaultChannelPromise */
public class DefaultChannelPromise extends DefaultPromise<Void> implements ChannelPromise, FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public boolean isVoid() {
        return false;
    }

    public ChannelPromise promise() {
        return this;
    }

    public ChannelPromise unvoid() {
        return this;
    }

    public DefaultChannelPromise(Channel channel2) {
        this.channel = channel2;
    }

    public DefaultChannelPromise(Channel channel2, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.channel = channel2;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        EventExecutor executor = super.executor();
        return executor == null ? channel().eventLoop() : executor;
    }

    public Channel channel() {
        return this.channel;
    }

    public ChannelPromise setSuccess() {
        return setSuccess((Void) null);
    }

    public ChannelPromise setSuccess(Void voidR) {
        super.setSuccess(voidR);
        return this;
    }

    public boolean trySuccess() {
        return trySuccess(null);
    }

    public ChannelPromise setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }

    public ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ChannelPromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    public ChannelPromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ChannelPromise await() throws InterruptedException {
        super.await();
        return this;
    }

    public ChannelPromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public long flushCheckpoint() {
        return this.checkpoint;
    }

    public void flushCheckpoint(long j) {
        this.checkpoint = j;
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        if (channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
}
