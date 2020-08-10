package p043io.netty.channel;

import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;
import p043io.netty.util.concurrent.ProgressiveFuture;

/* renamed from: io.netty.channel.ChannelProgressiveFuture */
public interface ChannelProgressiveFuture extends ChannelFuture, ProgressiveFuture<Void> {
    ChannelProgressiveFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressiveFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressiveFuture await() throws InterruptedException;

    ChannelProgressiveFuture awaitUninterruptibly();

    ChannelProgressiveFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressiveFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressiveFuture sync() throws InterruptedException;

    ChannelProgressiveFuture syncUninterruptibly();
}
