package p043io.opencensus.internal;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* renamed from: io.opencensus.internal.Utils */
public final class C5887Utils {
    private C5887Utils() {
    }

    public static void checkArgument(boolean z, @Nullable Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static void checkArgument(boolean z, String str, @Nullable Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(format(str, objArr));
        }
    }

    public static void checkState(boolean z, @Nullable Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkIndex(int i, int i2) {
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Negative size: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else if (i < 0 || i >= i2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Index out of bounds: size=");
            sb2.append(i2);
            sb2.append(", index=");
            sb2.append(i);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
    }

    public static <T> T checkNotNull(T t, @Nullable Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static <T> void checkListElementNotNull(List<T> list, @Nullable Object obj) {
        for (T t : list) {
            if (t == null) {
                throw new NullPointerException(String.valueOf(obj));
            }
        }
    }

    public static <K, V> void checkMapElementNotNull(Map<K, V> map, @Nullable Object obj) {
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() != null) {
                if (entry.getValue() == null) {
                }
            }
            throw new NullPointerException(String.valueOf(obj));
        }
    }

    public static boolean equalsObjects(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    private static String format(String str, @Nullable Object... objArr) {
        if (objArr == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + (objArr.length * 16));
        int i = 0;
        int i2 = 0;
        while (i < objArr.length) {
            int indexOf = str.indexOf("%s", i2);
            if (indexOf == -1) {
                break;
            }
            sb.append(str, i2, indexOf);
            int i3 = i + 1;
            sb.append(objArr[i]);
            int i4 = i3;
            i2 = indexOf + 2;
            i = i4;
        }
        sb.append(str, i2, str.length());
        if (i < objArr.length) {
            sb.append(" [");
            int i5 = i + 1;
            sb.append(objArr[i]);
            while (i5 < objArr.length) {
                sb.append(", ");
                int i6 = i5 + 1;
                sb.append(objArr[i5]);
                i5 = i6;
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
