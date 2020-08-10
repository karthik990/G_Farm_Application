package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzl;

final class zzfh {
    private zzdz<zzl> zzakf;
    private zzl zzakg;

    public zzfh(zzdz<zzl> zzdz, zzl zzl) {
        this.zzakf = zzdz;
        this.zzakg = zzl;
    }

    public final zzdz<zzl> zzjh() {
        return this.zzakf;
    }

    public final zzl zzji() {
        return this.zzakg;
    }

    public final int getSize() {
        int zzse = ((zzl) this.zzakf.getObject()).zzse();
        zzl zzl = this.zzakg;
        return zzse + (zzl == null ? 0 : zzl.zzse());
    }
}
