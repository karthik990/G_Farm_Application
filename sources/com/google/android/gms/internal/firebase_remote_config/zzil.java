package com.google.android.gms.internal.firebase_remote_config;

final class zzil {
    private static final zzij zzvg = zzic();
    private static final zzij zzvh = new zzik();

    static zzij zzia() {
        return zzvg;
    }

    static zzij zzib() {
        return zzvh;
    }

    private static zzij zzic() {
        try {
            return (zzij) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
