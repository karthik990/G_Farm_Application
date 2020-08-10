package com.rometools.utils;

public final class Integers {
    private Integers() {
    }

    public static Integer parse(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
