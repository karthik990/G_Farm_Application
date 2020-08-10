package p043io.netty.channel.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.MessageSizeEstimator;
import p043io.netty.channel.RecvByteBufAllocator;
import p043io.netty.channel.WriteBufferWaterMark;

/* renamed from: io.netty.channel.socket.DatagramChannelConfig */
public interface DatagramChannelConfig extends ChannelConfig {
    InetAddress getInterface();

    NetworkInterface getNetworkInterface();

    int getReceiveBufferSize();

    int getSendBufferSize();

    int getTimeToLive();

    int getTrafficClass();

    boolean isBroadcast();

    boolean isLoopbackModeDisabled();

    boolean isReuseAddress();

    DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    DatagramChannelConfig setAutoClose(boolean z);

    DatagramChannelConfig setAutoRead(boolean z);

    DatagramChannelConfig setBroadcast(boolean z);

    DatagramChannelConfig setConnectTimeoutMillis(int i);

    DatagramChannelConfig setInterface(InetAddress inetAddress);

    DatagramChannelConfig setLoopbackModeDisabled(boolean z);

    @Deprecated
    DatagramChannelConfig setMaxMessagesPerRead(int i);

    DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

    DatagramChannelConfig setReceiveBufferSize(int i);

    DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    DatagramChannelConfig setReuseAddress(boolean z);

    DatagramChannelConfig setSendBufferSize(int i);

    DatagramChannelConfig setTimeToLive(int i);

    DatagramChannelConfig setTrafficClass(int i);

    DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    DatagramChannelConfig setWriteSpinCount(int i);
}
