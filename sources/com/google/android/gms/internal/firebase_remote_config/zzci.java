package com.google.android.gms.internal.firebase_remote_config;

public final class zzci {
    public static void checkArgument(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(zzdz.zza(str, objArr));
        }
    }

    public static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }
}
