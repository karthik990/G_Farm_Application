package com.google.android.gms.internal.firebase_auth;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public enum zzle {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzgf.zzvv),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzabs;

    private zzle(Object obj) {
        this.zzabs = obj;
    }
}
