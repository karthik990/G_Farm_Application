package com.google.android.gms.internal.gtm;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public enum zzul {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzps.zzavx),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzbbx;

    private zzul(Object obj) {
        this.zzbbx = obj;
    }
}
