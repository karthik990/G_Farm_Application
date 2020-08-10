package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.CircularArrayOffsetCalculator */
public final class CircularArrayOffsetCalculator {
    private CircularArrayOffsetCalculator() {
    }

    public static <E> E[] allocate(int i) {
        return (Object[]) new Object[i];
    }

    public static long calcElementOffset(long j, long j2) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((j & j2) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
    }
}
