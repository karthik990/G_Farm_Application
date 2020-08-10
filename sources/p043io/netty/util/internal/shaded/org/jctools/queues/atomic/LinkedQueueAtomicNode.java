package p043io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode */
public final class LinkedQueueAtomicNode<E> extends AtomicReference<LinkedQueueAtomicNode<E>> {
    private static final long serialVersionUID = 2404266111789071508L;
    private E value;

    LinkedQueueAtomicNode() {
    }

    LinkedQueueAtomicNode(E e) {
        spValue(e);
    }

    public E getAndNullValue() {
        E lpValue = lpValue();
        spValue(null);
        return lpValue;
    }

    public E lpValue() {
        return this.value;
    }

    public void spValue(E e) {
        this.value = e;
    }

    public void soNext(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        lazySet(linkedQueueAtomicNode);
    }

    public LinkedQueueAtomicNode<E> lvNext() {
        return (LinkedQueueAtomicNode) get();
    }
}
