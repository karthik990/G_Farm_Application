package p043io.netty.channel.socket;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;

/* renamed from: io.netty.channel.socket.SocketChannelConfig */
public interface SocketChannelConfig extends ChannelConfig {
    int getReceiveBufferSize();

    int getSendBufferSize();

    int getSoLinger();

    int getTrafficClass();

    boolean isAllowHalfClosure();

    boolean isKeepAlive();

    boolean isReuseAddress();

    boolean isTcpNoDelay();

    SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    SocketChannelConfig setAllowHalfClosure(boolean z);

    SocketChannelConfig setAutoClose(boolean z);

    SocketChannelConfig setAutoRead(boolean z);

    SocketChannelConfig setConnectTimeoutMillis(int i);

    SocketChannelConfig setKeepAlive(boolean z);

    @Deprecated
    SocketChannelConfig setMaxMessagesPerRead(int i);

    SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    SocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    SocketChannelConfig setReceiveBufferSize(int i);

    SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    SocketChannelConfig setReuseAddress(boolean z);

    SocketChannelConfig setSendBufferSize(int i);

    SocketChannelConfig setSoLinger(int i);

    SocketChannelConfig setTcpNoDelay(boolean z);

    SocketChannelConfig setTrafficClass(int i);

    SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    SocketChannelConfig setWriteSpinCount(int i);
}
