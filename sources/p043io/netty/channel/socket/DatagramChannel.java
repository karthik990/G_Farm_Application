package p043io.netty.channel.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;

/* renamed from: io.netty.channel.socket.DatagramChannel */
public interface DatagramChannel extends Channel {
    ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2);

    ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise);

    ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2);

    ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise);

    DatagramChannelConfig config();

    boolean isConnected();

    ChannelFuture joinGroup(InetAddress inetAddress);

    ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise);

    ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2);

    ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise);

    ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface);

    ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise);

    ChannelFuture leaveGroup(InetAddress inetAddress);

    ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise);

    ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2);

    ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise);

    ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface);

    ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise);

    InetSocketAddress localAddress();

    InetSocketAddress remoteAddress();
}
