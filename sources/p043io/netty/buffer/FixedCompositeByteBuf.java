package p043io.netty.buffer;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Collections;
import kotlin.UShort;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.RecyclableArrayList;

/* renamed from: io.netty.buffer.FixedCompositeByteBuf */
final class FixedCompositeByteBuf extends AbstractReferenceCountedByteBuf {
    private static final ByteBuf[] EMPTY = {Unpooled.EMPTY_BUFFER};
    private final ByteBufAllocator allocator;
    private final Object[] buffers;
    private final int capacity;
    private final boolean direct;
    private final int nioBufferCount;
    private final ByteOrder order;

    /* renamed from: io.netty.buffer.FixedCompositeByteBuf$Component */
    private static final class Component {
        /* access modifiers changed from: private */
        public final ByteBuf buf;
        /* access modifiers changed from: private */
        public final int endOffset;
        /* access modifiers changed from: private */
        public final int index;
        /* access modifiers changed from: private */
        public final int offset;

        Component(int i, int i2, ByteBuf byteBuf) {
            this.index = i;
            this.offset = i2;
            this.endOffset = i2 + byteBuf.readableBytes();
            this.buf = byteBuf;
        }
    }

    public boolean hasArray() {
        return false;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int i) {
        return false;
    }

    public ByteBuf unwrap() {
        return null;
    }

    FixedCompositeByteBuf(ByteBufAllocator byteBufAllocator, ByteBuf... byteBufArr) {
        super(Integer.MAX_VALUE);
        int i = 1;
        if (byteBufArr.length == 0) {
            this.buffers = EMPTY;
            this.order = ByteOrder.BIG_ENDIAN;
            this.nioBufferCount = 1;
            this.capacity = 0;
            this.direct = false;
        } else {
            ByteBuf byteBuf = byteBufArr[0];
            this.buffers = new Object[byteBufArr.length];
            this.buffers[0] = byteBuf;
            int nioBufferCount2 = byteBuf.nioBufferCount();
            int readableBytes = byteBuf.readableBytes();
            this.order = byteBuf.order();
            boolean z = true;
            while (i < byteBufArr.length) {
                ByteBuf byteBuf2 = byteBufArr[i];
                if (byteBufArr[i].order() == this.order) {
                    nioBufferCount2 += byteBuf2.nioBufferCount();
                    readableBytes += byteBuf2.readableBytes();
                    if (!byteBuf2.isDirect()) {
                        z = false;
                    }
                    this.buffers[i] = byteBuf2;
                    i++;
                } else {
                    throw new IllegalArgumentException("All ByteBufs need to have same ByteOrder");
                }
            }
            this.nioBufferCount = nioBufferCount2;
            this.capacity = readableBytes;
            this.direct = z;
        }
        setIndex(0, capacity());
        this.allocator = byteBufAllocator;
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

    public int capacity() {
        return this.capacity;
    }

    public int maxCapacity() {
        return this.capacity;
    }

    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }

    public ByteBufAllocator alloc() {
        return this.allocator;
    }

    public ByteOrder order() {
        return this.order;
    }

    public boolean isDirect() {
        return this.direct;
    }

    private Component findComponent(int i) {
        boolean z;
        ByteBuf byteBuf;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            Object[] objArr = this.buffers;
            if (i2 < objArr.length) {
                Component component = null;
                Object obj = objArr[i2];
                if (obj instanceof ByteBuf) {
                    byteBuf = (ByteBuf) obj;
                    z = true;
                } else {
                    component = (Component) obj;
                    byteBuf = component.buf;
                    z = false;
                }
                i3 += byteBuf.readableBytes();
                if (i >= i3) {
                    i2++;
                } else if (!z) {
                    return component;
                } else {
                    Component component2 = new Component(i2, i3 - byteBuf.readableBytes(), byteBuf);
                    this.buffers[i2] = component2;
                    return component2;
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private ByteBuf buffer(int i) {
        Object obj = this.buffers[i];
        if (obj instanceof ByteBuf) {
            return (ByteBuf) obj;
        }
        return ((Component) obj).buf;
    }

    public byte getByte(int i) {
        return _getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        Component findComponent = findComponent(i);
        return findComponent.buf.getByte(i - findComponent.offset);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            return findComponent.buf.getShort(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
        }
        return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            return findComponent.buf.getShortLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
        }
        return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            return findComponent.buf.getUnsignedMedium(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getByte(i + 2) & 255) | ((_getShort(i) & UShort.MAX_VALUE) << 8);
        }
        return ((_getByte(i + 2) & 255) << Ascii.DLE) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            return findComponent.buf.getUnsignedMediumLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getByte(i + 2) & 255) << Ascii.DLE) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getByte(i + 2) & 255) | ((_getShortLE(i) & UShort.MAX_VALUE) << 8);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            return findComponent.buf.getInt(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getShort(i + 2) & UShort.MAX_VALUE) | ((_getShort(i) & UShort.MAX_VALUE) << 16);
        }
        return ((_getShort(i + 2) & UShort.MAX_VALUE) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            return findComponent.buf.getIntLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShortLE(i + 2) & UShort.MAX_VALUE) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getShortLE(i + 2) & UShort.MAX_VALUE) | ((_getShortLE(i) & UShort.MAX_VALUE) << 16);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            return findComponent.buf.getLong(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((((long) _getInt(i)) & 4294967295L) << 32) | (4294967295L & ((long) _getInt(i + 4)));
        }
        return (((long) _getInt(i)) & 4294967295L) | ((4294967295L & ((long) _getInt(i + 4))) << 32);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            return findComponent.buf.getLongLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (((long) _getIntLE(i)) & 4294967295L) | ((4294967295L & ((long) _getIntLE(i + 4))) << 32);
        }
        return ((((long) _getIntLE(i)) & 4294967295L) << 32) | (4294967295L & ((long) _getIntLE(i + 4)));
    }

    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        Component findComponent = findComponent(i);
        int access$300 = findComponent.index;
        int access$100 = findComponent.offset;
        ByteBuf access$000 = findComponent.buf;
        while (true) {
            int i4 = i - access$100;
            int min = Math.min(i3, access$000.readableBytes() - i4);
            access$000.getBytes(i4, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            access$100 += access$000.readableBytes();
            if (i3 <= 0) {
                return this;
            }
            access$300++;
            access$000 = buffer(access$300);
        }
    }

    /* JADX INFO: finally extract failed */
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        try {
            Component findComponent = findComponent(i);
            int access$300 = findComponent.index;
            int access$100 = findComponent.offset;
            ByteBuf access$000 = findComponent.buf;
            while (true) {
                int i2 = i - access$100;
                int min = Math.min(remaining, access$000.readableBytes() - i2);
                byteBuffer.limit(byteBuffer.position() + min);
                access$000.getBytes(i2, byteBuffer);
                i += min;
                remaining -= min;
                access$100 += access$000.readableBytes();
                if (remaining <= 0) {
                    byteBuffer.limit(limit);
                    return this;
                }
                access$300++;
                access$000 = buffer(access$300);
            }
        } catch (Throwable th) {
            byteBuffer.limit(limit);
            throw th;
        }
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        Component findComponent = findComponent(i);
        int access$300 = findComponent.index;
        int access$100 = findComponent.offset;
        ByteBuf access$000 = findComponent.buf;
        while (true) {
            int i4 = i - access$100;
            int min = Math.min(i3, access$000.readableBytes() - i4);
            access$000.getBytes(i4, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            access$100 += access$000.readableBytes();
            if (i3 <= 0) {
                return this;
            }
            access$300++;
            access$000 = buffer(access$300);
        }
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return gatheringByteChannel.write(internalNioBuffer(i, i2));
        }
        long write = gatheringByteChannel.write(nioBuffers(i, i2));
        if (write > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) write;
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return fileChannel.write(internalNioBuffer(i, i2), j);
        }
        long j2 = 0;
        for (ByteBuffer write : nioBuffers(i, i2)) {
            j2 += (long) fileChannel.write(write, j + j2);
        }
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return this;
        }
        Component findComponent = findComponent(i);
        int access$300 = findComponent.index;
        int access$100 = findComponent.offset;
        ByteBuf access$000 = findComponent.buf;
        while (true) {
            int i3 = i - access$100;
            int min = Math.min(i2, access$000.readableBytes() - i3);
            access$000.getBytes(i3, outputStream, min);
            i += min;
            i2 -= min;
            access$100 += access$000.readableBytes();
            if (i2 <= 0) {
                return this;
            }
            access$300++;
            access$000 = buffer(access$300);
        }
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf buffer = alloc().buffer(i2);
        try {
            buffer.writeBytes((ByteBuf) this, i, i2);
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        if (this.buffers.length == 1) {
            ByteBuf buffer = buffer(0);
            if (buffer.nioBufferCount() == 1) {
                return buffer.nioBuffer(i, i2);
            }
        }
        ByteBuffer order2 = ByteBuffer.allocate(i2).order(order());
        ByteBuffer[] nioBuffers = nioBuffers(i, i2);
        for (ByteBuffer put : nioBuffers) {
            order2.put(put);
        }
        order2.flip();
        return order2;
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        if (this.buffers.length == 1) {
            return buffer(0).internalNioBuffer(i, i2);
        }
        throw new UnsupportedOperationException();
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return EmptyArrays.EMPTY_BYTE_BUFFERS;
        }
        RecyclableArrayList newInstance = RecyclableArrayList.newInstance(this.buffers.length);
        try {
            Component findComponent = findComponent(i);
            int access$300 = findComponent.index;
            int access$100 = findComponent.offset;
            ByteBuf access$000 = findComponent.buf;
            while (true) {
                int i3 = i - access$100;
                int min = Math.min(i2, access$000.readableBytes() - i3);
                int nioBufferCount2 = access$000.nioBufferCount();
                if (nioBufferCount2 != 0) {
                    if (nioBufferCount2 != 1) {
                        Collections.addAll(newInstance, access$000.nioBuffers(i3, min));
                    } else {
                        newInstance.add(access$000.nioBuffer(i3, min));
                    }
                    i += min;
                    i2 -= min;
                    access$100 += access$000.readableBytes();
                    if (i2 <= 0) {
                        return (ByteBuffer[]) newInstance.toArray(new ByteBuffer[newInstance.size()]);
                    }
                    access$300++;
                    access$000 = buffer(access$300);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        } finally {
            newInstance.recycle();
        }
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

    /* access modifiers changed from: protected */
    public void deallocate() {
        for (int i = 0; i < this.buffers.length; i++) {
            buffer(i).release();
        }
    }

    public String toString() {
        String abstractReferenceCountedByteBuf = super.toString();
        String substring = abstractReferenceCountedByteBuf.substring(0, abstractReferenceCountedByteBuf.length() - 1);
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(", components=");
        sb.append(this.buffers.length);
        sb.append(')');
        return sb.toString();
    }
}
