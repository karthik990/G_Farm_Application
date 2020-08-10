package com.flurry.sdk;

import android.text.TextUtils;

/* renamed from: com.flurry.sdk.fg */
public final class C1778fg {
    /* renamed from: a */
    public static boolean m949a(String... strArr) {
        for (int i = 0; i < 4; i++) {
            if (TextUtils.isEmpty(strArr[i])) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    public static boolean m948a(Object... objArr) {
        for (int i = 0; i < 2; i++) {
            if (objArr[i] == null) {
                return false;
            }
        }
        return true;
    }
}
