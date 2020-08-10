package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.ByteProcessor;

@Deprecated
/* renamed from: io.netty.buffer.DuplicatedByteBuf */
public class DuplicatedByteBuf extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;

    public DuplicatedByteBuf(ByteBuf byteBuf) {
        this(byteBuf, byteBuf.readerIndex(), byteBuf.writerIndex());
    }

    DuplicatedByteBuf(ByteBuf byteBuf, int i, int i2) {
        super(byteBuf.maxCapacity());
        if (byteBuf instanceof DuplicatedByteBuf) {
            this.buffer = ((DuplicatedByteBuf) byteBuf).buffer;
        } else if (byteBuf instanceof AbstractPooledDerivedByteBuf) {
            this.buffer = byteBuf.unwrap();
        } else {
            this.buffer = byteBuf;
        }
        setIndex(i, i2);
        markReaderIndex();
        markWriterIndex();
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

    public int capacity() {
        return unwrap().capacity();
    }

    public ByteBuf capacity(int i) {
        unwrap().capacity(i);
        return this;
    }

    public boolean hasArray() {
        return unwrap().hasArray();
    }

    public byte[] array() {
        return unwrap().array();
    }

    public int arrayOffset() {
        return unwrap().arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress();
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

    public ByteBuf copy(int i, int i2) {
        return unwrap().copy(i, i2);
    }

    public ByteBuf slice(int i, int i2) {
        return unwrap().slice(i, i2);
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
        unwrap().setByte(i, i2);
    }

    public ByteBuf setShort(int i, int i2) {
        unwrap().setShort(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        unwrap().setShort(i, i2);
    }

    public ByteBuf setShortLE(int i, int i2) {
        unwrap().setShortLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        unwrap().setShortLE(i, i2);
    }

    public ByteBuf setMedium(int i, int i2) {
        unwrap().setMedium(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        unwrap().setMedium(i, i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        unwrap().setMediumLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        unwrap().setMediumLE(i, i2);
    }

    public ByteBuf setInt(int i, int i2) {
        unwrap().setInt(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        unwrap().setInt(i, i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        unwrap().setIntLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        unwrap().setIntLE(i, i2);
    }

    public ByteBuf setLong(int i, long j) {
        unwrap().setLong(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        unwrap().setLong(i, j);
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

    public int nioBufferCount() {
        return unwrap().nioBufferCount();
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
}
