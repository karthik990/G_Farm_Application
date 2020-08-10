package p043io.netty.channel;

import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;
import p043io.netty.util.concurrent.ProgressivePromise;

/* renamed from: io.netty.channel.ChannelProgressivePromise */
public interface ChannelProgressivePromise extends ProgressivePromise<Void>, ChannelProgressiveFuture, ChannelPromise {
    ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressivePromise await() throws InterruptedException;

    ChannelProgressivePromise awaitUninterruptibly();

    ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressivePromise setFailure(Throwable th);

    ChannelProgressivePromise setProgress(long j, long j2);

    ChannelProgressivePromise setSuccess();

    ChannelProgressivePromise setSuccess(Void voidR);

    ChannelProgressivePromise sync() throws InterruptedException;

    ChannelProgressivePromise syncUninterruptibly();

    ChannelProgressivePromise unvoid();
}
