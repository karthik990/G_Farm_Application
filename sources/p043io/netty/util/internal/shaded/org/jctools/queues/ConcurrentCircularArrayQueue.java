package p043io.netty.util.internal.shaded.org.jctools.queues;

import java.util.Iterator;
import p043io.netty.util.internal.shaded.org.jctools.util.Pow2;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.ConcurrentCircularArrayQueue */
public abstract class ConcurrentCircularArrayQueue<E> extends ConcurrentCircularArrayQueueL0Pad<E> {
    protected final E[] buffer;
    protected final long mask;

    public ConcurrentCircularArrayQueue(int i) {
        int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(i);
        this.mask = (long) (roundToPowerOfTwo - 1);
        this.buffer = CircularArrayOffsetCalculator.allocate(roundToPowerOfTwo);
    }

    /* access modifiers changed from: protected */
    public final long calcElementOffset(long j) {
        return calcElementOffset(j, this.mask);
    }

    protected static long calcElementOffset(long j, long j2) {
        return CircularArrayOffsetCalculator.calcElementOffset(j, j2);
    }

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getName();
    }

    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public int capacity() {
        return (int) (this.mask + 1);
    }

    public final int size() {
        return IndexedQueueSizeUtil.size(this);
    }

    public final boolean isEmpty() {
        return IndexedQueueSizeUtil.isEmpty(this);
    }

    public final long currentProducerIndex() {
        return lvProducerIndex();
    }

    public final long currentConsumerIndex() {
        return lvConsumerIndex();
    }
}
