package p043io.netty.channel.socket;

import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.channel.socket.DefaultSocketChannelConfig */
public class DefaultSocketChannelConfig extends DefaultChannelConfig implements SocketChannelConfig {
    private volatile boolean allowHalfClosure;
    protected final Socket javaSocket;

    public DefaultSocketChannelConfig(SocketChannel socketChannel, Socket socket) {
        super(socketChannel);
        if (socket != null) {
            this.javaSocket = socket;
            if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
                try {
                    setTcpNoDelay(true);
                } catch (Exception unused) {
                }
            }
        } else {
            throw new NullPointerException("javaSocket");
        }
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE);
    }

    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return Integer.valueOf(getSendBufferSize());
        }
        if (channelOption == ChannelOption.TCP_NODELAY) {
            return Boolean.valueOf(isTcpNoDelay());
        }
        if (channelOption == ChannelOption.SO_KEEPALIVE) {
            return Boolean.valueOf(isKeepAlive());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return Boolean.valueOf(isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return Integer.valueOf(getSoLinger());
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return Integer.valueOf(getTrafficClass());
        }
        if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            return Boolean.valueOf(isAllowHalfClosure());
        }
        return super.getOption(channelOption);
    }

    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.TCP_NODELAY) {
            setTcpNoDelay(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.SO_KEEPALIVE) {
            setKeepAlive(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.SO_LINGER) {
            setSoLinger(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) t).intValue());
        } else if (channelOption != ChannelOption.ALLOW_HALF_CLOSURE) {
            return super.setOption(channelOption, t);
        } else {
            setAllowHalfClosure(((Boolean) t).booleanValue());
        }
        return true;
    }

    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
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

    public int getSoLinger() {
        try {
            return this.javaSocket.getSoLinger();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public boolean isKeepAlive() {
        try {
            return this.javaSocket.getKeepAlive();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public boolean isTcpNoDelay() {
        try {
            return this.javaSocket.getTcpNoDelay();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setKeepAlive(boolean z) {
        try {
            this.javaSocket.setKeepAlive(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        this.javaSocket.setPerformancePreferences(i, i2, i3);
        return this;
    }

    public SocketChannelConfig setReceiveBufferSize(int i) {
        try {
            this.javaSocket.setReceiveBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setReuseAddress(boolean z) {
        try {
            this.javaSocket.setReuseAddress(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setSendBufferSize(int i) {
        try {
            this.javaSocket.setSendBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setSoLinger(int i) {
        if (i < 0) {
            try {
                this.javaSocket.setSoLinger(false, 0);
            } catch (SocketException e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            this.javaSocket.setSoLinger(true, i);
        }
        return this;
    }

    public SocketChannelConfig setTcpNoDelay(boolean z) {
        try {
            this.javaSocket.setTcpNoDelay(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public SocketChannelConfig setTrafficClass(int i) {
        try {
            this.javaSocket.setTrafficClass(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }

    public SocketChannelConfig setAllowHalfClosure(boolean z) {
        this.allowHalfClosure = z;
        return this;
    }

    public SocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Deprecated
    public SocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    public SocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    public SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    public SocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    public SocketChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    public SocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    public SocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    public SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
