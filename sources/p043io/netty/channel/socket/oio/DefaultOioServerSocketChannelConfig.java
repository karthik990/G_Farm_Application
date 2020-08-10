package p043io.netty.channel.socket.oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.channel.socket.DefaultServerSocketChannelConfig;
import p043io.netty.channel.socket.ServerSocketChannel;

/* renamed from: io.netty.channel.socket.oio.DefaultOioServerSocketChannelConfig */
public class DefaultOioServerSocketChannelConfig extends DefaultServerSocketChannelConfig implements OioServerSocketChannelConfig {
    @Deprecated
    public DefaultOioServerSocketChannelConfig(ServerSocketChannel serverSocketChannel, ServerSocket serverSocket) {
        super(serverSocketChannel, serverSocket);
    }

    DefaultOioServerSocketChannelConfig(OioServerSocketChannel oioServerSocketChannel, ServerSocket serverSocket) {
        super(oioServerSocketChannel, serverSocket);
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }

    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_TIMEOUT) {
            return Integer.valueOf(getSoTimeout());
        }
        return super.getOption(channelOption);
    }

    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption != ChannelOption.SO_TIMEOUT) {
            return super.setOption(channelOption, t);
        }
        setSoTimeout(((Integer) t).intValue());
        return true;
    }

    public OioServerSocketChannelConfig setSoTimeout(int i) {
        try {
            this.javaSocket.setSoTimeout(i);
            return this;
        } catch (IOException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public int getSoTimeout() {
        try {
            return this.javaSocket.getSoTimeout();
        } catch (IOException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public OioServerSocketChannelConfig setBacklog(int i) {
        super.setBacklog(i);
        return this;
    }

    public OioServerSocketChannelConfig setReuseAddress(boolean z) {
        super.setReuseAddress(z);
        return this;
    }

    public OioServerSocketChannelConfig setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
        return this;
    }

    public OioServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        super.setPerformancePreferences(i, i2, i3);
        return this;
    }

    public OioServerSocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Deprecated
    public OioServerSocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    public OioServerSocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    public OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    public OioServerSocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
        if (this.channel instanceof OioServerSocketChannel) {
            ((OioServerSocketChannel) this.channel).clearReadPending0();
        }
    }

    public OioServerSocketChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    public OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
