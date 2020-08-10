package org.junit;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

public class Assert {
    protected Assert() {
    }

    public static void assertTrue(String str, boolean z) {
        if (!z) {
            fail(str);
        }
    }

    public static void assertTrue(boolean z) {
        assertTrue(null, z);
    }

    public static void assertFalse(String str, boolean z) {
        assertTrue(str, !z);
    }

    public static void assertFalse(boolean z) {
        assertFalse(null, z);
    }

    public static void fail(String str) {
        if (str == null) {
            throw new AssertionError();
        }
        throw new AssertionError(str);
    }

    public static void fail() {
        fail(null);
    }

    public static void assertEquals(String str, Object obj, Object obj2) {
        if (!equalsRegardingNull(obj, obj2)) {
            if (!(obj instanceof String) || !(obj2 instanceof String)) {
                failNotEquals(str, obj, obj2);
                return;
            }
            if (str == null) {
                str = "";
            }
            throw new ComparisonFailure(str, (String) obj, (String) obj2);
        }
    }

    private static boolean equalsRegardingNull(Object obj, Object obj2) {
        if (obj != null) {
            return isEquals(obj, obj2);
        }
        return obj2 == null;
    }

    private static boolean isEquals(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    public static void assertEquals(Object obj, Object obj2) {
        assertEquals((String) null, obj, obj2);
    }

    public static void assertNotEquals(String str, Object obj, Object obj2) {
        if (equalsRegardingNull(obj, obj2)) {
            failEquals(str, obj2);
        }
    }

    public static void assertNotEquals(Object obj, Object obj2) {
        assertNotEquals((String) null, obj, obj2);
    }

    private static void failEquals(String str, Object obj) {
        String str2;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(". ");
            str2 = sb.toString();
        } else {
            str2 = "Values should be different. ";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append("Actual: ");
        sb2.append(obj);
        fail(sb2.toString());
    }

    public static void assertNotEquals(String str, long j, long j2) {
        if (j == j2) {
            failEquals(str, Long.valueOf(j2));
        }
    }

    public static void assertNotEquals(long j, long j2) {
        assertNotEquals((String) null, j, j2);
    }

    public static void assertNotEquals(String str, double d, double d2, double d3) {
        if (!doubleIsDifferent(d, d2, d3)) {
            failEquals(str, Double.valueOf(d2));
        }
    }

    public static void assertNotEquals(double d, double d2, double d3) {
        assertNotEquals((String) null, d, d2, d3);
    }

    public static void assertNotEquals(float f, float f2, float f3) {
        assertNotEquals((String) null, f, f2, f3);
    }

    public static void assertArrayEquals(String str, Object[] objArr, Object[] objArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, objArr, objArr2);
    }

    public static void assertArrayEquals(Object[] objArr, Object[] objArr2) {
        assertArrayEquals((String) null, objArr, objArr2);
    }

    public static void assertArrayEquals(String str, boolean[] zArr, boolean[] zArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, zArr, zArr2);
    }

    public static void assertArrayEquals(boolean[] zArr, boolean[] zArr2) {
        assertArrayEquals((String) null, zArr, zArr2);
    }

    public static void assertArrayEquals(String str, byte[] bArr, byte[] bArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, bArr, bArr2);
    }

    public static void assertArrayEquals(byte[] bArr, byte[] bArr2) {
        assertArrayEquals((String) null, bArr, bArr2);
    }

    public static void assertArrayEquals(String str, char[] cArr, char[] cArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, cArr, cArr2);
    }

    public static void assertArrayEquals(char[] cArr, char[] cArr2) {
        assertArrayEquals((String) null, cArr, cArr2);
    }

    public static void assertArrayEquals(String str, short[] sArr, short[] sArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, sArr, sArr2);
    }

    public static void assertArrayEquals(short[] sArr, short[] sArr2) {
        assertArrayEquals((String) null, sArr, sArr2);
    }

    public static void assertArrayEquals(String str, int[] iArr, int[] iArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, iArr, iArr2);
    }

    public static void assertArrayEquals(int[] iArr, int[] iArr2) {
        assertArrayEquals((String) null, iArr, iArr2);
    }

    public static void assertArrayEquals(String str, long[] jArr, long[] jArr2) throws ArrayComparisonFailure {
        internalArrayEquals(str, jArr, jArr2);
    }

    public static void assertArrayEquals(long[] jArr, long[] jArr2) {
        assertArrayEquals((String) null, jArr, jArr2);
    }

    public static void assertArrayEquals(String str, double[] dArr, double[] dArr2, double d) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(d).arrayEquals(str, dArr, dArr2);
    }

    public static void assertArrayEquals(double[] dArr, double[] dArr2, double d) {
        assertArrayEquals((String) null, dArr, dArr2, d);
    }

    public static void assertArrayEquals(String str, float[] fArr, float[] fArr2, float f) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(f).arrayEquals(str, fArr, fArr2);
    }

    public static void assertArrayEquals(float[] fArr, float[] fArr2, float f) {
        assertArrayEquals((String) null, fArr, fArr2, f);
    }

    private static void internalArrayEquals(String str, Object obj, Object obj2) throws ArrayComparisonFailure {
        new ExactComparisonCriteria().arrayEquals(str, obj, obj2);
    }

    public static void assertEquals(String str, double d, double d2, double d3) {
        if (doubleIsDifferent(d, d2, d3)) {
            failNotEquals(str, Double.valueOf(d), Double.valueOf(d2));
        }
    }

    public static void assertEquals(String str, float f, float f2, float f3) {
        if (floatIsDifferent(f, f2, f3)) {
            failNotEquals(str, Float.valueOf(f), Float.valueOf(f2));
        }
    }

    public static void assertNotEquals(String str, float f, float f2, float f3) {
        if (!floatIsDifferent(f, f2, f3)) {
            failEquals(str, Float.valueOf(f2));
        }
    }

    private static boolean doubleIsDifferent(double d, double d2, double d3) {
        if (Double.compare(d, d2) != 0 && Math.abs(d - d2) > d3) {
            return true;
        }
        return false;
    }

    private static boolean floatIsDifferent(float f, float f2, float f3) {
        if (Float.compare(f, f2) != 0 && Math.abs(f - f2) > f3) {
            return true;
        }
        return false;
    }

    public static void assertEquals(long j, long j2) {
        assertEquals((String) null, j, j2);
    }

    public static void assertEquals(String str, long j, long j2) {
        if (j != j2) {
            failNotEquals(str, Long.valueOf(j), Long.valueOf(j2));
        }
    }

    @Deprecated
    public static void assertEquals(double d, double d2) {
        assertEquals((String) null, d, d2);
    }

    @Deprecated
    public static void assertEquals(String str, double d, double d2) {
        fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
    }

    public static void assertEquals(double d, double d2, double d3) {
        assertEquals((String) null, d, d2, d3);
    }

    public static void assertEquals(float f, float f2, float f3) {
        assertEquals((String) null, f, f2, f3);
    }

    public static void assertNotNull(String str, Object obj) {
        assertTrue(str, obj != null);
    }

    public static void assertNotNull(Object obj) {
        assertNotNull(null, obj);
    }

    public static void assertNull(String str, Object obj) {
        if (obj != null) {
            failNotNull(str, obj);
        }
    }

    public static void assertNull(Object obj) {
        assertNull(null, obj);
    }

    private static void failNotNull(String str, Object obj) {
        String str2;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ");
            str2 = sb.toString();
        } else {
            str2 = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append("expected null, but was:<");
        sb2.append(obj);
        sb2.append(">");
        fail(sb2.toString());
    }

    public static void assertSame(String str, Object obj, Object obj2) {
        if (obj != obj2) {
            failNotSame(str, obj, obj2);
        }
    }

    public static void assertSame(Object obj, Object obj2) {
        assertSame(null, obj, obj2);
    }

    public static void assertNotSame(String str, Object obj, Object obj2) {
        if (obj == obj2) {
            failSame(str);
        }
    }

    public static void assertNotSame(Object obj, Object obj2) {
        assertNotSame(null, obj, obj2);
    }

    private static void failSame(String str) {
        String str2;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ");
            str2 = sb.toString();
        } else {
            str2 = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append("expected not same");
        fail(sb2.toString());
    }

    private static void failNotSame(String str, Object obj, Object obj2) {
        String str2;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ");
            str2 = sb.toString();
        } else {
            str2 = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append("expected same:<");
        sb2.append(obj);
        sb2.append("> was not:<");
        sb2.append(obj2);
        sb2.append(">");
        fail(sb2.toString());
    }

    private static void failNotEquals(String str, Object obj, Object obj2) {
        fail(format(str, obj, obj2));
    }

    static String format(String str, Object obj, Object obj2) {
        String str2 = "";
        if (str != null && !str.equals(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ");
            str2 = sb.toString();
        }
        String valueOf = String.valueOf(obj);
        String valueOf2 = String.valueOf(obj2);
        if (valueOf.equals(valueOf2)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append("expected: ");
            sb2.append(formatClassAndValue(obj, valueOf));
            sb2.append(" but was: ");
            sb2.append(formatClassAndValue(obj2, valueOf2));
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append("expected:<");
        sb3.append(valueOf);
        sb3.append("> but was:<");
        sb3.append(valueOf2);
        sb3.append(">");
        return sb3.toString();
    }

    private static String formatClassAndValue(Object obj, String str) {
        String name = obj == null ? "null" : obj.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("<");
        sb.append(str);
        sb.append(">");
        return sb.toString();
    }

    @Deprecated
    public static void assertEquals(String str, Object[] objArr, Object[] objArr2) {
        assertArrayEquals(str, objArr, objArr2);
    }

    @Deprecated
    public static void assertEquals(Object[] objArr, Object[] objArr2) {
        assertArrayEquals(objArr, objArr2);
    }

    public static <T> void assertThat(T t, Matcher<? super T> matcher) {
        assertThat("", t, matcher);
    }

    public static <T> void assertThat(String str, T t, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(str, t, matcher);
    }
}
