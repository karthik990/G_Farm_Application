package p043io.netty.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ReferenceCounted;

/* renamed from: io.netty.buffer.AbstractPooledDerivedByteBuf */
abstract class AbstractPooledDerivedByteBuf extends AbstractReferenceCountedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ByteBuf parent;
    private final Handle<AbstractPooledDerivedByteBuf> recyclerHandle;
    private AbstractByteBuf rootParent;

    /* renamed from: io.netty.buffer.AbstractPooledDerivedByteBuf$PooledNonRetainedDuplicateByteBuf */
    private static final class PooledNonRetainedDuplicateByteBuf extends UnpooledDuplicatedByteBuf {
        private final ReferenceCounted referenceCountDelegate;

        PooledNonRetainedDuplicateByteBuf(ReferenceCounted referenceCounted, AbstractByteBuf abstractByteBuf) {
            super(abstractByteBuf);
            this.referenceCountDelegate = referenceCounted;
        }

        /* access modifiers changed from: 0000 */
        public int refCnt0() {
            return this.referenceCountDelegate.refCnt();
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf retain0() {
            this.referenceCountDelegate.retain();
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf retain0(int i) {
            this.referenceCountDelegate.retain(i);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf touch0() {
            this.referenceCountDelegate.touch();
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf touch0(Object obj) {
            this.referenceCountDelegate.touch(obj);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public boolean release0() {
            return this.referenceCountDelegate.release();
        }

        /* access modifiers changed from: 0000 */
        public boolean release0(int i) {
            return this.referenceCountDelegate.release(i);
        }

        public ByteBuf duplicate() {
            return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this);
        }

        public ByteBuf retainedDuplicate() {
            return PooledDuplicatedByteBuf.newInstance(unwrap(), this, readerIndex(), writerIndex());
        }

        public ByteBuf slice(int i, int i2) {
            checkIndex0(i, i2);
            return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, unwrap(), i, i2);
        }

        public ByteBuf retainedSlice() {
            return retainedSlice(readerIndex(), capacity());
        }

        public ByteBuf retainedSlice(int i, int i2) {
            return PooledSlicedByteBuf.newInstance(unwrap(), this, i, i2);
        }
    }

    /* renamed from: io.netty.buffer.AbstractPooledDerivedByteBuf$PooledNonRetainedSlicedByteBuf */
    private static final class PooledNonRetainedSlicedByteBuf extends UnpooledSlicedByteBuf {
        private final ReferenceCounted referenceCountDelegate;

        PooledNonRetainedSlicedByteBuf(ReferenceCounted referenceCounted, AbstractByteBuf abstractByteBuf, int i, int i2) {
            super(abstractByteBuf, i, i2);
            this.referenceCountDelegate = referenceCounted;
        }

        /* access modifiers changed from: 0000 */
        public int refCnt0() {
            return this.referenceCountDelegate.refCnt();
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf retain0() {
            this.referenceCountDelegate.retain();
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf retain0(int i) {
            this.referenceCountDelegate.retain(i);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf touch0() {
            this.referenceCountDelegate.touch();
            return this;
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf touch0(Object obj) {
            this.referenceCountDelegate.touch(obj);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public boolean release0() {
            return this.referenceCountDelegate.release();
        }

        /* access modifiers changed from: 0000 */
        public boolean release0(int i) {
            return this.referenceCountDelegate.release(i);
        }

        public ByteBuf duplicate() {
            return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, unwrap()).setIndex(idx(readerIndex()), idx(writerIndex()));
        }

        public ByteBuf retainedDuplicate() {
            return PooledDuplicatedByteBuf.newInstance(unwrap(), this, idx(readerIndex()), idx(writerIndex()));
        }

        public ByteBuf slice(int i, int i2) {
            checkIndex0(i, i2);
            return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, unwrap(), idx(i), i2);
        }

        public ByteBuf retainedSlice() {
            return retainedSlice(0, capacity());
        }

        public ByteBuf retainedSlice(int i, int i2) {
            return PooledSlicedByteBuf.newInstance(unwrap(), this, idx(i), i2);
        }
    }

    AbstractPooledDerivedByteBuf(Handle<? extends AbstractPooledDerivedByteBuf> handle) {
        super(0);
        this.recyclerHandle = handle;
    }

    /* access modifiers changed from: 0000 */
    public final void parent(ByteBuf byteBuf) {
        this.parent = byteBuf;
    }

    public final AbstractByteBuf unwrap() {
        return this.rootParent;
    }

    /* access modifiers changed from: 0000 */
    public final <U extends AbstractPooledDerivedByteBuf> U init(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int i, int i2, int i3) {
        byteBuf.retain();
        this.parent = byteBuf;
        this.rootParent = abstractByteBuf;
        try {
            maxCapacity(i3);
            setIndex0(i, i2);
            setRefCnt(1);
            return this;
        } catch (Throwable th) {
            if (byteBuf != null) {
                this.rootParent = null;
                this.parent = null;
                byteBuf.release();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public final void deallocate() {
        ByteBuf byteBuf = this.parent;
        this.recyclerHandle.recycle(this);
        byteBuf.release();
    }

    public final ByteBufAllocator alloc() {
        return unwrap().alloc();
    }

    @Deprecated
    public final ByteOrder order() {
        return unwrap().order();
    }

    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    public final boolean isDirect() {
        return unwrap().isDirect();
    }

    public boolean hasArray() {
        return unwrap().hasArray();
    }

    public byte[] array() {
        return unwrap().array();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public final int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public final ByteBuffer internalNioBuffer(int i, int i2) {
        return nioBuffer(i, i2);
    }

    public final ByteBuf retainedSlice() {
        int readerIndex = readerIndex();
        return retainedSlice(readerIndex, writerIndex() - readerIndex);
    }

    public ByteBuf slice(int i, int i2) {
        return new PooledNonRetainedSlicedByteBuf(this, unwrap(), i, i2);
    }

    /* access modifiers changed from: 0000 */
    public final ByteBuf duplicate0() {
        return new PooledNonRetainedDuplicateByteBuf(this, unwrap());
    }
}
