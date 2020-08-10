package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.List;

final class zzjn<E> extends zzgb<E> {
    private static final zzjn<Object> zzadm;
    private final List<E> zzacb;

    public static <E> zzjn<E> zzju() {
        return zzadm;
    }

    zzjn() {
        this(new ArrayList(10));
    }

    private zzjn(List<E> list) {
        this.zzacb = list;
    }

    public final void add(int i, E e) {
        zzfz();
        this.zzacb.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzacb.get(i);
    }

    public final E remove(int i) {
        zzfz();
        E remove = this.zzacb.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzfz();
        E e2 = this.zzacb.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzacb.size();
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzacb);
            return new zzjn(arrayList);
        }
        throw new IllegalArgumentException();
    }

    static {
        zzjn<Object> zzjn = new zzjn<>(new ArrayList(0));
        zzadm = zzjn;
        zzjn.zzfy();
    }
}
