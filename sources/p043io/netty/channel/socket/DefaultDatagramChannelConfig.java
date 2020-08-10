package p043io.netty.channel.socket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.FixedRecvByteBufAllocator;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.socket.DefaultDatagramChannelConfig */
public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
    private volatile boolean activeOnOpen;
    private final DatagramSocket javaSocket;

    public DefaultDatagramChannelConfig(DatagramChannel datagramChannel, DatagramSocket datagramSocket) {
        super(datagramChannel, new FixedRecvByteBufAllocator(2048));
        if (datagramSocket != null) {
            this.javaSocket = datagramSocket;
            return;
        }
        throw new NullPointerException("javaSocket");
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION);
    }

    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_BROADCAST) {
            return Boolean.valueOf(isBroadcast());
        }
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return Integer.valueOf(getSendBufferSize());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return Boolean.valueOf(isReuseAddress());
        }
        if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return Boolean.valueOf(isLoopbackModeDisabled());
        }
        if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            return getInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            return getNetworkInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            return Integer.valueOf(getTimeToLive());
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return Integer.valueOf(getTrafficClass());
        }
        if (channelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return Boolean.valueOf(this.activeOnOpen);
        }
        return super.getOption(channelOption);
    }

    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.SO_BROADCAST) {
            setBroadcast(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            setLoopbackModeDisabled(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            setInterface((InetAddress) t);
        } else if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            setNetworkInterface((NetworkInterface) t);
        } else if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            setTimeToLive(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) t).intValue());
        } else if (channelOption != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return super.setOption(channelOption, t);
        } else {
            setActiveOnOpen(((Boolean) t).booleanValue());
        }
        return true;
    }

    private void setActiveOnOpen(boolean z) {
        if (!this.channel.isRegistered()) {
            this.activeOnOpen = z;
            return;
        }
        throw new IllegalStateException("Can only changed before channel was registered");
    }

    public boolean isBroadcast() {
        try {
            return this.javaSocket.getBroadcast();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setBroadcast(boolean z) {
        if (z) {
            try {
                if (!this.javaSocket.getLocalAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
                    InternalLogger internalLogger = logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to ");
                    sb.append(this.javaSocket.getLocalSocketAddress());
                    sb.append('.');
                    internalLogger.warn(sb.toString());
                }
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        }
        this.javaSocket.setBroadcast(z);
        return this;
    }

    public InetAddress getInterface() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getInterface();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setInterface(inetAddress);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isLoopbackModeDisabled() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getLoopbackMode();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean z) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setLoopbackMode(z);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public NetworkInterface getNetworkInterface() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getNetworkInterface();
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setNetworkInterface(networkInterface);
                return this;
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setReuseAddress(boolean z) {
        try {
            this.javaSocket.setReuseAddress(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setReceiveBufferSize(int i) {
        try {
            this.javaSocket.setReceiveBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setSendBufferSize(int i) {
        try {
            this.javaSocket.setSendBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getTimeToLive() {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getTimeToLive();
            } catch (IOException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramChannelConfig setTimeToLive(int i) {
        DatagramSocket datagramSocket = this.javaSocket;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setTimeToLive(i);
                return this;
            } catch (IOException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setTrafficClass(int i) {
        try {
            this.javaSocket.setTrafficClass(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public DatagramChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    public DatagramChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Deprecated
    public DatagramChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    public DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    public DatagramChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    public DatagramChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    public DatagramChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    public DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
