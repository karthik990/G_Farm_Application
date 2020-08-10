package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.PooledHeapByteBuf */
class PooledHeapByteBuf extends PooledByteBuf<byte[]> {
    private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledHeapByteBuf newObject(Handle<PooledHeapByteBuf> handle) {
            return new PooledHeapByteBuf(handle, 0);
        }
    };

    public final boolean hasArray() {
        return true;
    }

    public final boolean hasMemoryAddress() {
        return false;
    }

    public final boolean isDirect() {
        return false;
    }

    public final int nioBufferCount() {
        return 1;
    }

    static PooledHeapByteBuf newInstance(int i) {
        PooledHeapByteBuf pooledHeapByteBuf = (PooledHeapByteBuf) RECYCLER.get();
        pooledHeapByteBuf.reuse(i);
        return pooledHeapByteBuf;
    }

    PooledHeapByteBuf(Handle<? extends PooledHeapByteBuf> handle, int i) {
        super(handle, i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return HeapByteBufUtil.getByte((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return HeapByteBufUtil.getShort((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return HeapByteBufUtil.getShortLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return HeapByteBufUtil.getUnsignedMedium((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return HeapByteBufUtil.getUnsignedMediumLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return HeapByteBufUtil.getInt((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return HeapByteBufUtil.getIntLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return HeapByteBufUtil.getLong((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return HeapByteBufUtil.getLongLE((byte[]) this.memory, idx(i));
    }

    public final ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory((byte[]) this.memory, idx(i), ((long) i2) + byteBuf.memoryAddress(), (long) i3);
        } else if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.setBytes(i2, (byte[]) this.memory, idx(i), i3);
        }
        return this;
    }

    public final ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        System.arraycopy(this.memory, idx(i), bArr, i2, i3);
        return this;
    }

    public final ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i, byteBuffer.remaining());
        byteBuffer.put((byte[]) this.memory, idx(i), byteBuffer.remaining());
        return this;
    }

    public final ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        outputStream.write((byte[]) this.memory, idx(i), i2);
        return this;
    }

    public final int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return getBytes(i, gatheringByteChannel, i2, false);
    }

    private int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        checkIndex(i, i2);
        int idx = idx(i);
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = ByteBuffer.wrap((byte[]) this.memory);
        }
        return gatheringByteChannel.write((ByteBuffer) byteBuffer.clear().position(idx).limit(idx + i2));
    }

    public final int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return getBytes(i, fileChannel, j, i2, false);
    }

    private int getBytes(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        checkIndex(i, i2);
        int idx = idx(i);
        return fileChannel.write((ByteBuffer) (z ? internalNioBuffer() : ByteBuffer.wrap((byte[]) this.memory)).clear().position(idx).limit(idx + i2), j);
    }

    public final int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        checkReadableBytes(i);
        int bytes = getBytes(this.readerIndex, gatheringByteChannel, i, true);
        this.readerIndex += bytes;
        return bytes;
    }

    public final int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        checkReadableBytes(i);
        int bytes = getBytes(this.readerIndex, fileChannel, j, i, true);
        this.readerIndex += bytes;
        return bytes;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        HeapByteBufUtil.setByte((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        HeapByteBufUtil.setShort((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        HeapByteBufUtil.setShortLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        HeapByteBufUtil.setMedium((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        HeapByteBufUtil.setMediumLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        HeapByteBufUtil.setInt((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        HeapByteBufUtil.setIntLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        HeapByteBufUtil.setLong((byte[]) this.memory, idx(i), j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        HeapByteBufUtil.setLongLE((byte[]) this.memory, idx(i), j);
    }

    public final ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + ((long) i2), (byte[]) this.memory, idx(i), (long) i3);
        } else if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.getBytes(i2, (byte[]) this.memory, idx(i), i3);
        }
        return this;
    }

    public final ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        System.arraycopy(bArr, i2, this.memory, idx(i), i3);
        return this;
    }

    public final ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        byteBuffer.get((byte[]) this.memory, idx(i), remaining);
        return this;
    }

    public final int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex(i, i2);
        return inputStream.read((byte[]) this.memory, idx(i), i2);
    }

    public final int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex(i, i2);
        int idx = idx(i);
        try {
            return scatteringByteChannel.read((ByteBuffer) internalNioBuffer().clear().position(idx).limit(idx + i2));
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public final int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex(i, i2);
        int idx = idx(i);
        try {
            return fileChannel.read((ByteBuffer) internalNioBuffer().clear().position(idx).limit(idx + i2), j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public final ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf heapBuffer = alloc().heapBuffer(i2, maxCapacity());
        heapBuffer.writeBytes((byte[]) this.memory, idx(i), i2);
        return heapBuffer;
    }

    public final ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public final ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return ByteBuffer.wrap((byte[]) this.memory, idx(i), i2).slice();
    }

    public final ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int idx = idx(i);
        return (ByteBuffer) internalNioBuffer().clear().position(idx).limit(idx + i2);
    }

    public final byte[] array() {
        ensureAccessible();
        return (byte[]) this.memory;
    }

    public final int arrayOffset() {
        return this.offset;
    }

    public final long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer newInternalNioBuffer(byte[] bArr) {
        return ByteBuffer.wrap(bArr);
    }
}
