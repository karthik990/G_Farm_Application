package p043io.netty.buffer;

import java.nio.ByteBuffer;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.WrappedUnpooledUnsafeDirectByteBuf */
final class WrappedUnpooledUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
    WrappedUnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, long j, int i, boolean z) {
        super(byteBufAllocator, PlatformDependent.directBuffer(j, i), i, z);
    }

    /* access modifiers changed from: protected */
    public void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeMemory(this.memoryAddress);
    }
}
