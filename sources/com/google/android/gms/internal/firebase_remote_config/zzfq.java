package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class zzfq<E> extends AbstractList<E> implements zzhn<E> {
    private boolean zzog = true;

    zzfq() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        if (!(obj instanceof RandomAccess)) {
            return super.equals(obj);
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < size(); i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public boolean add(E e) {
        zzes();
        return super.add(e);
    }

    public void add(int i, E e) {
        zzes();
        super.add(i, e);
    }

    public boolean addAll(Collection<? extends E> collection) {
        zzes();
        return super.addAll(collection);
    }

    public boolean addAll(int i, Collection<? extends E> collection) {
        zzes();
        return super.addAll(i, collection);
    }

    public void clear() {
        zzes();
        super.clear();
    }

    public boolean zzeq() {
        return this.zzog;
    }

    public final void zzer() {
        this.zzog = false;
    }

    public E remove(int i) {
        zzes();
        return super.remove(i);
    }

    public boolean remove(Object obj) {
        zzes();
        return super.remove(obj);
    }

    public boolean removeAll(Collection<?> collection) {
        zzes();
        return super.removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        zzes();
        return super.retainAll(collection);
    }

    public E set(int i, E e) {
        zzes();
        return super.set(i, e);
    }

    /* access modifiers changed from: protected */
    public final void zzes() {
        if (!this.zzog) {
            throw new UnsupportedOperationException();
        }
    }
}
