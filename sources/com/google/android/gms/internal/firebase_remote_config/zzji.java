package com.google.android.gms.internal.firebase_remote_config;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzji implements Iterator<zzgf> {
    private final ArrayDeque<zzjf> zzwr;
    private zzgf zzws;

    private zzji(zzfw zzfw) {
        this.zzwr = new ArrayDeque<>();
        this.zzws = zzf(zzfw);
    }

    private final zzgf zzf(zzfw zzfw) {
        while (zzfw instanceof zzjf) {
            zzjf zzjf = (zzjf) zzfw;
            this.zzwr.push(zzjf);
            zzfw = zzjf.zzwj;
        }
        return (zzgf) zzfw;
    }

    public final boolean hasNext() {
        return this.zzws != null;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        zzgf zzgf;
        boolean z;
        zzgf zzgf2 = this.zzws;
        if (zzgf2 != null) {
            while (true) {
                if (!this.zzwr.isEmpty()) {
                    zzgf = zzf(((zzjf) this.zzwr.pop()).zzwk);
                    if (zzgf.size() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        break;
                    }
                } else {
                    zzgf = null;
                    break;
                }
            }
            this.zzws = zzgf;
            return zzgf2;
        }
        throw new NoSuchElementException();
    }

    /* synthetic */ zzji(zzfw zzfw, zzjg zzjg) {
        this(zzfw);
    }
}
