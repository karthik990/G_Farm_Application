package p043io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueue */
abstract class BaseLinkedAtomicQueue<E> extends AbstractQueue<E> {
    private final AtomicReference<LinkedQueueAtomicNode<E>> consumerNode = new AtomicReference<>();
    private final AtomicReference<LinkedQueueAtomicNode<E>> producerNode = new AtomicReference<>();

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lvProducerNode() {
        return (LinkedQueueAtomicNode) this.producerNode.get();
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lpProducerNode() {
        return (LinkedQueueAtomicNode) this.producerNode.get();
    }

    /* access modifiers changed from: protected */
    public final void spProducerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        this.producerNode.lazySet(linkedQueueAtomicNode);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> xchgProducerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        return (LinkedQueueAtomicNode) this.producerNode.getAndSet(linkedQueueAtomicNode);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lvConsumerNode() {
        return (LinkedQueueAtomicNode) this.consumerNode.get();
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lpConsumerNode() {
        return (LinkedQueueAtomicNode) this.consumerNode.get();
    }

    /* access modifiers changed from: protected */
    public final void spConsumerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        this.consumerNode.lazySet(linkedQueueAtomicNode);
    }

    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getName();
    }

    public final int size() {
        LinkedQueueAtomicNode lvConsumerNode = lvConsumerNode();
        LinkedQueueAtomicNode lvProducerNode = lvProducerNode();
        int i = 0;
        while (lvConsumerNode != lvProducerNode && lvConsumerNode != null && i < Integer.MAX_VALUE) {
            LinkedQueueAtomicNode lvNext = lvConsumerNode.lvNext();
            if (lvNext == lvConsumerNode) {
                return i;
            }
            i++;
            lvConsumerNode = lvNext;
        }
        return i;
    }

    public final boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }

    /* access modifiers changed from: protected */
    public E getSingleConsumerNodeValue(LinkedQueueAtomicNode<E> linkedQueueAtomicNode, LinkedQueueAtomicNode<E> linkedQueueAtomicNode2) {
        E andNullValue = linkedQueueAtomicNode2.getAndNullValue();
        linkedQueueAtomicNode.soNext(linkedQueueAtomicNode);
        spConsumerNode(linkedQueueAtomicNode2);
        return andNullValue;
    }
}
