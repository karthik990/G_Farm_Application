package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzbc<E> extends zzav<E> implements Set<E> {
    @NullableDecl
    private transient zzay<E> zzhd;

    zzbc() {
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        return zzbh.zza(this, obj);
    }

    public int hashCode() {
        return zzbh.zza(this);
    }

    public zzay<E> zzcd() {
        zzay<E> zzay = this.zzhd;
        if (zzay != null) {
            return zzay;
        }
        zzay<E> zzci = zzci();
        this.zzhd = zzci;
        return zzci;
    }

    /* access modifiers changed from: 0000 */
    public zzay<E> zzci() {
        return zzay.zza(toArray());
    }

    public /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
