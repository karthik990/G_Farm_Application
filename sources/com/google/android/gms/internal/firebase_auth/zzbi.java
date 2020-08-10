package com.google.android.gms.internal.firebase_auth;

final class zzbi extends zzay<Object> {
    private final transient int offset;
    private final transient int size;
    private final transient Object[] zzhf;

    zzbi(Object[] objArr, int i, int i2) {
        this.zzhf = objArr;
        this.offset = i;
        this.size = i2;
    }

    public final Object get(int i) {
        zzaj.zza(i, this.size);
        return this.zzhf[(i * 2) + this.offset];
    }

    public final int size() {
        return this.size;
    }
}
