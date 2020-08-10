package com.google.android.gms.internal.firebase_remote_config;

final class zzfr {
    private static final Class<?> zzoh = zzbk("libcore.io.Memory");
    private static final boolean zzoi = (zzbk("org.robolectric.Robolectric") != null);

    static boolean zzet() {
        return zzoh != null && !zzoi;
    }

    static Class<?> zzeu() {
        return zzoh;
    }

    private static <T> Class<T> zzbk(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
