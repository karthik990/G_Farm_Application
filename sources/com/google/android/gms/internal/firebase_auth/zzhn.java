package com.google.android.gms.internal.firebase_auth;

import java.lang.reflect.Type;

public enum zzhn {
    DOUBLE(0, zzhp.SCALAR, zzie.DOUBLE),
    FLOAT(1, zzhp.SCALAR, zzie.FLOAT),
    INT64(2, zzhp.SCALAR, zzie.LONG),
    UINT64(3, zzhp.SCALAR, zzie.LONG),
    INT32(4, zzhp.SCALAR, zzie.INT),
    FIXED64(5, zzhp.SCALAR, zzie.LONG),
    FIXED32(6, zzhp.SCALAR, zzie.INT),
    BOOL(7, zzhp.SCALAR, zzie.BOOLEAN),
    STRING(8, zzhp.SCALAR, zzie.STRING),
    MESSAGE(9, zzhp.SCALAR, zzie.MESSAGE),
    BYTES(10, zzhp.SCALAR, zzie.BYTE_STRING),
    UINT32(11, zzhp.SCALAR, zzie.INT),
    ENUM(12, zzhp.SCALAR, zzie.ENUM),
    SFIXED32(13, zzhp.SCALAR, zzie.INT),
    SFIXED64(14, zzhp.SCALAR, zzie.LONG),
    SINT32(15, zzhp.SCALAR, zzie.INT),
    SINT64(16, zzhp.SCALAR, zzie.LONG),
    GROUP(17, zzhp.SCALAR, zzie.MESSAGE),
    DOUBLE_LIST(18, zzhp.VECTOR, zzie.DOUBLE),
    FLOAT_LIST(19, zzhp.VECTOR, zzie.FLOAT),
    INT64_LIST(20, zzhp.VECTOR, zzie.LONG),
    UINT64_LIST(21, zzhp.VECTOR, zzie.LONG),
    INT32_LIST(22, zzhp.VECTOR, zzie.INT),
    FIXED64_LIST(23, zzhp.VECTOR, zzie.LONG),
    FIXED32_LIST(24, zzhp.VECTOR, zzie.INT),
    BOOL_LIST(25, zzhp.VECTOR, zzie.BOOLEAN),
    STRING_LIST(26, zzhp.VECTOR, zzie.STRING),
    MESSAGE_LIST(27, zzhp.VECTOR, zzie.MESSAGE),
    BYTES_LIST(28, zzhp.VECTOR, zzie.BYTE_STRING),
    UINT32_LIST(29, zzhp.VECTOR, zzie.INT),
    ENUM_LIST(30, zzhp.VECTOR, zzie.ENUM),
    SFIXED32_LIST(31, zzhp.VECTOR, zzie.INT),
    SFIXED64_LIST(32, zzhp.VECTOR, zzie.LONG),
    SINT32_LIST(33, zzhp.VECTOR, zzie.INT),
    SINT64_LIST(34, zzhp.VECTOR, zzie.LONG),
    DOUBLE_LIST_PACKED(35, zzhp.PACKED_VECTOR, zzie.DOUBLE),
    FLOAT_LIST_PACKED(36, zzhp.PACKED_VECTOR, zzie.FLOAT),
    INT64_LIST_PACKED(37, zzhp.PACKED_VECTOR, zzie.LONG),
    UINT64_LIST_PACKED(38, zzhp.PACKED_VECTOR, zzie.LONG),
    INT32_LIST_PACKED(39, zzhp.PACKED_VECTOR, zzie.INT),
    FIXED64_LIST_PACKED(40, zzhp.PACKED_VECTOR, zzie.LONG),
    FIXED32_LIST_PACKED(41, zzhp.PACKED_VECTOR, zzie.INT),
    BOOL_LIST_PACKED(42, zzhp.PACKED_VECTOR, zzie.BOOLEAN),
    UINT32_LIST_PACKED(43, zzhp.PACKED_VECTOR, zzie.INT),
    ENUM_LIST_PACKED(44, zzhp.PACKED_VECTOR, zzie.ENUM),
    SFIXED32_LIST_PACKED(45, zzhp.PACKED_VECTOR, zzie.INT),
    SFIXED64_LIST_PACKED(46, zzhp.PACKED_VECTOR, zzie.LONG),
    SINT32_LIST_PACKED(47, zzhp.PACKED_VECTOR, zzie.INT),
    SINT64_LIST_PACKED(48, zzhp.PACKED_VECTOR, zzie.LONG),
    GROUP_LIST(49, zzhp.VECTOR, zzie.MESSAGE),
    MAP(50, zzhp.MAP, zzie.VOID);
    
    private static final zzhn[] zzzt = null;
    private static final Type[] zzzu = null;

    /* renamed from: id */
    private final int f1539id;
    private final zzie zzzp;
    private final zzhp zzzq;
    private final Class<?> zzzr;
    private final boolean zzzs;

    private zzhn(int i, zzhp zzhp, zzie zzie) {
        this.f1539id = i;
        this.zzzq = zzhp;
        this.zzzp = zzie;
        int i2 = zzhm.zzxo[zzhp.ordinal()];
        if (i2 == 1) {
            this.zzzr = zzie.zzjb();
        } else if (i2 != 2) {
            this.zzzr = null;
        } else {
            this.zzzr = zzie.zzjb();
        }
        boolean z = false;
        if (zzhp == zzhp.SCALAR) {
            int i3 = zzhm.zzxp[zzie.ordinal()];
            if (!(i3 == 1 || i3 == 2 || i3 == 3)) {
                z = true;
            }
        }
        this.zzzs = z;
    }

    /* renamed from: id */
    public final int mo30255id() {
        return this.f1539id;
    }

    static {
        int i;
        zzzu = new Type[0];
        zzhn[] values = values();
        zzzt = new zzhn[values.length];
        for (zzhn zzhn : values) {
            zzzt[zzhn.f1539id] = zzhn;
        }
    }
}
