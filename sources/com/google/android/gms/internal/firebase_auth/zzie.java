package com.google.android.gms.internal.firebase_auth;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public enum zzie {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzgf.class, zzgf.class, zzgf.zzvv),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzabq;
    private final Class<?> zzabr;
    private final Object zzabs;

    private zzie(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzabq = cls;
        this.zzabr = cls2;
        this.zzabs = obj;
    }

    public final Class<?> zzjb() {
        return this.zzabr;
    }
}
