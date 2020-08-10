package p043io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnpooledHeapByteBuf */
public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    byte[] array;
    private ByteBuffer tmpNioBuf;

    public int arrayOffset() {
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void freeArray(byte[] bArr) {
    }

    public boolean hasArray() {
        return true;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public boolean isDirect() {
        return false;
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuf unwrap() {
        return null;
    }

    protected UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(i2);
        ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        if (i <= i2) {
            this.alloc = byteBufAllocator;
            setArray(allocateArray(i));
            setIndex(0, 0);
            return;
        }
        throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    protected UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, byte[] bArr, int i) {
        super(i);
        ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        ObjectUtil.checkNotNull(bArr, "initialArray");
        if (bArr.length <= i) {
            this.alloc = byteBufAllocator;
            setArray(bArr);
            setIndex(0, bArr.length);
            return;
        }
        throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i)}));
    }

    /* access modifiers changed from: 0000 */
    public byte[] allocateArray(int i) {
        return new byte[i];
    }

    private void setArray(byte[] bArr) {
        this.array = bArr;
        this.tmpNioBuf = null;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public int capacity() {
        ensureAccessible();
        return this.array.length;
    }

    public ByteBuf capacity(int i) {
        checkNewCapacity(i);
        byte[] bArr = this.array;
        int length = bArr.length;
        if (i > length) {
            byte[] allocateArray = allocateArray(i);
            System.arraycopy(bArr, 0, allocateArray, 0, bArr.length);
            setArray(allocateArray);
            freeArray(bArr);
        } else if (i < length) {
            byte[] allocateArray2 = allocateArray(i);
            int readerIndex = readerIndex();
            if (readerIndex < i) {
                int writerIndex = writerIndex();
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                System.arraycopy(bArr, readerIndex, allocateArray2, readerIndex, i - readerIndex);
            } else {
                setIndex(i, i);
            }
            setArray(allocateArray2);
            freeArray(bArr);
        }
        return this;
    }

    public byte[] array() {
        ensureAccessible();
        return this.array;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            byte[] bArr = this.array;
            long memoryAddress = ((long) i2) + byteBuf.memoryAddress();
            PlatformDependent.copyMemory(bArr, i, memoryAddress, (long) i3);
        } else if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.setBytes(i2, this.array, i, i3);
        }
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        System.arraycopy(this.array, i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i, byteBuffer.remaining());
        byteBuffer.put(this.array, i, byteBuffer.remaining());
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        ensureAccessible();
        outputStream.write(this.array, i, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        ensureAccessible();
        return getBytes(i, gatheringByteChannel, i2, false);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        return getBytes(i, fileChannel, j, i2, false);
    }

    private int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = ByteBuffer.wrap(this.array);
        }
        return gatheringByteChannel.write((ByteBuffer) byteBuffer.clear().position(i).limit(i + i2));
    }

    private int getBytes(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        ensureAccessible();
        return fileChannel.write((ByteBuffer) (z ? internalNioBuffer() : ByteBuffer.wrap(this.array)).clear().position(i).limit(i + i2), j);
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

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + ((long) i2), this.array, i, (long) i3);
        } else if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.getBytes(i2, this.array, i, i3);
        }
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        System.arraycopy(bArr, i2, this.array, i, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        ensureAccessible();
        byteBuffer.get(this.array, i, byteBuffer.remaining());
        return this;
    }

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        ensureAccessible();
        return inputStream.read(this.array, i, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        try {
            return scatteringByteChannel.read((ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2));
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        try {
            return fileChannel.read((ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2), j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        ensureAccessible();
        return ByteBuffer.wrap(this.array, i, i2).slice();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return (ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2);
    }

    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return HeapByteBufUtil.getByte(this.array, i);
    }

    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return HeapByteBufUtil.getShort(this.array, i);
    }

    public short getShortLE(int i) {
        ensureAccessible();
        return _getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return HeapByteBufUtil.getShortLE(this.array, i);
    }

    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return HeapByteBufUtil.getUnsignedMedium(this.array, i);
    }

    public int getUnsignedMediumLE(int i) {
        ensureAccessible();
        return _getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return HeapByteBufUtil.getUnsignedMediumLE(this.array, i);
    }

    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return HeapByteBufUtil.getInt(this.array, i);
    }

    public int getIntLE(int i) {
        ensureAccessible();
        return _getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return HeapByteBufUtil.getIntLE(this.array, i);
    }

    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return HeapByteBufUtil.getLong(this.array, i);
    }

    public long getLongLE(int i) {
        ensureAccessible();
        return _getLongLE(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return HeapByteBufUtil.getLongLE(this.array, i);
    }

    public ByteBuf setByte(int i, int i2) {
        ensureAccessible();
        _setByte(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        HeapByteBufUtil.setByte(this.array, i, i2);
    }

    public ByteBuf setShort(int i, int i2) {
        ensureAccessible();
        _setShort(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        HeapByteBufUtil.setShort(this.array, i, i2);
    }

    public ByteBuf setShortLE(int i, int i2) {
        ensureAccessible();
        _setShortLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        HeapByteBufUtil.setShortLE(this.array, i, i2);
    }

    public ByteBuf setMedium(int i, int i2) {
        ensureAccessible();
        _setMedium(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        HeapByteBufUtil.setMedium(this.array, i, i2);
    }

    public ByteBuf setMediumLE(int i, int i2) {
        ensureAccessible();
        _setMediumLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        HeapByteBufUtil.setMediumLE(this.array, i, i2);
    }

    public ByteBuf setInt(int i, int i2) {
        ensureAccessible();
        _setInt(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        HeapByteBufUtil.setInt(this.array, i, i2);
    }

    public ByteBuf setIntLE(int i, int i2) {
        ensureAccessible();
        _setIntLE(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        HeapByteBufUtil.setIntLE(this.array, i, i2);
    }

    public ByteBuf setLong(int i, long j) {
        ensureAccessible();
        _setLong(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        HeapByteBufUtil.setLong(this.array, i, j);
    }

    public ByteBuf setLongLE(int i, long j) {
        ensureAccessible();
        _setLongLE(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        HeapByteBufUtil.setLongLE(this.array, i, j);
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        byte[] bArr = new byte[i2];
        System.arraycopy(this.array, i, bArr, 0, i2);
        return new UnpooledHeapByteBuf(alloc(), bArr, maxCapacity());
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer wrap = ByteBuffer.wrap(this.array);
        this.tmpNioBuf = wrap;
        return wrap;
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        freeArray(this.array);
        this.array = null;
    }
}
