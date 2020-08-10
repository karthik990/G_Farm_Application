package p043io.netty.buffer;

import com.google.common.base.Ascii;
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

/* renamed from: io.netty.buffer.PooledDirectByteBuf */
final class PooledDirectByteBuf extends PooledByteBuf<ByteBuffer> {
    private static final Recycler<PooledDirectByteBuf> RECYCLER = new Recycler<PooledDirectByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledDirectByteBuf newObject(Handle<PooledDirectByteBuf> handle) {
            return new PooledDirectByteBuf(handle, 0);
        }
    };

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public boolean isDirect() {
        return true;
    }

    public int nioBufferCount() {
        return 1;
    }

    static PooledDirectByteBuf newInstance(int i) {
        PooledDirectByteBuf pooledDirectByteBuf = (PooledDirectByteBuf) RECYCLER.get();
        pooledDirectByteBuf.reuse(i);
        return pooledDirectByteBuf;
    }

    private PooledDirectByteBuf(Handle<PooledDirectByteBuf> handle, int i) {
        super(handle, i);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer newInternalNioBuffer(ByteBuffer byteBuffer) {
        return byteBuffer.duplicate();
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return ((ByteBuffer) this.memory).get(idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return ((ByteBuffer) this.memory).getShort(idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(_getShort(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        int idx = idx(i);
        return (((ByteBuffer) this.memory).get(idx + 2) & 255) | ((((ByteBuffer) this.memory).get(idx) & 255) << Ascii.DLE) | ((((ByteBuffer) this.memory).get(idx + 1) & 255) << 8);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        int idx = idx(i);
        return ((((ByteBuffer) this.memory).get(idx + 2) & 255) << Ascii.DLE) | (((ByteBuffer) this.memory).get(idx) & 255) | ((((ByteBuffer) this.memory).get(idx + 1) & 255) << 8);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return ((ByteBuffer) this.memory).getInt(idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(_getInt(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return ((ByteBuffer) this.memory).getLong(idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(_getLong(i));
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
        getBytes(i, bArr, i2, i3, false);
        return this;
    }

    private void getBytes(int i, byte[] bArr, int i2, int i3, boolean z) {
        ByteBuffer byteBuffer;
        checkDstIndex(i, i3, i2, bArr.length);
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = ((ByteBuffer) this.memory).duplicate();
        }
        int idx = idx(i);
        byteBuffer.clear().position(idx).limit(idx + i3);
        byteBuffer.get(bArr, i2, i3);
    }

    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        checkReadableBytes(i2);
        getBytes(this.readerIndex, bArr, i, i2, true);
        this.readerIndex += i2;
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        getBytes(i, byteBuffer, false);
        return this;
    }

    private void getBytes(int i, ByteBuffer byteBuffer, boolean z) {
        ByteBuffer byteBuffer2;
        checkIndex(i, byteBuffer.remaining());
        if (z) {
            byteBuffer2 = internalNioBuffer();
        } else {
            byteBuffer2 = ((ByteBuffer) this.memory).duplicate();
        }
        int idx = idx(i);
        byteBuffer2.clear().position(idx).limit(idx + byteBuffer.remaining());
        byteBuffer.put(byteBuffer2);
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        getBytes(this.readerIndex, byteBuffer, true);
        this.readerIndex += remaining;
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        getBytes(i, outputStream, i2, false);
        return this;
    }

    private void getBytes(int i, OutputStream outputStream, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        checkIndex(i, i2);
        if (i2 != 0) {
            byte[] bArr = new byte[i2];
            if (z) {
                byteBuffer = internalNioBuffer();
            } else {
                byteBuffer = ((ByteBuffer) this.memory).duplicate();
            }
            byteBuffer.clear().position(idx(i));
            byteBuffer.get(bArr);
            outputStream.write(bArr);
        }
    }

    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        checkReadableBytes(i);
        getBytes(this.readerIndex, outputStream, i, true);
        this.readerIndex += i;
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
        ((ByteBuffer) this.memory).put(idx(i), (byte) i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        ((ByteBuffer) this.memory).putShort(idx(i), (short) i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        _setShort(i, ByteBufUtil.swapShort((short) i2));
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        int idx = idx(i);
        ((ByteBuffer) this.memory).put(idx, (byte) (i2 >>> 16));
        ((ByteBuffer) this.memory).put(idx + 1, (byte) (i2 >>> 8));
        ((ByteBuffer) this.memory).put(idx + 2, (byte) i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        int idx = idx(i);
        ((ByteBuffer) this.memory).put(idx, (byte) i2);
        ((ByteBuffer) this.memory).put(idx + 1, (byte) (i2 >>> 8));
        ((ByteBuffer) this.memory).put(idx + 2, (byte) (i2 >>> 16));
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        ((ByteBuffer) this.memory).putInt(idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        _setInt(i, ByteBufUtil.swapInt(i2));
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        ((ByteBuffer) this.memory).putLong(idx(i), j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        _setLong(i, ByteBufUtil.swapLong(j));
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ByteBuffer[] nioBuffers;
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(i2, i3)) {
                int remaining = byteBuffer.remaining();
                setBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.getBytes(i2, (ByteBuf) this, i, i3);
        }
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        ByteBuffer internalNioBuffer = internalNioBuffer();
        int idx = idx(i);
        internalNioBuffer.clear().position(idx).limit(idx + i3);
        internalNioBuffer.put(bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i, byteBuffer.remaining());
        ByteBuffer internalNioBuffer = internalNioBuffer();
        if (byteBuffer == internalNioBuffer) {
            byteBuffer = byteBuffer.duplicate();
        }
        int idx = idx(i);
        internalNioBuffer.clear().position(idx).limit(idx + byteBuffer.remaining());
        internalNioBuffer.put(byteBuffer);
        return this;
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex(i, i2);
        byte[] bArr = new byte[i2];
        int read = inputStream.read(bArr);
        if (read <= 0) {
            return read;
        }
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(idx(i));
        internalNioBuffer.put(bArr, 0, read);
        return read;
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
        checkIndex(i, i2);
        ByteBuf directBuffer = alloc().directBuffer(i2, maxCapacity());
        directBuffer.writeBytes((ByteBuf) this, i, i2);
        return directBuffer;
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int idx = idx(i);
        return ((ByteBuffer) ((ByteBuffer) this.memory).duplicate().position(idx).limit(idx + i2)).slice();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
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
        throw new UnsupportedOperationException();
    }
}
