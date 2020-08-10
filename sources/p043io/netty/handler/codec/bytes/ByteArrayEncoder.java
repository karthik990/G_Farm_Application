package p043io.netty.handler.codec.bytes;

import java.util.List;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.bytes.ByteArrayEncoder */
public class ByteArrayEncoder extends MessageToMessageEncoder<byte[]> {
    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, byte[] bArr, List<Object> list) throws Exception {
        list.add(Unpooled.wrappedBuffer(bArr));
    }
}
