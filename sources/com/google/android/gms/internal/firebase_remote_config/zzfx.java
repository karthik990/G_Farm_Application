package com.google.android.gms.internal.firebase_remote_config;

import java.util.NoSuchElementException;

final class zzfx extends zzfz {
    private final int limit = this.zzos.size();
    private int position = 0;
    private final /* synthetic */ zzfw zzos;

    zzfx(zzfw zzfw) {
        this.zzos = zzfw;
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final byte nextByte() {
        int i = this.position;
        if (i < this.limit) {
            this.position = i + 1;
            return this.zzos.zzw(i);
        }
        throw new NoSuchElementException();
    }
}
