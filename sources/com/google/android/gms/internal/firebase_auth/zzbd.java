package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzbd<K, V> extends zzbc<Entry<K, V>> {
    /* access modifiers changed from: private */
    public final transient int size;
    private final transient zzaz<K, V> zzhe;
    /* access modifiers changed from: private */
    public final transient Object[] zzhf;
    private final transient int zzhg = 0;

    zzbd(zzaz<K, V> zzaz, Object[] objArr, int i, int i2) {
        this.zzhe = zzaz;
        this.zzhf = objArr;
        this.size = i2;
    }

    public final zzbk<Entry<K, V>> zzbz() {
        return (zzbk) zzcd().iterator();
    }

    /* access modifiers changed from: 0000 */
    public final int zza(Object[] objArr, int i) {
        return zzcd().zza(objArr, i);
    }

    /* access modifiers changed from: 0000 */
    public final zzay<Entry<K, V>> zzci() {
        return new zzbg(this);
    }

    public final boolean contains(Object obj) {
        if (obj instanceof Entry) {
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && value.equals(this.zzhe.get(key))) {
                return true;
            }
        }
        return false;
    }

    public final int size() {
        return this.size;
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
