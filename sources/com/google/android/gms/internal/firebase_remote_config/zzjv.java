package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class zzjv extends AbstractSet<Entry<K, V>> {
    private final /* synthetic */ zzjm zzxe;

    private zzjv(zzjm zzjm) {
        this.zzxe = zzjm;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new zzju(this.zzxe, null);
    }

    public int size() {
        return this.zzxe.size();
    }

    public boolean contains(Object obj) {
        Entry entry = (Entry) obj;
        Object obj2 = this.zzxe.get(entry.getKey());
        Object value = entry.getValue();
        return obj2 == value || (obj2 != null && obj2.equals(value));
    }

    public boolean remove(Object obj) {
        Entry entry = (Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zzxe.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zzxe.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Entry entry = (Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zzxe.put((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    /* synthetic */ zzjv(zzjm zzjm, zzjn zzjn) {
        this(zzjm);
    }
}
