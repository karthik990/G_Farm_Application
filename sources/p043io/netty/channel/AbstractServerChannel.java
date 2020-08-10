package p043io.netty.channel;

import java.net.SocketAddress;

/* renamed from: io.netty.channel.AbstractServerChannel */
public abstract class AbstractServerChannel extends AbstractChannel implements ServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);

    /* renamed from: io.netty.channel.AbstractServerChannel$DefaultServerUnsafe */
    private final class DefaultServerUnsafe extends AbstractUnsafe {
        private DefaultServerUnsafe() {
            super();
        }

        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            safeSetFailure(channelPromise, new UnsupportedOperationException());
        }
    }

    public SocketAddress remoteAddress() {
        return null;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return null;
    }

    protected AbstractServerChannel() {
        super(null);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public AbstractUnsafe newUnsafe() {
        return new DefaultServerUnsafe();
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object obj) {
        throw new UnsupportedOperationException();
    }
}
