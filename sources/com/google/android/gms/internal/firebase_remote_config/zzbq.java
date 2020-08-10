package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.WeakHashMap;

public final class zzbq {
    private static final Map<Class<?>, zzbq> zzem = new WeakHashMap();
    private static final Map<Class<?>, zzbq> zzen = new WeakHashMap();
    private final Class<?> zzeo;
    private final boolean zzep;
    private final IdentityHashMap<String, zzby> zzeq = new IdentityHashMap<>();
    final List<String> zzer;

    public static zzbq zzc(Class<?> cls) {
        return zza(cls, false);
    }

    public static zzbq zza(Class<?> cls, boolean z) {
        zzbq zzbq;
        if (cls == null) {
            return null;
        }
        Map<Class<?>, zzbq> map = z ? zzen : zzem;
        synchronized (map) {
            zzbq = (zzbq) map.get(cls);
            if (zzbq == null) {
                zzbq = new zzbq(cls, z);
                map.put(cls, zzbq);
            }
        }
        return zzbq;
    }

    public final boolean zzbw() {
        return this.zzep;
    }

    public final zzby zzae(String str) {
        if (str != null) {
            if (this.zzep) {
                str = str.toLowerCase(Locale.US);
            }
            str = str.intern();
        }
        return (zzby) this.zzeq.get(str);
    }

    public final boolean isEnum() {
        return this.zzeo.isEnum();
    }

    private zzbq(Class<?> cls, boolean z) {
        Field[] declaredFields;
        Field field;
        this.zzeo = cls;
        this.zzep = z;
        boolean z2 = !z || !cls.isEnum();
        String valueOf = String.valueOf(cls);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 31);
        sb.append("cannot ignore case on an enum: ");
        sb.append(valueOf);
        String sb2 = sb.toString();
        if (z2) {
            TreeSet treeSet = new TreeSet(new zzbr(this));
            for (Field field2 : cls.getDeclaredFields()) {
                zzby zza = zzby.zza(field2);
                if (zza != null) {
                    String name = zza.getName();
                    if (z) {
                        name = name.toLowerCase(Locale.US).intern();
                    }
                    zzby zzby = (zzby) this.zzeq.get(name);
                    boolean z3 = zzby == null;
                    Object[] objArr = new Object[4];
                    objArr[0] = z ? "case-insensitive " : "";
                    objArr[1] = name;
                    objArr[2] = field2;
                    if (zzby == null) {
                        field = null;
                    } else {
                        field = zzby.zzbz();
                    }
                    objArr[3] = field;
                    if (z3) {
                        this.zzeq.put(name, zza);
                        treeSet.add(name);
                    } else {
                        throw new IllegalArgumentException(zzdz.zza("two fields have the same %sname <%s>: %s and %s", objArr));
                    }
                }
            }
            Class superclass = cls.getSuperclass();
            if (superclass != null) {
                zzbq zza2 = zza(superclass, z);
                treeSet.addAll(zza2.zzer);
                for (Entry entry : zza2.zzeq.entrySet()) {
                    String str = (String) entry.getKey();
                    if (!this.zzeq.containsKey(str)) {
                        this.zzeq.put(str, (zzby) entry.getValue());
                    }
                }
            }
            this.zzer = treeSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(treeSet));
            return;
        }
        throw new IllegalArgumentException(String.valueOf(sb2));
    }

    public final Collection<zzby> zzbx() {
        return Collections.unmodifiableCollection(this.zzeq.values());
    }
}
