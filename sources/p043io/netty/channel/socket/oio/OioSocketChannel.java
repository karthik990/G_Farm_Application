package p043io.netty.channel.socket.oio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ConnectTimeoutException;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.oio.OioByteStreamChannel;
import p043io.netty.channel.socket.ServerSocketChannel;
import p043io.netty.channel.socket.SocketChannel;
import p043io.netty.util.internal.SocketUtils;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.socket.oio.OioSocketChannel */
public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
    private final OioSocketChannelConfig config;
    private final Socket socket;

    public OioSocketChannel() {
        this(new Socket());
    }

    public OioSocketChannel(Socket socket2) {
        this(null, socket2);
    }

    public OioSocketChannel(Channel channel, Socket socket2) {
        super(channel);
        this.socket = socket2;
        this.config = new DefaultOioSocketChannelConfig(this, socket2);
        try {
            if (socket2.isConnected()) {
                activate(socket2.getInputStream(), socket2.getOutputStream());
            }
            socket2.setSoTimeout(1000);
        } catch (Exception e) {
            throw new ChannelException("failed to initialize a socket", e);
        } catch (Throwable th) {
            try {
                socket2.close();
            } catch (IOException e2) {
                logger.warn("Failed to close a socket.", (Throwable) e2);
            }
            throw th;
        }
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public OioSocketChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return !this.socket.isClosed() && this.socket.isConnected();
    }

    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown() || !isActive();
    }

    public boolean isInputShutdown() {
        return this.socket.isInputShutdown() || !isActive();
    }

    public boolean isShutdown() {
        return (this.socket.isInputShutdown() && this.socket.isOutputShutdown()) || !isActive();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf byteBuf) throws Exception {
        if (this.socket.isClosed()) {
            return -1;
        }
        try {
            return super.doReadBytes(byteBuf);
        } catch (SocketTimeoutException unused) {
            return 0;
        }
    }

    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            shutdownOutput0(channelPromise);
        } else {
            eventLoop.execute(new Runnable() {
                public void run() {
                    OioSocketChannel.this.shutdownOutput0(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void shutdownOutput0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdownOutput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            shutdownInput0(channelPromise);
        } else {
            eventLoop.execute(new Runnable() {
                public void run() {
                    OioSocketChannel.this.shutdownInput0(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void shutdownInput0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdownInput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            shutdown0(channelPromise);
        } else {
            eventLoop.execute(new Runnable() {
                public void run() {
                    OioSocketChannel.this.shutdown0(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void shutdown0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdownOutput();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.socket.shutdownInput();
            if (th == null) {
                channelPromise.setSuccess();
            } else {
                channelPromise.setFailure(th);
            }
        } catch (Throwable th2) {
            if (th == null) {
                channelPromise.setFailure(th2);
            } else {
                logger.debug("Exception suppressed because a previous exception occurred.", th2);
                channelPromise.setFailure(th);
            }
        }
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
        SocketUtils.bind(this.socket, socketAddress);
    }

    /* access modifiers changed from: protected */
    public void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            SocketUtils.bind(this.socket, socketAddress2);
        }
        try {
            SocketUtils.connect(this.socket, socketAddress, config().getConnectTimeoutMillis());
            activate(this.socket.getInputStream(), this.socket.getOutputStream());
        } catch (SocketTimeoutException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("connection timed out: ");
            sb.append(socketAddress);
            ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException(sb.toString());
            connectTimeoutException.setStackTrace(e.getStackTrace());
            throw connectTimeoutException;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        this.socket.close();
    }

    /* access modifiers changed from: protected */
    public boolean checkInputShutdown() {
        if (!isInputShutdown()) {
            return false;
        }
        try {
            Thread.sleep((long) config().getSoTimeout());
        } catch (Throwable unused) {
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setReadPending(boolean z) {
        super.setReadPending(z);
    }

    /* access modifiers changed from: 0000 */
    public final void clearReadPending0() {
        clearReadPending();
    }
}
