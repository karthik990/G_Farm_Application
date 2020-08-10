package p043io.netty.channel.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.FileRegion;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.channel.nio.AbstractNioByteChannel;
import p043io.netty.channel.nio.NioEventLoop;
import p043io.netty.channel.socket.DefaultSocketChannelConfig;
import p043io.netty.channel.socket.ServerSocketChannel;
import p043io.netty.channel.socket.SocketChannel;
import p043io.netty.channel.socket.SocketChannelConfig;
import p043io.netty.util.concurrent.GlobalEventExecutor;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SocketUtils;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.socket.nio.NioSocketChannel */
public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    private final SocketChannelConfig config;

    /* renamed from: io.netty.channel.socket.nio.NioSocketChannel$NioSocketChannelConfig */
    private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
        private NioSocketChannelConfig(NioSocketChannel nioSocketChannel, Socket socket) {
            super(nioSocketChannel, socket);
        }

        /* access modifiers changed from: protected */
        public void autoReadCleared() {
            NioSocketChannel.this.clearReadPending();
        }
    }

    /* renamed from: io.netty.channel.socket.nio.NioSocketChannel$NioSocketChannelUnsafe */
    private final class NioSocketChannelUnsafe extends NioByteUnsafe {
        private NioSocketChannelUnsafe() {
            super();
        }

        /* access modifiers changed from: protected */
        public Executor prepareToClose() {
            try {
                if (NioSocketChannel.this.javaChannel().isOpen() && NioSocketChannel.this.config().getSoLinger() > 0) {
                    NioSocketChannel.this.doDeregister();
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable unused) {
            }
            return null;
        }
    }

    private static java.nio.channels.SocketChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    public NioSocketChannel() {
        this(DEFAULT_SELECTOR_PROVIDER);
    }

    public NioSocketChannel(SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socketChannel) {
        this(null, socketChannel);
    }

    public NioSocketChannel(Channel channel, java.nio.channels.SocketChannel socketChannel) {
        super(channel, socketChannel);
        this.config = new NioSocketChannelConfig(this, socketChannel.socket());
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public SocketChannelConfig config() {
        return this.config;
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel) super.javaChannel();
    }

    public boolean isActive() {
        java.nio.channels.SocketChannel javaChannel = javaChannel();
        return javaChannel.isOpen() && javaChannel.isConnected();
    }

    public boolean isOutputShutdown() {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    public boolean isInputShutdown() {
        return javaChannel().socket().isInputShutdown() || !isActive();
    }

    public boolean isShutdown() {
        Socket socket = javaChannel().socket();
        return (socket.isInputShutdown() && socket.isOutputShutdown()) || !isActive();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdownOutput0(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                shutdownOutput0(channelPromise);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdownOutput0(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    /* access modifiers changed from: protected */
    public boolean isInputShutdown0() {
        return isInputShutdown();
    }

    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdownInput0(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                shutdownInput0(channelPromise);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdownInput0(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdown0(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                shutdown0(channelPromise);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdown0(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    /* access modifiers changed from: private */
    public void shutdownOutput0(ChannelPromise channelPromise) {
        try {
            shutdownOutput0();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    private void shutdownOutput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownOutput();
        } else {
            javaChannel().socket().shutdownOutput();
        }
    }

    /* access modifiers changed from: private */
    public void shutdownInput0(ChannelPromise channelPromise) {
        try {
            shutdownInput0();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    private void shutdownInput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownInput();
        } else {
            javaChannel().socket().shutdownInput();
        }
    }

    /* access modifiers changed from: private */
    public void shutdown0(ChannelPromise channelPromise) {
        try {
            shutdownOutput0();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            shutdownInput0();
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

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
        doBind0(socketAddress);
    }

    private void doBind0(SocketAddress socketAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            SocketUtils.bind(javaChannel(), socketAddress);
        } else {
            SocketUtils.bind(javaChannel().socket(), socketAddress);
        }
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            doBind0(socketAddress2);
        }
        try {
            boolean connect = SocketUtils.connect(javaChannel(), socketAddress);
            if (!connect) {
                selectionKey().interestOps(8);
            }
            return connect;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() throws Exception {
        if (!javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        super.doClose();
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf byteBuf) throws Exception {
        Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        recvBufAllocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes((ScatteringByteChannel) javaChannel(), recvBufAllocHandle.attemptedBytesRead());
    }

    /* access modifiers changed from: protected */
    public int doWriteBytes(ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes((GatheringByteChannel) javaChannel(), byteBuf.readableBytes());
    }

    /* access modifiers changed from: protected */
    public long doWriteFileRegion(FileRegion fileRegion) throws Exception {
        return fileRegion.transferTo(javaChannel(), fileRegion.transferred());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006e, code lost:
        r8 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007b A[LOOP:0: B:0:0x0000->B:33:0x007b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0076 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doWrite(p043io.netty.channel.ChannelOutboundBuffer r16) throws java.lang.Exception {
        /*
            r15 = this;
        L_0x0000:
            int r0 = r16.size()
            if (r0 != 0) goto L_0x000c
            r15.clearOpWrite()
            r1 = r15
            goto L_0x007a
        L_0x000c:
            java.nio.ByteBuffer[] r0 = r16.nioBuffers()
            int r1 = r16.nioBufferCount()
            long r2 = r16.nioBufferSize()
            java.nio.channels.SocketChannel r4 = r15.javaChannel()
            if (r1 == 0) goto L_0x007d
            r5 = 0
            r6 = 0
            r8 = 1
            if (r1 == r8) goto L_0x004a
            io.netty.channel.socket.SocketChannelConfig r9 = r15.config()
            int r9 = r9.getWriteSpinCount()
            int r9 = r9 - r8
            r10 = r6
        L_0x002e:
            if (r9 < 0) goto L_0x0046
            long r12 = r4.write(r0, r5, r1)
            int r14 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r14 != 0) goto L_0x0039
            goto L_0x006f
        L_0x0039:
            long r2 = r2 - r12
            long r10 = r10 + r12
            int r12 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r12 != 0) goto L_0x0043
            r0 = r16
            r5 = 1
            goto L_0x0048
        L_0x0043:
            int r9 = r9 + -1
            goto L_0x002e
        L_0x0046:
            r0 = r16
        L_0x0048:
            r8 = 0
            goto L_0x0071
        L_0x004a:
            r0 = r0[r5]
            io.netty.channel.socket.SocketChannelConfig r1 = r15.config()
            int r1 = r1.getWriteSpinCount()
            int r1 = r1 - r8
            r9 = r6
        L_0x0056:
            if (r1 < 0) goto L_0x006d
            int r11 = r4.write(r0)
            if (r11 != 0) goto L_0x0060
            r10 = r9
            goto L_0x006f
        L_0x0060:
            long r11 = (long) r11
            long r2 = r2 - r11
            long r9 = r9 + r11
            int r11 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r11 != 0) goto L_0x006a
            r10 = r9
            r5 = 1
            goto L_0x006e
        L_0x006a:
            int r1 = r1 + -1
            goto L_0x0056
        L_0x006d:
            r10 = r9
        L_0x006e:
            r8 = 0
        L_0x006f:
            r0 = r16
        L_0x0071:
            r0.removeBytes(r10)
            if (r5 != 0) goto L_0x007b
            r1 = r15
            r15.incompleteWrite(r8)
        L_0x007a:
            return
        L_0x007b:
            r1 = r15
            goto L_0x0000
        L_0x007d:
            r1 = r15
            r0 = r16
            super.doWrite(r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.socket.nio.NioSocketChannel.doWrite(io.netty.channel.ChannelOutboundBuffer):void");
    }

    /* access modifiers changed from: protected */
    public AbstractNioUnsafe newUnsafe() {
        return new NioSocketChannelUnsafe();
    }
}
