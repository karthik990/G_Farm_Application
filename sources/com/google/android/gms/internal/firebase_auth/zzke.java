package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class zzke extends AbstractSet<Entry<K, V>> {
    private final /* synthetic */ zzjt zzaeb;

    private zzke(zzjt zzjt) {
        this.zzaeb = zzjt;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new zzkb(this.zzaeb, null);
    }

    public int size() {
        return this.zzaeb.size();
    }

    public boolean contains(Object obj) {
        Entry entry = (Entry) obj;
        Object obj2 = this.zzaeb.get(entry.getKey());
        Object value = entry.getValue();
        return obj2 == value || (obj2 != null && obj2.equals(value));
    }

    public boolean remove(Object obj) {
        Entry entry = (Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zzaeb.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zzaeb.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Entry entry = (Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zzaeb.put((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    /* synthetic */ zzke(zzjt zzjt, zzjw zzjw) {
        this(zzjt);
    }
}
