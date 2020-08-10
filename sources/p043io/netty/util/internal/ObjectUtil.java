package p043io.netty.util.internal;

/* renamed from: io.netty.util.internal.ObjectUtil */
public final class ObjectUtil {
    private ObjectUtil() {
    }

    public static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static int checkPositive(int i, String str) {
        if (i > 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(i);
        sb.append(" (expected: > 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public static long checkPositive(long j, String str) {
        if (j > 0) {
            return j;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(j);
        sb.append(" (expected: > 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public static int checkPositiveOrZero(int i, String str) {
        if (i >= 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(i);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public static long checkPositiveOrZero(long j, String str) {
        if (j >= 0) {
            return j;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(j);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> T[] checkNonEmpty(T[] tArr, String str) {
        checkNotNull(tArr, str);
        int length = tArr.length;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".length");
        checkPositive(length, sb.toString());
        return tArr;
    }

    public static int intValue(Integer num, int i) {
        return num != null ? num.intValue() : i;
    }

    public static long longValue(Long l, long j) {
        return l != null ? l.longValue() : j;
    }
}
