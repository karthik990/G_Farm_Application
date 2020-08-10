package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzrs extends zzpo<String> implements zzrt, RandomAccess {
    private static final zzrs zzbce;
    private static final zzrt zzbcf = zzbce;
    private final List<Object> zzbcg;

    public zzrs() {
        this(10);
    }

    public zzrs(int i) {
        this(new ArrayList<>(i));
    }

    private zzrs(ArrayList<Object> arrayList) {
        this.zzbcg = arrayList;
    }

    public final int size() {
        return this.zzbcg.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzmz();
        if (collection instanceof zzrt) {
            collection = ((zzrt) collection).zzqa();
        }
        boolean addAll = this.zzbcg.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzmz();
        this.zzbcg.clear();
        this.modCount++;
    }

    public final void zzc(zzps zzps) {
        zzmz();
        this.zzbcg.add(zzps);
        this.modCount++;
    }

    public final Object zzbn(int i) {
        return this.zzbcg.get(i);
    }

    private static String zzv(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzps) {
            return ((zzps) obj).zznc();
        }
        return zzre.zzj((byte[]) obj);
    }

    public final List<?> zzqa() {
        return Collections.unmodifiableList(this.zzbcg);
    }

    public final zzrt zzqb() {
        return zzmy() ? new zztu(this) : this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzmz();
        return zzv(this.zzbcg.set(i, str));
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
        zzmz();
        Object remove = this.zzbcg.remove(i);
        this.modCount++;
        return zzv(remove);
    }

    public final /* bridge */ /* synthetic */ boolean zzmy() {
        return super.zzmy();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzmz();
        this.zzbcg.add(i, str);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzrj zzaj(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzbcg);
            return new zzrs(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzbcg.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzps) {
            zzps zzps = (zzps) obj;
            String zznc = zzps.zznc();
            if (zzps.zznd()) {
                this.zzbcg.set(i, zznc);
            }
            return zznc;
        }
        byte[] bArr = (byte[]) obj;
        String zzj = zzre.zzj(bArr);
        if (zzre.zzi(bArr)) {
            this.zzbcg.set(i, zzj);
        }
        return zzj;
    }

    static {
        zzrs zzrs = new zzrs();
        zzbce = zzrs;
        zzrs.zzmi();
    }
}
