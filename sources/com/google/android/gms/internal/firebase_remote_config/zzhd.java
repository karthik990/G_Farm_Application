package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.Type;

public enum zzhd {
    DOUBLE(0, zzhf.SCALAR, zzhq.DOUBLE),
    FLOAT(1, zzhf.SCALAR, zzhq.FLOAT),
    INT64(2, zzhf.SCALAR, zzhq.LONG),
    UINT64(3, zzhf.SCALAR, zzhq.LONG),
    INT32(4, zzhf.SCALAR, zzhq.INT),
    FIXED64(5, zzhf.SCALAR, zzhq.LONG),
    FIXED32(6, zzhf.SCALAR, zzhq.INT),
    BOOL(7, zzhf.SCALAR, zzhq.BOOLEAN),
    STRING(8, zzhf.SCALAR, zzhq.STRING),
    MESSAGE(9, zzhf.SCALAR, zzhq.MESSAGE),
    BYTES(10, zzhf.SCALAR, zzhq.BYTE_STRING),
    UINT32(11, zzhf.SCALAR, zzhq.INT),
    ENUM(12, zzhf.SCALAR, zzhq.ENUM),
    SFIXED32(13, zzhf.SCALAR, zzhq.INT),
    SFIXED64(14, zzhf.SCALAR, zzhq.LONG),
    SINT32(15, zzhf.SCALAR, zzhq.INT),
    SINT64(16, zzhf.SCALAR, zzhq.LONG),
    GROUP(17, zzhf.SCALAR, zzhq.MESSAGE),
    DOUBLE_LIST(18, zzhf.VECTOR, zzhq.DOUBLE),
    FLOAT_LIST(19, zzhf.VECTOR, zzhq.FLOAT),
    INT64_LIST(20, zzhf.VECTOR, zzhq.LONG),
    UINT64_LIST(21, zzhf.VECTOR, zzhq.LONG),
    INT32_LIST(22, zzhf.VECTOR, zzhq.INT),
    FIXED64_LIST(23, zzhf.VECTOR, zzhq.LONG),
    FIXED32_LIST(24, zzhf.VECTOR, zzhq.INT),
    BOOL_LIST(25, zzhf.VECTOR, zzhq.BOOLEAN),
    STRING_LIST(26, zzhf.VECTOR, zzhq.STRING),
    MESSAGE_LIST(27, zzhf.VECTOR, zzhq.MESSAGE),
    BYTES_LIST(28, zzhf.VECTOR, zzhq.BYTE_STRING),
    UINT32_LIST(29, zzhf.VECTOR, zzhq.INT),
    ENUM_LIST(30, zzhf.VECTOR, zzhq.ENUM),
    SFIXED32_LIST(31, zzhf.VECTOR, zzhq.INT),
    SFIXED64_LIST(32, zzhf.VECTOR, zzhq.LONG),
    SINT32_LIST(33, zzhf.VECTOR, zzhq.INT),
    SINT64_LIST(34, zzhf.VECTOR, zzhq.LONG),
    DOUBLE_LIST_PACKED(35, zzhf.PACKED_VECTOR, zzhq.DOUBLE),
    FLOAT_LIST_PACKED(36, zzhf.PACKED_VECTOR, zzhq.FLOAT),
    INT64_LIST_PACKED(37, zzhf.PACKED_VECTOR, zzhq.LONG),
    UINT64_LIST_PACKED(38, zzhf.PACKED_VECTOR, zzhq.LONG),
    INT32_LIST_PACKED(39, zzhf.PACKED_VECTOR, zzhq.INT),
    FIXED64_LIST_PACKED(40, zzhf.PACKED_VECTOR, zzhq.LONG),
    FIXED32_LIST_PACKED(41, zzhf.PACKED_VECTOR, zzhq.INT),
    BOOL_LIST_PACKED(42, zzhf.PACKED_VECTOR, zzhq.BOOLEAN),
    UINT32_LIST_PACKED(43, zzhf.PACKED_VECTOR, zzhq.INT),
    ENUM_LIST_PACKED(44, zzhf.PACKED_VECTOR, zzhq.ENUM),
    SFIXED32_LIST_PACKED(45, zzhf.PACKED_VECTOR, zzhq.INT),
    SFIXED64_LIST_PACKED(46, zzhf.PACKED_VECTOR, zzhq.LONG),
    SINT32_LIST_PACKED(47, zzhf.PACKED_VECTOR, zzhq.INT),
    SINT64_LIST_PACKED(48, zzhf.PACKED_VECTOR, zzhq.LONG),
    GROUP_LIST(49, zzhf.VECTOR, zzhq.MESSAGE),
    MAP(50, zzhf.MAP, zzhq.VOID);
    
    private static final zzhd[] zzsi = null;
    private static final Type[] zzsj = null;

    /* renamed from: id */
    private final int f1541id;
    private final zzhq zzse;
    private final zzhf zzsf;
    private final Class<?> zzsg;
    private final boolean zzsh;

    private zzhd(int i, zzhf zzhf, zzhq zzhq) {
        this.f1541id = i;
        this.zzsf = zzhf;
        this.zzse = zzhq;
        int i2 = zzhe.zzsl[zzhf.ordinal()];
        if (i2 == 1) {
            this.zzsg = zzhq.zzhp();
        } else if (i2 != 2) {
            this.zzsg = null;
        } else {
            this.zzsg = zzhq.zzhp();
        }
        boolean z = false;
        if (zzhf == zzhf.SCALAR) {
            int i3 = zzhe.zzsm[zzhq.ordinal()];
            if (!(i3 == 1 || i3 == 2 || i3 == 3)) {
                z = true;
            }
        }
        this.zzsh = z;
    }

    /* renamed from: id */
    public final int mo31239id() {
        return this.f1541id;
    }

    static {
        int i;
        zzsj = new Type[0];
        zzhd[] values = values();
        zzsi = new zzhd[values.length];
        for (zzhd zzhd : values) {
            zzsi[zzhd.f1541id] = zzhd;
        }
    }
}
