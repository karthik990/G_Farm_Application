package p043io.netty.channel;

import java.net.SocketAddress;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.util.AttributeMap;

/* renamed from: io.netty.channel.Channel */
public interface Channel extends AttributeMap, ChannelOutboundInvoker, Comparable<Channel> {

    /* renamed from: io.netty.channel.Channel$Unsafe */
    public interface Unsafe {
        void beginRead();

        void bind(SocketAddress socketAddress, ChannelPromise channelPromise);

        void close(ChannelPromise channelPromise);

        void closeForcibly();

        void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise);

        void deregister(ChannelPromise channelPromise);

        void disconnect(ChannelPromise channelPromise);

        void flush();

        SocketAddress localAddress();

        ChannelOutboundBuffer outboundBuffer();

        Handle recvBufAllocHandle();

        void register(EventLoop eventLoop, ChannelPromise channelPromise);

        SocketAddress remoteAddress();

        ChannelPromise voidPromise();

        void write(Object obj, ChannelPromise channelPromise);
    }

    ByteBufAllocator alloc();

    long bytesBeforeUnwritable();

    long bytesBeforeWritable();

    ChannelFuture closeFuture();

    ChannelConfig config();

    EventLoop eventLoop();

    Channel flush();

    /* renamed from: id */
    ChannelId mo66192id();

    boolean isActive();

    boolean isOpen();

    boolean isRegistered();

    boolean isWritable();

    SocketAddress localAddress();

    ChannelMetadata metadata();

    Channel parent();

    ChannelPipeline pipeline();

    Channel read();

    SocketAddress remoteAddress();

    Unsafe unsafe();
}
