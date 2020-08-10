package com.google.android.gms.internal.firebase_remote_config;

final class zzgu {
    private static final Class<?> zzpt = zzgg();

    private static Class<?> zzgg() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzgv zzgh() {
        Class<?> cls = zzpt;
        if (cls != null) {
            try {
                return (zzgv) cls.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception unused) {
            }
        }
        return zzgv.zzpw;
    }
}
