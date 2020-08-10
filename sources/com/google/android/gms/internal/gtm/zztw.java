package com.google.android.gms.internal.gtm;

import java.util.Iterator;

final class zztw implements Iterator<String> {
    private final /* synthetic */ zztu zzber;
    private Iterator<String> zzbes = this.zzber.zzbeo.iterator();

    zztw(zztu zztu) {
        this.zzber = zztu;
    }

    public final boolean hasNext() {
        return this.zzbes.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzbes.next();
    }
}
