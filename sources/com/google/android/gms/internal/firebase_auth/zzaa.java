package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class zzaa<T> implements Iterator<T> {
    private int zzgb = zzab.zzge;
    @NullableDecl
    private T zzgc;

    protected zzaa() {
    }

    /* access modifiers changed from: protected */
    public abstract T zzbw();

    /* access modifiers changed from: protected */
    @NullableDecl
    public final T zzbx() {
        this.zzgb = zzab.zzgf;
        return null;
    }

    public final boolean hasNext() {
        if (this.zzgb != zzab.zzgg) {
            int i = zzac.zzgi[this.zzgb - 1];
            if (i == 1) {
                return true;
            }
            if (i != 2) {
                this.zzgb = zzab.zzgg;
                this.zzgc = zzbw();
                if (this.zzgb != zzab.zzgf) {
                    this.zzgb = zzab.zzgd;
                    return true;
                }
            }
            return false;
        }
        throw new IllegalStateException();
    }

    public final T next() {
        if (hasNext()) {
            this.zzgb = zzab.zzge;
            T t = this.zzgc;
            this.zzgc = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
