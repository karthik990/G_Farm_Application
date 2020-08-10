package p043io.netty.channel.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.nio.AbstractNioMessageChannel;
import p043io.netty.channel.socket.DefaultServerSocketChannelConfig;
import p043io.netty.channel.socket.ServerSocketChannel;
import p043io.netty.channel.socket.ServerSocketChannelConfig;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SocketUtils;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.socket.nio.NioServerSocketChannel */
public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
    private final ServerSocketChannelConfig config;

    /* renamed from: io.netty.channel.socket.nio.NioServerSocketChannel$NioServerSocketChannelConfig */
    private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
        private NioServerSocketChannelConfig(NioServerSocketChannel nioServerSocketChannel, ServerSocket serverSocket) {
            super(nioServerSocketChannel, serverSocket);
        }

        /* access modifiers changed from: protected */
        public void autoReadCleared() {
            NioServerSocketChannel.this.clearReadPending();
        }
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return null;
    }

    private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }

    public NioServerSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioServerSocketChannel(SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel serverSocketChannel) {
        super(null, serverSocketChannel, 16);
        this.config = new NioServerSocketChannelConfig(this, javaChannel().socket());
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ServerSocketChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        return javaChannel().socket().isBound();
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel) super.javaChannel();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(javaChannel().socket());
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().bind(socketAddress, this.config.getBacklog());
        } else {
            javaChannel().socket().bind(socketAddress, this.config.getBacklog());
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> list) throws Exception {
        SocketChannel accept = SocketUtils.accept(javaChannel());
        if (accept != null) {
            try {
                list.add(new NioSocketChannel(this, accept));
                return 1;
            } catch (Throwable th) {
                logger.warn("Failed to close a socket.", th);
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
