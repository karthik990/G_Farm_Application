package p043io.netty.channel.group;

import java.util.Iterator;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.group.ChannelGroupFuture */
public interface ChannelGroupFuture extends Future<Void>, Iterable<ChannelFuture> {
    ChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelGroupFuture await() throws InterruptedException;

    ChannelGroupFuture awaitUninterruptibly();

    ChannelGroupException cause();

    ChannelFuture find(Channel channel);

    ChannelGroup group();

    boolean isPartialFailure();

    boolean isPartialSuccess();

    boolean isSuccess();

    Iterator<ChannelFuture> iterator();

    ChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelGroupFuture sync() throws InterruptedException;

    ChannelGroupFuture syncUninterruptibly();
}
