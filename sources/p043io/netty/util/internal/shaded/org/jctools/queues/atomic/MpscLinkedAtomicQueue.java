package p043io.netty.util.internal.shaded.org.jctools.queues.atomic;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscLinkedAtomicQueue */
public final class MpscLinkedAtomicQueue<E> extends BaseLinkedAtomicQueue<E> {
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public MpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode linkedQueueAtomicNode = new LinkedQueueAtomicNode();
        spConsumerNode(linkedQueueAtomicNode);
        xchgProducerNode(linkedQueueAtomicNode);
    }

    public final boolean offer(E e) {
        if (e != null) {
            LinkedQueueAtomicNode linkedQueueAtomicNode = new LinkedQueueAtomicNode(e);
            xchgProducerNode(linkedQueueAtomicNode).soNext(linkedQueueAtomicNode);
            return true;
        }
        throw new NullPointerException();
    }

    public final E poll() {
        LinkedQueueAtomicNode lvNext;
        LinkedQueueAtomicNode lpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode lvNext2 = lpConsumerNode.lvNext();
        if (lvNext2 != null) {
            return getSingleConsumerNodeValue(lpConsumerNode, lvNext2);
        }
        if (lpConsumerNode == lvProducerNode()) {
            return null;
        }
        do {
            lvNext = lpConsumerNode.lvNext();
        } while (lvNext == null);
        return getSingleConsumerNodeValue(lpConsumerNode, lvNext);
    }

    public final E peek() {
        LinkedQueueAtomicNode lvNext;
        LinkedQueueAtomicNode lpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode lvNext2 = lpConsumerNode.lvNext();
        if (lvNext2 != null) {
            return lvNext2.lpValue();
        }
        if (lpConsumerNode == lvProducerNode()) {
            return null;
        }
        do {
            lvNext = lpConsumerNode.lvNext();
        } while (lvNext == null);
        return lvNext.lpValue();
    }
}
