package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzke extends AbstractList<String> implements zzhx, RandomAccess {
    /* access modifiers changed from: private */
    public final zzhx zzxn;

    public zzke(zzhx zzhx) {
        this.zzxn = zzhx;
    }

    public final zzhx zzht() {
        return this;
    }

    public final Object zzbe(int i) {
        return this.zzxn.zzbe(i);
    }

    public final int size() {
        return this.zzxn.size();
    }

    public final void zzd(zzfw zzfw) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzkf(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzkg(this);
    }

    public final List<?> zzhs() {
        return this.zzxn.zzhs();
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzxn.get(i);
    }
}
