package p043io.netty.util.internal.shaded.org.jctools.queues;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueConsumerFields */
/* compiled from: BaseMpscLinkedArrayQueue */
abstract class BaseMpscLinkedArrayQueueConsumerFields<E> extends BaseMpscLinkedArrayQueuePad2<E> {
    protected E[] consumerBuffer;
    protected long consumerIndex;
    protected long consumerMask;

    BaseMpscLinkedArrayQueueConsumerFields() {
    }
}
