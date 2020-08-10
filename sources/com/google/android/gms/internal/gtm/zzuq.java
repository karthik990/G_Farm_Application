package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzuq;
import java.io.IOException;

public abstract class zzuq<M extends zzuq<M>> extends zzuw {
    protected zzus zzbhb;

    /* access modifiers changed from: protected */
    public int zzy() {
        if (this.zzbhb == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzbhb.size(); i2++) {
            i += this.zzbhb.zzce(i2).zzy();
        }
        return i;
    }

    public void zza(zzuo zzuo) throws IOException {
        if (this.zzbhb != null) {
            for (int i = 0; i < this.zzbhb.size(); i++) {
                this.zzbhb.zzce(i).zza(zzuo);
            }
        }
    }

    public final <T> T zza(zzur<M, T> zzur) {
        zzus zzus = this.zzbhb;
        if (zzus == null) {
            return null;
        }
        zzut zzcd = zzus.zzcd(zzur.tag >>> 3);
        if (zzcd == null) {
            return null;
        }
        return zzcd.zzb(zzur);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzun zzun, int i) throws IOException {
        int position = zzun.getPosition();
        if (!zzun.zzao(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzuy zzuy = new zzuy(i, zzun.zzt(position, zzun.getPosition() - position));
        zzut zzut = null;
        zzus zzus = this.zzbhb;
        if (zzus == null) {
            this.zzbhb = new zzus();
        } else {
            zzut = zzus.zzcd(i2);
        }
        if (zzut == null) {
            zzut = new zzut();
            this.zzbhb.zza(i2, zzut);
        }
        zzut.zza(zzuy);
        return true;
    }

    public final /* synthetic */ zzuw zzry() throws CloneNotSupportedException {
        return (zzuq) clone();
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzuq zzuq = (zzuq) super.clone();
        zzuu.zza(this, zzuq);
        return zzuq;
    }
}
