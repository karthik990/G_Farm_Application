package p043io.netty.buffer;

import java.nio.ByteBuffer;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnpooledUnsafeNoCleanerDirectByteBuf */
class UnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(byteBufAllocator, i, i2);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer allocateDirect(int i) {
        return PlatformDependent.allocateDirectNoCleaner(i);
    }

    /* access modifiers changed from: 0000 */
    public ByteBuffer reallocateDirect(ByteBuffer byteBuffer, int i) {
        return PlatformDependent.reallocateDirectNoCleaner(byteBuffer, i);
    }

    /* access modifiers changed from: protected */
    public void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectNoCleaner(byteBuffer);
    }

    public ByteBuf capacity(int i) {
        checkNewCapacity(i);
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int capacity = capacity();
        if (i > capacity) {
            setByteBuffer(reallocateDirect(this.buffer, i), false);
        } else if (i < capacity) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer allocateDirect = allocateDirect(i);
            if (readerIndex < i) {
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                byteBuffer.position(readerIndex).limit(i);
                allocateDirect.position(readerIndex).limit(i);
                allocateDirect.put(byteBuffer);
                allocateDirect.clear();
            } else {
                setIndex(i, i);
            }
            setByteBuffer(allocateDirect, true);
        }
        return this;
    }
}
