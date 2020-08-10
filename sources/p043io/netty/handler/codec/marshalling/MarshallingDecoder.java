package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Unmarshaller;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/* renamed from: io.netty.handler.codec.marshalling.MarshallingDecoder */
public class MarshallingDecoder extends LengthFieldBasedFrameDecoder {
    private final UnmarshallerProvider provider;

    public MarshallingDecoder(UnmarshallerProvider unmarshallerProvider) {
        this(unmarshallerProvider, 1048576);
    }

    public MarshallingDecoder(UnmarshallerProvider unmarshallerProvider, int i) {
        super(i, 0, 4, 0, 4);
        this.provider = unmarshallerProvider;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = (ByteBuf) super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(channelHandlerContext);
        try {
            unmarshaller.start(new ChannelBufferByteInput(byteBuf2));
            Object readObject = unmarshaller.readObject();
            unmarshaller.finish();
            return readObject;
        } finally {
            unmarshaller.close();
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.slice(i, i2);
    }
}
