package p043io.netty.channel.group;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.GenericFutureListener;

/* renamed from: io.netty.channel.group.VoidChannelGroupFuture */
final class VoidChannelGroupFuture implements ChannelGroupFuture {
    private static final Iterator<ChannelFuture> EMPTY = Collections.emptyList().iterator();
    private final ChannelGroup group;

    public boolean cancel(boolean z) {
        return false;
    }

    public ChannelGroupException cause() {
        return null;
    }

    public ChannelFuture find(Channel channel) {
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

    public boolean isPartialFailure() {
        return false;
    }

    public boolean isPartialSuccess() {
        return false;
    }

    public boolean isSuccess() {
        return false;
    }

    VoidChannelGroupFuture(ChannelGroup channelGroup) {
        this.group = channelGroup;
    }

    public ChannelGroup group() {
        return this.group;
    }

    public ChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw reject();
    }

    public ChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        throw reject();
    }

    public ChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        throw reject();
    }

    public ChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        throw reject();
    }

    public ChannelGroupFuture await() {
        throw reject();
    }

    public ChannelGroupFuture awaitUninterruptibly() {
        throw reject();
    }

    public ChannelGroupFuture syncUninterruptibly() {
        throw reject();
    }

    public ChannelGroupFuture sync() {
        throw reject();
    }

    public Iterator<ChannelFuture> iterator() {
        return EMPTY;
    }

    public boolean await(long j, TimeUnit timeUnit) {
        throw reject();
    }

    public boolean await(long j) {
        throw reject();
    }

    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        throw reject();
    }

    public boolean awaitUninterruptibly(long j) {
        throw reject();
    }

    public Void get() {
        throw reject();
    }

    public Void get(long j, TimeUnit timeUnit) {
        throw reject();
    }

    private static RuntimeException reject() {
        return new IllegalStateException("void future");
    }
}
