package com.google.android.gms.internal.firebase_auth;

final class zzge {
    private static final Class<?> zzvt = zzdg("libcore.io.Memory");
    private static final boolean zzvu = (zzdg("org.robolectric.Robolectric") != null);

    static boolean zzga() {
        return zzvt != null && !zzvu;
    }

    static Class<?> zzgb() {
        return zzvt;
    }

    private static <T> Class<T> zzdg(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
