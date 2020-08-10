package com.google.android.gms.internal.gtm;

import java.util.Map.Entry;

final class zzrp<K> implements Entry<K, Object> {
    private Entry<K, zzrn> zzbbz;

    private zzrp(Entry<K, zzrn> entry) {
        this.zzbbz = entry;
    }

    public final K getKey() {
        return this.zzbbz.getKey();
    }

    public final Object getValue() {
        if (((zzrn) this.zzbbz.getValue()) == null) {
            return null;
        }
        return zzrn.zzpy();
    }

    public final zzrn zzpz() {
        return (zzrn) this.zzbbz.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzsk) {
            return ((zzrn) this.zzbbz.getValue()).zzi((zzsk) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
