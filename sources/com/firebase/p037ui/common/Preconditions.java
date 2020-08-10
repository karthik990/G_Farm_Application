package com.firebase.p037ui.common;

/* renamed from: com.firebase.ui.common.Preconditions */
public final class Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("Argument cannot be null.");
    }

    public static void assertNull(Object obj, String str) {
        if (obj != null) {
            throw new RuntimeException(str);
        }
    }

    public static void assertNonNull(Object obj, String str) {
        if (obj == null) {
            throw new RuntimeException(str);
        }
    }
}
