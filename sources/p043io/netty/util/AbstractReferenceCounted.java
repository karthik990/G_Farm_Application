package p043io.netty.util;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.AbstractReferenceCounted */
public abstract class AbstractReferenceCounted implements ReferenceCounted {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
    private volatile int refCnt = 1;

    /* access modifiers changed from: protected */
    public abstract void deallocate();

    public final int refCnt() {
        return this.refCnt;
    }

    /* access modifiers changed from: protected */
    public final void setRefCnt(int i) {
        this.refCnt = i;
    }

    public ReferenceCounted retain() {
        return retain0(1);
    }

    public ReferenceCounted retain(int i) {
        return retain0(ObjectUtil.checkPositive(i, "increment"));
    }

    private ReferenceCounted retain0(int i) {
        int i2;
        int i3;
        do {
            i2 = this.refCnt;
            i3 = i2 + i;
            if (i3 <= i) {
                throw new IllegalReferenceCountException(i2, i);
            }
        } while (!refCntUpdater.compareAndSet(this, i2, i3));
        return this;
    }

    public ReferenceCounted touch() {
        return touch(null);
    }

    public boolean release() {
        return release0(1);
    }

    public boolean release(int i) {
        return release0(ObjectUtil.checkPositive(i, "decrement"));
    }

    private boolean release0(int i) {
        int i2;
        do {
            i2 = this.refCnt;
            if (i2 < i) {
                throw new IllegalReferenceCountException(i2, -i);
            }
        } while (!refCntUpdater.compareAndSet(this, i2, i2 - i));
        if (i2 != i) {
            return false;
        }
        deallocate();
        return true;
    }
}
