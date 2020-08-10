package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzhu<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzum;

    public zzhu(Iterator<Entry<K, Object>> it) {
        this.zzum = it;
    }

    public final boolean hasNext() {
        return this.zzum.hasNext();
    }

    public final void remove() {
        this.zzum.remove();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzum.next();
        return entry.getValue() instanceof zzhr ? new zzht(entry) : entry;
    }
}
