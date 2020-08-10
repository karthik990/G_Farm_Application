package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzrq<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzbca;

    public zzrq(Iterator<Entry<K, Object>> it) {
        this.zzbca = it;
    }

    public final boolean hasNext() {
        return this.zzbca.hasNext();
    }

    public final void remove() {
        this.zzbca.remove();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzbca.next();
        return entry.getValue() instanceof zzrn ? new zzrp(entry) : entry;
    }
}
