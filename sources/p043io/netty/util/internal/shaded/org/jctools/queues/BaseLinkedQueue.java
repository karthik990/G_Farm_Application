package p043io.netty.util.internal.shaded.org.jctools.queues;

import java.util.Iterator;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueue */
abstract class BaseLinkedQueue<E> extends BaseLinkedQueueConsumerNodeRef<E> {
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    public int capacity() {
        return -1;
    }

    BaseLinkedQueue() {
    }

    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getName();
    }

    public final int size() {
        LinkedQueueNode lvConsumerNode = lvConsumerNode();
        LinkedQueueNode lvProducerNode = lvProducerNode();
        int i = 0;
        while (lvConsumerNode != lvProducerNode && lvConsumerNode != null && i < Integer.MAX_VALUE) {
            LinkedQueueNode lvNext = lvConsumerNode.lvNext();
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
    public E getSingleConsumerNodeValue(LinkedQueueNode<E> linkedQueueNode, LinkedQueueNode<E> linkedQueueNode2) {
        E andNullValue = linkedQueueNode2.getAndNullValue();
        linkedQueueNode.soNext(linkedQueueNode);
        spConsumerNode(linkedQueueNode2);
        return andNullValue;
    }

    public E relaxedPoll() {
        LinkedQueueNode lpConsumerNode = lpConsumerNode();
        LinkedQueueNode lvNext = lpConsumerNode.lvNext();
        if (lvNext != null) {
            return getSingleConsumerNodeValue(lpConsumerNode, lvNext);
        }
        return null;
    }

    public int drain(Consumer<E> consumer) {
        long j = 0;
        do {
            int drain = drain(consumer, 4096);
            j += (long) drain;
            if (drain != 4096) {
                break;
            }
        } while (j <= 2147479551);
        return (int) j;
    }

    public int drain(Consumer<E> consumer, int i) {
        LinkedQueueNode linkedQueueNode = this.consumerNode;
        int i2 = 0;
        while (i2 < i) {
            LinkedQueueNode lvNext = linkedQueueNode.lvNext();
            if (lvNext == null) {
                return i2;
            }
            consumer.accept(getSingleConsumerNodeValue(linkedQueueNode, lvNext));
            i2++;
            linkedQueueNode = lvNext;
        }
        return i;
    }

    public void drain(Consumer<E> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        LinkedQueueNode linkedQueueNode = this.consumerNode;
        int i = 0;
        while (exitCondition.keepRunning()) {
            int i2 = i;
            LinkedQueueNode linkedQueueNode2 = linkedQueueNode;
            for (int i3 = 0; i3 < 4096; i3++) {
                LinkedQueueNode lvNext = linkedQueueNode2.lvNext();
                if (lvNext == null) {
                    i2 = waitStrategy.idle(i2);
                } else {
                    consumer.accept(getSingleConsumerNodeValue(linkedQueueNode2, lvNext));
                    linkedQueueNode2 = lvNext;
                    i2 = 0;
                }
            }
            linkedQueueNode = linkedQueueNode2;
            i = i2;
        }
    }

    public E relaxedPeek() {
        LinkedQueueNode lvNext = this.consumerNode.lvNext();
        if (lvNext != null) {
            return lvNext.lpValue();
        }
        return null;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }
}
