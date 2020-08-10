package p043io.netty.util.internal.shaded.org.jctools.queues;

import android.support.p009v4.media.session.PlaybackStateCompat;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue */
public class SpscLinkedQueue<E> extends BaseLinkedQueue<E> {
    public /* bridge */ /* synthetic */ int capacity() {
        return super.capacity();
    }

    public /* bridge */ /* synthetic */ int drain(Consumer consumer) {
        return super.drain(consumer);
    }

    public /* bridge */ /* synthetic */ int drain(Consumer consumer, int i) {
        return super.drain(consumer, i);
    }

    public /* bridge */ /* synthetic */ void drain(Consumer consumer, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    public /* bridge */ /* synthetic */ boolean relaxedOffer(Object obj) {
        return super.relaxedOffer(obj);
    }

    public /* bridge */ /* synthetic */ Object relaxedPeek() {
        return super.relaxedPeek();
    }

    public /* bridge */ /* synthetic */ Object relaxedPoll() {
        return super.relaxedPoll();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscLinkedQueue() {
        spProducerNode(new LinkedQueueNode());
        spConsumerNode(this.producerNode);
        this.consumerNode.soNext(null);
    }

    public boolean offer(E e) {
        if (e != null) {
            LinkedQueueNode linkedQueueNode = new LinkedQueueNode(e);
            lpProducerNode().soNext(linkedQueueNode);
            spProducerNode(linkedQueueNode);
            return true;
        }
        throw new NullPointerException();
    }

    public E poll() {
        return relaxedPoll();
    }

    public E peek() {
        return relaxedPeek();
    }

    public int fill(Supplier<E> supplier) {
        long j = 0;
        do {
            fill(supplier, 4096);
            j += PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        } while (j <= 2147479551);
        return (int) j;
    }

    public int fill(Supplier<E> supplier, int i) {
        if (i == 0) {
            return 0;
        }
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode(supplier.get());
        int i2 = 1;
        LinkedQueueNode linkedQueueNode2 = linkedQueueNode;
        while (i2 < i) {
            LinkedQueueNode linkedQueueNode3 = new LinkedQueueNode(supplier.get());
            linkedQueueNode2.soNext(linkedQueueNode3);
            i2++;
            linkedQueueNode2 = linkedQueueNode3;
        }
        lpProducerNode().soNext(linkedQueueNode);
        spProducerNode(linkedQueueNode2);
        return i;
    }

    public void fill(Supplier<E> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        LinkedQueueNode linkedQueueNode = this.producerNode;
        while (exitCondition.keepRunning()) {
            int i = 0;
            while (i < 4096) {
                LinkedQueueNode linkedQueueNode2 = new LinkedQueueNode(supplier.get());
                linkedQueueNode.soNext(linkedQueueNode2);
                this.producerNode = linkedQueueNode2;
                i++;
                linkedQueueNode = linkedQueueNode2;
            }
        }
    }
}
