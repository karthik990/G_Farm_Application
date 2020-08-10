package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.Pow2;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue */
public class MpscChunkedArrayQueue<E> extends MpscChunkedArrayQueueColdProducerFields<E> {

    /* renamed from: p0 */
    long f3756p0;

    /* renamed from: p1 */
    long f3757p1;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    /* renamed from: p2 */
    long f3758p2;

    /* renamed from: p3 */
    long f3759p3;

    /* renamed from: p4 */
    long f3760p4;

    /* renamed from: p5 */
    long f3761p5;

    /* renamed from: p6 */
    long f3762p6;

    /* renamed from: p7 */
    long f3763p7;

    /* access modifiers changed from: protected */
    public long getCurrentBufferCapacity(long j) {
        return j;
    }

    public MpscChunkedArrayQueue(int i) {
        super(Math.max(2, Math.min(1024, Pow2.roundToPowerOfTwo(i / 8))), i);
    }

    public MpscChunkedArrayQueue(int i, int i2) {
        super(i, i2);
    }

    /* access modifiers changed from: protected */
    public long availableInQueue(long j, long j2) {
        return this.maxQueueCapacity - (j - j2);
    }

    public int capacity() {
        return (int) (this.maxQueueCapacity / 2);
    }

    /* access modifiers changed from: protected */
    public int getNextBufferSize(E[] eArr) {
        return eArr.length;
    }
}
