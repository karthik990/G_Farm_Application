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

/* renamed from: io.netty.buffer.PooledDuplicatedByteBuf */
final class PooledDuplicatedByteBuf extends AbstractPooledDerivedByteBuf {
    private static final Recycler<PooledDuplicatedByteBuf> RECYCLER = new Recycler<PooledDuplicatedByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledDuplicatedByteBuf newObject(Handle<PooledDuplicatedByteBuf> handle) {
            return new PooledDuplicatedByteBuf(handle);
        }
    };

    static PooledDuplicatedByteBuf newInstance(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int i, int i2) {
        PooledDuplicatedByteBuf pooledDuplicatedByteBuf = (PooledDuplicatedByteBuf) RECYCLER.get();
        pooledDuplicatedByteBuf.init(abstractByteBuf, byteBuf, i, i2, abstractByteBuf.maxCapacity());
        pooledDuplicatedByteBuf.markReaderIndex();
        pooledDuplicatedByteBuf.markWriterIndex();
        return pooledDuplicatedByteBuf;
    }

    private PooledDuplicatedByteBuf(Handle<PooledDuplicatedByteBuf> handle) {
        super(handle);
    }

    public int capacity() {
        return unwrap().capacity();
    }

    public ByteBuf capacity(int i) {
        unwrap().capacity(i);
        return this;
    }

    public int arrayOffset() {
        return unwrap().arrayOffset();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress();
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        return unwrap().nioBuffer(i, i2);
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return unwrap().nioBuffers(i, i2);
    }

    public ByteBuf copy(int i, int i2) {
        return unwrap().copy(i, i2);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return PooledSlicedByteBuf.newInstance(unwrap(), this, i, i2);
    }

    public ByteBuf duplicate() {
        return duplicate0().setIndex(readerIndex(), writerIndex());
    }

    public ByteBuf retainedDuplicate() {
        return newInstance(unwrap(), this, readerIndex(), writerIndex());
    }

    public byte getByte(int i) {
        return unwrap().getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return unwrap()._getByte(i);
    }

    public short getShort(int i) {
        return unwrap().getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return unwrap()._getShort(i);
    }

    public short getShortLE(int i) {
        return unwrap().getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return unwrap()._getShortLE(i);
    }

    public int getUnsignedMedium(int i) {
        return unwrap().getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return unwrap().getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return unwrap().getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return unwrap()._getInt(i);
    }

    public int getIntLE(int i) {
        return unwrap().getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return unwrap()._getIntLE(i);
    }

    public long getLong(int i) {
        return unwrap().getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return unwrap()._getLong(i);
    }

    public long getLongLE(int i) {
        return unwrap().getLongLE(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return unwrap()._getLongLE(i);
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        unwrap().getBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        unwrap().getBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        unwrap().getBytes(i, byteBuffer);
        return this;
    }

    public ByteBuf setByte(int i, int i2) {
        unwrap().setByte(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        unwrap()._setByte(i, i2);
    }

    public ByteBuf setShort(int i, int i2) {
        unwrap().setShort(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        unwrap()._setShort(i, i2);
    }

    public ByteBuf setShortLE(int i, int i2) {
        unwrap().setShortLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(i, i2);
    }

    public ByteBuf setMedium(int i, int i2) {
        unwrap().setMedium(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        unwrap()._setMedium(i, i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        unwrap().setMediumLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(i, i2);
    }

    public ByteBuf setInt(int i, int i2) {
        unwrap().setInt(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        unwrap()._setInt(i, i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        unwrap().setIntLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(i, i2);
    }

    public ByteBuf setLong(int i, long j) {
        unwrap().setLong(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        unwrap()._setLong(i, j);
    }

    public ByteBuf setLongLE(int i, long j) {
        unwrap().setLongLE(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        unwrap().setLongLE(i, j);
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        unwrap().setBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        unwrap().setBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        unwrap().setBytes(i, byteBuffer);
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        unwrap().getBytes(i, outputStream, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return unwrap().getBytes(i, gatheringByteChannel, i2);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return unwrap().getBytes(i, fileChannel, j, i2);
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return unwrap().setBytes(i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return unwrap().setBytes(i, scatteringByteChannel, i2);
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return unwrap().setBytes(i, fileChannel, j, i2);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByte(i, i2, byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByteDesc(i, i2, byteProcessor);
    }
}
