package p043io.netty.channel.socket.oio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.NotYetConnectedException;
import java.util.List;
import java.util.Locale;
import kotlin.text.Typography;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.AddressedEnvelope;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.channel.oio.AbstractOioMessageChannel;
import p043io.netty.channel.socket.DatagramChannel;
import p043io.netty.channel.socket.DatagramChannelConfig;
import p043io.netty.channel.socket.DefaultDatagramChannelConfig;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.socket.oio.OioDatagramChannel */
public class OioDatagramChannel extends AbstractOioMessageChannel implements DatagramChannel {
    private static final String EXPECTED_TYPES;
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
    private final DatagramChannelConfig config;
    private final MulticastSocket socket;
    private final DatagramPacket tmpPacket;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(" (expected: ");
        sb.append(StringUtil.simpleClassName(p043io.netty.channel.socket.DatagramPacket.class));
        String str = ", ";
        sb.append(str);
        sb.append(StringUtil.simpleClassName(AddressedEnvelope.class));
        sb.append(Typography.less);
        sb.append(StringUtil.simpleClassName(ByteBuf.class));
        sb.append(str);
        sb.append(StringUtil.simpleClassName(SocketAddress.class));
        sb.append(">, ");
        sb.append(StringUtil.simpleClassName(ByteBuf.class));
        sb.append(')');
        EXPECTED_TYPES = sb.toString();
    }

    private static MulticastSocket newSocket() {
        try {
            return new MulticastSocket(null);
        } catch (Exception e) {
            throw new ChannelException("failed to create a new socket", e);
        }
    }

    public OioDatagramChannel() {
        this(newSocket());
    }

    public OioDatagramChannel(MulticastSocket multicastSocket) {
        super(null);
        this.tmpPacket = new DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
        try {
            multicastSocket.setSoTimeout(1000);
            multicastSocket.setBroadcast(false);
            this.socket = multicastSocket;
            this.config = new DefaultDatagramChannelConfig(this, multicastSocket);
        } catch (SocketException e) {
            throw new ChannelException("Failed to configure the datagram socket timeout.", e);
        } catch (Throwable th) {
            multicastSocket.close();
            throw th;
        }
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket.isBound());
    }

    public boolean isConnected() {
        return this.socket.isConnected();
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
        this.socket.bind(socketAddress);
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    /* access modifiers changed from: protected */
    public void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.socket.bind(socketAddress2);
        }
        try {
            this.socket.connect(socketAddress);
            return;
        } catch (Throwable th) {
            logger.warn("Failed to close a socket.", th);
        }
        throw th;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        this.socket.disconnect();
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        this.socket.close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> list) throws Exception {
        DatagramChannelConfig config2 = config();
        Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        ByteBuf heapBuffer = config2.getAllocator().heapBuffer(recvBufAllocHandle.guess());
        try {
            this.tmpPacket.setData(heapBuffer.array(), heapBuffer.arrayOffset(), heapBuffer.capacity());
            this.socket.receive(this.tmpPacket);
            InetSocketAddress inetSocketAddress = (InetSocketAddress) this.tmpPacket.getSocketAddress();
            recvBufAllocHandle.lastBytesRead(this.tmpPacket.getLength());
            list.add(new p043io.netty.channel.socket.DatagramPacket(heapBuffer.writerIndex(recvBufAllocHandle.lastBytesRead()), localAddress(), inetSocketAddress));
            return 1;
        } catch (SocketTimeoutException unused) {
            heapBuffer.release();
            return 0;
        } catch (SocketException e) {
            if (e.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                heapBuffer.release();
                return -1;
            }
            throw e;
        } catch (Throwable th) {
            heapBuffer.release();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SocketAddress socketAddress;
        ByteBuf byteBuf;
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                if (current instanceof AddressedEnvelope) {
                    AddressedEnvelope addressedEnvelope = (AddressedEnvelope) current;
                    socketAddress = addressedEnvelope.recipient();
                    byteBuf = (ByteBuf) addressedEnvelope.content();
                } else {
                    byteBuf = (ByteBuf) current;
                    socketAddress = null;
                }
                int readableBytes = byteBuf.readableBytes();
                if (socketAddress != null) {
                    try {
                        this.tmpPacket.setSocketAddress(socketAddress);
                    } catch (IOException e) {
                        channelOutboundBuffer.remove(e);
                    }
                } else if (!isConnected()) {
                    throw new NotYetConnectedException();
                }
                if (byteBuf.hasArray()) {
                    this.tmpPacket.setData(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.tmpPacket.setData(bArr);
                }
                this.socket.send(this.tmpPacket);
                channelOutboundBuffer.remove();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object obj) {
        if ((obj instanceof p043io.netty.channel.socket.DatagramPacket) || (obj instanceof ByteBuf)) {
            return obj;
        }
        if ((obj instanceof AddressedEnvelope) && (((AddressedEnvelope) obj).content() instanceof ByteBuf)) {
            return obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unsupported message type: ");
        sb.append(StringUtil.simpleClassName(obj));
        sb.append(EXPECTED_TYPES);
        throw new UnsupportedOperationException(sb.toString());
    }

    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return joinGroup(inetAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        ensureBound();
        try {
            this.socket.joinGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException e) {
            channelPromise.setFailure(e);
        }
        return channelPromise;
    }

    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return joinGroup(inetSocketAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        ensureBound();
        try {
            this.socket.joinGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException e) {
            channelPromise.setFailure(e);
        }
        return channelPromise;
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    private void ensureBound() {
        if (!isActive()) {
            StringBuilder sb = new StringBuilder();
            sb.append(DatagramChannel.class.getName());
            sb.append(" must be bound to join a group.");
            throw new IllegalStateException(sb.toString());
        }
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return leaveGroup(inetAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            this.socket.leaveGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException e) {
            channelPromise.setFailure(e);
        }
        return channelPromise;
    }

    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return leaveGroup(inetSocketAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        try {
            this.socket.leaveGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException e) {
            channelPromise.setFailure(e);
        }
        return channelPromise;
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }
}
