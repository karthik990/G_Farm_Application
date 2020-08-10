package p043io.netty.util.internal;

import java.nio.ByteBuffer;

/* renamed from: io.netty.util.internal.Cleaner */
interface Cleaner {
    void freeDirectBuffer(ByteBuffer byteBuffer);
}
