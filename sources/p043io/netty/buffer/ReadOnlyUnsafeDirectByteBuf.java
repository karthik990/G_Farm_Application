package p043io.netty.buffer;

import java.nio.ByteBuffer;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.ReadOnlyUnsafeDirectByteBuf */
final class ReadOnlyUnsafeDirectByteBuf extends ReadOnlyByteBufferBuf {
    private final long memoryAddress;

    ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBufAllocator, byteBuffer);
        this.memoryAddress = PlatformDependent.directBufferAddress(byteBuffer);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return UnsafeByteBufUtil.getByte(addr(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return UnsafeByteBufUtil.getShort(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return UnsafeByteBufUtil.getUnsignedMedium(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return UnsafeByteBufUtil.getInt(addr(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return UnsafeByteBufUtil.getLong(addr(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkIndex(i, i3);
        if (byteBuf == null) {
            throw new NullPointerException("dst");
        } else if (i2 < 0 || i2 > byteBuf.capacity() - i3) {
            StringBuilder sb = new StringBuilder();
            sb.append("dstIndex: ");
            sb.append(i2);
            throw new IndexOutOfBoundsException(sb.toString());
        } else {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr(i), ((long) i2) + byteBuf.memoryAddress(), (long) i3);
            } else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(addr(i), byteBuf.array(), byteBuf.arrayOffset() + i2, (long) i3);
            } else {
                byteBuf.setBytes(i2, (ByteBuf) this, i, i3);
            }
            return this;
        }
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkIndex(i, i3);
        if (bArr == null) {
            throw new NullPointerException("dst");
        } else if (i2 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length)}));
        } else {
            if (i3 != 0) {
                PlatformDependent.copyMemory(addr(i), bArr, i2, (long) i3);
            }
            return this;
        }
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i);
        if (byteBuffer != null) {
            int min = Math.min(capacity() - i, byteBuffer.remaining());
            ByteBuffer internalNioBuffer = internalNioBuffer();
            internalNioBuffer.clear().position(i).limit(i + min);
            byteBuffer.put(internalNioBuffer);
            return this;
        }
        throw new NullPointerException("dst");
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf directBuffer = alloc().directBuffer(i2, maxCapacity());
        if (i2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr(i), directBuffer.memoryAddress(), (long) i2);
                directBuffer.setIndex(0, i2);
            } else {
                directBuffer.writeBytes((ByteBuf) this, i, i2);
            }
        }
        return directBuffer;
    }

    private long addr(int i) {
        return this.memoryAddress + ((long) i);
    }
}
