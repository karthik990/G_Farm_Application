package com.google.android.gms.internal.firebase_remote_config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzhw extends zzfq<String> implements zzhx, RandomAccess {
    private static final zzhw zzuq;
    private static final zzhx zzur = zzuq;
    private final List<Object> zzus;

    public zzhw() {
        this(10);
    }

    public zzhw(int i) {
        this(new ArrayList<>(i));
    }

    private zzhw(ArrayList<Object> arrayList) {
        this.zzus = arrayList;
    }

    public final int size() {
        return this.zzus.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzes();
        if (collection instanceof zzhx) {
            collection = ((zzhx) collection).zzhs();
        }
        boolean addAll = this.zzus.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzes();
        this.zzus.clear();
        this.modCount++;
    }

    public final void zzd(zzfw zzfw) {
        zzes();
        this.zzus.add(zzfw);
        this.modCount++;
    }

    public final Object zzbe(int i) {
        return this.zzus.get(i);
    }

    private static String zzo(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzfw) {
            return ((zzfw) obj).zzb(zzhk.UTF_8);
        }
        return zzhk.zze((byte[]) obj);
    }

    public final List<?> zzhs() {
        return Collections.unmodifiableList(this.zzus);
    }

    public final zzhx zzht() {
        return zzeq() ? new zzke(this) : this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzes();
        return zzo(this.zzus.set(i, str));
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
        zzes();
        Object remove = this.zzus.remove(i);
        this.modCount++;
        return zzo(remove);
    }

    public final /* bridge */ /* synthetic */ boolean zzeq() {
        return super.zzeq();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzes();
        this.zzus.add(i, str);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzhn zzu(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzus);
            return new zzhw(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzus.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzfw) {
            zzfw zzfw = (zzfw) obj;
            String zzb = zzfw.zzb(zzhk.UTF_8);
            if (zzfw.zzew()) {
                this.zzus.set(i, zzb);
            }
            return zzb;
        }
        byte[] bArr = (byte[]) obj;
        String zze = zzhk.zze(bArr);
        if (zzhk.zzd(bArr)) {
            this.zzus.set(i, zze);
        }
        return zze;
    }

    static {
        zzhw zzhw = new zzhw();
        zzuq = zzhw;
        zzhw.zzer();
    }
}
