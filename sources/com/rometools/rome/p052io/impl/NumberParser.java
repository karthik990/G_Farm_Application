package com.rometools.rome.p052io.impl;

/* renamed from: com.rometools.rome.io.impl.NumberParser */
public final class NumberParser {
    private NumberParser() {
    }

    public static Long parseLong(String str) {
        if (str != null) {
            try {
                return new Long(Long.parseLong(str.trim()));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Integer parseInt(String str) {
        if (str != null) {
            try {
                return new Integer(Integer.parseInt(str.trim()));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Float parseFloat(String str) {
        if (str != null) {
            try {
                return new Float(Float.parseFloat(str.trim()));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static float parseFloat(String str, float f) {
        Float parseFloat = parseFloat(str);
        if (parseFloat == null) {
            return f;
        }
        return parseFloat.floatValue();
    }

    public static long parseLong(String str, long j) {
        Long parseLong = parseLong(str);
        if (parseLong == null) {
            return j;
        }
        return parseLong.longValue();
    }
}
