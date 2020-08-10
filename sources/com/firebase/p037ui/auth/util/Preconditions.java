package com.firebase.p037ui.auth.util;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import com.firebase.p037ui.auth.AuthUI;

/* renamed from: com.firebase.ui.auth.util.Preconditions */
public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T t, String str, Object... objArr) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.format(str, objArr));
    }

    public static int checkValidStyle(Context context, int i, String str, Object... objArr) {
        try {
            if (TtmlNode.TAG_STYLE.equals(context.getResources().getResourceTypeName(i))) {
                return i;
            }
            throw new IllegalArgumentException(String.format(str, objArr));
        } catch (NotFoundException unused) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static void checkUnset(Bundle bundle, String str, String... strArr) {
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            if (!bundle.containsKey(strArr[i])) {
                i++;
            } else {
                throw new IllegalStateException(str);
            }
        }
    }

    public static void checkConfigured(Context context, String str, int... iArr) {
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            if (!context.getString(iArr[i]).equals(AuthUI.UNCONFIGURED_CONFIG_VALUE)) {
                i++;
            } else {
                throw new IllegalStateException(str);
            }
        }
    }

    public static void checkArgument(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }
}
