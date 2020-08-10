package com.google.android.gms.internal.firebase_remote_config;

final class zzgw {
    private final int number;
    private final Object zzff;

    zzgw(Object obj, int i) {
        this.zzff = obj;
        this.number = i;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zzff) * 65535) + this.number;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzgw)) {
            return false;
        }
        zzgw zzgw = (zzgw) obj;
        if (this.zzff == zzgw.zzff && this.number == zzgw.number) {
            return true;
        }
        return false;
    }
}
