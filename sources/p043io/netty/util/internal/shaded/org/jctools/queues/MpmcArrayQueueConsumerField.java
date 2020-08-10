package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueueConsumerField */
/* compiled from: MpmcArrayQueue */
abstract class MpmcArrayQueueConsumerField<E> extends MpmcArrayQueueL2Pad<E> {
    private static final long C_INDEX_OFFSET;
    private volatile long consumerIndex;

    static {
        try {
            C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpmcArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MpmcArrayQueueConsumerField(int i) {
        super(i);
    }

    public final long lvConsumerIndex() {
        return this.consumerIndex;
    }

    /* access modifiers changed from: protected */
    public final boolean casConsumerIndex(long j, long j2) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, C_INDEX_OFFSET, j, j2);
    }
}
