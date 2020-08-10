package p043io.netty.channel.socket;

import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Map;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.util.NetUtil;

/* renamed from: io.netty.channel.socket.DefaultServerSocketChannelConfig */
public class DefaultServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig {
    private volatile int backlog = NetUtil.SOMAXCONN;
    protected final ServerSocket javaSocket;

    public DefaultServerSocketChannelConfig(ServerSocketChannel serverSocketChannel, ServerSocket serverSocket) {
        super(serverSocketChannel);
        if (serverSocket != null) {
            this.javaSocket = serverSocket;
            return;
        }
        throw new NullPointerException("javaSocket");
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG);
    }

    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return Boolean.valueOf(isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return Integer.valueOf(getBacklog());
        }
        return super.getOption(channelOption);
    }

    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) t).booleanValue());
        } else if (channelOption != ChannelOption.SO_BACKLOG) {
            return super.setOption(channelOption, t);
        } else {
            setBacklog(((Integer) t).intValue());
        }
        return true;
    }

    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public ServerSocketChannelConfig setReuseAddress(boolean z) {
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

    public ServerSocketChannelConfig setReceiveBufferSize(int i) {
        try {
            this.javaSocket.setReceiveBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public ServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        this.javaSocket.setPerformancePreferences(i, i2, i3);
        return this;
    }

    public int getBacklog() {
        return this.backlog;
    }

    public ServerSocketChannelConfig setBacklog(int i) {
        if (i >= 0) {
            this.backlog = i;
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("backlog: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public ServerSocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Deprecated
    public ServerSocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    public ServerSocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    public ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    public ServerSocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    public ServerSocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    public ServerSocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
