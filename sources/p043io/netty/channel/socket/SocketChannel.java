package p043io.netty.channel.socket;

import java.net.InetSocketAddress;

/* renamed from: io.netty.channel.socket.SocketChannel */
public interface SocketChannel extends DuplexChannel {
    SocketChannelConfig config();

    InetSocketAddress localAddress();

    ServerSocketChannel parent();

    InetSocketAddress remoteAddress();
}
