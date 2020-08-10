package p043io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;

/* renamed from: io.netty.handler.ssl.ApplicationProtocolUtil */
final class ApplicationProtocolUtil {
    private static final int DEFAULT_LIST_SIZE = 2;

    private ApplicationProtocolUtil() {
    }

    static List<String> toList(Iterable<String> iterable) {
        return toList(2, iterable);
    }

    static List<String> toList(int i, Iterable<String> iterable) {
        if (iterable == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(i);
        for (String str : iterable) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(str);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }

    static List<String> toList(String... strArr) {
        return toList(2, strArr);
    }

    static List<String> toList(int i, String... strArr) {
        if (strArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(i);
        for (String str : strArr) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(str);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }
}
