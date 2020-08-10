package com.rometools.utils;

import java.util.Locale;

public final class Strings {
    public static boolean isNull(String str) {
        return str == null;
    }

    private Strings() {
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return isEmpty(str) || str.trim().isEmpty();
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String trimToNull(String str) {
        String trim = trim(str);
        if (trim == null || trim.isEmpty()) {
            return null;
        }
        return trim;
    }

    public static String trimToEmpty(String str) {
        String trim = trim(str);
        return (trim == null || trim.isEmpty()) ? "" : trim;
    }

    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(Locale.ENGLISH);
    }
}
