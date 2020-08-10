package p043io.netty.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p043io.netty.util.Recycler.Handle;

/* renamed from: io.netty.buffer.PooledByteBuf */
abstract class PooledByteBuf<T> extends AbstractReferenceCountedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ByteBufAllocator allocator;
    PoolThreadCache cache;
    protected PoolChunk<T> chunk;
    protected long handle;
    protected int length;
    int maxLength;
    protected T memory;
    protected int offset;
    private final Handle<PooledByteBuf<T>> recyclerHandle;
    private ByteBuffer tmpNioBuf;

    /* access modifiers changed from: protected */
    public abstract ByteBuffer newInternalNioBuffer(T t);

    public final ByteBuf unwrap() {
        return null;
    }

    protected PooledByteBuf(Handle<? extends PooledByteBuf<T>> handle2, int i) {
        super(i);
        this.recyclerHandle = handle2;
    }

    /* access modifiers changed from: 0000 */
    public void init(PoolChunk<T> poolChunk, long j, int i, int i2, int i3, PoolThreadCache poolThreadCache) {
        init0(poolChunk, j, i, i2, i3, poolThreadCache);
    }

    /* access modifiers changed from: 0000 */
    public void initUnpooled(PoolChunk<T> poolChunk, int i) {
        init0(poolChunk, 0, poolChunk.offset, i, i, null);
    }

    private void init0(PoolChunk<T> poolChunk, long j, int i, int i2, int i3, PoolThreadCache poolThreadCache) {
        this.chunk = poolChunk;
        this.memory = poolChunk.memory;
        this.allocator = poolChunk.arena.parent;
        this.cache = poolThreadCache;
        this.handle = j;
        this.offset = i;
        this.length = i2;
        this.maxLength = i3;
        this.tmpNioBuf = null;
    }

    /* access modifiers changed from: 0000 */
    public final void reuse(int i) {
        maxCapacity(i);
        setRefCnt(1);
        setIndex0(0, 0);
        discardMarks();
    }

    public final int capacity() {
        return this.length;
    }

    public final ByteBuf capacity(int i) {
        checkNewCapacity(i);
        if (!this.chunk.unpooled) {
            int i2 = this.length;
            if (i <= i2) {
                if (i < i2) {
                    int i3 = this.maxLength;
                    if (i > (i3 >>> 1)) {
                        if (i3 > 512) {
                            this.length = i;
                            setIndex(Math.min(readerIndex(), i), Math.min(writerIndex(), i));
                            return this;
                        } else if (i > i3 - 16) {
                            this.length = i;
                            setIndex(Math.min(readerIndex(), i), Math.min(writerIndex(), i));
                            return this;
                        }
                    }
                }
                return this;
            } else if (i <= this.maxLength) {
                this.length = i;
                return this;
            }
        } else if (i == this.length) {
            return this;
        }
        this.chunk.arena.reallocate(this, i, true);
        return this;
    }

    public final ByteBufAllocator alloc() {
        return this.allocator;
    }

    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public final ByteBuf retainedDuplicate() {
        return PooledDuplicatedByteBuf.newInstance(this, this, readerIndex(), writerIndex());
    }

    public final ByteBuf retainedSlice() {
        int readerIndex = readerIndex();
        return retainedSlice(readerIndex, writerIndex() - readerIndex);
    }

    public final ByteBuf retainedSlice(int i, int i2) {
        return PooledSlicedByteBuf.newInstance(this, this, i, i2);
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer newInternalNioBuffer = newInternalNioBuffer(this.memory);
        this.tmpNioBuf = newInternalNioBuffer;
        return newInternalNioBuffer;
    }

    /* access modifiers changed from: protected */
    public final void deallocate() {
        long j = this.handle;
        if (j >= 0) {
            this.handle = -1;
            this.memory = null;
            this.tmpNioBuf = null;
            this.chunk.arena.free(this.chunk, j, this.maxLength, this.cache);
            this.chunk = null;
            recycle();
        }
    }

    private void recycle() {
        this.recyclerHandle.recycle(this);
    }

    /* access modifiers changed from: protected */
    public final int idx(int i) {
        return this.offset + i;
    }
}
