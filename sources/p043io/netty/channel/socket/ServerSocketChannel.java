package p043io.netty.channel.socket;

import java.net.InetSocketAddress;
import p043io.netty.channel.ServerChannel;

/* renamed from: io.netty.channel.socket.ServerSocketChannel */
public interface ServerSocketChannel extends ServerChannel {
    ServerSocketChannelConfig config();

    InetSocketAddress localAddress();

    InetSocketAddress remoteAddress();
}
