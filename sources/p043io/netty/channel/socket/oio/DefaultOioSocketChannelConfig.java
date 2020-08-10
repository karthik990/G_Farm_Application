package p043io.netty.channel.socket.oio;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.channel.socket.DefaultSocketChannelConfig;
import p043io.netty.channel.socket.SocketChannel;

/* renamed from: io.netty.channel.socket.oio.DefaultOioSocketChannelConfig */
public class DefaultOioSocketChannelConfig extends DefaultSocketChannelConfig implements OioSocketChannelConfig {
    @Deprecated
    public DefaultOioSocketChannelConfig(SocketChannel socketChannel, Socket socket) {
        super(socketChannel, socket);
    }

    DefaultOioSocketChannelConfig(OioSocketChannel oioSocketChannel, Socket socket) {
        super(oioSocketChannel, socket);
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

    public OioSocketChannelConfig setSoTimeout(int i) {
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

    public OioSocketChannelConfig setTcpNoDelay(boolean z) {
        super.setTcpNoDelay(z);
        return this;
    }

    public OioSocketChannelConfig setSoLinger(int i) {
        super.setSoLinger(i);
        return this;
    }

    public OioSocketChannelConfig setSendBufferSize(int i) {
        super.setSendBufferSize(i);
        return this;
    }

    public OioSocketChannelConfig setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
        return this;
    }

    public OioSocketChannelConfig setKeepAlive(boolean z) {
        super.setKeepAlive(z);
        return this;
    }

    public OioSocketChannelConfig setTrafficClass(int i) {
        super.setTrafficClass(i);
        return this;
    }

    public OioSocketChannelConfig setReuseAddress(boolean z) {
        super.setReuseAddress(z);
        return this;
    }

    public OioSocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        super.setPerformancePreferences(i, i2, i3);
        return this;
    }

    public OioSocketChannelConfig setAllowHalfClosure(boolean z) {
        super.setAllowHalfClosure(z);
        return this;
    }

    public OioSocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Deprecated
    public OioSocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    public OioSocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    public OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    public OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    public OioSocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
        if (this.channel instanceof OioSocketChannel) {
            ((OioSocketChannel) this.channel).clearReadPending0();
        }
    }

    public OioSocketChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    public OioSocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    public OioSocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    public OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
