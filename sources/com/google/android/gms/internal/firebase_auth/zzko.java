package com.google.android.gms.internal.firebase_auth;

import java.util.ListIterator;

final class zzko implements ListIterator<String> {
    private ListIterator<String> zzaen = this.zzaep.zzaeq.listIterator(this.zzaeo);
    private final /* synthetic */ int zzaeo;
    private final /* synthetic */ zzkp zzaep;

    zzko(zzkp zzkp, int i) {
        this.zzaep = zzkp;
        this.zzaeo = i;
    }

    public final boolean hasNext() {
        return this.zzaen.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zzaen.hasPrevious();
    }

    public final int nextIndex() {
        return this.zzaen.nextIndex();
    }

    public final int previousIndex() {
        return this.zzaen.previousIndex();
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
        return (String) this.zzaen.previous();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzaen.next();
    }
}
