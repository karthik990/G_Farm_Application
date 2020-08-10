package com.rometools.utils;

public final class Alternatives {
    private Alternatives() {
    }

    public static <T> T firstNotNull(T... tArr) {
        for (T t : tArr) {
            if (t != null) {
                return t;
            }
        }
        return null;
    }
}
