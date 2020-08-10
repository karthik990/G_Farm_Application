package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.ByteProcessor;

@Deprecated
/* renamed from: io.netty.buffer.ReadOnlyByteBuf */
public class ReadOnlyByteBuf extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;

    public ByteBuf asReadOnly() {
        return this;
    }

    public boolean hasArray() {
        return false;
    }

    public boolean isReadOnly() {
        return true;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int i) {
        return false;
    }

    public ReadOnlyByteBuf(ByteBuf byteBuf) {
        super(byteBuf.maxCapacity());
        if ((byteBuf instanceof ReadOnlyByteBuf) || (byteBuf instanceof DuplicatedByteBuf)) {
            this.buffer = byteBuf.unwrap();
        } else {
            this.buffer = byteBuf;
        }
        setIndex(byteBuf.readerIndex(), byteBuf.writerIndex());
    }

    public ByteBuf unwrap() {
        return this.buffer;
    }

    public ByteBufAllocator alloc() {
        return unwrap().alloc();
    }

    @Deprecated
    public ByteOrder order() {
        return unwrap().order();
    }

    public boolean isDirect() {
        return unwrap().isDirect();
    }

    public byte[] array() {
        throw new ReadOnlyBufferException();
    }

    public int arrayOffset() {
        throw new ReadOnlyBufferException();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress();
    }

    public ByteBuf discardReadBytes() {
        throw new ReadOnlyBufferException();
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

    public int setBytes(int i, InputStream inputStream, int i2) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw new ReadOnlyBufferException();
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return unwrap().getBytes(i, gatheringByteChannel, i2);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return unwrap().getBytes(i, fileChannel, j, i2);
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        unwrap().getBytes(i, outputStream, i2);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        unwrap().getBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        unwrap().getBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        unwrap().getBytes(i, byteBuffer);
        return this;
    }

    public ByteBuf duplicate() {
        return new ReadOnlyByteBuf(this);
    }

    public ByteBuf copy(int i, int i2) {
        return unwrap().copy(i, i2);
    }

    public ByteBuf slice(int i, int i2) {
        return Unpooled.unmodifiableBuffer(unwrap().slice(i, i2));
    }

    public byte getByte(int i) {
        return unwrap().getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return unwrap().getByte(i);
    }

    public short getShort(int i) {
        return unwrap().getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return unwrap().getShort(i);
    }

    public short getShortLE(int i) {
        return unwrap().getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return unwrap().getShortLE(i);
    }

    public int getUnsignedMedium(int i) {
        return unwrap().getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return unwrap().getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return unwrap().getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return unwrap().getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return unwrap().getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return unwrap().getInt(i);
    }

    public int getIntLE(int i) {
        return unwrap().getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return unwrap().getIntLE(i);
    }

    public long getLong(int i) {
        return unwrap().getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return unwrap().getLong(i);
    }

    public long getLongLE(int i) {
        return unwrap().getLongLE(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return unwrap().getLongLE(i);
    }

    public int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        return unwrap().nioBuffer(i, i2).asReadOnlyBuffer();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return unwrap().nioBuffers(i, i2);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByte(i, i2, byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByteDesc(i, i2, byteProcessor);
    }

    public int capacity() {
        return unwrap().capacity();
    }

    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }
}
