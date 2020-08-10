package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzjo implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzxd;
    private final /* synthetic */ zzjm zzxe;

    private zzjo(zzjm zzjm) {
        this.zzxe = zzjm;
        this.pos = this.zzxe.zzwy.size();
    }

    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzxe.zzwy.size()) || zzja().hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Entry<K, V>> zzja() {
        if (this.zzxd == null) {
            this.zzxd = this.zzxe.zzxb.entrySet().iterator();
        }
        return this.zzxd;
    }

    public final /* synthetic */ Object next() {
        if (zzja().hasNext()) {
            return (Entry) zzja().next();
        }
        List zzb = this.zzxe.zzwy;
        int i = this.pos - 1;
        this.pos = i;
        return (Entry) zzb.get(i);
    }

    /* synthetic */ zzjo(zzjm zzjm, zzjn zzjn) {
        this(zzjm);
    }
}
