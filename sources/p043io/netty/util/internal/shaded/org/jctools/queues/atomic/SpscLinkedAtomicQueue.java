package p043io.netty.util.internal.shaded.org.jctools.queues.atomic;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue */
public final class SpscLinkedAtomicQueue<E> extends BaseLinkedAtomicQueue<E> {
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode linkedQueueAtomicNode = new LinkedQueueAtomicNode();
        spProducerNode(linkedQueueAtomicNode);
        spConsumerNode(linkedQueueAtomicNode);
        linkedQueueAtomicNode.soNext(null);
    }

    public boolean offer(E e) {
        if (e != null) {
            LinkedQueueAtomicNode linkedQueueAtomicNode = new LinkedQueueAtomicNode(e);
            lpProducerNode().soNext(linkedQueueAtomicNode);
            spProducerNode(linkedQueueAtomicNode);
            return true;
        }
        throw new NullPointerException();
    }

    public E poll() {
        LinkedQueueAtomicNode lpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode lvNext = lpConsumerNode.lvNext();
        if (lvNext != null) {
            return getSingleConsumerNodeValue(lpConsumerNode, lvNext);
        }
        return null;
    }

    public E peek() {
        LinkedQueueAtomicNode lvNext = lpConsumerNode().lvNext();
        if (lvNext != null) {
            return lvNext.lpValue();
        }
        return null;
    }
}
