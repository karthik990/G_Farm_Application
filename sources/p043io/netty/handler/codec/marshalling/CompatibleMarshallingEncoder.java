package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToByteEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.marshalling.CompatibleMarshallingEncoder */
public class CompatibleMarshallingEncoder extends MessageToByteEncoder<Object> {
    private final MarshallerProvider provider;

    public CompatibleMarshallingEncoder(MarshallerProvider marshallerProvider) {
        this.provider = marshallerProvider;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        Marshaller marshaller = this.provider.getMarshaller(channelHandlerContext);
        marshaller.start(new ChannelBufferByteOutput(byteBuf));
        marshaller.writeObject(obj);
        marshaller.finish();
        marshaller.close();
    }
}
