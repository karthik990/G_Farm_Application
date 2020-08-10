package p043io.netty.handler.codec.base64;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.base64.Base64Encoder */
public class Base64Encoder extends MessageToMessageEncoder<ByteBuf> {
    private final boolean breakLines;
    private final Base64Dialect dialect;

    public Base64Encoder() {
        this(true);
    }

    public Base64Encoder(boolean z) {
        this(z, Base64Dialect.STANDARD);
    }

    public Base64Encoder(boolean z, Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            this.breakLines = z;
            this.dialect = base64Dialect;
            return;
        }
        throw new NullPointerException("dialect");
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), this.breakLines, this.dialect));
    }
}
