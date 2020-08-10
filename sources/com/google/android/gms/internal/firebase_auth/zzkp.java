package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzkp extends AbstractList<String> implements zzij, RandomAccess {
    /* access modifiers changed from: private */
    public final zzij zzaeq;

    public zzkp(zzij zzij) {
        this.zzaeq = zzij;
    }

    public final zzij zzje() {
        return this;
    }

    public final Object zzax(int i) {
        return this.zzaeq.zzax(i);
    }

    public final int size() {
        return this.zzaeq.size();
    }

    public final void zzc(zzgf zzgf) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzko(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzkr(this);
    }

    public final List<?> zzjd() {
        return this.zzaeq.zzjd();
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzaeq.get(i);
    }
}
