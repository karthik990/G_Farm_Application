package p043io.netty.buffer;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.buffer.ReadOnlyByteBufferBuf */
class ReadOnlyByteBufferBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator allocator;
    protected final ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;

    /* access modifiers changed from: protected */
    public void deallocate() {
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuf unwrap() {
        return null;
    }

    ReadOnlyByteBufferBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBuffer.remaining());
        if (byteBuffer.isReadOnly()) {
            this.allocator = byteBufAllocator;
            this.buffer = byteBuffer.slice().order(ByteOrder.BIG_ENDIAN);
            writerIndex(this.buffer.limit());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("must be a readonly buffer: ");
        sb.append(StringUtil.simpleClassName((Object) byteBuffer));
        throw new IllegalArgumentException(sb.toString());
    }

    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return this.buffer.get(i);
    }

    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return this.buffer.getShort(i);
    }

    public short getShortLE(int i) {
        ensureAccessible();
        return _getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(this.buffer.getShort(i));
    }

    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return (getByte(i + 2) & 255) | ((getByte(i) & 255) << Ascii.DLE) | ((getByte(i + 1) & 255) << 8);
    }

    public int getUnsignedMediumLE(int i) {
        ensureAccessible();
        return _getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return ((getByte(i + 2) & 255) << Ascii.DLE) | (getByte(i) & 255) | ((getByte(i + 1) & 255) << 8);
    }

    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return this.buffer.getInt(i);
    }

    public int getIntLE(int i) {
        ensureAccessible();
        return _getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(this.buffer.getInt(i));
    }

    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return this.buffer.getLong(i);
    }

    public long getLongLE(int i) {
        ensureAccessible();
        return _getLongLE(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(this.buffer.getLong(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ByteBuffer[] nioBuffers;
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(i2, i3)) {
                int remaining = byteBuffer.remaining();
                getBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.setBytes(i2, (ByteBuf) this, i, i3);
        }
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i2 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length)}));
        }
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i3);
        internalNioBuffer.get(bArr, i2, i3);
        return this;
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

    public ByteBuf setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setShortLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setMediumLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setIntLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setLongLE(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    public int capacity() {
        return maxCapacity();
    }

    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }

    public ByteBufAllocator alloc() {
        return this.allocator;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public boolean isReadOnly() {
        return this.buffer.isReadOnly();
    }

    public boolean isDirect() {
        return this.buffer.isDirect();
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return this;
        }
        if (this.buffer.hasArray()) {
            outputStream.write(this.buffer.array(), i + this.buffer.arrayOffset(), i2);
        } else {
            byte[] bArr = new byte[i2];
            ByteBuffer internalNioBuffer = internalNioBuffer();
            internalNioBuffer.clear().position(i);
            internalNioBuffer.get(bArr);
            outputStream.write(bArr);
        }
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i2);
        return gatheringByteChannel.write(internalNioBuffer);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i2);
        return fileChannel.write(internalNioBuffer, j);
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer duplicate = this.buffer.duplicate();
        this.tmpNioBuf = duplicate;
        return duplicate;
    }

    public ByteBuf copy(int i, int i2) {
        ensureAccessible();
        try {
            ByteBuffer byteBuffer = (ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2);
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i2);
            allocateDirect.put(byteBuffer);
            allocateDirect.order(order());
            allocateDirect.clear();
            return new UnpooledDirectByteBuf(alloc(), allocateDirect, maxCapacity());
        } catch (IllegalArgumentException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Too many bytes to read - Need ");
            sb.append(i + i2);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        return (ByteBuffer) this.buffer.duplicate().position(i).limit(i + i2);
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        ensureAccessible();
        return (ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2);
    }

    public boolean hasArray() {
        return this.buffer.hasArray();
    }

    public byte[] array() {
        return this.buffer.array();
    }

    public int arrayOffset() {
        return this.buffer.arrayOffset();
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
}
