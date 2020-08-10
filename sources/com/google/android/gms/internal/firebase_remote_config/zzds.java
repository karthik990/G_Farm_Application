package com.google.android.gms.internal.firebase_remote_config;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class zzds {
    public static void checkState(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static int zza(int i, int i2, @NullableDecl String str) {
        String str2;
        if (i >= 0 && i <= i2) {
            return i;
        }
        if (i < 0) {
            str2 = zzdz.zza("%s (%s) must not be negative", str, Integer.valueOf(i));
        } else if (i2 < 0) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("negative size: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else {
            str2 = zzdz.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IndexOutOfBoundsException(str2);
    }
}
