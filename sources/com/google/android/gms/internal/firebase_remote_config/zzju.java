package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzju implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzxd;
    private final /* synthetic */ zzjm zzxe;
    private boolean zzxi;

    private zzju(zzjm zzjm) {
        this.zzxe = zzjm;
        this.pos = -1;
    }

    public final boolean hasNext() {
        if (this.pos + 1 < this.zzxe.zzwy.size() || (!this.zzxe.zzwz.isEmpty() && zzja().hasNext())) {
            return true;
        }
        return false;
    }

    public final void remove() {
        if (this.zzxi) {
            this.zzxi = false;
            this.zzxe.zziy();
            if (this.pos < this.zzxe.zzwy.size()) {
                zzjm zzjm = this.zzxe;
                int i = this.pos;
                this.pos = i - 1;
                zzjm.zzbp(i);
                return;
            }
            zzja().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }

    private final Iterator<Entry<K, V>> zzja() {
        if (this.zzxd == null) {
            this.zzxd = this.zzxe.zzwz.entrySet().iterator();
        }
        return this.zzxd;
    }

    public final /* synthetic */ Object next() {
        this.zzxi = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i < this.zzxe.zzwy.size()) {
            return (Entry) this.zzxe.zzwy.get(this.pos);
        }
        return (Entry) zzja().next();
    }

    /* synthetic */ zzju(zzjm zzjm, zzjn zzjn) {
        this(zzjm);
    }
}
