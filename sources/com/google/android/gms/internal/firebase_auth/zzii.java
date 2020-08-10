package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzii<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzaby;

    public zzii(Iterator<Entry<K, Object>> it) {
        this.zzaby = it;
    }

    public final boolean hasNext() {
        return this.zzaby.hasNext();
    }

    public final void remove() {
        this.zzaby.remove();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzaby.next();
        return entry.getValue() instanceof zzid ? new zzif(entry) : entry;
    }
}
