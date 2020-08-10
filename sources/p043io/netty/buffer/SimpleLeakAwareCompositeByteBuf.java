package p043io.netty.buffer;

import java.nio.ByteOrder;
import p043io.netty.util.ResourceLeakTracker;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.buffer.SimpleLeakAwareCompositeByteBuf */
class SimpleLeakAwareCompositeByteBuf extends WrappedCompositeByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final ResourceLeakTracker<ByteBuf> leak;

    SimpleLeakAwareCompositeByteBuf(CompositeByteBuf compositeByteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        super(compositeByteBuf);
        this.leak = (ResourceLeakTracker) ObjectUtil.checkNotNull(resourceLeakTracker, "leak");
    }

    public final boolean release() {
        ByteBuf unwrap = unwrap();
        if (!super.release()) {
            return false;
        }
        closeLeak(unwrap);
        return true;
    }

    public final boolean release(int i) {
        ByteBuf unwrap = unwrap();
        if (!super.release(i)) {
            return false;
        }
        closeLeak(unwrap);
        return true;
    }

    private void closeLeak(ByteBuf byteBuf) {
        this.leak.close(byteBuf);
    }

    public ByteBuf order(ByteOrder byteOrder) {
        if (order() == byteOrder) {
            return this;
        }
        return newLeakAwareByteBuf(super.order(byteOrder));
    }

    public ByteBuf slice() {
        return newLeakAwareByteBuf(super.slice());
    }

    public ByteBuf retainedSlice() {
        return newLeakAwareByteBuf(super.retainedSlice());
    }

    public ByteBuf slice(int i, int i2) {
        return newLeakAwareByteBuf(super.slice(i, i2));
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return newLeakAwareByteBuf(super.retainedSlice(i, i2));
    }

    public ByteBuf duplicate() {
        return newLeakAwareByteBuf(super.duplicate());
    }

    public ByteBuf retainedDuplicate() {
        return newLeakAwareByteBuf(super.retainedDuplicate());
    }

    public ByteBuf readSlice(int i) {
        return newLeakAwareByteBuf(super.readSlice(i));
    }

    public ByteBuf readRetainedSlice(int i) {
        return newLeakAwareByteBuf(super.readRetainedSlice(i));
    }

    public ByteBuf asReadOnly() {
        return newLeakAwareByteBuf(super.asReadOnly());
    }

    private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf) {
        return newLeakAwareByteBuf(byteBuf, unwrap(), this.leak);
    }

    /* access modifiers changed from: protected */
    public SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return new SimpleLeakAwareByteBuf(byteBuf, byteBuf2, resourceLeakTracker);
    }
}
