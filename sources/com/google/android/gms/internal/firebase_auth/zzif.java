package com.google.android.gms.internal.firebase_auth;

import java.util.Map.Entry;

final class zzif<K> implements Entry<K, Object> {
    private Entry<K, zzid> zzabu;

    private zzif(Entry<K, zzid> entry) {
        this.zzabu = entry;
    }

    public final K getKey() {
        return this.zzabu.getKey();
    }

    public final Object getValue() {
        if (((zzid) this.zzabu.getValue()) == null) {
            return null;
        }
        return zzid.zzja();
    }

    public final zzid zzjc() {
        return (zzid) this.zzabu.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzjc) {
            return ((zzid) this.zzabu.getValue()).zzj((zzjc) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
