package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzjv implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzaea;
    private final /* synthetic */ zzjt zzaeb;

    private zzjv(zzjt zzjt) {
        this.zzaeb = zzjt;
        this.pos = this.zzaeb.zzadr.size();
    }

    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzaeb.zzadr.size()) || zzki().hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Entry<K, V>> zzki() {
        if (this.zzaea == null) {
            this.zzaea = this.zzaeb.zzadu.entrySet().iterator();
        }
        return this.zzaea;
    }

    public final /* synthetic */ Object next() {
        if (zzki().hasNext()) {
            return (Entry) zzki().next();
        }
        List zzb = this.zzaeb.zzadr;
        int i = this.pos - 1;
        this.pos = i;
        return (Entry) zzb.get(i);
    }

    /* synthetic */ zzjv(zzjt zzjt, zzjw zzjw) {
        this(zzjt);
    }
}
