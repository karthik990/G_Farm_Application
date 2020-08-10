package p043io.netty.util.internal.shaded.org.jctools.util;

/* renamed from: io.netty.util.internal.shaded.org.jctools.util.Pow2 */
public final class Pow2 {
    public static final int MAX_POW2 = 1073741824;

    public static boolean isPowerOfTwo(int i) {
        return (i & (i + -1)) == 0;
    }

    private Pow2() {
    }

    public static int roundToPowerOfTwo(int i) {
        if (i > 1073741824) {
            StringBuilder sb = new StringBuilder();
            sb.append("There is no larger power of 2 int for value:");
            sb.append(i);
            sb.append(" since it exceeds 2^31.");
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= 0) {
            return 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Given value:");
            sb2.append(i);
            sb2.append(". Expecting value >= 0.");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static long align(long j, int i) {
        if (isPowerOfTwo(i)) {
            int i2 = i - 1;
            return (j + ((long) i2)) & ((long) (i2 ^ -1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("alignment must be a power of 2:");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }
}
