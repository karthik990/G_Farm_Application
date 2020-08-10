package p043io.opencensus.internal;

/* renamed from: io.opencensus.internal.StringUtils */
public final class StringUtils {
    private static boolean isPrintableChar(char c) {
        return c >= ' ' && c <= '~';
    }

    public static boolean isPrintableString(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isPrintableChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private StringUtils() {
    }
}
