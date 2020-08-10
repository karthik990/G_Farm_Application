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
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnpooledUnsafeDirectByteBuf */
public class UnpooledUnsafeDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    ByteBuffer buffer;
    private int capacity;
    private boolean doNotFree;
    long memoryAddress;
    private ByteBuffer tmpNioBuf;

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return true;
    }

    public boolean isDirect() {
        return true;
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuf unwrap() {
        return null;
    }

    protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(i2);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("initialCapacity: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("maxCapacity: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i <= i2) {
            this.alloc = byteBufAllocator;
            setByteBuffer(allocateDirect(i), false);
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        }
    }

    protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int i) {
        this(byteBufAllocator, byteBuffer.slice(), i, false);
    }

    UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int i, boolean z) {
        super(i);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (byteBuffer == null) {
            throw new NullPointerException("initialBuffer");
        } else if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        } else if (!byteBuffer.isReadOnly()) {
            int remaining = byteBuffer.remaining();
            if (remaining <= i) {
                this.alloc = byteBufAllocator;
                this.doNotFree = !z;
                setByteBuffer(byteBuffer.order(ByteOrder.BIG_ENDIAN), false);
                writerIndex(remaining);
                return;
            }
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(remaining), Integer.valueOf(i)}));
        } else {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuffer allocateDirect(int i) {
        return ByteBuffer.allocateDirect(i);
    }

    /* access modifiers changed from: protected */
    public void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectBuffer(byteBuffer);
    }

    /* access modifiers changed from: 0000 */
    public final void setByteBuffer(ByteBuffer byteBuffer, boolean z) {
        if (z) {
            ByteBuffer byteBuffer2 = this.buffer;
            if (byteBuffer2 != null) {
                if (this.doNotFree) {
                    this.doNotFree = false;
                } else {
                    freeDirect(byteBuffer2);
                }
            }
        }
        this.buffer = byteBuffer;
        this.memoryAddress = PlatformDependent.directBufferAddress(byteBuffer);
        this.tmpNioBuf = null;
        this.capacity = byteBuffer.remaining();
    }

    public int capacity() {
        return this.capacity;
    }

    public ByteBuf capacity(int i) {
        checkNewCapacity(i);
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int i2 = this.capacity;
        if (i > i2) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer allocateDirect = allocateDirect(i);
            byteBuffer.position(0).limit(byteBuffer.capacity());
            allocateDirect.position(0).limit(byteBuffer.capacity());
            allocateDirect.put(byteBuffer);
            allocateDirect.clear();
            setByteBuffer(allocateDirect, true);
        } else if (i < i2) {
            ByteBuffer byteBuffer2 = this.buffer;
            ByteBuffer allocateDirect2 = allocateDirect(i);
            if (readerIndex < i) {
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                byteBuffer2.position(readerIndex).limit(i);
                allocateDirect2.position(readerIndex).limit(i);
                allocateDirect2.put(byteBuffer2);
                allocateDirect2.clear();
            } else {
                setIndex(i, i);
            }
            setByteBuffer(allocateDirect2, true);
        }
        return this;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public long memoryAddress() {
        ensureAccessible();
        return this.memoryAddress;
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return UnsafeByteBufUtil.getByte(addr(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return UnsafeByteBufUtil.getShort(addr(i));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return UnsafeByteBufUtil.getShortLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return UnsafeByteBufUtil.getUnsignedMedium(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return UnsafeByteBufUtil.getUnsignedMediumLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return UnsafeByteBufUtil.getInt(addr(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return UnsafeByteBufUtil.getIntLE(addr(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return UnsafeByteBufUtil.getLong(addr(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return UnsafeByteBufUtil.getLongLE(addr(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(i), i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(i), i, bArr, i2, i3);
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.getBytes(this, addr(i), i, byteBuffer);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        getBytes(this.readerIndex, byteBuffer);
        this.readerIndex += remaining;
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        UnsafeByteBufUtil.setByte(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        UnsafeByteBufUtil.setShort(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        UnsafeByteBufUtil.setShortLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        UnsafeByteBufUtil.setMedium(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        UnsafeByteBufUtil.setMediumLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        UnsafeByteBufUtil.setInt(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        UnsafeByteBufUtil.setIntLE(addr(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        UnsafeByteBufUtil.setLong(addr(i), j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        UnsafeByteBufUtil.setLongLE(addr(i), j);
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(i), i, byteBuf, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(i), i, bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.setBytes(this, addr(i), i, byteBuffer);
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        UnsafeByteBufUtil.getBytes(this, addr(i), i, outputStream, i2);
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return getBytes(i, gatheringByteChannel, i2, false);
    }

    private int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = this.buffer.duplicate();
        }
        byteBuffer.clear().position(i).limit(i + i2);
        return gatheringByteChannel.write(byteBuffer);
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return getBytes(i, fileChannel, j, i2, false);
    }

    private int getBytes(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer internalNioBuffer = z ? internalNioBuffer() : this.buffer.duplicate();
        internalNioBuffer.clear().position(i).limit(i + i2);
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

    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return UnsafeByteBufUtil.setBytes(this, addr(i), i, inputStream, i2);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i2);
        try {
            return scatteringByteChannel.read(internalNioBuffer);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i2);
        try {
            return fileChannel.read(internalNioBuffer, j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public ByteBuf copy(int i, int i2) {
        return UnsafeByteBufUtil.copy(this, addr(i), i, i2);
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return (ByteBuffer) internalNioBuffer().clear().position(i).limit(i + i2);
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer duplicate = this.buffer.duplicate();
        this.tmpNioBuf = duplicate;
        return duplicate;
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return ((ByteBuffer) this.buffer.duplicate().position(i).limit(i + i2)).slice();
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        ByteBuffer byteBuffer = this.buffer;
        if (byteBuffer != null) {
            this.buffer = null;
            if (!this.doNotFree) {
                freeDirect(byteBuffer);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public long addr(int i) {
        return this.memoryAddress + ((long) i);
    }

    /* access modifiers changed from: protected */
    public SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeDirectSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }

    public ByteBuf setZero(int i, int i2) {
        UnsafeByteBufUtil.setZero(this, addr(i), i, i2);
        return this;
    }

    public ByteBuf writeZero(int i) {
        ensureWritable(i);
        int i2 = this.writerIndex;
        setZero(i2, i);
        this.writerIndex = i2 + i;
        return this;
    }
}
