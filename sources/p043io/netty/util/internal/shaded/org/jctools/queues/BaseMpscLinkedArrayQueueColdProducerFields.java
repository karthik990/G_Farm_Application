package p043io.netty.util.internal.shaded.org.jctools.queues;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueColdProducerFields */
/* compiled from: BaseMpscLinkedArrayQueue */
abstract class BaseMpscLinkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueuePad3<E> {
    protected E[] producerBuffer;
    protected volatile long producerLimit;
    protected long producerMask;

    BaseMpscLinkedArrayQueueColdProducerFields() {
    }
}
