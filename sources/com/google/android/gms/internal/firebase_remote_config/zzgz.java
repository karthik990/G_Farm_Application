package com.google.android.gms.internal.firebase_remote_config;

final class zzgz {
    private static final zzgx<?> zzpy = new zzgy();
    private static final zzgx<?> zzpz = zzgk();

    private static zzgx<?> zzgk() {
        try {
            return (zzgx) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zzgx<?> zzgl() {
        return zzpy;
    }

    static zzgx<?> zzgm() {
        zzgx<?> zzgx = zzpz;
        if (zzgx != null) {
            return zzgx;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
