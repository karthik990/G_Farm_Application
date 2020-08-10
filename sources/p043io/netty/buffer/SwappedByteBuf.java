package p043io.netty.buffer;

import androidx.core.view.ViewCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import kotlin.UShort;
import p043io.netty.util.ByteProcessor;

@Deprecated
/* renamed from: io.netty.buffer.SwappedByteBuf */
public class SwappedByteBuf extends ByteBuf {
    private final ByteBuf buf;
    private final ByteOrder order;

    public SwappedByteBuf(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.buf = byteBuf;
            if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
                this.order = ByteOrder.LITTLE_ENDIAN;
            } else {
                this.order = ByteOrder.BIG_ENDIAN;
            }
        } else {
            throw new NullPointerException("buf");
        }
    }

    public ByteOrder order() {
        return this.order;
    }

    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == this.order) {
            return this;
        } else {
            return this.buf;
        }
    }

    public ByteBuf unwrap() {
        return this.buf;
    }

    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    public int capacity() {
        return this.buf.capacity();
    }

    public ByteBuf capacity(int i) {
        this.buf.capacity(i);
        return this;
    }

    public int maxCapacity() {
        return this.buf.maxCapacity();
    }

    public boolean isReadOnly() {
        return this.buf.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer((ByteBuf) this);
    }

    public boolean isDirect() {
        return this.buf.isDirect();
    }

    public int readerIndex() {
        return this.buf.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        this.buf.readerIndex(i);
        return this;
    }

    public int writerIndex() {
        return this.buf.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        this.buf.writerIndex(i);
        return this;
    }

    public ByteBuf setIndex(int i, int i2) {
        this.buf.setIndex(i, i2);
        return this;
    }

    public int readableBytes() {
        return this.buf.readableBytes();
    }

    public int writableBytes() {
        return this.buf.writableBytes();
    }

    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.buf.isReadable();
    }

    public boolean isReadable(int i) {
        return this.buf.isReadable(i);
    }

    public boolean isWritable() {
        return this.buf.isWritable();
    }

    public boolean isWritable(int i) {
        return this.buf.isWritable(i);
    }

    public ByteBuf clear() {
        this.buf.clear();
        return this;
    }

    public ByteBuf markReaderIndex() {
        this.buf.markReaderIndex();
        return this;
    }

    public ByteBuf resetReaderIndex() {
        this.buf.resetReaderIndex();
        return this;
    }

    public ByteBuf markWriterIndex() {
        this.buf.markWriterIndex();
        return this;
    }

    public ByteBuf resetWriterIndex() {
        this.buf.resetWriterIndex();
        return this;
    }

    public ByteBuf discardReadBytes() {
        this.buf.discardReadBytes();
        return this;
    }

    public ByteBuf discardSomeReadBytes() {
        this.buf.discardSomeReadBytes();
        return this;
    }

    public ByteBuf ensureWritable(int i) {
        this.buf.ensureWritable(i);
        return this;
    }

    public int ensureWritable(int i, boolean z) {
        return this.buf.ensureWritable(i, z);
    }

    public boolean getBoolean(int i) {
        return this.buf.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.buf.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.buf.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return ByteBufUtil.swapShort(this.buf.getShort(i));
    }

    public short getShortLE(int i) {
        return this.buf.getShort(i);
    }

    public int getUnsignedShort(int i) {
        return getShort(i) & UShort.MAX_VALUE;
    }

    public int getUnsignedShortLE(int i) {
        return getShortLE(i) & UShort.MAX_VALUE;
    }

    public int getMedium(int i) {
        return ByteBufUtil.swapMedium(this.buf.getMedium(i));
    }

    public int getMediumLE(int i) {
        return this.buf.getMedium(i);
    }

    public int getUnsignedMedium(int i) {
        return getMedium(i) & ViewCompat.MEASURED_SIZE_MASK;
    }

    public int getUnsignedMediumLE(int i) {
        return getMediumLE(i) & ViewCompat.MEASURED_SIZE_MASK;
    }

    public int getInt(int i) {
        return ByteBufUtil.swapInt(this.buf.getInt(i));
    }

    public int getIntLE(int i) {
        return this.buf.getInt(i);
    }

    public long getUnsignedInt(int i) {
        return ((long) getInt(i)) & 4294967295L;
    }

    public long getUnsignedIntLE(int i) {
        return ((long) getIntLE(i)) & 4294967295L;
    }

    public long getLong(int i) {
        return ByteBufUtil.swapLong(this.buf.getLong(i));
    }

    public long getLongLE(int i) {
        return this.buf.getLong(i);
    }

    public char getChar(int i) {
        return (char) getShort(i);
    }

    public float getFloat(int i) {
        return Float.intBitsToFloat(getInt(i));
    }

    public double getDouble(int i) {
        return Double.longBitsToDouble(getLong(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        this.buf.getBytes(i, byteBuf);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        this.buf.getBytes(i, byteBuf, i2);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.buf.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr) {
        this.buf.getBytes(i, bArr);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        this.buf.getBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        this.buf.getBytes(i, byteBuffer);
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        this.buf.getBytes(i, outputStream, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return this.buf.getBytes(i, gatheringByteChannel, i2);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.buf.getBytes(i, fileChannel, j, i2);
    }

    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        return this.buf.getCharSequence(i, i2, charset);
    }

    public ByteBuf setBoolean(int i, boolean z) {
        this.buf.setBoolean(i, z);
        return this;
    }

    public ByteBuf setByte(int i, int i2) {
        this.buf.setByte(i, i2);
        return this;
    }

    public ByteBuf setShort(int i, int i2) {
        this.buf.setShort(i, ByteBufUtil.swapShort((short) i2));
        return this;
    }

    public ByteBuf setShortLE(int i, int i2) {
        this.buf.setShort(i, (short) i2);
        return this;
    }

    public ByteBuf setMedium(int i, int i2) {
        this.buf.setMedium(i, ByteBufUtil.swapMedium(i2));
        return this;
    }

    public ByteBuf setMediumLE(int i, int i2) {
        this.buf.setMedium(i, i2);
        return this;
    }

    public ByteBuf setInt(int i, int i2) {
        this.buf.setInt(i, ByteBufUtil.swapInt(i2));
        return this;
    }

    public ByteBuf setIntLE(int i, int i2) {
        this.buf.setInt(i, i2);
        return this;
    }

    public ByteBuf setLong(int i, long j) {
        this.buf.setLong(i, ByteBufUtil.swapLong(j));
        return this;
    }

    public ByteBuf setLongLE(int i, long j) {
        this.buf.setLong(i, j);
        return this;
    }

    public ByteBuf setChar(int i, int i2) {
        setShort(i, i2);
        return this;
    }

    public ByteBuf setFloat(int i, float f) {
        setInt(i, Float.floatToRawIntBits(f));
        return this;
    }

    public ByteBuf setDouble(int i, double d) {
        setLong(i, Double.doubleToRawLongBits(d));
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        this.buf.setBytes(i, byteBuf);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        this.buf.setBytes(i, byteBuf, i2);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.buf.setBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr) {
        this.buf.setBytes(i, bArr);
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        this.buf.setBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        this.buf.setBytes(i, byteBuffer);
        return this;
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return this.buf.setBytes(i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return this.buf.setBytes(i, scatteringByteChannel, i2);
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.buf.setBytes(i, fileChannel, j, i2);
    }

    public ByteBuf setZero(int i, int i2) {
        this.buf.setZero(i, i2);
        return this;
    }

    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return this.buf.setCharSequence(i, charSequence, charset);
    }

    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    public short readShort() {
        return ByteBufUtil.swapShort(this.buf.readShort());
    }

    public short readShortLE() {
        return this.buf.readShort();
    }

    public int readUnsignedShort() {
        return readShort() & UShort.MAX_VALUE;
    }

    public int readUnsignedShortLE() {
        return readShortLE() & UShort.MAX_VALUE;
    }

    public int readMedium() {
        return ByteBufUtil.swapMedium(this.buf.readMedium());
    }

    public int readMediumLE() {
        return this.buf.readMedium();
    }

    public int readUnsignedMedium() {
        return readMedium() & ViewCompat.MEASURED_SIZE_MASK;
    }

    public int readUnsignedMediumLE() {
        return readMediumLE() & ViewCompat.MEASURED_SIZE_MASK;
    }

    public int readInt() {
        return ByteBufUtil.swapInt(this.buf.readInt());
    }

    public int readIntLE() {
        return this.buf.readInt();
    }

    public long readUnsignedInt() {
        return ((long) readInt()) & 4294967295L;
    }

    public long readUnsignedIntLE() {
        return ((long) readIntLE()) & 4294967295L;
    }

    public long readLong() {
        return ByteBufUtil.swapLong(this.buf.readLong());
    }

    public long readLongLE() {
        return this.buf.readLong();
    }

    public char readChar() {
        return (char) readShort();
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public ByteBuf readBytes(int i) {
        return this.buf.readBytes(i).order(order());
    }

    public ByteBuf readSlice(int i) {
        return this.buf.readSlice(i).order(this.order);
    }

    public ByteBuf readRetainedSlice(int i) {
        return this.buf.readRetainedSlice(i).order(this.order);
    }

    public ByteBuf readBytes(ByteBuf byteBuf) {
        this.buf.readBytes(byteBuf);
        return this;
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        this.buf.readBytes(byteBuf, i);
        return this;
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        this.buf.readBytes(byteBuf, i, i2);
        return this;
    }

    public ByteBuf readBytes(byte[] bArr) {
        this.buf.readBytes(bArr);
        return this;
    }

    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        this.buf.readBytes(bArr, i, i2);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        this.buf.readBytes(byteBuffer);
        return this;
    }

    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        this.buf.readBytes(outputStream, i);
        return this;
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, i);
    }

    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.buf.readBytes(fileChannel, j, i);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return this.buf.readCharSequence(i, charset);
    }

    public ByteBuf skipBytes(int i) {
        this.buf.skipBytes(i);
        return this;
    }

    public ByteBuf writeBoolean(boolean z) {
        this.buf.writeBoolean(z);
        return this;
    }

    public ByteBuf writeByte(int i) {
        this.buf.writeByte(i);
        return this;
    }

    public ByteBuf writeShort(int i) {
        this.buf.writeShort(ByteBufUtil.swapShort((short) i));
        return this;
    }

    public ByteBuf writeShortLE(int i) {
        this.buf.writeShort((short) i);
        return this;
    }

    public ByteBuf writeMedium(int i) {
        this.buf.writeMedium(ByteBufUtil.swapMedium(i));
        return this;
    }

    public ByteBuf writeMediumLE(int i) {
        this.buf.writeMedium(i);
        return this;
    }

    public ByteBuf writeInt(int i) {
        this.buf.writeInt(ByteBufUtil.swapInt(i));
        return this;
    }

    public ByteBuf writeIntLE(int i) {
        this.buf.writeInt(i);
        return this;
    }

    public ByteBuf writeLong(long j) {
        this.buf.writeLong(ByteBufUtil.swapLong(j));
        return this;
    }

    public ByteBuf writeLongLE(long j) {
        this.buf.writeLong(j);
        return this;
    }

    public ByteBuf writeChar(int i) {
        writeShort(i);
        return this;
    }

    public ByteBuf writeFloat(float f) {
        writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    public ByteBuf writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
        return this;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf) {
        this.buf.writeBytes(byteBuf);
        return this;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        this.buf.writeBytes(byteBuf, i);
        return this;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        this.buf.writeBytes(byteBuf, i, i2);
        return this;
    }

    public ByteBuf writeBytes(byte[] bArr) {
        this.buf.writeBytes(bArr);
        return this;
    }

    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        this.buf.writeBytes(bArr, i, i2);
        return this;
    }

    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.buf.writeBytes(byteBuffer);
        return this;
    }

    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return this.buf.writeBytes(inputStream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, i);
    }

    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.buf.writeBytes(fileChannel, j, i);
    }

    public ByteBuf writeZero(int i) {
        this.buf.writeZero(i);
        return this;
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.buf.writeCharSequence(charSequence, charset);
    }

    public int indexOf(int i, int i2, byte b) {
        return this.buf.indexOf(i, i2, b);
    }

    public int bytesBefore(byte b) {
        return this.buf.bytesBefore(b);
    }

    public int bytesBefore(int i, byte b) {
        return this.buf.bytesBefore(i, b);
    }

    public int bytesBefore(int i, int i2, byte b) {
        return this.buf.bytesBefore(i, i2, b);
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        return this.buf.forEachByte(byteProcessor);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return this.buf.forEachByte(i, i2, byteProcessor);
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(i, i2, byteProcessor);
    }

    public ByteBuf copy() {
        return this.buf.copy().order(this.order);
    }

    public ByteBuf copy(int i, int i2) {
        return this.buf.copy(i, i2).order(this.order);
    }

    public ByteBuf slice() {
        return this.buf.slice().order(this.order);
    }

    public ByteBuf retainedSlice() {
        return this.buf.retainedSlice().order(this.order);
    }

    public ByteBuf slice(int i, int i2) {
        return this.buf.slice(i, i2).order(this.order);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return this.buf.retainedSlice(i, i2).order(this.order);
    }

    public ByteBuf duplicate() {
        return this.buf.duplicate().order(this.order);
    }

    public ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate().order(this.order);
    }

    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer().order(this.order);
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        return this.buf.nioBuffer(i, i2).order(this.order);
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        return nioBuffer(i, i2);
    }

    public ByteBuffer[] nioBuffers() {
        ByteBuffer[] nioBuffers = this.buf.nioBuffers();
        for (int i = 0; i < nioBuffers.length; i++) {
            nioBuffers[i] = nioBuffers[i].order(this.order);
        }
        return nioBuffers;
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        ByteBuffer[] nioBuffers = this.buf.nioBuffers(i, i2);
        for (int i3 = 0; i3 < nioBuffers.length; i3++) {
            nioBuffers[i3] = nioBuffers[i3].order(this.order);
        }
        return nioBuffers;
    }

    public boolean hasArray() {
        return this.buf.hasArray();
    }

    public byte[] array() {
        return this.buf.array();
    }

    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.buf.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    public String toString(int i, int i2, Charset charset) {
        return this.buf.toString(i, i2, charset);
    }

    public int refCnt() {
        return this.buf.refCnt();
    }

    public ByteBuf retain() {
        this.buf.retain();
        return this;
    }

    public ByteBuf retain(int i) {
        this.buf.retain(i);
        return this;
    }

    public ByteBuf touch() {
        this.buf.touch();
        return this;
    }

    public ByteBuf touch(Object obj) {
        this.buf.touch(obj);
        return this;
    }

    public boolean release() {
        return this.buf.release();
    }

    public boolean release(int i) {
        return this.buf.release(i);
    }

    public int hashCode() {
        return this.buf.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteBuf) {
            return ByteBufUtil.equals(this, (ByteBuf) obj);
        }
        return false;
    }

    public int compareTo(ByteBuf byteBuf) {
        return ByteBufUtil.compare(this, byteBuf);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Swapped(");
        sb.append(this.buf);
        sb.append(')');
        return sb.toString();
    }
}
