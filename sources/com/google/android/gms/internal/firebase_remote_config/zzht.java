package com.google.android.gms.internal.firebase_remote_config;

import java.util.Map.Entry;

final class zzht<K> implements Entry<K, Object> {
    private Entry<K, zzhr> zzul;

    private zzht(Entry<K, zzhr> entry) {
        this.zzul = entry;
    }

    public final K getKey() {
        return this.zzul.getKey();
    }

    public final Object getValue() {
        if (((zzhr) this.zzul.getValue()) == null) {
            return null;
        }
        return zzhr.zzhq();
    }

    public final zzhr zzhr() {
        return (zzhr) this.zzul.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzio) {
            return ((zzhr) this.zzul.getValue()).zzi((zzio) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
