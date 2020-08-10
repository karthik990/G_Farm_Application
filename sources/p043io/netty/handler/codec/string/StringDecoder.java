package p043io.netty.handler.codec.string;

import java.nio.charset.Charset;
import java.util.List;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
/* renamed from: io.netty.handler.codec.string.StringDecoder */
public class StringDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final Charset charset;

    public StringDecoder() {
        this(Charset.defaultCharset());
    }

    public StringDecoder(Charset charset2) {
        if (charset2 != null) {
            this.charset = charset2;
            return;
        }
        throw new NullPointerException(HttpRequest.PARAM_CHARSET);
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.toString(this.charset));
    }
}
