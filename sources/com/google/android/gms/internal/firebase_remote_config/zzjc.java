package com.google.android.gms.internal.firebase_remote_config;

import java.util.ArrayList;
import java.util.List;

final class zzjc<E> extends zzfq<E> {
    private static final zzjc<Object> zzwg;
    private final List<E> zzus;

    public static <E> zzjc<E> zzil() {
        return zzwg;
    }

    zzjc() {
        this(new ArrayList(10));
    }

    private zzjc(List<E> list) {
        this.zzus = list;
    }

    public final void add(int i, E e) {
        zzes();
        this.zzus.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzus.get(i);
    }

    public final E remove(int i) {
        zzes();
        E remove = this.zzus.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzes();
        E e2 = this.zzus.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzus.size();
    }

    public final /* synthetic */ zzhn zzu(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzus);
            return new zzjc(arrayList);
        }
        throw new IllegalArgumentException();
    }

    static {
        zzjc<Object> zzjc = new zzjc<>(new ArrayList(0));
        zzwg = zzjc;
        zzjc.zzer();
    }
}
