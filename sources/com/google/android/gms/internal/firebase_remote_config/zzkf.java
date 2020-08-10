package com.google.android.gms.internal.firebase_remote_config;

import java.util.ListIterator;

final class zzkf implements ListIterator<String> {
    private ListIterator<String> zzxo = this.zzxq.zzxn.listIterator(this.zzxp);
    private final /* synthetic */ int zzxp;
    private final /* synthetic */ zzke zzxq;

    zzkf(zzke zzke, int i) {
        this.zzxq = zzke;
        this.zzxp = i;
    }

    public final boolean hasNext() {
        return this.zzxo.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zzxo.hasPrevious();
    }

    public final int nextIndex() {
        return this.zzxo.nextIndex();
    }

    public final int previousIndex() {
        return this.zzxo.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object previous() {
        return (String) this.zzxo.previous();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzxo.next();
    }
}
