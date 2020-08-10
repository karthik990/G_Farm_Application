package p043io.netty.handler.codec.string;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;
import p043io.netty.util.CharsetUtil;
import p043io.netty.util.internal.ObjectUtil;

@Sharable
/* renamed from: io.netty.handler.codec.string.LineEncoder */
public class LineEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset charset;
    private final byte[] lineSeparator;

    public LineEncoder() {
        this(LineSeparator.DEFAULT, CharsetUtil.UTF_8);
    }

    public LineEncoder(LineSeparator lineSeparator2) {
        this(lineSeparator2, CharsetUtil.UTF_8);
    }

    public LineEncoder(Charset charset2) {
        this(LineSeparator.DEFAULT, charset2);
    }

    public LineEncoder(LineSeparator lineSeparator2, Charset charset2) {
        this.charset = (Charset) ObjectUtil.checkNotNull(charset2, HttpRequest.PARAM_CHARSET);
        this.lineSeparator = ((LineSeparator) ObjectUtil.checkNotNull(lineSeparator2, "lineSeparator")).value().getBytes(charset2);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List<Object> list) throws Exception {
        ByteBuf encodeString = ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), this.charset, this.lineSeparator.length);
        encodeString.writeBytes(this.lineSeparator);
        list.add(encodeString);
    }
}
