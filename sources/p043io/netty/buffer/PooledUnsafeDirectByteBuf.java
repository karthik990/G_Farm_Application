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

/* renamed from: io.netty.buffer.PooledUnsafeDirectByteBuf */
final class PooledUnsafeDirectByteBuf extends PooledByteBuf<ByteBuffer> {
    private static final Recycler<PooledUnsafeDirectByteBuf> RECYCLER = new Recycler<PooledUnsafeDirectByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledUnsafeDirectByteBuf newObject(Handle<PooledUnsafeDirectByteBuf> handle) {
            return new PooledUnsafeDirectByteBuf(handle, 0);
        }
    };
    private long memoryAddress;

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return true;
    }

    public boolean isDirect() {
        return true;
    }

    public int nioBufferCount() {
        return 1;
    }

    static PooledUnsafeDirectByteBuf newInstance(int i) {
        PooledUnsafeDirectByteBuf pooledUnsafeDirectByteBuf = (PooledUnsafeDirectByteBuf) RECYCLER.get();
        pooledUnsafeDirectByteBuf.reuse(i);
        return pooledUnsafeDirectByteBuf;
    }

    private PooledUnsafeDirectByteBuf(Handle<PooledUnsafeDirectByteBuf> handle, int i) {
        super(handle, i);
    }

    /* access modifiers changed from: 0000 */
    public void init(PoolChunk<ByteBuffer> poolChunk, long j, int i, int i2, int i3, PoolThreadCache poolThreadCache) {
        super.init(poolChunk, j, i, i2, i3, poolThreadCache);
        initMemoryAddress();
    }

    /* access modifiers changed from: 0000 */
    public void initUnpooled(PoolChunk<ByteBuffer> poolChunk, int i) {
        super.initUnpooled(poolChunk, i);
        initMemoryAddress();
    }

    private void initMemoryAddress() {
        this.memoryAddress = PlatformDependent.directBufferAddress((ByteBuffer) this.memory) + ((long) this.offset);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer newInternalNioBuffer(ByteBuffer byteBuffer) {
        return byteBuffer.duplicate();
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
    public short _getShortLE(int i) {
        return UnsafeByteBufUtil.getShortLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return UnsafeByteBufUtil.getUnsignedMedium(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return UnsafeByteBufUtil.getUnsignedMediumLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return UnsafeByteBufUtil.getInt(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return UnsafeByteBufUtil.getIntLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return UnsafeByteBufUtil.getLong(addr(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return UnsafeByteBufUtil.getLongLE(addr(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(i), i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(i), i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.getBytes(this, addr(i), i, byteBuffer);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        getBytes(this.readerIndex, byteBuffer);
        this.readerIndex += remaining;
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        UnsafeByteBufUtil.getBytes(this, addr(i), i, outputStream, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return getBytes(i, gatheringByteChannel, i2, false);
    }

    private int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        checkIndex(i, i2);
        if (i2 == 0) {
            return 0;
        }
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = ((ByteBuffer) this.memory).duplicate();
        }
        int idx = idx(i);
        byteBuffer.clear().position(idx).limit(idx + i2);
        return gatheringByteChannel.write(byteBuffer);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return getBytes(i, fileChannel, j, i2, false);
    }

    private int getBytes(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer internalNioBuffer = z ? internalNioBuffer() : ((ByteBuffer) this.memory).duplicate();
        int idx = idx(i);
        internalNioBuffer.clear().position(idx).limit(idx + i2);
        return fileChannel.write(internalNioBuffer, j);
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        checkReadableBytes(i);
        int bytes = getBytes(this.readerIndex, gatheringByteChannel, i, true);
        this.readerIndex += bytes;
        return bytes;
    }

    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        checkReadableBytes(i);
        int bytes = getBytes(this.readerIndex, fileChannel, j, i, true);
        this.readerIndex += bytes;
        return bytes;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        UnsafeByteBufUtil.setByte(addr(i), (byte) i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        UnsafeByteBufUtil.setShort(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        UnsafeByteBufUtil.setShortLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        UnsafeByteBufUtil.setMedium(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        UnsafeByteBufUtil.setMediumLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        UnsafeByteBufUtil.setInt(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        UnsafeByteBufUtil.setIntLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        UnsafeByteBufUtil.setLong(addr(i), j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        UnsafeByteBufUtil.setLongLE(addr(i), j);
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(i), i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(i), i, bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.setBytes(this, addr(i), i, byteBuffer);
        return this;
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return UnsafeByteBufUtil.setBytes(this, addr(i), i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex(i, i2);
        ByteBuffer internalNioBuffer = internalNioBuffer();
        int idx = idx(i);
        internalNioBuffer.clear().position(idx).limit(idx + i2);
        try {
            return scatteringByteChannel.read(internalNioBuffer);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex(i, i2);
        ByteBuffer internalNioBuffer = internalNioBuffer();
        int idx = idx(i);
        internalNioBuffer.clear().position(idx).limit(idx + i2);
        try {
            return fileChannel.read(internalNioBuffer, j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public ByteBuf copy(int i, int i2) {
        return UnsafeByteBufUtil.copy(this, addr(i), i, i2);
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int idx = idx(i);
        return ((ByteBuffer) ((ByteBuffer) this.memory).duplicate().position(idx).limit(idx + i2)).slice();
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int idx = idx(i);
        return (ByteBuffer) internalNioBuffer().clear().position(idx).limit(idx + i2);
    }

    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public long memoryAddress() {
        ensureAccessible();
        return this.memoryAddress;
    }

    private long addr(int i) {
        return this.memoryAddress + ((long) i);
    }

    /* access modifiers changed from: protected */
    public SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeDirectSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }

    public ByteBuf setZero(int i, int i2) {
        UnsafeByteBufUtil.setZero(this, addr(i), i, i2);
        return this;
    }

    public ByteBuf writeZero(int i) {
        ensureWritable(i);
        int i2 = this.writerIndex;
        setZero(i2, i);
        this.writerIndex = i2 + i;
        return this;
    }
}
