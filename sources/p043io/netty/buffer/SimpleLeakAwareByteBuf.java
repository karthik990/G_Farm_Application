package p043io.netty.buffer;

import java.nio.ByteOrder;
import p043io.netty.util.ResourceLeakTracker;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.buffer.SimpleLeakAwareByteBuf */
class SimpleLeakAwareByteBuf extends WrappedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final ResourceLeakTracker<ByteBuf> leak;
    private final ByteBuf trackedByteBuf;

    public ByteBuf touch() {
        return this;
    }

    public ByteBuf touch(Object obj) {
        return this;
    }

    SimpleLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        super(byteBuf);
        this.trackedByteBuf = (ByteBuf) ObjectUtil.checkNotNull(byteBuf2, "trackedByteBuf");
        this.leak = (ResourceLeakTracker) ObjectUtil.checkNotNull(resourceLeakTracker, "leak");
    }

    SimpleLeakAwareByteBuf(ByteBuf byteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        this(byteBuf, byteBuf, resourceLeakTracker);
    }

    public ByteBuf slice() {
        return newSharedLeakAwareByteBuf(super.slice());
    }

    public ByteBuf retainedSlice() {
        return unwrappedDerived(super.retainedSlice());
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return unwrappedDerived(super.retainedSlice(i, i2));
    }

    public ByteBuf retainedDuplicate() {
        return unwrappedDerived(super.retainedDuplicate());
    }

    public ByteBuf readRetainedSlice(int i) {
        return unwrappedDerived(super.readRetainedSlice(i));
    }

    public ByteBuf slice(int i, int i2) {
        return newSharedLeakAwareByteBuf(super.slice(i, i2));
    }

    public ByteBuf duplicate() {
        return newSharedLeakAwareByteBuf(super.duplicate());
    }

    public ByteBuf readSlice(int i) {
        return newSharedLeakAwareByteBuf(super.readSlice(i));
    }

    public ByteBuf asReadOnly() {
        return newSharedLeakAwareByteBuf(super.asReadOnly());
    }

    public final boolean release() {
        if (!super.release()) {
            return false;
        }
        closeLeak();
        return true;
    }

    public final boolean release(int i) {
        if (!super.release(i)) {
            return false;
        }
        closeLeak();
        return true;
    }

    private void closeLeak() {
        this.leak.close(this.trackedByteBuf);
    }

    public ByteBuf order(ByteOrder byteOrder) {
        if (order() == byteOrder) {
            return this;
        }
        return newSharedLeakAwareByteBuf(super.order(byteOrder));
    }

    private ByteBuf unwrappedDerived(ByteBuf byteBuf) {
        ByteBuf unwrapSwapped = unwrapSwapped(byteBuf);
        if (!(unwrapSwapped instanceof AbstractPooledDerivedByteBuf)) {
            return newSharedLeakAwareByteBuf(byteBuf);
        }
        ((AbstractPooledDerivedByteBuf) unwrapSwapped).parent(this);
        ResourceLeakTracker track = AbstractByteBuf.leakDetector.track(byteBuf);
        if (track == null) {
            return byteBuf;
        }
        return newLeakAwareByteBuf(byteBuf, track);
    }

    private static ByteBuf unwrapSwapped(ByteBuf byteBuf) {
        if (byteBuf instanceof SwappedByteBuf) {
            do {
                byteBuf = byteBuf.unwrap();
            } while (byteBuf instanceof SwappedByteBuf);
        }
        return byteBuf;
    }

    private SimpleLeakAwareByteBuf newSharedLeakAwareByteBuf(ByteBuf byteBuf) {
        return newLeakAwareByteBuf(byteBuf, this.trackedByteBuf, this.leak);
    }

    private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return newLeakAwareByteBuf(byteBuf, byteBuf, resourceLeakTracker);
    }

    /* access modifiers changed from: protected */
    public SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return new SimpleLeakAwareByteBuf(byteBuf, byteBuf2, resourceLeakTracker);
    }
}
