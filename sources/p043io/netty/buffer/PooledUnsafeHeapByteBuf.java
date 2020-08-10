package p043io.netty.buffer;

import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.PooledUnsafeHeapByteBuf */
final class PooledUnsafeHeapByteBuf extends PooledHeapByteBuf {
    private static final Recycler<PooledUnsafeHeapByteBuf> RECYCLER = new Recycler<PooledUnsafeHeapByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledUnsafeHeapByteBuf newObject(Handle<PooledUnsafeHeapByteBuf> handle) {
            return new PooledUnsafeHeapByteBuf(handle, 0);
        }
    };

    static PooledUnsafeHeapByteBuf newUnsafeInstance(int i) {
        PooledUnsafeHeapByteBuf pooledUnsafeHeapByteBuf = (PooledUnsafeHeapByteBuf) RECYCLER.get();
        pooledUnsafeHeapByteBuf.reuse(i);
        return pooledUnsafeHeapByteBuf;
    }

    private PooledUnsafeHeapByteBuf(Handle<PooledUnsafeHeapByteBuf> handle, int i) {
        super(handle, i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return UnsafeByteBufUtil.getByte((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return UnsafeByteBufUtil.getShort((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return UnsafeByteBufUtil.getShortLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return UnsafeByteBufUtil.getUnsignedMedium((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return UnsafeByteBufUtil.getUnsignedMediumLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return UnsafeByteBufUtil.getInt((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return UnsafeByteBufUtil.getIntLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return UnsafeByteBufUtil.getLong((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return UnsafeByteBufUtil.getLongLE((byte[]) this.memory, idx(i));
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        UnsafeByteBufUtil.setByte((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        UnsafeByteBufUtil.setShort((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        UnsafeByteBufUtil.setShortLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        UnsafeByteBufUtil.setMedium((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        UnsafeByteBufUtil.setMediumLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        UnsafeByteBufUtil.setInt((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        UnsafeByteBufUtil.setIntLE((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        UnsafeByteBufUtil.setLong((byte[]) this.memory, idx(i), j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        UnsafeByteBufUtil.setLongLE((byte[]) this.memory, idx(i), j);
    }

    public ByteBuf setZero(int i, int i2) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.setZero(i, i2);
        }
        _setZero(i, i2);
        return this;
    }

    public ByteBuf writeZero(int i) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.writeZero(i);
        }
        ensureWritable(i);
        int i2 = this.writerIndex;
        _setZero(i2, i);
        this.writerIndex = i2 + i;
        return this;
    }

    private void _setZero(int i, int i2) {
        checkIndex(i, i2);
        UnsafeByteBufUtil.setZero((byte[]) this.memory, idx(i), i2);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeHeapSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }
}
