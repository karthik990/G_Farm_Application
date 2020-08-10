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
import p043io.netty.util.ResourceLeakTracker;

/* renamed from: io.netty.buffer.AdvancedLeakAwareCompositeByteBuf */
final class AdvancedLeakAwareCompositeByteBuf extends SimpleLeakAwareCompositeByteBuf {
    AdvancedLeakAwareCompositeByteBuf(CompositeByteBuf compositeByteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        super(compositeByteBuf, resourceLeakTracker);
    }

    public ByteBuf order(ByteOrder byteOrder) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.order(byteOrder);
    }

    public ByteBuf slice() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.slice();
    }

    public ByteBuf retainedSlice() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.retainedSlice();
    }

    public ByteBuf slice(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.slice(i, i2);
    }

    public ByteBuf retainedSlice(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.retainedSlice(i, i2);
    }

    public ByteBuf duplicate() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.retainedDuplicate();
    }

    public ByteBuf readSlice(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readRetainedSlice(i);
    }

    public ByteBuf asReadOnly() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.asReadOnly();
    }

    public boolean isReadOnly() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.isReadOnly();
    }

    public CompositeByteBuf discardReadBytes() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.discardReadBytes();
    }

    public CompositeByteBuf discardSomeReadBytes() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.discardSomeReadBytes();
    }

    public CompositeByteBuf ensureWritable(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean z) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.ensureWritable(i, z);
    }

    public boolean getBoolean(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBoolean(i);
    }

    public byte getByte(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getByte(i);
    }

    public short getUnsignedByte(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedByte(i);
    }

    public short getShort(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getShort(i);
    }

    public int getUnsignedShort(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedShort(i);
    }

    public int getMedium(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getMedium(i);
    }

    public int getUnsignedMedium(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedMedium(i);
    }

    public int getInt(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getInt(i);
    }

    public long getUnsignedInt(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedInt(i);
    }

    public long getLong(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getLong(i);
    }

    public char getChar(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getChar(i);
    }

    public float getFloat(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getFloat(i);
    }

    public double getDouble(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getDouble(i);
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, byteBuf);
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, byteBuf, i2);
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, byteBuf, i2, i3);
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, bArr);
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, bArr, i2, i3);
    }

    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, byteBuffer);
    }

    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, outputStream, i2);
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, gatheringByteChannel, i2);
    }

    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getCharSequence(i, i2, charset);
    }

    public CompositeByteBuf setBoolean(int i, boolean z) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBoolean(i, z);
    }

    public CompositeByteBuf setByte(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setByte(i, i2);
    }

    public CompositeByteBuf setShort(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setShort(i, i2);
    }

    public CompositeByteBuf setMedium(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setMedium(i, i2);
    }

    public CompositeByteBuf setInt(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setInt(i, i2);
    }

    public CompositeByteBuf setLong(int i, long j) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setLong(i, j);
    }

    public CompositeByteBuf setChar(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setChar(i, i2);
    }

    public CompositeByteBuf setFloat(int i, float f) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setFloat(i, f);
    }

    public CompositeByteBuf setDouble(int i, double d) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setDouble(i, d);
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, byteBuf);
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, byteBuf, i2);
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, byteBuf, i2, i3);
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, bArr);
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, bArr, i2, i3);
    }

    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, byteBuffer);
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, scatteringByteChannel, i2);
    }

    public CompositeByteBuf setZero(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setZero(i, i2);
    }

    public boolean readBoolean() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBoolean();
    }

    public byte readByte() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readByte();
    }

    public short readUnsignedByte() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedByte();
    }

    public short readShort() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readShort();
    }

    public int readUnsignedShort() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedShort();
    }

    public int readMedium() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readMedium();
    }

    public int readUnsignedMedium() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedMedium();
    }

    public int readInt() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readInt();
    }

    public long readUnsignedInt() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedInt();
    }

    public long readLong() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readLong();
    }

    public char readChar() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readChar();
    }

    public float readFloat() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readFloat();
    }

    public double readDouble() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readDouble();
    }

    public ByteBuf readBytes(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(i);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(byteBuf);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(byteBuf, i);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(byteBuf, i, i2);
    }

    public CompositeByteBuf readBytes(byte[] bArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(bArr);
    }

    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(bArr, i, i2);
    }

    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(byteBuffer);
    }

    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(outputStream, i);
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(gatheringByteChannel, i);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readCharSequence(i, charset);
    }

    public CompositeByteBuf skipBytes(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.skipBytes(i);
    }

    public CompositeByteBuf writeBoolean(boolean z) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBoolean(z);
    }

    public CompositeByteBuf writeByte(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeByte(i);
    }

    public CompositeByteBuf writeShort(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeShort(i);
    }

    public CompositeByteBuf writeMedium(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeMedium(i);
    }

    public CompositeByteBuf writeInt(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeInt(i);
    }

    public CompositeByteBuf writeLong(long j) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeLong(j);
    }

    public CompositeByteBuf writeChar(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeChar(i);
    }

    public CompositeByteBuf writeFloat(float f) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeFloat(f);
    }

    public CompositeByteBuf writeDouble(double d) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeDouble(d);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(byteBuf);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(byteBuf, i);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(byteBuf, i, i2);
    }

    public CompositeByteBuf writeBytes(byte[] bArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(bArr);
    }

    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(bArr, i, i2);
    }

    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(byteBuffer);
    }

    public int writeBytes(InputStream inputStream, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(inputStream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(scatteringByteChannel, i);
    }

    public CompositeByteBuf writeZero(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeZero(i);
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeCharSequence(charSequence, charset);
    }

    public int indexOf(int i, int i2, byte b) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.indexOf(i, i2, b);
    }

    public int bytesBefore(byte b) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.bytesBefore(b);
    }

    public int bytesBefore(int i, byte b) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.bytesBefore(i, b);
    }

    public int bytesBefore(int i, int i2, byte b) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.bytesBefore(i, i2, b);
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.forEachByte(byteProcessor);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.forEachByte(i, i2, byteProcessor);
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.forEachByteDesc(byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.forEachByteDesc(i, i2, byteProcessor);
    }

    public ByteBuf copy() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.copy();
    }

    public ByteBuf copy(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.copy(i, i2);
    }

    public int nioBufferCount() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.nioBuffer(i, i2);
    }

    public ByteBuffer[] nioBuffers() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.nioBuffers(i, i2);
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.internalNioBuffer(i, i2);
    }

    public String toString(Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.toString(charset);
    }

    public String toString(int i, int i2, Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.toString(i, i2, charset);
    }

    public CompositeByteBuf capacity(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.capacity(i);
    }

    public short getShortLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getShortLE(i);
    }

    public int getUnsignedShortLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedShortLE(i);
    }

    public int getUnsignedMediumLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedMediumLE(i);
    }

    public int getMediumLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getMediumLE(i);
    }

    public int getIntLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getIntLE(i);
    }

    public long getUnsignedIntLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getUnsignedIntLE(i);
    }

    public long getLongLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getLongLE(i);
    }

    public ByteBuf setShortLE(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setShortLE(i, i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setMediumLE(i, i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setIntLE(i, i2);
    }

    public ByteBuf setLongLE(int i, long j) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setLongLE(i, j);
    }

    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setCharSequence(i, charSequence, charset);
    }

    public short readShortLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readShortLE();
    }

    public int readUnsignedShortLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedShortLE();
    }

    public int readMediumLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readMediumLE();
    }

    public int readUnsignedMediumLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedMediumLE();
    }

    public int readIntLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readIntLE();
    }

    public long readUnsignedIntLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readUnsignedIntLE();
    }

    public long readLongLE() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readLongLE();
    }

    public ByteBuf writeShortLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeShortLE(i);
    }

    public ByteBuf writeMediumLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeMediumLE(i);
    }

    public ByteBuf writeIntLE(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeIntLE(i);
    }

    public ByteBuf writeLongLE(long j) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeLongLE(j);
    }

    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponent(byteBuf);
    }

    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents(byteBufArr);
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents((Iterable) iterable);
    }

    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponent(i, byteBuf);
    }

    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents(i, byteBufArr);
    }

    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents(i, (Iterable) iterable);
    }

    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponent(z, byteBuf);
    }

    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents(z, byteBufArr);
    }

    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponents(z, (Iterable) iterable);
    }

    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.addComponent(z, i, byteBuf);
    }

    public CompositeByteBuf removeComponent(int i) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.removeComponent(i);
    }

    public CompositeByteBuf removeComponents(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.removeComponents(i, i2);
    }

    public Iterator<ByteBuf> iterator() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.iterator();
    }

    public List<ByteBuf> decompose(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.decompose(i, i2);
    }

    public CompositeByteBuf consolidate() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.consolidate();
    }

    public CompositeByteBuf discardReadComponents() {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.discardReadComponents();
    }

    public CompositeByteBuf consolidate(int i, int i2) {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.consolidate(i, i2);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.getBytes(i, fileChannel, j, i2);
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.setBytes(i, fileChannel, j, i2);
    }

    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.readBytes(fileChannel, j, i);
    }

    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation(this.leak);
        return super.writeBytes(fileChannel, j, i);
    }

    public CompositeByteBuf retain() {
        this.leak.record();
        return super.retain();
    }

    public CompositeByteBuf retain(int i) {
        this.leak.record();
        return super.retain(i);
    }

    public CompositeByteBuf touch() {
        this.leak.record();
        return this;
    }

    public CompositeByteBuf touch(Object obj) {
        this.leak.record(obj);
        return this;
    }

    /* access modifiers changed from: protected */
    public AdvancedLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return new AdvancedLeakAwareByteBuf(byteBuf, byteBuf2, resourceLeakTracker);
    }
}
