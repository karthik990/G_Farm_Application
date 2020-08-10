package com.flurry.sdk;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.flurry.sdk.cu */
public final class C1676cu<K, V> {

    /* renamed from: a */
    public final Map<K, List<V>> f851a = new HashMap();

    /* renamed from: b */
    private int f852b;

    /* renamed from: a */
    public final List<V> mo16384a(K k, boolean z) {
        List<V> list = (List) this.f851a.get(k);
        if (z && list == null) {
            int i = this.f852b;
            if (i > 0) {
                list = new ArrayList<>(i);
            } else {
                list = new ArrayList<>();
            }
            this.f851a.put(k, list);
        }
        return list;
    }

    /* renamed from: a */
    public final void mo16385a(K k, V v) {
        if (k != null) {
            mo16384a(k, true).add(v);
        }
    }

    /* renamed from: a */
    public final Collection<Entry<K, V>> mo16383a() {
        ArrayList arrayList = new ArrayList();
        for (Entry entry : this.f851a.entrySet()) {
            for (Object simpleImmutableEntry : (List) entry.getValue()) {
                arrayList.add(new SimpleImmutableEntry(entry.getKey(), simpleImmutableEntry));
            }
        }
        return arrayList;
    }
}
