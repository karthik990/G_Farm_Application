package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import p043io.netty.util.ByteProcessor;

/* renamed from: io.netty.buffer.WrappedCompositeByteBuf */
class WrappedCompositeByteBuf extends CompositeByteBuf {
    private final CompositeByteBuf wrapped;

    WrappedCompositeByteBuf(CompositeByteBuf compositeByteBuf) {
        super(compositeByteBuf.alloc());
        this.wrapped = compositeByteBuf;
    }

    public boolean release() {
        return this.wrapped.release();
    }

    public boolean release(int i) {
        return this.wrapped.release(i);
    }

    public final int maxCapacity() {
        return this.wrapped.maxCapacity();
    }

    public final int readerIndex() {
        return this.wrapped.readerIndex();
    }

    public final int writerIndex() {
        return this.wrapped.writerIndex();
    }

    public final boolean isReadable() {
        return this.wrapped.isReadable();
    }

    public final boolean isReadable(int i) {
        return this.wrapped.isReadable(i);
    }

    public final boolean isWritable() {
        return this.wrapped.isWritable();
    }

    public final boolean isWritable(int i) {
        return this.wrapped.isWritable(i);
    }

    public final int readableBytes() {
        return this.wrapped.readableBytes();
    }

    public final int writableBytes() {
        return this.wrapped.writableBytes();
    }

    public final int maxWritableBytes() {
        return this.wrapped.maxWritableBytes();
    }

    public int ensureWritable(int i, boolean z) {
        return this.wrapped.ensureWritable(i, z);
    }

    public ByteBuf order(ByteOrder byteOrder) {
        return this.wrapped.order(byteOrder);
    }

    public boolean getBoolean(int i) {
        return this.wrapped.getBoolean(i);
    }

    public short getUnsignedByte(int i) {
        return this.wrapped.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.wrapped.getShort(i);
    }

    public short getShortLE(int i) {
        return this.wrapped.getShortLE(i);
    }

    public int getUnsignedShort(int i) {
        return this.wrapped.getUnsignedShort(i);
    }

    public int getUnsignedShortLE(int i) {
        return this.wrapped.getUnsignedShortLE(i);
    }

    public int getUnsignedMedium(int i) {
        return this.wrapped.getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return this.wrapped.getUnsignedMediumLE(i);
    }

    public int getMedium(int i) {
        return this.wrapped.getMedium(i);
    }

    public int getMediumLE(int i) {
        return this.wrapped.getMediumLE(i);
    }

    public int getInt(int i) {
        return this.wrapped.getInt(i);
    }

    public int getIntLE(int i) {
        return this.wrapped.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        return this.wrapped.getUnsignedInt(i);
    }

    public long getUnsignedIntLE(int i) {
        return this.wrapped.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        return this.wrapped.getLong(i);
    }

    public long getLongLE(int i) {
        return this.wrapped.getLongLE(i);
    }

    public char getChar(int i) {
        return this.wrapped.getChar(i);
    }

    public float getFloat(int i) {
        return this.wrapped.getFloat(i);
    }

    public double getDouble(int i) {
        return this.wrapped.getDouble(i);
    }

    public ByteBuf setShortLE(int i, int i2) {
        return this.wrapped.setShortLE(i, i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        return this.wrapped.setMediumLE(i, i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        return this.wrapped.setIntLE(i, i2);
    }

    public ByteBuf setLongLE(int i, long j) {
        return this.wrapped.setLongLE(i, j);
    }

    public byte readByte() {
        return this.wrapped.readByte();
    }

    public boolean readBoolean() {
        return this.wrapped.readBoolean();
    }

    public short readUnsignedByte() {
        return this.wrapped.readUnsignedByte();
    }

    public short readShort() {
        return this.wrapped.readShort();
    }

    public short readShortLE() {
        return this.wrapped.readShortLE();
    }

    public int readUnsignedShort() {
        return this.wrapped.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.wrapped.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.wrapped.readMedium();
    }

    public int readMediumLE() {
        return this.wrapped.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.wrapped.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.wrapped.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.wrapped.readInt();
    }

    public int readIntLE() {
        return this.wrapped.readIntLE();
    }

    public long readUnsignedInt() {
        return this.wrapped.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.wrapped.readUnsignedIntLE();
    }

    public long readLong() {
        return this.wrapped.readLong();
    }

    public long readLongLE() {
        return this.wrapped.readLongLE();
    }

    public char readChar() {
        return this.wrapped.readChar();
    }

    public float readFloat() {
        return this.wrapped.readFloat();
    }

    public double readDouble() {
        return this.wrapped.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.wrapped.readBytes(i);
    }

    public ByteBuf slice() {
        return this.wrapped.slice();
    }

    public ByteBuf retainedSlice() {
        return this.wrapped.retainedSlice();
    }

    public ByteBuf slice(int i, int i2) {
        return this.wrapped.slice(i, i2);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return this.wrapped.retainedSlice(i, i2);
    }

    public ByteBuffer nioBuffer() {
        return this.wrapped.nioBuffer();
    }

    public String toString(Charset charset) {
        return this.wrapped.toString(charset);
    }

    public String toString(int i, int i2, Charset charset) {
        return this.wrapped.toString(i, i2, charset);
    }

    public int indexOf(int i, int i2, byte b) {
        return this.wrapped.indexOf(i, i2, b);
    }

    public int bytesBefore(byte b) {
        return this.wrapped.bytesBefore(b);
    }

    public int bytesBefore(int i, byte b) {
        return this.wrapped.bytesBefore(i, b);
    }

    public int bytesBefore(int i, int i2, byte b) {
        return this.wrapped.bytesBefore(i, i2, b);
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        return this.wrapped.forEachByte(byteProcessor);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return this.wrapped.forEachByte(i, i2, byteProcessor);
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.wrapped.forEachByteDesc(byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return this.wrapped.forEachByteDesc(i, i2, byteProcessor);
    }

    public final int hashCode() {
        return this.wrapped.hashCode();
    }

    public final boolean equals(Object obj) {
        return this.wrapped.equals(obj);
    }

    public final int compareTo(ByteBuf byteBuf) {
        return this.wrapped.compareTo(byteBuf);
    }

    public final int refCnt() {
        return this.wrapped.refCnt();
    }

    public ByteBuf duplicate() {
        return this.wrapped.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.wrapped.retainedDuplicate();
    }

    public ByteBuf readSlice(int i) {
        return this.wrapped.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        return this.wrapped.readRetainedSlice(i);
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return this.wrapped.readBytes(gatheringByteChannel, i);
    }

    public ByteBuf writeShortLE(int i) {
        return this.wrapped.writeShortLE(i);
    }

    public ByteBuf writeMediumLE(int i) {
        return this.wrapped.writeMediumLE(i);
    }

    public ByteBuf writeIntLE(int i) {
        return this.wrapped.writeIntLE(i);
    }

    public ByteBuf writeLongLE(long j) {
        return this.wrapped.writeLongLE(j);
    }

    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return this.wrapped.writeBytes(inputStream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return this.wrapped.writeBytes(scatteringByteChannel, i);
    }

    public ByteBuf copy() {
        return this.wrapped.copy();
    }

    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        this.wrapped.addComponent(byteBuf);
        return this;
    }

    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        this.wrapped.addComponents(byteBufArr);
        return this;
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(iterable);
        return this;
    }

    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        this.wrapped.addComponent(i, byteBuf);
        return this;
    }

    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        this.wrapped.addComponents(i, byteBufArr);
        return this;
    }

    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(i, iterable);
        return this;
    }

    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        this.wrapped.addComponent(z, byteBuf);
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        this.wrapped.addComponents(z, byteBufArr);
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(z, iterable);
        return this;
    }

    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        this.wrapped.addComponent(z, i, byteBuf);
        return this;
    }

    public CompositeByteBuf removeComponent(int i) {
        this.wrapped.removeComponent(i);
        return this;
    }

    public CompositeByteBuf removeComponents(int i, int i2) {
        this.wrapped.removeComponents(i, i2);
        return this;
    }

    public Iterator<ByteBuf> iterator() {
        return this.wrapped.iterator();
    }

    public List<ByteBuf> decompose(int i, int i2) {
        return this.wrapped.decompose(i, i2);
    }

    public final boolean isDirect() {
        return this.wrapped.isDirect();
    }

    public final boolean hasArray() {
        return this.wrapped.hasArray();
    }

    public final byte[] array() {
        return this.wrapped.array();
    }

    public final int arrayOffset() {
        return this.wrapped.arrayOffset();
    }

    public final boolean hasMemoryAddress() {
        return this.wrapped.hasMemoryAddress();
    }

    public final long memoryAddress() {
        return this.wrapped.memoryAddress();
    }

    public final int capacity() {
        return this.wrapped.capacity();
    }

    public CompositeByteBuf capacity(int i) {
        this.wrapped.capacity(i);
        return this;
    }

    public final ByteBufAllocator alloc() {
        return this.wrapped.alloc();
    }

    public final ByteOrder order() {
        return this.wrapped.order();
    }

    public final int numComponents() {
        return this.wrapped.numComponents();
    }

    public final int maxNumComponents() {
        return this.wrapped.maxNumComponents();
    }

    public final int toComponentIndex(int i) {
        return this.wrapped.toComponentIndex(i);
    }

    public final int toByteIndex(int i) {
        return this.wrapped.toByteIndex(i);
    }

    public byte getByte(int i) {
        return this.wrapped.getByte(i);
    }

    /* access modifiers changed from: protected */
    public final byte _getByte(int i) {
        return this.wrapped._getByte(i);
    }

    /* access modifiers changed from: protected */
    public final short _getShort(int i) {
        return this.wrapped._getShort(i);
    }

    /* access modifiers changed from: protected */
    public final short _getShortLE(int i) {
        return this.wrapped._getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public final int _getUnsignedMedium(int i) {
        return this.wrapped._getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public final int _getUnsignedMediumLE(int i) {
        return this.wrapped._getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public final int _getInt(int i) {
        return this.wrapped._getInt(i);
    }

    /* access modifiers changed from: protected */
    public final int _getIntLE(int i) {
        return this.wrapped._getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public final long _getLong(int i) {
        return this.wrapped._getLong(i);
    }

    /* access modifiers changed from: protected */
    public final long _getLongLE(int i) {
        return this.wrapped._getLongLE(i);
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        this.wrapped.getBytes(i, bArr, i2, i3);
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        this.wrapped.getBytes(i, byteBuffer);
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.wrapped.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return this.wrapped.getBytes(i, gatheringByteChannel, i2);
    }

    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        this.wrapped.getBytes(i, outputStream, i2);
        return this;
    }

    public CompositeByteBuf setByte(int i, int i2) {
        this.wrapped.setByte(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void _setByte(int i, int i2) {
        this.wrapped._setByte(i, i2);
    }

    public CompositeByteBuf setShort(int i, int i2) {
        this.wrapped.setShort(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void _setShort(int i, int i2) {
        this.wrapped._setShort(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void _setShortLE(int i, int i2) {
        this.wrapped._setShortLE(i, i2);
    }

    public CompositeByteBuf setMedium(int i, int i2) {
        this.wrapped.setMedium(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void _setMedium(int i, int i2) {
        this.wrapped._setMedium(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void _setMediumLE(int i, int i2) {
        this.wrapped._setMediumLE(i, i2);
    }

    public CompositeByteBuf setInt(int i, int i2) {
        this.wrapped.setInt(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void _setInt(int i, int i2) {
        this.wrapped._setInt(i, i2);
    }

    /* access modifiers changed from: protected */
    public final void _setIntLE(int i, int i2) {
        this.wrapped._setIntLE(i, i2);
    }

    public CompositeByteBuf setLong(int i, long j) {
        this.wrapped.setLong(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void _setLong(int i, long j) {
        this.wrapped._setLong(i, j);
    }

    /* access modifiers changed from: protected */
    public final void _setLongLE(int i, long j) {
        this.wrapped._setLongLE(i, j);
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        this.wrapped.setBytes(i, bArr, i2, i3);
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        this.wrapped.setBytes(i, byteBuffer);
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.wrapped.setBytes(i, byteBuf, i2, i3);
        return this;
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return this.wrapped.setBytes(i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return this.wrapped.setBytes(i, scatteringByteChannel, i2);
    }

    public ByteBuf copy(int i, int i2) {
        return this.wrapped.copy(i, i2);
    }

    public final ByteBuf component(int i) {
        return this.wrapped.component(i);
    }

    public final ByteBuf componentAtOffset(int i) {
        return this.wrapped.componentAtOffset(i);
    }

    public final ByteBuf internalComponent(int i) {
        return this.wrapped.internalComponent(i);
    }

    public final ByteBuf internalComponentAtOffset(int i) {
        return this.wrapped.internalComponentAtOffset(i);
    }

    public int nioBufferCount() {
        return this.wrapped.nioBufferCount();
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        return this.wrapped.internalNioBuffer(i, i2);
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        return this.wrapped.nioBuffer(i, i2);
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return this.wrapped.nioBuffers(i, i2);
    }

    public CompositeByteBuf consolidate() {
        this.wrapped.consolidate();
        return this;
    }

    public CompositeByteBuf consolidate(int i, int i2) {
        this.wrapped.consolidate(i, i2);
        return this;
    }

    public CompositeByteBuf discardReadComponents() {
        this.wrapped.discardReadComponents();
        return this;
    }

    public CompositeByteBuf discardReadBytes() {
        this.wrapped.discardReadBytes();
        return this;
    }

    public final String toString() {
        return this.wrapped.toString();
    }

    public final CompositeByteBuf readerIndex(int i) {
        this.wrapped.readerIndex(i);
        return this;
    }

    public final CompositeByteBuf writerIndex(int i) {
        this.wrapped.writerIndex(i);
        return this;
    }

    public final CompositeByteBuf setIndex(int i, int i2) {
        this.wrapped.setIndex(i, i2);
        return this;
    }

    public final CompositeByteBuf clear() {
        this.wrapped.clear();
        return this;
    }

    public final CompositeByteBuf markReaderIndex() {
        this.wrapped.markReaderIndex();
        return this;
    }

    public final CompositeByteBuf resetReaderIndex() {
        this.wrapped.resetReaderIndex();
        return this;
    }

    public final CompositeByteBuf markWriterIndex() {
        this.wrapped.markWriterIndex();
        return this;
    }

    public final CompositeByteBuf resetWriterIndex() {
        this.wrapped.resetWriterIndex();
        return this;
    }

    public CompositeByteBuf ensureWritable(int i) {
        this.wrapped.ensureWritable(i);
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        this.wrapped.getBytes(i, byteBuf);
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        this.wrapped.getBytes(i, byteBuf, i2);
        return this;
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        this.wrapped.getBytes(i, bArr);
        return this;
    }

    public CompositeByteBuf setBoolean(int i, boolean z) {
        this.wrapped.setBoolean(i, z);
        return this;
    }

    public CompositeByteBuf setChar(int i, int i2) {
        this.wrapped.setChar(i, i2);
        return this;
    }

    public CompositeByteBuf setFloat(int i, float f) {
        this.wrapped.setFloat(i, f);
        return this;
    }

    public CompositeByteBuf setDouble(int i, double d) {
        this.wrapped.setDouble(i, d);
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        this.wrapped.setBytes(i, byteBuf);
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        this.wrapped.setBytes(i, byteBuf, i2);
        return this;
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        this.wrapped.setBytes(i, bArr);
        return this;
    }

    public CompositeByteBuf setZero(int i, int i2) {
        this.wrapped.setZero(i, i2);
        return this;
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        this.wrapped.readBytes(byteBuf);
        return this;
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        this.wrapped.readBytes(byteBuf, i);
        return this;
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        this.wrapped.readBytes(byteBuf, i, i2);
        return this;
    }

    public CompositeByteBuf readBytes(byte[] bArr) {
        this.wrapped.readBytes(bArr);
        return this;
    }

    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        this.wrapped.readBytes(bArr, i, i2);
        return this;
    }

    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        this.wrapped.readBytes(byteBuffer);
        return this;
    }

    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        this.wrapped.readBytes(outputStream, i);
        return this;
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.wrapped.getBytes(i, fileChannel, j, i2);
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.wrapped.setBytes(i, fileChannel, j, i2);
    }

    public boolean isReadOnly() {
        return this.wrapped.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.wrapped.asReadOnly();
    }

    /* access modifiers changed from: protected */
    public SwappedByteBuf newSwappedByteBuf() {
        return this.wrapped.newSwappedByteBuf();
    }

    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        return this.wrapped.getCharSequence(i, i2, charset);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return this.wrapped.readCharSequence(i, charset);
    }

    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return this.wrapped.setCharSequence(i, charSequence, charset);
    }

    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.wrapped.readBytes(fileChannel, j, i);
    }

    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.wrapped.writeBytes(fileChannel, j, i);
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.wrapped.writeCharSequence(charSequence, charset);
    }

    public CompositeByteBuf skipBytes(int i) {
        this.wrapped.skipBytes(i);
        return this;
    }

    public CompositeByteBuf writeBoolean(boolean z) {
        this.wrapped.writeBoolean(z);
        return this;
    }

    public CompositeByteBuf writeByte(int i) {
        this.wrapped.writeByte(i);
        return this;
    }

    public CompositeByteBuf writeShort(int i) {
        this.wrapped.writeShort(i);
        return this;
    }

    public CompositeByteBuf writeMedium(int i) {
        this.wrapped.writeMedium(i);
        return this;
    }

    public CompositeByteBuf writeInt(int i) {
        this.wrapped.writeInt(i);
        return this;
    }

    public CompositeByteBuf writeLong(long j) {
        this.wrapped.writeLong(j);
        return this;
    }

    public CompositeByteBuf writeChar(int i) {
        this.wrapped.writeChar(i);
        return this;
    }

    public CompositeByteBuf writeFloat(float f) {
        this.wrapped.writeFloat(f);
        return this;
    }

    public CompositeByteBuf writeDouble(double d) {
        this.wrapped.writeDouble(d);
        return this;
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        this.wrapped.writeBytes(byteBuf);
        return this;
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        this.wrapped.writeBytes(byteBuf, i);
        return this;
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        this.wrapped.writeBytes(byteBuf, i, i2);
        return this;
    }

    public CompositeByteBuf writeBytes(byte[] bArr) {
        this.wrapped.writeBytes(bArr);
        return this;
    }

    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        this.wrapped.writeBytes(bArr, i, i2);
        return this;
    }

    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.wrapped.writeBytes(byteBuffer);
        return this;
    }

    public CompositeByteBuf writeZero(int i) {
        this.wrapped.writeZero(i);
        return this;
    }

    public CompositeByteBuf retain(int i) {
        this.wrapped.retain(i);
        return this;
    }

    public CompositeByteBuf retain() {
        this.wrapped.retain();
        return this;
    }

    public CompositeByteBuf touch() {
        this.wrapped.touch();
        return this;
    }

    public CompositeByteBuf touch(Object obj) {
        this.wrapped.touch(obj);
        return this;
    }

    public ByteBuffer[] nioBuffers() {
        return this.wrapped.nioBuffers();
    }

    public CompositeByteBuf discardSomeReadBytes() {
        this.wrapped.discardSomeReadBytes();
        return this;
    }

    public final void deallocate() {
        this.wrapped.deallocate();
    }

    public final ByteBuf unwrap() {
        return this.wrapped;
    }
}
