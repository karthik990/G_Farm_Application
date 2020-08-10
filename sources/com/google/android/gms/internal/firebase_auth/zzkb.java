package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzkb implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzaea;
    private final /* synthetic */ zzjt zzaeb;
    private boolean zzaee;

    private zzkb(zzjt zzjt) {
        this.zzaeb = zzjt;
        this.pos = -1;
    }

    public final boolean hasNext() {
        if (this.pos + 1 < this.zzaeb.zzadr.size() || (!this.zzaeb.zzads.isEmpty() && zzki().hasNext())) {
            return true;
        }
        return false;
    }

    public final void remove() {
        if (this.zzaee) {
            this.zzaee = false;
            this.zzaeb.zzkb();
            if (this.pos < this.zzaeb.zzadr.size()) {
                zzjt zzjt = this.zzaeb;
                int i = this.pos;
                this.pos = i - 1;
                zzjt.zzbg(i);
                return;
            }
            zzki().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }

    private final Iterator<Entry<K, V>> zzki() {
        if (this.zzaea == null) {
            this.zzaea = this.zzaeb.zzads.entrySet().iterator();
        }
        return this.zzaea;
    }

    public final /* synthetic */ Object next() {
        this.zzaee = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i < this.zzaeb.zzadr.size()) {
            return (Entry) this.zzaeb.zzadr.get(this.pos);
        }
        return (Entry) zzki().next();
    }

    /* synthetic */ zzkb(zzjt zzjt, zzjw zzjw) {
        this(zzjt);
    }
}
