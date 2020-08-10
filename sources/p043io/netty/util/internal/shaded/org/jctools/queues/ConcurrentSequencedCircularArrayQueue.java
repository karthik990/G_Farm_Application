package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.JvmInfo;
import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.ConcurrentSequencedCircularArrayQueue */
public abstract class ConcurrentSequencedCircularArrayQueue<E> extends ConcurrentCircularArrayQueue<E> {
    private static final long ARRAY_BASE;
    private static final int ELEMENT_SHIFT = 3;
    protected static final int SEQ_BUFFER_PAD;
    protected final long[] sequenceBuffer;

    static {
        Class<long[]> cls = long[].class;
        int arrayIndexScale = UnsafeAccess.UNSAFE.arrayIndexScale(cls);
        if (8 == arrayIndexScale) {
            SEQ_BUFFER_PAD = (JvmInfo.CACHE_LINE_SIZE * 2) / arrayIndexScale;
            ARRAY_BASE = (long) (UnsafeAccess.UNSAFE.arrayBaseOffset(cls) + (SEQ_BUFFER_PAD * arrayIndexScale));
            return;
        }
        throw new IllegalStateException("Unexpected long[] element size");
    }

    public ConcurrentSequencedCircularArrayQueue(int i) {
        super(i);
        int i2 = (int) (this.mask + 1);
        this.sequenceBuffer = new long[((SEQ_BUFFER_PAD * 2) + i2)];
        for (long j = 0; j < ((long) i2); j++) {
            soSequence(this.sequenceBuffer, calcSequenceOffset(j), j);
        }
    }

    /* access modifiers changed from: protected */
    public final long calcSequenceOffset(long j) {
        return calcSequenceOffset(j, this.mask);
    }

    protected static long calcSequenceOffset(long j, long j2) {
        return ARRAY_BASE + ((j & j2) << ELEMENT_SHIFT);
    }

    /* access modifiers changed from: protected */
    public final void soSequence(long[] jArr, long j, long j2) {
        UnsafeAccess.UNSAFE.putOrderedLong(jArr, j, j2);
    }

    /* access modifiers changed from: protected */
    public final long lvSequence(long[] jArr, long j) {
        return UnsafeAccess.UNSAFE.getLongVolatile(jArr, j);
    }
}
