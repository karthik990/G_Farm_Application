package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzjy extends zzke {
    private final /* synthetic */ zzjt zzaeb;

    private zzjy(zzjt zzjt) {
        this.zzaeb = zzjt;
        super(zzjt, null);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzjv(this.zzaeb, null);
    }

    /* synthetic */ zzjy(zzjt zzjt, zzjw zzjw) {
        this(zzjt);
    }
}
