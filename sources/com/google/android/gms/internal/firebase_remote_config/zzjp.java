package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzjp extends zzjv {
    private final /* synthetic */ zzjm zzxe;

    private zzjp(zzjm zzjm) {
        this.zzxe = zzjm;
        super(zzjm, null);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzjo(this.zzxe, null);
    }

    /* synthetic */ zzjp(zzjm zzjm, zzjn zzjn) {
        this(zzjm);
    }
}
