package com.google.android.gms.internal.firebase_remote_config;

public enum zzkq {
    DOUBLE(zzkv.DOUBLE, 1),
    FLOAT(zzkv.FLOAT, 5),
    INT64(zzkv.LONG, 0),
    UINT64(zzkv.LONG, 0),
    INT32(zzkv.INT, 0),
    FIXED64(zzkv.LONG, 1),
    FIXED32(zzkv.INT, 5),
    BOOL(zzkv.BOOLEAN, 0),
    STRING(zzkv.STRING, 2),
    GROUP(zzkv.MESSAGE, 3),
    MESSAGE(zzkv.MESSAGE, 2),
    BYTES(zzkv.BYTE_STRING, 2),
    UINT32(zzkv.INT, 0),
    ENUM(zzkv.ENUM, 0),
    SFIXED32(zzkv.INT, 5),
    SFIXED64(zzkv.LONG, 1),
    SINT32(zzkv.INT, 0),
    SINT64(zzkv.LONG, 0);
    
    private final zzkv zzzf;
    private final int zzzg;

    private zzkq(zzkv zzkv, int i) {
        this.zzzf = zzkv;
        this.zzzg = i;
    }

    public final zzkv zzjn() {
        return this.zzzf;
    }

    public final int zzjo() {
        return this.zzzg;
    }
}
