package p043io.netty.channel.socket.oio;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.channel.socket.ServerSocketChannelConfig;

/* renamed from: io.netty.channel.socket.oio.OioServerSocketChannelConfig */
public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {
    int getSoTimeout();

    OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioServerSocketChannelConfig setAutoClose(boolean z);

    OioServerSocketChannelConfig setAutoRead(boolean z);

    OioServerSocketChannelConfig setBacklog(int i);

    OioServerSocketChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    OioServerSocketChannelConfig setMaxMessagesPerRead(int i);

    OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    OioServerSocketChannelConfig setReceiveBufferSize(int i);

    OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioServerSocketChannelConfig setReuseAddress(boolean z);

    OioServerSocketChannelConfig setSoTimeout(int i);

    OioServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    OioServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    OioServerSocketChannelConfig setWriteSpinCount(int i);
}
