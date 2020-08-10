package com.google.android.gms.internal.gtm;

import java.util.NoSuchElementException;

final class zzpt extends zzpv {
    private final int limit = this.zzawa.size();
    private int position = 0;
    private final /* synthetic */ zzps zzawa;

    zzpt(zzps zzps) {
        this.zzawa = zzps;
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final byte nextByte() {
        int i = this.position;
        if (i < this.limit) {
            this.position = i + 1;
            return this.zzawa.zzal(i);
        }
        throw new NoSuchElementException();
    }
}
