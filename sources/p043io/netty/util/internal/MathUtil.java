package p043io.netty.util.internal;

/* renamed from: io.netty.util.internal.MathUtil */
public final class MathUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i > i2 ? 1 : 0;
    }

    public static int compare(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    public static boolean isOutOfBounds(int i, int i2, int i3) {
        int i4 = i | i2;
        int i5 = i + i2;
        return ((i4 | i5) | (i3 - i5)) < 0;
    }

    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int i) {
        return 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
    }

    public static int safeFindNextPositivePowerOfTwo(int i) {
        if (i <= 0) {
            return 1;
        }
        if (i >= 1073741824) {
            return 1073741824;
        }
        return findNextPositivePowerOfTwo(i);
    }
}
