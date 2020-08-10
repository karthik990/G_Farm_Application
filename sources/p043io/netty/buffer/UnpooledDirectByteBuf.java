package p043io.netty.buffer;

import com.google.common.base.Ascii;
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

/* renamed from: io.netty.buffer.UnpooledDirectByteBuf */
public class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private int capacity;
    private boolean doNotFree;
    private ByteBuffer tmpNioBuf;

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return false;
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

    protected UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
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
            setByteBuffer(ByteBuffer.allocateDirect(i));
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        }
    }

    protected UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int i) {
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
                this.doNotFree = true;
                setByteBuffer(byteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
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

    private void setByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = this.buffer;
        if (byteBuffer2 != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                freeDirect(byteBuffer2);
            }
        }
        this.buffer = byteBuffer;
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
            setByteBuffer(allocateDirect);
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
            setByteBuffer(allocateDirect2);
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
        throw new UnsupportedOperationException();
    }

    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return this.buffer.get(i);
    }

    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return this.buffer.getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(this.buffer.getShort(i));
    }

    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return (getByte(i + 2) & 255) | ((getByte(i) & 255) << Ascii.DLE) | ((getByte(i + 1) & 255) << 8);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return ((getByte(i + 2) & 255) << Ascii.DLE) | (getByte(i) & 255) | ((getByte(i + 1) & 255) << 8);
    }

    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return this.buffer.getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(this.buffer.getInt(i));
    }

    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return this.buffer.getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(this.buffer.getLong(i));
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ByteBuffer[] nioBuffers;
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(i2, i3)) {
                int remaining = byteBuffer.remaining();
                getBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.setBytes(i2, (ByteBuf) this, i, i3);
        }
        return this;
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        getBytes(i, bArr, i2, i3, false);
        return this;
    }

    private void getBytes(int i, byte[] bArr, int i2, int i3, boolean z) {
        ByteBuffer byteBuffer;
        checkDstIndex(i, i3, i2, bArr.length);
        if (z) {
            byteBuffer = internalNioBuffer();
        } else {
            byteBuffer = this.buffer.duplicate();
        }
        byteBuffer.clear().position(i).limit(i + i3);
        byteBuffer.get(bArr, i2, i3);
    }

    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        checkReadableBytes(i2);
        getBytes(this.readerIndex, bArr, i, i2, true);
        this.readerIndex += i2;
        return this;
    }

    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        getBytes(i, byteBuffer, false);
        return this;
    }

    private void getBytes(int i, ByteBuffer byteBuffer, boolean z) {
        ByteBuffer byteBuffer2;
        checkIndex(i, byteBuffer.remaining());
        if (z) {
            byteBuffer2 = internalNioBuffer();
        } else {
            byteBuffer2 = this.buffer.duplicate();
        }
        byteBuffer2.clear().position(i).limit(i + byteBuffer.remaining());
        byteBuffer.put(byteBuffer2);
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        getBytes(this.readerIndex, byteBuffer, true);
        this.readerIndex += remaining;
        return this;
    }

    public ByteBuf setByte(int i, int i2) {
        ensureAccessible();
        _setByte(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        this.buffer.put(i, (byte) i2);
    }

    public ByteBuf setShort(int i, int i2) {
        ensureAccessible();
        _setShort(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        this.buffer.putShort(i, (short) i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        this.buffer.putShort(i, ByteBufUtil.swapShort((short) i2));
    }

    public ByteBuf setMedium(int i, int i2) {
        ensureAccessible();
        _setMedium(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        setByte(i, (byte) (i2 >>> 16));
        setByte(i + 1, (byte) (i2 >>> 8));
        setByte(i + 2, (byte) i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        setByte(i, (byte) i2);
        setByte(i + 1, (byte) (i2 >>> 8));
        setByte(i + 2, (byte) (i2 >>> 16));
    }

    public ByteBuf setInt(int i, int i2) {
        ensureAccessible();
        _setInt(i, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        this.buffer.putInt(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        this.buffer.putInt(i, ByteBufUtil.swapInt(i2));
    }

    public ByteBuf setLong(int i, long j) {
        ensureAccessible();
        _setLong(i, j);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        this.buffer.putLong(i, j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        this.buffer.putLong(i, ByteBufUtil.swapLong(j));
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ByteBuffer[] nioBuffers;
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(i2, i3)) {
                int remaining = byteBuffer.remaining();
                setBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.getBytes(i2, (ByteBuf) this, i, i3);
        }
        return this;
    }

    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i).limit(i + i3);
        internalNioBuffer.put(bArr, i2, i3);
        return this;
    }

    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        ensureAccessible();
        ByteBuffer internalNioBuffer = internalNioBuffer();
        if (byteBuffer == internalNioBuffer) {
            byteBuffer = byteBuffer.duplicate();
        }
        internalNioBuffer.clear().position(i).limit(i + byteBuffer.remaining());
        internalNioBuffer.put(byteBuffer);
        return this;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        getBytes(i, outputStream, i2, false);
        return this;
    }

    private void getBytes(int i, OutputStream outputStream, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (i2 != 0) {
            if (this.buffer.hasArray()) {
                outputStream.write(this.buffer.array(), i + this.buffer.arrayOffset(), i2);
            } else {
                byte[] bArr = new byte[i2];
                if (z) {
                    byteBuffer = internalNioBuffer();
                } else {
                    byteBuffer = this.buffer.duplicate();
                }
                byteBuffer.clear().position(i);
                byteBuffer.get(bArr);
                outputStream.write(bArr);
            }
        }
    }

    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        checkReadableBytes(i);
        getBytes(this.readerIndex, outputStream, i, true);
        this.readerIndex += i;
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
        ensureAccessible();
        if (this.buffer.hasArray()) {
            return inputStream.read(this.buffer.array(), this.buffer.arrayOffset() + i, i2);
        }
        byte[] bArr = new byte[i2];
        int read = inputStream.read(bArr);
        if (read <= 0) {
            return read;
        }
        ByteBuffer internalNioBuffer = internalNioBuffer();
        internalNioBuffer.clear().position(i);
        internalNioBuffer.put(bArr, 0, read);
        return read;
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        internalNioBuffer().clear().position(i).limit(i + i2);
        try {
            return scatteringByteChannel.read(this.tmpNioBuf);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        internalNioBuffer().clear().position(i).limit(i + i2);
        try {
            return fileChannel.read(this.tmpNioBuf, j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    public ByteBuf copy(int i, int i2) {
        ensureAccessible();
        try {
            return alloc().directBuffer(i2, maxCapacity()).writeBytes((ByteBuffer) this.buffer.duplicate().clear().position(i).limit(i + i2));
        } catch (IllegalArgumentException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Too many bytes to read - Need ");
            sb.append(i + i2);
            throw new IndexOutOfBoundsException(sb.toString());
        }
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
}
