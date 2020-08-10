package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;

final class zzkg implements Iterator<String> {
    private final /* synthetic */ zzke zzxq;
    private Iterator<String> zzxr = this.zzxq.zzxn.iterator();

    zzkg(zzke zzke) {
        this.zzxq = zzke;
    }

    public final boolean hasNext() {
        return this.zzxr.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzxr.next();
    }
}
