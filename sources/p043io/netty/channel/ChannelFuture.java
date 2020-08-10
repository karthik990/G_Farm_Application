package p043io.netty.channel;

import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.ChannelFuture */
public interface ChannelFuture extends Future<Void> {
    ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelFuture await() throws InterruptedException;

    ChannelFuture awaitUninterruptibly();

    Channel channel();

    boolean isVoid();

    ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelFuture sync() throws InterruptedException;

    ChannelFuture syncUninterruptibly();
}
