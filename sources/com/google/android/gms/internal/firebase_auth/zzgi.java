package com.google.android.gms.internal.firebase_auth;

import java.util.NoSuchElementException;

final class zzgi extends zzgk {
    private final int limit = this.zzvz.size();
    private int position = 0;
    private final /* synthetic */ zzgf zzvz;

    zzgi(zzgf zzgf) {
        this.zzvz = zzgf;
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final byte nextByte() {
        int i = this.position;
        if (i < this.limit) {
            this.position = i + 1;
            return this.zzvz.zzq(i);
        }
        throw new NoSuchElementException();
    }
}
