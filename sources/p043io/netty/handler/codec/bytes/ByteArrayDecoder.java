package p043io.netty.handler.codec.bytes;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageDecoder;

/* renamed from: io.netty.handler.codec.bytes.ByteArrayDecoder */
public class ByteArrayDecoder extends MessageToMessageDecoder<ByteBuf> {
    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bArr = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0, bArr);
        list.add(bArr);
    }
}
