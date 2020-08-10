package com.google.android.gms.internal.firebase_remote_config;

final class zziy {
    private static final zziw zzwb = zzij();
    private static final zziw zzwc = new zzix();

    static zziw zzih() {
        return zzwb;
    }

    static zziw zzii() {
        return zzwc;
    }

    private static zziw zzij() {
        try {
            return (zziw) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
