package p043io.netty.channel.socket.oio;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;
import p043io.netty.channel.socket.SocketChannelConfig;

/* renamed from: io.netty.channel.socket.oio.OioSocketChannelConfig */
public interface OioSocketChannelConfig extends SocketChannelConfig {
    int getSoTimeout();

    OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioSocketChannelConfig setAllowHalfClosure(boolean z);

    OioSocketChannelConfig setAutoClose(boolean z);

    OioSocketChannelConfig setAutoRead(boolean z);

    OioSocketChannelConfig setConnectTimeoutMillis(int i);

    OioSocketChannelConfig setKeepAlive(boolean z);

    @Deprecated
    OioSocketChannelConfig setMaxMessagesPerRead(int i);

    OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    OioSocketChannelConfig setReceiveBufferSize(int i);

    OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioSocketChannelConfig setReuseAddress(boolean z);

    OioSocketChannelConfig setSendBufferSize(int i);

    OioSocketChannelConfig setSoLinger(int i);

    OioSocketChannelConfig setSoTimeout(int i);

    OioSocketChannelConfig setTcpNoDelay(boolean z);

    OioSocketChannelConfig setTrafficClass(int i);

    OioSocketChannelConfig setWriteBufferHighWaterMark(int i);

    OioSocketChannelConfig setWriteBufferLowWaterMark(int i);

    OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    OioSocketChannelConfig setWriteSpinCount(int i);
}
