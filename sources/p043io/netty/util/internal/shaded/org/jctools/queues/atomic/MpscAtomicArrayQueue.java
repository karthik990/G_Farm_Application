package p043io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import p043io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue */
public final class MpscAtomicArrayQueue<E> extends AtomicReferenceArrayQueue<E> implements QueueProgressIndicators {
    private final AtomicLong consumerIndex = new AtomicLong();
    private volatile long headCache;
    private final AtomicLong producerIndex = new AtomicLong();

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public MpscAtomicArrayQueue(int i) {
        super(i);
    }

    public boolean offer(E e) {
        long lvProducerIndex;
        if (e != null) {
            int i = this.mask;
            long j = (long) (i + 1);
            long lvConsumerIndexCache = lvConsumerIndexCache();
            do {
                lvProducerIndex = lvProducerIndex();
                long j2 = lvProducerIndex - j;
                if (lvConsumerIndexCache <= j2) {
                    lvConsumerIndexCache = lvConsumerIndex();
                    if (lvConsumerIndexCache <= j2) {
                        return false;
                    }
                    svConsumerIndexCache(lvConsumerIndexCache);
                }
            } while (!casProducerIndex(lvProducerIndex, 1 + lvProducerIndex));
            soElement(calcElementOffset(lvProducerIndex, i), e);
            return true;
        }
        throw new NullPointerException();
    }

    public final int weakOffer(E e) {
        if (e != null) {
            int i = this.mask;
            long j = (long) (i + 1);
            long lvProducerIndex = lvProducerIndex();
            long j2 = lvProducerIndex - j;
            if (lvConsumerIndexCache() <= j2) {
                long lvConsumerIndex = lvConsumerIndex();
                if (lvConsumerIndex <= j2) {
                    return 1;
                }
                svConsumerIndexCache(lvConsumerIndex);
            }
            if (!casProducerIndex(lvProducerIndex, 1 + lvProducerIndex)) {
                return -1;
            }
            soElement(calcElementOffset(lvProducerIndex, i), e);
            return 0;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    public E poll() {
        long lvConsumerIndex = lvConsumerIndex();
        int calcElementOffset = calcElementOffset(lvConsumerIndex);
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        E lvElement = lvElement(atomicReferenceArray, calcElementOffset);
        if (lvElement != null) {
            spElement(atomicReferenceArray, calcElementOffset, null);
            soConsumerIndex(lvConsumerIndex + 1);
        } else if (lvConsumerIndex == lvProducerIndex()) {
            return null;
        } else {
            do {
                lvElement = lvElement(atomicReferenceArray, calcElementOffset);
            } while (lvElement == null);
        }
        spElement(atomicReferenceArray, calcElementOffset, null);
        soConsumerIndex(lvConsumerIndex + 1);
        return lvElement;
    }

    public E peek() {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        long lvConsumerIndex = lvConsumerIndex();
        int calcElementOffset = calcElementOffset(lvConsumerIndex);
        E lvElement = lvElement(atomicReferenceArray, calcElementOffset);
        if (lvElement != null) {
            return lvElement;
        }
        if (lvConsumerIndex == lvProducerIndex()) {
            return null;
        }
        do {
            lvElement = lvElement(atomicReferenceArray, calcElementOffset);
        } while (lvElement == null);
        return lvElement;
    }

    public int size() {
        long lvConsumerIndex = lvConsumerIndex();
        while (true) {
            long lvProducerIndex = lvProducerIndex();
            long lvConsumerIndex2 = lvConsumerIndex();
            if (lvConsumerIndex == lvConsumerIndex2) {
                return (int) (lvProducerIndex - lvConsumerIndex2);
            }
            lvConsumerIndex = lvConsumerIndex2;
        }
    }

    public boolean isEmpty() {
        return lvConsumerIndex() == lvProducerIndex();
    }

    public long currentProducerIndex() {
        return lvProducerIndex();
    }

    public long currentConsumerIndex() {
        return lvConsumerIndex();
    }

    private long lvConsumerIndex() {
        return this.consumerIndex.get();
    }

    private long lvProducerIndex() {
        return this.producerIndex.get();
    }

    /* access modifiers changed from: protected */
    public final long lvConsumerIndexCache() {
        return this.headCache;
    }

    /* access modifiers changed from: protected */
    public final void svConsumerIndexCache(long j) {
        this.headCache = j;
    }

    /* access modifiers changed from: protected */
    public final boolean casProducerIndex(long j, long j2) {
        return this.producerIndex.compareAndSet(j, j2);
    }

    /* access modifiers changed from: protected */
    public void soConsumerIndex(long j) {
        this.consumerIndex.lazySet(j);
    }
}
