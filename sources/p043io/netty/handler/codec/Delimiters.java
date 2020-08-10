package p043io.netty.handler.codec;

import com.google.common.base.Ascii;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;

/* renamed from: io.netty.handler.codec.Delimiters */
public final class Delimiters {
    public static ByteBuf[] nulDelimiter() {
        return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{0})};
    }

    public static ByteBuf[] lineDelimiter() {
        return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{Ascii.f1866CR, 10}), Unpooled.wrappedBuffer(new byte[]{10})};
    }

    private Delimiters() {
    }
}
