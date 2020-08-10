package p043io.netty.channel.socket;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;

/* renamed from: io.netty.channel.socket.ServerSocketChannelConfig */
public interface ServerSocketChannelConfig extends ChannelConfig {
    int getBacklog();

    int getReceiveBufferSize();

    boolean isReuseAddress();

    ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    ServerSocketChannelConfig setAutoRead(boolean z);

    ServerSocketChannelConfig setBacklog(int i);

    ServerSocketChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    ServerSocketChannelConfig setMaxMessagesPerRead(int i);

    ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    ServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    ServerSocketChannelConfig setReceiveBufferSize(int i);

    ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    ServerSocketChannelConfig setReuseAddress(boolean z);

    ServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    ServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    ServerSocketChannelConfig setWriteSpinCount(int i);
}
