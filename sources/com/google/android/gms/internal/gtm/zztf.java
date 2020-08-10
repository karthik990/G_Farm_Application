package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map.Entry;

final class zztf extends zztl {
    private final /* synthetic */ zztc zzbef;

    private zztf(zztc zztc) {
        this.zzbef = zztc;
        super(zztc, null);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzte(this.zzbef, null);
    }

    /* synthetic */ zztf(zztc zztc, zztd zztd) {
        this(zztc);
    }
}
