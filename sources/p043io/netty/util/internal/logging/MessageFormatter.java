package p043io.netty.util.internal.logging;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/* renamed from: io.netty.util.internal.logging.MessageFormatter */
final class MessageFormatter {
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    static FormattingTuple format(String str, Object obj) {
        return arrayFormat(str, new Object[]{obj});
    }

    static FormattingTuple format(String str, Object obj, Object obj2) {
        return arrayFormat(str, new Object[]{obj, obj2});
    }

    static FormattingTuple arrayFormat(String str, Object[] objArr) {
        Throwable th = null;
        if (objArr == null || objArr.length == 0) {
            return new FormattingTuple(str, null);
        }
        int length = objArr.length - 1;
        Throwable th2 = objArr[length];
        Throwable th3 = th2 instanceof Throwable ? th2 : null;
        if (str == null) {
            return new FormattingTuple(null, th3);
        }
        String str2 = DELIM_STR;
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return new FormattingTuple(str, th3);
        }
        StringBuilder sb = new StringBuilder(str.length() + 50);
        int i = 0;
        int i2 = 0;
        do {
            boolean z = indexOf == 0 || str.charAt(indexOf + -1) != '\\';
            if (z) {
                sb.append(str, i, indexOf);
            } else {
                sb.append(str, i, indexOf - 1);
                z = indexOf >= 2 && str.charAt(indexOf + -2) == '\\';
            }
            i = indexOf + 2;
            if (z) {
                deeplyAppendParameter(sb, objArr[i2], null);
                i2++;
                if (i2 > length) {
                    break;
                }
            } else {
                sb.append(str2);
            }
            indexOf = str.indexOf(str2, i);
        } while (indexOf != -1);
        sb.append(str, i, str.length());
        String sb2 = sb.toString();
        if (i2 <= length) {
            th = th3;
        }
        return new FormattingTuple(sb2, th);
    }

    private static void deeplyAppendParameter(StringBuilder sb, Object obj, Set<Object[]> set) {
        if (obj == null) {
            sb.append("null");
            return;
        }
        Class<Float> cls = obj.getClass();
        if (cls.isArray()) {
            sb.append('[');
            if (cls == boolean[].class) {
                booleanArrayAppend(sb, (boolean[]) obj);
            } else if (cls == byte[].class) {
                byteArrayAppend(sb, (byte[]) obj);
            } else if (cls == char[].class) {
                charArrayAppend(sb, (char[]) obj);
            } else if (cls == short[].class) {
                shortArrayAppend(sb, (short[]) obj);
            } else if (cls == int[].class) {
                intArrayAppend(sb, (int[]) obj);
            } else if (cls == long[].class) {
                longArrayAppend(sb, (long[]) obj);
            } else if (cls == float[].class) {
                floatArrayAppend(sb, (float[]) obj);
            } else if (cls == double[].class) {
                doubleArrayAppend(sb, (double[]) obj);
            } else {
                objectArrayAppend(sb, (Object[]) obj, set);
            }
            sb.append(']');
        } else if (!Number.class.isAssignableFrom(cls)) {
            safeObjectAppend(sb, obj);
        } else if (cls == Long.class) {
            sb.append(((Long) obj).longValue());
        } else if (cls == Integer.class || cls == Short.class || cls == Byte.class) {
            sb.append(((Number) obj).intValue());
        } else if (cls == Double.class) {
            sb.append(((Double) obj).doubleValue());
        } else if (cls == Float.class) {
            sb.append(((Float) obj).floatValue());
        } else {
            safeObjectAppend(sb, obj);
        }
    }

    private static void safeObjectAppend(StringBuilder sb, Object obj) {
        try {
            sb.append(obj.toString());
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("SLF4J: Failed toString() invocation on an object of type [");
            sb2.append(obj.getClass().getName());
            sb2.append(']');
            printStream.println(sb2.toString());
            th.printStackTrace();
            sb.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder sb, Object[] objArr, Set<Object[]> set) {
        if (objArr.length != 0) {
            if (set == null) {
                set = new HashSet<>(objArr.length);
            }
            if (set.add(objArr)) {
                deeplyAppendParameter(sb, objArr[0], set);
                for (int i = 1; i < objArr.length; i++) {
                    sb.append(", ");
                    deeplyAppendParameter(sb, objArr[i], set);
                }
                set.remove(objArr);
            } else {
                sb.append("...");
            }
        }
    }

    private static void booleanArrayAppend(StringBuilder sb, boolean[] zArr) {
        if (zArr.length != 0) {
            sb.append(zArr[0]);
            for (int i = 1; i < zArr.length; i++) {
                sb.append(", ");
                sb.append(zArr[i]);
            }
        }
    }

    private static void byteArrayAppend(StringBuilder sb, byte[] bArr) {
        if (bArr.length != 0) {
            sb.append(bArr[0]);
            for (int i = 1; i < bArr.length; i++) {
                sb.append(", ");
                sb.append(bArr[i]);
            }
        }
    }

    private static void charArrayAppend(StringBuilder sb, char[] cArr) {
        if (cArr.length != 0) {
            sb.append(cArr[0]);
            for (int i = 1; i < cArr.length; i++) {
                sb.append(", ");
                sb.append(cArr[i]);
            }
        }
    }

    private static void shortArrayAppend(StringBuilder sb, short[] sArr) {
        if (sArr.length != 0) {
            sb.append(sArr[0]);
            for (int i = 1; i < sArr.length; i++) {
                sb.append(", ");
                sb.append(sArr[i]);
            }
        }
    }

    private static void intArrayAppend(StringBuilder sb, int[] iArr) {
        if (iArr.length != 0) {
            sb.append(iArr[0]);
            for (int i = 1; i < iArr.length; i++) {
                sb.append(", ");
                sb.append(iArr[i]);
            }
        }
    }

    private static void longArrayAppend(StringBuilder sb, long[] jArr) {
        if (jArr.length != 0) {
            sb.append(jArr[0]);
            for (int i = 1; i < jArr.length; i++) {
                sb.append(", ");
                sb.append(jArr[i]);
            }
        }
    }

    private static void floatArrayAppend(StringBuilder sb, float[] fArr) {
        if (fArr.length != 0) {
            sb.append(fArr[0]);
            for (int i = 1; i < fArr.length; i++) {
                sb.append(", ");
                sb.append(fArr[i]);
            }
        }
    }

    private static void doubleArrayAppend(StringBuilder sb, double[] dArr) {
        if (dArr.length != 0) {
            sb.append(dArr[0]);
            for (int i = 1; i < dArr.length; i++) {
                sb.append(", ");
                sb.append(dArr[i]);
            }
        }
    }

    private MessageFormatter() {
    }
}
