package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue */
public class MpscArrayQueue<E> extends MpscArrayQueueConsumerField<E> implements QueueProgressIndicators {
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

    public MpscArrayQueue(int i) {
        super(i);
    }

    public boolean offerIfBelowThreshold(E e, int i) {
        long lvProducerIndex;
        E e2 = e;
        if (e2 != null) {
            long j = this.mask;
            long j2 = j + 1;
            long lvProducerLimit = lvProducerLimit();
            do {
                lvProducerIndex = lvProducerIndex();
                long j3 = (long) i;
                if (j2 - (lvProducerLimit - lvProducerIndex) >= j3) {
                    long lvConsumerIndex = lvConsumerIndex();
                    if (lvProducerIndex - lvConsumerIndex >= j3) {
                        return false;
                    }
                    lvProducerLimit = lvConsumerIndex + j2;
                    soProducerLimit(lvProducerLimit);
                }
            } while (!casProducerIndex(lvProducerIndex, lvProducerIndex + 1));
            UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(lvProducerIndex, j), e2);
            return true;
        }
        throw new NullPointerException();
    }

    public boolean offer(E e) {
        long lvProducerIndex;
        if (e != null) {
            long j = this.mask;
            long lvProducerLimit = lvProducerLimit();
            do {
                lvProducerIndex = lvProducerIndex();
                if (lvProducerIndex >= lvProducerLimit) {
                    lvProducerLimit = lvConsumerIndex() + j + 1;
                    if (lvProducerIndex >= lvProducerLimit) {
                        return false;
                    }
                    soProducerLimit(lvProducerLimit);
                }
            } while (!casProducerIndex(lvProducerIndex, 1 + lvProducerIndex));
            UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(lvProducerIndex, j), e);
            return true;
        }
        throw new NullPointerException();
    }

    public final int failFastOffer(E e) {
        if (e != null) {
            long j = this.mask;
            long j2 = j + 1;
            long lvProducerIndex = lvProducerIndex();
            if (lvProducerIndex >= lvProducerLimit()) {
                long lvConsumerIndex = lvConsumerIndex() + j2;
                if (lvProducerIndex >= lvConsumerIndex) {
                    return 1;
                }
                soProducerLimit(lvConsumerIndex);
            }
            if (!casProducerIndex(lvProducerIndex, 1 + lvProducerIndex)) {
                return -1;
            }
            UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(lvProducerIndex, j), e);
            return 0;
        }
        throw new NullPointerException();
    }

    public E poll() {
        long lpConsumerIndex = lpConsumerIndex();
        long calcElementOffset = calcElementOffset(lpConsumerIndex);
        Object[] objArr = this.buffer;
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
        if (lvElement != null) {
            UnsafeRefArrayAccess.spElement(objArr, calcElementOffset, null);
            soConsumerIndex(lpConsumerIndex + 1);
        } else if (lpConsumerIndex == lvProducerIndex()) {
            return null;
        } else {
            do {
                lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
            } while (lvElement == null);
        }
        UnsafeRefArrayAccess.spElement(objArr, calcElementOffset, null);
        soConsumerIndex(lpConsumerIndex + 1);
        return lvElement;
    }

    public E peek() {
        Object[] objArr = this.buffer;
        long lpConsumerIndex = lpConsumerIndex();
        long calcElementOffset = calcElementOffset(lpConsumerIndex);
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
        if (lvElement != null) {
            return lvElement;
        }
        if (lpConsumerIndex == lvProducerIndex()) {
            return null;
        }
        do {
            lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
        } while (lvElement == null);
        return lvElement;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    public E relaxedPoll() {
        Object[] objArr = this.buffer;
        long lpConsumerIndex = lpConsumerIndex();
        long calcElementOffset = calcElementOffset(lpConsumerIndex);
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
        if (lvElement == null) {
            return null;
        }
        UnsafeRefArrayAccess.spElement(objArr, calcElementOffset, null);
        soConsumerIndex(lpConsumerIndex + 1);
        return lvElement;
    }

    public E relaxedPeek() {
        return UnsafeRefArrayAccess.lvElement(this.buffer, calcElementOffset(lpConsumerIndex(), this.mask));
    }

    public int drain(Consumer<E> consumer) {
        return drain(consumer, capacity());
    }

    public int fill(Supplier<E> supplier) {
        int capacity = capacity();
        long j = 0;
        do {
            int fill = fill(supplier, MpmcArrayQueue.RECOMENDED_OFFER_BATCH);
            if (fill == 0) {
                return (int) j;
            }
            j += (long) fill;
        } while (j <= ((long) capacity));
        return (int) j;
    }

    public int drain(Consumer<E> consumer, int i) {
        Object[] objArr = this.buffer;
        long j = this.mask;
        long lpConsumerIndex = lpConsumerIndex();
        for (int i2 = 0; i2 < i; i2++) {
            long j2 = ((long) i2) + lpConsumerIndex;
            long calcElementOffset = calcElementOffset(j2, j);
            Object lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
            if (lvElement == null) {
                return i2;
            }
            UnsafeRefArrayAccess.spElement(objArr, calcElementOffset, null);
            soConsumerIndex(j2 + 1);
            consumer.accept(lvElement);
        }
        return i;
    }

    public int fill(Supplier<E> supplier, int i) {
        long lvProducerIndex;
        int i2;
        int min;
        long j = this.mask;
        long j2 = 1 + j;
        long lvProducerLimit = lvProducerLimit();
        do {
            lvProducerIndex = lvProducerIndex();
            long j3 = lvProducerLimit - lvProducerIndex;
            if (j3 <= 0) {
                lvProducerLimit = lvConsumerIndex() + j2;
                j3 = lvProducerLimit - lvProducerIndex;
                if (j3 <= 0) {
                    return 0;
                }
                soProducerLimit(lvProducerLimit);
            }
            min = Math.min((int) j3, i);
        } while (!casProducerIndex(lvProducerIndex, ((long) min) + lvProducerIndex));
        Object[] objArr = this.buffer;
        for (i2 = 0; i2 < min; i2++) {
            UnsafeRefArrayAccess.soElement(objArr, calcElementOffset(((long) i2) + lvProducerIndex, j), supplier.get());
        }
        return min;
    }

    public void drain(Consumer<E> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        Object[] objArr = this.buffer;
        long j = this.mask;
        long lpConsumerIndex = lpConsumerIndex();
        int i = 0;
        while (exitCondition.keepRunning()) {
            int i2 = i;
            long j2 = lpConsumerIndex;
            for (int i3 = 0; i3 < 4096; i3++) {
                long calcElementOffset = calcElementOffset(j2, j);
                Object lvElement = UnsafeRefArrayAccess.lvElement(objArr, calcElementOffset);
                if (lvElement == null) {
                    i2 = waitStrategy.idle(i2);
                    Consumer<E> consumer2 = consumer;
                } else {
                    WaitStrategy waitStrategy2 = waitStrategy;
                    j2++;
                    UnsafeRefArrayAccess.spElement(objArr, calcElementOffset, null);
                    soConsumerIndex(j2);
                    consumer.accept(lvElement);
                    i2 = 0;
                }
            }
            Consumer<E> consumer3 = consumer;
            WaitStrategy waitStrategy3 = waitStrategy;
            long j3 = j2;
            i = i2;
            lpConsumerIndex = j3;
        }
    }

    public void fill(Supplier<E> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        while (true) {
            int i = 0;
            while (true) {
                if (!exitCondition.keepRunning()) {
                    return;
                }
                if (fill(supplier, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) == 0) {
                    i = waitStrategy.idle(i);
                }
            }
        }
    }
}
