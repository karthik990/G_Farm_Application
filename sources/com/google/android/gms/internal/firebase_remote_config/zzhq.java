package com.google.android.gms.internal.firebase_remote_config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public enum zzhq {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzfw.class, zzfw.class, zzfw.zzop),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzuh;
    private final Class<?> zzui;
    private final Object zzuj;

    private zzhq(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzuh = cls;
        this.zzui = cls2;
        this.zzuj = obj;
    }

    public final Class<?> zzhp() {
        return this.zzui;
    }
}
