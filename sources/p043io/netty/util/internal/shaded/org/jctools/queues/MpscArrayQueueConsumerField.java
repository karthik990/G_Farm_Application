package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueConsumerField */
/* compiled from: MpscArrayQueue */
abstract class MpscArrayQueueConsumerField<E> extends MpscArrayQueueL2Pad<E> {
    private static final long C_INDEX_OFFSET;
    protected long consumerIndex;

    static {
        try {
            C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MpscArrayQueueConsumerField(int i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    public final long lpConsumerIndex() {
        return this.consumerIndex;
    }

    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    /* access modifiers changed from: protected */
    public void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }
}
