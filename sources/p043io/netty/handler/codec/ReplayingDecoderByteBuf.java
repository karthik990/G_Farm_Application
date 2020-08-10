package p043io.netty.handler.codec;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.SwappedByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.ByteProcessor;
import p043io.netty.util.Signal;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.handler.codec.ReplayingDecoderByteBuf */
final class ReplayingDecoderByteBuf extends ByteBuf {
    static final ReplayingDecoderByteBuf EMPTY_BUFFER = new ReplayingDecoderByteBuf(Unpooled.EMPTY_BUFFER);
    private static final Signal REPLAY = ReplayingDecoder.REPLAY;
    private ByteBuf buffer;
    private SwappedByteBuf swapped;
    private boolean terminated;

    public boolean equals(Object obj) {
        return this == obj;
    }

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public boolean isReadOnly() {
        return false;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int i) {
        return false;
    }

    public int maxWritableBytes() {
        return 0;
    }

    public int writableBytes() {
        return 0;
    }

    static {
        EMPTY_BUFFER.terminate();
    }

    ReplayingDecoderByteBuf() {
    }

    ReplayingDecoderByteBuf(ByteBuf byteBuf) {
        setCumulation(byteBuf);
    }

    /* access modifiers changed from: 0000 */
    public void setCumulation(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    /* access modifiers changed from: 0000 */
    public void terminate() {
        this.terminated = true;
    }

    public int capacity() {
        if (this.terminated) {
            return this.buffer.capacity();
        }
        return Integer.MAX_VALUE;
    }

    public ByteBuf capacity(int i) {
        throw reject();
    }

    public int maxCapacity() {
        return capacity();
    }

    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }

    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer((ByteBuf) this);
    }

    public boolean isDirect() {
        return this.buffer.isDirect();
    }

    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    public ByteBuf clear() {
        throw reject();
    }

    public int compareTo(ByteBuf byteBuf) {
        throw reject();
    }

    public ByteBuf copy() {
        throw reject();
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.copy(i, i2);
    }

    public ByteBuf discardReadBytes() {
        throw reject();
    }

    public ByteBuf ensureWritable(int i) {
        throw reject();
    }

    public int ensureWritable(int i, boolean z) {
        throw reject();
    }

    public ByteBuf duplicate() {
        throw reject();
    }

    public ByteBuf retainedDuplicate() {
        throw reject();
    }

    public boolean getBoolean(int i) {
        checkIndex(i, 1);
        return this.buffer.getBoolean(i);
    }

    public byte getByte(int i) {
        checkIndex(i, 1);
        return this.buffer.getByte(i);
    }

    public short getUnsignedByte(int i) {
        checkIndex(i, 1);
        return this.buffer.getUnsignedByte(i);
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkIndex(i, i3);
        this.buffer.getBytes(i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr) {
        checkIndex(i, bArr.length);
        this.buffer.getBytes(i, bArr);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        throw reject();
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkIndex(i, i3);
        this.buffer.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        throw reject();
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        throw reject();
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) {
        throw reject();
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw reject();
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) {
        throw reject();
    }

    public int getInt(int i) {
        checkIndex(i, 4);
        return this.buffer.getInt(i);
    }

    public int getIntLE(int i) {
        checkIndex(i, 4);
        return this.buffer.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        checkIndex(i, 4);
        return this.buffer.getUnsignedInt(i);
    }

    public long getUnsignedIntLE(int i) {
        checkIndex(i, 4);
        return this.buffer.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        checkIndex(i, 8);
        return this.buffer.getLong(i);
    }

    public long getLongLE(int i) {
        checkIndex(i, 8);
        return this.buffer.getLongLE(i);
    }

    public int getMedium(int i) {
        checkIndex(i, 3);
        return this.buffer.getMedium(i);
    }

    public int getMediumLE(int i) {
        checkIndex(i, 3);
        return this.buffer.getMediumLE(i);
    }

    public int getUnsignedMedium(int i) {
        checkIndex(i, 3);
        return this.buffer.getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        checkIndex(i, 3);
        return this.buffer.getUnsignedMediumLE(i);
    }

    public short getShort(int i) {
        checkIndex(i, 2);
        return this.buffer.getShort(i);
    }

    public short getShortLE(int i) {
        checkIndex(i, 2);
        return this.buffer.getShortLE(i);
    }

    public int getUnsignedShort(int i) {
        checkIndex(i, 2);
        return this.buffer.getUnsignedShort(i);
    }

    public int getUnsignedShortLE(int i) {
        checkIndex(i, 2);
        return this.buffer.getUnsignedShortLE(i);
    }

    public char getChar(int i) {
        checkIndex(i, 2);
        return this.buffer.getChar(i);
    }

    public float getFloat(int i) {
        checkIndex(i, 4);
        return this.buffer.getFloat(i);
    }

    public double getDouble(int i) {
        checkIndex(i, 8);
        return this.buffer.getDouble(i);
    }

    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        checkIndex(i, i2);
        return this.buffer.getCharSequence(i, i2, charset);
    }

    public int hashCode() {
        throw reject();
    }

    public int indexOf(int i, int i2, byte b) {
        if (i == i2) {
            return -1;
        }
        if (Math.max(i, i2) <= this.buffer.writerIndex()) {
            return this.buffer.indexOf(i, i2, b);
        }
        throw REPLAY;
    }

    public int bytesBefore(byte b) {
        int bytesBefore = this.buffer.bytesBefore(b);
        if (bytesBefore >= 0) {
            return bytesBefore;
        }
        throw REPLAY;
    }

    public int bytesBefore(int i, byte b) {
        return bytesBefore(this.buffer.readerIndex(), i, b);
    }

    public int bytesBefore(int i, int i2, byte b) {
        int writerIndex = this.buffer.writerIndex();
        if (i >= writerIndex) {
            throw REPLAY;
        } else if (i <= writerIndex - i2) {
            return this.buffer.bytesBefore(i, i2, b);
        } else {
            int bytesBefore = this.buffer.bytesBefore(i, writerIndex - i, b);
            if (bytesBefore >= 0) {
                return bytesBefore;
            }
            throw REPLAY;
        }
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        int forEachByte = this.buffer.forEachByte(byteProcessor);
        if (forEachByte >= 0) {
            return forEachByte;
        }
        throw REPLAY;
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        int writerIndex = this.buffer.writerIndex();
        if (i >= writerIndex) {
            throw REPLAY;
        } else if (i <= writerIndex - i2) {
            return this.buffer.forEachByte(i, i2, byteProcessor);
        } else {
            int forEachByte = this.buffer.forEachByte(i, writerIndex - i, byteProcessor);
            if (forEachByte >= 0) {
                return forEachByte;
            }
            throw REPLAY;
        }
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        if (this.terminated) {
            return this.buffer.forEachByteDesc(byteProcessor);
        }
        throw reject();
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        if (i + i2 <= this.buffer.writerIndex()) {
            return this.buffer.forEachByteDesc(i, i2, byteProcessor);
        }
        throw REPLAY;
    }

    public ByteBuf markReaderIndex() {
        this.buffer.markReaderIndex();
        return this;
    }

    public ByteBuf markWriterIndex() {
        throw reject();
    }

    public ByteOrder order() {
        return this.buffer.order();
    }

    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == order()) {
            return this;
        } else {
            SwappedByteBuf swappedByteBuf = this.swapped;
            if (swappedByteBuf == null) {
                swappedByteBuf = new SwappedByteBuf(this);
                this.swapped = swappedByteBuf;
            }
            return swappedByteBuf;
        }
    }

    public boolean isReadable() {
        if (this.terminated) {
            return this.buffer.isReadable();
        }
        return true;
    }

    public boolean isReadable(int i) {
        if (this.terminated) {
            return this.buffer.isReadable(i);
        }
        return true;
    }

    public int readableBytes() {
        if (this.terminated) {
            return this.buffer.readableBytes();
        }
        return Integer.MAX_VALUE - this.buffer.readerIndex();
    }

    public boolean readBoolean() {
        checkReadableBytes(1);
        return this.buffer.readBoolean();
    }

    public byte readByte() {
        checkReadableBytes(1);
        return this.buffer.readByte();
    }

    public short readUnsignedByte() {
        checkReadableBytes(1);
        return this.buffer.readUnsignedByte();
    }

    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        checkReadableBytes(i2);
        this.buffer.readBytes(bArr, i, i2);
        return this;
    }

    public ByteBuf readBytes(byte[] bArr) {
        checkReadableBytes(bArr.length);
        this.buffer.readBytes(bArr);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        throw reject();
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        checkReadableBytes(i2);
        this.buffer.readBytes(byteBuf, i, i2);
        return this;
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        throw reject();
    }

    public ByteBuf readBytes(ByteBuf byteBuf) {
        checkReadableBytes(byteBuf.writableBytes());
        this.buffer.readBytes(byteBuf);
        return this;
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) {
        throw reject();
    }

    public int readBytes(FileChannel fileChannel, long j, int i) {
        throw reject();
    }

    public ByteBuf readBytes(int i) {
        checkReadableBytes(i);
        return this.buffer.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        checkReadableBytes(i);
        return this.buffer.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        checkReadableBytes(i);
        return this.buffer.readRetainedSlice(i);
    }

    public ByteBuf readBytes(OutputStream outputStream, int i) {
        throw reject();
    }

    public int readerIndex() {
        return this.buffer.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        this.buffer.readerIndex(i);
        return this;
    }

    public int readInt() {
        checkReadableBytes(4);
        return this.buffer.readInt();
    }

    public int readIntLE() {
        checkReadableBytes(4);
        return this.buffer.readIntLE();
    }

    public long readUnsignedInt() {
        checkReadableBytes(4);
        return this.buffer.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        checkReadableBytes(4);
        return this.buffer.readUnsignedIntLE();
    }

    public long readLong() {
        checkReadableBytes(8);
        return this.buffer.readLong();
    }

    public long readLongLE() {
        checkReadableBytes(8);
        return this.buffer.readLongLE();
    }

    public int readMedium() {
        checkReadableBytes(3);
        return this.buffer.readMedium();
    }

    public int readMediumLE() {
        checkReadableBytes(3);
        return this.buffer.readMediumLE();
    }

    public int readUnsignedMedium() {
        checkReadableBytes(3);
        return this.buffer.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        checkReadableBytes(3);
        return this.buffer.readUnsignedMediumLE();
    }

    public short readShort() {
        checkReadableBytes(2);
        return this.buffer.readShort();
    }

    public short readShortLE() {
        checkReadableBytes(2);
        return this.buffer.readShortLE();
    }

    public int readUnsignedShort() {
        checkReadableBytes(2);
        return this.buffer.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        checkReadableBytes(2);
        return this.buffer.readUnsignedShortLE();
    }

    public char readChar() {
        checkReadableBytes(2);
        return this.buffer.readChar();
    }

    public float readFloat() {
        checkReadableBytes(4);
        return this.buffer.readFloat();
    }

    public double readDouble() {
        checkReadableBytes(8);
        return this.buffer.readDouble();
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        checkReadableBytes(i);
        return this.buffer.readCharSequence(i, charset);
    }

    public ByteBuf resetReaderIndex() {
        this.buffer.resetReaderIndex();
        return this;
    }

    public ByteBuf resetWriterIndex() {
        throw reject();
    }

    public ByteBuf setBoolean(int i, boolean z) {
        throw reject();
    }

    public ByteBuf setByte(int i, int i2) {
        throw reject();
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        throw reject();
    }

    public ByteBuf setBytes(int i, byte[] bArr) {
        throw reject();
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        throw reject();
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        throw reject();
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        throw reject();
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        throw reject();
    }

    public int setBytes(int i, InputStream inputStream, int i2) {
        throw reject();
    }

    public ByteBuf setZero(int i, int i2) {
        throw reject();
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) {
        throw reject();
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw reject();
    }

    public ByteBuf setIndex(int i, int i2) {
        throw reject();
    }

    public ByteBuf setInt(int i, int i2) {
        throw reject();
    }

    public ByteBuf setIntLE(int i, int i2) {
        throw reject();
    }

    public ByteBuf setLong(int i, long j) {
        throw reject();
    }

    public ByteBuf setLongLE(int i, long j) {
        throw reject();
    }

    public ByteBuf setMedium(int i, int i2) {
        throw reject();
    }

    public ByteBuf setMediumLE(int i, int i2) {
        throw reject();
    }

    public ByteBuf setShort(int i, int i2) {
        throw reject();
    }

    public ByteBuf setShortLE(int i, int i2) {
        throw reject();
    }

    public ByteBuf setChar(int i, int i2) {
        throw reject();
    }

    public ByteBuf setFloat(int i, float f) {
        throw reject();
    }

    public ByteBuf setDouble(int i, double d) {
        throw reject();
    }

    public ByteBuf skipBytes(int i) {
        checkReadableBytes(i);
        this.buffer.skipBytes(i);
        return this;
    }

    public ByteBuf slice() {
        throw reject();
    }

    public ByteBuf retainedSlice() {
        throw reject();
    }

    public ByteBuf slice(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.slice(i, i2);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.slice(i, i2);
    }

    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        throw reject();
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.nioBuffer(i, i2);
    }

    public ByteBuffer[] nioBuffers() {
        throw reject();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.nioBuffers(i, i2);
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return this.buffer.internalNioBuffer(i, i2);
    }

    public String toString(int i, int i2, Charset charset) {
        checkIndex(i, i2);
        return this.buffer.toString(i, i2, charset);
    }

    public String toString(Charset charset) {
        throw reject();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('(');
        sb.append("ridx=");
        sb.append(readerIndex());
        sb.append(", widx=");
        sb.append(writerIndex());
        sb.append(')');
        return sb.toString();
    }

    public ByteBuf writeBoolean(boolean z) {
        throw reject();
    }

    public ByteBuf writeByte(int i) {
        throw reject();
    }

    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        throw reject();
    }

    public ByteBuf writeBytes(byte[] bArr) {
        throw reject();
    }

    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        throw reject();
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        throw reject();
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        throw reject();
    }

    public ByteBuf writeBytes(ByteBuf byteBuf) {
        throw reject();
    }

    public int writeBytes(InputStream inputStream, int i) {
        throw reject();
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) {
        throw reject();
    }

    public int writeBytes(FileChannel fileChannel, long j, int i) {
        throw reject();
    }

    public ByteBuf writeInt(int i) {
        throw reject();
    }

    public ByteBuf writeIntLE(int i) {
        throw reject();
    }

    public ByteBuf writeLong(long j) {
        throw reject();
    }

    public ByteBuf writeLongLE(long j) {
        throw reject();
    }

    public ByteBuf writeMedium(int i) {
        throw reject();
    }

    public ByteBuf writeMediumLE(int i) {
        throw reject();
    }

    public ByteBuf writeZero(int i) {
        throw reject();
    }

    public int writerIndex() {
        return this.buffer.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        throw reject();
    }

    public ByteBuf writeShort(int i) {
        throw reject();
    }

    public ByteBuf writeShortLE(int i) {
        throw reject();
    }

    public ByteBuf writeChar(int i) {
        throw reject();
    }

    public ByteBuf writeFloat(float f) {
        throw reject();
    }

    public ByteBuf writeDouble(double d) {
        throw reject();
    }

    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        throw reject();
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        throw reject();
    }

    private void checkIndex(int i, int i2) {
        if (i + i2 > this.buffer.writerIndex()) {
            throw REPLAY;
        }
    }

    private void checkReadableBytes(int i) {
        if (this.buffer.readableBytes() < i) {
            throw REPLAY;
        }
    }

    public ByteBuf discardSomeReadBytes() {
        throw reject();
    }

    public int refCnt() {
        return this.buffer.refCnt();
    }

    public ByteBuf retain() {
        throw reject();
    }

    public ByteBuf retain(int i) {
        throw reject();
    }

    public ByteBuf touch() {
        this.buffer.touch();
        return this;
    }

    public ByteBuf touch(Object obj) {
        this.buffer.touch(obj);
        return this;
    }

    public boolean release() {
        throw reject();
    }

    public boolean release(int i) {
        throw reject();
    }

    public ByteBuf unwrap() {
        throw reject();
    }

    private static UnsupportedOperationException reject() {
        return new UnsupportedOperationException("not a replayable operation");
    }
}
