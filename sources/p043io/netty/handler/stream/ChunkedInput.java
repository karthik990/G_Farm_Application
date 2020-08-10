package p043io.netty.handler.stream;

import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.stream.ChunkedInput */
public interface ChunkedInput<B> {
    void close() throws Exception;

    boolean isEndOfInput() throws Exception;

    long length();

    long progress();

    B readChunk(ByteBufAllocator byteBufAllocator) throws Exception;

    @Deprecated
    B readChunk(ChannelHandlerContext channelHandlerContext) throws Exception;
}
