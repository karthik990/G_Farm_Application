package p043io.netty.buffer;

import p043io.netty.util.ReferenceCounted;

/* renamed from: io.netty.buffer.ByteBufHolder */
public interface ByteBufHolder extends ReferenceCounted {
    ByteBuf content();

    ByteBufHolder copy();

    ByteBufHolder duplicate();

    ByteBufHolder replace(ByteBuf byteBuf);

    ByteBufHolder retain();

    ByteBufHolder retain(int i);

    ByteBufHolder retainedDuplicate();

    ByteBufHolder touch();

    ByteBufHolder touch(Object obj);
}
