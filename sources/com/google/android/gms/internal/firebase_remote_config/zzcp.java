package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzcp implements Iterator<T> {
    private int index = 0;
    private final int length = Array.getLength(this.zzgj.zzgi);
    private final /* synthetic */ zzco zzgj;

    zzcp(zzco zzco) {
        this.zzgj = zzco;
    }

    public final boolean hasNext() {
        return this.index < this.length;
    }

    public final T next() {
        if (hasNext()) {
            Object obj = this.zzgj.zzgi;
            int i = this.index;
            this.index = i + 1;
            return Array.get(obj, i);
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
