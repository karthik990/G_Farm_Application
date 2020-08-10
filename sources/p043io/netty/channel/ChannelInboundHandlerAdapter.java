package p043io.netty.channel;

/* renamed from: io.netty.channel.ChannelInboundHandlerAdapter */
public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelInactive();
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        channelHandlerContext.fireChannelRead(obj);
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelReadComplete();
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        channelHandlerContext.fireUserEventTriggered(obj);
    }

    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        channelHandlerContext.fireExceptionCaught(th);
    }
}
