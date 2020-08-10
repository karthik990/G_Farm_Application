package p043io.netty.channel;

import java.util.concurrent.TimeUnit;
import p043io.netty.util.concurrent.AbstractFuture;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.VoidChannelPromise */
final class VoidChannelPromise extends AbstractFuture<Void> implements ChannelPromise {
    private final Channel channel;
    private final boolean fireException;

    public boolean cancel(boolean z) {
        return false;
    }

    public Throwable cause() {
        return null;
    }

    public Void getNow() {
        return null;
    }

    public boolean isCancellable() {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return false;
    }

    public boolean isSuccess() {
        return false;
    }

    public boolean isVoid() {
        return true;
    }

    public VoidChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        return this;
    }

    public VoidChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        return this;
    }

    public VoidChannelPromise setSuccess() {
        return this;
    }

    public VoidChannelPromise setSuccess(Void voidR) {
        return this;
    }

    public boolean setUncancellable() {
        return true;
    }

    public boolean trySuccess() {
        return false;
    }

    public boolean trySuccess(Void voidR) {
        return false;
    }

    VoidChannelPromise(Channel channel2, boolean z) {
        if (channel2 != null) {
            this.channel = channel2;
            this.fireException = z;
            return;
        }
        throw new NullPointerException("channel");
    }

    public VoidChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        fail();
        return this;
    }

    public VoidChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        fail();
        return this;
    }

    public VoidChannelPromise await() throws InterruptedException {
        if (!Thread.interrupted()) {
            return this;
        }
        throw new InterruptedException();
    }

    public boolean await(long j, TimeUnit timeUnit) {
        fail();
        return false;
    }

    public boolean await(long j) {
        fail();
        return false;
    }

    public VoidChannelPromise awaitUninterruptibly() {
        fail();
        return this;
    }

    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        fail();
        return false;
    }

    public boolean awaitUninterruptibly(long j) {
        fail();
        return false;
    }

    public Channel channel() {
        return this.channel;
    }

    public VoidChannelPromise sync() {
        fail();
        return this;
    }

    public VoidChannelPromise syncUninterruptibly() {
        fail();
        return this;
    }

    public VoidChannelPromise setFailure(Throwable th) {
        fireException(th);
        return this;
    }

    public boolean tryFailure(Throwable th) {
        fireException(th);
        return false;
    }

    private static void fail() {
        throw new IllegalStateException("void future");
    }

    public ChannelPromise unvoid() {
        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise(this.channel);
        if (this.fireException) {
            defaultChannelPromise.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        VoidChannelPromise.this.fireException(channelFuture.cause());
                    }
                }
            });
        }
        return defaultChannelPromise;
    }

    /* access modifiers changed from: private */
    public void fireException(Throwable th) {
        if (this.fireException && this.channel.isRegistered()) {
            this.channel.pipeline().fireExceptionCaught(th);
        }
    }
}
