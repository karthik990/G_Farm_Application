package com.google.android.gms.tagmanager;

import android.util.LruCache;

final class zzdb<K, V> implements zzp<K, V> {
    private LruCache<K, V> zzahy;

    zzdb(int i, zzs<K, V> zzs) {
        this.zzahy = new zzdc(this, 1048576, zzs);
    }

    public final void zza(K k, V v) {
        this.zzahy.put(k, v);
    }

    public final V get(K k) {
        return this.zzahy.get(k);
    }
}
