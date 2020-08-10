package com.google.android.gms.tagmanager;

import java.util.List;

final class zzap implements zzaq {
    private final /* synthetic */ DataLayer zzafv;

    zzap(DataLayer dataLayer) {
        this.zzafv = dataLayer;
    }

    public final void zzc(List<zza> list) {
        for (zza zza : list) {
            this.zzafv.zze(DataLayer.zzg(zza.mKey, zza.mValue));
        }
        this.zzafv.zzafu.countDown();
    }
}
