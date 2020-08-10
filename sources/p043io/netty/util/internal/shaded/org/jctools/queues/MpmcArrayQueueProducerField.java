package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueueProducerField */
/* compiled from: MpmcArrayQueue */
abstract class MpmcArrayQueueProducerField<E> extends MpmcArrayQueueL1Pad<E> {
    private static final long P_INDEX_OFFSET;
    private volatile long producerIndex;

    static {
        try {
            P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpmcArrayQueueProducerField.class.getDeclaredField("producerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MpmcArrayQueueProducerField(int i) {
        super(i);
    }

    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    /* access modifiers changed from: protected */
    public final boolean casProducerIndex(long j, long j2) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, j, j2);
    }
}
