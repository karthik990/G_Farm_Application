package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

final class zzbv implements Iterator<Entry<String, Object>> {
    private final /* synthetic */ zzbt zzfi;
    private int zzfj = -1;
    private zzby zzfk;
    private Object zzfl;
    private boolean zzfm;
    private boolean zzfn;
    private zzby zzfo;

    zzbv(zzbt zzbt) {
        this.zzfi = zzbt;
    }

    public final boolean hasNext() {
        if (!this.zzfn) {
            this.zzfn = true;
            this.zzfl = null;
            while (this.zzfl == null) {
                int i = this.zzfj + 1;
                this.zzfj = i;
                if (i >= this.zzfi.zzar.zzer.size()) {
                    break;
                }
                this.zzfk = this.zzfi.zzar.zzae((String) this.zzfi.zzar.zzer.get(this.zzfj));
                this.zzfl = this.zzfk.zzh(this.zzfi.zzff);
            }
        }
        if (this.zzfl != null) {
            return true;
        }
        return false;
    }

    public final void remove() {
        zzds.checkState(this.zzfo != null && !this.zzfm);
        this.zzfm = true;
        this.zzfo.zzb(this.zzfi.zzff, null);
    }

    public final /* synthetic */ Object next() {
        if (hasNext()) {
            this.zzfo = this.zzfk;
            Object obj = this.zzfl;
            this.zzfn = false;
            this.zzfm = false;
            this.zzfk = null;
            this.zzfl = null;
            return new zzbu(this.zzfi, this.zzfo, obj);
        }
        throw new NoSuchElementException();
    }
}
