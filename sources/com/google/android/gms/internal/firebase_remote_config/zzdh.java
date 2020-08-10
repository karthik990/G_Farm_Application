package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class zzdh<T> implements Iterator<T> {
    private int zzgw = zzdj.zzha;
    @NullableDecl
    private T zzgx;

    protected zzdh() {
    }

    /* access modifiers changed from: protected */
    public abstract T zzch();

    /* access modifiers changed from: protected */
    @NullableDecl
    public final T zzci() {
        this.zzgw = zzdj.zzhb;
        return null;
    }

    public final boolean hasNext() {
        zzds.checkState(this.zzgw != zzdj.zzhc);
        int i = zzdi.zzgy[this.zzgw - 1];
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            this.zzgw = zzdj.zzhc;
            this.zzgx = zzch();
            if (this.zzgw != zzdj.zzhb) {
                this.zzgw = zzdj.zzgz;
                return true;
            }
        }
        return false;
    }

    public final T next() {
        if (hasNext()) {
            this.zzgw = zzdj.zzha;
            T t = this.zzgx;
            this.zzgx = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
