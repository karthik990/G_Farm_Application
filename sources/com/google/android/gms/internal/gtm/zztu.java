package com.google.android.gms.internal.gtm;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zztu extends AbstractList<String> implements zzrt, RandomAccess {
    /* access modifiers changed from: private */
    public final zzrt zzbeo;

    public zztu(zzrt zzrt) {
        this.zzbeo = zzrt;
    }

    public final zzrt zzqb() {
        return this;
    }

    public final Object zzbn(int i) {
        return this.zzbeo.zzbn(i);
    }

    public final int size() {
        return this.zzbeo.size();
    }

    public final void zzc(zzps zzps) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zztv(this, i);
    }

    public final Iterator<String> iterator() {
        return new zztw(this);
    }

    public final List<?> zzqa() {
        return this.zzbeo.zzqa();
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzbeo.get(i);
    }
}
