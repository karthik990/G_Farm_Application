package com.rometools.utils;

public class Doubles {
    private Doubles() {
    }

    public static Double parse(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
