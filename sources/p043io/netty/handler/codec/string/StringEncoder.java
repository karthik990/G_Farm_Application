package p043io.netty.handler.codec.string;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.string.StringEncoder */
public class StringEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset charset;

    public StringEncoder() {
        this(Charset.defaultCharset());
    }

    public StringEncoder(Charset charset2) {
        if (charset2 != null) {
            this.charset = charset2;
            return;
        }
        throw new NullPointerException(HttpRequest.PARAM_CHARSET);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List<Object> list) throws Exception {
        if (charSequence.length() != 0) {
            list.add(ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), this.charset));
        }
    }
}
