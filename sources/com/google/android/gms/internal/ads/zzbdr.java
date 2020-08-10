package com.google.android.gms.internal.ads;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzbdr implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzdyp;
    private final /* synthetic */ zzbdp zzdyq;

    private zzbdr(zzbdp zzbdp) {
        this.zzdyq = zzbdp;
        this.pos = this.zzdyq.zzdyk.size();
    }

    /* synthetic */ zzbdr(zzbdp zzbdp, zzbdq zzbdq) {
        this(zzbdp);
    }

    private final Iterator<Entry<K, V>> zzafx() {
        if (this.zzdyp == null) {
            this.zzdyp = this.zzdyq.zzdyn.entrySet().iterator();
        }
        return this.zzdyp;
    }

    public final boolean hasNext() {
        int i = this.pos;
        return (i > 0 && i <= this.zzdyq.zzdyk.size()) || zzafx().hasNext();
    }

    public final /* synthetic */ Object next() {
        Object obj;
        if (zzafx().hasNext()) {
            obj = zzafx().next();
        } else {
            List zzb = this.zzdyq.zzdyk;
            int i = this.pos - 1;
            this.pos = i;
            obj = zzb.get(i);
        }
        return (Entry) obj;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
