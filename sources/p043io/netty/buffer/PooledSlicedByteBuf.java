package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.ByteProcessor;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;

/* renamed from: io.netty.buffer.PooledSlicedByteBuf */
final class PooledSlicedByteBuf extends AbstractPooledDerivedByteBuf {
    private static final Recycler<PooledSlicedByteBuf> RECYCLER = new Recycler<PooledSlicedByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledSlicedByteBuf newObject(Handle<PooledSlicedByteBuf> handle) {
            return new PooledSlicedByteBuf(handle);
        }
    };
    int adjustment;

    static PooledSlicedByteBuf newInstance(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int i, int i2) {
        AbstractUnpooledSlicedByteBuf.checkSliceOutOfBounds(i, i2, abstractByteBuf);
        return newInstance0(abstractByteBuf, byteBuf, i, i2);
    }

    private static PooledSlicedByteBuf newInstance0(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int i, int i2) {
        PooledSlicedByteBuf pooledSlicedByteBuf = (PooledSlicedByteBuf) RECYCLER.get();
        pooledSlicedByteBuf.init(abstractByteBuf, byteBuf, 0, i2, i2);
        pooledSlicedByteBuf.discardMarks();
        pooledSlicedByteBuf.adjustment = i;
        return pooledSlicedByteBuf;
    }

    private PooledSlicedByteBuf(Handle<PooledSlicedByteBuf> handle) {
        super(handle);
    }

    public int capacity() {
        return maxCapacity();
    }

    public ByteBuf capacity(int i) {
        throw new UnsupportedOperationException("sliced buffer");
    }

    public int arrayOffset() {
        return idx(unwrap().arrayOffset());
    }

    public long memoryAddress() {
        return unwrap().memoryAddress() + ((long) this.adjustment);
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex0(i, i2);
        return unwrap().nioBuffer(idx(i), i2);
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex0(i, i2);
        return unwrap().nioBuffers(idx(i), i2);
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex0(i, i2);
        return unwrap().copy(idx(i), i2);
    }

    public ByteBuf slice(int i, int i2) {
        checkIndex0(i, i2);
        return super.slice(idx(i), i2);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        checkIndex0(i, i2);
        return newInstance0(unwrap(), this, idx(i), i2);
    }

    public ByteBuf duplicate() {
        return duplicate0().setIndex(idx(readerIndex()), idx(writerIndex()));
    }

    public ByteBuf retainedDuplicate() {
        return PooledDuplicatedByteBuf.newInstance(unwrap(), this, idx(readerIndex()), idx(writerIndex()));
    }

    public byte getByte(int i) {
        checkIndex0(i, 1);
        return unwrap().getByte(idx(i));
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return unwrap()._getByte(idx(i));
    }

    public short getShort(int i) {
        checkIndex0(i, 2);
        return unwrap().getShort(idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return unwrap()._getShort(idx(i));
    }

    public short getShortLE(int i) {
        checkIndex0(i, 2);
        return unwrap().getShortLE(idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return unwrap()._getShortLE(idx(i));
    }

    public int getUnsignedMedium(int i) {
        checkIndex0(i, 3);
        return unwrap().getUnsignedMedium(idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(idx(i));
    }

    public int getUnsignedMediumLE(int i) {
        checkIndex0(i, 3);
        return unwrap().getUnsignedMediumLE(idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(idx(i));
    }

    public int getInt(int i) {
        checkIndex0(i, 4);
        return unwrap().getInt(idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return unwrap()._getInt(idx(i));
    }

    public int getIntLE(int i) {
        checkIndex0(i, 4);
        return unwrap().getIntLE(idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return unwrap()._getIntLE(idx(i));
    }

    public long getLong(int i) {
        checkIndex0(i, 8);
        return unwrap().getLong(idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return unwrap()._getLong(idx(i));
    }

    public long getLongLE(int i) {
        checkIndex0(i, 8);
        return unwrap().getLongLE(idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return unwrap()._getLongLE(idx(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkIndex0(i, i3);
        unwrap().getBytes(idx(i), byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkIndex0(i, i3);
        unwrap().getBytes(idx(i), bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex0(i, byteBuffer.remaining());
        unwrap().getBytes(idx(i), byteBuffer);
        return this;
    }

    public ByteBuf setByte(int i, int i2) {
        checkIndex0(i, 1);
        unwrap().setByte(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        unwrap()._setByte(idx(i), i2);
    }

    public ByteBuf setShort(int i, int i2) {
        checkIndex0(i, 2);
        unwrap().setShort(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        unwrap()._setShort(idx(i), i2);
    }

    public ByteBuf setShortLE(int i, int i2) {
        checkIndex0(i, 2);
        unwrap().setShortLE(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(idx(i), i2);
    }

    public ByteBuf setMedium(int i, int i2) {
        checkIndex0(i, 3);
        unwrap().setMedium(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        unwrap()._setMedium(idx(i), i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        checkIndex0(i, 3);
        unwrap().setMediumLE(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(idx(i), i2);
    }

    public ByteBuf setInt(int i, int i2) {
        checkIndex0(i, 4);
        unwrap().setInt(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        unwrap()._setInt(idx(i), i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        checkIndex0(i, 4);
        unwrap().setIntLE(idx(i), i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(idx(i), i2);
    }

    public ByteBuf setLong(int i, long j) {
        checkIndex0(i, 8);
        unwrap().setLong(idx(i), j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        unwrap()._setLong(idx(i), j);
    }

    public ByteBuf setLongLE(int i, long j) {
        checkIndex0(i, 8);
        unwrap().setLongLE(idx(i), j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        unwrap().setLongLE(idx(i), j);
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkIndex0(i, i3);
        unwrap().setBytes(idx(i), bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkIndex0(i, i3);
        unwrap().setBytes(idx(i), byteBuf, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        checkIndex0(i, byteBuffer.remaining());
        unwrap().setBytes(idx(i), byteBuffer);
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex0(i, i2);
        unwrap().getBytes(idx(i), outputStream, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        checkIndex0(i, i2);
        return unwrap().getBytes(idx(i), gatheringByteChannel, i2);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex0(i, i2);
        return unwrap().getBytes(idx(i), fileChannel, j, i2);
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex0(i, i2);
        return unwrap().setBytes(idx(i), inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex0(i, i2);
        return unwrap().setBytes(idx(i), scatteringByteChannel, i2);
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex0(i, i2);
        return unwrap().setBytes(idx(i), fileChannel, j, i2);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        checkIndex0(i, i2);
        int forEachByte = unwrap().forEachByte(idx(i), i2, byteProcessor);
        int i3 = this.adjustment;
        if (forEachByte < i3) {
            return -1;
        }
        return forEachByte - i3;
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        checkIndex0(i, i2);
        int forEachByteDesc = unwrap().forEachByteDesc(idx(i), i2, byteProcessor);
        int i3 = this.adjustment;
        if (forEachByteDesc < i3) {
            return -1;
        }
        return forEachByteDesc - i3;
    }

    private int idx(int i) {
        return i + this.adjustment;
    }
}
