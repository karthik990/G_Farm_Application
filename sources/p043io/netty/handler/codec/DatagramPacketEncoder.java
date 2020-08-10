package p043io.netty.handler.codec;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.AddressedEnvelope;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.socket.DatagramPacket;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.handler.codec.DatagramPacketEncoder */
public class DatagramPacketEncoder<M> extends MessageToMessageEncoder<AddressedEnvelope<M, InetSocketAddress>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MessageToMessageEncoder<? super M> encoder;

    public DatagramPacketEncoder(MessageToMessageEncoder<? super M> messageToMessageEncoder) {
        this.encoder = (MessageToMessageEncoder) ObjectUtil.checkNotNull(messageToMessageEncoder, "encoder");
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        if (!super.acceptOutboundMessage(obj)) {
            return false;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
        if (!this.encoder.acceptOutboundMessage(addressedEnvelope.content()) || !(addressedEnvelope.sender() instanceof InetSocketAddress) || !(addressedEnvelope.recipient() instanceof InetSocketAddress)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<M, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        this.encoder.encode(channelHandlerContext, addressedEnvelope.content(), list);
        if (list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof ByteBuf) {
                list.set(0, new DatagramPacket((ByteBuf) obj, (InetSocketAddress) addressedEnvelope.recipient(), (InetSocketAddress) addressedEnvelope.sender()));
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtil.simpleClassName((Object) this.encoder));
            sb.append(" must produce only ByteBuf.");
            throw new EncoderException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(StringUtil.simpleClassName((Object) this.encoder));
        sb2.append(" must produce only one message.");
        throw new EncoderException(sb2.toString());
    }

    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        this.encoder.bind(channelHandlerContext, socketAddress, channelPromise);
    }

    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        this.encoder.connect(channelHandlerContext, socketAddress, socketAddress2, channelPromise);
    }

    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.disconnect(channelHandlerContext, channelPromise);
    }

    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.close(channelHandlerContext, channelPromise);
    }

    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.encoder.deregister(channelHandlerContext, channelPromise);
    }

    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.read(channelHandlerContext);
    }

    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.flush(channelHandlerContext);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.handlerAdded(channelHandlerContext);
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.handlerRemoved(channelHandlerContext);
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        this.encoder.exceptionCaught(channelHandlerContext, th);
    }

    public boolean isSharable() {
        return this.encoder.isSharable();
    }
}
