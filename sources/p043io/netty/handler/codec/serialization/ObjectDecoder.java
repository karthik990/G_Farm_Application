package p043io.netty.handler.codec.serialization;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufInputStream;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/* renamed from: io.netty.handler.codec.serialization.ObjectDecoder */
public class ObjectDecoder extends LengthFieldBasedFrameDecoder {
    private final ClassResolver classResolver;

    public ObjectDecoder(ClassResolver classResolver2) {
        this(1048576, classResolver2);
    }

    public ObjectDecoder(int i, ClassResolver classResolver2) {
        super(i, 0, 4, 0, 4);
        this.classResolver = classResolver2;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = (ByteBuf) super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        CompactObjectInputStream compactObjectInputStream = new CompactObjectInputStream(new ByteBufInputStream(byteBuf2, true), this.classResolver);
        try {
            return compactObjectInputStream.readObject();
        } finally {
            compactObjectInputStream.close();
        }
    }
}
