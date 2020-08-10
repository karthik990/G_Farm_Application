package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import p043io.netty.util.internal.shaded.org.jctools.util.JvmInfo;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue */
public class MpmcArrayQueue<E> extends MpmcArrayQueueConsumerField<E> implements QueueProgressIndicators {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int RECOMENDED_OFFER_BATCH = (JvmInfo.CPUs * 4);
    static final int RECOMENDED_POLL_BATCH = (JvmInfo.CPUs * 4);
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

    public MpmcArrayQueue(int i) {
        super(validateCapacity(i));
    }

    private static int validateCapacity(int i) {
        if (i >= 2) {
            return i;
        }
        throw new IllegalArgumentException("Minimum size is 2");
    }

    public boolean offer(E e) {
        E e2 = e;
        if (e2 != null) {
            long j = this.mask;
            long j2 = j + 1;
            long[] jArr = this.sequenceBuffer;
            long j3 = Long.MAX_VALUE;
            while (true) {
                long lvProducerIndex = lvProducerIndex();
                long calcSequenceOffset = calcSequenceOffset(lvProducerIndex, j);
                long lvSequence = lvSequence(jArr, calcSequenceOffset);
                if (lvSequence < lvProducerIndex) {
                    long j4 = lvProducerIndex - j2;
                    if (j4 <= j3) {
                        j3 = lvConsumerIndex();
                        if (j4 <= j3) {
                            return false;
                        }
                    }
                    lvSequence = lvProducerIndex + 1;
                }
                int i = (lvSequence > lvProducerIndex ? 1 : (lvSequence == lvProducerIndex ? 0 : -1));
                long j5 = j2;
                if (i <= 0) {
                    long j6 = lvProducerIndex + 1;
                    if (casProducerIndex(lvProducerIndex, j6)) {
                        UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(lvProducerIndex, j), e2);
                        soSequence(jArr, calcSequenceOffset, j6);
                        return true;
                    }
                }
                j2 = j5;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public E poll() {
        long[] jArr = this.sequenceBuffer;
        long j = this.mask;
        long j2 = -1;
        while (true) {
            long lvConsumerIndex = lvConsumerIndex();
            long calcSequenceOffset = calcSequenceOffset(lvConsumerIndex, j);
            long lvSequence = lvSequence(jArr, calcSequenceOffset);
            long j3 = calcSequenceOffset;
            long j4 = lvConsumerIndex + 1;
            if (lvSequence < j4) {
                if (lvConsumerIndex >= j2) {
                    j2 = lvProducerIndex();
                    if (lvConsumerIndex == j2) {
                        return null;
                    }
                }
                lvSequence = j4 + 1;
            }
            if (lvSequence <= j4 && casConsumerIndex(lvConsumerIndex, j4)) {
                long calcElementOffset = calcElementOffset(lvConsumerIndex, j);
                E lpElement = UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset);
                UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset, null);
                soSequence(jArr, j3, lvConsumerIndex + j + 1);
                return lpElement;
            }
        }
    }

    public E peek() {
        long lvConsumerIndex;
        E lpElement;
        do {
            lvConsumerIndex = lvConsumerIndex();
            lpElement = UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset(lvConsumerIndex));
            if (lpElement != null) {
                break;
            }
        } while (lvConsumerIndex != lvProducerIndex());
        return lpElement;
    }

    public boolean relaxedOffer(E e) {
        if (e != null) {
            long j = this.mask;
            long[] jArr = this.sequenceBuffer;
            while (true) {
                long lvProducerIndex = lvProducerIndex();
                long calcSequenceOffset = calcSequenceOffset(lvProducerIndex, j);
                long lvSequence = lvSequence(jArr, calcSequenceOffset);
                if (lvSequence < lvProducerIndex) {
                    return false;
                }
                if (lvSequence <= lvProducerIndex) {
                    long j2 = 1 + lvProducerIndex;
                    if (casProducerIndex(lvProducerIndex, j2)) {
                        UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(lvProducerIndex, j), e);
                        soSequence(jArr, calcSequenceOffset, j2);
                        return true;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    public E relaxedPoll() {
        long[] jArr = this.sequenceBuffer;
        long j = this.mask;
        while (true) {
            long lvConsumerIndex = lvConsumerIndex();
            long calcSequenceOffset = calcSequenceOffset(lvConsumerIndex, j);
            long lvSequence = lvSequence(jArr, calcSequenceOffset);
            long j2 = lvConsumerIndex + 1;
            if (lvSequence < j2) {
                return null;
            }
            if (lvSequence <= j2 && casConsumerIndex(lvConsumerIndex, j2)) {
                long calcElementOffset = calcElementOffset(lvConsumerIndex, j);
                E lpElement = UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset);
                UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset, null);
                soSequence(jArr, calcSequenceOffset, lvConsumerIndex + j + 1);
                return lpElement;
            }
        }
    }

    public E relaxedPeek() {
        return UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset(lvConsumerIndex()));
    }

    public int drain(Consumer<E> consumer) {
        int capacity = capacity();
        int i = 0;
        while (i < capacity) {
            int drain = drain(consumer, RECOMENDED_POLL_BATCH);
            if (drain == 0) {
                break;
            }
            i += drain;
        }
        return i;
    }

    public int fill(Supplier<E> supplier) {
        int capacity = capacity();
        long j = 0;
        do {
            int fill = fill(supplier, RECOMENDED_OFFER_BATCH);
            if (fill == 0) {
                return (int) j;
            }
            j += (long) fill;
        } while (j <= ((long) capacity));
        return (int) j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
        r2 = calcElementOffset(r0, r9);
        r4 = p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lpElement(r11, r2);
        p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.soElement(r11, r2, null);
        r2 = r15;
        r15 = r4;
        soSequence(r8, r2, 1 + (r0 + r9));
        r19.accept(r15);
        r12 = r12 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int drain(p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer<E> r19, int r20) {
        /*
            r18 = this;
            r6 = r18
            r7 = r20
            long[] r8 = r6.sequenceBuffer
            long r9 = r6.mask
            java.lang.Object[] r11 = r6.buffer
            r0 = 0
            r12 = 0
        L_0x000c:
            if (r12 >= r7) goto L_0x0050
        L_0x000e:
            long r0 = r18.lvConsumerIndex()
            long r2 = calcSequenceOffset(r0, r9)
            long r4 = r6.lvSequence(r8, r2)
            r13 = 1
            r15 = r2
            long r2 = r0 + r13
            int r17 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r17 >= 0) goto L_0x0024
            return r12
        L_0x0024:
            int r17 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r17 > 0) goto L_0x004d
            boolean r2 = r6.casConsumerIndex(r0, r2)
            if (r2 == 0) goto L_0x004d
            long r2 = calcElementOffset(r0, r9)
            java.lang.Object r4 = p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lpElement(r11, r2)
            r5 = 0
            p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.soElement(r11, r2, r5)
            long r0 = r0 + r9
            long r13 = r13 + r0
            r0 = r18
            r1 = r8
            r2 = r15
            r15 = r4
            r4 = r13
            r0.soSequence(r1, r2, r4)
            r0 = r19
            r0.accept(r15)
            int r12 = r12 + 1
            goto L_0x000c
        L_0x004d:
            r0 = r19
            goto L_0x000e
        L_0x0050:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue.drain(io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Consumer, int):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.soElement(r9, calcElementOffset(r0, r7), r13.get());
        soSequence(r6, r2, r4);
        r10 = r10 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int fill(p043io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier<E> r13, int r14) {
        /*
            r12 = this;
            long[] r6 = r12.sequenceBuffer
            long r7 = r12.mask
            java.lang.Object[] r9 = r12.buffer
            r0 = 0
            r10 = 0
        L_0x0008:
            if (r10 >= r14) goto L_0x003b
        L_0x000a:
            long r0 = r12.lvProducerIndex()
            long r2 = calcSequenceOffset(r0, r7)
            long r4 = r12.lvSequence(r6, r2)
            int r11 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r11 >= 0) goto L_0x001b
            return r10
        L_0x001b:
            int r11 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r11 > 0) goto L_0x000a
            r4 = 1
            long r4 = r4 + r0
            boolean r11 = r12.casProducerIndex(r0, r4)
            if (r11 == 0) goto L_0x000a
            long r0 = calcElementOffset(r0, r7)
            java.lang.Object r11 = r13.get()
            p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.soElement(r9, r0, r11)
            r0 = r12
            r1 = r6
            r0.soSequence(r1, r2, r4)
            int r10 = r10 + 1
            goto L_0x0008
        L_0x003b:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue.fill(io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Supplier, int):int");
    }

    public void drain(Consumer<E> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        while (true) {
            int i = 0;
            while (true) {
                if (!exitCondition.keepRunning()) {
                    return;
                }
                if (drain(consumer, RECOMENDED_POLL_BATCH) == 0) {
                    i = waitStrategy.idle(i);
                }
            }
        }
    }

    public void fill(Supplier<E> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition) {
        while (true) {
            int i = 0;
            while (true) {
                if (!exitCondition.keepRunning()) {
                    return;
                }
                if (fill(supplier, RECOMENDED_OFFER_BATCH) == 0) {
                    i = waitStrategy.idle(i);
                }
            }
        }
    }
}
