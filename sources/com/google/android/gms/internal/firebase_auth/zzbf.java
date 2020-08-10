package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class zzbf<K> extends zzbc<K> {
    private final transient zzay<K> zzgu;
    private final transient zzaz<K, ?> zzhe;

    zzbf(zzaz<K, ?> zzaz, zzay<K> zzay) {
        this.zzhe = zzaz;
        this.zzgu = zzay;
    }

    public final zzbk<K> zzbz() {
        return (zzbk) zzcd().iterator();
    }

    /* access modifiers changed from: 0000 */
    public final int zza(Object[] objArr, int i) {
        return zzcd().zza(objArr, i);
    }

    public final zzay<K> zzcd() {
        return this.zzgu;
    }

    public final boolean contains(@NullableDecl Object obj) {
        return this.zzhe.get(obj) != null;
    }

    public final int size() {
        return this.zzhe.size();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
