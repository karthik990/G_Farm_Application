package p043io.netty.handler.codec.compression;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.handler.codec.MessageToByteEncoder;

/* renamed from: io.netty.handler.codec.compression.ZlibEncoder */
public abstract class ZlibEncoder extends MessageToByteEncoder<ByteBuf> {
    public abstract ChannelFuture close();

    public abstract ChannelFuture close(ChannelPromise channelPromise);

    public abstract boolean isClosed();

    protected ZlibEncoder() {
        super(false);
    }
}
