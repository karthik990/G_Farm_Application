package p043io.netty.channel.socket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.MembershipKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.AddressedEnvelope;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.DefaultAddressedEnvelope;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.channel.nio.AbstractNioMessageChannel;
import p043io.netty.channel.socket.DatagramChannel;
import p043io.netty.channel.socket.DatagramChannelConfig;
import p043io.netty.channel.socket.DatagramPacket;
import p043io.netty.channel.socket.InternetProtocolFamily;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SocketUtils;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.socket.nio.NioDatagramChannel */
public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final String EXPECTED_TYPES;
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private final DatagramChannelConfig config;
    private Map<InetAddress, List<MembershipKey>> memberships;

    /* access modifiers changed from: protected */
    public boolean continueOnWriteError() {
        return true;
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(" (expected: ");
        sb.append(StringUtil.simpleClassName(DatagramPacket.class));
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

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openDatagramChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        if (internetProtocolFamily == null) {
            return newSocket(selectorProvider);
        }
        checkJavaVersion();
        try {
            return selectorProvider.openDatagramChannel(ProtocolFamilyConverter.convert(internetProtocolFamily));
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static void checkJavaVersion() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }

    public NioDatagramChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }

    public NioDatagramChannel(InternetProtocolFamily internetProtocolFamily) {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER, internetProtocolFamily));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        this(newSocket(selectorProvider, internetProtocolFamily));
    }

    public NioDatagramChannel(java.nio.channels.DatagramChannel datagramChannel) {
        super(null, datagramChannel, 1);
        this.config = new NioDatagramChannelConfig(this, datagramChannel);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        java.nio.channels.DatagramChannel javaChannel = javaChannel();
        return javaChannel.isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || javaChannel.socket().isBound());
    }

    public boolean isConnected() {
        return javaChannel().isConnected();
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel) super.javaChannel();
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
            javaChannel().socket().bind(socketAddress);
        }
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            doBind0(socketAddress2);
        }
        try {
            javaChannel().connect(socketAddress);
            return true;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() throws Exception {
        throw new Error();
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        javaChannel().disconnect();
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> list) throws Exception {
        java.nio.channels.DatagramChannel javaChannel = javaChannel();
        DatagramChannelConfig config2 = config();
        Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        ByteBuf allocate = recvBufAllocHandle.allocate(config2.getAllocator());
        recvBufAllocHandle.attemptedBytesRead(allocate.writableBytes());
        try {
            ByteBuffer internalNioBuffer = allocate.internalNioBuffer(allocate.writerIndex(), allocate.writableBytes());
            int position = internalNioBuffer.position();
            InetSocketAddress inetSocketAddress = (InetSocketAddress) javaChannel.receive(internalNioBuffer);
            if (inetSocketAddress == null) {
                return 0;
            }
            recvBufAllocHandle.lastBytesRead(internalNioBuffer.position() - position);
            list.add(new DatagramPacket(allocate.writerIndex(allocate.writerIndex() + recvBufAllocHandle.lastBytesRead()), localAddress(), inetSocketAddress));
            return 1;
        } catch (Throwable th) {
            PlatformDependent.throwException(th);
            return -1;
        } finally {
            allocate.release();
        }
    }

    /* access modifiers changed from: protected */
    public boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SocketAddress socketAddress;
        ByteBuf byteBuf;
        int i;
        if (obj instanceof AddressedEnvelope) {
            AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
            socketAddress = addressedEnvelope.recipient();
            byteBuf = (ByteBuf) addressedEnvelope.content();
        } else {
            byteBuf = (ByteBuf) obj;
            socketAddress = null;
        }
        int readableBytes = byteBuf.readableBytes();
        boolean z = true;
        if (readableBytes == 0) {
            return true;
        }
        ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), readableBytes);
        if (socketAddress != null) {
            i = javaChannel().send(internalNioBuffer, socketAddress);
        } else {
            i = javaChannel().write(internalNioBuffer);
        }
        if (i <= 0) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object obj) {
        if (obj instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket) obj;
            ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
            if (isSingleDirectBuffer(byteBuf)) {
                return datagramPacket;
            }
            return new DatagramPacket(newDirectBuffer(datagramPacket, byteBuf), (InetSocketAddress) datagramPacket.recipient());
        } else if (obj instanceof ByteBuf) {
            ByteBuf byteBuf2 = (ByteBuf) obj;
            if (isSingleDirectBuffer(byteBuf2)) {
                return byteBuf2;
            }
            return newDirectBuffer(byteBuf2);
        } else {
            if (obj instanceof AddressedEnvelope) {
                AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
                if (addressedEnvelope.content() instanceof ByteBuf) {
                    ByteBuf byteBuf3 = (ByteBuf) addressedEnvelope.content();
                    if (isSingleDirectBuffer(byteBuf3)) {
                        return addressedEnvelope;
                    }
                    return new DefaultAddressedEnvelope(newDirectBuffer(addressedEnvelope, byteBuf3), addressedEnvelope.recipient());
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unsupported message type: ");
            sb.append(StringUtil.simpleClassName(obj));
            sb.append(EXPECTED_TYPES);
            throw new UnsupportedOperationException(sb.toString());
        }
    }

    private static boolean isSingleDirectBuffer(ByteBuf byteBuf) {
        return byteBuf.isDirect() && byteBuf.nioBufferCount() == 1;
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return joinGroup(inetAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return joinGroup(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, channelPromise);
        } catch (SocketException e) {
            channelPromise.setFailure(e);
            return channelPromise;
        }
    }

    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return joinGroup(inetSocketAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return joinGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return joinGroup(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        MembershipKey membershipKey;
        checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            if (inetAddress2 == null) {
                try {
                    membershipKey = javaChannel().join(inetAddress, networkInterface);
                } catch (Throwable th) {
                    channelPromise.setFailure(th);
                }
            } else {
                membershipKey = javaChannel().join(inetAddress, networkInterface, inetAddress2);
            }
            synchronized (this) {
                List list = null;
                if (this.memberships == null) {
                    this.memberships = new HashMap();
                } else {
                    list = (List) this.memberships.get(inetAddress);
                }
                if (list == null) {
                    list = new ArrayList();
                    this.memberships.put(inetAddress, list);
                }
                list.add(membershipKey);
            }
            channelPromise.setSuccess();
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return leaveGroup(inetAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return leaveGroup(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, channelPromise);
        } catch (SocketException e) {
            channelPromise.setFailure(e);
            return channelPromise;
        }
    }

    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return leaveGroup(inetSocketAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return leaveGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return leaveGroup(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            synchronized (this) {
                if (this.memberships != null) {
                    List list = (List) this.memberships.get(inetAddress);
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            MembershipKey membershipKey = (MembershipKey) it.next();
                            if (networkInterface.equals(membershipKey.networkInterface()) && ((inetAddress2 == null && membershipKey.sourceAddress() == null) || (inetAddress2 != null && inetAddress2.equals(membershipKey.sourceAddress())))) {
                                membershipKey.drop();
                                it.remove();
                            }
                        }
                        if (list.isEmpty()) {
                            this.memberships.remove(inetAddress);
                        }
                    }
                }
            }
            channelPromise.setSuccess();
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return block(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface != null) {
            synchronized (this) {
                if (this.memberships != null) {
                    for (MembershipKey membershipKey : (List) this.memberships.get(inetAddress)) {
                        if (networkInterface.equals(membershipKey.networkInterface())) {
                            try {
                                membershipKey.block(inetAddress2);
                            } catch (IOException e) {
                                channelPromise.setFailure(e);
                            }
                        }
                    }
                }
            }
            channelPromise.setSuccess();
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return block(inetAddress, inetAddress2, newPromise());
    }

    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        try {
            return block(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), inetAddress2, channelPromise);
        } catch (SocketException e) {
            channelPromise.setFailure(e);
            return channelPromise;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setReadPending(boolean z) {
        super.setReadPending(z);
    }

    /* access modifiers changed from: 0000 */
    public void clearReadPending0() {
        clearReadPending();
    }

    /* access modifiers changed from: protected */
    public boolean closeOnReadError(Throwable th) {
        if (th instanceof SocketException) {
            return false;
        }
        return super.closeOnReadError(th);
    }
}
