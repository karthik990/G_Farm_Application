package p043io.netty.channel.oio;

import java.net.SocketAddress;
import p043io.netty.channel.AbstractChannel;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.ThreadPerChannelEventLoop;

/* renamed from: io.netty.channel.oio.AbstractOioChannel */
public abstract class AbstractOioChannel extends AbstractChannel {
    protected static final int SO_TIMEOUT = 1000;
    private final Runnable clearReadPendingRunnable = new Runnable() {
        public void run() {
            AbstractOioChannel.this.readPending = false;
        }
    };
    boolean readPending;
    private final Runnable readTask = new Runnable() {
        public void run() {
            AbstractOioChannel.this.doRead();
        }
    };

    /* renamed from: io.netty.channel.oio.AbstractOioChannel$DefaultOioUnsafe */
    private final class DefaultOioUnsafe extends AbstractUnsafe {
        private DefaultOioUnsafe() {
            super();
        }

        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    boolean isActive = AbstractOioChannel.this.isActive();
                    AbstractOioChannel.this.doConnect(socketAddress, socketAddress2);
                    boolean isActive2 = AbstractOioChannel.this.isActive();
                    safeSetSuccess(channelPromise);
                    if (!isActive && isActive2) {
                        AbstractOioChannel.this.pipeline().fireChannelActive();
                    }
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, annotateConnectException(th, socketAddress));
                    closeIfClosed();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doRead();

    protected AbstractOioChannel(Channel channel) {
        super(channel);
    }

    /* access modifiers changed from: protected */
    public AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe();
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof ThreadPerChannelEventLoop;
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
        if (!this.readPending) {
            this.readPending = true;
            eventLoop().execute(this.readTask);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean isReadPending() {
        return this.readPending;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setReadPending(final boolean z) {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = z;
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        AbstractOioChannel.this.readPending = z;
                    }
                });
            }
        } else {
            this.readPending = z;
        }
    }

    /* access modifiers changed from: protected */
    public final void clearReadPending() {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = false;
            } else {
                eventLoop.execute(this.clearReadPendingRunnable);
            }
        } else {
            this.readPending = false;
        }
    }
}
