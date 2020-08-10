package com.google.android.gms.internal.ads;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzbds extends zzbdy {
    private final /* synthetic */ zzbdp zzdyq;

    private zzbds(zzbdp zzbdp) {
        this.zzdyq = zzbdp;
        super(zzbdp, null);
    }

    /* synthetic */ zzbds(zzbdp zzbdp, zzbdq zzbdq) {
        this(zzbdp);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzbdr(this.zzdyq, null);
    }
}
