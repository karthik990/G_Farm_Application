package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;

final class zzkr implements Iterator<String> {
    private final /* synthetic */ zzkp zzaep;
    private Iterator<String> zzafl = this.zzaep.zzaeq.iterator();

    zzkr(zzkp zzkp) {
        this.zzaep = zzkp;
    }

    public final boolean hasNext() {
        return this.zzafl.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzafl.next();
    }
}
