package p043io.netty.channel;

import p043io.netty.util.concurrent.DefaultProgressivePromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.DefaultChannelProgressivePromise */
public class DefaultChannelProgressivePromise extends DefaultProgressivePromise<Void> implements ChannelProgressivePromise, FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public boolean isVoid() {
        return false;
    }

    public ChannelProgressivePromise promise() {
        return this;
    }

    public ChannelProgressivePromise unvoid() {
        return this;
    }

    public DefaultChannelProgressivePromise(Channel channel2) {
        this.channel = channel2;
    }

    public DefaultChannelProgressivePromise(Channel channel2, EventExecutor eventExecutor) {
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

    public ChannelProgressivePromise setSuccess() {
        return setSuccess((Void) null);
    }

    public ChannelProgressivePromise setSuccess(Void voidR) {
        super.setSuccess(voidR);
        return this;
    }

    public boolean trySuccess() {
        return trySuccess(null);
    }

    public ChannelProgressivePromise setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }

    public ChannelProgressivePromise setProgress(long j, long j2) {
        super.setProgress(j, j2);
        return this;
    }

    public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ChannelProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ChannelProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }

    public ChannelProgressivePromise awaitUninterruptibly() {
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
