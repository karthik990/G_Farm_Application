package com.google.android.gms.internal.firebase_auth;

final class zzix {
    private static final zziv zzacp = zzjn();
    private static final zziv zzacq = new zziy();

    static zziv zzjl() {
        return zzacp;
    }

    static zziv zzjm() {
        return zzacq;
    }

    private static zziv zzjn() {
        try {
            return (zziv) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
