package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.socket.DatagramPacket;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.codec.DatagramPacketDecoder */
public class DatagramPacketDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final MessageToMessageDecoder<ByteBuf> decoder;

    public DatagramPacketDecoder(MessageToMessageDecoder<ByteBuf> messageToMessageDecoder) {
        this.decoder = (MessageToMessageDecoder) ObjectUtil.checkNotNull(messageToMessageDecoder, "decoder");
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        if (obj instanceof DatagramPacket) {
            return this.decoder.acceptInboundMessage(((DatagramPacket) obj).content());
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        this.decoder.decode(channelHandlerContext, datagramPacket.content(), list);
    }

    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelRegistered(channelHandlerContext);
    }

    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelUnregistered(channelHandlerContext);
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelActive(channelHandlerContext);
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelInactive(channelHandlerContext);
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelReadComplete(channelHandlerContext);
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        this.decoder.userEventTriggered(channelHandlerContext, obj);
    }

    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelWritabilityChanged(channelHandlerContext);
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        this.decoder.exceptionCaught(channelHandlerContext, th);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.handlerAdded(channelHandlerContext);
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.handlerRemoved(channelHandlerContext);
    }

    public boolean isSharable() {
        return this.decoder.isSharable();
    }
}
