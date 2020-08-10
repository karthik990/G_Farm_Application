package p043io.netty.util.internal.shaded.org.jctools.queues;

import java.util.Iterator;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import p043io.netty.util.internal.shaded.org.jctools.util.Pow2;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueue */
public abstract class BaseMpscLinkedArrayQueue<E> extends BaseMpscLinkedArrayQueueColdProducerFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {
    private static final long C_INDEX_OFFSET;
    private static final Object JUMP = new Object();
    private static final long P_INDEX_OFFSET;
    private static final long P_LIMIT_OFFSET;

    /* access modifiers changed from: protected */
    public abstract long availableInQueue(long j, long j2);

    public abstract int capacity();

    /* access modifiers changed from: protected */
    public abstract long getCurrentBufferCapacity(long j);

    /* access modifiers changed from: protected */
    public abstract int getNextBufferSize(E[] eArr);

    static {
        try {
            P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseMpscLinkedArrayQueueProducerFields.class.getDeclaredField("producerIndex"));
            try {
                C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseMpscLinkedArrayQueueConsumerFields.class.getDeclaredField("consumerIndex"));
                try {
                    P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseMpscLinkedArrayQueueColdProducerFields.class.getDeclaredField("producerLimit"));
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException e2) {
                throw new RuntimeException(e2);
            }
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    public BaseMpscLinkedArrayQueue(int i) {
        if (i >= 2) {
            int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(i);
            long j = (long) ((roundToPowerOfTwo - 1) << 1);
            Object[] allocate = CircularArrayOffsetCalculator.allocate(roundToPowerOfTwo + 1);
            this.producerBuffer = allocate;
            this.producerMask = j;
            this.consumerBuffer = allocate;
            this.consumerMask = j;
            soProducerLimit(j);
            return;
        }
        throw new IllegalArgumentException("Initial capacity must be 2 or more");
    }

    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getName();
    }

    public boolean offer(E e) {
        E e2 = e;
        if (e2 != null) {
            while (true) {
                long lvProducerLimit = lvProducerLimit();
                long lvProducerIndex = lvProducerIndex();
                if ((lvProducerIndex & 1) != 1) {
                    long j = this.producerMask;
                    Object[] objArr = this.producerBuffer;
                    if (lvProducerLimit <= lvProducerIndex) {
                        int offerSlowPath = offerSlowPath(j, lvProducerIndex, lvProducerLimit);
                        if (offerSlowPath != 0) {
                            if (offerSlowPath == 1) {
                                continue;
                            } else if (offerSlowPath == 2) {
                                return false;
                            } else {
                                if (offerSlowPath == 3) {
                                    resize(j, objArr, lvProducerIndex, e);
                                    return true;
                                }
                            }
                        }
                    }
                    if (casProducerIndex(lvProducerIndex, 2 + lvProducerIndex)) {
                        UnsafeRefArrayAccess.soElement(objArr, modifiedCalcElementOffset(lvProducerIndex, j), e2);
                        return true;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    private int offerSlowPath(long j, long j2, long j3) {
        long lvConsumerIndex = lvConsumerIndex();
        long currentBufferCapacity = getCurrentBufferCapacity(j) + lvConsumerIndex;
        if (currentBufferCapacity > j2) {
            if (!casProducerLimit(j3, currentBufferCapacity)) {
                return 1;
            }
            return 0;
        } else if (availableInQueue(j2, lvConsumerIndex) <= 0) {
            return 2;
        } else {
            if (casProducerIndex(j2, 1 + j2)) {
                return 3;
            }
            return 1;
        }
    }

    private static long modifiedCalcElementOffset(long j, long j2) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((j & j2) << (UnsafeRefArrayAccess.REF_ELEMENT_SHIFT - 1));
    }

    public E poll() {
        Object[] objArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long j2 = this.consumerMask;
        long modifiedCalcElementOffset = modifiedCalcElementOffset(j, j2);
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, modifiedCalcElementOffset);
        if (lvElement == null) {
            if (j == lvProducerIndex()) {
                return null;
            }
            do {
                lvElement = UnsafeRefArrayAccess.lvElement(objArr, modifiedCalcElementOffset);
            } while (lvElement == null);
        }
        if (lvElement == JUMP) {
            return newBufferPoll(getNextBuffer(objArr, j2), j);
        }
        UnsafeRefArrayAccess.soElement(objArr, modifiedCalcElementOffset, null);
        soConsumerIndex(j + 2);
        return lvElement;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public E peek() {
        /*
            r11 = this;
            java.lang.Object[] r0 = r11.consumerBuffer
            long r1 = r11.consumerIndex
            long r3 = r11.consumerMask
            long r5 = modifiedCalcElementOffset(r1, r3)
            java.lang.Object r7 = p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lvElement(r0, r5)
            if (r7 != 0) goto L_0x001f
            long r8 = r11.lvProducerIndex()
            int r10 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x001f
        L_0x0018:
            java.lang.Object r7 = p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lvElement(r0, r5)
            if (r7 != 0) goto L_0x001f
            goto L_0x0018
        L_0x001f:
            java.lang.Object r5 = JUMP
            if (r7 != r5) goto L_0x002c
            java.lang.Object[] r0 = r11.getNextBuffer(r0, r3)
            java.lang.Object r0 = r11.newBufferPeek(r0, r1)
            return r0
        L_0x002c:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueue.peek():java.lang.Object");
    }

    private E[] getNextBuffer(E[] eArr, long j) {
        long nextArrayOffset = nextArrayOffset(j);
        E[] eArr2 = (Object[]) UnsafeRefArrayAccess.lvElement(eArr, nextArrayOffset);
        UnsafeRefArrayAccess.soElement(eArr, nextArrayOffset, null);
        return eArr2;
    }

    private long nextArrayOffset(long j) {
        return modifiedCalcElementOffset(j + 2, Long.MAX_VALUE);
    }

    private E newBufferPoll(E[] eArr, long j) {
        long newBufferAndOffset = newBufferAndOffset(eArr, j);
        E lvElement = UnsafeRefArrayAccess.lvElement(eArr, newBufferAndOffset);
        if (lvElement != null) {
            UnsafeRefArrayAccess.soElement(eArr, newBufferAndOffset, null);
            soConsumerIndex(j + 2);
            return lvElement;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private E newBufferPeek(E[] eArr, long j) {
        E lvElement = UnsafeRefArrayAccess.lvElement(eArr, newBufferAndOffset(eArr, j));
        if (lvElement != null) {
            return lvElement;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private long newBufferAndOffset(E[] eArr, long j) {
        this.consumerBuffer = eArr;
        this.consumerMask = (long) ((eArr.length - 2) << 1);
        return modifiedCalcElementOffset(j, this.consumerMask);
    }

    public final int size() {
        long lvProducerIndex;
        long lvConsumerIndex;
        long lvConsumerIndex2 = lvConsumerIndex();
        while (true) {
            lvProducerIndex = lvProducerIndex();
            lvConsumerIndex = lvConsumerIndex();
            if (lvConsumerIndex2 == lvConsumerIndex) {
                break;
            }
            lvConsumerIndex2 = lvConsumerIndex;
        }
        long j = (lvProducerIndex - lvConsumerIndex) >> 1;
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    public final boolean isEmpty() {
        return lvConsumerIndex() == lvProducerIndex();
    }

    private long lvProducerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }

    private long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    private void soProducerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, j);
    }

    private boolean casProducerIndex(long j, long j2) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, j, j2);
    }

    private void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }

    private long lvProducerLimit() {
        return this.producerLimit;
    }

    private boolean casProducerLimit(long j, long j2) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, j, j2);
    }

    private void soProducerLimit(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, j);
    }

    public long currentProducerIndex() {
        return lvProducerIndex() / 2;
    }

    public long currentConsumerIndex() {
        return lvConsumerIndex() / 2;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    public E relaxedPoll() {
        Object[] objArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long j2 = this.consumerMask;
        long modifiedCalcElementOffset = modifiedCalcElementOffset(j, j2);
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, modifiedCalcElementOffset);
        if (lvElement == null) {
            return null;
        }
        if (lvElement == JUMP) {
            return newBufferPoll(getNextBuffer(objArr, j2), j);
        }
        UnsafeRefArrayAccess.soElement(objArr, modifiedCalcElementOffset, null);
        soConsumerIndex(j + 2);
        return lvElement;
    }

    public E relaxedPeek() {
        Object[] objArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long j2 = this.consumerMask;
        E lvElement = UnsafeRefArrayAccess.lvElement(objArr, modifiedCalcElementOffset(j, j2));
        return lvElement == JUMP ? newBufferPeek(getNextBuffer(objArr, j2), j) : lvElement;
    }

    public int fill(Supplier<E> supplier, int i) {
        while (true) {
            long lvProducerLimit = lvProducerLimit();
            long lvProducerIndex = lvProducerIndex();
            if ((lvProducerIndex & 1) != 1) {
                long j = this.producerMask;
                Object[] objArr = this.producerBuffer;
                long min = Math.min(lvProducerLimit, ((long) (i * 2)) + lvProducerIndex);
                if (lvProducerIndex == lvProducerLimit || lvProducerLimit < min) {
                    int offerSlowPath = offerSlowPath(j, lvProducerIndex, lvProducerLimit);
                    if (offerSlowPath == 1) {
                        continue;
                    } else if (offerSlowPath == 2) {
                        return 0;
                    } else {
                        if (offerSlowPath == 3) {
                            resize(j, objArr, lvProducerIndex, supplier.get());
                            return 1;
                        }
                    }
                }
                if (casProducerIndex(lvProducerIndex, min)) {
                    int i2 = (int) ((min - lvProducerIndex) / 2);
                    for (int i3 = 0; i3 < i2; i3++) {
                        UnsafeRefArrayAccess.soElement(objArr, modifiedCalcElementOffset(((long) (i3 * 2)) + lvProducerIndex, j), supplier.get());
                    }
                    return i2;
                }
            }
        }
    }

    private void resize(long j, E[] eArr, long j2, E e) {
        int nextBufferSize = getNextBufferSize(eArr);
        Object[] allocate = CircularArrayOffsetCalculator.allocate(nextBufferSize);
        this.producerBuffer = allocate;
        long j3 = (long) ((nextBufferSize - 2) << 1);
        this.producerMask = j3;
        long modifiedCalcElementOffset = modifiedCalcElementOffset(j2, j);
        UnsafeRefArrayAccess.soElement(allocate, modifiedCalcElementOffset(j2, j3), e);
        UnsafeRefArrayAccess.soElement(eArr, nextArrayOffset(j), allocate);
        long availableInQueue = availableInQueue(j2, lvConsumerIndex());
        if (availableInQueue > 0) {
            soProducerLimit(Math.min(j3, availableInQueue) + j2);
            soProducerIndex(j2 + 2);
            UnsafeRefArrayAccess.soElement(eArr, modifiedCalcElementOffset, JUMP);
            return;
        }
        throw new IllegalStateException();
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

    public void fill(Supplier<E> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            while (fill(supplier, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) != 0) {
                if (!exitCondition.keepRunning()) {
                    break;
                }
            }
            int i = 0;
            while (exitCondition.keepRunning() && fill(supplier, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) == 0) {
                i = waitStrategy.idle(i);
            }
        }
    }

    public void drain(Consumer<E> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        while (true) {
            int i = 0;
            while (exitCondition.keepRunning()) {
                Object relaxedPoll = relaxedPoll();
                if (relaxedPoll == null) {
                    i = waitStrategy.idle(i);
                } else {
                    consumer.accept(relaxedPoll);
                }
            }
            return;
        }
    }

    public int drain(Consumer<E> consumer) {
        return drain(consumer, capacity());
    }

    public int drain(Consumer<E> consumer, int i) {
        int i2 = 0;
        while (i2 < i) {
            Object relaxedPoll = relaxedPoll();
            if (relaxedPoll == null) {
                break;
            }
            consumer.accept(relaxedPoll);
            i2++;
        }
        return i2;
    }
}
