package p043io.netty.handler.ssl;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;

/* renamed from: io.netty.handler.ssl.PemEncoded */
interface PemEncoded extends ByteBufHolder {
    PemEncoded copy();

    PemEncoded duplicate();

    boolean isSensitive();

    PemEncoded replace(ByteBuf byteBuf);

    PemEncoded retain();

    PemEncoded retain(int i);

    PemEncoded retainedDuplicate();

    PemEncoded touch();

    PemEncoded touch(Object obj);
}
