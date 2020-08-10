package com.google.android.gms.internal.firebase_remote_config;

import java.util.NoSuchElementException;

final class zzjg extends zzfz {
    private final zzji zzwn = new zzji(this.zzwp, null);
    private zzgd zzwo = zzip();
    private final /* synthetic */ zzjf zzwp;

    zzjg(zzjf zzjf) {
        this.zzwp = zzjf;
    }

    private final zzgd zzip() {
        if (this.zzwn.hasNext()) {
            return (zzgd) ((zzgf) this.zzwn.next()).iterator();
        }
        return null;
    }

    public final boolean hasNext() {
        return this.zzwo != null;
    }

    public final byte nextByte() {
        zzgd zzgd = this.zzwo;
        if (zzgd != null) {
            byte nextByte = zzgd.nextByte();
            if (!this.zzwo.hasNext()) {
                this.zzwo = zzip();
            }
            return nextByte;
        }
        throw new NoSuchElementException();
    }
}
