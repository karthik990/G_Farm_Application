package com.rometools.utils;

public final class Longs {
    private Longs() {
    }

    public static Long parseDecimal(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Long.valueOf((long) Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
