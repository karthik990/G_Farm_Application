package com.google.android.gms.internal.firebase_auth;

public enum zzlb {
    DOUBLE(zzle.DOUBLE, 1),
    FLOAT(zzle.FLOAT, 5),
    INT64(zzle.LONG, 0),
    UINT64(zzle.LONG, 0),
    INT32(zzle.INT, 0),
    FIXED64(zzle.LONG, 1),
    FIXED32(zzle.INT, 5),
    BOOL(zzle.BOOLEAN, 0),
    STRING(zzle.STRING, 2),
    GROUP(zzle.MESSAGE, 3),
    MESSAGE(zzle.MESSAGE, 2),
    BYTES(zzle.BYTE_STRING, 2),
    UINT32(zzle.INT, 0),
    ENUM(zzle.ENUM, 0),
    SFIXED32(zzle.INT, 5),
    SFIXED64(zzle.LONG, 1),
    SINT32(zzle.INT, 0),
    SINT64(zzle.LONG, 0);
    
    private final zzle zzagg;
    private final int zzagh;

    private zzlb(zzle zzle, int i) {
        this.zzagg = zzle;
        this.zzagh = i;
    }

    public final zzle zzkx() {
        return this.zzagg;
    }

    public final int zzky() {
        return this.zzagh;
    }
}
