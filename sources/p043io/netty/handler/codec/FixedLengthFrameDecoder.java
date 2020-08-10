package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.FixedLengthFrameDecoder */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int i) {
        if (i > 0) {
            this.frameLength = i;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("frameLength must be a positive integer: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        int i = this.frameLength;
        if (readableBytes < i) {
            return null;
        }
        return byteBuf.readRetainedSlice(i);
    }
}
