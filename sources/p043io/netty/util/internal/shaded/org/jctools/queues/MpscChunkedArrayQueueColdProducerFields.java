package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.Pow2;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueueColdProducerFields */
/* compiled from: MpscChunkedArrayQueue */
abstract class MpscChunkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueue<E> {
    protected final long maxQueueCapacity;

    public MpscChunkedArrayQueueColdProducerFields(int i, int i2) {
        super(i);
        if (i2 < 4) {
            throw new IllegalArgumentException("Max capacity must be 4 or more");
        } else if (Pow2.roundToPowerOfTwo(i) < Pow2.roundToPowerOfTwo(i2)) {
            this.maxQueueCapacity = ((long) Pow2.roundToPowerOfTwo(i2)) << 1;
        } else {
            throw new IllegalArgumentException("Initial capacity cannot exceed maximum capacity(both rounded up to a power of 2)");
        }
    }
}
