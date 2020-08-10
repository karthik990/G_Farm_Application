package p043io.netty.util.internal.shaded.org.jctools.util;

/* renamed from: io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess */
public final class UnsafeRefArrayAccess {
    public static final long REF_ARRAY_BASE = ((long) UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class));
    public static final int REF_ELEMENT_SHIFT;

    static {
        int arrayIndexScale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
        if (4 == arrayIndexScale) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == arrayIndexScale) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
    }

    private UnsafeRefArrayAccess() {
    }

    public static <E> void spElement(E[] eArr, long j, E e) {
        UnsafeAccess.UNSAFE.putObject(eArr, j, e);
    }

    public static <E> void soElement(E[] eArr, long j, E e) {
        UnsafeAccess.UNSAFE.putOrderedObject(eArr, j, e);
    }

    public static <E> E lpElement(E[] eArr, long j) {
        return UnsafeAccess.UNSAFE.getObject(eArr, j);
    }

    public static <E> E lvElement(E[] eArr, long j) {
        return UnsafeAccess.UNSAFE.getObjectVolatile(eArr, j);
    }

    public static long calcElementOffset(long j) {
        return REF_ARRAY_BASE + (j << REF_ELEMENT_SHIFT);
    }
}
