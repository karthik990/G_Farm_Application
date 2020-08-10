package p043io.netty.channel;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.Attribute;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.AttributeMap;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.channel.ChannelHandlerContext */
public interface ChannelHandlerContext extends AttributeMap, ChannelInboundInvoker, ChannelOutboundInvoker {
    ByteBufAllocator alloc();

    @Deprecated
    <T> Attribute<T> attr(AttributeKey<T> attributeKey);

    Channel channel();

    EventExecutor executor();

    ChannelHandlerContext fireChannelActive();

    ChannelHandlerContext fireChannelInactive();

    ChannelHandlerContext fireChannelRead(Object obj);

    ChannelHandlerContext fireChannelReadComplete();

    ChannelHandlerContext fireChannelRegistered();

    ChannelHandlerContext fireChannelUnregistered();

    ChannelHandlerContext fireChannelWritabilityChanged();

    ChannelHandlerContext fireExceptionCaught(Throwable th);

    ChannelHandlerContext fireUserEventTriggered(Object obj);

    ChannelHandlerContext flush();

    ChannelHandler handler();

    @Deprecated
    <T> boolean hasAttr(AttributeKey<T> attributeKey);

    boolean isRemoved();

    String name();

    ChannelPipeline pipeline();

    ChannelHandlerContext read();
}
