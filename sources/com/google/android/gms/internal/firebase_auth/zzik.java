package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzik extends zzgb<String> implements zzij, RandomAccess {
    private static final zzik zzabz;
    private static final zzij zzaca = zzabz;
    private final List<Object> zzacb;

    public zzik() {
        this(10);
    }

    public zzik(int i) {
        this(new ArrayList<>(i));
    }

    private zzik(ArrayList<Object> arrayList) {
        this.zzacb = arrayList;
    }

    public final int size() {
        return this.zzacb.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzfz();
        if (collection instanceof zzij) {
            collection = ((zzij) collection).zzjd();
        }
        boolean addAll = this.zzacb.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzfz();
        this.zzacb.clear();
        this.modCount++;
    }

    public final void zzc(zzgf zzgf) {
        zzfz();
        this.zzacb.add(zzgf);
        this.modCount++;
    }

    public final Object zzax(int i) {
        return this.zzacb.get(i);
    }

    private static String zzh(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgf) {
            return ((zzgf) obj).zzgc();
        }
        return zzht.zzf((byte[]) obj);
    }

    public final List<?> zzjd() {
        return Collections.unmodifiableList(this.zzacb);
    }

    public final zzij zzje() {
        return zzfx() ? new zzkp(this) : this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzfz();
        return zzh(this.zzacb.set(i, str));
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public final /* synthetic */ Object remove(int i) {
        zzfz();
        Object remove = this.zzacb.remove(i);
        this.modCount++;
        return zzh(remove);
    }

    public final /* bridge */ /* synthetic */ boolean zzfx() {
        return super.zzfx();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzfz();
        this.zzacb.add(i, str);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzacb);
            return new zzik(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzacb.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgf) {
            zzgf zzgf = (zzgf) obj;
            String zzgc = zzgf.zzgc();
            if (zzgf.zzgd()) {
                this.zzacb.set(i, zzgc);
            }
            return zzgc;
        }
        byte[] bArr = (byte[]) obj;
        String zzf = zzht.zzf(bArr);
        if (zzht.zze(bArr)) {
            this.zzacb.set(i, zzf);
        }
        return zzf;
    }

    static {
        zzik zzik = new zzik();
        zzabz = zzik;
        zzik.zzfy();
    }
}
