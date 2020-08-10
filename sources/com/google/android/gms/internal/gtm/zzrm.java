package com.google.android.gms.internal.gtm;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public enum zzrm {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzps.class, zzps.class, zzps.zzavx),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzbbv;
    private final Class<?> zzbbw;
    private final Object zzbbx;

    private zzrm(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzbbv = cls;
        this.zzbbw = cls2;
        this.zzbbx = obj;
    }

    public final Class<?> zzpx() {
        return this.zzbbw;
    }
}
