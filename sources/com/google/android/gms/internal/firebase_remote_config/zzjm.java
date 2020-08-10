package com.google.android.gms.internal.firebase_remote_config;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzjm<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzqb;
    private final int zzwx;
    /* access modifiers changed from: private */
    public List<zzjt> zzwy;
    /* access modifiers changed from: private */
    public Map<K, V> zzwz;
    private volatile zzjv zzxa;
    /* access modifiers changed from: private */
    public Map<K, V> zzxb;
    private volatile zzjp zzxc;

    static <FieldDescriptorType extends zzhc<FieldDescriptorType>> zzjm<FieldDescriptorType, Object> zzbn(int i) {
        return new zzjn(i);
    }

    private zzjm(int i) {
        this.zzwx = i;
        this.zzwy = Collections.emptyList();
        this.zzwz = Collections.emptyMap();
        this.zzxb = Collections.emptyMap();
    }

    public void zzer() {
        Map<K, V> map;
        Map<K, V> map2;
        if (!this.zzqb) {
            if (this.zzwz.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = Collections.unmodifiableMap(this.zzwz);
            }
            this.zzwz = map;
            if (this.zzxb.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(this.zzxb);
            }
            this.zzxb = map2;
            this.zzqb = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzqb;
    }

    public final int zziv() {
        return this.zzwy.size();
    }

    public final Entry<K, V> zzbo(int i) {
        return (Entry) this.zzwy.get(i);
    }

    public final Iterable<Entry<K, V>> zziw() {
        if (this.zzwz.isEmpty()) {
            return zzjq.zzjb();
        }
        return this.zzwz.entrySet();
    }

    public int size() {
        return this.zzwy.size() + this.zzwz.size();
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((K) comparable) >= 0 || this.zzwz.containsKey(comparable);
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza((K) comparable);
        if (zza >= 0) {
            return ((zzjt) this.zzwy.get(zza)).getValue();
        }
        return this.zzwz.get(comparable);
    }

    /* renamed from: zza */
    public final V put(K k, V v) {
        zziy();
        int zza = zza(k);
        if (zza >= 0) {
            return ((zzjt) this.zzwy.get(zza)).setValue(v);
        }
        zziy();
        if (this.zzwy.isEmpty() && !(this.zzwy instanceof ArrayList)) {
            this.zzwy = new ArrayList(this.zzwx);
        }
        int i = -(zza + 1);
        if (i >= this.zzwx) {
            return zziz().put(k, v);
        }
        int size = this.zzwy.size();
        int i2 = this.zzwx;
        if (size == i2) {
            zzjt zzjt = (zzjt) this.zzwy.remove(i2 - 1);
            zziz().put((Comparable) zzjt.getKey(), zzjt.getValue());
        }
        this.zzwy.add(i, new zzjt(this, k, v));
        return null;
    }

    public void clear() {
        zziy();
        if (!this.zzwy.isEmpty()) {
            this.zzwy.clear();
        }
        if (!this.zzwz.isEmpty()) {
            this.zzwz.clear();
        }
    }

    public V remove(Object obj) {
        zziy();
        Comparable comparable = (Comparable) obj;
        int zza = zza((K) comparable);
        if (zza >= 0) {
            return zzbp(zza);
        }
        if (this.zzwz.isEmpty()) {
            return null;
        }
        return this.zzwz.remove(comparable);
    }

    /* access modifiers changed from: private */
    public final V zzbp(int i) {
        zziy();
        V value = ((zzjt) this.zzwy.remove(i)).getValue();
        if (!this.zzwz.isEmpty()) {
            Iterator it = zziz().entrySet().iterator();
            this.zzwy.add(new zzjt(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final int zza(K k) {
        int size = this.zzwy.size() - 1;
        if (size >= 0) {
            int compareTo = k.compareTo((Comparable) ((zzjt) this.zzwy.get(size)).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = k.compareTo((Comparable) ((zzjt) this.zzwy.get(i2)).getKey());
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else if (compareTo2 <= 0) {
                return i2;
            } else {
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzxa == null) {
            this.zzxa = new zzjv(this, null);
        }
        return this.zzxa;
    }

    /* access modifiers changed from: 0000 */
    public final Set<Entry<K, V>> zzix() {
        if (this.zzxc == null) {
            this.zzxc = new zzjp(this, null);
        }
        return this.zzxc;
    }

    /* access modifiers changed from: private */
    public final void zziy() {
        if (this.zzqb) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zziz() {
        zziy();
        if (this.zzwz.isEmpty() && !(this.zzwz instanceof TreeMap)) {
            this.zzwz = new TreeMap();
            this.zzxb = ((TreeMap) this.zzwz).descendingMap();
        }
        return (SortedMap) this.zzwz;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzjm)) {
            return super.equals(obj);
        }
        zzjm zzjm = (zzjm) obj;
        int size = size();
        if (size != zzjm.size()) {
            return false;
        }
        int zziv = zziv();
        if (zziv != zzjm.zziv()) {
            return entrySet().equals(zzjm.entrySet());
        }
        for (int i = 0; i < zziv; i++) {
            if (!zzbo(i).equals(zzjm.zzbo(i))) {
                return false;
            }
        }
        if (zziv != size) {
            return this.zzwz.equals(zzjm.zzwz);
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zziv(); i2++) {
            i += ((zzjt) this.zzwy.get(i2)).hashCode();
        }
        return this.zzwz.size() > 0 ? i + this.zzwz.hashCode() : i;
    }

    /* synthetic */ zzjm(int i, zzjn zzjn) {
        this(i);
    }
}
