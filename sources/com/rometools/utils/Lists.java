package com.rometools.utils;

import java.util.ArrayList;
import java.util.List;

public final class Lists {
    private Lists() {
    }

    public static <T> List<T> createWhenNull(List<T> list) {
        return list == null ? new ArrayList() : list;
    }

    public static <T> List<T> create(T t) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(t);
        return arrayList;
    }

    public static <T> T firstEntry(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static boolean sizeIs(List<?> list, int i) {
        boolean z = true;
        if (i == 0) {
            if (list != null && !list.isEmpty()) {
                z = false;
            }
            return z;
        }
        if (list == null || list.size() != i) {
            z = false;
        }
        return z;
    }

    public static <T> List<T> emptyToNull(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list;
    }
}
