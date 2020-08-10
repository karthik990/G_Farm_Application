package com.google.android.youtube.player.internal;

import android.text.TextUtils;

/* renamed from: com.google.android.youtube.player.internal.ab */
public final class C2774ab {
    /* renamed from: a */
    public static <T> T m1495a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null reference");
    }

    /* renamed from: a */
    public static <T> T m1496a(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    /* renamed from: a */
    public static String m1497a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    /* renamed from: a */
    public static void m1498a(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }
}
